package io.netty.example.aqs.study;

import java.util.concurrent.locks.ReentrantLock;

/**
 * aqs学习 -- 自定义锁的实现
 */
public class AqsStudy {




    public static void main(String[] args) {

        MyLock myLock = new MyLock();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100L);

                    myLock.lock();
                    System.out.println(Thread.currentThread().getName()+" myLock! ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        myLock.lock();
        System.out.println("main myLock!");

        try {
            Thread.sleep(5000L);

            myLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
