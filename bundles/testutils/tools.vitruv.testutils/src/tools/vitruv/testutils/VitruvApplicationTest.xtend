package tools.vitruv.testutils

import java.nio.file.Path
import org.eclipse.emf.ecore.resource.ResourceSet
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.testutils.util.TestSetup

import static edu.kit.ipd.sdq.commons.util.java.lang.StringUtil.isEmpty

import static extension tools.vitruv.testutils.util.TestSetup.*
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.util.bridges.EMFBridge
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import java.io.FileNotFoundException
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.util.ResourceSetUtil
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import org.junit.jupiter.api.AfterEach
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import java.util.function.Consumer
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import static com.google.common.base.Preconditions.checkState

abstract class VitruvApplicationTest {
	static Path workspace
	Path testProjectFolder
	ResourceSet resourceSet
	UuidGeneratorAndResolver uuidGeneratorAndResolver
	TestUserInteraction testUserInteractor
	InternalVirtualModel virtualModel
	AtomicEmfChangeRecorder changeRecorder

	def protected abstract Iterable<ChangePropagationSpecification> createChangePropagationSpecifications()

	def protected abstract Iterable<VitruvDomain> getVitruvDomains()

	@BeforeAll
	def final static package void prepareTestWorkspace() {
		TestSetup.initializeLogger()
		workspace = TestSetup.createTestWorkspace()
	}

	@BeforeEach
	def final package void initTestProject(TestInfo testInfo) {
		TuidManager.instance.reinitialize()
		testProjectFolder = workspace.createProjectFolder(testInfo.getDisplayName(), true)
	}

	@BeforeEach
	def final package void prepareVirtualModel(TestInfo testInfo) {
		var interactionProvider = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)
		testUserInteractor = new TestUserInteraction(interactionProvider)
		var userInteractor = UserInteractionFactory.instance.createUserInteractor(interactionProvider)
		// The virtual model has to be created first because otherwise some domain
		// overwrites may not be applied correctly before instantiating the ResourceSet
		virtualModel = workspace.createVirtualModel(testInfo.getDisplayName() + "_vsum_", true, vitruvDomains,
			createChangePropagationSpecifications(), userInteractor)
		renewResourceCache()
	}

	@BeforeEach
	def final package void setupChangeRecorder() {
		this.changeRecorder = new AtomicEmfChangeRecorder(getLocalUuidGeneratorAndResolver())
	}

	@AfterEach
	def final package void stopChangeRecording() {
		if (changeRecorder.isRecording()) {
			changeRecorder.endRecording()
		}
	}

	/**
	 * Gets the resource at the provided {@code modelPathInProject}. If the resource
	 * does not exist yet, it will be created virtually, without being persisted. You can use
	 * {@link ModelMatchers.exist} to test whether the resource actually exists on the file system.
	 */
	def protected Resource resourceAt(String modelPathInProject) {
		try {
			getModelResource(modelPathInProject)
		} catch (RuntimeException e) {
			if (e.cause instanceof FileNotFoundException) {
				createModelResource(modelPathInProject)
			} else
				throw e
		}
	}

	/** 
	 * Loads the model resource for the provided {@code modelPathInProject}, casts its root element to the provided 
	 * {@code clazz} and returns the casted object.
	 */
	def protected <T> T from(Class<T> clazz, String modelPathInProject) {
		return from(clazz, getModelResource(modelPathInProject))
	}

	/** 
	 * Casts the root element of the provided {@code resource} to the provided {@code clazz} and returns the casted object.
	 */
	def protected <T> T from(Class<T> clazz, Resource resource) {
		checkState(!resource.contents.isEmpty(), "The resource %s is empty!", resource)
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
	 * Saves the model containing the given {@link EObject} and propagates changes that were recorded for it.
	 * @return a list with the {@link PropagatedChange}s, containing the original and consequential changes.
	 */
	def protected List<PropagatedChange> saveAndSynchronizeChanges(EObject object) {
		return saveAndSynchronizeChanges(checkedResourceOf(object))
	}

	/** 
	 * Saves the provided {@link Resource} and propagates changes that were recorded for it.
	 * @return a list with the {@link PropagatedChange}s, containing the original * and consequential changes.
	 */
	def protected List<PropagatedChange> saveAndSynchronizeChanges(Resource resource) {
		EcoreResourceBridge.saveResource(resource)
		return propagateChanges()
	}

	/** 
	 * Creates a model with the given root element at the given path within the test project.
	 * Propagates the changes for inserting the root element.
	 * @param modelPathInProject path within project to persist the model at
	 * @param rootElement        root element to persist
	 */
	def protected void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
		checkArgument(rootElement !== null, "The rootElement must not be null!")
		var Resource resource = createModelResource(modelPathInProject)
		resource.record[contents += rootElement]
		saveAndSynchronizeChanges(rootElement)
	}

	/** 
	 * Deletes the model at the provided {@code modelPathInProject}. Propagates changes for removing the root elements.
	 */
	def protected List<PropagatedChange> deleteAndSynchronizeModel(String modelPathInProject) {
		getModelResource(modelPathInProject).record[delete(emptyMap())]
		return propagateChanges()
	}

	def protected CorrespondenceModel getCorrespondenceModel() {
		return virtualModel.correspondenceModel
	}

	def protected InternalVirtualModel getVirtualModel() {
		return virtualModel
	}

	def protected TestUserInteraction getUserInteractor() {
		return testUserInteractor
	}

	def protected UuidGeneratorAndResolver getLocalUuidGeneratorAndResolver() {
		return uuidGeneratorAndResolver
	}

	def private Path getPlatformModelPath(String modelPathWithinProject) {
		checkArgument(!isEmpty(modelPathWithinProject), "The modelPathWithinProject must not be empty!")
		return testProjectFolder.resolve(modelPathWithinProject)
	}

	def private VURI getModelVuri(String modelPathWithinProject) {
		checkArgument(!isEmpty(modelPathWithinProject), "The modelPathWithinProject must not be empty!")
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(getPlatformModelPath(modelPathWithinProject).toFile()))
	}

	def private Resource createModelResource(String modelPathWithinProject) {
		resourceSet.createResource(getModelVuri(modelPathWithinProject).EMFUri)
	}

	def private Resource getModelResource(String modelPathWithinProject) {
		resourceSet.getResource(getModelVuri(modelPathWithinProject).EMFUri, true)
	}

	def private Resource checkedResourceOf(EObject object) {
		checkArgument(object !== null, "The object must not be null!")
		var resource = object.eResource()
		checkArgument(resource !== null, "The object must be contained in a resource!")
		return resource
	}

	def private List<PropagatedChange> propagateChanges() {
		changeRecorder.endRecording()
		val changes = changeRecorder.changes
		var CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		var propagationResult = virtualModel.propagateChange(compositeChange)
		renewResourceCache()
		changeRecorder.beginRecording()
		return propagationResult
	}

	def private void startRecordingChanges(EObject object) {
		startRecordingChanges(checkedResourceOf(object))
	}

	def private void startRecordingChanges(Resource resource) {
		changeRecorder.addToRecording(resource)
		if (!changeRecorder.isRecording()) {
			changeRecorder.beginRecording()
		}
	}

	def private void stopRecordingChanges(EObject object) {
		stopRecordingChanges(checkedResourceOf(object))
	}

	def private void stopRecordingChanges(Resource resource) {
		changeRecorder.removeFromRecording(resource)
	}

	def private void renewResourceCache() {
		resourceSet = new ResourceSetImpl()
		ResourceSetUtil.addExistingFactoriesToResourceSet(resourceSet)
		uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(virtualModel.uuidGeneratorAndResolver, resourceSet,
			true)
	}
}
