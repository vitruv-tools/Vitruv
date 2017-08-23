package tools.vitruv.extensions.dslsruntime.mappings

import java.util.List
import org.apache.log4j.Logger
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject

class MappingRegistryHalf<H extends MappingInstanceHalf> {
	static extension Logger LOGGER = Logger.getLogger(MappingRegistryHalf.getSimpleName())
	val String mappingName
	val String sideName
	val Set<H> candidatesRegistry = newHashSet()
	val Map<List<EObject>,H> instanceHalvesRegistry = newHashMap()

	new(String mappingName, String sideName) {
		this.mappingName = mappingName
		this.sideName = sideName
	}
	
	/********** BEGIN CANDIDATE METHODS **********/
	def <C extends EObject> List<H> removeCandidatesAndInstancesHalvesForElement(Class<C> clazz, C element) {
		removeCandidatesForElement(element)
		return removeInstanceHalvesForElement(element)
	}
	
	def Set<H> getCandidates() {
		return candidatesRegistry
	}
	
	def void addCandidates(Iterable<H> candidates) {
		for (candidate : candidates) {
			val candidateIsNew = candidatesRegistry.add(candidate)
			if (!candidateIsNew) {
				throw new IllegalStateException('''Cannot register the «sideName» candidate '«candidate»' for the mapping '«mappingName»'
				because it is already registered!''')
			}
		}
	}

	private def boolean removeCandidatesForElement(EObject element) {
		val iterator = candidatesRegistry.iterator()
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
		return atLeastOneSetRemoved
	}
	
	private def void removeCandidate(H candidate) {
		val wasRegistered = candidatesRegistry.remove(candidate)
		if (!wasRegistered) {
			throw new IllegalStateException('''Cannot deregister the «sideName» candidate '«candidate»' for the mapping '«mappingName»'
			because it is not registered!''')
		}
	}

	/********** BEGIN INSTANCE METHODS **********/
	def Iterable<H> getInstanceHalves() {
		return instanceHalvesRegistry.values
	}
	
	def H getInstanceHalfForElements(List<Object> elements) {
		return instanceHalvesRegistry.get(elements)
	}

	def promoteCandidateToInstanceHalf(H candidate) {
		removeCandidate(candidate)
		addInstanceHalf(candidate)
	}

	def void addInstanceHalf(H instanceHalf) {
		val previouslyRegisteredHalf = instanceHalvesRegistry.put(instanceHalf.getElements(), instanceHalf)
		if (previouslyRegisteredHalf !== null) {
			throw new IllegalStateException('''Cannot register the «sideName» mapping instance half '«instanceHalf»' for the mapping '«mappingName»'
			because the  «sideName» mapping instance half '«previouslyRegisteredHalf»' is already registered for
			the «sideName» elements '«instanceHalf.getElements()»'!''')
		}
	}
	
	def void removeInstanceHalf(H instanceHalf) {
		val registeredHalf = instanceHalvesRegistry.remove(instanceHalf.getElements())
		if (registeredHalf === null) {
			throw new IllegalStateException(getDeregisterInstanceHalfMessage(instanceHalf,"nothing "))
		} else if (registeredHalf != instanceHalf) {
			throw new IllegalStateException(getDeregisterInstanceHalfMessage(instanceHalf,'''the «sideName» mapping instance half '«registeredHalf»' '''))
		}
	}
	
	private def String getDeregisterInstanceHalfMessage(H instanceHalf, String mapped) '''Cannot deregister the «sideName» mapping instance half '«instanceHalf»' for the mapping '«mappingName»'
			and the «sideName» elements '«instanceHalf.getElements()» because «mapped»was registered for these elements!'''
	
	private def List<H> removeInstanceHalvesForElement(Object element) {
		val removedInstanceHalves = newArrayList()
		for (elementList: instanceHalvesRegistry.keySet) {
			if (elementList?.contains(element)) {
				val removedInstanceHalf = instanceHalvesRegistry.remove(elementList)
				removedInstanceHalves.add(removedInstanceHalf)
			}
		}
		return removedInstanceHalves
	}
}