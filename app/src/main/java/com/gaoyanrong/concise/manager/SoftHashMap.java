package com.gaoyanrong.concise.manager;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * @author 高延荣
 * @date 2018/12/10 11:40
 * 描述: 具备软引用功能的 HashMap
 */
public class SoftHashMap<K, V> extends HashMap<K, V> {

    /**
     * queue,软引用标记队列
     * <p>
     * ★★★★★★★ 解释 ★★★★★★★
     * 当SoftNode中 Value 被回收时，SoftNode 对象会被放入 queue中，以表示当前SoftNode 中的Value不存在
     * 对我们的使用好处就是，我们读取 queue 队列，取出 SoftNode对象，取出其内部的 Key
     * 以便于 temp 通过 key remove
     */
    private ReferenceQueue<V> queue;
    /**
     * 真正的map对象
     * 1、temp 内部 封装的 Node 强引用 K 和 SoftNode
     * 2、SoftNode 内部强引用K，弱引用真正的Value
     */
    private HashMap<K, SoftNode<K, V>> temp;

    public SoftHashMap() {
        queue = new ReferenceQueue<>();
        temp = new HashMap<>();
    }

    @Override
    public V get(Object key) {
        clearQueue();
        // 通过 key进行取值，如果为null，返回null，否则返回 SoftNode 软引用的值
        SoftNode softNode = temp.get(key);
        return softNode == null ? null : (V) softNode.get();
    }

    @Override
    public V put(K key, V value) {
        clearQueue();
        // 创建 SoftNode对象
        SoftNode softNode = new SoftNode(key, value, queue);
        // 返回key之前所对应的SoftNode对象，即oldSoftNode
        SoftNode oldSoftNode = temp.put(key, softNode);
        // 如果oldSoftNode为null，就返回null，否则就返回 oldSoftNode所软引用的 Value
        return oldSoftNode == null ? null : (V) oldSoftNode.get();
    }

    @Override
    public boolean containsKey(Object key) {
        clearQueue();
        return temp.containsKey(key);
    }

    @Override
    public V remove(Object key) {
        clearQueue();
        SoftNode<K, V> remove = temp.remove(key);
        return remove == null ? null : remove.get();
    }

    @Override
    public int size() {
        clearQueue();
        return temp.size();
    }

    /**
     * 通过软引用队列内的 SoftNode，获取Key，然后temp 清除此 Key
     * @see ReferenceQueue poll()
     * poll() -- 类似于 stack 的pop(),移除并返回此对象
     */
    private void clearQueue() {
        SoftNode poll;
        while ((poll = (SoftNode) queue.poll()) != null) {
            temp.remove(poll.key);
        }
    }


    /**
     * 对V进行软引用的类
     * @param <K> key，用于当 V 被回收后，temp 可以通过 key 进行移除
     * @param <V> Value，真正的值
     *
     *           传入的queue，用于当Value被回收后，将 SoftNode对象放入 queue中，
     *           以便于表示 某 SoftNode对象中的Value 已经被收回了。
     */
    private class SoftNode<K, V> extends SoftReference<V> {
        K key;

        public SoftNode(K k, V v, ReferenceQueue queue) {
            super(v, queue);
            key = k;
        }
    }


}