package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class MutatingListsFixingClaimableHashMap<K extends Object, V extends Object> extends ClaimableHashMap<K, V> {
  @Override
  public V get(final Object key) {
    final Object newKey = this.<Object>fixMutatingList(key);
    return super.get(newKey);
  }
  
  @Override
  public V put(final K key, final V value) {
    V _xblockexpression = null;
    {
      final K newKey = this.<K>fixMutatingList(key);
      final V newValue = this.<V>fixMutatingList(value);
      _xblockexpression = super.put(newKey, newValue);
    }
    return _xblockexpression;
  }
  
  @Override
  public V remove(final Object key) {
    V _xblockexpression = null;
    {
      final Object newKey = this.<Object>fixMutatingList(key);
      _xblockexpression = super.remove(newKey);
    }
    return _xblockexpression;
  }
  
  private <T extends Object> T fixMutatingList(final T object) {
    if ((object instanceof List<?>)) {
      final List<?> list = ((List<?>) object);
      int _size = list.size();
      final List<Object> fixedList = new ArrayList<Object>(_size);
      for (final Object o : list) {
        fixedList.add(o);
      }
      return ((T) fixedList);
    }
    return object;
  }
}
