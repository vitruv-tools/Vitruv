package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslsruntime.mappings.registry.CandidatesAndInstanceHalves4ReactionsRegistry
import tools.vitruv.extensions.dslsruntime.mappings.registry.MappingInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.registry.SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry

/**
 * Base class to be instantiated by all runtime registry classes that are generated
 * for mappings defined with the mappings language. 
 */
abstract class AbstractMappingRuntime<L extends MappingInstanceHalf, R extends MappingInstanceHalf> implements CandidatesAndInstanceHalves4ReactionsRegistry<L,R> {
	@Accessors(PROTECTED_GETTER)
	@Delegate
	val SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry<L,R> registry
	
	/** 
	 * @param mappingName the name of the mapping for debugging and error messages.
	 */
	protected new(String mappingName) {
		this.registry = new SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry(mappingName)
	}
}