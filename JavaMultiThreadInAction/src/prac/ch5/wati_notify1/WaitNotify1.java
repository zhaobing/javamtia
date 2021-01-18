package prac.ch5.wati_notify1;

import io.github.viscent.mtia.util.Tools;

import java.util.logging.Logger;


public class WaitNotify1 {


    private final static Logger LOG = Logger.getAnonymousLogger();
    static Object lock = new Object();
    static boolean flag = false;

    public static void main(String[] args) {
        //        new Thread(new WaitThread(),"WaitThread").start();
        //        Tools.SleepSeconds(1);
        //        new Thread(new NotifyThread(),"NotifyThread").start();



        System.out.println("MAIN Start");
        new Thread(new SleepThread(1),"a").start();
        new Thread(new SleepThread(5),"b").start();
        System.out.println("MAIN End");

    }


    static class SleepThread implements Runnable {
        private long sleepSeconds;

        public SleepThread(long sleepSeconds) {

            this.sleepSeconds = sleepSeconds;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name +" will sleep" + sleepSeconds + " s");
            Tools.sleepSeconds(sleepSeconds);
            System.out.println(name +" End");
        }
    }


    static class WaitThread implements Runnable {

        @Override
        public void run() {

            //acquire lock
            synchronized (lock) {
                while (!flag) {//条件不满足时，则一直等待，等待唤醒线程改变条件，并通知该线程
                    System.out.println(Thread.currentThread() + " is waiting, flag is " + flag);
                    try {
                        //wait()方法会释放锁,当前线程进入等待队列
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }//条件满足

                System.out.println(Thread.currentThread() + " is running, flag is " + flag);
            }
        }
    }


    static class NotifyThread implements Runnable {

        @Override
        public void run() {

            //acquire lock
            synchronized (lock) {
                flag = true;
                //通知wait线程,我已经改变了条件,wait线程可以继续执行了(返回之后继续判断while)
                //notify操作并不会释放锁，需要等待当前线程释放锁
                lock.notifyAll();
                System.out.println(Thread.currentThread() + " hold lock, notify waitThread and flag is " + flag);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " ready to release lock " + flag);
            }
        }
    }


}
