package thread;

/**
 * @author Wang Hao
 * @date 2021/3/24 22:21
 */
public class SynchronizedAndVolatile {

    private static  int a;

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(a);
        while (true){

        }
    }

    public synchronized int A(){
        int a = 0;
        long b = 0;
        Long c = b;
        return 0;
    }

    Object o = new Object();
    public  void B(){
        a++;
        synchronized (o){

        }
    }
}
