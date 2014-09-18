package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A map that recursively maps {@link K keys} to {@link V values} so that an ordered sequence of
 * keys is mapped to an (internally computed) ordered sequence of values of the same size.
 * 
 * @author kramerm
 * 
 * @param <K>
 *            keyType
 * @param <V>
 *            valueType
 */
public interface RecursiveMap<K, V> {
    /**
     * Maps the specified keys to an (internally computed) sequence of values and returns the
     * <b>first</b> mapped value.
     * 
     * @param keys
     *            an ordered sequence of keys
     * @return the <b>first</b> mapped value
     */
    V putWhereNecessary(final List<K> keys);

    /**
     * Returns a sequence that contains all values that are currently mapped for the specified
     * sequence of keys until a key is not mapped. If none of the specified keys are mapped an empty
     * list is returned.
     * 
     * @param keys
     *            an ordered sequence of keys
     * @return all values that are currently mapped for the specified sequence of keys, or an empty
     *         list
     */
    List<V> getValuesAsLongAsPossible(List<K> keys);

    /**
     * Returns the last value that is mapped for the specified sequence of keys.
     * 
     * @param keys
     *            an ordered sequence of keys
     * @return the last value that is mapped for the specified sequence of keys
     */
    V getLastValue(List<K> keys);

    /**
     * 
     * @param originLeaf
     * @param originValueList
     * @param destinationLeaf
     * @param destinationValueList
     * @return all values of the specified map that became obsolete because they existed already in
     *         this map
     */
    Collection<Pair<V,V>> mergeLeafIntoAnother(V originLeaf, List<K> originValueList, V destinationLeaf,
            List<K> destinationValueList);

    /**
     * Returns the next recursive map for the specified key.
     * 
     * @param key
     *            a key
     * @return the next recursive map for the specified key
     */
    RecursiveMap<K, V> getNextMap(K key);

    /**
     * Returns the value that is mapped to the specified key in this map.
     * 
     * @param key
     *            a key
     * @return the value mapped to the specified key
     */
    V get(K key);

    /**
     * Removes the mapping for the specified oldKey and adds a new mapping for the specified newKey
     * and an (internally computed) value.
     * 
     * @param oldKey
     *            an old key
     * @param newKey
     *            a new key
     */
    void replaceKey(K oldKey, K newKey);

    /**
     * Removes all mappings for the specified key and returns the recursive map and the value that
     * were mapped before.
     * 
     * @param key
     *            a key
     * @return the recursive map and the value that were mapped before
     */
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
    Collection<Pair<V,V>> mergeNextMap(K key, V value, RecursiveMap<K, V> recursiveMap);

    /**
     * Adds all entries of the specified map to this map and returns all values of the specified map
     * that became obsolete because they existed already in this map.
     * 
     * @param recursiveMap
     *            a recursive maps with entries to be added
     * @return all values of the specified map that became obsolete because they existed already in
     *         this map
     */
    Collection<Pair<V,V>> mergeMaps(V lastValue, RecursiveMap<K, V> mapToAdd);

    /**
     * Returns a set containing all entries of keys that are recursively mapped to another map and a
     * set containing all entries of keys that are directly mapped to a value.
     * 
     * @return a set containing all entries of keys that are recursively mapped to another map and a
     *         set containing all entries of keys that are directly mapped to a value.
     */
    Pair<Set<Entry<K, RecursiveMap<K, V>>>, Set<Entry<K, V>>> entrySets();

    /**
     * Returns all values that are directly mapped with a key in this map (excluding values that are
     * recursively mapped).
     * 
     * @return all values that are directly mapped
     */
    Collection<V> leafValues();

    /**
     * Returns all values that are directly and indirectly mapped with a key in this map and its
     * recursive maps.
     * 
     * @return all values that are directly and indirectly mapped
     */
    Collection<V> allValues();
}
