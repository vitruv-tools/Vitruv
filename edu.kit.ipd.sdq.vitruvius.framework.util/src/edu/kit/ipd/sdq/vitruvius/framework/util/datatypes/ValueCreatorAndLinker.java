package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

interface ValueCreatorAndLinker<K, V> {
    /**
     * Creates a new value for the specified key based on the specified value for the next key in a
     * backward linked list.
     * 
     * @param key
     *            a key to be used for value creation
     * @param valueForNextKey
     *            value for the next key in a backward linked list
     * @return the new value
     */
    V createNewValueForKey(K key, V valueForNextKey);

    /**
     * Links the specified {@link secondValue} to the specified {@last firstValue} in a backward
     * linked list if it has no predecessor. Throws a {@link IllegalArgumentException} if the
     * {@link secondValue} is {@link null} or has another predecessor than the specified one.
     * 
     * @param firstValue
     *            the predecessor
     * @param secondValue
     *            the successor
     */
    void linkSubsequentValuesIfNecessary(V firstValue, V secondValue);

    /**
     * Links the specified {@link secondValue} to the specified {@last firstValue} in a backward
     * linked list and overrides a possibly different predecessor. Throws a
     * {@link IllegalArgumentException} if the {@link secondValue} is {@link null}.
     * 
     * @param firstValue
     *            the predecessor
     * @param secondValue
     *            the successor
     */
    void linkSubsequentValuesAndOverride(V firstValue, V secondValue);
}