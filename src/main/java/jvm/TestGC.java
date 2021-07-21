package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xmx20m -Xms20m -Xmn10m -XX:+PrintGCDetails -verbose:gc
 * @author Wang Hao
 * @date 2021/4/17 17:39
 */
public class TestGC {

    public static final int _8Mb = 8 * 1024 * 1024;
    public static final int _5Mb = 5 * 1024 * 1024;


    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            List<byte[]> list = new ArrayList<>();
            list.add(new byte[_8Mb]);
            list.add(new byte[_5Mb]);
            list.add(new byte[_5Mb]);
        }).start();
        Thread.sleep(2000);
    }
}
