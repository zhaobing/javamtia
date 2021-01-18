package prac.ch6.ThreadLocalMemoryLeakDemo1;

import io.github.viscent.mtia.ch6.ThreadLocalMemoryLeak;
import io.github.viscent.mtia.util.Tools;

import javax.servlet.ServletException;
import java.io.IOException;

public class ThreadLocalMemoryLeakDemoClient {
    public static void main(String[] args) throws ServletException, IOException {

        ThreadLocalMemoryLeak tml= new ThreadLocalMemoryLeak();
        tml.doGet(null,null);

    }
    public static void t1() throws ServletException, IOException {
        ThreadLocalMemoryLeak tml= new ThreadLocalMemoryLeak();
        tml.doGet(null,null);
        int i =500;
        while (i-->0){
            new Thread(() -> {
                try {
                    tml.doGet(null,null);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        System.out.println("main done");
    }
}
