package prac.ch2.non_visibility_by_synchronized;

public class VisibilityBySynchronizedDemo {
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
            if (ShareData.getFlag() == 1) {
                break;
            }
        }
        System.out.println("WorkThread end");
    }
}

class ShareData {
    private static  int flag = -1;

    public static synchronized int getFlag() {
        return flag;
    }

    public static synchronized void setFlag(int value) {
        flag = value;
    }
}


class CancelThread extends Thread {
    @Override
    public void run() {
        ShareData.setFlag(1);
        System.out.println("CancelThread setFlag flag=" + ShareData.getFlag());
    }
}
