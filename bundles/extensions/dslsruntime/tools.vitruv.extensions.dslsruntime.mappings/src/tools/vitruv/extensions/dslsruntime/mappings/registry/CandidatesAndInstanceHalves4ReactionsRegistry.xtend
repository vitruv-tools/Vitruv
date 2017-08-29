package tools.vitruv.extensions.dslsruntime.mappings.registry

import java.util.Set

interface CandidatesAndInstanceHalves4ReactionsRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> {
	def Iterable<L> getLeftCandidatesClone();
	def Iterable<R> getRightCandidatesClone();
	def Iterable<L> getLeftInstanceHalvesClone();
	def Iterable<R> getRightInstanceHalvesClone();
	def Set<L> getLeftCandidatesAndInstances();
	def Set<R> getRightCandidatesAndInstances();
}