package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.Map;

/**
 * A extension of an ordinary {@link java.util.Map<K,V>} that provides additional methods that throw
 * {@link java.lang.RuntimeException}s if the mapping is not as assumed.
 * 
 * @author kramerm
 * 
 * @param <K>
 * @param <V>
 */
public interface ClaimableMap<K, V> extends Map<K, V> {
    /**
     * Throws a {@link java.lang.RuntimeException} if the specified key is not mapped.
     * 
     * @param key
     *            the key
     */
    void claimKeyIsMapped(K key);

    /**
     * Returns the value to which the specified key is mapped, or throws a
     * {@link java.lang.RuntimeException} if the key is not mapped.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    V claimValueForKey(K key);

    /**
     * Associates the specified value with the specified key in this map. If the map previously
     * contained a non-null mapping to another value than the specified value, then a
     * {@link java.lang.RuntimeException} is thrown.
     * 
     * @param key
     *            the key with which the specified value is to be associated
     * @param value
     *            the value to be associated with the specified key
     */
    void putClaimingNullOrSameMapped(K key, V value);
}
