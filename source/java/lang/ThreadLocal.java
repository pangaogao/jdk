/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.lang.ref.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 这个类提供了线程本地变量
 */
public class ThreadLocal<T> {
    /**
     * ThreadLocals rely on per-thread linear-probe hash maps attached
     * to each thread (Thread.threadLocals and
     * inheritableThreadLocals).  The ThreadLocal objects act as keys,
     * searched via threadLocalHashCode.  This is a custom hash code
     * (useful only within ThreadLocalMaps) that eliminates collisions
     * in the common case where consecutively constructed ThreadLocals
     * are used by the same threads, while remaining well-behaved in
     * less common cases.
     */
    private final int threadLocalHashCode = nextHashCode();
    private static AtomicInteger nextHashCode = new AtomicInteger();  // 从0开始，自动更新
    /**
     * TODO 这个后期应该搞懂
     *
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    /**
     * 返回线程局部变量的内部值，这个方法会在第一次get方法时被调用，如果之前有set方法调用，这个方法就不会被调用
     * 这个方法最多一个线程调用一次
     * 默认设置为null，如果要改变默认值，可以通过子类继承进行方法复写，需要使用匿名内部类
     * @return the initial value for this thread-local
     */
    protected T initialValue() {
        return null;
    }

    /**
     * 创建一个线程本地变量，初始值通过调用supplier方法决定
     * @param <S> the type of the thread local's value 线程本地变量值
     * @param supplier the supplier to be used to determine the initial value 用来决定初始值的supplier
     * @return a new thread local variable 返回本地方法变量
     * @throws NullPointerException if the specified supplier is null
     * @since 1.8
     */
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(supplier);
    }

    public ThreadLocal() {
    }

    /**
     * Returns the value in the current thread's copy of this
     * thread-local variable.  If the variable has no value for the
     * current thread, it is first initialized to the value returned
     * by an invocation of the {@link #initialValue} method.
     *
     * 返回当前线程的变量副本值，如果当前线程变量没有值，则第一次返回初始化方法返回的值
     *
     * @return the current thread's value of this thread-local
     */
    public T get() {
        Thread t = Thread.currentThread(); // 获取当前线程
        ThreadLocalMap map = getMap(t); // 获取与当前线程本地线程相关的Map
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }

    /**
     * set的变体来设置初始值，防止用户重写set方法
     */
    private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }

    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    // 移除之后会采用initialValue的初始值
     public void remove() {
         ThreadLocalMap m = getMap(Thread.currentThread());
         if (m != null)
             m.remove(this);
     }

    // 获取与线程本地变量相关的Map
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }

    /**
     * 在InheritableThreadLocal里被重写
     * Create the map associated with a ThreadLocal. Overridden in
     * InheritableThreadLocal.
     *
     * @param t the current thread
     * @param firstValue value for the initial entry of the map
     */
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    /**
     * Factory method to create map of inherited thread locals.
     * Designed to be called only from Thread constructor.
     * 只设计用于在线程构造方法里面调用
     * 工厂方法来创建继承的线程本地变量的map
     *
     * @param  parentMap the map associated with parent thread
     * @return a map containing the parent's inheritable bindings
     */
    static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
        return new ThreadLocalMap(parentMap);
    }

    /**
     * Method childValue is visibly defined in subclass
     * InheritableThreadLocal, but is internally defined here for the
     * sake of providing createInheritedMap factory method without
     * needing to subclass the map class in InheritableThreadLocal.
     * This technique is preferable to the alternative of embedding
     * instanceof tests in methods.
     */
    //
    T childValue(T parentValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * ThreadLocal的扩展类，可使用初始化的方法（利用Supplier）
     */
    static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {

        private final Supplier<? extends T> supplier;

        SuppliedThreadLocal(Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }

        @Override
        protected T initialValue() {
            return supplier.get();
        }
    }

    /**
     * 定制的Map只用来维护线程的本地变量
     *
     * 为了避免大量的长时间的使用，hash表使用弱引用来作为key
     * ThreadLocalMap is a customized hash map suitable only for
     * maintaining thread local values. No operations are exported
     * outside of the ThreadLocal class. The class is package private to
     * allow declaration of fields in class Thread.  To help deal with
     * very large and long-lived usages, the hash table entries use
     * WeakReferences for keys. However, since reference queues are not
     * used, stale entries are guaranteed to be removed only when
     * the table starts running out of space.
     */
    static class ThreadLocalMap {

        /**
         * The entries in this hash map extend WeakReference, using
         * its main ref field as the key (which is always a
         * ThreadLocal object).  Note that null keys (i.e. entry.get()
         * == null) mean that the key is no longer referenced, so the
         * entry can be expunged from table.  Such entries are referred to
         * as "stale entries" in the code that follows.
         */
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        private static final int INITIAL_CAPACITY = 16;   // 初始容量，必须是2的指数
        private Entry[] table;   // 数据实体数组，必要时扩容，容量是2的指数
        private int size = 0;   // 实体数量
        private int threshold; // 阀值，下一次扩容的点

        // 设置扩容的阀值维护在2/3的因子
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }
        // 增长i，模长度
        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }
        // 减少i，模长度
        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        // 构建一个初始化的Map，是延迟加载的，在至少有一个实体放入的时候才进行创建
        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1); // 这里的映射通过threadLocal的hashCode和容量求与获取
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            setThreshold(INITIAL_CAPACITY);
        }
        // 构建一个新的Map包含所有父Map的所有值，在创建继承类的时候调用
        private ThreadLocalMap(ThreadLocalMap parentMap) {
            Entry[] parentTable = parentMap.table;
            int len = parentTable.length;
            setThreshold(len);
            table = new Entry[len];

            for (int j = 0; j < len; j++) {
                Entry e = parentTable[j];
                if (e != null) {
                    @SuppressWarnings("unchecked")
                    ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                    if (key != null) {
                        Object value = key.childValue(e.value);
                        Entry c = new Entry(key, value);
                        int h = key.threadLocalHashCode & (len - 1);
                        while (table[h] != null)   // 冲突解决的方案采用的是线程探测法
                            h = nextIndex(h, len);
                        table[h] = c;
                        size++;
                    }
                }
            }
        }

        /**
         * 获取关联key的实体
         * 直接获取到存在的key，否则依赖于getEntryAfterMiss，这种设计对于直接命中效率最高
         * 返回关联的实体，没有则返回null
         */
        private Entry getEntry(ThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];
            if (e != null && e.get() == key)
                return e;
            else
                return getEntryAfterMiss(key, i, e);
        }

        /**
         * 没有直接命中，情况下使用
         *
         * 1.发现则返回
         * 2.过期了，则进行清除，并进行rehash，继续遍历，直到查到实体或null则返回没有查到
         */
        private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;

            while (e != null) {
                ThreadLocal<?> k = e.get();
                if (k == key)
                    return e;
                if (k == null)
                    expungeStaleEntry(i);  // 发现有过期的值,则进行过期擦除，并rehash，可以继续遍历
                else
                    i = nextIndex(i, len);  // 不为空，则获取下一个e
                e = tab[i];
            }
            return null;
        }

        /**
         * 设置关联key的值
         * 1.查询到就直接修改返回
         * 2.如果有过期，则使用替换，replaceStaleEntry
         * 3.没有过期则会新建实体插入
         */
        private void set(ThreadLocal<?> key, Object value) {
            // 没有使用类似于get的快速路径，因为set创建一个新实体和替代一种实体都很平常，在这种情况下，快速路径相对而言会更多失败
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);

            for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();
                if (k == key) {
                    e.value = value;
                    return;
                }
                if (k == null) {
                    replaceStaleEntry(key, value, i);  // 如果已过期，则进行替换
                    return;
                }
            }
            tab[i] = new Entry(key, value);  // 如果没有找到，也没有过期，直接新建
            int sz = ++size;
            if (!cleanSomeSlots(i, sz) && sz >= threshold) // 清除过期值，如果结果大于阀值，也进行rehash
                rehash();
        }

        /**
         * Remove the entry for key.
         */
        private void remove(ThreadLocal<?> key) {
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);
            for (Entry e = tab[i];e != null;e = tab[i = nextIndex(i, len)]) {
                if (e.get() == key) {
                    e.clear();
                    expungeStaleEntry(i);
                    return;
                }
            }
        }

        /**
         * Replace a stale entry encountered during a set operation
         * with an entry for the specified key.  The value passed in
         * the value parameter is stored in the entry, whether or not
         * an entry already exists for the specified key.
         *
         * As a side effect, this method expunges all stale entries in the
         * "run" containing the stale entry.  (A run is a sequence of entries
         * between two null slots.)
         *
         * @param  key the key
         * @param  value the value to be associated with key
         * @param  staleSlot index of the first stale entry encountered while
         *         searching for key.
         */
        private void replaceStaleEntry(ThreadLocal<?> key, Object value, int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;
            // 找到两个null值之间最靠前的过期下标
            int slotToExpunge = staleSlot;
            for (int i = prevIndex(staleSlot, len); (e = tab[i]) != null; i = prevIndex(i, len))
                if (e.get() == null)
                    slotToExpunge = i;

            // Find either the key or trailing null slot of run, whichever
            // occurs first
            // 找到对应的key
            for (int i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();

                // If we find key, then we need to swap it
                // with the stale entry to maintain hash table order.
                // The newly stale slot, or any other stale slot
                // encountered above it, can then be sent to expungeStaleEntry
                // to remove or rehash all of the other entries in run.
                if (k == key) {  // 如果查询到对应的key, 覆盖对应的value，把当前的值放到过期的下标处，过期的值，放到当前（下标是i）
                    e.value = value;
                    tab[i] = tab[staleSlot];
                    tab[staleSlot] = e;
                    // Start expunge at preceding stale entry if it exists
                    if (slotToExpunge == staleSlot)  // 如果在两个null之间只有一个过期的值，staleSlot，则直接设置下标，避免扫描
                        slotToExpunge = i;
                    cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }

                // If we didn't find stale entry on backward scan, the
                // first stale entry seen while scanning for key is the
                // first still present in the run.
                if (k == null && slotToExpunge == staleSlot)  // 如果遇到了过期值，并且两个空实体之间都没有过期， 就把过期值设置为i
                    slotToExpunge = i;
            }
            // 如果没有找到key，就新建实体放入
            tab[staleSlot].value = null;  // 释放引用，让内存可回收
            tab[staleSlot] = new Entry(key, value);
            // If there are any other stale entries in run, expunge them
            if (slotToExpunge != staleSlot) // 如果存在过期的值
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }

        /**
         * 通过rehash=在头槽和下一个空槽之间可能冲突的实体，擦除过期的实体，这也擦除了蔓延的null
         * 剔除两个空实体间的过期值，并
         * 返回下一个为null的槽
         */
        private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            // 擦除坐标下的过期实体
            tab[staleSlot].value = null;
            tab[staleSlot] = null;
            size--;
            // 重新计算hash，直到遇见null
            Entry e;
            int i;
            for (i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                if (k == null) {  // 遇见过期直接擦除
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {  // 非过期的实体，重新计算hash，放置到对应位置，如果存在对应的key，则会被重新计算放置到对应位置，这样在getEntryAfterMiss方法中可以继续遍历
                    int h = k.threadLocalHashCode & (len - 1);
                    if (h != i) {
                        tab[i] = null;
                        // Unlike Knuth 6.4 Algorithm R, we must scan until
                        // null because multiple entries could have been stale.
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }
            return i;
        }

        /**
         * Heuristically scan some cells looking for stale entries.
         * This is invoked when either a new element is added, or
         * another stale one has been expunged. It performs a
         * logarithmic number of scans, as a balance between no
         * scanning (fast but retains garbage) and a number of scans
         * proportional to number of elements, that would find all
         * garbage but would cause some insertions to take O(n) time.
         *
         * @param i a position known NOT to hold a stale entry. The
         * scan starts at the element after i.
         *
         * @param n scan control: {@code log2(n)} cells are scanned,
         * unless a stale entry is found, in which case
         * {@code log2(table.length)-1} additional cells are scanned.
         * When called from insertions, this parameter is the number
         * of elements, but when from replaceStaleEntry, it is the
         * table length. (Note: all this could be changed to be either
         * more or less aggressive by weighting n instead of just
         * using straight log n. But this version is simple, fast, and
         * seems to work well.)
         *
         * @return true if any stale entries have been removed.
         * 这里好的情况清除了所有的过期值
         */
        private boolean cleanSomeSlots(int i, int n) {  // i为下一个为null的槽
            boolean removed = false;
            Entry[] tab = table;
            int len = tab.length;
            do {
                i = nextIndex(i, len);
                Entry e = tab[i];
                if (e != null && e.get() == null) {
                    n = len;
                    removed = true;
                    i = expungeStaleEntry(i);
                }
            } while ( (n >>>= 1) != 0);
            return removed;
        }

        /**
         * 先清理所有的过期数据
         * resize
         */
        private void rehash() {
            expungeStaleEntries();

            // Use lower threshold for doubling to avoid hysteresis
            if (size >= threshold - threshold / 4)
                resize();
        }

        /**
         * Double the capacity of the table.
         */
        private void resize() {
            Entry[] oldTab = table;
            int oldLen = oldTab.length;
            int newLen = oldLen * 2;
            Entry[] newTab = new Entry[newLen];
            int count = 0;

            for (int j = 0; j < oldLen; ++j) {
                Entry e = oldTab[j];
                if (e != null) {
                    ThreadLocal<?> k = e.get();
                    if (k == null) {
                        e.value = null; // Help the GC
                    } else {
                        int h = k.threadLocalHashCode & (newLen - 1);
                        while (newTab[h] != null)
                            h = nextIndex(h, newLen);
                        newTab[h] = e;
                        count++;
                    }
                }
            }

            setThreshold(newLen);
            size = count;
            table = newTab;
        }

        /**
         * Expunge all stale entries in the table.
         * 清除所有的过期槽
         */
        private void expungeStaleEntries() {
            Entry[] tab = table;
            int len = tab.length;
            for (int j = 0; j < len; j++) {
                Entry e = tab[j];
                if (e != null && e.get() == null)
                    expungeStaleEntry(j);
            }
        }
    }
}
