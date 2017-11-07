package tools.vitruv.extensions.dslsruntime.mappings.interfaces

/**
 * Interface for registering tuples of instances of mapped metaclasses that may or may not fulfill the conditions
 * specified in a mapping. The tuples are called mapping instantiation candidates or briefly candidates.
 */
interface ILeftAndRightCandidatesRegistry<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> {
	def void addLeftCandidates(Iterable<L> candidates);
	def void addRightCandidates(Iterable<R> candidates);
}