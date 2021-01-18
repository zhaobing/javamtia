/*
授权声明：
本源码系《Java多线程编程实战指南（核心篇）》一书（ISBN：978-7-121-31065-2，以下称之为“原书”）的配套源码，
欲了解本代码的更多细节，请参考原书。
本代码仅为原书的配套说明之用，并不附带任何承诺（如质量保证和收益）。
以任何形式将本代码之部分或者全部用于营利性用途需经版权人书面同意。
将本代码之部分或者全部用于非营利性用途需要在代码中保留本声明。
任何对本代码的修改需在代码中以注释的形式注明修改人、修改时间以及修改内容。
本代码可以从以下网址下载：
https://github.com/Viscent/javamtia
http://www.broadview.com.cn/31065
*/
package io.github.viscent.mtia.ch6;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该类可能导致内存泄漏！
 *
 * @author Viscent Huang
 */
@WebServlet("/memoryLeak")
public class ThreadLocalMemoryLeak extends HttpServlet {
    public ThreadLocalMemoryLeak() {
        System.out.println("构造ThreadLocalMemoryLeak");
    }

    private static final long serialVersionUID = 4364376277297114653L;

    //    final static ThreadLocal<Counter> counterHolder = ThreadLocal.withInitial(() -> {
    //        Counter tsoCounter = new Counter();
    //        return tsoCounter;
    //    });

    final static ThreadLocal<Counter> counterHolder = new ThreadLocal<Counter>() {
        @Override
        protected Counter initialValue() {
            System.out.println("counterHolder initialValue");
            Counter tsoCounter = new Counter();
            return tsoCounter;
        }
    };


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("调用Get  doGet");

        Counter counter = counterHolder.get();
        System.out.format("Thread %s,counter:%d\n",
                Thread.currentThread().getName(),
                counter.getAndIncrement());

        System.out.println("===========");
        doProcess(req, resp);

    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        System.out.println("调用Get  doProcess");
        Counter counter = counterHolder.get();
        counter.getAndIncrement();
        // 省略其他代码
    }
}

// 非线程安全
class Counter {
    private int i = 0;

    public int getAndIncrement() {
        return i++;
    }
}