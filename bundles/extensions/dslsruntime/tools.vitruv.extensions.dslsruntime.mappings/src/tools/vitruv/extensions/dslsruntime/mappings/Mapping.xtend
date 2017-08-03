package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set

/**
 * Generic base class for mappings on the metamodel level as defined with the mappings language. 
 */
abstract class Mapping<L extends MappingInstanceHalf, R extends MappingInstanceHalf> {
	@Accessors(PROTECTED_GETTER) val MappingRegistry<L,R> mappingRegistry
	
	protected new() {
		this.mappingRegistry = new MappingRegistry(this)
	}
	
	def Set<L> getLeftCandidates() {
		return mappingRegistry.getLeftCandidates()
	}
		
	def Set<R> getRightCandidates() {
		return mappingRegistry.getRightCandidates()
	}
	
	def Iterable<L> getLeftInstances() {
		return mappingRegistry.getLeftInstances()
	}
	
	def Iterable<R> getRightInstances() {
		return mappingRegistry.getRightInstances()
	}
	
	def void addLeftInstance(L instance) {
		mappingRegistry.addLeftInstance(instance)
	}
	
	def void addRightInstance(R instance) {
		mappingRegistry.addRightInstance(instance)
	}
	
	def void removeLeftInstance(L instance) {
		mappingRegistry.removeLeftInstance(instance)
	}
	
	def void removeRightInstance(R instance) {
		mappingRegistry.removeRightInstance(instance)
	}
	
	/** 
	 * Returns the name of the mapping for debugging and error messages.
	 */
	def String getMappingName()
}