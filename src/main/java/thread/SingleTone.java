package thread;

import java.io.Serializable;

/**
 * @author Wang Hao
 * @date 2021/4/8 14:07
 */
public final class SingleTone implements Serializable {

    private SingleTone(){
    }

    private static volatile SingleTone INSTANCE = null;

    public static SingleTone getInstance(){
        if (INSTANCE==null){
            synchronized (SingleTone.class){
                if (INSTANCE==null){
                    INSTANCE = new SingleTone();
                }
            }
        }
        return INSTANCE;
    }

    public Object readResolve(){
        return INSTANCE;
    }
}
