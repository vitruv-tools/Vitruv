package tools.vitruvius.framework.util.datatypes;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public abstract class ClaimableConcatHashMap<K, V, C> implements ClaimableConcatMap<K, V> {
    private ClaimableMap<C, V> map;

    public ClaimableConcatHashMap() {
        this.map = new ClaimableHashMap<C, V>();
    }

    @SuppressWarnings("unchecked")
	public abstract C getConcatenatedKey(final K... keys);

    public abstract boolean isPartOf(K partialKey, C fullKey);

    @SuppressWarnings("unchecked")
	@Override
    public V put(final V value, final K... keys) {
        C concatenatedKey = getConcatenatedKey(keys);
        return this.map.put(concatenatedKey, value);
    }

    @SuppressWarnings("unchecked")
	@Override
    public V get(final K... keys) {
        C concatenatedKey = getConcatenatedKey(keys);
        return this.map.get(concatenatedKey);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void claimKeysAreMapped(final K... keys) {
        C concatenatedKey = getConcatenatedKey(keys);
        this.map.claimKeyIsMapped(concatenatedKey);
    }

    @SuppressWarnings("unchecked")
	@Override
    public V claimValueForKeys(final K... keys) {
        C concatenatedKey = getConcatenatedKey(keys);
        return this.map.claimValueForKey(concatenatedKey);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void putClaimingNullOrSameMapped(final V value, final K... keys) {
        C concatenatedKey = getConcatenatedKey(keys);
        this.map.putClaimingNullOrSameMapped(concatenatedKey, value);
    }

    @Override
    public Collection<V> get(final K key) {
        Collection<V> values = new LinkedList<V>();
        Set<Entry<C, V>> entrySet = this.map.entrySet();
        for (Entry<C, V> entry : entrySet) {
            boolean involved = isPartOf(key, entry.getKey());
            if (involved) {
                V value = entry.getValue();
                values.add(value);
            }
        }
        return values;
    }

}
