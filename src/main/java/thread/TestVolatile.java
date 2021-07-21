package thread;

import java.io.Serializable;

/**
 * @author Wang Hao
 * @date 2021/4/8 13:24
 */
public class TestVolatile implements Serializable {


    static volatile int a;
    static volatile Object o;

    public void test(){
        a++;
        o = new Object();
        if (o==null){

        }
    }

}
