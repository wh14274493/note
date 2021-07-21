package thread;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wang Hao
 * @date 2021/4/6 20:11
 */
@Slf4j
public class TestWaitAndNotify {

    public static void main(String[] args) {
        Task task = new Task(1);
        new Thread(()->{
            task.run(1,2,"a");
        }).start();
        new Thread(()->{
            task.run(2,3,"b");
        }).start();
        new Thread(()->{
            task.run(3,1,"c");
        }).start();
    }

}

@Slf4j
class Task{

    private Object lock = new Object();
    private int waitCondition;

    public Task(int waitCondition){
        this.waitCondition = waitCondition;
    }

    public void run(int condition,int next,String content) {
        IntStream.range(0,10).forEach((index)->{
            synchronized (lock){
                try {
                    while (waitCondition!=condition){
                        lock.wait();
                    }
                    log.info(content);
                    waitCondition = next;
                    lock.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
