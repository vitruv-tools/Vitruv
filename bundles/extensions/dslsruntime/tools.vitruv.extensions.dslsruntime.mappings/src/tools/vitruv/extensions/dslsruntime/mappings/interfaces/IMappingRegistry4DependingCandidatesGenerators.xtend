package tools.vitruv.extensions.dslsruntime.mappings.interfaces

import java.util.Set

/**
 * Interface used by candidate generators that are generated as Xtend classes for every mapping of the mappings language.
 * If a mapping B depends on a mapping A, then then this interface provides all current mapping instantiations
 * and mapping instantiation candidates of A to the code generated for B.
 */
interface IMappingRegistry4DependingCandidatesGenerators<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> {
	def Set<L> getLeftCandidatesAndInstances();
	def Set<R> getRightCandidatesAndInstances();
}