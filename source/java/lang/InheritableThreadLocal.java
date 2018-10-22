/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 *
 * 这个类继承ThreadLocal，提供了值从父线程到子线程继承的功能：当子线程被创建，会从父线程中继承酥油的已有的本地变量值
 * 正常情况下，子类的值对父类来说是等效的，但子类的值可以供通过覆写childValue来定制
 *
 * <p>
 * <p>Inheritable thread-local variables are used in preference to
 * ordinary thread-local variables when the per-thread-attribute being
 * maintained in the variable (e.g., User ID, Transaction ID) must be
 * automatically transmitted to any child threads that are created.
 *
 * 使用InheritableThreadLocal这个类，子线程可以继承父线程的本地临时变量
 * 使用的是Thread的inheritableThreadLocals变量，子线程基于父线程创建副本
 *
 * @author Josh Bloch and Doug Lea
 * @see ThreadLocal
 * @since 1.2
 */

public class InheritableThreadLocal<T> extends ThreadLocal<T> {
    /**
     * Computes the child's initial value for this inheritable thread-local
     * variable as a function of the parent's value at the time the child
     * thread is created.  This method is called from within the parent
     * thread before the child is started.
     * <p>
     * This method merely returns its input argument, and should be overridden
     * if a different behavior is desired.
     *
     * @param parentValue the parent thread's value
     * @return the child thread's initial value
     */
    protected T childValue(T parentValue) {
        return parentValue;
    }

    /**
     * 返回的是inheritableThreadLocals的Map
     * @param t the current thread
     */
    ThreadLocalMap getMap(Thread t) {
        return t.inheritableThreadLocals;
    }

    /**
     * 创建的Map挂载在线程的inheritableThreadLocals变量中
     *
     * @param t          the current thread
     * @param firstValue value for the initial entry of the table.
     */
    void createMap(Thread t, T firstValue) {
        t.inheritableThreadLocals = new ThreadLocalMap(this, firstValue);
    }
}
