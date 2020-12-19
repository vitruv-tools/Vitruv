package tools.vitruv.testutils

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.notify.Notifier
import java.util.function.Consumer
import java.nio.file.Path
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.List
import java.util.LinkedList
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.vsum.VirtualModel

/**
 * A test view that will publish the changes created in it.
 */
class ChangePublishingTestView implements TestView {
	val ResourceSet resourceSet
	val Path persistenceDirectory
	@Accessors
	val TestUserInteraction userInteraction
	val UriMode uriMode
	val AtomicEmfChangeRecorder changeRecorder
	val List<(VitruviusChange)=>List<PropagatedChange>> changeProcessors = new LinkedList()

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory},
	 * allow to program interactions for the provided {@code interactionProvider} and use the provided {@code uriMode}.
	 */
	new(Path persistenceDirectory, PredefinedInteractionResultProvider interactionProvider, UriMode uriMode) {
		this(persistenceDirectory, interactionProvider, uriMode, null as UuidGeneratorAndResolver)
	}

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory},
	 * allow to program interactions for the provided {@code interactionProvider}, use the provided {@code uriMode} and
	 * use the provided {@code parentResolver} as parent resolver for UUID resolving.
	 */
	new(
		Path persistenceDirectory,
		PredefinedInteractionResultProvider interactionProvider,
		UriMode uriMode,
		UuidGeneratorAndResolver parentResolver
	) {
		this.resourceSet = new ResourceSetImpl().withGlobalFactories()
		this.persistenceDirectory = persistenceDirectory
		this.uriMode = uriMode
		this.userInteraction = new TestUserInteraction(interactionProvider)
		val uuidResolver = new UuidGeneratorAndResolverImpl(parentResolver, resourceSet, true)
		changeRecorder = new AtomicEmfChangeRecorder(uuidResolver)
		changeRecorder.beginRecording()
	}

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory},
	 * allow to program interactions for the provided {@code interactionProvider}, use the provided {@code uriMode} and
	 * be connected to the provided {@code virtualModel}.
	 */
	new(
		Path persistenceDirectory,
		PredefinedInteractionResultProvider interactionProvider,
		UriMode uriMode,
		VirtualModel virtualModel
	) {
		this(persistenceDirectory, interactionProvider, uriMode, virtualModel.uuidGeneratorAndResolver)
		registerChangeProcessor[change|virtualModel.propagateChange(change)]
	}

	override close() {
		changeRecorder.stopRecording()
	}

	override resourceAt(URI modelUri) {
		synchronized (resourceSet) {
			resourceSet.loadOrCreateResource(modelUri)
		}
	}

	override <T> T from(Class<T> clazz, URI modelUri) {
		return from(clazz, resourceSet.getResource(modelUri, true))
	}

	override <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		checkState(changeRecorder.recording, "This test view has already been closed!")

		changeRecorder.addToRecording(notifier)
		consumer.accept(notifier)
		changeRecorder.removeFromRecording(notifier)

		return notifier
	}

	override <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		notifier.record(consumer)
		saveAndPropagateChanges()
	}

	override getUri(Path viewRelativePath) {
		checkArgument(viewRelativePath !== null, "The viewRelativePath must not be null!")
		checkArgument(!viewRelativePath.isEmpty, "The viewRelativePath must not be empty!")
		return switch (uriMode) {
			case PLATFORM_URIS:
				// platform URIs must always use '/'
				EMFBridge.createURI(persistenceDirectory.resolve(viewRelativePath).join('/'))
			case FILE_URIS:
				EMFBridge.getEmfFileUriForFile(persistenceDirectory.resolve(viewRelativePath).toFile())
		}
	}

	def private List<PropagatedChange> saveAndPropagateChanges() {
		changeRecorder.endRecording()
		val compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changeRecorder.changes)
		checkState(compositeChange.validate, "The recorded change set is not valid!")
		compositeChange.changedResource?.save(emptyMap)
		val propagationResult = changeProcessors.flatMap[apply(compositeChange)].toList
		renewResourceCache()
		changeRecorder.beginRecording()
		return propagationResult
	}

	def private renewResourceCache() {
		resourceSet.resources.clear()
	}

	/**
	 * Registers the provided {@code processor} to be used when this view publishes changes.
	 */
	def registerChangeProcessor((VitruviusChange)=>List<PropagatedChange> processor) {
		changeProcessors += processor
	}

	@Deprecated
	override getChangeRecorder() { changeRecorder }
}
