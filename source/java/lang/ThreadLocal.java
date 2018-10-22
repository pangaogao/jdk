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
     * 线程本地变量依赖于每个线程的线性hash Map(Thread.threadLocals和inheritableThreadLocals)
     * 线程本地变量对象作为key，通过hash值搜索
     */
    private final int threadLocalHashCode = nextHashCode();
    private static AtomicInteger nextHashCode = new AtomicInteger();  // 从0开始，自动更新
    /**
     * TODO 这个后期应该搞懂
     * <p>
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
     *
     * @return the initial value for this thread-local
     */
    protected T initialValue() {
        return null;
    }

    /**
     * 创建一个线程本地变量，初始值通过调用supplier方法决定
     *
     * @param <S>      the type of the thread local's value 线程本地变量值
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
     * 返回当前线程的变量副本值，如果当前线程变量没有值，则第一次返回初始化方法返回的值
     * <p>
     * 1.先获取当前Thread的的ThreadLocalMap变量
     * 2.再通过ThreadLocal获取对应的value
     * 3.如果没有获取到或者map是空的，返回初始值，可以通过重写方法来设置初始值
     */
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T) e.value;
                return result;
            }
        }
        return setInitialValue();
    }

    /**
     * set的变体来设置初始值，防止用户重写set方法
     * 原因：第一次get的时候如果没有设置值，会采用默认的值，如果使用set方法，一旦用户重写了set的方法，会造成初始值的改变
     * 所以：设置私有的初始化方法，如果子类要设置初始化方法，直接重写initialValue方法就ok，initialValue方法提供了扩展点
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

    /**
     * 获取线程的Map对象，直接设置
     * 如果Map对象不为空，则直接新建map，并赋值给Thread的成员
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    /**
     * 移除之后，获取的时候会走初始化的值
     */
    public void remove() {
        ThreadLocalMap m = getMap(Thread.currentThread());
        if (m != null)
            m.remove(this);
    }

    /**
     * 获取与线程本地变量相关的Map
     */
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }

    /**
     * 创建一个与线程相关的本地变量ThreadLocalMap，在InheritableThreadLocal类中被覆写
     */
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    /**
     * 只用于在线程构造方法里面调用，继承父线程的变量值
     * 工厂方法来创建继承的线程本地变量的map
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
    /**
     * 在子类InheritableThreadLocal中定义，涉及值的可见性定义
     */
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
     * 为了避免大量的长时间的使用，hash表使用弱引用来作为key
     */
    static class ThreadLocalMap {

        /**
         * 哈希表中的实体采用弱引用，采用主引用域作为key（ThreadLocal对象），一旦为null了表示不会再被引用了，就可以清除了
         */
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /**
             * 与ThreadLocal关联的值
             */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        /**
         * 初始化容量16，2的指数倍
         */
        private static final int INITIAL_CAPACITY = 16;
        private Entry[] table;
        private int size = 0;
        /**
         * 阈值，为总容量的2/3
         */
        private int threshold;

        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }

        /**
         * 增长i，模总数
         */
        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }

        /**
         * 减少i，模总数
         */
        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        /**
         * 创建一个初始化的Map，当放入值的时候才会创建，延迟创建，优化了性能
         */
        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            /** 哈希映射规则 */
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            setThreshold(INITIAL_CAPACITY);
        }

        /**
         * 构建一个新的Map包含所有父Map的所有值，在创建继承类的时候调用,对应Thread中inheritableThreadLocals的变量
         */
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
                        /** 对应InheritableThreadLocal类 */
                        Object value = key.childValue(e.value);
                        Entry c = new Entry(key, value);
                        int h = key.threadLocalHashCode & (len - 1);
                        /** 线程探测法冲突解决 */
                        while (table[h] != null)
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
         * <p>
         * 1.根据哈希值获取对应的实体，判断实体内容，则返回
         * 2.否则走miss逻辑
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
                /** 发现有过期的值,则进行过期擦除，并rehash */
                if (k == null)
                    expungeStaleEntry(i);
                else
                    i = nextIndex(i, len);
                e = tab[i];
            }
            return null;
        }

        /**
         * 设置关联key的值，冲突的解决方法：线性探测法
         * 1.查询到就直接修改,返回
         * 2.如果有过期，则使用替换，replaceStaleEntry
         * 3.没有过期则会新建实体插入
         */
        private void set(ThreadLocal<?> key, Object value) {
            // 没有使用类似于get的快速路径，因为set创建一个新实体和替代一种实体都很平常，在这种情况下，快速路径相对而言会更多失败
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len - 1);
            /** 根据哈希值映射开始设置 */
            for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();
                if (k == key) {
                    /** 如果命中，则直接赋值，返回 */
                    e.value = value;
                    return;
                }
                /** 如果遇到过期槽，则rehash并替换 */
                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }
            /** 没有找到，也没有过期，直接新建 */
            tab[i] = new Entry(key, value);
            int sz = ++size;
            /** 如果没有无过期值，并且结果大于阀值，进行rehash */
            if (!cleanSomeSlots(i, sz) && sz >= threshold) //
                rehash();
        }

        /** 移除相关key的实体 */
        private void remove(ThreadLocal<?> key) {
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len - 1);
            for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
                if (e.get() == key) {
                    /** 清除引用，进行过期清除 */
                    e.clear();
                    expungeStaleEntry(i);
                    return;
                }
            }
        }

        /**
         * 在设置指定key的value时，替换遇到的过期实体
         * 无论指定key的实体是否存在，value值都会存到指定实体内
         * 这个方法会清除包含过期实体周围所有的过期实体
         *
         * @param key       the key
         * @param value     与key关联的value
         * @param staleSlot 搜索key的时候遇到第一个过期实体的索引
         */
        private void replaceStaleEntry(ThreadLocal<?> key, Object value, int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;
            /** 从当前槽（null）往前找，两个null之间，最远过期key的槽记为slotToExpunge */
            int slotToExpunge = staleSlot;
            for (int i = prevIndex(staleSlot, len); (e = tab[i]) != null; i = prevIndex(i, len))
                if (e.get() == null)
                    slotToExpunge = i;
            /** 从当前槽（null）往后找，两个null之间，找相关的key，看是否命中 */
            for (int i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                /** 查询到了对应的key，覆盖对应的值，把当前值 赋值到起始位置，把起始的（null）赋为当前 */
                if (k == key) {
                    e.value = value;
                    tab[i] = tab[staleSlot];  // 过期实体
                    tab[staleSlot] = e;
                    /** 如果从staleSlot之前没有过期，将slotToExpunge置为i，避免多次扫描 */
                    if (slotToExpunge == staleSlot)
                        slotToExpunge = i;
                    cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }
                /** 如果遇到了过期值，并且staleSlot到第一个空值间没有过期实体，就把过期索引值设置为i */
                if (k == null && slotToExpunge == staleSlot)
                    slotToExpunge = i;
            }
            /** 如果没有找到key，就新建实体放入, 新建实体放入前，将之前的值释放 */
            tab[staleSlot].value = null;
            tab[staleSlot] = new Entry(key, value);
            /** 如果存在其他过期实体，清除 */
            if (slotToExpunge != staleSlot)
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }

        /**
         * 清理过期槽staleSlot，并重新计算hash值移动槽位（或清理过期槽），直到遇到空槽
         *
         * @param staleSlot 需要擦除的过期槽位置，处理后续的过期实体，如果过期则清除，不过期则重新计算哈希值
         * @return 返回之后第一个为null的槽位置
         */
        private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            /** 先擦除当前的槽 */
            tab[staleSlot].value = null;
            tab[staleSlot] = null;
            size--;
            /** 继续清理后面的过期槽，重新计算哈希值，直到实体为null */
            Entry e;
            int i;
            for (i = nextIndex(staleSlot, len); (e = tab[i]) != null; i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                /** 过期key,直接删除 */
                if (k == null) {
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {
                    /** 非过期key，重新计算哈希值 */
                    int h = k.threadLocalHashCode & (len - 1);
                    /** 不是当前位置，则将当前位置置为null */
                    if (h != i) {
                        tab[i] = null;
                        /** 线性探测法解决哈希冲突 */
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }
            return i;
        }

        /**
         * 启发式地扫描寻找过期实体，这个方法会在新元素添加或者另外一个过期实体被清除的时候触发。最好的情况，O(n)时间，清除所有过期的实体
         *
         * @param i 非过期实体的索引，扫描的开始从i之后开始
         * @param n 扫描的控制  log2(n) 除非在扫描（log2(table.length)-1）之前，过期实体被找到
         * @return 如果有过期实体被清除，则返回true，否则返回false
         * 这里好的情况清除了所有的过期值
         */
        private boolean cleanSomeSlots(int i, int n) {
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
            } while ((n >>>= 1) != 0);
            return removed;
        }

        /** 先清理所有的过期数据再判断是否 resize */
        private void rehash() {
            expungeStaleEntries();
            if (size >= threshold - threshold / 4)
                resize();
        }

        /** 表的大小，增加一倍 */
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
                        /** 每一处不使用的时候，都会将引用置为null,这个习惯可以学习 */
                        e.value = null;
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

        /** 清除所有的过期槽 */
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
