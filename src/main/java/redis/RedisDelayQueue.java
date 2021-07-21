package redis;

import com.alibaba.fastjson.JSON;
import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Wang Hao
 * @date 2021/6/21 13:55
 */
@Slf4j
public class RedisDelayQueue<T> {

    final RedisCommands<String, String> cmd;
    final AtomicInteger counter;
    final String queueName;
    final Set<Looper> loopers;
    boolean shutdown;

    public RedisDelayQueue(RedisClient redisClient, String queueName) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        this.cmd = connect.sync();
        this.counter = new AtomicInteger();
        this.queueName = queueName;
        this.loopers = new HashSet<>();
        Looper looper = new Looper();
        looper.start();
        loopers.add(looper);
    }

    public void offer(T task, long delay) {
        //serialize a task
        TaskItem taskItem = TaskItem.builder().id(counter.getAndIncrement()).body(JSON.toJSONString(task)).build();
        cmd.zadd(queueName, (double) (System.currentTimeMillis() + delay), JSON.toJSONString(taskItem));
    }

    public void addLooper() {
        Looper looper = new Looper();
        looper.start();
        loopers.add(looper);
    }

    public synchronized void shutdown() {
        if (!shutdown) {
            shutdown = true;
            loopers.forEach(Thread::interrupt);
        }
    }

    @ToString
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    static class TaskItem {

        int id;
        String body;
    }

    @EqualsAndHashCode(callSuper = false)
    class Looper extends Thread {

        AtomicInteger index = new AtomicInteger(0);
        Executor executor = new ThreadPoolExecutor(2, 2, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            r -> new Thread(r, "executor-" + index.getAndIncrement()));

        @Override
        public void run() {
            while (!interrupted()) {
                List<String> tasks = cmd
                    .zrangebyscore(queueName, Range.create(0, System.currentTimeMillis()), Limit.create(0, 10));
                if (tasks.isEmpty()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        currentThread().interrupt();
                    }
                    continue;
                }
                for (String task : tasks) {
                    if (cmd.zrem(queueName, task) > 0) {
                        executor.execute(() -> log.info("execute task[{}]", task));
                    }
                }
            }
            log.info("end");
        }
    }

    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create(new RedisURI("192.168.0.31", 6379, Duration.ofSeconds(60)));
        RedisDelayQueue<String> delayQueue = new RedisDelayQueue<>(redisClient, "delay");
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
            r -> new Thread(r, "main-scheduler"));
        scheduledThreadPoolExecutor
            .scheduleAtFixedRate(() -> delayQueue.offer("task-" + System.currentTimeMillis(), 10000), 0, 100,
                TimeUnit.MILLISECONDS);
    }
}
