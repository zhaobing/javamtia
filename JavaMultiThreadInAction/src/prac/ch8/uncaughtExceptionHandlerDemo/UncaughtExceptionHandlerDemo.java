package prac.ch8.uncaughtExceptionHandlerDemo;

import io.github.viscent.mtia.util.Tools;

public class UncaughtExceptionHandlerDemo {
    public static void main(String[] args) {
        System.out.println("start");
       new Thread(() -> {
         throw  new RuntimeException("TTTT");
       }).start();

        Tools.sleepSeconds(3);
    }
}
