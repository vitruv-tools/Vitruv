package tools.vitruv.framework.domains

import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.merge.BatchMerger
import org.eclipse.emf.compare.merge.IMerger
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.ChangeRecorder
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.uuid.UuidResolver
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import static com.google.common.base.Preconditions.checkArgument
import java.util.Set

/**
 * This default strategy for diff based state changes uses EMFCompare to resolve a 
 * diff to a sequence of individual changes.
 * @author Timur Saglam
 */
class DefaultStateBasedChangeResolutionStrategy implements StateBasedChangeResolutionStrategy {
	val VitruviusChangeFactory changeFactory
	val VitruvDomainRepository domainRepository
	
	/**
	 * Creates the strategy.
	 */
	new(Set<VitruvDomain> domains) {
		domainRepository = new VitruvDomainRepositoryImpl(domains)
		changeFactory = VitruviusChangeFactory.instance
	}
	
	private def createResourceSet() {
		new ResourceSetImpl().awareOfDomains(domainRepository)
	}

	override getChangeSequenceBetween(Resource newState, Resource oldState, UuidResolver resolver) {
		checkArgument(resolver !== null, "UUID generator and resolver cannot be null!")
		checkArgument(oldState !== null && newState !== null, "old state or new state must not be null!")
		val resourceSet = createResourceSet()
		val currentStateCopy = oldState.copyInto(resourceSet)
		val diffs = currentStateCopy.record(resolver) [
			if (oldState.URI != newState.URI) {
				currentStateCopy.URI = newState.URI
			}
			compareStatesAndReplayChanges(newState, currentStateCopy)
		]
		return changeFactory.createCompositeChange(diffs)
	}
	
	override getChangeSequenceForCreated(Resource newState, UuidResolver resolver) {
		checkArgument(resolver !== null, "UUID generator and resolver cannot be null!")
		checkArgument(newState !== null, "new state must not be null!")
		// It is possible that root elements are automatically generated during resource creation (e.g., Java packages).
		// Thus, we create the resource and then monitor the re-insertion of the elements
		val resourceSet = createResourceSet()
		val newResource = resourceSet.createResource(newState.URI)
		newResource.contents.clear()
		val diffs = newResource.record(resolver) [
			newResource.contents += EcoreUtil.copyAll(newState.contents)
		]
		return changeFactory.createCompositeChange(diffs)
	}
	
	override getChangeSequenceForDeleted(Resource oldState, UuidResolver resolver) {
		checkArgument(resolver !== null, "UUID generator and resolver cannot be null!")
		checkArgument(oldState !== null, "old state must not be null!")
		// Setup resolver and copy state:
		val copyResourceSet = createResourceSet()
		val currentStateCopy = oldState.copyInto(copyResourceSet)
		val diffs = currentStateCopy.record(resolver) [
			currentStateCopy.contents.clear()
		]
		return changeFactory.createCompositeChange(diffs)
	}
	
	private def <T extends Notifier> record(Resource resource, UuidResolver parentResolver, () => void function) {
		val uuidGeneratorAndResolver = createUuidGeneratorAndResolver(parentResolver, resource.resourceSet)
		try (val changeRecorder = new ChangeRecorder(uuidGeneratorAndResolver)) {
			changeRecorder.beginRecording
			changeRecorder.addToRecording(resource)
			function.apply()
			return changeRecorder.endRecording
		}
	}

	/**
	 * Compares states using EMFCompare and replays the changes to the current state.
	 */
	private def compareStatesAndReplayChanges(Notifier newState, Notifier currentState) {
		val scope = new DefaultComparisonScope(newState, currentState, null)
		val comparison = EMFCompare.builder.build.compare(scope)
		// Assign the eResource of a root element, as otherwise the DomainAwareResource is used
		// and can lead to a mismatch with the ordinary resource in the EMF merger 
		comparison.matchedResources.forEach[
			it.right = it.right.contents.get(0).eResource()
			it.left = it.left.contents.get(0).eResource()
		]
		val changes = comparison.differences
		// Replay the EMF compare differences
		val mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance()
		val merger = new BatchMerger(mergerRegistry)
		merger.copyAllLeftToRight(changes, new BasicMonitor)
	}

	/**
	 * Creates a new resource set, creates a resource and copies the content of the orignal resource.
	 */
	private def Resource copyInto(Resource resource, ResourceSet resourceSet) {
		val uri = resource.URI
		val copy = resourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
		copy.contents.addAll(EcoreUtil.copyAll(resource.contents))
		resourceSet.resources += copy
		return copy
	}
}
