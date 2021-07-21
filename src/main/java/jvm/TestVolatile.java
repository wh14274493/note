package jvm;

/**
 * @author Wang Hao
 * @date 2021/6/7 21:56
 */
public class TestVolatile {

    public static volatile int a = 0;

    public static void main(String[] args) {
        System.out.println(a);
        a = 1;
        System.out.println(a);
    }
}
