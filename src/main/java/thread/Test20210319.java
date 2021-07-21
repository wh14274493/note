package thread;


/**
 * @author Wang Hao
 * @date 2021/3/19 20:44
 */
public class Test20210319 {

    int count;

    public static void main(String[] args) {
        Test20210319 obj = new Test20210319();
        Thread t1 = new Thread(() -> {
            int index = 5;

            while (index > 0) {
                synchronized (obj) {
                    while (obj.count != 0) {
                        try {
                            System.out.println("t1 wait");
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    obj.count++;
                    index--;
                    System.out.println(" t1=" + obj.count);
                    obj.notify();
                }
//                obj.notify();
            }
        });
        Thread t2 = new Thread(() -> {
            int index = 5;

            while (index > 0) {
                synchronized (obj) {
                    while (obj.count == 0) {
                        try {
                            System.out.println("t2 wait");
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    obj.count--;
                    index--;
                    System.out.println(" t2=" + obj.count);
                    obj.notify();
                }
//                obj.notify();
            }
        });
        Thread t3 = new Thread(() -> {
            int index = 5;

            while (index > 0) {
                synchronized (obj) {
                    while (obj.count != 0) {
                        try {
                            System.out.println("t3 wait");
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    obj.count++;
                    index--;
                    System.out.println(" t3=" + obj.count);
                    obj.notify();
                }
//                obj.notify();
            }
        });
        Thread t4 = new Thread(() -> {
            int index = 5;

            while (index > 0) {
                synchronized (obj) {
                    while (obj.count == 0) {
                        try {
                            System.out.println("t4 wait");
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    obj.count--;
                    index--;
                    System.out.println(" t4=" + obj.count);
                    obj.notify();
                }
//                obj.notify();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
