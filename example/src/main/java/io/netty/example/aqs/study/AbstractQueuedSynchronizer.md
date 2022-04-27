created by zhuss


# AQS（队列同步器） 学习

## AQS 详解

 AQS是AbstractQueuedSynchronizer的简称。 AQS提供了一种实现阻塞锁和一系列依赖FIFO(first in first out(先进先出))的同步器的框架。
 AQS为一系列同步器依赖于一个单独原子变量（state）的同步器提供了一个非常有用的基础。
 子类必须定义改变state变量的方法，这些方法定义了state是如何被获取或释放的。
 本类的其他方法执行所有的排队和阻塞机制。子类也可以维护其他的state变量，但为了保证同步，必须原子的操作这些变量。
 
 AQS定义两种资源共享模式
 * Exclusive(独占模式) 只有一个线程能执行。 例如：ReentrantLock
 * Share（共享模式） 多个线程可同时执行。例如:Semaphore,CountDownLatch.

## AQS 核心属性

* state 同步状态 AbstractQueuedSynchronizer对state的修改都是原子的。当state为0时线程未获取到锁,当state大于0时说明有线程获取到锁

        getState
        setState
        compareAndSetState 调用unsafe.compareAndSwapInt保证修改的同步 涉及知识CAS（compare and set (比较并交换)）
        
* Node AQS内部维护了一个线程队列。队列的节点为Node，节点状态为shared（共享模式）和Exclusive(独占模式)两种。
    分别保存head（头）和tail（尾）两个节点。
        
        节点的等待状态为waitStatus
            * CANCELLED（1）： 取消状态，当线程不再希望获取锁时，设置为取消状态。
            * SIGNAL（-1）：当前节点的后继者处于等待状态，前节点的线程如果释放或取消了同步状态，通知后继节点。
            * CONDITION(-2) : 表示结点等待在Condition上，当其他线程调用了Condition的signal()方法后，CONDITION状态的结点将从等待队列转移到同步队列中，等待获取同步锁
            * PROPAGATE(-3) : 传递状态,前继节点不仅会唤醒其后继节点，同时也有可能唤醒后继的后继节点。
        上一个节点prev和下一个节点next
            * prev上一个节点
            * next下一个节点
        节点对应的thread
        下一个等待节点nextWaiter
##核心方法        
    * compareAndSetState 比较并交换状态 采用Unsafe.compareAndSwapInt方法实现 原子操作
    * acquire 进行同步器获取
            public final void acquire(int arg) {
                //进行tryAcquire操作失败 并且
                if (!tryAcquire(arg) &&
                    //添加同步队列
                    acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
                    中断这个线程
                    selfInterrupt();
            }
    * acquireQueued 添加同步队列
                final boolean acquireQueued(final Node node, int arg) {
                    //是否失败
                    boolean failed = true;
                    try {
                        //是否中断
                        boolean interrupted = false;
                        //循环处理
                        for (;;) {
                            final Node p = node.predecessor();
                            if (p == head && tryAcquire(arg)) {
                                setHead(node);
                                p.next = null; // help GC
                                failed = false;
                                return interrupted;
                            }
                            if (shouldParkAfterFailedAcquire(p, node) &&
                                parkAndCheckInterrupt())
                                interrupted = true;
                        }
                    } finally {
                        if (failed)
                            cancelAcquire(node);
                    }
                }
    * selfInterrupt
    * tryAcquire 子类实现
    * addWaiter 添加等待节点到队列
           private Node addWaiter(Node mode) {
                //创建新的节点
                Node node = new Node(Thread.currentThread(), mode);
                // Try the fast path of enq; backup to full enq on failure
                //获取尾节点
                Node pred = tail;
                if (pred != null) {
                    //上一个节点不为空
                    node.prev = pred;
                    //设置尾节点
                    if (compareAndSetTail(pred, node)) {
                        pred.next = node;
                        //返回创建完的新节点
                        return node;
                    }
                }
                //添加到尾部失败
                
                enq(node);
                return node;
            }
    *
        
        
## AQS 获取锁的流程

        当lock()执行的时候：
        
        先快速获取锁，当前没有线程执行的时候直接获取锁
        尝试获取锁，当没有线程执行或是当前线程占用锁，可以直接获取锁
        将当前线程包装为node放入同步队列，设置为尾节点
        前一个节点如果为头节点，再次尝试获取一次锁
        将前一个有效节点设置为SIGNAL
        然后阻塞直到被唤醒











