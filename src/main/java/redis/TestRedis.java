package redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Wang Hao
 * @date 2021/6/5 22:11
 */
@Slf4j
public class TestRedis {

//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("192.168.31.76", 6379);
//        log.debug(jedis.ping());
//        StringBuilder value = new StringBuilder();
//        while (value.length() < 256) {
//            value.append(UUID.randomUUID());
//        }
//        String v = value.substring(0, 3);
//        String id = UUID.randomUUID().toString();
//        long begin = System.nanoTime();
//        log.info("start at {}", begin);
//        IntStream.range(0, 50000).forEach(index -> {
//            String set = jedis.get(index + "wanghao11");
//            log.info(set);
//        });
//        log.info("cost {} ns.", System.nanoTime() - begin);
//    }

    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create(new RedisURI("192.168.31.76", 6379, Duration.ofSeconds(60)));
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> sync = connect.sync();
        long begin = System.nanoTime();
        log.info("start at {}", begin);
        IntStream.range(0, 10000).forEach(index -> {
        String result = sync.set("name", "throwable");
        log.info(result);
        });
        log.info("cost {} ns.", System.nanoTime() - begin);
    }
}
