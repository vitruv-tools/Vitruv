package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.HashMap;

public class ClaimableHashMap<K, V> extends HashMap<K, V> implements ClaimableMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void claimKeyIsMapped(K key) {
		if (!this.containsKey(key)) {
			throw new RuntimeException(getKeyViolationMsg(key));
		}
	}

	@Override
	public V claimValueForKey(K key) {
		V value = this.get(key);
		if (value == null) {
			throw new RuntimeException(getKeyViolationMsg(key));
		} else {
			return value;
		}
	}
	
	private String getKeyViolationMsg(K key) {
		return "The key '" + key + "' was claimed to be in the map '" + this + "' but was not found!";
	}

	@Override
	public void putClaimingNullOrSameMapped(K key, V value) {
		V previousValue = this.put(key, value);
		if (previousValue != null) {
			if (previousValue.equals(value)) {
				throw new RuntimeException(getNullOrSameMappedViolationMsg(key, previousValue, value));
			}
		}
	}

	private String getNullOrSameMappedViolationMsg(K key, V oldValue, V newValue) {
		return "The key '" + key + "' was claimed to be mapped in '" + this + "' to null or '" + newValue + "' but it was mapped to '" + oldValue + "'!";
	}
}
