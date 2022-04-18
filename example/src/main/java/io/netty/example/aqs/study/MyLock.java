package io.netty.example.aqs.study;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {
    private  LockSync lockSync;

    public MyLock(){
        lockSync = new LockSync();
    }


    @Override
    public synchronized void  lock() {

      Thread thread = lockSync.getFirstQueuedThread();

        if(Thread.currentThread().equals(thread)){

        }


    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        lockSync.release(0);

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    static class LockSync extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, arg)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected  boolean tryRelease(int arg){
            setExclusiveOwnerThread(Thread.currentThread());
            setState(0);
            return true;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

}
