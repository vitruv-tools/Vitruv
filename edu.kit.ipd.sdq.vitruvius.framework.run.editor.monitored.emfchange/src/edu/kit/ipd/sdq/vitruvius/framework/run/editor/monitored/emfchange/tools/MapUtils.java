package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link MapUtils} contains utility functions for Java {@link Map}s.
 */
public final class MapUtils {
    private MapUtils() {
    }

    /**
     * Adds an element to a map associating keys with {@link Set}s of values.
     * 
     * @param key
     *            The key value. If the key is new, a new {@link HashSet} containing the given value
     *            is inserted into the map; Otherwise, the given value is added to the set
     *            associated with the given key.
     * @param value
     *            The value to be inserted.
     * @param map
     *            The map in which the key/value pair needs to be inserted.
     * 
     * @param <U>
     *            The key type.
     * @param <V>
     *            The value type.
     */
    public static <U, V> void addToSetMap(U key, V value, Map<U, Set<V>> map) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<V>());
        }
        map.get(key).add(value);
    }

    /**
     * Adds a value to a map containing lists of values. If the map does not contain a list as the
     * value for a given key, a new ArrayList gets associated with the key prior to inserting the
     * value.
     * 
     * @param key
     *            The key.
     * @param value
     *            The value.
     * @param target
     *            The target map.
     * @param <K>
     *            The key type.
     * @param <V>
     *            The value type.
     */
    public static <K, V> void addToMap(K key, V value, Map<K, List<V>> target) {
        if (!target.containsKey(key)) {
            target.put(key, new ArrayList<V>());
        }
        target.get(key).add(value);
    }

}
