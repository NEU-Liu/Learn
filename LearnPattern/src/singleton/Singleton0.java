package singleton;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 86175
 */
public class Singleton0 {
    private static Map<String, Singleton0> map = new HashMap<>();


    static long TimeInterval = TimeUnit.SECONDS.toSeconds(3);

    static Map<String,Long> hashMap = new HashMap<>();

    private static void check(String identity){
        Long now = System.currentTimeMillis();
        for (Iterator<Map.Entry<String, Long>> iterator = hashMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String, Long> item = iterator.next();
            Long timeStamp = item.getValue();
            if (TimeUnit.SECONDS.toSeconds(now - timeStamp) >= TimeInterval){
                iterator.remove();
            }
        }
        boolean result = hashMap.isEmpty();
        if (result){
            hashMap.put(identity,now);
            //todo feedback
        }
    }

    private Singleton0() {
        System.out.println("111111111");
    }

    public static synchronized Singleton0 getInstance(String identity) {
        if (!map.containsKey(identity)) {
            Singleton0 singleton = new Singleton0();
            map.put(identity, singleton);
        }
        return map.get(identity);
    }


}