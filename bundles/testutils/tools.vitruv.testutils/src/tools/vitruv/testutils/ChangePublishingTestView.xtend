package tools.vitruv.testutils

import org.eclipse.emf.common.notify.Notifier
import java.util.function.Consumer
import java.nio.file.Path
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.List
import java.util.LinkedList
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.vsum.VirtualModel
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.change.description.TransactionalChange
import java.util.ArrayList
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

/**
 * A test view that will record and publish the changes created in it.
 */
class ChangePublishingTestView implements NonTransactionalTestView {
	val ResourceSet resourceSet
	@Delegate
	val TestView delegate
	val AtomicEmfChangeRecorder changeRecorder
	val List<(VitruviusChange)=>List<PropagatedChange>> changeProcessors = new LinkedList()
	var renewResourceCacheAfterPropagation = true

	/**
	 * Creates a test view for the provided {@code targetDomains} that will store its persisted resources in the provided
	 * {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction} and use
	 * the provided {@code uriMode}.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode,
		VitruvDomainRepository targetDomains
	) {
		this(persistenceDirectory, userInteraction, uriMode, null as UuidGeneratorAndResolver, targetDomains)
	}

	/**
	 * Creates a test view for the provided {@code targetDomains} that will store its persisted resources in the 
	 * provided {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction},
	 * use the provided {@code uriMode} and use the provided {@code parentResolver} as parent resolver for UUID resolving.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode,
		UuidGeneratorAndResolver parentResolver,
		VitruvDomainRepository targetDomains
	) {
		resourceSet = new ResourceSetImpl().withGlobalFactories()
		delegate = new BasicTestView(resourceSet, persistenceDirectory, userInteraction, uriMode, targetDomains)
		val uuidResolver = new UuidGeneratorAndResolverImpl(parentResolver, resourceSet, true)
		changeRecorder = new AtomicEmfChangeRecorder(uuidResolver)
		changeRecorder.beginRecording()
	}

	/**
	 * Creates a test view for the provided {@code targetDomains} that will store its persisted resources in the
	 * provided {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction},
	 * use the provided {@code uriMode} and be connected to the provided {@code virtualModel}.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode,
		VirtualModel virtualModel,
		VitruvDomainRepository targetDomains
	) {
		this(persistenceDirectory, userInteraction, uriMode, virtualModel.uuidGeneratorAndResolver, targetDomains)
		registerChangeProcessor [change|virtualModel.propagateChange(change)]
	}

	override close() {
		delegate.close()
		changeRecorder.stopRecording()
	}

	override <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		try {
			startRecordingChanges(notifier)
			return delegate.record(notifier, consumer)
		} finally {
			stopRecordingChanges(notifier)
		}
	}

	override <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		val delegateChanges = delegate.propagate(notifier) [record(consumer)]
		changeRecorder.endRecording()
		val ourChanges = propagateChanges(changeRecorder.changes)
		changeRecorder.beginRecording()
		return delegateChanges + ourChanges
	}

	override propagate() {
		changeRecorder.endRecording()
		val recordedChanges = changeRecorder.changes
		val delegateChanges = recordedChanges.map [changedResource]
			.filterNull.toSet
			.flatMapFixed [changedResource | 
				// Propagating an empty modification gives the delegate a chance participation in propagation.
				// This is not really a meaningful operation, but rather a hack to bridge between this 
				// non-transactional view and the transactional delegate.
				delegate.propagate(changedResource) []
			] 
		val ourChanges = propagateChanges(recordedChanges)
		changeRecorder.beginRecording()
		return delegateChanges + ourChanges
	}
	
	def private propagateChanges(Iterable<TransactionalChange> changes) {
		val compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		checkState(compositeChange.validate, "The recorded change set is not valid!")
		val propagationResult = changeProcessors.flatMap[apply(compositeChange)].toList
		if (renewResourceCacheAfterPropagation) {
			renewResourceCache()
		}
		return propagationResult
	}

	override renewResourceCache() {
		resourceSet.resources.clear()
	}

	/**
	 * Registers the provided {@code processor} to be used when this view publishes changes.
	 */
	def registerChangeProcessor((VitruviusChange)=>List<PropagatedChange> processor) {
		changeProcessors += processor
	}

	override <T extends Notifier> T startRecordingChanges(T notifier) {
		checkState(changeRecorder.recording, "This test view has already been closed!")
		checkArgument(notifier !== null, '''The object to record changes of is null!''')
		changeRecorder.addToRecording(notifier)
		return notifier
	}

	override <T extends Notifier> T stopRecordingChanges(T notifier) {
		checkState(changeRecorder.recording, "This test view has already been closed!")
		checkArgument(notifier !== null, '''The object to stop recording changes of is null!''')
		changeRecorder.removeFromRecording(notifier)
		return notifier
	}

	def setRenewResourceCacheAfterPropagation(boolean enabled) {
		renewResourceCacheAfterPropagation = enabled
	}
	
	def static <T> List<T> operator_plus(List<T> a, List<T> b) {
		return if (a.isEmpty) b
		else if (b.isEmpty) a
		else {
			val result = new ArrayList(a.size + b.size)
			result.addAll(a)
			result.addAll(b)
			result
		}
	}
}
