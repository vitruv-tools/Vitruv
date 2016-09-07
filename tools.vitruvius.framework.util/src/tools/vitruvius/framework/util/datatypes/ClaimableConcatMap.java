package tools.vitruvius.framework.util.datatypes;

public interface ClaimableConcatMap<K, V> extends ConcatMap<K, V> {
	@SuppressWarnings("unchecked")
	void claimKeysAreMapped(K... keys);
	@SuppressWarnings("unchecked")
	V claimValueForKeys(K... keys);
	@SuppressWarnings("unchecked")
	void putClaimingNullOrSameMapped(V value, K... keys);
}
