package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wang Hao
 * @date 2021/4/7 21:55
 */
@Slf4j
public class MyFuture {

    public static void main(String[] args) throws InterruptedException {
        Future<String> future = execute().addListener(() -> log.info("complete"));
        String s = future.get();
        log.info(s);
        Future<String> future1 = execute().addListener(() -> log.info("complete1"));
        log.info("start getting");
        String s1 = future1.get(1);
        log.info(s1);
    }

    public static Future<String> execute() {
        Future<String> future = new Future<>();
        new Thread(() -> {
            try {
                URLConnection connection = new URL("https://www.baidu.com").openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder s = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    s.append(line).append('\n');
                }
                future.complete(s.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return future;
    }

    static class Future<T> {

        T res;
        List<Listener> listeners;

        public T get() throws InterruptedException {
            synchronized (this) {
                while (res == null) {
                    this.wait();
                }
            }
            return res;
        }

        public T get(long timeout) throws InterruptedException {
            long begin = System.currentTimeMillis();
            long passed;
            synchronized (this) {
                while (res == null) {
                    passed = System.currentTimeMillis() - begin;
                    if (passed >= timeout) {
                        break;
                    }
                    this.wait(timeout - passed);
                }
            }
            return res;
        }

        public void complete(T res) {
            synchronized (this) {
                this.res = res;
                this.notifyAll();
            }
            if (listeners != null) {
                for (Listener listener : listeners) {
                    listener.doComplete();
                }
            }
        }

        public Future<T> addListener(Listener listener) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            listeners.add(listener);
            return this;
        }
    }

    @FunctionalInterface
    interface Listener {

        void doComplete();
    }
}
