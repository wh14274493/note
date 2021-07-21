package jvm;

/**
 * @author Wang Hao
 * @date 2021/4/8 16:46
 */
public class TestLong {

    public static void main(String[] args) {
        Long a = 128L;
        Long b = 128L;
        System.out.println(a==b);
        System.out.println(a==128);
        System.out.println(a==128L);
        Long a1 = 127L;
        Long b1 = 127L;
        System.out.println(a1==b1);
        System.out.println(a1==127);
        System.out.println(a1==127L);
    }
}
