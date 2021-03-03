package tools.vitruv.framework.domains

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.compare.Diff
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.merge.BatchMerger
import org.eclipse.emf.compare.merge.IMerger
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.recording.ChangeRecorder
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.uuid.UuidResolver
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver

/**
 * This default strategy for diff based state changes uses EMFCompare to resolve a 
 * diff to a sequence of individual changes.
 * @author Timur Saglam
 */
class DefaultStateBasedChangeResolutionStrategy implements StateBasedChangeResolutionStrategy {
	val VitruviusChangeFactory changeFactory

	/**
	 * Creates the strategy.
	 */
	new() {
		changeFactory = VitruviusChangeFactory.instance
	}

	override getChangeSequences(Resource newState, Resource currentState, UuidResolver resolver) {
		if (resolver === null) {
			throw new IllegalArgumentException("UUID generator and resolver cannot be null!")
		} else if (newState === null || currentState === null) {
			return changeFactory.createCompositeChange(Collections.emptyList)
		}
		// Setup resolver and copy state:
		val copyResourceSet = new ResourceSetImpl
		val uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resolver, copyResourceSet)
		val currentStateCopy = currentState.copyInto(copyResourceSet)
		// Create change sequences:
		val diffs = compareStates(newState, currentStateCopy)
		val vitruvDiffs = replayChanges(diffs, currentStateCopy, uuidGeneratorAndResolver)
		return changeFactory.createCompositeChange(vitruvDiffs)
	}

	/**
	 * Compares states using EMFCompare and returns a list of all differences.
	 */
	private def List<Diff> compareStates(Notifier newState, Notifier currentState) {
		var scope = new DefaultComparisonScope(newState, currentState, null)
		var comparison = EMFCompare.builder.build.compare(scope)
		return comparison.differences
	}

	/**
	 * Replays a list of of EMFCompare differences and records the changes to receive Vitruv change sequences. 
	 */
	private def List<? extends TransactionalChange> replayChanges(List<Diff> changesToReplay, Notifier currentState, UuidGeneratorAndResolver resolver) {
		// Setup recorder:
		try (val changeRecorder = new ChangeRecorder(resolver)) {
			changeRecorder.addToRecording(currentState)
			changeRecorder.beginRecording
			// replay the EMF compare diffs:
			val mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance()
			val merger = new BatchMerger(mergerRegistry)
			merger.copyAllLeftToRight(changesToReplay, new BasicMonitor)
			// Finish recording:
			changeRecorder.endRecording
			return changeRecorder.changes
		}
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
