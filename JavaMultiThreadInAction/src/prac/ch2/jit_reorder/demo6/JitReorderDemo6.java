package prac.ch2.jit_reorder.demo6;

public class JitReorderDemo6 {
    public static void main(String[] args) {

        int round = 2000000;
        while (round-- > 0) {

            Stat stat = new Stat();

            new Thread(() -> {
                stat.a1 = 1;
                stat.b2 = 2;
                stat.c3 = 3;
                stat.d4 = 4;
                stat.e5 = 5;
                stat.f6 = 6;
            }).start();


            new Thread(() -> {
                int a = stat.a1;
                int b = stat.b2;
                int c = stat.c3;
                int d = stat.d4;
                int e = stat.e5;
                int f = stat.f6;

                if (a == 0 && b == 2) {
                    System.out.println("wtf a1=0,b2=2");
                }

                if (a == 0 && c == 3) {
                    System.out.println("wtf a1=0,c3=3");
                }

                if (b == 0 && c == 3) {
                    System.out.println("wtf b2=0,c3=3");
                }

                if (a == 0 && d == 4) {
                    System.out.println("wtf a1=0,d4=4");
                }

                if (a == 0 && e == 5) {
                    System.out.println("wtf a1=0,e5=5");
                }
                if (a == 0 && f == 6) {
                    System.out.println("wtf a1=0,f6=6");
                }

            }).start();
        }
        System.out.println("done");
    }


    static class Stat {
        int a1 = 0;
        int b2 = 0;
        int c3 = 0;
        int d4 = 0;
        int e5 = 0;
        int f6 = 0;
    }
}
