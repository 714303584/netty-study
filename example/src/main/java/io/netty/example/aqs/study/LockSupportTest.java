package io.netty.example.aqs.study;


import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 源码学习
 */
public class LockSupportTest {

    public static void main(String[] args) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LockSupport.park();
                    Thread.sleep(100L);


                    System.out.println(Thread.currentThread().getName() + " myLock! ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

        System.out.println("main myLock!");

        try {
            Thread.sleep(5000L);
            LockSupport.unpark(thread);


            Thread.sleep(5000L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
