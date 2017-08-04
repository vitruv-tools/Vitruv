package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set

/**
 * Generic base class for mappings on the metamodel level as defined with the mappings language. 
 */
abstract class Mapping<L extends MappingInstanceHalf, R extends MappingInstanceHalf, I extends MappingInstance<L,R>> {
	@Accessors(PROTECTED_GETTER) val MappingRegistry<L,R,I> mappingRegistry
	
	protected new() {
		this.mappingRegistry = new MappingRegistry(getMappingName())
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
	
	def Iterable<L> removeInvalidatedLeftInstances() {
		val instances = getLeftInstances()
		val invalidatedInstances = newHashSet()
		for (instance : instances) {
			val preconditionsStillHold = instance.checkConditions()
			if (!preconditionsStillHold) {
				mappingRegistry.removeLeftInstance(instance)
				mappingRegistry.removeFullInstancesForLeftInstance(instance)
				invalidatedInstances.add(instance)
			}
		}
		return invalidatedInstances
	}
	
	def Iterable<L> promoteValidatedLeftCandidatesToInstances() {
		val candidates = getLeftCandidates()
		val newInstances = newHashSet()
		for (candidate : candidates) {
			val preconditionsHold = candidate.checkConditions()
			if (preconditionsHold) {
				mappingRegistry.promoteLeftCandidateToInstance(candidate)
				newInstances.add(candidate)
			}
		}
		return newInstances
	}
	
	def void enforceConditionsFromLeft2Right(I instance) {
		instance.enforceRigthConditions()
		instance.enforceFromLeft2Right()
	}
	
	def void enforceConditionsFromRight2Left(I instance) {
		instance.enforceLeftConditions()
		instance.enforceFromRight2Left()
	}
//	
//	def Iterable<R> getRightInstances() {
//		return mappingRegistry.getRightInstances()
//	}
//	
//	private def void addLeftInstance(L instance) {
//		mappingRegistry.addLeftInstance(instance)
//	}
//	
//	def void addRightInstance(R instance) {
//		mappingRegistry.addRightInstance(instance)
//	}
//	
//	private def void removeLeftInstance(L instance) {
//		mappingRegistry.removeLeftInstance(instance)
//	}
//	
//	def void removeRightInstance(R instance) {
//		mappingRegistry.removeRightInstance(instance)
//	}
	
	/** 
	 * Returns the name of the mapping for debugging and error messages.
	 */
	def String getMappingName()
}