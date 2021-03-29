package tools.vitruv.testutils

import org.eclipse.emf.common.notify.Notifier
import java.util.function.Consumer
import java.nio.file.Path
import org.eclipse.emf.ecore.resource.ResourceSet
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
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.uuid.UuidResolver
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver

/**
 * A test view that will record and publish the changes created in it.
 */
class ChangePublishingTestView implements NonTransactionalTestView {
	val ResourceSet resourceSet
	@Delegate
	val TestView delegate
	val ChangeRecorder changeRecorder
	val List<(VitruviusChange)=>List<PropagatedChange>> changeProcessors = new LinkedList()
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
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
		UuidResolver parentResolver,
		VitruvDomainRepository targetDomains
	) {
		this.resourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(targetDomains)
		this.delegate = new BasicTestView(persistenceDirectory, resourceSet, userInteraction, uriMode)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(parentResolver, resourceSet)
		this.changeRecorder = new ChangeRecorder(uuidGeneratorAndResolver)
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
		this(persistenceDirectory, userInteraction, uriMode, virtualModel.uuidResolver, targetDomains)
		registerChangeProcessor [change|virtualModel.propagateChange(change)]
	}

	override close() {
		delegate.close()
		changeRecorder.close()
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
		val delegateChanges = recordedChanges.flatMap [changedURIs]
			.toSet
			.flatMapFixed [changedURI | 
				val changedResource = resourceSet.getResource(changedURI, false)
				if (changedResource !== null) {
					// Propagating an empty modification for every changed resource gives the delegate a 
					// chance to participate in change propagation (e.g. BasicTestView saves or cleans up resources).
					// This is not a meaningful operation at all, but rather a hack to bridge between this 
					// non-transactional operation and the transactional delegate.
					delegate.propagate(changedResource) []
				}
			] 
		val ourChanges = propagateChanges(recordedChanges)
		changeRecorder.beginRecording()
		return delegateChanges + ourChanges
	}
	
	def private propagateChanges(Iterable<? extends TransactionalChange> changes) {
		val compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		val propagationResult = changeProcessors.flatMapFixed [apply(compositeChange)]
		if (renewResourceCacheAfterPropagation) {
			renewResourceCache()
		}
		return propagationResult
	}

	override renewResourceCache() {
		resourceSet.resources.clear()
		uuidGeneratorAndResolver.save()
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
