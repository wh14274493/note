package collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wang Hao
 * @date 2021/4/11 18:04
 */
public class TestConcurrentHashMap {

    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("123","qe");
    }
}
