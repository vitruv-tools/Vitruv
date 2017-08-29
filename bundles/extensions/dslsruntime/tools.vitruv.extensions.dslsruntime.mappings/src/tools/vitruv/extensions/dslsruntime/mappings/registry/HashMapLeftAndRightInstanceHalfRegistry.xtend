package tools.vitruv.extensions.dslsruntime.mappings.registry

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject

class HashMapLeftAndRightInstanceHalfRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> implements ILeftAndRightInstanceHalvesRegistry<L,R>, CandidatesAndInstanceHalves4ReactionsRegistry<L,R> {
	val HashMapCandidatesAndInstanceHalvesRegistry<L> left
	val HashMapCandidatesAndInstanceHalvesRegistry<R> right
	
	new(String mappingName) {
		this.left = new HashMapCandidatesAndInstanceHalvesRegistry<L>(mappingName, "left")
		this.right = new HashMapCandidatesAndInstanceHalvesRegistry<R>(mappingName, "right")
	}
	
	/********** BEGIN DELEGATED CANDIDATE AND INSTANCE HALVES METHODS **********/
	override Iterable<L> getLeftCandidatesClone() {
		return left.candidatesClone
	}
			
	override Iterable<R> getRightCandidatesClone() {
		return right.candidatesClone
	}
	
	override L getLeftCandidateForElements(List<Object> elements) {
		return left.getCandidateForElements(elements)
	}
	
	override R getRightCandidateForElements(List<Object> elements) {
		return right.getCandidateForElements(elements)
	}
	
	override void addLeftCandidates(Iterable<L> candidates) {
		left.addCandidates(candidates)
	}
	
	override void addRightCandidates(Iterable<R> candidates) {
		right.addCandidates(candidates)
	}
	
	override void promoteLeftCandidateToInstanceHalf(L candidate) {
		left.promoteCandidateToInstanceHalf(candidate)
	}
	
	override void promoteRightCandidateToInstanceHalf(R candidate) {
		right.promoteCandidateToInstanceHalf(candidate)
	}
	
	override Iterable<L> getLeftInstanceHalvesClone() {
		return left.instanceHalvesClone
	}
	
	override Iterable<R> getRightInstanceHalvesClone() {
		return right.instanceHalvesClone
	}
	
	override L getLeftInstanceHalfForElements(List<Object> elements) {
		return left.getInstanceHalfForElements(elements)
	}
	
	override R getRightInstanceHalfForElements(List<Object> elements) {
		return right.getInstanceHalfForElements(elements)
	}
	
	override Set<L> getLeftCandidatesAndInstances() {
		return left.candidatesAndInstances
	}
	
	override Set<R> getRightCandidatesAndInstances() {
		return right.candidatesAndInstances
	}
	
	override void removeLeftInstanceHalf(L instance) {
		left.removeInstanceHalf(instance)
	}
	
    override void removeRightInstanceHalf(R instance) {
		right.removeInstanceHalf(instance)
	}
	
	override void removeLeftCandidatesForElement(EObject element) {
		left.removeCandidatesForElement(element)
	}
	
	override void removeRightCandidatesForElement(EObject element) {
		right.removeCandidatesForElement(element)
	}
}