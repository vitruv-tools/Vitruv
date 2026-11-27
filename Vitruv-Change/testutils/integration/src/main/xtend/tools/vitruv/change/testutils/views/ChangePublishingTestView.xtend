package tools.vitruv.change.testutils.views

import java.nio.file.Files
import java.nio.file.Path
import java.util.ArrayList
import java.util.List
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.change.atomic.uuid.Uuid
import tools.vitruv.change.atomic.uuid.UuidResolver
import tools.vitruv.change.composite.description.PropagatedChange
import tools.vitruv.change.composite.description.TransactionalChange
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory
import tools.vitruv.change.composite.propagation.ChangeableModelRepository
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.change.propagation.ChangePropagationSpecificationRepository
import tools.vitruv.change.propagation.impl.DefaultChangeRecordingModelRepository
import tools.vitruv.change.propagation.impl.DefaultChangeableModelRepository
import tools.vitruv.change.testutils.TestUserInteraction

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap
import static tools.vitruv.change.testutils.TestModelRepositoryFactory.createTestChangeableModelRepository

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.flatMapFixed
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A test view that will record and publish the changes created in it.
 */
class ChangePublishingTestView implements NonTransactionalTestView {
	val ResourceSet resourceSet
	val UuidResolver uuidResolver
	val VitruviusChangeResolver<Uuid> changeResolver
	@Delegate
	val TestView delegate
	val ChangeRecorder changeRecorder
	val ChangeableModelRepository modelRepository
	val BiConsumer<Resource, UuidResolver> uuidResolution
	var disposeViewResourcesAfterPropagation = true

	/**
	 * Creates a test view that will store its persisted resources in the
	 * provided {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction},
	 * use the provided {@code uriMode}.
	 * 
	 * @param persistenceDirectory is the directory to store files at.
	 * @param userInteraction the {@link TestUserInteraction} to use for interactions during change propagation.
	 * @param uriMode is the URI mode.
	 * @param changeableModelRepository is the repository responsible for propagating and storing the models.
	 * @param uuidResolution is a consumer that populates the given view's {@link UuidResolver} with the UUIDs of all elements in the given {@link Resource}.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode,
		ChangeableModelRepository changeableModelRepository,
		BiConsumer<Resource, UuidResolver> uuidResolution
	) {
		this.resourceSet = new ResourceSetImpl().withGlobalFactories()
		this.uuidResolver = UuidResolver.create(resourceSet)
		this.modelRepository = changeableModelRepository
		this.delegate = new BasicTestView(persistenceDirectory, resourceSet, userInteraction, uriMode)
		this.changeRecorder = new ChangeRecorder(resourceSet)
		this.changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver)
		this.uuidResolution = uuidResolution
		changeRecorder.beginRecording()
	}

	/**
	 * Creates a test view that will store its persisted resources in the
	 * provided {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction},
	 * use the provided {@code uriMode}.
	 * 
	 * @param persistenceDirectory is the directory to store files at.
	 * @param userInteraction the {@link TestUserInteraction} to use for interactions during change propagation.
	 * @param uriMode is the URI mode.
	 * @param changeableModelRepository is the repository responsible for propagating and storing the models.
	 * @param modelUuidResolver is the {@link UuidResolver} associated with the {@code changeableModelRepository}.
	 * @param modelResourceAt is a function that provides the model resource as stored in the 
	 * 						  {@code changeableModelRepository} for a given URI.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode,
		ChangeableModelRepository changeableModelRepository,
		UuidResolver modelUuidResolver,
		Function<URI, Resource> modelResourceAt
	) {
		this(persistenceDirectory, userInteraction, uriMode, changeableModelRepository) [ viewResource, viewUuidResolver |
			val modelResource = modelResourceAt.apply(viewResource.URI)
			if (modelResource !== null) {
				modelUuidResolver.resolveResource(modelResource, viewResource, viewUuidResolver)
			}
		]
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
		val delegateChanges = delegate.propagate(notifier)[record(consumer)]
		changeRecorder.endRecording()
		val ourChanges = propagateChanges(changeRecorder.change)
		changeRecorder.beginRecording()
		return delegateChanges + ourChanges
	}

	override propagate() {
		changeRecorder.endRecording()
		val recordedChange = changeRecorder.change
		val delegateChanges = recordedChange.changedURIs.map[resourceSet.getResource(it, false)].filterNull.flatMapFixed [ changedResource |
			// Propagating an empty modification for every changed resource gives the delegate a
			// chance to participate in change propagation (e.g. BasicTestView saves or cleans up resources).
			// This is not a meaningful operation at all, but rather a hack to bridge between this
			// non-transactional operation and the transactional delegate.
			delegate.propagate(changedResource)[]
		]
		val ourChanges = propagateChanges(recordedChange)
		changeRecorder.beginRecording()
		return delegateChanges + ourChanges
	}

	def private propagateChanges(TransactionalChange<EObject> change) {
		val unresolvedChange = changeResolver.assignIds(change)
		val propagationResult = modelRepository.propagateChange(unresolvedChange)
		if (disposeViewResourcesAfterPropagation) {
			disposeViewResources()
		}
		return propagationResult
	}

	override Resource resourceAt(URI modelUri) {
		val resource = delegate.resourceAt(modelUri)
		uuidResolution.accept(resource, uuidResolver)
		return resource
	}

	override Resource resourceAt(Path viewRelativePath) {
		resourceAt(viewRelativePath.uri)
	}

	override <T extends EObject> T from(Class<T> clazz, URI modelUri) {
		val resource = resourceSet.getResource(modelUri, true)
		uuidResolution.accept(resource, uuidResolver)
		return clazz.from(resource)
	}

	override <T extends EObject> T from(Class<T> clazz, Path viewRelativePath) {
		clazz.from(viewRelativePath.uri)
	}

	override disposeViewResources() {
		resourceSet.resources.forEach [ resource |
			if (resource.URI === null || !isPathmap(resource.URI)) {
				resource.allContents.forEach [
					if (uuidResolver.hasUuid(it)) {
						uuidResolver.unregisterEObject(uuidResolver.getUuid(it), it)
					}
				]
			}
		]
		resourceSet.resources.clear()
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

	override setDisposeViewResourcesAfterPropagation(boolean enabled) {
		disposeViewResourcesAfterPropagation = enabled
	}

	def static <T> List<T> operator_plus(List<T> a, List<T> b) {
		return if (a.isEmpty) {
			b
		} else if (b.isEmpty) {
			a
		} else {
			val result = new ArrayList(a.size + b.size)
			result.addAll(a)
			result.addAll(b)
			result
		}
	}

	/**
	 * Creates a {@link ChangePublishingTestView} with the given persistence directory
	 * and for the given {@link ChangePropagationSpecification}s. It uses file URIs
	 * (see {@link UriMode}) and instantiates a {@link DefaultChangeableModelRepository}
	 * for a {@link TestUserInteraction}.
	 */
	static def createDefaultChangePublishingTestView(
		Path persistenceDirectory,
		Iterable<ChangePropagationSpecification> changePropagationSpecifications
	) {
		val userInteraction = new TestUserInteraction()
		val changePropagationSpecificationProvider = new ChangePropagationSpecificationRepository(
			changePropagationSpecifications)
		val modelRepository = new DefaultChangeRecordingModelRepository(null, Files.createTempDirectory(null));
		val changeableModelRepository = createTestChangeableModelRepository(modelRepository,
			changePropagationSpecificationProvider, userInteraction)
		return new ChangePublishingTestView(persistenceDirectory, userInteraction, UriMode.FILE_URIS,
			changeableModelRepository, modelRepository.uuidResolver)[modelRepository.getModelResource(it)]
	}
}
