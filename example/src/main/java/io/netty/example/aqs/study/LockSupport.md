#LockSupport 详解

        用于创建锁和其他线程同步类的基础类。其底层使用Unsafe类实现。
        * park()   暂停当前线程 除非许可可用
        * park(Object blocker) 暂停当前线程 除非许可可用  blocker方便在线程dump的时候看到具体的阻塞对象的信息。
        * parkNanos(Object blocker, long nanos)  nanos为超时时间
        * parkUntil(Object blocker, long deadline)  // 暂停当前线程，直到某个时间
        * getBlocker(Thread t) 
        * parkNanos(long nanos)
        * unpark(Thread thread)  回复当前线程


##实现方式
        使用Unsafe中的park和unpark方法进行实现。
        
        * public native void unpark(Object var1);
        * public native void park(boolean var1, long var2);
