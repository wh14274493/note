package thread;


/**
 * @author Wang Hao
 * @date 2021/3/21 23:00
 */
public class Test20210321 {

    public static void main(String[] args) {
        Object o = new Object();
        final int[] i = {0};
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    i[0]++;
                    i[0]++;
                    i[0]++;
                    System.out.println("t1"+i[0]);
                    synchronized (o) {
                        i[0]++;
                        System.out.println("t1"+i[0]);
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    i[0]++;
                    i[0]++;
                    i[0]++;
                    System.out.println("t2"+i[0]);
                    synchronized (o) {
                        i[0]++;
                        System.out.println("t2"+i[0]);
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
