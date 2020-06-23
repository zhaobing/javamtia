package prac.non_visibility_by_volatile;

public class VisibilityByVolatileDemo {
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
            if (ShareData.flag == 1) {
                break;
            }
        }
        System.out.println("WorkThread end");
    }
}

class ShareData {
    public static volatile  int flag = -1;
}


class CancelThread extends Thread {
    @Override
    public void run() {
        ShareData.flag = 1;
        System.out.println("CancelThread set flag=" + ShareData.flag);
    }
}
