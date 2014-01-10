package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.Arrays;

public class ClaimableConcatHashMap<K, V> implements ClaimableConcatMap<K, V> {
	private ClaimableMap<String,V> map;

	public ClaimableConcatHashMap() {
		this.map = new ClaimableHashMap<String, V>();
	}
	
	private String getConcatenatedKey(K... keys) {
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
	public V put(V value, K... keys) {
		String concatenatedKey = getConcatenatedKey(keys);
		return this.map.put(concatenatedKey, value);
	}

	@Override
	public V get(K... keys) {
		String concatenatedKey = getConcatenatedKey(keys);
		return this.map.get(concatenatedKey);
	}

	@Override
	public void claimKeysAreMapped(K... keys) {
		String concatenatedKey = getConcatenatedKey(keys);
		this.map.claimKeyIsMapped(concatenatedKey);
	}

	@Override
	public V claimValueForKeys(K... keys) {
		String concatenatedKey = getConcatenatedKey(keys);
		return this.map.claimValueForKey(concatenatedKey);
	}

	@Override
	public void putClaimingNullOrSameMapped(V value, K... keys) {
		String concatenatedKey = getConcatenatedKey(keys);
		this.map.putClaimingNullOrSameMapped(concatenatedKey, value);
	}
	
}
