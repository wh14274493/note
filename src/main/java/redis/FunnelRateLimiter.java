package redis;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wang Hao
 * @date 2021/5/4 17:48
 */
@Slf4j
public class FunnelRateLimiter {


    final int capacity;
    final double rate;
    long lastRequest;
    int left;

    public FunnelRateLimiter(int capacity, double rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.left = capacity;
        this.lastRequest = System.currentTimeMillis();
    }

    public void computeLeft() {
        long now = System.currentTimeMillis();
        long passed = now - lastRequest;
        double v = rate * passed / 1000;
        if (v < 0) {
            left = capacity;
            lastRequest = now;
            return;
        }
        if (v < 1) {
            return;
        }
        left += v;
        left = Math.min(left, capacity);
        lastRequest = now;
    }

    public boolean request() {
        computeLeft();
        if (left > 0) {
            left -= 1;
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws InterruptedException {
        FunnelRateLimiter rateLimiter = new FunnelRateLimiter(60, 0.5);
        IntStream.range(0, 100).forEach(index -> {
            if (rateLimiter.request()) {
                log.debug("{} passed.", index);
            }
        });
        TimeUnit.MILLISECONDS.sleep(10000L);
        IntStream.range(0, 100).forEach(index -> {
            if (rateLimiter.request()) {
                log.debug("{} passed.", index);
            }
        });
    }
}
