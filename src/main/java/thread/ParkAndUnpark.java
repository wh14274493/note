package thread;


import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wang Hao
 * @date 2021/4/2 18:01
 */
public class ParkAndUnpark {

    static Thread A;
    static Thread B;
    static Thread C;
    public static void main(String[] args) {
        Task task = new Task();
        A = new Thread(()->{
            task.run(B,"A");
        });
        B = new Thread(()->{
            task.run(C,"B");
        });
        C = new Thread(()->{
            task.run(A,"C");
        });
        A.start();
        B.start();
        C.start();
        LockSupport.unpark(A);
    }

    @Slf4j
    static class Task{

        public void run(Thread unpark,String content){
            IntStream.range(0,10).forEach((index)->{
                LockSupport.park();
                log.info(content);
                LockSupport.unpark(unpark);
            });
        }
    }
}
