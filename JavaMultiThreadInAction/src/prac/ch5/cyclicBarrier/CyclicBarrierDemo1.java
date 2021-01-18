package prac.ch5.cyclicBarrier;

import io.github.viscent.mtia.util.Tools;
import org.omg.PortableInterceptor.ObjectReferenceFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo1 {


    static class MySQLWriteThread extends Thread {
        private CyclicBarrier cyclicBarrier;
        private int round = 0;

        public MySQLWriteThread(String name, CyclicBarrier cyclicBarrier) {
            super("[t-mysql]" + name);
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(Tools.getCurrentThreadName() + "\twrite to oracle start.");
                    cyclicBarrier.await();
                    System.out.println(Tools.getCurrentThreadName() + "\twrite to oracle end.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class OracleWriteThread extends Thread {
        private CyclicBarrier cyclicBarrier;
        private int round = 0;

        public OracleWriteThread(String name, CyclicBarrier cyclicBarrier) {
            super("[t-oracle]" + name);
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(Tools.getCurrentThreadName() + "\twrite to oracle start.");
                    Tools.randomPause(2000);
                    cyclicBarrier.await();
                    System.out.println(Tools.getCurrentThreadName() + "\twrite to oracle end.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    this.round++;
                }
            }

        }
    }


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println(Tools.getCurrentThreadName() + "\treport write end");
        });
        //批次生成写入任务
        //每个任务写入到数据库A，数据库B,数据库C,写完后,再进行下轮次次任务
        int taskRound = 2;
        OracleWriteThread t1 = new OracleWriteThread("t1", cyclicBarrier);
        MySQLWriteThread t2 = new MySQLWriteThread("t2", cyclicBarrier);
        t1.start();
        t2.start();
        while (taskRound-- > 0) {
            try {
                cyclicBarrier.await();
//                System.out.println("reset");
//                cyclicBarrier.reset();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

    }
}
