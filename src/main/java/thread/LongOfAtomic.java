package thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * @author Wang Hao
 * @date 2021/3/24 14:48
 */
public class LongOfAtomic {

    private static long l=1L;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            IntStream.range(0, 1000000).forEach(value -> l = 1);
        });
        Thread t2 = new Thread(() -> {
            IntStream.range(0, 1000000).forEach(new IntConsumer() {
                @Override
                public void accept(int value) {
                    l = -1;
                }
            });
        });
        Thread t3 = new Thread(() -> {
            IntStream.range(0, 100000).forEach(new IntConsumer() {
                @Override
                public void accept(int value) {
                        System.out.println(l);
                }
            });
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
