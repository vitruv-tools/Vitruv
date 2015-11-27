package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import java.util.List
import java.util.ArrayList

class MutatingListsFixingClaimableHashMap<K,V> extends ClaimableHashMap<K,V> {
	
	override get(Object key) {
		val newKey = fixMutatingList(key)
		return super.get(newKey)
	}
	
	override put(K key, V value) {
		val newKey = fixMutatingList(key)
		val newValue = fixMutatingList(value)
		super.put(newKey, newValue)
	}
	
	override remove(Object key) {
		val newKey = fixMutatingList(key)
		super.remove(newKey)
	}
	
	def private <T> T fixMutatingList(T object) {
		if (object instanceof List<?>) {
			val list = object as List<?>
			val List<Object> fixedList = new ArrayList<Object>(list.size)
			for (o : list) {
				fixedList.add(o)
			}
			return fixedList as T
		}
		return object
	}
}