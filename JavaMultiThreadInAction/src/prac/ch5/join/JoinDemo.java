package prac.ch5.join;

import io.github.viscent.mtia.util.Tools;

public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("main run");
        Thread t1 = new Thread(() -> {
            System.out.println("t1 sleeping");
            Tools.sleepSeconds(3);
            System.out.println("t1 wakeup");
        });
        t1.start();
        t1.join();
        System.out.println("main end");
    }
}
