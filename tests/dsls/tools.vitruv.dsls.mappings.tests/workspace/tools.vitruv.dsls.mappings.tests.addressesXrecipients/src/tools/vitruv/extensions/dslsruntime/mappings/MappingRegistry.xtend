package tools.vitruv.extensions.dslsruntime.mappings

import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import com.google.common.collect.Sets
import java.util.Iterator
import java.util.Set

class MappingRegistry {
	val Mapping mapping
	val SetMultimap<Class<?>, Object> elementsMap = HashMultimap.create()
	val Set<Set<Object>> leftCandidates = newHashSet()
	val Set<Set<Object>> rightCandidates = newHashSet()
	val Set<Set<Object>> leftInstances = newHashSet()
	val Set<Set<Object>> rightInstances = newHashSet()
	
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
		val elementIsNew = elementsMap.put(clazz, element)
		if (!elementIsNew) {
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
	
	def void addLeftCandidates(Iterable<Set<Object>> candidates) {
		addSets(leftCandidates, candidates, "left candidate")
	}
	
	def void addRightCandidates(Iterable<Set<Object>> candidates) {
		addSets(rightCandidates, candidates, "right candidate")
	}
	
	private def void addSets(Set<Set<Object>> setsRegistry, Iterable<Set<Object>> sets, String setType) {
		for (set : sets) {
			addSet(setsRegistry, set, setType)
		}
	}
	
	private def void addSet(Set<Set<Object>> setsRegistry, Set<Object> set, String setType) {
		val setIsNew = setsRegistry.add(set)
		if (!setIsNew) {
			throw new IllegalStateException('''Cannot register the «setType» '«set»' for the mapping '«this.mapping»'
			because it is already registered!''')
		}
	}
	
	private def void removeLeftCandidates(Object object) {
		val iterator = leftCandidates.iterator()
		removeSetsThatContainAnElement(iterator, object, "left candidates")
	}
	
	private def void removeRightCandidates(Object object) {
		val iterator = rightCandidates.iterator()
		removeSetsThatContainAnElement(iterator, object, "right candidates")
	}
	
	private def boolean removeSetsThatContainAnElement(Iterator<Set<Object>> iterator, Object element, String setType) {
		val atLeastOneSetRemoved = removeSetsThatContainAnElement(iterator, element)
		if (!atLeastOneSetRemoved) {
			throw new IllegalStateException('''No «setType» to be removed are registered for the element '«element»'
			in '«iterator.toList»' of the mapping '«this.mapping»'!''')
		}
	}
	
	private def boolean removeSetsThatContainAnElement(Iterator<Set<Object>> iterator, Object element) {
		var atLeastOneSetRemoved = false
		while (iterator.hasNext()) {
			val pivot = iterator.next()
			if (pivot.contains(element)) {
				iterator.remove()
				atLeastOneSetRemoved = true
			}
		}
		return atLeastOneSetRemoved
	}

	/********** BEGIN INSTANCE METHODS **********/
	def Iterable<Set<Object>> getLeftInstances() {
		return leftInstances
	}
	
	def Iterable<Set<Object>> getRightInstances() {
		return rightInstances
	}

	def void addLeftInstance(Set<Object> instance) {
		addSet(leftInstances, instance, "right instance")
	}

	def void addRightInstance(Set<Object> instance) {
		addSet(rightInstances, instance, "right instance")
	}
	
	def void removeLeftInstance(Set<Object> instance) {
		removeSet(leftInstances, instance, "left instance")
	}
	
    def void removeRightInstance(Set<Object> instance) {
		removeSet(rightInstances, instance, "right instance")
	}
	
	private def void removeSet(Set<Set<Object>> setsRegistry, Set<Object> set, String setType) {
		val wasRegistered = setsRegistry.remove(set)
		if (!wasRegistered) {
			throw new IllegalStateException('''Cannot register the «setType» '«set»' for the mapping '«this.mapping»'
			because it is not registered!''')
		}
	}
	
	private def void removeLeftInstances(Object object) {
		val iterator = leftInstances.iterator()
		removeSetsThatContainAnElement(iterator, object, "left instances")
	}
	
    private def void removeRightInstances(Object object) {
		val iterator = rightInstances.iterator()
		removeSetsThatContainAnElement(iterator, object, "right instances")
	}
}