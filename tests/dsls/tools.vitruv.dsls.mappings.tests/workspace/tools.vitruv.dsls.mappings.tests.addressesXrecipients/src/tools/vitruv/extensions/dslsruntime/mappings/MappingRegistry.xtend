package tools.vitruv.extensions.dslsruntime.mappings

import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import com.google.common.collect.Sets
import java.util.Iterator
import java.util.List
import java.util.Set

class MappingRegistry {
	val Mapping mapping
	val SetMultimap<Class<?>, Object> elementsMap = HashMultimap.create()
	val List<Set<Object>> leftCandidates = newArrayList()
	val List<Set<Object>> rightCandidates = newArrayList()
	val List<Set<Object>> leftInstances = newArrayList()
	val List<Set<Object>> rightInstances = newArrayList()
	
	new(Mapping mapping) {
		this.mapping = mapping
	}
	
	def <T> Iterable<Set<T>> cartesianProduct(Set<? extends T>... sets) {
		return Sets.cartesianProduct(sets).map[it.toSet]
	}
	
	/********** BEGIN COMBINED REMOVE METHODS **********/
	def <T> void removeLeftElementAndCandidatesAndInstances(Class<T> clazz, T element) {
		removeElement(clazz, element)
		removeLeftCandidates(element)
		removeLeftInstances(element)
	}
	
	def <T> void removeRightElementAndCandidatesAndInstances(Class<T> clazz, T element) {
		removeElement(clazz, element)
		removeRightCandidates(element)
		removeRightInstances(element)
	}
	
	/********** BEGIN ELEMENT METHODS **********/
	def <T> Set<?> getElements(Class<T> clazz) {
		return elementsMap.get(clazz)
	}
	
	def <T> void addElement(Class<T> clazz, T element) {
		val alreadyMapped = elementsMap.put(clazz, element)
		if (alreadyMapped) {
			throw new IllegalStateException('''Cannot register the element '«element»' for the mapping '«this.mapping»'
			and the class '«clazz»' because it is already registered for them!''')
		}
	}
	
	private def <T> void removeElement(Class<T> clazz, T element) {
		val wasMapped = elementsMap.remove(clazz, element)
		if (!wasMapped) {
			throw new IllegalStateException('''Cannot deregister the element '«element»' for the mapping '«mapping»'
			and the class '«clazz»' because it is not registered for them!''')
		}
	}
	
	/********** BEGIN CANDIDATE METHODS **********/
	def Iterable<Set<Object>> getLeftCandidates() {
		return leftCandidates
	}
			
	def Iterable<Set<Object>> getRightCandidates() {
		return rightCandidates
	}
	
	def addLeftCandidates(Iterable<Set<Object>> newCandidates) {
		leftCandidates.addAll(newCandidates)
	}
	
	def addRightCandidates(Iterable<Set<Object>> newCandidates) {
		rightCandidates.addAll(newCandidates)
	}
	
	private def removeLeftCandidates(Object object) {
		val iterator = leftCandidates.iterator()
		removeCandidates(iterator, object)
	}
	
	private def removeRightCandidates(Object object) {
		val iterator = rightCandidates.iterator()
		removeCandidates(iterator, object)
	}
	
	private def removeCandidates(Iterator<Set<Object>> iterator, Object object) {
		while (iterator.hasNext()) {
			val candidate = iterator.next()
			if (candidate.contains(object)) {
				iterator.remove()
			}
		}
	}

	/********** BEGIN INSTANCE METHODS **********/
	def Iterable<Set<Object>> getLeftInstances() {
		return leftInstances
	}
	
	def Iterable<Set<Object>> getRightInstances() {
		return rightInstances
	}
	
//	def void addLeftInstance(Address a) {
//		// FIXME MK
//	}
//	
//	def void addRightInstance(Recipient r, Location l, City c) {
//		// FIXME MK
//	}
//	
	def void removeLeftInstances(Object element) {
		// FIXME MK
	}
	
	def void removeRightInstances(Object element) {
		// FIXME MK
	}
}