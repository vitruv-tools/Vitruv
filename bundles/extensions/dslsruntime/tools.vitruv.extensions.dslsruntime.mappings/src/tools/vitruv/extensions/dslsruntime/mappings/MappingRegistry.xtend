package tools.vitruv.extensions.dslsruntime.mappings

import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import com.google.common.collect.Sets
import java.util.Iterator
import java.util.Set
import java.util.List

class MappingRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> {
	val Mapping<L,R> mapping
	val SetMultimap<Class<?>, Object> elementsMap = HashMultimap.create()
	val Set<L> leftCandidates = newHashSet()
	val Set<R> rightCandidates = newHashSet()
	val Set<L> leftInstances = newHashSet()
	val Set<R> rightInstances = newHashSet()
	
	new(Mapping<L,R> mapping) {
		this.mapping = mapping
	}
	
	def <T> Iterable<List<T>> cartesianProduct(Set<? extends T>... sets) {
		return Sets.cartesianProduct(sets)
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
	def Set<L> getLeftCandidates() {
		return leftCandidates
	}
			
	def Set<R> getRightCandidates() {
		return rightCandidates
	}
	
	def void addLeftCandidates(Iterable<L> candidates) {
		addHalves(leftCandidates, candidates, "left candidate")
	}
	
	def void addRightCandidates(Iterable<R> candidates) {
		addHalves(rightCandidates, candidates, "right candidate")
	}
	
	private def <T> void addHalves(Set<T> halvesRegistry, Iterable<T> halves, String halfType) {
		for (half : halves) {
			addSet(halvesRegistry, half, halfType)
		}
	}
	
	private def <T> void addSet(Set<T> halvesRegistry, T half, String halfType) {
		val halfIsNew = halvesRegistry.add(half)
		if (!halfIsNew) {
			throw new IllegalStateException('''Cannot register the «halfType» '«half»' for the mapping '«this.mapping»'
			because it is already registered!''')
		}
	}
	
	private def void removeLeftCandidates(Object object) {
		val iterator = leftCandidates.iterator()
		removeHalvesThatContainAnElement(iterator, object, "left candidates")
	}
	
	private def void removeRightCandidates(Object object) {
		val iterator = rightCandidates.iterator()
		removeHalvesThatContainAnElement(iterator, object, "right candidates")
	}
	
	private def <T extends MappingInstanceHalf> boolean removeHalvesThatContainAnElement(Iterator<T> iterator, Object element, String halfType) {
		val atLeastOneSetRemoved = removeHalvesThatContainAnElement(iterator, element)
		if (!atLeastOneSetRemoved) {
			throw new IllegalStateException('''No «halfType» to be removed are registered for the element '«element»'
			in '«iterator.toList»' of the mapping '«this.mapping»'!''')
		}
	}
	
	private def <T extends MappingInstanceHalf> boolean removeHalvesThatContainAnElement(Iterator<T> iterator, Object element) {
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
	def Iterable<L> getLeftInstances() {
		return leftInstances
	}
	
	def Iterable<R> getRightInstances() {
		return rightInstances
	}

	def void addLeftInstance(L instance) {
		addSet(leftInstances, instance, "right instance")
	}

	def void addRightInstance(R instance) {
		addSet(rightInstances, instance, "right instance")
	}
	
	def void removeLeftInstance(L instance) {
		removeSet(leftInstances, instance, "left instance")
	}
	
    def void removeRightInstance(R instance) {
		removeSet(rightInstances, instance, "right instance")
	}
	
	private def <T> void removeSet(Set<T> halvesRegistry, T half, String halfType) {
		val wasRegistered = halvesRegistry.remove(half)
		if (!wasRegistered) {
			throw new IllegalStateException('''Cannot register the «halfType» '«half»' for the mapping '«this.mapping»'
			because it is not registered!''')
		}
	}
	
	private def void removeLeftInstances(Object object) {
		val iterator = leftInstances.iterator()
		removeHalvesThatContainAnElement(iterator, object, "left instances")
	}
	
    private def void removeRightInstances(Object object) {
		val iterator = rightInstances.iterator()
		removeHalvesThatContainAnElement(iterator, object, "right instances")
	}
}