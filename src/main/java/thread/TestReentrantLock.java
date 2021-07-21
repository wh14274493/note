package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wang Hao
 * @date 2021/4/7 21:36
 */
public class TestReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition A = lock.newCondition();
        Condition B = lock.newCondition();
        Condition C = lock.newCondition();
        Task task = new Task();
        new Thread(()->{
            task.run(lock,A,B,"A");
        }).start();
        new Thread(()->{
            task.run(lock,B,C,"B");
        }).start();
        new Thread(()->{
            task.run(lock,C,A,"C");
        }).start();
        TimeUnit.SECONDS.sleep(2);
        lock.lock();
        try {
            A.signal();
        } finally {
            lock.unlock();
        }
    }

    @Slf4j
    static class Task {

        public void run(Lock lock, Condition waiter, Condition signaler, String content) {
            IntStream.range(0, 10).forEach((index) -> {
                lock.lock();
                try {
                    waiter.await();
                    log.info(content);
                    signaler.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }
    }
}


