created by zhuss


# AQS 学习

## AQS 详解

 AQS是AbstractQueuedSynchronizer的简称。 AQS提供了一种实现阻塞锁和一系列依赖FIFO(first in first out(先进先出))的同步器的框架。
 AQS为一系列同步器依赖于一个单独原子变量（state）的同步器提供了一个非常有用的基础。
 子类必须定义改变state变量的方法，这些方法定义了state是如何被获取或释放的。
 本类的其他方法执行所有的排队和阻塞机制。子类也可以维护其他的state变量，但为了保证同步，必须原子的操作这些变量。
 
 AQS定义两种资源共享模式
 * Exclusive(独占模式) 只有一个线程能执行。 例如：ReentrantLock
 * Share（共享模式） 多个线程可同时执行。例如:Semaphore,CountDownLatch.

## AQS 核心属性和方法

* state 同步状态 AbstractQueuedSynchronizer对state的修改都是原子的。

        getState
        setState
        compareAndSetState 调用unsafe.compareAndSwapInt保证修改的同步










