package tools.vitruv.extensions.dslsruntime.mappings.impl

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry

/**
 * An object of this class keeps track of the left and the right half of every 
 * mapping instantiation and every mapping instantiation candidate for a certain mapping.
 * See also {@link HashMapCandidatesAndInstanceHalvesRegistry}
 */
class HashMapLeftAndRightInstanceHalfRegistry<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> implements IMappingRegistry<L,R> {
	val HashMapCandidatesAndInstanceHalvesRegistry<L> left
	val HashMapCandidatesAndInstanceHalvesRegistry<R> right
	
	new(String mappingName) {
		this.left = new HashMapCandidatesAndInstanceHalvesRegistry<L>(mappingName, "left")
		this.right = new HashMapCandidatesAndInstanceHalvesRegistry<R>(mappingName, "right")
	}
	
	/********** BEGIN PROTECTED METHODS **********/
	/* (only used by SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry) */
	protected def L getLeftCandidateForElements(List<Object> elements) {
		return left.getCandidateForElements(elements)
	}
	
	protected def R getRightCandidateForElements(List<Object> elements) {
		return right.getCandidateForElements(elements)
	}
	
	protected def void removeLeftCandidatesForElement(EObject element) {
		left.removeCandidatesForElement(element)
	}
	
	protected def void removeRightCandidatesForElement(EObject element) {
		right.removeCandidatesForElement(element)
	}
	
	protected def void promoteLeftCandidateToInstanceHalf(L candidate) {
		left.promoteCandidateToInstanceHalf(candidate)
	}
	
	protected def void promoteRightCandidateToInstanceHalf(R candidate) {
		right.promoteCandidateToInstanceHalf(candidate)
	}
	
	protected def L getLeftInstanceHalfForElements(List<Object> elements) {
		return left.getInstanceHalfForElements(elements)
	}
	
	protected def R getRightInstanceHalfForElements(List<Object> elements) {
		return right.getInstanceHalfForElements(elements)
	}
	
	protected def void removeLeftInstanceHalf(L instance) {
		left.removeInstanceHalf(instance)
	}
	
    protected def removeRightInstanceHalf(R instance) {
		right.removeInstanceHalf(instance)
	}
	
	/********** BEGIN DELEGATED PUBLIC METHODS **********/
	override Iterable<L> getLeftCandidatesClone() {
		return left.candidatesClone
	}
			
	override Iterable<R> getRightCandidatesClone() {
		return right.candidatesClone
	}
	
	override void addLeftCandidates(Iterable<L> candidates) {
		left.addCandidates(candidates)
	}
	
	override void addRightCandidates(Iterable<R> candidates) {
		right.addCandidates(candidates)
	}
	
	override Iterable<L> getLeftInstanceHalvesClone() {
		return left.instanceHalvesClone
	}
	
	override Iterable<R> getRightInstanceHalvesClone() {
		return right.instanceHalvesClone
	}

	override Set<L> getLeftCandidatesAndInstances() {
		return left.candidatesAndInstances
	}
	
	override Set<R> getRightCandidatesAndInstances() {
		return right.candidatesAndInstances
	}
}