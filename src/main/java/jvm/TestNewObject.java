package jvm;

/**
 * @author Wang Hao
 * @date 2021/4/24 14:30
 */
public class TestNewObject {
    {
        a = 5;
    }

    private int a = 10;

    {
        a = 15;
    }

    public TestNewObject(){
        a = 20;
    }

    public static void main(String[] args) {
        TestNewObject o = new TestNewObject();
    }
}
