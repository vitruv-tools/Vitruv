package tools.vitruv.extensions.dslsruntime.mappings

import com.google.common.collect.HashBasedTable
import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import com.google.common.collect.Sets
import com.google.common.collect.Table
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject

class MappingRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf, I extends MappingInstance<L,R>> {
	val String mappingName
	val SetMultimap<Class<?>, EObject> elementsMap
	val Table<List<EObject>,List<EObject>,I> instances
	val MappingRegistryHalf<L> left
	val MappingRegistryHalf<R> right
	
	new(String mappingName) {
		this.mappingName = mappingName
		this.elementsMap = HashMultimap.create()
		this.instances = HashBasedTable.create()
		this.left = new MappingRegistryHalf<L>(mappingName, "left")
		this.right = new MappingRegistryHalf<R>(mappingName, "right")
	}
	
	/**
	 * Returns the cartesian product of the given sets. Used by generated Mapping code for computing all mapping candidate combinations.
	 */
	def <T> Iterable<List<T>> cartesianProduct(Set<? extends T>... sets) {
		return Sets.cartesianProduct(sets)
	}
	
	/********** BEGIN ELEMENT METHODS **********/
	def <C> Set<?> getElements(Class<C> clazz) {
		return elementsMap.get(clazz)
	}
	
	def <C extends EObject> void addElement(Class<C> clazz, C element) {
		val elementIsNew = elementsMap.put(clazz, element)
		if (!elementIsNew) {
			throw new IllegalStateException('''Cannot register the element '«element»' for the mapping '«mappingName»'
			and the class '«clazz»' because it is already registered for them!''')
		}
	}
	
	private def <C> void removeElement(Class<C> clazz, C element) {
		val wasMapped = elementsMap.remove(clazz, element)
		if (!wasMapped) {
			throw new IllegalStateException('''Cannot deregister the element '«element»' for the mapping '«mappingName»'
			and the class '«clazz»' because it is not registered for them!''')
		}
	}
		
	/********** BEGIN COMBINED REMOVE METHODS **********/
	def <C extends EObject> void removeLeftElementCandidatesHalvesAndFullInstances(Class<C> clazz, C element) {
		removeElement(clazz, element)
		val removedLeftInstances = left.removeCandidatesAndInstancesHalvesForElement(clazz, element)
		removedLeftInstances.forEach[removeFullInstancesForLeftInstance(it)]
	}
	
	def <C extends EObject> void removeRightElementCandidatesHalvesAndFullInstances(Class<C> clazz, C element) {
		removeElement(clazz, element)
		val removedRightInstances = right.removeCandidatesAndInstancesHalvesForElement(clazz, element)
		removedRightInstances.forEach[removeFullInstancesForRightInstance(it)]
	}
	
	/********** BEGIN FULL INSTANCE METHODS **********/
	def void addFullInstance(I instance) {
		val previouslyRegisteredInstance = instances.put(instance.leftHalf.getElements(), instance.rightHalf.getElements(), instance)
		if (previouslyRegisteredInstance !== null) {
			throw new IllegalStateException(getDeregisterInstanceMessage(instance, previouslyRegisteredInstance))
		}
	}
	
	private def String getDeregisterInstanceMessage(I instance, I registeredInstance) '''Cannot deregister the mapping instance '«instance»' for the mapping '«mappingName»' 
			for the left elements '«instance.leftHalf.getElements()»' and the right elements '«instance.rightHalf.getElements()»' 
			because the mapping instance '«registeredInstance»' is registered for them!'''
	
	def I getFullInstance(List<Object> leftElements, List<Object> rightElements) {
		return instances.get(leftElements, rightElements)
	}
	
	def removeFullInstancesForLeftInstance(L leftInstanceHalf) {
		val rightElements2FullInstancesMap = instances.row(leftInstanceHalf.elements)
		for (rightElements : rightElements2FullInstancesMap.keySet) {
			instances.remove(leftInstanceHalf.elements, rightElements)
		}
	}
	
	def removeFullInstancesForRightInstance(R rightInstanceHalf) {
		val leftElements2FullInstancesMap = instances.column(rightInstanceHalf.elements)
		for (leftElements : leftElements2FullInstancesMap.keySet) {
			instances.remove(leftElements, rightInstanceHalf.elements)
		}
	}
	
	/********** BEGIN DELEGATED CANDIDATE AND INSTANCE HALVES METHODS **********/
	def Set<L> getLeftCandidates() {
		return left.candidates
	}
			
	def Set<R> getRightCandidates() {
		return right.candidates
	}
	
	def void addLeftCandidates(Iterable<L> candidates) {
		left.addCandidates(candidates)
	}
	
	def void addRightCandidates(Iterable<R> candidates) {
		right.addCandidates(candidates)
	}
	
	def Iterable<L> getLeftInstances() {
		return left.instanceHalves
	}
	
	def Iterable<R> getRightInstances() {
		return right.instanceHalves
	}
	
	def L getLeftInstance(List<Object> elements) {
		return left.getInstanceHalfForElements(elements)
	}
	
	def R getRightInstance(List<Object> elements) {
		return right.getInstanceHalfForElements(elements)
	}
	
	def promoteLeftCandidateToInstance(L candidate) {
		left.promoteCandidateToInstanceHalf(candidate)
	}
	
	def promoteRightCandidateToInstance(R candidate) {
		right.promoteCandidateToInstanceHalf(candidate)
	}
	
	def addLeftInstance(L instance) {
		left.addInstanceHalf(instance)
	}
	
	def addRightInstance(R instance) {
		right.addInstanceHalf(instance)
	}
	
	def void removeLeftInstance(L instance) {
		left.removeInstanceHalf(instance)
	}
	
    def void removeRightInstance(R instance) {
		right.removeInstanceHalf(instance)
	}
}