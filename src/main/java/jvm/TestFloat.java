package jvm;

/**
 * @author Wang Hao
 * @date 2021/4/8 16:52
 */
public class TestFloat {

    public static void main(String[] args) {
        Float a = 128f;
        Float b = 128f;
        System.out.println(a==b);
        System.out.println(a==128);
        System.out.println(a==128.0);
        System.out.println(a==128L);
        System.out.println(a==128f);
    }
}
