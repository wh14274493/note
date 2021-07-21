package jvm;

/**
 * @author Wang Hao
 * @date 2021/4/8 17:57
 */
public class TestFinal {

    static /*final*/ int a = 128;
    static final int f = 1;
    static final Object o = new Object();

    public /*final*/ int b = 129;

    public /*final*/ int c = Integer.MAX_VALUE;
    public /*final*/ int d = Short.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println(a);
        TestFinal testFinal = new TestFinal();
        System.out.println(testFinal.b);
        System.out.println(testFinal.c);
        System.out.println(testFinal.d);
    }
}
