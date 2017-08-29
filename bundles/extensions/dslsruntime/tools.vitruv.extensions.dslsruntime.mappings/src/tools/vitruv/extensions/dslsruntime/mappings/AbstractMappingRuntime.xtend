package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate

/**
 * Generic base class for mappings on the metamodel level as defined with the mappings language. 
 */
abstract class AbstractMappingRuntime<L extends MappingInstanceHalf, R extends MappingInstanceHalf> extends AbstractElementRuntime implements MappingRuntime<L,R> {
	@Accessors(PROTECTED_GETTER) @Delegate val MappingRegistry<L,R> mappingRegistry
	
	/** 
	 * @param mappingName the name of the mapping for debugging and error messages.
	 */
	protected new(String mappingName) {
		super(mappingName)
		this.mappingRegistry = new MappingRegistry(mappingName)
	}
}