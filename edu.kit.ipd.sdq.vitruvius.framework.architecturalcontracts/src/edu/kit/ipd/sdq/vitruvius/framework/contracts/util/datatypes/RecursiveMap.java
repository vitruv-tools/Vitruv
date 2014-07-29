package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.List;

public interface RecursiveMap<K, V> {
    /**
     * 
     * @param keys
     * @return the FIRST value
     */
    V putWhereNecessary(final List<K> keys);

    List<V> getValuesAsLongAsPossible(List<K> keys);

    V getLastValue(List<K> keys);

    void updateLeafKey(V leafValue, List<K> currentKeys, K newLeafKey);

    RecursiveMap<K, V> getNextMap(K key);

    V get(K key);

    void replaceKey(K oldKey, K newKey);
}
