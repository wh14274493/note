package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Wang Hao
 * @date 2021/3/23 20:33
 */
public class BlockingList {

    int[] arr;
    int count;
    int putIndex;
    int getIndex;
    ReentrantLock lock = new ReentrantLock();
    Condition putCondition = lock.newCondition();
    Condition getCondition = lock.newCondition();

    public BlockingList(int size) {
        arr = new int[size];
    }

    public void put(int num) throws InterruptedException {
        lock.lock();
        try {
            while (count == arr.length) {
                putCondition.await();
            }
            count++;
            arr[putIndex++] = num;
            if (putIndex == arr.length) {
                putIndex = 0;
            }
            getCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Integer get() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                getCondition.await();
            }
            count--;
            Integer num = arr[getIndex++];
            if (getIndex == arr.length) {
                getIndex = 0;
            }
            putCondition.signal();
            System.out.println(num);
            return num;
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        BlockingList blockingList = new BlockingList(10);
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (i < 30) {
                try {
                    blockingList.put(i++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            int i = 0;
            while (i < 15) {
                try {
                    blockingList.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        });
        Thread t3 = new Thread(() -> {
            int i = 0;
            while (i < 15) {
                try {
                    blockingList.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }

}
