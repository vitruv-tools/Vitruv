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
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import static com.google.common.base.Preconditions.checkArgument
import java.util.Set
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getReferencedProxies
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver

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

	private def checkNoProxies(Resource resource, String stateNotice) {
		val proxies = resource.referencedProxies
		checkArgument(proxies.empty, "%s '%s' should not contain proxies, but contains the following: %s", stateNotice,
			resource.URI, String.join(", ", proxies.map[toString]))
	}

	override getChangeSequenceBetween(Resource newState, Resource oldState, UuidResolver resolver) {
		checkArgument(resolver !== null, "UUID generator and resolver cannot be null!")
		checkArgument(oldState !== null && newState !== null, "old state or new state must not be null!")
		newState.checkNoProxies("new state")
		oldState.checkNoProxies("old state")
		val monitoredResourceSet = new ResourceSetImpl()
		val monitoredSetGeneratorAndResolver = createUuidGeneratorAndResolver(resolver, monitoredResourceSet)
		val newResourceSet = new ResourceSetImpl()
		val currentStateCopy = oldState.copyInto(monitoredResourceSet)
		val newStateCopy = newState.copyInto(newResourceSet)
		val diffs = currentStateCopy.record(monitoredSetGeneratorAndResolver) [
			if (oldState.URI != newState.URI) {
				currentStateCopy.URI = newStateCopy.URI
			}
			compareStatesAndReplayChanges(newStateCopy, currentStateCopy)
		]
		return changeFactory.createCompositeChange(diffs)
	}

	override getChangeSequenceForCreated(Resource newState, UuidResolver resolver) {
		checkArgument(resolver !== null, "UUID generator and resolver cannot be null!")
		checkArgument(newState !== null, "new state must not be null!")
		newState.checkNoProxies("new state")
		// It is possible that root elements are automatically generated during resource creation (e.g., Java packages).
		// Thus, we create the resource and then monitor the re-insertion of the elements
		val resourceSet = new ResourceSetImpl().awareOfDomains(domainRepository)
		val newResource = resourceSet.createResource(newState.URI)
		newResource.contents.clear()
		val monitoredSetGeneratorAndResolver = createUuidGeneratorAndResolver(resolver, resourceSet)
		val diffs = newResource.record(monitoredSetGeneratorAndResolver) [
			newResource.contents += EcoreUtil.copyAll(newState.contents)
		]
		return changeFactory.createCompositeChange(diffs)
	}

	override getChangeSequenceForDeleted(Resource oldState, UuidResolver resolver) {
		checkArgument(resolver !== null, "UUID generator and resolver cannot be null!")
		checkArgument(oldState !== null, "old state must not be null!")
		oldState.checkNoProxies("old state")
		// Setup resolver and copy state:
		val monitoredResourceSet = new ResourceSetImpl()
		val monitoredSetGeneratorAndResolver = createUuidGeneratorAndResolver(resolver, monitoredResourceSet)
		val currentStateCopy = oldState.copyInto(monitoredResourceSet)
		val diffs = currentStateCopy.record(monitoredSetGeneratorAndResolver) [
			currentStateCopy.contents.clear()
		]
		return changeFactory.createCompositeChange(diffs)
	}

	private def <T extends Notifier> record(Resource resource,
		UuidGeneratorAndResolver monitoredResourceUuidGeneratorAndResolver, ()=>void function) {
		try (val changeRecorder = new ChangeRecorder(monitoredResourceUuidGeneratorAndResolver)) {
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
		val elementsCopy = EcoreUtil.copyAll(resource.contents)
		elementsCopy.forEach[eAdapters.clear]
		copy.contents.addAll(elementsCopy)
		resourceSet.resources += copy
		return copy
	}
}
