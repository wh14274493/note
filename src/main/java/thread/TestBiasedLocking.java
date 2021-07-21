package thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author Wang Hao
 * @date 2021/4/6 17:08
 */
@Slf4j
public class TestBiasedLocking {


    /**
     * 测试启动时偏向锁是否延时 偏向锁在启动后默认会有大约4s左右的延时才会生效 可以通过参数-XX:BiasedLockingStartupDelay=0来禁用延迟
     */
    public static void testBiasedLockingStartupDelay() throws InterruptedException {
        log.info(ClassLayout.parseInstance(new Object()).toPrintable());
        TimeUnit.SECONDS.sleep(4);
        log.info(ClassLayout.parseInstance(new Object()).toPrintable());
    }

    /**
     * 测试hashcode对偏向锁的影响 调用hashcode方法会导致对象头中markword无法存放线程id，因此会取消偏向锁（101）的使用，变成normal状态（001）
     */
    public static void testHashCode() {
        Object o = new Object();
        log.info(ClassLayout.parseInstance(o).toPrintable());
        o.hashCode();
        log.info(ClassLayout.parseInstance(o).toPrintable());
    }

    /**
     * 测试锁竞争对偏向锁的影响 结论： 锁竞争有两种情况: 1. t1执行完之后t2开始抢锁，这时会撤销偏向锁，并转化为轻量级锁 2. t1执行时t2开始抢锁，这是会撤销偏向锁，并转化为重量级锁
     */
    public static void testRace() {
        Object o = new Object();
        Thread t1 = new Thread(() -> {
            log.info(ClassLayout.parseInstance(o).toPrintable());
            synchronized (o) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(ClassLayout.parseInstance(o).toPrintable());
            }
            log.info(ClassLayout.parseInstance(o).toPrintable());
        });
        t1.start();
        Thread t2 = new Thread(() -> {

            log.info(ClassLayout.parseInstance(o).toPrintable());
            synchronized (o) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(ClassLayout.parseInstance(o).toPrintable());
            }
            log.info(ClassLayout.parseInstance(o).toPrintable());
        });
        t2.start();
    }


    static Thread t1;
    static Thread t2;
    static Thread t3;

    /**
     * 测试批量撤销和批量重偏向 当同一个类有连续超过19个对象被撤销了偏向锁升级轻量级锁时，那么之后所有的锁对象都可以进行重新偏向 当超过40次以后，JVM会认为这个类的对象不适合偏向锁，那么接下来该类的所有对象都不再支持偏向锁，即使时新创建的对象
     */
    public static void testBulkRevokeAndBulkRebias() throws InterruptedException {
        List<Object> list = new CopyOnWriteArrayList<>();
        log.info(markword(new Object()));
        log.info(markword(Object.class));
        int loop = 65;
        t1 = new Thread(() -> {
            IntStream.range(0, loop).forEach((n) -> {
                Object o = new Object();
                list.add(o);
                log.info(n+"\t"+markword(o));
                synchronized (o) {
                    log.info(n+"\t"+markword(o));
                }
                log.info(n+"\t"+markword(o));
                log.info("");
            });
            LockSupport.unpark(t2);
        });
        t1.start();
        t2 = new Thread(() -> {
            LockSupport.park();
            IntStream.range(0, loop).forEach((n) -> {
                Object o = list.get(n);
                log.info(n+"\t"+markword(o));
                synchronized (o) {
                    if (n==17||n==18){
                        log.info(n+"\t\t"+markword(new Object()));
                    }
                    log.info(n+"\t"+markword(o));
                }
                log.info(n+"\t"+markword(o));
                log.info("");
            });
            LockSupport.unpark(t3);
        });
        t2.start();
        t3 = new Thread(() -> {
            LockSupport.park();
            IntStream.range(0, loop).forEach((n) -> {
                Object o = list.get(n);
                log.info(n+"\t"+markword(o));
                synchronized (o) {
                    log.info(n+"\t"+markword(o));
                }
                log.info(n+"\t"+markword(o));
                log.info("");
            });

        });
        t3.start();
        t3.join();
        log.info(markword(new Object()));
        log.info(markword(Object.class));
    }


    public static String markword(Object o) {
        String s2 = ClassLayout.parseInstance(o).toPrintable();
        return s2.substring(s2.indexOf("0x"), s2.indexOf("0x") + 19);
    }

    public static void main(String[] args) throws InterruptedException {
//        testBiasedLockingStartupDelay();
//        testHashCode();
//        testRace();
        testBulkRevokeAndBulkRebias();
    }
}
