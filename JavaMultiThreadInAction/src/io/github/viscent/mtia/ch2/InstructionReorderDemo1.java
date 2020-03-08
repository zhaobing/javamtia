package io.github.viscent.mtia.ch2;

import com.alibaba.fastjson.JSON;
import lombok.Data;

public class InstructionReorderDemo1 {
    int value = 0;
    boolean startFlag = false;

    public void startSystem() {
        value = 1;//关注点A
        startFlag = true;//关注点B
    }

    public Pair checkSystemState() {
        if (startFlag) {//关注点C -如果系统处于started状态，则value=1
            int changedValue = value;
            return new Pair(changedValue, startFlag);
        } else {//关注点D -如果系统处于非started状态，则value=-1
            int changedValue = value - 1;
            return new Pair(changedValue, startFlag);
        }
    }

    @Data
    static class Pair {
        int value;
        boolean flag;

        //错误情况 {"flag":true,"value":-1}
        //        {"flag":true,"value":0}
        public void checkError() {
            if (value == 1 && flag) {
            } else if (value == -1 && !flag) {
            } else {
                System.out.println(this.toString());
            }
        }


        public Pair(int value, boolean flag) {
            this.value = value;
            this.flag = flag;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static void main(String[] args) {
        int i = 20000;
        while (i-- > 0) {
            InstructionReorderDemo1 demo = new InstructionReorderDemo1();
            new Thread(() -> demo.startSystem()).start();

            new Thread(() -> {
                Pair pair = demo.checkSystemState();
                pair.checkError();
            }).start();

        }
    }

}


