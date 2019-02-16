package tools.vitruv.framework.domains

import java.util.List
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.compare.Diff
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.merge.BatchMerger
import org.eclipse.emf.compare.merge.IMerger
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl

/**
 * This default strategy for diff based state changes uses EMFCompare to resolve a 
 * diff to a sequence of individual changes.
 * @author Timur Saglam
 */
class DefaultStateChangePropagationStrategy implements StateChangePropagationStrategy {

	override getChangeSequences(Resource newState, Resource currentState, UuidGeneratorAndResolver resolver) {
		/*
		 * TODO TS Try first: create new resource set and create resource. copy content.
		 * OR compare root object instead of resource
		 * OR use orignal and revert
		 */
		val currentStateCopy = currentState.copy()
		val uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(resolver, resolver.resourceSet, true)
		// EMF Compare:
		var scope = new DefaultComparisonScope(newState, currentStateCopy, null)
		var comparison = EMFCompare.builder().build().compare(scope)
		val List<Diff> diffs = comparison.getDifferences()
		// Setup recorder:
		val changeRecorder = new AtomicEmfChangeRecorder(uuidGeneratorAndResolver)
		changeRecorder.addToRecording(currentStateCopy)
		changeRecorder.beginRecording()
		// replay the EMF compare diffs:
		val mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance()
		val merger = new BatchMerger(mergerRegistry)
		merger.copyAllLeftToRight(diffs, new BasicMonitor)
		changeRecorder.endRecording()
		// propagate vitruv changes:
		val List<TransactionalChange> vitruvDiffs = changeRecorder.changes
		return VitruviusChangeFactory.getInstance().createCompositeChange(vitruvDiffs);
	}

	def private Resource copy(Resource resource) {
		val resourceSet = new ResourceSetImpl
		val copy = resourceSet.createResource(resource.URI)
		copy.contents.addAll(EcoreUtil.copyAll(resource.contents))
		return copy
	}
}
