package tools.vitruv.extensions.dslsruntime.mappings.impl

import java.util.List
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf

/**
 * An object of this class keeps track of current mapping instantiations and of candidates, which are combinations
 * of model elements that can become future mapping instantiations, for <b>one side of a certain mapping</b>.
 * That is, two objects of this class are necessary to keep track of the left and the right half of each 
 * mapping instantiation and each mapping instantiation candidate.
 */
class HashMapCandidatesAndInstanceHalvesRegistry<H extends IMappingInstanceHalf> {
	static extension Logger LOGGER = Logger.getLogger(HashMapCandidatesAndInstanceHalvesRegistry.getSimpleName())
	val String mappingName
	val String sideName
	val Map<List<EObject>,H> candidatesRegistry = newHashMap()
	val Map<List<EObject>,H> instanceHalvesRegistry = newHashMap()

	new(String mappingName, String sideName) {
		this.mappingName = mappingName
		this.sideName = sideName
	}
	
	/********** BEGIN GETTER METHODS **********/
	protected def Iterable<H> getCandidatesClone() {
		return candidatesRegistry.values.clone
	}
	
	protected def Iterable<H> getInstanceHalvesClone() {
		return instanceHalvesRegistry.values.clone
	}
	
	protected def H getCandidateForElements(List<Object> elements) {
		return candidatesRegistry.get(elements)
	}
	
	protected def H getInstanceHalfForElements(List<Object> elements) {
		return instanceHalvesRegistry.get(elements)
	}
	
	/********** BEGIN SETTER METHODS **********/	
	protected def void addCandidates(Iterable<H> candidates) {
		candidates.forEach[addCandidatesOrInstanceHalf(candidatesRegistry, it, "candidate")]
	}
	
	private def void removeCandidate(H candidate) {
		removeCandidateOrInstanceHalf(candidatesRegistry, candidate, "candidate")
	}
	
	private def void addInstanceHalf(H instanceHalf) {
		addCandidatesOrInstanceHalf(instanceHalvesRegistry, instanceHalf, "instance half")
	}
	
	protected def void removeInstanceHalf(H instanceHalf) {
		removeCandidateOrInstanceHalf(instanceHalvesRegistry, instanceHalf, "instance half")
	}
	
	private def void addCandidatesOrInstanceHalf(Map<List<EObject>,H> registry, H candidateOrInstanceHalf, String type) {
		val previouslyRegistered = registry.put(candidateOrInstanceHalf.getElements(), candidateOrInstanceHalf)
		if (previouslyRegistered !== null) {
			throw new IllegalStateException('''Cannot register the «sideName» mapping «type» '«candidateOrInstanceHalf»' for the mapping '«mappingName»'
			because the  «sideName» mapping «type» '«previouslyRegistered»' is already registered for
			the «sideName» elements '«candidateOrInstanceHalf.getElements()»'!''')
		}
	}
	
	private def void removeCandidateOrInstanceHalf(Map<List<EObject>,H> registry, H candidateOrInstanceHalf, String type) {
		val registered = registry.remove(candidateOrInstanceHalf.getElements())
		if (registered === null) {
			throw new IllegalStateException(getDeregisterMessage(candidateOrInstanceHalf,"nothing ", type))
		} else if (registered != candidateOrInstanceHalf) {
			throw new IllegalStateException(getDeregisterMessage(candidateOrInstanceHalf,'''the «sideName» mapping instance half '«registered»' ''', type))
		}
	}

	private def String getDeregisterMessage(H candidateOrInstanceHalf, String mapped, String type) '''Cannot deregister the «sideName» mapping «type» '«candidateOrInstanceHalf»' for the mapping '«mappingName»'
			and the «sideName» elements '«candidateOrInstanceHalf.getElements()» because «mapped»was registered for these elements!'''
	
	/********** BEGIN CANDIDATE METHODS **********/
	protected def void removeCandidatesForElement(EObject element) {
		val iterator = candidatesRegistry.keySet().iterator()
		var atLeastOneSetRemoved = false
		while (iterator.hasNext()) {
			val pivot = iterator.next()
			if (pivot.contains(element)) {
				iterator.remove()
				atLeastOneSetRemoved = true
			}
		}
		if (!atLeastOneSetRemoved) {
			info('''No «sideName» candidates to be removed are registered for the element '«element»'
			in '«iterator.toList»' of the mapping '«mappingName»'.''')
		}
	}
	
	protected def Set<H> getCandidatesAndInstances() {
		val candidatesAndInstances = newHashSet()
		candidatesAndInstances.addAll(candidatesClone)
		candidatesAndInstances.addAll(instanceHalvesClone)
		return candidatesAndInstances
	}

	/********** BEGIN INSTANCE METHODS **********/
	protected def void promoteCandidateToInstanceHalf(H candidate) {
		removeCandidate(candidate)
		addInstanceHalf(candidate)
	}
}