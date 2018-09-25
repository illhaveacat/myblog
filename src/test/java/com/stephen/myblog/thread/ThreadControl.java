package com.stephen.myblog.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadControl {


    public static void main(String args[]) throws InterruptedException {

        while (true) {
            if (TestTread.threadCount.get() < 10) {
                new TestTread().start();
            } else {
                System.out.println("有10个线程正在执行");
                TimeUnit.SECONDS.sleep(5);
            }

        }
    }


}


class TestTread extends Thread {

    public static AtomicInteger threadCount = new AtomicInteger(0);

    public int getThreadCount() {
        return threadCount.get();
    }

    TestTread() {
        threadCount.incrementAndGet();
    }

    @Override
    public void run() {
        System.out.println(currentThread().getName() + " is running ");

        try {
            sleep(10000);
            threadCount.getAndDecrement();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}