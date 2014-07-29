package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecursiveHashMap<K, V> implements RecursiveMap<K, V> {
    private Map<K, RecursiveMap<K, V>> key2NextRecursiveMapMap;
    private Map<K, V> key2ValueMap;
    private ValueCreatorAndLinker<K, V> valueCreator;

    public RecursiveHashMap(final ValueCreatorAndLinker<K, V> valueCreator) {
        this.key2ValueMap = new HashMap<K, V>();
        this.valueCreator = valueCreator;
    }

    @Override
    public V putWhereNecessary(final List<K> keys) {
        K firstKey = getFirstKeyToPutOrGet(keys, "put");
        V valueForSecondKey = null;
        if (keys.size() > 1) {
            RecursiveMap<K, V> recursiveMapForFirstKey = getValueAndLazilyCreateMapMapIfNecessary(firstKey);
            if (recursiveMapForFirstKey == null) {
                recursiveMapForFirstKey = new RecursiveHashMap<K, V>(this.valueCreator);
                this.key2NextRecursiveMapMap.put(firstKey, recursiveMapForFirstKey);
            }
            // first, put the last n-1 keys recursively where needed
            valueForSecondKey = recursiveMapForFirstKey.putWhereNecessary(keys.subList(1, keys.size()));
        }
        // second, put the first key if needed
        V valueForFirstKey = this.key2ValueMap.get(firstKey);
        if (valueForFirstKey == null) {
            valueForFirstKey = this.valueCreator.createNewValueForKey(firstKey, valueForSecondKey);
            this.key2ValueMap.put(firstKey, valueForFirstKey);
        } else {
            this.valueCreator.linkSubsequentValuesIfNecessary(valueForFirstKey, valueForSecondKey);
        }
        return valueForFirstKey;
    }

    private RecursiveMap<K, V> getValueAndLazilyCreateMapMapIfNecessary(final K key) {
        if (this.key2NextRecursiveMapMap == null) {
            this.key2NextRecursiveMapMap = new HashMap<K, RecursiveMap<K, V>>();
        }
        return this.key2NextRecursiveMapMap.get(key);
    }

    @Override
    public List<V> getValuesAsLongAsPossible(final List<K> keys) {
        K firstKey = getFirstKeyToPutOrGet(keys, "get");
        RecursiveMap<K, V> recursiveMapForFirstKey = getValueAndLazilyCreateMapMapIfNecessary(firstKey);
        V valueForFirstKey = this.key2ValueMap.get(firstKey);
        if (valueForFirstKey == null) {
            if (recursiveMapForFirstKey == null) {
                // do not return the unmodifiable Collections.emptyList() because segments might be
                // prepended, what would cause an UnsupportedOperationException
                return new LinkedList<V>();
            } else {
                throw new IllegalStateException("The key '" + firstKey
                        + "' is not mapped to a value but to another RecursiveMap '" + recursiveMapForFirstKey
                        + "' in the RecursiveMap '" + this + "'!");
            }
        } else {
            if (recursiveMapForFirstKey == null) {
                LinkedList<V> mutableSingleValueList = new LinkedList<V>();
                mutableSingleValueList.add(valueForFirstKey);
                return mutableSingleValueList;
            } else {
                List<V> resultValueList;
                // first, get recursively the values for the second and all following keys
                if (keys.size() > 1) {
                    resultValueList = recursiveMapForFirstKey.getValuesAsLongAsPossible(keys.subList(1, keys.size()));
                } else {
                    resultValueList = new LinkedList<V>();
                }
                // next, prepend the value for the first key to the list
                resultValueList.add(0, valueForFirstKey);
                return resultValueList;
            }
        }

    }

    private K getFirstKeyToPutOrGet(final List<K> keys, final String putOrGet) {
        if (keys != null) {
            if (keys.size() > 0) {
                return keys.get(0);
            } else {
                throw new IllegalArgumentException("The empty key list '" + keys + "' cannot be used to " + putOrGet
                        + " something!");
            }
        } else {
            throw new IllegalArgumentException("A null key list cannot be used to " + putOrGet + " something'!");
        }
    }

    @Override
    public V getLastValue(final List<K> keys) {
        // TODO may write own get method that does not build the complete list if neccessary (due to
        // performance issues)
        List<V> values = getValuesAsLongAsPossible(keys);
        return values.get(values.size() - 1);
    }

    @Override
    public void updateLeafKey(final V leafValue, final List<K> currentKeys, final K newLeafKey) {
        Iterator<K> currentKeysIterator = currentKeys.iterator();
        if (currentKeysIterator.hasNext()) {
            K pivotKey = currentKeysIterator.next();
            RecursiveMap<K, V> pivotMap = this;
            V pivotValue = pivotMap.get(pivotKey);
            while (currentKeysIterator.hasNext() && pivotValue != leafValue) {
                // get the current map for the last key
                pivotMap = pivotMap.getNextMap(pivotKey);
                // get the current key
                pivotKey = currentKeysIterator.next();
                // get the value for the current key
                pivotValue = pivotMap.get(pivotKey);
            }
            // check if we did not even enter the loop
            if (pivotValue != null && pivotValue.equals(leafValue)) {
                K lastKey = currentKeys.get(currentKeys.size() - 1);
                if (lastKey.equals(pivotKey)) {
                    pivotMap.replaceKey(pivotKey, newLeafKey);
                } else {
                    throw new IllegalArgumentException("The key '" + lastKey
                            + "' is not the last key in the key list '" + currentKeys + "'!");
                }
            } else {
                throw new IllegalArgumentException("The value '" + leafValue
                        + "' is not a leaf for the tree induced by the keys '" + currentKeys + "'!");
            }
        } else {
            throw new IllegalArgumentException("Cannot update the leaf key for the empty key list '" + currentKeys
                    + "'!");
        }
    }

    @Override
    public RecursiveMap<K, V> getNextMap(final K key) {
        return this.key2NextRecursiveMapMap.get(key);
    }

    @Override
    public V get(final K key) {
        return this.key2ValueMap.get(key);
    }

    @Override
    public void replaceKey(final K oldKey, final K newKey) {
        RecursiveMap<K, V> currentMap = this.key2NextRecursiveMapMap.remove(oldKey);
        if (currentMap != null) {
            this.key2NextRecursiveMapMap.put(newKey, currentMap);
        }
        V currentValue = this.key2ValueMap.remove(oldKey);
        if (currentValue != null) {
            this.key2ValueMap.put(newKey, currentValue);
        }
        if (currentMap == null && currentValue == null) {
            throw new IllegalArgumentException("The key '" + oldKey + "' cannot be replaced with '" + newKey
                    + "' in the " + this.getClass().getSimpleName()
                    + " because it is neither mapped to a next map nor a value!");
        }
    }
}
