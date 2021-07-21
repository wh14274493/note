package redis;

import java.util.UUID;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Wang Hao
 * @date 2021/5/3 16:05
 */
@Slf4j
public class RedisLock {

    public static void main(String[] args) throws InterruptedException {
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        IntStream.range(0, 10).forEach(index -> new Thread(() -> {
            Jedis jedis = new Jedis("192.168.31.70", 6379);
            log.info(Thread.currentThread().getName()+" start");
            while (jedis.setnx(key, value) > 0) {
                log.info(Thread.currentThread().getName()+" lock");
                break;
            }
            log.info(Thread.currentThread().getName()+" unlock");
            jedis.del(key);
        }, "t" + index).start());
    }
}
