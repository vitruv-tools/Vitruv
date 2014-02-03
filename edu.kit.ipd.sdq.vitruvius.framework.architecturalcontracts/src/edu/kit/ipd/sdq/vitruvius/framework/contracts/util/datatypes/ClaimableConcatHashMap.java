package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class ClaimableConcatHashMap<K, V> implements ClaimableConcatMap<K, V> {
    private ClaimableMap<String, V> map;

    public ClaimableConcatHashMap() {
        this.map = new ClaimableHashMap<String, V>();
    }

    private String getConcatenatedKey(final K... keys) {
        if (keys.length > 0) {
            Arrays.sort(keys);
            StringBuffer concatenatedKeyBuffer = new StringBuffer();
            for (K pivot : keys) {
                concatenatedKeyBuffer.append(pivot);
            }
            return concatenatedKeyBuffer.toString();
        } else {
            throw new IllegalArgumentException("At least one key required!");
        }
    }

    @Override
    public V put(final V value, final K... keys) {
        String concatenatedKey = getConcatenatedKey(keys);
        return this.map.put(concatenatedKey, value);
    }

    @Override
    public V get(final K... keys) {
        String concatenatedKey = getConcatenatedKey(keys);
        return this.map.get(concatenatedKey);
    }

    @Override
    public void claimKeysAreMapped(final K... keys) {
        String concatenatedKey = getConcatenatedKey(keys);
        this.map.claimKeyIsMapped(concatenatedKey);
    }

    @Override
    public V claimValueForKeys(final K... keys) {
        String concatenatedKey = getConcatenatedKey(keys);
        return this.map.claimValueForKey(concatenatedKey);
    }

    @Override
    public void putClaimingNullOrSameMapped(final V value, final K... keys) {
        String concatenatedKey = getConcatenatedKey(keys);
        this.map.putClaimingNullOrSameMapped(concatenatedKey, value);
    }

    @Override
    public Collection<V> get(final K key) {
        Collection<V> values = new LinkedList<V>();
        Set<Entry<String, V>> entrySet = this.map.entrySet();
        for (Entry<String, V> entry : entrySet) {
            boolean involved = entry.getKey().contains(key.toString());
            if (involved) {
                V value = entry.getValue();
                values.add(value);
            }
        }
        return values;
    }

}
