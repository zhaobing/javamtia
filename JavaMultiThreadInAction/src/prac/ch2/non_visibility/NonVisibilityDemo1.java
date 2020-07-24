package prac.ch2.non_visibility;

public class NonVisibilityDemo1 {
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
    public static int flag = -1;
}


class CancelThread extends Thread {
    @Override
    public void run() {
        ShareData.flag = 1;
        System.out.println("CancelThread setFlag flag=" + ShareData.flag);
    }
}
