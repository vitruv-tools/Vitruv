package tools.vitruv.testutils

import java.io.FileNotFoundException
import java.nio.file.Path
import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.util.ResourceSetUtil
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.vsum.VirtualModelImpl

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.testutils.matchers.CorrespondenceModelContainer
import tools.vitruv.testutils.TestLogging
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ChangeMatchers.isValid

@ExtendWith(TestProjectManager, TestLogging)
abstract class VitruvApplicationTest implements CorrespondenceModelContainer {
	Path testProjectFolder
	ResourceSet resourceSet
	UuidGeneratorAndResolver uuidGeneratorAndResolver
	TestUserInteraction testUserInteractor
	InternalVirtualModel virtualModel
	AtomicEmfChangeRecorder changeRecorder

	def protected abstract Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications()

	@BeforeEach
	def final package void setTestProjectFolder(@TestProject Path testProjectPath) {
		this.testProjectFolder = testProjectPath
	}

	@BeforeEach
	def final package void prepareVirtualModel(TestInfo testInfo, @TestProject(variant="vsum") Path vsumPath) {
		TuidManager.instance.reinitialize()
		val changePropagationSpecifications = this.changePropagationSpecifications
		val domains = changePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
		var interactionProvider = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)
		testUserInteractor = new TestUserInteraction(interactionProvider)
		var userInteractor = UserInteractionFactory.instance.createUserInteractor(interactionProvider)
		// The virtual model has to be created first because otherwise some domain
		// overwrites may not be applied correctly before instantiating the ResourceSet
		virtualModel = new VirtualModelImpl(vsumPath.toFile(), userInteractor, new VirtualModelConfiguration() => [
			domains.forEach[domain|addMetamodel(domain)]
			changePropagationSpecifications.forEach[spec|addChangePropagationSpecification(spec)]
		])
		resourceSet = new ResourceSetImpl()
		ResourceSetUtil.addExistingFactoriesToResourceSet(resourceSet)
		uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(virtualModel.uuidGeneratorAndResolver, resourceSet,
			true)
		renewResourceCache()
	}

	@BeforeEach
	def final package void setupChangeRecorder() {
		changeRecorder = new AtomicEmfChangeRecorder(uuidGeneratorAndResolver)
		changeRecorder.beginRecording()
	}

	@AfterEach
	def final package void stopChangeRecording() {
		if (changeRecorder !== null && changeRecorder.isRecording()) {
			changeRecorder.endRecording()
		}
	}

	/**
	 * Gets the resource at the provided {@code modelPathInProject}. If the resource
	 * does not exist yet, it will be created virtually, without being persisted. You can use
	 * {@link ModelMatchers.exist} to test whether the resource actually exists on the file system.
	 * 
	 * @param modelPathWithinProject A project-relative path to a model.
	 */
	def protected Resource resourceAt(Path modelPathWithinProject) {
		try {
			getModelResource(modelPathWithinProject)
		} catch (RuntimeException e) {
			if (e.cause instanceof FileNotFoundException) {
				createModelResource(modelPathWithinProject)
			} else
				throw e
		}
	}

	/** 
	 * Loads the model resource for the provided {@code modelPathInProject}, casts its root element to the provided 
	 * {@code clazz} and returns the casted object.
	 * 
	 * @param modelPathWithinProject A project-relative path to a model.
	 */
	def protected <T> T from(Class<T> clazz, Path modelPathWithinProject) {
		return from(clazz, getModelResource(modelPathWithinProject))
	}

	/** 
	 * Casts the root element of the provided {@code resource} to the provided {@code clazz} and returns the casted object.
	 */
	def protected <T> T from(Class<T> clazz, Resource resource) {
		checkState(!resource.contents.isEmpty(), '''The resource at «resource.URI» is empty!''')
		return clazz.cast(resource.contents.get(0))
	}

	/**
	 * Starts recording changes for the resource of the provided {@code object}, executes the provided {@code consumer}
	 * on the {@code object} and stops recording changes for the resource afterwards.
	 */
	def protected <T extends EObject> T record(T object, Consumer<T> consumer) {
		object => [
			startRecordingChanges()
			consumer.accept(it)
			stopRecordingChanges()
		]
	}

	/**
	 * Starts recording changes for the provided {@code resource}, executes the provided {@code consumer} on the
	 *  {@code resource} and stops recording changes for the resource afterwards.
	 */
	def protected <T extends Resource> T record(T resource, Consumer<T> consumer) {
		resource => [
			startRecordingChanges()
			consumer.accept(it)
			stopRecordingChanges()
		]
	}

	/**
	 * {@linkplain #record Records} the changes to {@code object} created by the provided {@code consumer}, 
	 * {@code object}’s resource and propagates all changes.
	 * 
	 * @return the changes resulting from propagating the recorded changes.
	 * @see #saveAndPropagateChanges
	 */
	def protected <T extends EObject> List<PropagatedChange> recordAndPropagate(T object, Consumer<T> consumer) {
		val resource = checkedResourceOf(object)
		object.record(consumer)
		resource.save(emptyMap)
		propagateChanges()
	}

	/**
	 * {@linkplain #record Records} the changes to {@code resource} created by the provided {@code consumer}, saves
	 * the resource and propagates all changes.
	 * 
	 * @return the changes resulting from propagating the recorded changes.
	 * @see #saveAndPropagateChanges
	 */
	def protected <T extends Resource> List<PropagatedChange> recordAndPropagate(T resource, Consumer<T> consumer) {
		resource.record(consumer)
		resource.save(emptyMap)
		propagateChanges()
	}

	/**
	 * Propagates all recorded changes.
	 * 
	 * @return the changes resulting from propagating the recorded changes. 
	 */
	def protected List<PropagatedChange> propagateChanges() {
		changeRecorder.endRecording()
		var compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changeRecorder.changes)
		assertThat("The recorded change set is not valid!", compositeChange, isValid)
		var propagationResult = virtualModel.propagateChange(compositeChange)
		renewResourceCache()
		changeRecorder.beginRecording()
		return propagationResult
	}

	override getCorrespondenceModel() { virtualModel.correspondenceModel }

	def protected InternalVirtualModel getVirtualModel() { virtualModel }

	def protected TestUserInteraction getUserInteractor() { testUserInteractor }

	def private Path getPlatformModelPath(Path modelPathWithinProject) {
		checkArgument(modelPathWithinProject !== null, "The modelPathWithinProject must not be null!")
		checkArgument(!modelPathWithinProject.isEmpty, "The modelPathWithinProject must not be empty!")
		return testProjectFolder.resolve(modelPathWithinProject)
	}

	def private VURI getModelVuri(Path modelPathWithinProject) {
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(getPlatformModelPath(modelPathWithinProject).toFile()))
	}

	def private Resource createModelResource(Path modelPathWithinProject) {
		resourceSet.createResource(getModelVuri(modelPathWithinProject).EMFUri)
	}

	def private Resource getModelResource(Path modelPathWithinProject) {
		resourceSet.getResource(getModelVuri(modelPathWithinProject).EMFUri, true)
	}

	def private Resource checkedResourceOf(EObject object) {
		checkArgument(object !== null, "The object must not be null!")
		var resource = object.eResource()
		checkArgument(resource !== null, "The object must be contained in a resource!")
		return resource
	}

	def private void startRecordingChanges(EObject object) {
		startRecordingChanges(checkedResourceOf(object))
	}

	def private void startRecordingChanges(Resource resource) {
		checkState(changeRecorder.recording, "The change recorder is currently not recording!")
		changeRecorder.addToRecording(resource)
	}

	def private void stopRecordingChanges(EObject object) {
		checkArgument(object !== null, "The object must not be null!")
		// the object might just have been deleted, so we don’t require it to be in a resource
		if (object.eResource !== null) {
			stopRecordingChanges(object.eResource)
		}
	}

	def private void stopRecordingChanges(Resource resource) {
		changeRecorder.removeFromRecording(resource)
	}

	def private void renewResourceCache() {
		resourceSet.resources.clear()
	}
}
