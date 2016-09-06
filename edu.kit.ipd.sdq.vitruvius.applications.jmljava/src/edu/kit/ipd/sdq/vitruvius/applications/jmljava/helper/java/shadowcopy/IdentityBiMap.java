package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Implementation of the BiMap, which uses the object identity instead of equals to match objects.
 * 
 * @author Stephan Seifermann
 *
 * @param <K>
 *            The key type for the BiMap.
 * @param <V>
 *            The value type for the BiMap.
 */
public class IdentityBiMap<K, V> implements BiMap<K, V> {

    private final Map<K, V> regular = new IdentityHashMap<K, V>();
    private IdentityBiMap<V, K> inverse;

    /**
     * Factory method for creating a BiMap.
     * 
     * @param <K>
     *            The type of the key.
     * @param <V>
     *            The type of the value.
     * @return The created empty BiMap.
     */
    public static <K, V> BiMap<K, V> create() {
        IdentityBiMap<K, V> map = new IdentityBiMap<K, V>();
        IdentityBiMap<V, K> inverse = new IdentityBiMap<V, K>();
        map.setInverse(inverse);
        inverse.setInverse(map);
        return map;
    }

    /**
     * Constructor. It is forbidden to instantiate the BiMap directly since we have to link two
     * BiMaps in order to provide the wanted functionality.
     */
    protected IdentityBiMap() {
    }

    /**
     * Sets the inverse attribute of this BiMap. This is used for internal purposes only and should
     * never be called outside a factory method.
     * 
     * @param inverse
     *            The inverse BiMap.
     */
    private void setInverse(IdentityBiMap<V, K> inverse) {
        this.inverse = inverse;
    }

    @Override
    public int size() {
        return regular.size();
    }

    @Override
    public boolean isEmpty() {
        return regular.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return regular.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return regular.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return regular.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (regular.containsKey(key)) {
            throw new IllegalArgumentException("Key " + key + " already exists in map.");
        }

        V tmp = regular.put(key, value);
        inverse.putInverseAlreadyProcessed(value, key);
        return tmp;
    }

    /**
     * Adds an entry to the map without doing this for the inverse map too. This can be used if the
     * inverse map is already updated.
     * 
     * @param key The key for the entry to update.
     * @param value The value for the entry to update.
     */
    private void putInverseAlreadyProcessed(K key, V value) {
        regular.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if (regular.containsKey(key)) {
            V tmp = regular.remove(key);
            inverse.remove(regular.get(key));
            return tmp;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (!containsAll(m.keySet())) {
            BiMap<K, V> a = HashBiMap.create(m);
            regular.putAll(m);
            inverse.putAll(a.inverse());
        }
    }

    /**
     * Checks whether the map contains all of the keys in the given set.
     * @param keys The key set.
     * @return True if the map contains all keys from the key set.
     */
    private boolean containsAll(Set<? extends K> keys) {
        for (K key : keys) {
            if (!regular.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        if (regular.size() > 0) {
            regular.clear();
            inverse.clear();
        }
    }

    @Override
    public Set<K> keySet() {
        return regular.keySet();
    }

    @Override
    public Set<V> values() {
        return new HashSet<V>(regular.values());
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return regular.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return regular.equals(o);
    }

    @Override
    public int hashCode() {
        return regular.hashCode();
    }

    @Override
    public V forcePut(K key, V value) {
        if (regular.containsKey(key)) {
            remove(key);
        }
        return put(key, value);
    }

    @Override
    public BiMap<V, K> inverse() {
        return inverse;
    }

}
