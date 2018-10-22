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

/**
 *
 * 执行提交任务的对象，这个提供了一个屏蔽了任务执行调度细节的接口
 * 执行器来代替一般的一个个创建线程，使用可能如下：
 *
 * Executor executor = <em>anExecutor</em>;
 * executor.execute(new RunnableTask1());
 * executor.execute(new RunnableTask2());
 * ...
 *
 * 然而，执行器接口并没有严格限制执行的同步性
 * 在简单的场景中，提交的任务可能会被调用线程直接执行：
 *
 * class DirectExecutor implements Executor {
 *   public void execute(Runnable r) {
 *     r.run();
 *   }
 * }}
 *
 * 另外的场景，任务会在其他线程中执行：
 *
 * class ThreadPerTaskExecutor implements Executor {
 *   public void execute(Runnable r) {
 *     new Thread(r).start();
 *   }
 * }}
 *
 * 很多执行器的实现有对如何和何时执行作一些限制
 * 下面顺序执行器的实现，会将任务传递给下一个执行器执行：
 *
 * class SerialExecutor implements Executor {
 *   final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
 *   final Executor executor;
 *   Runnable active;
 *
 *   SerialExecutor(Executor executor) {
 *     this.executor = executor;
 *   }
 *
 *   public synchronized void execute(final Runnable r) {
 *     tasks.offer(new Runnable() {
 *       public void run() {
 *         try {
 *           r.run();
 *         } finally {
 *           scheduleNext();
 *         }
 *       }
 *     });
 *     if (active == null) {
 *       scheduleNext();
 *     }
 *   }
 *
 *   protected synchronized void scheduleNext() {
 *     if ((active = tasks.poll()) != null) {
 *       executor.execute(active);
 *     }
 *   }
 * }}
 *
 * 傻子三连问
 * 问题：1. 为什么不把r直接加入队列呢？ 为了保证任务的顺序性，基于任务做串行，在前一个任务完成后，才会开始执行下一个任务。如果直接加入队列，无法感知任务的执行结束时间，无法保证顺序性
 *      2. 如果r中抛了异常，会继续执行任务，如果任务在一直的增加，这个链条一直执行，是否意味着极端情况下这个异常永远抛不出来？不会，因为方法是保证同步的，前一个执行成功了之后，下一个才能执行
 *      3. 继问题2，如果不会，那么任务的执行接口任务是串行执行，每次只能执行一个任务，一个任务执行完成前，不会有新任务加入？会有新任务加入
 *
 *      上述的问题，毫无意义，因为这里本身就是一个生产者消费者的问题，一个生产加入队列，一直加入，如果任务标记为空，则提醒消费；同时任务结束，则从队列继续取任务
 *
 *
 *
 * 这个包中的ExecutorService接口，提供了比Executor更丰富的接口
 * ThreadPoolExecutor类提供了扩展线程池的实现
 * Executors提供了扩展线程池的工厂方法
 *
 * <p>Memory consistency effects: Actions in a thread prior to
 * submitting a {@code Runnable} object to an {@code Executor}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * its execution begins, perhaps in another thread.
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface Executor {

    /**
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution
     * @throws NullPointerException if command is null
     */
    /** 执行给定的任务在未来的某个时刻，该任务可能在线程池中的新线程中执行或者在调用线程中执行，依赖于实现类 */
    void execute(Runnable command);
}
