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

package java.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.io.Serializable;

/**
 * 整体说明：
 * Map数据结构接口
 * 提供的方法：size、isEmpty、containsKey、containsValue、put、get、remove、putAll、clear
 * 提供的视图：keySet、values、entrySet（返回Entity的Set集合）
 * 一般方法：equals、hashCode
 * default方法：getOrDefault(key, defaultV)、forEach(BiConsumer)、replace(k, v)、replace(k, v, newV)、replaceAll(BiFunction)、putIfAbsent、remove(k、v)、computeIfAbsent(k,Function)、computeIfPresent(k,Function)
 * compute(k, BiFunction)、merge(k, v, BiFunction)
 *
 * 提供了Entity接口
 * 提供的方法：getKey、getValue、setValue
 * 一般方法：equals、hashCode
 * 静态方法：comparingByKey、comparingByValue 支持基于key和value的比较或者基于传入比较器comparingByKey(Comparator) comparingByValue(Comparator)
 */

/**
 *
 * 映射键值的对象，不能包含重复的键
 * 每一个键最多映射一个值
 *
 * 这个接口替代Dictionary类，Dictionary是一个抽象类，而不是接口
 *
 *
 * 接口提供三种集合视图，可以获取map中键的集合、值的集合以及键值的映射集合
 * 其中HashMap是无序的，TreeMap是保证有序的
 *
 *
 * 注意：如果易变的对象作为map的key，要多加小心，value也可能会随着key equals方法的比较而受到影响
 * 造成map行为的不确定。
 * 一个map包含自身作为key是不允许的，但自身可以作为value，当map的equals和hashCode没有很好定义的情况下，尤其要小心谨慎
 *
 *
 * 所有一般map的实现类应该提供两个标准的构造方法：一个无参构造方法和一个Map类型作为入参的构造方法。后者即允许用户复制任何map
 * 产生一个相同的类。这种推荐并非强制的，但所有JKD中的实现都遵循了这个规则。
 *
 *
 * 在这个接口里，有些不能用的方法，即操作这些方法的时候，如果操作不被支持，会抛UnsupportedOperationException异常
 * 如果调用对map没有影响，就没必要抛出UnsupportedOperationException异常。比如在一个不可变map中调用putAll（map）方法，如果添加的是一个空map，就没有必要抛异常了。
 *
 * 有一些实现类对key和value有限制。比如有些实现不允许空key和空value以及一些限制key的类型。
 * 如果插入一些不合法的key或value，会抛出不合法的异常，比如空指针异常，类型转换异常
 * 如果试图查询不合法的key或Value，可能也会抛异常或者返回false，一些实现类是禁止前者或后者
 *
 * 在集合框架的很多方法是基于equals方法定义的。比如containsKey(Object)，会调用equals方法，首先也会先调用hashCode方法
 *
 * 一些操作会产生循环，抛出自引用的异常，map直接或间接地包含了自身。比如clone()、equals()、hashCode、toString等，实现应该选择性地处理自引用的场景，然而很多时候实现没有这样做。
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see HashMap
 * @see TreeMap
 * @see Hashtable
 * @see SortedMap
 * @see Collection
 * @see Set
 * @since 1.2
 */
public interface Map<K,V> {
    // 查询操作
    /**
     * 返回map中键值对的数量，如果超过Integer.MAX_VALUE，则返回Integer.MAX_VALUE
     */
    int size();

    /**
     * 返回map是否为空
     */
    boolean isEmpty();

    /**
     * 返回map是否包含指定的key
     * 判断：(key==null ? k==null : key.equals(k)) 这种方式可借鉴
     * 如果key的类型不对，则会抛ClassCastException异常
     */
    boolean containsKey(Object key);

    /**
     * 同containsKey
     */
    boolean containsValue(Object value);

    /**
     * 获取指定key的value值
     * 如果map中包含key，则返回value，如果不包含则返回null
     * 注意：返回null，有两种情况 1.map中不包含key 2.key对应的value是null，两种情况可以通过containsKey来进行区分
     */
    V get(Object key);

    // 修改操作

    /**
     * 将指定的key与value进行关联，如果key已存在，则原value就会被覆盖
     *
     * @throws UnsupportedOperationException 当map不支持此操作的时候会抛出
     * @throws ClassCastException 如果指定的key或Value不支持正在插入的类型，抛出此异常
     * @throws NullPointerException 如果map不支持空值，当key或value为null，则抛出此异常
     * @throws IllegalArgumentException 指定的key或Value的属性不合法
     */
    V put(K key, V value);

    /**
     * 将指定key的映射实体移除
     * 如果map中有key存在的value，则移除并返回该value，否则返回null
     * 如果map允许空值，则返回null，并不表明map中不存在key
     * 一旦调用返回，map中肯定不再存在指定的key
     * @throws UnsupportedOperationException 不支持
     * @throws ClassCastException key类型不正确
     * @throws NullPointerException 如果map不允许null，而传入了null
     */
    V remove(Object key);

    // 整体操作

    /**
     * 当此操作进行时，传入的map还在修改，则这个操作行为无效
     *
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    void putAll(Map<? extends K, ? extends V> m);

    /**
     * 移除所有的实体，此操作执行后，map就会被清空
     *
     * @throws UnsupportedOperationException
     */
    void clear();


    // 视图

    /**
     * 返回map包含的key集合
     */
    Set<K> keySet();

    /**
     * 返回map包含的value集合
     */
    Collection<V> values();

    /**
     * 返回map包含的实体集合
     */
    Set<Map.Entry<K, V>> entrySet();

    /**
     * 一个键值对，Map.entrySet方法返回一个map的集合视图，它的元素就在这个类中，这是为一个一种取得map实体的方法
     * Map.Entry只在迭代期间有效，如果map迭代器返回之后被修改了，这个也就无效了
     */
    interface Entry<K,V> {
        /**
         * 返回实体的key
         * @throws IllegalStateException 如果key被移除了
         */
        K getKey();

        /**
         * 返回实体的value
         * @throws IllegalStateException 如果被移除了
         */
        V getValue();

        /**
         * 设置新值，返回旧值
         * @throws UnsupportedOperationException
         * @throws ClassCastException
         * @throws NullPointerException
         * @throws IllegalArgumentException
         * @throws IllegalStateException
         */
        V setValue(V value);

        /**
         * 比较两个实体的等价性
         *     (e1.getKey()==null ?
         *      e2.getKey()==null : e1.getKey().equals(e2.getKey())) &&
         *     (e1.getValue()==null ?
         *      e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         */
        boolean equals(Object o);

        /**
         * 返回实体的哈希值
         * defined to be:
         *     (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *     (e.getValue()==null ? 0 : e.getValue().hashCode())
         * 如果两个实体quals为true，则哈希值一定相等
         */
        int hashCode();

        /**
         * 返回一个比较器，基于map中key的自然顺序
         * @since 1.8
         */
        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K,V>> comparingByKey() {
            return (Comparator<Map.Entry<K, V>> & Serializable)  // java8新语法，同时满足两个接口，猜想就是要同时实现两个接口
                (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        /**
         * 返回一个比较器，基于map中value的自然顺序
         * @since 1.8
         */
        public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K,V>> comparingByValue() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        /**
         * 返回指定的key的比较器
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        /**
         * 返回指定的key的比较器
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }

    // 比较和哈希

    /**
     * 两个map相等，内部的实体也相等
     */
    boolean equals(Object o);

    /**
     * map的hashCode是内部每一个实体hashcode之和
     */
    int hashCode();

    // 默认方法

    /**
     * 返回指定key的值，如果存在就返回value，不存在就返回defalutValue
     * @throws ClassCastException
     * @throws NullPointerException
     * @since 1.8
     */
    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
            ? v
            : defaultValue;
    }

    /**
     * 对map中每一个实体执行Action操作，直到action抛异常了，或执行完毕
     *
     * for (Map.Entry<K, V> entry : map.entrySet())
     *     action.accept(entry.getKey(), entry.getValue());
     * }
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     * @throws NullPointerException 如果action为null
     * @throws ConcurrentModificationException
     * @since 1.8
     */
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }

    /**
     * 依照特定的规则，重新设置每一个key的值
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     *
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws ConcurrentModificationException
     * @since 1.8
     */
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }

            // ise thrown from function is not a cme.
            v = function.apply(k, v);

            try {
                entry.setValue(v);
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
        }
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to {@code null}) associates it with the given value and returns
     * {@code null}, else returns the current value.
     * 如果指定的key没有关联到一个value或者关联到null，将其关联到给定的值，并返回null，否则之前关联值
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @since 1.8
     */
    default V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }

        return v;
    }

    /**
     * 移除指定key和value的实体
     *
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @since 1.8
     */
    default boolean remove(Object key, Object value) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, value) ||
            (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    /**
     * 替换指定key对应的指定值
     *
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @since 1.8
     */
    default boolean replace(K key, V oldValue, V newValue) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, oldValue) ||
            (curValue == null && !containsKey(key))) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    /**
     * Replaces the entry for the specified key only if it is
     * currently mapped to some value.
     *
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @since 1.8
     */
    default V replace(K key, V value) {
        V curValue;
        if (((curValue = get(key)) != null) || containsKey(key)) {
            curValue = put(key, value);
        }
        return curValue;
    }

    /**
     *
     * 默认的实现没有确保同步或原子性，如果要保证原子性，必须要重写这个方法
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @since 1.8
     */
    default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }

    /**
     * If the value for the specified key is present and non-null, attempts to
     * compute a new mapping given the key and its current mapped value.
     *
     * <p>If the function returns {@code null}, the mapping is removed.  If the
     * function itself throws an (unchecked) exception, the exception is
     * rethrown, and the current mapping is left unchanged.
    *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if now absent:
     *
     * <pre> {@code
     * if (map.get(key) != null) {
     *     V oldValue = map.get(key);
     *     V newValue = remappingFunction.apply(key, oldValue);
     *     if (newValue != null)
     *         map.put(key, newValue);
     *     else
     *         map.remove(key);
     * }
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * @param key key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     * @return the new value associated with the specified key, or null if none
     * @throws NullPointerException if the specified key is null and
     *         this map does not support null keys, or the
     *         remappingFunction is null
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V computeIfPresent(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Attempts to compute a mapping for the specified key and its current
     * mapped value (or {@code null} if there is no current mapping). For
     * example, to either create or append a {@code String} msg to a value
     * mapping:
     *
     * <pre> {@code
     * map.compute(key, (k, v) -> (v == null) ? msg : v.concat(msg))}</pre>
     * (Method {@link #merge merge()} is often simpler to use for such purposes.)
     *
     * <p>If the function returns {@code null}, the mapping is removed (or
     * remains absent if initially absent).  If the function itself throws an
     * (unchecked) exception, the exception is rethrown, and the current mapping
     * is left unchanged.
     *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if absent:
     *
     * <pre> {@code
     * V oldValue = map.get(key);
     * V newValue = remappingFunction.apply(key, oldValue);
     * if (oldValue != null ) {
     *    if (newValue != null)
     *       map.put(key, newValue);
     *    else
     *       map.remove(key);
     * } else {
     *    if (newValue != null)
     *       map.put(key, newValue);
     *    else
     *       return null;
     * }
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * @param key key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     * @return the new value associated with the specified key, or null if none
     * @throws NullPointerException if the specified key is null and
     *         this map does not support null keys, or the
     *         remappingFunction is null
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V compute(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);

        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            // delete mapping
            if (oldValue != null || containsKey(key)) {
                // something to remove
                remove(key);
                return null;
            } else {
                // nothing to do. Leave things as they were.
                return null;
            }
        } else {
            // add or replace old mapping
            put(key, newValue);
            return newValue;
        }
    }

    /**
     * If the specified key is not already associated with a value or is
     * associated with null, associates it with the given non-null value.
     * Otherwise, replaces the associated value with the results of the given
     * remapping function, or removes if the result is {@code null}. This
     * method may be of use when combining multiple mapped values for a key.
     * For example, to either create or append a {@code String msg} to a
     * value mapping:
     *
     * <pre> {@code
     * map.merge(key, msg, String::concat)
     * }</pre>
     *
     * <p>If the function returns {@code null} the mapping is removed.  If the
     * function itself throws an (unchecked) exception, the exception is
     * rethrown, and the current mapping is left unchanged.
     *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if absent:
     *
     * <pre> {@code
     * V oldValue = map.get(key);
     * V newValue = (oldValue == null) ? value :
     *              remappingFunction.apply(oldValue, value);
     * if (newValue == null)
     *     map.remove(key);
     * else
     *     map.put(key, newValue);
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * @param key key with which the resulting value is to be associated
     * @param value the non-null value to be merged with the existing value
     *        associated with the key or, if no existing value or a null value
     *        is associated with the key, to be associated with the key
     * @param remappingFunction the function to recompute a value if present
     * @return the new value associated with the specified key, or null if no
     *         value is associated with the key
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not support null keys or the value or remappingFunction is
     *         null
     * @since 1.8
     */
    default V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                   remappingFunction.apply(oldValue, value);
        if(newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
    }
}
