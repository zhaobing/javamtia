package prac.ch2.jit_recorde_demo2;

/**
 * 这是一个错误的DEMO,没有能够做到指令重排序
 */
public class JitRecordingDemo2 {
    public static void main(String[] args) {
        int threadNum = 20000000;
        while (threadNum-- > 0) {
            JitRcodeUnit jitRcodeUnit = new JitRcodeUnit();
            new Thread(() -> jitRcodeUnit.initDataUnit()).start();
            new Thread(() -> {
                int i = jitRcodeUnit.countData();
                if (i != 200) {
                    System.out.println("~~~~~~~");
                }
            }).start();
        }
    }
}

class JitRcodeUnit {

    private int initValue = 100;

    private DataUnit dataUnit;

    public void initDataUnit() {
        dataUnit = new DataUnit(initValue);
    }

    public int countData() {
        if (dataUnit == null) {
            return 200;
        } else {
            int sum = dataUnit.elemA + dataUnit.elemB;
            return sum;
        }
    }


}

class DataUnit {
    int elemA;
    int elemB;

    public DataUnit(int initValue) {
        this.elemA = initValue;
        this.elemB = initValue;
    }
}


