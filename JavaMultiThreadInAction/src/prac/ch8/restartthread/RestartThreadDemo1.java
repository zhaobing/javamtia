package prac.ch8.restartthread;


import io.github.viscent.mtia.util.Debug;
import io.github.viscent.mtia.util.Tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 演示线程捕获异常并进行线程重启
 * 生产者线程生产
 * 消费者线程模拟消费，消费中出现异常，对消费者线程进行重启
 * 线程异常捕获处理对象来重启消费者线程
 */
public class RestartThreadDemo1 {

    final static Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) throws InterruptedException {

        ProducerThread pt = new ProducerThread(100);
        pt.start();
        restartConsumerThread();
        Thread.sleep(2000);
        System.exit(0);

    }

    public static synchronized void restartConsumerThread() {
        if (State.workerThreadStartFlag) {
            return;
        }
        ConsumerThread t = new ConsumerThread("Consumer0-" + State.workerThreadIdx++);
        t.setUncaughtExceptionHandler(new ThreadMonitor());
        t.start();
        State.workerThreadStartFlag = true;
    }


    static class ThreadMonitor implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {

            Debug.info("Current Thread is t: %s,it is still alive:%s"
                    , Thread.currentThread() == t, t.isAlive());
            String threadInfo = t.getName();
            LOGGER.log(Level.SEVERE, threadInfo + "\tterminated:", e);
            //创建并启动替代线程
            LOGGER.info("About to restart" + threadInfo);
            State.workerThreadStartFlag = false;
            restartConsumerThread();
        }
    }

    static class ConsumerThread extends Thread {
        public ConsumerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Debug.info("start consuming...");
            try {
                while (true) {
                    String msg = RestartThreadDemo1.State.channel.take();
                    service(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void service(String msg) {
            String name = Thread.currentThread().getName();
            Debug.info(name + "\tproc\t" + msg);
            //模拟随机异常
            int randomNum = (int) (Math.random() * 100);
            if (randomNum < 5) {
                throw new RuntimeException("OMG-RandomException");
            }
            Tools.randomPause(100);
        }
    }

    static class ProducerThread extends Thread {
        private int taskNum = 0;

        public ProducerThread(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public void run() {
            while (this.taskNum-- > 0) {
                try {
                    RestartThreadDemo1.State.channel.put("task-" + taskNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    enum State {
        INS;
        static volatile boolean workerThreadStartFlag = false;
        static volatile int workerThreadIdx = 0;
        static final BlockingQueue<String> channel = new ArrayBlockingQueue<String>(100);

    }
}
