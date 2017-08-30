package tools.vitruv.extensions.dslsruntime.mappings.interfaces

/**
 * Interface used in reactions that are generated for mappings that are defined using the mappings language.
 * It is used to obtain runtime information about current instances of a mapping and tuples of elements
 * that may become such instances and are therefore called candidates.
 */
interface IMappingRegistry4Reactions<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> {
	def Iterable<L> getLeftCandidatesClone();
	def Iterable<R> getRightCandidatesClone();
	def Iterable<L> getLeftInstanceHalvesClone();
	def Iterable<R> getRightInstanceHalvesClone();
}