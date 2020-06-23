package prac.non_visibility_by_atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class VisibilityByAtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        new WorkThread().start();
        Thread.sleep(1000);
        new CancelThread().start();
    }
}


class WorkThread extends Thread {

    @Override
    public void run() {
        System.out.println("WorkThread start");
        while (true) {
            if (ShareData.flag.get() == 1) {
                break;
            }
        }
        System.out.println("WorkThread end");
    }
}

class ShareData {
    public static AtomicInteger flag = new AtomicInteger(-1);
}


class CancelThread extends Thread {
    @Override
    public void run() {
        ShareData.flag.set(1);
        System.out.println("CancelThread set flag=" + ShareData.flag);
    }
}
