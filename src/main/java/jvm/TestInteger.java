package jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wang Hao
 * @date 2021/4/8 16:37
 */
@Slf4j
public class TestInteger {

    public static void main(String[] args) {
//        Integer a = 128;
//        Integer b = 128;
//        System.out.println(a == b);
//        System.out.println(a == 128);
//        Integer a1 = 127;
//        Integer b1 = 127;
//        System.out.println(a1 == b1);
//        System.out.println(a1 == 127);
        byte i = 1;
        i++;
        i += 1;
        i = (byte) (i + 1);
        int j = 1;
        j++;
        j += 10;
        j = j+10;
    }
}
