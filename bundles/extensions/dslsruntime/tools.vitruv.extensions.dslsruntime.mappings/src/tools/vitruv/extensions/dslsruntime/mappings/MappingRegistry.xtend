package tools.vitruv.extensions.dslsruntime.mappings

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject

class MappingRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> extends AbstractElementRuntime implements MappingRuntime<L,R> {
	val MappingRegistryHalf<L> left
	val MappingRegistryHalf<R> right
	
	new(String mappingName) {
		super(mappingName)
		this.left = new MappingRegistryHalf<L>(mappingName, "left")
		this.right = new MappingRegistryHalf<R>(mappingName, "right")
	}
	
	/********** BEGIN COMBINED REMOVE METHODS **********/
	def <C extends EObject> void removeLeftCandidatesForElement(Class<C> clazz, C element) {
		removeElement(clazz, element)
		left.removeCandidatesForElement(element)
	}
	
	def <C extends EObject> void removeRightCandidatesForElement(Class<C> clazz, C element) {
		removeElement(clazz, element)
		right.removeCandidatesForElement(element)
	}
	
	def void removeInvalidatedInstanceHalves(List<Object> leftElements, List<Object> rightElements) {
		val leftInstanceHalf = left.getInstanceHalfForElements(leftElements)
		val rightInstanceHalf = right.getInstanceHalfForElements(rightElements)
		left.removeInstanceHalf(leftInstanceHalf)
		right.removeInstanceHalf(rightInstanceHalf)
	}
	
	def void promoteValidatedCandidatesToInstances(List<Object> leftElements, List<Object> rightElements) {
		val leftCandidate = left.getCandidateForElements(leftElements)
		val rightCandidate = right.getCandidateForElements(rightElements)
		left.promoteCandidateToInstanceHalf(leftCandidate)
		right.promoteCandidateToInstanceHalf(rightCandidate)
	}

	/********** BEGIN DELEGATED CANDIDATE AND INSTANCE HALVES METHODS **********/
	override Iterable<L> getLeftCandidates() {
		return left.candidates.clone
	}
			
	override Iterable<R> getRightCandidates() {
		return right.candidates.clone
	}
	
	override void addLeftCandidates(Iterable<L> candidates) {
		left.addCandidates(candidates)
	}
	
	override void addRightCandidates(Iterable<R> candidates) {
		right.addCandidates(candidates)
	}
	
	override Iterable<L> getLeftInstances() {
		return left.instanceHalves.clone
	}
	
	override Iterable<R> getRightInstances() {
		return right.instanceHalves.clone
	}
	
	override L getLeftInstanceHalf(List<Object> elements) {
		return left.getInstanceHalfForElements(elements)
	}
	
	override R getRightInstanceHalf(List<Object> elements) {
		return right.getInstanceHalfForElements(elements)
	}
	
	override Set<L> getLeftCandidatesAndInstances() {
		return left.candidatesAndInstances
	}
	
	override Set<R> getRightCandidatesAndInstances() {
		return right.candidatesAndInstances
	}
	
//	def promoteLeftCandidateToInstance(L candidate) {
//		left.promoteCandidateToInstanceHalf(candidate)
//	}
//	
//	def promoteRightCandidateToInstance(R candidate) {
//		right.promoteCandidateToInstanceHalf(candidate)
//	}
	
	def addLeftInstanceHalf(L instance) {
		left.addInstanceHalf(instance)
	}
	
	def addRightInstanceHalf(R instance) {
		right.addInstanceHalf(instance)
	}
	
	def void removeLeftInstanceHalf(L instance) {
		left.removeInstanceHalf(instance)
	}
	
    def void removeRightInstanceHalf(R instance) {
		right.removeInstanceHalf(instance)
	}	
}