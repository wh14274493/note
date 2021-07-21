package jvm;

import java.nio.ByteBuffer;

/**
 * @author Wang Hao
 * @date 2021/5/6 14:35
 */
public class TestDirectByteBuffer {

    public static void main(String[] args) throws InterruptedException {
        int size = 1024 * 1024 * 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size);
        int i = 0;
        while (i < size) {
            byteBuffer.put((byte) 1);
            i++;
        }
        Thread.sleep(100000);
    }
}
