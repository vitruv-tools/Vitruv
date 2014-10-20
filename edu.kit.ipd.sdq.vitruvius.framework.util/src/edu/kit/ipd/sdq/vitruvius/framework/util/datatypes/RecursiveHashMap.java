package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RecursiveHashMap<K, V> implements RecursiveMap<K, V> {
    private Map<K, RecursiveMap<K, V>> key2NextRecursiveMapMap;
    private Map<K, V> key2ValueMap;
    private ValueCreatorAndLinker<K, V> valueCreatorAndLinker;

    public RecursiveHashMap(final ValueCreatorAndLinker<K, V> valueCreatorAndLinker) {
        this.key2NextRecursiveMapMap = new HashMap<K, RecursiveMap<K, V>>();
        this.key2ValueMap = new HashMap<K, V>();
        this.valueCreatorAndLinker = valueCreatorAndLinker;
    }

    @Override
    public V putWhereNecessary(final List<K> keys) {
        K firstKey = getFirstKeyToPutOrGet(keys, "put");
        V valueForSecondKey = null;
        if (keys.size() > 1) {
            RecursiveMap<K, V> recursiveMapForFirstKey = this.key2NextRecursiveMapMap.get(firstKey);
            if (recursiveMapForFirstKey == null) {
                recursiveMapForFirstKey = new RecursiveHashMap<K, V>(this.valueCreatorAndLinker);
                this.key2NextRecursiveMapMap.put(firstKey, recursiveMapForFirstKey);
            }
            // first, put the last n-1 keys recursively where needed
            valueForSecondKey = recursiveMapForFirstKey.putWhereNecessary(keys.subList(1, keys.size()));
        }
        // second, put the first key if needed
        V valueForFirstKey = this.key2ValueMap.get(firstKey);
        if (valueForFirstKey == null) {
            valueForFirstKey = this.valueCreatorAndLinker.createNewValueForKey(firstKey, valueForSecondKey);
            this.key2ValueMap.put(firstKey, valueForFirstKey);
        } else {
            if (valueForSecondKey != null) {
                this.valueCreatorAndLinker.linkSubsequentValuesIfNecessary(valueForFirstKey, valueForSecondKey);
            }
        }
        return valueForFirstKey;
    }

    @Override
    public List<V> getValuesAsLongAsPossible(final List<K> keys) {
        K firstKey = getFirstKeyToPutOrGet(keys, "get");
        RecursiveMap<K, V> recursiveMapForFirstKey = this.key2NextRecursiveMapMap.get(firstKey);
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
    public RecursiveMap<K, V> getNextMap(final K key) {
        return this.key2NextRecursiveMapMap.get(key);
    }

    @Override
    public V get(final K key) {
        return this.key2ValueMap.get(key);
    }

    private Pair<RecursiveMap<K, V>, K> getMapAndKeyForLeaf(final V leafValue, final List<K> currentKeys) {
        Iterator<K> currentKeysIterator = currentKeys.iterator();
        // perform a special first loop iteration because getNextMap() cannot be called on null
        if (currentKeysIterator.hasNext()) {
            K pivotKey = currentKeysIterator.next();
            RecursiveMap<K, V> pivotMap = this;
            V pivotValue = pivotMap.get(pivotKey);
            // now that getNextMap() can be called on pivotMap iterate over the loop
            while (currentKeysIterator.hasNext() && pivotValue != leafValue) {
                // first get the current map using the last key
                pivotMap = pivotMap.getNextMap(pivotKey);
                // now update the key to the current key
                pivotKey = currentKeysIterator.next();
                // get the value for the current key
                pivotValue = pivotMap.get(pivotKey);
            }
            // check the loop invariant
            if (pivotValue != null && pivotValue.equals(leafValue)) {
                // check whether we really got until to the last key
                K lastKey = currentKeys.get(currentKeys.size() - 1);
                if (lastKey.equals(pivotKey)) {
                    return new Pair<RecursiveMap<K, V>, K>(pivotMap, pivotKey);
                } else {
                    throw new IllegalArgumentException("The key '" + lastKey
                            + "' is not the last key in the key list '" + currentKeys + "'!");
                }
            } else {
                throw new IllegalArgumentException("The value '" + leafValue
                        + "' is not a leaf for the tree induced by the keys '" + currentKeys + "'!");
            }
        } else {
            throw new IllegalArgumentException("Cannot find a leaf key for the empty key list '" + currentKeys + "'!");
        }
    }

    @Override
    public Collection<Pair<V,V>> mergeLeafIntoAnother(final V originLeaf, final List<K> originValueList,
            final V destinationLeaf, final List<K> destinationValueList) {
        Pair<RecursiveMap<K, V>, K> mapAndKeyForOriginLeaf = getMapAndKeyForLeaf(originLeaf, originValueList);
        RecursiveMap<K, V> mapForOriginLeaf = mapAndKeyForOriginLeaf.getFirst();
        K originKey = mapAndKeyForOriginLeaf.getSecond();
        Pair<RecursiveMap<K, V>, V> removedRecursiveMapAndLeaf = mapForOriginLeaf.remove(originKey);
        RecursiveMap<K, V> removedRecursiveMap = removedRecursiveMapAndLeaf.getFirst();
        V removedLeaf = removedRecursiveMapAndLeaf.getSecond();
        if (originLeaf == removedLeaf) {
            Pair<RecursiveMap<K, V>, K> mapAndKeyForDestinationLeaf = getMapAndKeyForLeaf(destinationLeaf,
                    destinationValueList);
            RecursiveMap<K, V> mapForDestinationLeaf = mapAndKeyForDestinationLeaf.getFirst();
            K destinationKey = mapAndKeyForDestinationLeaf.getSecond();
            Collection<Pair<V,V>> updatedValues = mapForDestinationLeaf.mergeNextMap(destinationKey, destinationLeaf, removedRecursiveMap);
            updatedValues.add(new Pair<V,V>(removedLeaf,destinationLeaf));
            return updatedValues;
        } else {
            throw new IllegalStateException("mapForOriginLeaf '" + mapForOriginLeaf + "' did not contain '"
                    + originLeaf + "' but '" + removedLeaf + "!");
        }
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

    @Override
    public Pair<RecursiveMap<K, V>, V> remove(final K key) {
        RecursiveMap<K, V> removedRecursiveMap = this.key2NextRecursiveMapMap.remove(key);
        V removedValue = this.key2ValueMap.remove(key);
        return new Pair<RecursiveMap<K, V>, V>(removedRecursiveMap, removedValue);
    }

    @Override
    public Collection<Pair<V,V>> mergeNextMap(final K key, final V value, final RecursiveMap<K, V> mapToAdd) {
        RecursiveMap<K, V> mapForKey = this.key2NextRecursiveMapMap.get(key);
        if (mapForKey == null) {
            if (mapToAdd == null) {
                // do not return the unmodifiable Collections.emptyList() because segments might be
                // prepended, what would cause an UnsupportedOperationException
                return new LinkedList<Pair<V,V>>();
            } else {
                mapForKey = new RecursiveHashMap<K, V>(this.valueCreatorAndLinker);
                this.key2NextRecursiveMapMap.put(key, mapForKey);
            }
        } else if (mapToAdd == null) {
        	// nothing to merge
            // do not return the unmodifiable Collections.emptyList() because segments might be
            // prepended, what would cause an UnsupportedOperationException
        	return new LinkedList<Pair<V,V>>();
        }
        return mapForKey.mergeMaps(value, mapToAdd);
    }

    @Override
    public Collection<Pair<V,V>> mergeMaps(V previousValue, final RecursiveMap<K, V> mapToAdd) {
    	if (previousValue == null || mapToAdd == null) {
    		throw new IllegalArgumentException("Cannot merge the previous value '" + previousValue + "' and the map '" + mapToAdd + "'!"); 
    	}
        final Collection<Pair<V,V>> updatedValues = new LinkedList<Pair<V,V>>();
        Pair<Set<Map.Entry<K, RecursiveMap<K, V>>>, Set<Map.Entry<K, V>>> entrySets = mapToAdd.entrySets();
        Set<Entry<K, RecursiveMap<K, V>>> nextMapsToAddEntries = entrySets.getFirst();
        Set<Entry<K, V>> key2ValuesToAddEntries = entrySets.getSecond();
        for (Entry<K, RecursiveMap<K, V>> nextMapToAddEntry : nextMapsToAddEntries) {
            K nextKey = nextMapToAddEntry.getKey();
            RecursiveMap<K, V> nextMapToAdd = nextMapToAddEntry.getValue();
            RecursiveMap<K, V> currentMapForNextKey = this.key2NextRecursiveMapMap.get(nextKey);
            if (currentMapForNextKey == null) {
                this.key2NextRecursiveMapMap.put(nextKey, nextMapToAdd);
            } else {
            	// recursion in next map layer
            	V newPreviousValue = this.key2ValueMap.get(nextKey);
                updatedValues.addAll(currentMapForNextKey.mergeMaps(newPreviousValue, nextMapToAdd));
            }
        }
        for (Entry<K, V> key2ValueToAddEntry : key2ValuesToAddEntries) {
            K nextKeyToAdd = key2ValueToAddEntry.getKey();
            V nextValueToAdd = key2ValueToAddEntry.getValue();
            V currentValueForNextKey = this.key2ValueMap.get(nextKeyToAdd);
            if (currentValueForNextKey == null) {
                currentValueForNextKey = nextValueToAdd;
                this.key2ValueMap.put(nextKeyToAdd, nextValueToAdd);
            } else {
                RecursiveMap<K, V> currentMapForNextKey = this.key2NextRecursiveMapMap.get(nextKeyToAdd);
                if (currentMapForNextKey != null) {
                    Collection<V> leafValues = currentMapForNextKey.leafValues();
                    for (V leafValue : leafValues) {
                        this.valueCreatorAndLinker.linkSubsequentValuesAndOverride(currentValueForNextKey, leafValue);
                    }
                }
                updatedValues.add(new Pair<V,V>(nextValueToAdd,currentValueForNextKey));
            }
            this.valueCreatorAndLinker.linkSubsequentValuesAndOverride(previousValue, currentValueForNextKey);
        }
        return updatedValues;
    }

    @Override
    public Pair<Set<Entry<K, RecursiveMap<K, V>>>, Set<Entry<K, V>>> entrySets() {
        Set<Entry<K, RecursiveMap<K, V>>> nextMapsEntrySet = this.key2NextRecursiveMapMap.entrySet();
        Set<Entry<K, V>> key2ValueEntrySet = this.key2ValueMap.entrySet();
        return new Pair<Set<Entry<K, RecursiveMap<K, V>>>, Set<Entry<K, V>>>(nextMapsEntrySet, key2ValueEntrySet);
    }

    @Override
    public Collection<V> leafValues() {
        return this.key2ValueMap.values();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Set<Entry<K, RecursiveMap<K, V>>> nextMapEntrySet = this.key2NextRecursiveMapMap.entrySet();
        List<Entry<K, RecursiveMap<K, V>>> nextMapEntryList = new ArrayList<Entry<K, RecursiveMap<K, V>>>(
                nextMapEntrySet);
        Comparator<Entry<K, RecursiveMap<K, V>>> nextMapEntryComparator = new Comparator<Entry<K, RecursiveMap<K, V>>>() {

            @Override
            public int compare(final Entry<K, RecursiveMap<K, V>> arg0, final Entry<K, RecursiveMap<K, V>> arg1) {
                return arg0.getKey().toString().compareTo(arg1.getKey().toString());
            }

        };
        Collections.sort(nextMapEntryList, nextMapEntryComparator);

        for (Entry<K, RecursiveMap<K, V>> nextMapEntry : nextMapEntryList) {
            K key = nextMapEntry.getKey();
            sb.append(key);
            String s;
            V value = this.key2ValueMap.get(key);
            if (value == null) {
                s = "-";
            } else {
                s = "=(" + value + ")";
            }
            RecursiveMap<K, V> nextMap = nextMapEntry.getValue();
            sb.append(s + nextMap.toString());
        }
        Set<Entry<K, V>> key2ValueEntrySet = this.key2ValueMap.entrySet();
        for (Entry<K, V> key2ValueEntry : key2ValueEntrySet) {
            K key = key2ValueEntry.getKey();
            if (!this.key2NextRecursiveMapMap.containsKey(key)) {
                V value = key2ValueEntry.getValue();
                sb.append("_(" + value + ")\n");
            }
        }
        return sb.toString();
    }

    @Override
    public Collection<V> allValues() {
        Collection<V> values = new ArrayList<V>();
        Collection<RecursiveMap<K, V>> nextMaps = this.key2NextRecursiveMapMap.values();
        for (RecursiveMap<K, V> nextMap : nextMaps) {
            values.addAll(nextMap.allValues());
        }
        values.addAll(this.key2ValueMap.values());
        return values;
    }
}
