package tools.vitruv.extensions.dslsruntime.mappings

import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1

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
	
	def Iterable<R> getRightInstances() {
		return mappingRegistry.getRightInstances()
	}
	
	def Iterable<L> removeInvalidatedLeftInstances() {
		val instances = getLeftInstances()
		removeInvalidatedInstances(instances, [
			mappingRegistry.removeLeftInstance(it)
			mappingRegistry.removeFullInstancesForLeftInstance(it)
		])
	}
	
	def Iterable<R> removeInvalidatedRightInstances() {
		val instances = getRightInstances()
		removeInvalidatedInstances(instances, [
			mappingRegistry.removeRightInstance(it)
			mappingRegistry.removeFullInstancesForRightInstance(it)
		])
	}
	
	private def <H extends MappingInstanceHalf> Iterable<H> removeInvalidatedInstances(Iterable<H> instances, Procedure1<H> procedure) {
		val invalidatedInstances = newHashSet()
		for (instance : instances) {
			val preconditionsStillHold = instance.checkConditions()
			if (!preconditionsStillHold) {
				invalidatedInstances.add(instance)
			}
		}
		// apply passed procedure while iterating over 'invalidatedInstances'
		// not over 'instances' as the procedure may contain code that 
		// modifies 'instances' but not 'invalidatedInstances' concurrently
		for (invalidatedInstance : invalidatedInstances) {
			procedure.apply(invalidatedInstance)
		}
		return invalidatedInstances
	}
	
	def Iterable<L> promoteValidatedLeftCandidatesToInstances() {
		val candidates = getLeftCandidates()
		return promoteValidatedCandidatesToInstances(candidates, [
			mappingRegistry.promoteLeftCandidateToInstance(it)
		])		
	}
	
	def Iterable<R> promoteValidatedRightCandidatesToInstances() {
		val candidates = getRightCandidates()
		return promoteValidatedCandidatesToInstances(candidates, [
			mappingRegistry.promoteRightCandidateToInstance(it)
		])		
	}
	
	private def <H extends MappingInstanceHalf> Iterable<H> promoteValidatedCandidatesToInstances(Iterable<H> candidates, Procedure1<H> procedure) {
		val newInstances = newHashSet()
		for (candidate : candidates) {
			val preconditionsHold = candidate.checkConditions()
			if (preconditionsHold) {
				newInstances.add(candidate)
			}
		}
		// apply passed procedure while iterating over 'newInstances'
		// not over 'candidates' as the procedure may contain code that 
		// modifies 'candidates' but not 'newInstances' concurrently
		for (newInstance : newInstances) {
			procedure.apply(newInstance)
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
	
	/** 
	 * Returns the name of the mapping for debugging and error messages.
	 */
	def String getMappingName()
}