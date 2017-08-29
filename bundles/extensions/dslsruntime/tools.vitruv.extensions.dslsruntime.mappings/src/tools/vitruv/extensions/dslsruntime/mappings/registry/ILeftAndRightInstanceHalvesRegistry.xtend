package tools.vitruv.extensions.dslsruntime.mappings.registry

import java.util.List
import org.eclipse.emf.ecore.EObject

interface ILeftAndRightInstanceHalvesRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> extends CandidatesAndInstanceHalves4ReactionsRegistry<L,R> {
	
//	def Iterable<L> getLeftCandidatesClone();
//	def Iterable<R> getRightCandidatesClone();
	def L getLeftCandidateForElements(List<Object> elements);
	def R getRightCandidateForElements(List<Object> elements);
	def void addLeftCandidates(Iterable<L> candidates);
	def void addRightCandidates(Iterable<R> candidates);
	def void promoteLeftCandidateToInstanceHalf(L candidate);
	def void promoteRightCandidateToInstanceHalf(R candidate);
//	def Iterable<L> getLeftInstanceHalvesClone();
//	def Iterable<R> getRightInstanceHalvesClone();
	def L getLeftInstanceHalfForElements(List<Object> elements);
	def R getRightInstanceHalfForElements(List<Object> elements);
//	def Set<L> getLeftCandidatesAndInstances();
//	def Set<R> getRightCandidatesAndInstances();
	def void removeLeftInstanceHalf(L instance);
    def void removeRightInstanceHalf(R instance);
    def void removeLeftCandidatesForElement(EObject element);
    def void removeRightCandidatesForElement(EObject element);
}