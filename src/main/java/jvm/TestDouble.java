package jvm;

/**
 * @author Wang Hao
 * @date 2021/4/8 16:55
 */
public class TestDouble {

    public static void main(String[] args) {
        Double a = 0d;
        Double b = 0d;
        System.out.println(a==b);
        System.out.println(a==0);
        System.out.println(a==0.0);
        System.out.println(a==0L);
        System.out.println(a==0f);
        System.out.println(a==0d);
    }
}
