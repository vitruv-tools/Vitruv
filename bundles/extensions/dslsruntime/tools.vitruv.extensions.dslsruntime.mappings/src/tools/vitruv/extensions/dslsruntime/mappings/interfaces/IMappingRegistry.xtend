package tools.vitruv.extensions.dslsruntime.mappings.interfaces

/**
 * Convenience interface combining all methods to maintain runtime information about instantiations
 * and instantiation candidates for a mapping on both sides. It does <i>not</i> provide information
 * about individual instantiations of metaclasses that appear as mapping parameters but only on
 * tuples that contain such elements of the right type and the right order.
 */
interface IMappingRegistry<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> extends ILeftAndRightCandidatesRegistry<L,R>, IMappingRegistry4Reactions<L,R>, IMappingRegistry4DependingCandidatesGenerators<L,R> {
	// no additional methods
}