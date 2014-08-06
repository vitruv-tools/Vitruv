package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

interface ValueCreatorAndLinker<K, V> {
    V createNewValueForKey(K key, V valueForNextKey);

    void linkSubsequentValuesIfNecessary(V firstValue, V secondValue);

    void linkSubsequentValuesAndOverride(V firstValue, V secondValue);
}