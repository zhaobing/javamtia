package prac.ch2.jit_reorder.demo3;

public class JitReorderDemo3 {
    private static boolean stop = false;
    private static volatile int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
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
