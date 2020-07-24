package prac.ch2.jit_reorder.demo4;

/**
 * 在JDK1.8环境下仍然无法模拟jit reordering
 * 是否在JDK1.7环境能够模拟出来?
 */
public class JitReorderDemo4 {
    private static boolean stop = false;
    private static volatile int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            i = 0;
            while (!stop) {
                i++;
            }
        });

        thread.start();
        Thread.sleep(1000);
        stop = true;
        thread.join();
    }
}
