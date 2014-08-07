package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

public interface ClaimableConcatMap<K, V> extends ConcatMap<K, V> {
	void claimKeysAreMapped(K... keys);
	V claimValueForKeys(K... keys);
	void putClaimingNullOrSameMapped(V value, K... keys);
}
