package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

public interface ConcatMap<K,V> {
	V put(V value, K... keys);
	V get(K... keys);
}
