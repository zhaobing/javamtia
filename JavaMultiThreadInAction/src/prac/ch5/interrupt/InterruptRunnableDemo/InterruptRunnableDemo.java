package prac.ch5.interrupt.InterruptRunnableDemo;

import io.github.viscent.mtia.util.Tools;

public class InterruptRunnableDemo extends Thread{
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            System.out.println("still running....");
        }
        System.out.println("OMG you are end");
    }


    public static void main(String[] args) throws InterruptedException {
        InterruptRunnableDemo t1 = new InterruptRunnableDemo();
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
    }
}
