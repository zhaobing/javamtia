package prac.ch6.referece;

import com.alibaba.fastjson.JSON;

import java.util.WeakHashMap;

/**
 * 输出:
 * GC前:{128:1280}
 * GC后:{}
 */
public class WeakHashMapDemo1 {

    public static void main(String[] args) {
        WeakHashMap<Integer, Integer> weakHashMap = new WeakHashMap<>();
        Integer k1 = 128;
        Integer v1 = 1280;
        weakHashMap.put(k1, v1);
        System.out.println("GC前:"+JSON.toJSONString(weakHashMap));

        k1 = null;
        System.gc();
        System.out.println("GC后:"+JSON.toJSONString(weakHashMap));
    }


}
