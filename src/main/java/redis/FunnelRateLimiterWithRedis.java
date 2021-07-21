package redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Wang Hao
 * @date 2021/5/4 18:38
 */
@Slf4j
public class FunnelRateLimiterWithRedis {

    Jedis jedis;
    String key;

    public FunnelRateLimiterWithRedis(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

}
