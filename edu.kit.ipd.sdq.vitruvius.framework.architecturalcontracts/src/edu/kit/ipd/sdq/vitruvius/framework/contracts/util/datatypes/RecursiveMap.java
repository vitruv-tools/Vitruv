package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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

    void mergeLeafIntoAnother(V originLeaf, List<K> originValueList, V destinationLeaf, List<K> destinationValueList);

    RecursiveMap<K, V> getNextMap(K key);

    V get(K key);

    void replaceKey(K oldKey, K newKey);

    Pair<RecursiveMap<K, V>, V> remove(K key);

    /**
     * Adds all entries of the specified map to the next map for the specified key and returns this
     * next map with old and new entries.
     * 
     * @param key
     *            a key for which entries are to be added
     * @param recursiveMap
     *            a recursive maps with entries to be added
     * @return the next map for the specified key with old and new entries
     */
    RecursiveMap<K, V> addToNextMap(K key, V value, RecursiveMap<K, V> recursiveMap);

    /**
     * Adds all entries of the specified map to this map.
     * 
     * @param recursiveMap
     *            a recursive maps with entries to be added
     */
    void addToThisMap(V value, RecursiveMap<K, V> recursiveMap);

    Pair<Set<Entry<K, RecursiveMap<K, V>>>, Set<Entry<K, V>>> entrySets();

    Collection<V> leafValues();
}
