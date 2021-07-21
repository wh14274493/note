package redis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author Wang Hao
 * @date 2021/5/4 17:12
 */
@Slf4j
public class SimpleRateLimiter {

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean pass(String key, int count, long interval) {
        Pipeline pipeline = jedis.pipelined();
        long v = System.nanoTime();
        interval *= 1000000000;
        pipeline.multi();
        pipeline.zadd(key, v, String.valueOf(v));
        pipeline.zremrangeByScore(key, 0, v - interval);
        Response<Long> response = pipeline.zcard(key);
        pipeline.expire(key, interval + 1);
        pipeline.exec();
        pipeline.close();
        return response.get() <= count;
    }

    public static void main(String[] args) {
        SimpleRateLimiter simpleRateLimiter = new SimpleRateLimiter(new Jedis("192.168.31.70", 6379));
        String key = UUID.randomUUID().toString();
        IntStream.range(0, 1000).forEach(index -> {
            if (simpleRateLimiter.pass(key, 500, 60)) {
                log.debug("{} passed.", index);
            }
        });
    }
}
