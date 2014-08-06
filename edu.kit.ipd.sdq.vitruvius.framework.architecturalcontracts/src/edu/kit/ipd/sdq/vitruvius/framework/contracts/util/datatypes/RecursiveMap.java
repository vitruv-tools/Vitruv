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

    /**
     * 
     * @param originLeaf
     * @param originValueList
     * @param destinationLeaf
     * @param destinationValueList
     * @return all values of the specified map that became obsolete because they existed already in
     *         this map
     */
    Collection<V> mergeLeafIntoAnother(V originLeaf, List<K> originValueList, V destinationLeaf,
            List<K> destinationValueList);

    RecursiveMap<K, V> getNextMap(K key);

    V get(K key);

    void replaceKey(K oldKey, K newKey);

    Pair<RecursiveMap<K, V>, V> remove(K key);

    /**
     * Adds all entries of the specified map to the next map for the specified key and returns all
     * values of the specified map that became obsolete because they existed already in this map.
     * 
     * @param key
     *            a key for which entries are to be added
     * @param recursiveMap
     *            a recursive maps with entries to be added
     * @return all values of the specified map that became obsolete because they existed already in
     *         this map
     */
    Collection<V> addToNextMap(K key, V value, RecursiveMap<K, V> recursiveMap);

    /**
     * Adds all entries of the specified map to this map and returns all values of the specified map
     * that became obsolete because they existed already in this map.
     * 
     * @param recursiveMap
     *            a recursive maps with entries to be added
     * @return all values of the specified map that became obsolete because they existed already in
     *         this map
     */
    Collection<V> addToThisMap(V lastValue, RecursiveMap<K, V> mapToAdd);

    Pair<Set<Entry<K, RecursiveMap<K, V>>>, Set<Entry<K, V>>> entrySets();

    Collection<V> leafValues();

    String toString(Integer offset);

    Collection<V> values();
}
