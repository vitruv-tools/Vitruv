package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.Collection;

public interface ConcatMap<K, V> {
    V put(V value, K... keys);

    /**
     * Returns the value to which the specified keys are mapped, or null if this map contains no
     * mapping for these keys.
     * 
     * @param keys
     *            the keys whose associated value is to be returned
     * @return the value to which the specified keys are mapped, or null if this map contains no
     *         mapping for these keys.
     */
    V get(K... keys);

    /**
     * Returns a collection of all values for which the specified key is one of the mapped keys. If
     * a value is mapped for multiple sets of keys that involve the specified key, then the
     * collection contains this value for every set of of keys that involves the specified key.
     * 
     * @param keys
     *            the keys whose associated value is to be returned
     * @return a collection of all values for which the specified key is one of the mapped keys
     */
    Collection<V> get(K key);
}
