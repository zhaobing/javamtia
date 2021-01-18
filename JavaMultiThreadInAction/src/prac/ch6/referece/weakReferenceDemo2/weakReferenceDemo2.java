package prac.ch6.referece.weakReferenceDemo2;

import java.lang.ref.WeakReference;

/**
 * 输出:
 * GC前:[B@3764951d
 * 启动GC
 * GC后:null
 */
public class weakReferenceDemo2 {
    public static void main(String[] args) {
        //100M的缓存数据
        byte[] cacheData = new byte[100 * 1024 * 1024];
        WeakReference<byte[]> cacheRef = new WeakReference<>(cacheData);
        System.out.println("GC前:" + cacheRef.get());
        System.out.println("启动GC");
        cacheData = null;
        System.gc();
        System.out.println("GC后:" + cacheRef.get());
    }
}
