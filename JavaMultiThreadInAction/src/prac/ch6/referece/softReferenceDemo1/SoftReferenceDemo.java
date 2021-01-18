package prac.ch6.referece.softReferenceDemo1;

import java.lang.ref.SoftReference;

/*
JVM参数
-Xmx330m -Xms330m
输出:
GC前[B@61064425
分配空间,制造内存不足，并启动GC
GC后null
 */
public class SoftReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        //100M的缓存数据
        byte[] cacheData = new byte[100 * 1024 * 1024];
        //将缓存数据用软引用持有
        SoftReference<byte[]> cacheRef = new SoftReference<>(cacheData);
        System.out.println("GC前" + cacheRef.get());

        cacheData=null;
        System.out.println("分配空间,制造内存不足，并启动GC");
        byte[] newData = new byte[120 * 1024 * 1024];
        System.gc();
        Thread.sleep(2000);
        //再分配一个120M的对象，看看缓存对象的回收情况
        //等待GC
        System.out.println("GC后" + cacheRef.get());

    }
}
