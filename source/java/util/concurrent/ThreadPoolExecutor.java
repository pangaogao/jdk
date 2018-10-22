/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

/**
 * ExecutorService使用线程池中的线程执行提交的任务，一般采用Executors工厂方法进行配置
 * <p>
 * 线程池解决了两个问题：
 * 1.通过避免线程开销来提高的执行的性能
 * 2.提供了一个动态调整资源的机制
 * <p>
 * 为了更好地使用，这个类提供了很多灵活的参数和可扩展的钩子
 * <p>
 * 编程人员可以更方便地使用Executors的工厂方法创建
 * newCachedThreadPool 自动回收的无界线程池
 * newFixedThreadPool 固定数量的线程池
 * newSingleThreadExecutor 单后台线程池
 * <p>
 * <dt> 核心线程数和最大线程数 </dt>
 * <p>
 * 新任务提交到线程池
 * 1. 如果任务数少于coreSize，即使有空闲线程，也会创建新线程。
 * 2. 若线程数，大于coreSize，小于maximumPoolSize，只有在队列满的情况下，才会创建新线程
 * <p>
 * <dt> 按需构建 </dt>
 * <p>
 * 1. 默认，即使是core线程，也是在任务到达的时候创建的，可通过方法prestartCoreThread或prestartAllCoreThreads来调整
 * <p>
 * <dt> 创建新线程 </dt>
 * <p>
 * 新线程使用ThreadFactory创建，若没有指定，则采用默认的线程工厂，所有的线程会同属一个线程组
 * 通过提供一个不同的线程工厂，可以修改线程的名称、线程组、优先级、守护线程状态等
 * 如果线程工厂创建线程失败，返回null,执行器会继续执行，但不会执行任何任务
 * <p>
 * <p>
 * <dt>保持时间</dt>
 * <p>
 * 如果当前池中有多于coreSize的线程数，超过保持时间的无效线程会被关闭
 * 当线程池不会有效使用的时候，提供了一种降低资源消耗的方式。当后面更多线程有效的时候，新的线程会被创建
 * 这个参数可以被动态修改，默认情况下保持时间策略只对于超过核心线程数时有效，但allowCoreThreadTimeOut(boolean)可以设置针对核心线程也生效
 * <p>
 * <dt>队列</dt>
 * <p>
 * 阻塞队列都可以用来保存提交的任务，队列的使用主要与线程池的大小变化有关
 * <p>
 * <p>
 * 1.线程数小于coreSize时，执行器会优先增加线程，而不是加入队列
 * 2.线程数大于等于coreSize时，执行器会优先加入队列，而不是创建线程
 * 3.请求不能入队，线程数也超过了maxSize，任务将会被拒绝
 * <p>
 * 目前有三种排队策略
 * <p>
 * <p>
 * 1.直接交付：一个好的默认选择是SynchronousQueue，直接将任务交给线程池处理，如果没有线程可用，任务入队会失败，所以会立刻新建线程
 * 当处理大量内部依赖请求的时候，要避免死锁
 * 直接交付，通常需要不限最大线程数，来避免拒绝提交的新任务，这种情况，如果新任务到达的数量比处理的速度大会造成大量的线程数增长
 * 死锁实例： http://www.importnew.com/22705.html
 * 2.无界队列：比如LinkedBlockingQueue，当所有核心线程都忙碌的时候，新任务会入队，不会有更多的线程被创建（最大线程数就没有任何作用了）
 * 每个任务是完全互相独立的是必要的，之间的执行互不依赖
 * 3.有界队列：比如ArrayBlockingQueue，避免在有限的最大线程数的情况下，资源耗尽，但更加难以协调和控制，队列的大小和最大线程数需要权衡和取舍
 * 大队列、小线程数，会降低CPU、资源的使用率，线程上下文切换次数，会导致吞吐量的降低。如果任务频繁阻塞，系统需要更多的时间去调度
 * 小队列，需要多的线程数，会保持CPU更加繁忙，也会有等多的调度开销，降低吞吐量
 * <p>
 * <li><em>Bounded queues.</em> A bounded queue (for example, an
 * {@link }) helps prevent resource exhaustion when
 * used with finite maximumPoolSizes, but can be more difficult to
 * tune and control.  Queue sizes and maximum pool sizes may be traded
 * off for each other: Using large queues and small pools minimizes
 * CPU usage, OS resources, and context-switching overhead, but can
 * lead to artificially low throughput.  If tasks frequently block (for
 * example if they are I/O bound), a system may be able to schedule
 * time for more threads than you otherwise allow. Use of small queues
 * generally requires larger pool sizes, which keeps CPUs busier but
 * may encounter unacceptable scheduling overhead, which also
 * decreases throughput.  </li>
 * <p>
 * <dt>拒绝任务</dt>
 * <p>
 * 如果在执行器关闭或者队列有限且线程数已达到最大数量情况下，新任务的提交会被拒绝，拒绝策略RejectedExecutionHandler
 * 默认的拒绝策略是AbortPolicy，拒绝并抛出异常
 * CallerRunsPolicy，由主线程运行
 * DiscardPolicy 直接丢弃
 * DiscardOldestPolicy 丢弃队头任务
 * 也可以自定义其他的拒绝策略RejectedExecutionHandler，不过要特别小心容量和队列类型
 * <p>
 * 这个类提供了可扩展的方法，beforeExecute(Thread, Runnable)和afterExecute(Runnable, Throwable)
 * 两个方法在每个任务的执行前后进行调用，可以用来控制执行的环境，比如维护线程本地变量，手机数据或者加入日志
 * terminated方法也可以覆写来实现执行器被完全关闭后的一些特殊处理
 * <p>
 * 如果钩子或者回调方法抛出异常，内部的工作线程会失败并关闭
 * <p>
 * getQueue()方法可以用来对工作队列的监控和调试，强烈不建议用于其他用途
 * 两个补充的方法remove(Runnable)}和purge可以用来在大量任务取消的时候，方便内存回收
 * <p>
 * <p>
 * 程序中线程池不在引用，也没有线程了，将会被自动关闭，
 * 若你希望没有被引用的线程（即使用户忘记关闭）也能自动回收，则必须保证没有使用的线程最终消亡。
 * 可以通过设置keep-alive、使用低于0的core线程数或设置allowCoreThreadTimeOut(boolean)
 * <p>
 * <p>
 * 扩展的例子
 * 这个类大多数的扩展都是覆写一个或多个protected的钩子方法
 * 下面就是一个子类增加了一个停止/恢复的例子
 * <p>
 * class PausableThreadPoolExecutor extends ThreadPoolExecutor {
 * private boolean isPaused;
 * private ReentrantLock pauseLock = new ReentrantLock();
 * private Condition unpaused = pauseLock.newCondition();
 * public PausableThreadPoolExecutor(...) { super(...); }
 * protected void beforeExecute(Thread t, Runnable r) {
 * super.beforeExecute(t, r);
 * pauseLock.lock();
 * try {
 * while (isPaused) unpaused.await();
 * } catch (InterruptedException ie) {
 * t.interrupt();
 * } finally {
 * pauseLock.unlock();
 * }
 * }
 * public void pause() {
 * pauseLock.lock();
 * try {
 * isPaused = true;
 * } finally {
 * pauseLock.unlock();
 * }
 * }
 * public void resume() {
 * pauseLock.lock();
 * try {
 * isPaused = false;
 * unpaused.signalAll();
 * } finally {
 * pauseLock.unlock();
 * }
 * }
 * }}
 *
 * @author Doug Lea
 * @since 1.5
 */


/**
 * 问题：
 * 1.线程池里的线程如何重复利用？比如一个线程执行完请求，怎么控制不退出。
 *   线程通过getTask来获取队列中的任务，进行阻塞
 * 2.线程池空闲时线程池里的线程数量会不会降到0？
 *   不会降低到0
 * 3.线程池如何保持活动时间？线程可以设置一段时间内闲置就会退出（通过keepAliveTime 设置）
 *   通过队列的poll传入等待时间
 * 4.线程池的阻塞队列有什么用？
 *   存储任务，阻塞
 * 5.请求数量太多如何处理过多的请求
 *   拒绝策略
 *
 * */
public class ThreadPoolExecutor extends AbstractExecutorService {
    /**
     * 线程池的控制状态，ctl，原子int分为两个域，工作线程数（有效的线程数量）和运行状态（运行、关闭等）
     * <p>
     * 为了能把两个标记放到一个int，限制了工作线程数最大(2^29)-1，即29位，以后扩展可以使用AtomicLong，目前采用int，简单高效
     * <p>
     * 工作线程数是可以启动没有停止的线程数，这个值可能跟实际活动的线程数不一样
     * 比如，当一个线程工厂创建线程失败，存在的线程
     * <p>
     * The workerCount is the number of workers that have been
     * permitted to start and not permitted to stop.  The value may be
     * transiently different from the actual number of live threads,
     * for example when a ThreadFactory fails to create a thread when
     * asked, and when exiting threads are still performing
     * bookkeeping before terminating. The user-visible pool size is
     * reported as the current size of the workers set.
     * <p>
     * The runState provides the main lifecycle control, taking on values:
     * <p>
     * 运行：接收新任务、并处理队列中的任务
     * 关闭：不接收新任务，继续处理队列中的任务
     * 停止：不接收新任务，不处理队列中的任务，并中断正在处理的任务
     * 整理：任务都已结束，工作线程为0，线程状态变为TIDYING，结束的钩子方法将会执行
     * 结束：terminated()方法执行完毕
     *
     *
     * RUNNING:  Accept new tasks and process queued tasks
     * SHUTDOWN: Don't accept new tasks, but process queued tasks
     * STOP:     Don't accept new tasks, don't process queued tasks,
     * and interrupt in-progress tasks
     * TIDYING:  All tasks have terminated, workerCount is zero,
     * the thread transitioning to state TIDYING
     * will run the terminated() hook method
     * TERMINATED: terminated() has completed
     * <p>
     * The numerical order among these values matters, to allow
     * ordered comparisons. The runState monotonically increases over
     * time, but need not hit each state. The transitions are:
     * <p>
     * RUNNING -> SHUTDOWN
     * On invocation of shutdown(), perhaps implicitly in finalize()
     * (RUNNING or SHUTDOWN) -> STOP
     * On invocation of shutdownNow()
     * SHUTDOWN -> TIDYING
     * When both queue and pool are empty
     * STOP -> TIDYING
     * When pool is empty
     * TIDYING -> TERMINATED
     * When the terminated() hook method has completed
     * <p>
     * Threads waiting in awaitTermination() will return when the
     * state reaches TERMINATED.
     * <p>
     * Detecting the transition from SHUTDOWN to TIDYING is less
     * straightforward than you'd like because the queue may become
     * empty after non-empty and vice versa during SHUTDOWN state, but
     * we can only terminate if, after seeing that it is empty, we see
     * that workerCount is 0 (which sometimes entails a recheck -- see
     * below).
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    /** 29 */
    private static final int COUNT_BITS = Integer.SIZE - 3;
    /** 线程最大数量 */
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    /** 111 */
    private static final int RUNNING = -1 << COUNT_BITS;
    /**  000 */
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    /** 001 */
    private static final int STOP = 1 << COUNT_BITS;
    /** 010 */
    private static final int TIDYING = 2 << COUNT_BITS;
    /** 011 */
    private static final int TERMINATED = 3 << COUNT_BITS;
    /** ~ 取非 */
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }
    /**  获取活动线程的数量 */
    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }
    /** 运行状态和线程数量求或，更新状态 */
    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    /**
     * 小于0都是运行状态
     */
    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    /**
     * 通过CAS增加线程数
     */
    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    /**
     * 通过CAS减少线程数
     */
    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    /**
     * Decrements the workerCount field of ctl. This is called only on
     * abrupt termination of a thread (see processWorkerExit). Other
     * decrements are performed within getTask.
     * 调用在突然的线程关闭(see processWorkerExit)
     */
    private void decrementWorkerCount() {
        do {
        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    /**
     * The queue used for holding tasks and handing off to worker
     * threads.  We do not require that workQueue.poll() returning
     * null necessarily means that workQueue.isEmpty(), so rely
     * solely on isEmpty to see if the queue is empty (which we must
     * do for example when deciding whether to transition from
     * SHUTDOWN to TIDYING).  This accommodates special-purpose
     * queues such as DelayQueues for which poll() is allowed to
     * return null even if it may later return non-null when delays
     * expire.
     */
    private final BlockingQueue<Runnable> workQueue;

    /**
     * Lock held on access to workers set and related bookkeeping.
     * While we could use a concurrent set of some sort, it turns out
     * to be generally preferable to use a lock. Among the reasons is
     * that this serializes interruptIdleWorkers, which avoids
     * unnecessary interrupt storms, especially during shutdown.
     * Otherwise exiting threads would concurrently interrupt those
     * that have not yet interrupted. It also simplifies some of the
     * associated statistics bookkeeping of largestPoolSize etc. We
     * also hold mainLock on shutdown and shutdownNow, for the sake of
     * ensuring workers set is stable while separately checking
     * permission to interrupt and actually interrupting.
     */
    /**
     * 用来限制工作集合和相关记录
     */
    private final ReentrantLock mainLock = new ReentrantLock();
    /**
     * 线程池中所有的线程集合（只有在获取锁的时候才能操作）
     */
    private final HashSet<Worker> workers = new HashSet<Worker>();
    /**
     * 等待条件，支持awaitTermination
     */
    private final Condition termination = mainLock.newCondition();
    /**
     * 记录最大的维护线程大小（只有在获取锁的时候才能操作）
     */
    private int largestPoolSize;
    /**
     * 已完成任务的计数，只有在线程关闭的时候更新（只有在获取锁的时候才能操作）
     */
    private long completedTaskCount;

    /** 所有的用于控制的参数都被声明为volatile变量，以便所有的操作都基于最新的值，无需加锁 */

    /**
     * 创建新线程的工厂
     * 所有用这个工厂创建的线程都会通过方法（addWorker）
     * 所有的调用者必须准备好有可能addWorker会失败，依据系统和用户限制线程数量的策略
     * 即使不是ERROR，创建线程失败可能会造成新线程失败或者阻塞在队列中
     * Factory for new threads. All threads are created using this
     * factory (via method addWorker).  All callers must be prepared
     * for addWorker to fail, which may reflect a system or user's
     * policy limiting the number of threads.  Even though it is not
     * treated as an error, failure to create threads may result in
     * new tasks being rejected or existing ones remaining stuck in
     * the queue.
     * <p>
     * We go further and preserve pool invariants even in the face of
     * errors such as OutOfMemoryError, that might be thrown while
     * trying to create threads.  Such errors are rather common due to
     * the need to allocate a native stack in Thread.start, and users
     * will want to perform clean pool shutdown to clean up.  There
     * will likely be enough memory available for the cleanup code to
     * complete without encountering yet another OutOfMemoryError.
     */
    private volatile ThreadFactory threadFactory;
    /**
     * 当饱和或者关闭，调用拒绝策略
     */
    private volatile RejectedExecutionHandler handler;
    /**
     * 空闲线程等待超时时间，当超过核心线程数或者allowCoreThreadTimeOut为true时，会采用这个超时时间，否则线程会永远等待
     */
    private volatile long keepAliveTime;
    /**
     * 默认是false，核心的线程即使空闲也会保持存活，若设置为true，核心线程会使用keepAliveTime作为超时等待时间
     */
    private volatile boolean allowCoreThreadTimeOut;
    /**
     * 核心线程大小是工作线程保持存活最小的数量。如果allowCoreThreadTimeOut被设置为true，minimum最小的线程数为0
     */
    private volatile int corePoolSize;
    /**
     * 最大线程数，实际最大数量边界为CAPACITY
     */
    private volatile int maximumPoolSize;
    /**
     * 默认的拒绝策略
     */
    private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();

    /**
     * 调用者关闭线程的权限控制，也可以看(see checkShutdownAccess)，只有校验通过才能尝试关闭
     * <p>
     * All actual invocations of Thread.interrupt (see
     * interruptIdleWorkers and interruptWorkers) ignore
     * SecurityExceptions, meaning that the attempted interrupts
     * silently fail. In the case of shutdown, they should not fail
     * unless the SecurityManager has inconsistent policies, sometimes
     * allowing access to a thread and sometimes not. In such cases,
     * failure to actually interrupt threads may disable or delay full
     * termination. Other uses of interruptIdleWorkers are advisory,
     * and failure to actually interrupt will merely delay response to
     * configuration changes so is not handled exceptionally.
     */
    private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");

    /**
     * Worker类主要维护线程运行任务的中断控制状态以及其他的监控记录
     * 这个类继承AbstractQueuedSynchronizer来简化获取和释放任务执行锁
     * 中断保护主要来唤醒等待工作的线程，而不是中断正在执行的任务
     * 实现了一个简单的非重入锁互斥锁，而不用重入锁，因为希望工作线程在调用线程池控制方法（比如setCorePoolSize）时，不能获取锁
     * 另外，为了防止运行前中断，初始化锁状态为负值，启动的时候清除它
     * Class Worker mainly maintains interrupt control state for
     * threads running tasks, along with other minor bookkeeping.
     * This class opportunistically extends
     * to simplify acquiring and releasing a lock surrounding each
     * task execution.  This protects against interrupts that are
     * intended to wake up a worker thread waiting for a task from
     * instead interrupting a task being run.  We implement a simple
     * non-reentrant mutual exclusion lock rather than use
     * ReentrantLock because we do not want worker tasks to be able to
     * reacquire the lock when they invoke pool control methods like
     * setCorePoolSize.  Additionally, to suppress interrupts until
     * the thread actually starts running tasks, we initialize lock
     * state to a negative value, and clear it upon start (in
     * runWorker).
     *
     *
     * TODO 为什么Worker要继承AbstractQueuedSynchronizer，而不直接用ReentrantLock
     *   1.ReentrantLock是可重入的，会造成线程自我中断
     *   2.Worker继承AbstractQueuedSynchronizer，获取锁的时候，是不可重入
     *
     */
    private final class Worker extends AbstractQueuedSynchronizer implements Runnable {
        /** 序列化号 */
        private static final long serialVersionUID = 6138294804551838833L;
        /** 运行的线程，若工厂创建失败，则为null */
        final Thread thread;
        /** 初始运行的任务，可能为null */
        Runnable firstTask;
        /** 线程完成的任务数 */
        volatile long completedTasks;
        /** 基于给的任务和线程工厂创建Worker */
        Worker(Runnable firstTask) {
            /** 禁止中断，直到运行，参见方法interruptIfStarted */
            setState(-1);
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        /**
         * Delegates main run loop to outer runWorker
         */
        /** 外部任务运行的代理 */
        public void run() {
            runWorker(this);
        }
        /** 0代表未锁，1代表锁 */
        protected boolean isHeldExclusively() {
            return getState() != 0;
        }
        /** 获取锁,这里是不可重入的 */
        protected boolean tryAcquire(int unused) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        /** 释放锁 */
        protected boolean tryRelease(int unused) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        public void lock() {
            acquire(1);
        }
        public boolean tryLock() {
            return tryAcquire(1);
        }
        public void unlock() {
            release(1);
        }
        public boolean isLocked() {
            return isHeldExclusively();
        }
        /** 自我中断，启动后中断 */
        void interruptIfStarted() {
            Thread t;
            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException ignore) {
                }
            }
        }
    }

    /**
     * 设置控制状态的方法
     */

    /**
     * 将运行状态转换为给定的目标状态（若大于等于目标状态，则什么都不做）
     * @param targetState 可选的状态 SHUTDOWN或STOP（不能是TIDYING或TERMINATED，可使用tryTerminate代替）
     */
    private void advanceRunState(int targetState) {
        for (; ; ) {
            int c = ctl.get();
            if (runStateAtLeast(c, targetState) ||
                    ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))))
                break;
        }
    }

    /**
     * 若状态为（已关闭且线程池和队列都为空）或（停止，线程为空）则转换状态为TERMINATED
     * 否则，直接执行terminate，工作线程非0，中断空闲线程确保扩散关闭信号，在终止之前这个方法必须被调用，关闭必须减少线程数或者移除队列
     * 这个方法是对ScheduledThreadPoolExecutor是非私有，可调用的
     * Transitions to TERMINATED state if either (SHUTDOWN and pool
     * and queue empty) or (STOP and pool empty).  If otherwise
     * eligible to terminate but workerCount is nonzero, interrupts an
     * idle worker to ensure that shutdown signals propagate. This
     * method must be called following any action that might make
     * termination possible -- reducing worker count or removing tasks
     * from the queue during shutdown. The method is non-private to
     * allow access from ScheduledThreadPoolExecutor.
     */
    final void tryTerminate() {
        for (; ; ) {
            int c = ctl.get();
            /** 若（正在运行）或（状态大于等于整理）或（状态为关闭队列不为空），则直接返回 */
            if (isRunning(c) ||
                    runStateAtLeast(c, TIDYING) ||
                    (runStateOf(c) == SHUTDOWN && !workQueue.isEmpty()))
                return;
            /** 工作线程不为0，中断 */
            if (workerCountOf(c) != 0) { // Eligible to terminate
                interruptIdleWorkers(ONLY_ONE);
                return;
            }
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try {
                        /** 钩子方法 */
                        terminated();
                    } finally {
                        ctl.set(ctlOf(TERMINATED, 0));
                        termination.signalAll();
                    }
                    return;
                }
            } finally {
                mainLock.unlock();
            }
        }
    }

    /**
     * 控制线程中断的方法
     * Methods for controlling interrupts to worker threads.
     */

    /**
     * If there is a security manager, makes sure caller has
     * permission to shut down threads in general (see shutdownPerm).
     * If this passes, additionally makes sure the caller is allowed
     * to interrupt each worker thread. This might not be true even if
     * first check passed, if the SecurityManager treats some threads
     * specially.
     */
    private void checkShutdownAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(shutdownPerm);
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                for (Worker w : workers)
                    security.checkAccess(w.thread);
            } finally {
                mainLock.unlock();
            }
        }
    }

    /**
     * Interrupts all threads, even if active. Ignores SecurityExceptions
     * (in which case some threads may remain uninterrupted).
     */
    /** 中断所有线程（即使是活动的，忽略安全异常，有可能一些线程仍然是未被中断的） */
    private void interruptWorkers() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers)
                w.interruptIfStarted();
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Interrupts threads that might be waiting for tasks (as
     * indicated by not being locked) so they can check for
     * termination or configuration changes. Ignores
     * SecurityExceptions (in which case some threads may remain
     * uninterrupted).
     * 中断那些等待任务的线程（没有被阻塞），这样可以检查终止或配置的修改，忽略安全异常（那种情况下，可能有些线程仍然不会被中断）
     * @param onlyOne 若true，则最多中断一个任务，这个只会在tryTerminate中在终止的时候仍然有工作线程时调用，这种情况下，最多中断一个线程来床底关闭信号防止目前所有线程都在等待
     * @param onlyOne If true, interrupt at most one worker. This is
     *                called only from tryTerminate when termination is otherwise
     *                enabled but there are still other workers.  In this case, at
     *                most one waiting worker is interrupted to propagate shutdown
     *                signals in case all threads are currently waiting.
     *                Interrupting any arbitrary thread ensures that newly arriving
     *                workers since shutdown began will also eventually exit.
     *                To guarantee eventual termination, it suffices to always
     *                interrupt only one idle worker, but shutdown() interrupts all
     *                idle workers so that redundant workers exit promptly, not
     *                waiting for a straggler task to finish.
     */
    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                /** 线程没有被中断，且能获取锁，说明线程没有任务，则调用中断 */
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne)
                    break;
            }
        } finally {
            mainLock.unlock();
        }
    }

    /** 中断所有工作线程，避免记忆参数的意义 */
    private void interruptIdleWorkers() {
        interruptIdleWorkers(false);
    }

    private static final boolean ONLY_ONE = true;

    /** 最小的使用，大部分用于ScheduledThreadPoolExecutor */

    /** 触发拒绝策略对给命令的执行 */
    final void reject(Runnable command) {
        handler.rejectedExecution(command, this);
    }

    /** 从运行状态转变到关闭，执行进一步的清理。这里无操作，但ScheduledThreadPoolExecutor会用来取消延迟的任务 */
    void onShutdown() {
    }

    /** ScheduledThreadPoolExecutor在关闭期间检查对运行状态的检查
     * @param shutdownOK true if should return true if SHUTDOWN
     */
    final boolean isRunningOrShutdown(boolean shutdownOK) {
        int rs = runStateOf(ctl.get());
        return rs == RUNNING || (rs == SHUTDOWN && shutdownOK);
    }

    /**
     * 将任务队列放入新集合，正常情况下使用drainTo
     * 但是，如果队列是延迟队列或者其他类型的队列（取出或drainTo会移除元素失败），就一个个删除
     */
    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> q = workQueue;
        ArrayList<Runnable> taskList = new ArrayList<Runnable>();
        q.drainTo(taskList);
        if (!q.isEmpty()) {
            for (Runnable r : q.toArray(new Runnable[0])) {
                if (q.remove(r))
                    taskList.add(r);
            }
        }
        return taskList;
    }

    /**
     * 用于创建、运行、清理线程的方法
     */

    /**
     * Checks if a new worker can be added with respect to current
     * pool state and the given bound (either core or maximum). If so,
     * the worker count is adjusted accordingly, and, if possible, a
     * new worker is created and started, running firstTask as its
     * first task. This method returns false if the pool is stopped or
     * eligible to shut down. It also returns false if the thread
     * factory fails to create a thread when asked.  If the thread
     * creation fails, either due to the thread factory returning
     * null, or due to an exception (typically OutOfMemoryError in
     * Thread.start()), we roll back cleanly.
     *
     * @param firstTask the task the new thread should run first (or
     *                  null if none). Workers are created with an initial first task
     *                  (in method execute()) to bypass queuing when there are fewer
     *                  than corePoolSize threads (in which case we always start one),
     *                  or when the queue is full (in which case we must bypass queue).
     *                  Initially idle threads are usually created via
     *                  prestartCoreThread or to replace other dying workers.
     * @param core      if true use corePoolSize as bound, else
     *                  maximumPoolSize. (A boolean indicator is used here rather than a
     *                  value to ensure reads of fresh values after checking other pool
     *                  state).
     * @return true if successful
     */
    /**
     * 依据当前的线程状态和边界（coreSize or maximum）校验新线程是否可以被加入
     * 若可加入，则调整线程数量，这样新的线程会被创建、启动、运行，运行firstTask作为它的第一个方法
     * 若线程停止或者要关闭，方法会返回false
     * 若线程工厂创建线程失败，可能是因为线程工厂返回了null或者在线程启动的时候抛了异常（比如OutOfMemoryError），会有规则地回滚
     *
     * 总结：
     * 1.可创建任务，任务数+1
     * 在大于等于shutdown状态时，直接返回失败（例外在Shutdown状态时可以加入空任务）
     * 2.函数复用，通过core参数进行参数对象判断 coreSize or maxSize
     * 3.使用ThreadFactory创建线程，并启动
     */
    private boolean addWorker(Runnable firstTask, boolean core) {
        /** 如果队列满了或者终止了，则加入失败 */
        retry:
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);
            /**
             * (rs >= SHUTDOWN) && (rs != SHUTDOWN || firstTask != null || workQueue.isEmpty())
             * 以下两种状态会直接返回false
             * 1.STOP、TIDYING、TERMINATED状态
             * 2.SHUTDOWN状态，firstTask不为null或任务队列为空
             * 只有不是在（关闭状态，firstTask = null 并且任务队列不为空的）这情况，都直接返回false
             * */
            if (rs >= SHUTDOWN &&
                    !(rs == SHUTDOWN &&
                            firstTask == null &&
                            !workQueue.isEmpty()))
                return false;
            /**
             * 以下状态会继续执行
             * 1.运行状态
             * 2.SHUTDOWN状态，firstTask为空或任务队列不为空
             *  */
            for (; ; ) {
                int wc = workerCountOf(c);
                /** 校验是否新开线程，线程数是否+1 */
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                if (compareAndIncrementWorkerCount(c))
                    break retry;
                c = ctl.get();  // Re-read ctl
                if (runStateOf(c) != rs)
                    continue retry;
            }
        }
        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            w = new Worker(firstTask);
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    /** 获取锁之后，重新check状态，防止线程工厂失败或者在锁获取前线程池关闭 */
                    int rs = runStateOf(ctl.get());
                    /** 当线程池是运行的或者关闭的任务是空的，则新启线程 */
                    if (rs < SHUTDOWN || (rs == SHUTDOWN && firstTask == null)) {
                        /** 确保线程池是可启动的 */
                        if (t.isAlive())
                            throw new IllegalThreadStateException();
                        workers.add(w);
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {
            if (!workerStarted)
                addWorkerFailed(w);
        }
        return workerStarted;
    }

    /**
     * 线程创建失败回滚
     * 1.从工作线程中移除
     * 2.减少线程数量
     * 3.重新检测终止状态，防止这个线程是进行关闭的
     */
    private void addWorkerFailed(Worker w) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (w != null)
                workers.remove(w);
            decrementWorkerCount();
            tryTerminate();
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Performs cleanup and bookkeeping for a dying worker. Called
     * only from worker threads. Unless completedAbruptly is set,
     * assumes that workerCount has already been adjusted to account
     * for exit.  This method removes thread from worker set, and
     * possibly terminates the pool or replaces the worker if either
     * it exited due to user task exception or if fewer than
     * corePoolSize workers are running or queue is non-empty but
     * there are no workers.
     *
     * 执行清理和记录要dying的线程
     * @param w                 the worker
     * @param completedAbruptly if the worker died due to user exception  是否由用户异常造成的线程死亡
     */
    private void processWorkerExit(Worker w, boolean completedAbruptly) {
        /** 用户异常造成的，线程数减一 */
        if (completedAbruptly) // If abrupt, then workerCount wasn't adjusted
            decrementWorkerCount();

        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += w.completedTasks;
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }

        tryTerminate();

        int c = ctl.get();
        if (runStateLessThan(c, STOP)) {
            if (!completedAbruptly) {
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
                if (min == 0 && !workQueue.isEmpty())
                    min = 1;
                if (workerCountOf(c) >= min)
                    return; // replacement not needed
            }
            addWorker(null, false);
        }
    }

    /**
     * Performs blocking or timed wait for a task, depending on
     * current configuration settings, or returns null if this worker
     * must exit because of any of:
     * 1. There are more than maximumPoolSize workers (due to
     * a call to setMaximumPoolSize).
     * 2. The pool is stopped.
     * 3. The pool is shutdown and the queue is empty.
     * 4. This worker timed out waiting for a task, and timed-out
     * workers are subject to termination (that is,
     * {@code allowCoreThreadTimeOut || workerCount > corePoolSize})
     * both before and after the timed wait, and if the queue is
     * non-empty, this worker is not the last thread in the pool.
     *
     * @return task, or null if the worker must exit, in which case
     * workerCount is decremented
     */
    private Runnable getTask() {
        boolean timedOut = false; // Did the last poll() time out?
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);
            /** 必要的时候，检查队列是否是空，返回null，工作线程就会退出 */
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }
            int wc = workerCountOf(c);
            /** 判断线程是否要剔除 */
            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
            /** （工作线程数大于最大线程数，或者计时并超时）并且（工作线程大于1或者工作队列为空） */
            if ((wc > maximumPoolSize || (timed && timedOut))
                    && (wc > 1 || workQueue.isEmpty())) {
                /** 至少保证有一个线程或者没有任务 */
                if (compareAndDecrementWorkerCount(c))
                    return null;
                continue;
            }
            try {
                /**
                 * 是否采用计时超时
                 * 是：poll，等待指定时间
                 * 否：take，有就拿，没有就阻塞
                 *  */
                Runnable r = timed ?
                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                /** 获取到直接返回，没有获取到说明超时，如果take到了null，相当于直接丢弃 */
                if (r != null)
                    return r;
                timedOut = true;
            } catch (InterruptedException retry) {
                timedOut = false;
            }
        }
    }

    /**
     * Main worker run loop.  Repeatedly gets tasks from queue and
     * executes them, while coping with a number of issues:
     * <p>
     * 1. We may start out with an initial task, in which case we
     * don't need to get the first one. Otherwise, as long as pool is
     * running, we get tasks from getTask. If it returns null then the
     * worker exits due to changed pool state or configuration
     * parameters.  Other exits result from exception throws in
     * external code, in which case completedAbruptly holds, which
     * usually leads processWorkerExit to replace this thread.
     * <p>
     * 2. Before running any task, the lock is acquired to prevent
     * other pool interrupts while the task is executing, and then we
     * ensure that unless pool is stopping, this thread does not have
     * its interrupt set.
     * <p>
     * 3. Each task run is preceded by a call to beforeExecute, which
     * might throw an exception, in which case we cause thread to die
     * (breaking loop with completedAbruptly true) without processing
     * the task.
     * <p>
     * 4. Assuming beforeExecute completes normally, we run the task,
     * gathering any of its thrown exceptions to send to afterExecute.
     * We separately handle RuntimeException, Error (both of which the
     * specs guarantee that we trap) and arbitrary Throwables.
     * Because we cannot rethrow Throwables within Runnable.run, we
     * wrap them within Errors on the way out (to the thread's
     * UncaughtExceptionHandler).  Any thrown exception also
     * conservatively causes thread to die.
     * <p>
     * 5. After task.run completes, we call afterExecute, which may
     * also throw an exception, which will also cause thread to
     * die. According to JLS Sec 14.20, this exception is the one that
     * will be in effect even if task.run throws.
     * <p>
     * The net effect of the exception mechanics is that afterExecute
     * and the thread's UncaughtExceptionHandler have as accurate
     * information as we can provide about any problems encountered by
     * user code.
     *
     * @param w the worker
     */
    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        /** 允许中断，设置为启动 */
        w.unlock();
        /** 是否由用户线程异常造成的 */
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                /** 若线程池正在停止，保证线程是中断的，否则，确保线程不被中断，清理中断的时候，需要再次检查来处理shutdownNow，reckeck与shutdownNow函数有关 */
                if ((runStateAtLeast(ctl.get(), STOP) ||
                        (Thread.interrupted() &&
                                runStateAtLeast(ctl.get(), STOP))) &&
                        !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x;
                        throw x;
                    } catch (Error x) {
                        thrown = x;
                        throw x;
                    } catch (Throwable x) {
                        thrown = x;
                        throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }

    // Public constructors and methods

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory and rejected execution handler.
     * It may be more convenient to use one of the {@link Executors} factory
     * methods instead of this general purpose constructor.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument
     * @param workQueue       the queue to use for holding tasks before they are
     *                        executed.  This queue will hold only the {@code Runnable}
     *                        tasks submitted by the {@code execute} method.
     * @throws IllegalArgumentException if one of the following holds:<br>
     *                                  {@code corePoolSize < 0}<br>
     *                                  {@code keepAliveTime < 0}<br>
     *                                  {@code maximumPoolSize <= 0}<br>
     *                                  {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException     if {@code workQueue} is null
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), defaultHandler);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default rejected execution handler.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument
     * @param workQueue       the queue to use for holding tasks before they are
     *                        executed.  This queue will hold only the {@code Runnable}
     *                        tasks submitted by the {@code execute} method.
     * @param threadFactory   the factory to use when the executor
     *                        creates a new thread
     * @throws IllegalArgumentException if one of the following holds:<br>
     *                                  {@code corePoolSize < 0}<br>
     *                                  {@code keepAliveTime < 0}<br>
     *                                  {@code maximumPoolSize <= 0}<br>
     *                                  {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException     if {@code workQueue}
     *                                  or {@code threadFactory} is null
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, defaultHandler);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument
     * @param workQueue       the queue to use for holding tasks before they are
     *                        executed.  This queue will hold only the {@code Runnable}
     *                        tasks submitted by the {@code execute} method.
     * @param handler         the handler to use when execution is blocked
     *                        because the thread bounds and queue capacities are reached
     * @throws IllegalArgumentException if one of the following holds:<br>
     *                                  {@code corePoolSize < 0}<br>
     *                                  {@code keepAliveTime < 0}<br>
     *                                  {@code maximumPoolSize <= 0}<br>
     *                                  {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException     if {@code workQueue}
     *                                  or {@code handler} is null
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), handler);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument
     * @param workQueue       the queue to use for holding tasks before they are
     *                        executed.  This queue will hold only the {@code Runnable}
     *                        tasks submitted by the {@code execute} method.
     * @param threadFactory   the factory to use when the executor
     *                        creates a new thread
     * @param handler         the handler to use when execution is blocked
     *                        because the thread bounds and queue capacities are reached
     * @throws IllegalArgumentException if one of the following holds:<br>
     *                                  {@code corePoolSize < 0}<br>
     *                                  {@code keepAliveTime < 0}<br>
     *                                  {@code maximumPoolSize <= 0}<br>
     *                                  {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException     if {@code workQueue}
     *                                  or {@code threadFactory} or {@code handler} is null
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

    /**
     * Executes the given task sometime in the future.  The task
     * may execute in a new thread or in an existing pooled thread.
     * <p>
     * If the task cannot be submitted for execution, either because this
     * executor has been shutdown or because its capacity has been reached,
     * the task is handled by the current {@code RejectedExecutionHandler}.
     *
     * @param command the task to execute
     * @throws RejectedExecutionException at discretion of
     *                                    {@code RejectedExecutionHandler}, if the task
     *                                    cannot be accepted for execution
     * @throws NullPointerException       if {@code command} is null
     */
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        /*
         * Proceed in 3 steps:
         *
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.
         */
        /**
         * 主要有三步
         * 1.若小于核心线程数，尝试开启新线程（调用增加线程会原子校验运行状态和工作任务数，当不应该加入的时候会返回false）
         * 2.若任务加入队列，需要double check是否新加了线程（因为存在上次检验之后，死掉的线程）或者任务加入之后，线程关闭了
         * 3.若不能加入队列，则创建新线程，若失败，则知道在关闭或者满了，走拒绝策略
         * */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            /** 小于核心线程数，设置core为true， 新建线程并启动 */
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        /** 大于等于核心线程数，加入队列 */
        if (isRunning(c) && workQueue.offer(command)) {
            /** 再次检查，如果非运行状态，则移除，并执行拒绝 TODO 为什么要重新检查，因为存在上次检验之后，死掉的线程或者线程关闭 */
            int recheck = ctl.get();
            /** 非运行，则移除；移除成功，执行拒绝； 无论是否运行，队列有内容无线程时，需增加一个线程 */
            if (!isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        /** 入队失败，以maxSize为主，新建线程 */
        } else if (!addWorker(command, false))
            /** 失败则拒绝 */
            reject(command);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     * Invocation has no additional effect if already shut down.
     * <p>
     * <p>This method does not wait for previously submitted tasks to
     * complete execution.  Use {@link #awaitTermination awaitTermination}
     * to do that.
     *
     * @throws SecurityException {@inheritDoc}
     */
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            advanceRunState(SHUTDOWN);
            interruptIdleWorkers();
            onShutdown(); // hook for ScheduledThreadPoolExecutor
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }

    /**
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution. These tasks are drained (removed)
     * from the task queue upon return from this method.
     * <p>
     * <p>This method does not wait for actively executing tasks to
     * terminate.  Use {@link #awaitTermination awaitTermination} to
     * do that.
     * <p>
     * <p>There are no guarantees beyond best-effort attempts to stop
     * processing actively executing tasks.  This implementation
     * cancels tasks via {@link Thread#interrupt}, so any task that
     * fails to respond to interrupts may never terminate.
     *
     * @throws SecurityException {@inheritDoc}
     */
    public List<Runnable> shutdownNow() {
        List<Runnable> tasks;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            advanceRunState(STOP);
            interruptWorkers();
            tasks = drainQueue();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
        return tasks;
    }

    public boolean isShutdown() {
        return !isRunning(ctl.get());
    }

    /**
     * Returns true if this executor is in the process of terminating
     * after {@link #shutdown} or {@link #shutdownNow} but has not
     * completely terminated.  This method may be useful for
     * debugging. A return of {@code true} reported a sufficient
     * period after shutdown may indicate that submitted tasks have
     * ignored or suppressed interruption, causing this executor not
     * to properly terminate.
     *
     * @return {@code true} if terminating but not yet terminated
     */
    public boolean isTerminating() {
        int c = ctl.get();
        return !isRunning(c) && runStateLessThan(c, TERMINATED);
    }

    public boolean isTerminated() {
        return runStateAtLeast(ctl.get(), TERMINATED);
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (; ; ) {
                if (runStateAtLeast(ctl.get(), TERMINATED))
                    return true;
                if (nanos <= 0)
                    return false;
                nanos = termination.awaitNanos(nanos);
            }
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Invokes {@code shutdown} when this executor is no longer
     * referenced and it has no threads.
     */
    protected void finalize() {
        shutdown();
    }

    /**
     * Sets the thread factory used to create new threads.
     *
     * @param threadFactory the new thread factory
     * @throws NullPointerException if threadFactory is null
     * @see #getThreadFactory
     */
    public void setThreadFactory(ThreadFactory threadFactory) {
        if (threadFactory == null)
            throw new NullPointerException();
        this.threadFactory = threadFactory;
    }

    /**
     * Returns the thread factory used to create new threads.
     *
     * @return the current thread factory
     * @see #setThreadFactory(ThreadFactory)
     */
    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    /**
     * Sets a new handler for unexecutable tasks.
     *
     * @param handler the new handler
     * @throws NullPointerException if handler is null
     * @see #getRejectedExecutionHandler
     */
    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        if (handler == null)
            throw new NullPointerException();
        this.handler = handler;
    }

    /**
     * Returns the current handler for unexecutable tasks.
     *
     * @return the current handler
     * @see #setRejectedExecutionHandler(RejectedExecutionHandler)
     */
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return handler;
    }

    /**
     * Sets the core number of threads.  This overrides any value set
     * in the constructor.  If the new value is smaller than the
     * current value, excess existing threads will be terminated when
     * they next become idle.  If larger, new threads will, if needed,
     * be started to execute any queued tasks.
     *
     * @param corePoolSize the new core size
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @see #getCorePoolSize
     *
     * 设置核心线程数
     * 若新值比当前值小，超过的线程会在空闲的时候被关闭
     * 若新值比当前值大，必要的时候，新线程会被启动执行队列中的任务
     * 变化影响点（新值、当前值、线程数、任务数）：
     *   1.运行线程数
     *   2.队列任务数
     *
     */
    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize < 0)
            throw new IllegalArgumentException();
        int delta = corePoolSize - this.corePoolSize;
        this.corePoolSize = corePoolSize;
        /** 活动线程大于核心线程数，中断所有线程 */
        if (workerCountOf(ctl.get()) > corePoolSize)
            /** 中断空闲线程 */
            interruptIdleWorkers();
        /** 活动线程数不大于核心线程数，且新值比当前值大 */
        else if (delta > 0) {
            /** 获取差值和队列任务数的最小值，在这个范围内，添加线程，直到队列为空或值为0 */
            int k = Math.min(delta, workQueue.size());
            while (k-- > 0 && addWorker(null, true)) {
                if (workQueue.isEmpty())
                    break;
            }
        }
    }

    /**
     * Returns the core number of threads.
     *
     * @return the core number of threads
     * @see #setCorePoolSize
     */
    public int getCorePoolSize() {
        return corePoolSize;
    }

    /**
     * Starts a core thread, causing it to idly wait for work. This
     * overrides the default policy of starting core threads only when
     * new tasks are executed. This method will return {@code false}
     * if all core threads have already been started.
     *
     * @return {@code true} if a thread was started
     */
    public boolean prestartCoreThread() {
        return workerCountOf(ctl.get()) < corePoolSize &&
                addWorker(null, true);
    }

    /**
     * Same as prestartCoreThread except arranges that at least one
     * thread is started even if corePoolSize is 0.
     */
    void ensurePrestart() {
        int wc = workerCountOf(ctl.get());
        if (wc < corePoolSize)
            addWorker(null, true);
        else if (wc == 0)
            addWorker(null, false);
    }

    /**
     * Starts all core threads, causing them to idly wait for work. This
     * overrides the default policy of starting core threads only when
     * new tasks are executed.
     *
     * @return the number of threads started
     */
    public int prestartAllCoreThreads() {
        int n = 0;
        while (addWorker(null, true))
            ++n;
        return n;
    }

    /**
     * Returns true if this pool allows core threads to time out and
     * terminate if no tasks arrive within the keepAlive time, being
     * replaced if needed when new tasks arrive. When true, the same
     * keep-alive policy applying to non-core threads applies also to
     * core threads. When false (the default), core threads are never
     * terminated due to lack of incoming tasks.
     *
     * @return {@code true} if core threads are allowed to time out,
     * else {@code false}
     * @since 1.6
     */
    public boolean allowsCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    /**
     * Sets the policy governing whether core threads may time out and
     * terminate if no tasks arrive within the keep-alive time, being
     * replaced if needed when new tasks arrive. When false, core
     * threads are never terminated due to lack of incoming
     * tasks. When true, the same keep-alive policy applying to
     * non-core threads applies also to core threads. To avoid
     * continual thread replacement, the keep-alive time must be
     * greater than zero when setting {@code true}. This method
     * should in general be called before the pool is actively used.
     *
     * @param value {@code true} if should time out, else {@code false}
     * @throws IllegalArgumentException if value is {@code true}
     *                                  and the current keep-alive time is not greater than zero
     * @since 1.6
     */
    public void allowCoreThreadTimeOut(boolean value) {
        if (value && keepAliveTime <= 0)
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        if (value != allowCoreThreadTimeOut) {
            allowCoreThreadTimeOut = value;
            if (value)
                interruptIdleWorkers();
        }
    }

    /**
     * Sets the maximum allowed number of threads. This overrides any
     * value set in the constructor. If the new value is smaller than
     * the current value, excess existing threads will be
     * terminated when they next become idle.
     *
     * @param maximumPoolSize the new maximum
     * @throws IllegalArgumentException if the new maximum is
     *                                  less than or equal to zero, or
     *                                  less than the {@linkplain #getCorePoolSize core pool size}
     * @see #getMaximumPoolSize
     */
    public void setMaximumPoolSize(int maximumPoolSize) {
        if (maximumPoolSize <= 0 || maximumPoolSize < corePoolSize)
            throw new IllegalArgumentException();
        this.maximumPoolSize = maximumPoolSize;
        if (workerCountOf(ctl.get()) > maximumPoolSize)
            interruptIdleWorkers();
    }

    /**
     * Returns the maximum allowed number of threads.
     *
     * @return the maximum allowed number of threads
     * @see #setMaximumPoolSize
     */
    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    /**
     * Sets the time limit for which threads may remain idle before
     * being terminated.  If there are more than the core number of
     * threads currently in the pool, after waiting this amount of
     * time without processing a task, excess threads will be
     * terminated.  This overrides any value set in the constructor.
     *
     * @param time the time to wait.  A time value of zero will cause
     *             excess threads to terminate immediately after executing tasks.
     * @param unit the time unit of the {@code time} argument
     * @throws IllegalArgumentException if {@code time} less than zero or
     *                                  if {@code time} is zero and {@code allowsCoreThreadTimeOut}
     * @see #getKeepAliveTime(TimeUnit)
     *
     * 设置线程保持空闲的等待时间
     *
     */
    public void setKeepAliveTime(long time, TimeUnit unit) {
        if (time < 0)
            throw new IllegalArgumentException();
        if (time == 0 && allowsCoreThreadTimeOut())
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        long keepAliveTime = unit.toNanos(time);
        long delta = keepAliveTime - this.keepAliveTime;
        this.keepAliveTime = keepAliveTime;
        if (delta < 0)
            interruptIdleWorkers();
    }

    /**
     * Returns the thread keep-alive time, which is the amount of time
     * that threads in excess of the core pool size may remain
     * idle before being terminated.
     *
     * @param unit the desired time unit of the result
     * @return the time limit
     * @see #setKeepAliveTime(long, TimeUnit)
     */
    public long getKeepAliveTime(TimeUnit unit) {
        return unit.convert(keepAliveTime, TimeUnit.NANOSECONDS);
    }

    /* 用户级别的队列工具 */

    /**
     * Returns the task queue used by this executor. Access to the
     * task queue is intended primarily for debugging and monitoring.
     * This queue may be in active use.  Retrieving the task queue
     * does not prevent queued tasks from executing.
     *
     * @return the task queue
     */
    public BlockingQueue<Runnable> getQueue() {
        return workQueue;
    }

    /**
     * Removes this task from the executor's internal queue if it is
     * present, thus causing it not to be run if it has not already
     * started.
     * <p>
     * <p>This method may be useful as one part of a cancellation
     * scheme.  It may fail to remove tasks that have been converted
     * into other forms before being placed on the internal queue. For
     * example, a task entered using {@code submit} might be
     * converted into a form that maintains {@code Future} status.
     * However, in such cases, method {@link #purge} may be used to
     * remove those Futures that have been cancelled.
     *
     * @param task the task to remove
     * @return {@code true} if the task was removed
     */
    public boolean remove(Runnable task) {
        boolean removed = workQueue.remove(task);
        /** 防止现在关闭或者队列为空 */
        tryTerminate();
        return removed;
    }

    /**
     * Tries to remove from the work queue all {@link Future}
     * tasks that have been cancelled. This method can be useful as a
     * storage reclamation operation, that has no other impact on
     * functionality. Cancelled tasks are never executed, but may
     * accumulate in work queues until worker threads can actively
     * remove them. Invoking this method instead tries to remove them now.
     * However, this method may fail to remove tasks in
     * the presence of interference by other threads.
     * 尝试去移除工作队列（Future）中所有被取消的线程
     * 这个方法可以被用作内存回收的操作，对功能没有任何影响
     * 取消的线程永远不会被执行，但可能在队列中堆积，直到工作线程移除它们
     * 这个方法的调用就是尝试现在移除取消的线程，但是，这个方法可能在其他线程存在影响的情况下执行失败
     */
    public void purge() {
        final BlockingQueue<Runnable> q = workQueue;
        try {
            Iterator<Runnable> it = q.iterator();
            while (it.hasNext()) {
                Runnable r = it.next();
                if (r instanceof Future<?> && ((Future<?>) r).isCancelled())
                    it.remove();
            }
        } catch (ConcurrentModificationException fallThrough) {
            // Take slow path if we encounter interference during traversal.
            // Make copy for traversal and call remove for cancelled entries.
            // The slow path is more likely to be O(N*N).
            /** 若在移动的过程中遇到干扰，进行线程的拷贝，在 */
            for (Object r : q.toArray())
                if (r instanceof Future<?> && ((Future<?>) r).isCancelled())
                    q.remove(r);
        }
        /** 防止现在关闭或者队列为空 */
        tryTerminate();
    }
    /* 统计 */
    /** 返回当前线程的数量 */
    public int getPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            // Remove rare and surprising possibility of isTerminated() && getPoolSize() > 0
            return runStateAtLeast(ctl.get(), TIDYING) ? 0 : workers.size();
        } finally {
            mainLock.unlock();
        }
    }

    /** 返回活动线程数 */
    public int getActiveCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            int n = 0;
            for (Worker w : workers)
                if (w.isLocked())
                    ++n;
            return n;
        } finally {
            mainLock.unlock();
        }
    }

    /** 返回线程池中曾有的最大线程数 */
    public int getLargestPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            return largestPoolSize;
        } finally {
            mainLock.unlock();
        }
    }

    /** 返回大致被调度过的总任务数（由于任务的状态和线程在计算的过程中动态变化，所以返回值是个大概值） */
    public long getTaskCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            long n = completedTaskCount;
            for (Worker w : workers) {
                n += w.completedTasks;
                if (w.isLocked())
                    ++n;
            }
            return n + workQueue.size();
        } finally {
            mainLock.unlock();
        }
    }

    /** 返回大致的已经完成的任务数 */
    public long getCompletedTaskCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            long n = completedTaskCount;
            for (Worker w : workers)
                n += w.completedTasks;
            return n;
        } finally {
            mainLock.unlock();
        }
    }

    /** 返回线程池的描述信息，状态、预计线程数、任务数量 */
    public String toString() {
        long ncompleted;
        int nworkers, nactive;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            ncompleted = completedTaskCount;
            nactive = 0;
            nworkers = workers.size();
            for (Worker w : workers) {
                ncompleted += w.completedTasks;
                if (w.isLocked())
                    ++nactive;
            }
        } finally {
            mainLock.unlock();
        }
        int c = ctl.get();
        String rs = (runStateLessThan(c, SHUTDOWN) ? "Running" :
                (runStateAtLeast(c, TERMINATED) ? "Terminated" :
                        "Shutting down"));
        return super.toString() +
                "[" + rs +
                ", pool size = " + nworkers +
                ", active threads = " + nactive +
                ", queued tasks = " + workQueue.size() +
                ", completed tasks = " + ncompleted +
                "]";
    }

    /* 扩展的钩子方法 */

    /**
     * Method invoked prior to executing the given Runnable in the
     * given thread.  This method is invoked by thread {@code t} that
     * will execute task {@code r}, and may be used to re-initialize
     * ThreadLocals, or to perform logging.
     * <p>
     * <p>This implementation does nothing, but may be customized in
     * subclasses. Note: To properly nest multiple overridings, subclasses
     * should generally invoke {@code super.beforeExecute} at the end of
     * this method.
     *
     * @param t the thread that will run task {@code r}
     * @param r the task that will be executed
     */
    protected void beforeExecute(Thread t, Runnable r) {
    }

    /**
     * Method invoked upon completion of execution of the given Runnable.
     * This method is invoked by the thread that executed the task. If
     * non-null, the Throwable is the uncaught {@code RuntimeException}
     * or {@code Error} that caused execution to terminate abruptly.
     * <p>
     * <p>This implementation does nothing, but may be customized in
     * subclasses. Note: To properly nest multiple overridings, subclasses
     * should generally invoke {@code super.afterExecute} at the
     * beginning of this method.
     * <p>
     * <p><b>Note:</b> When actions are enclosed in tasks (such as
     * {@link FutureTask}) either explicitly or via methods such as
     * {@code submit}, these task objects catch and maintain
     * computational exceptions, and so they do not cause abrupt
     * termination, and the internal exceptions are <em>not</em>
     * passed to this method. If you would like to trap both kinds of
     * failures in this method, you can further probe for such cases,
     * as in this sample subclass that prints either the direct cause
     * or the underlying exception if a task has been aborted:
     * <p>
     * <pre> {@code
     * class ExtendedExecutor extends ThreadPoolExecutor {
     *   // ...
     *   protected void afterExecute(Runnable r, Throwable t) {
     *     super.afterExecute(r, t);
     *     if (t == null && r instanceof Future<?>) {
     *       try {
     *         Object result = ((Future<?>) r).get();
     *       } catch (CancellationException ce) {
     *           t = ce;
     *       } catch (ExecutionException ee) {
     *           t = ee.getCause();
     *       } catch (InterruptedException ie) {
     *           Thread.currentThread().interrupt(); // ignore/reset
     *       }
     *     }
     *     if (t != null)
     *       System.out.println(t);
     *   }
     * }}</pre>
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     *          execution completed normally
     */
    protected void afterExecute(Runnable r, Throwable t) {
    }

    /**
     * Method invoked when the Executor has terminated.  Default
     * implementation does nothing. Note: To properly nest multiple
     * overridings, subclasses should generally invoke
     * {@code super.terminated} within this method.
     */
    protected void terminated() {
    }

    /* Predefined RejectedExecutionHandlers */

    /**
     * A handler for rejected tasks that runs the rejected task
     * directly in the calling thread of the {@code execute} method,
     * unless the executor has been shut down, in which case the task
     * is discarded.
     */
    public static class CallerRunsPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code CallerRunsPolicy}.
         */
        public CallerRunsPolicy() {
        }

        /**
         * Executes task r in the caller's thread, unless the executor
         * has been shut down, in which case the task is discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }

    /**
     * 默认策略，拒绝，且抛出异常
     */
    public static class AbortPolicy implements RejectedExecutionHandler {
        /**
         * Creates an {@code AbortPolicy}.
         */
        public AbortPolicy() {
        }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException always
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException("Task " + r.toString() +
                    " rejected from " +
                    e.toString());
        }
    }

    /**
     * A handler for rejected tasks that silently discards the
     * rejected task.
     */
    public static class DiscardPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardPolicy}.
         */
        public DiscardPolicy() {
        }

        /**
         * Does nothing, which has the effect of discarding task r.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        }
    }

    /**
     * A handler for rejected tasks that discards the oldest unhandled
     * request and then retries {@code execute}, unless the executor
     * is shut down, in which case the task is discarded.
     */
    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardOldestPolicy} for the given executor.
         */
        public DiscardOldestPolicy() {
        }

        /**
         * Obtains and ignores the next task that the executor
         * would otherwise execute, if one is immediately available,
         * and then retries execution of task r, unless the executor
         * is shut down, in which case task r is instead discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                e.getQueue().poll();
                e.execute(r);
            }
        }
    }
}
