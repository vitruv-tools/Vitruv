package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.Map;


public interface ClaimableMap<K, V> extends Map<K, V> {
	void claimKeyIsMapped(K key);
	V claimValueForKey(K key);
	void putClaimingNullOrSameMapped(K key, V value);
}
