package thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Wang Hao
 * @date 2021/4/5 16:16
 */
public class Interrupt {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("t1 interrupt");
                e.printStackTrace();
            }
        });
        t1.start();
        t1.interrupt();
    }
}
