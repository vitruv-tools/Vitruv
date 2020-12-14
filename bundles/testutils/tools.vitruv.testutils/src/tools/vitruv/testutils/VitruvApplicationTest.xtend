package tools.vitruv.testutils

import java.nio.file.Path
import java.util.List
import java.util.function.Consumer
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
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import java.io.File

@ExtendWith(TestProjectManager, TestLogging)
abstract class VitruvApplicationTest implements CorrespondenceModelContainer {
	Path testProjectFolder
	ResourceSet resourceSet
	UuidGeneratorAndResolver uuidGeneratorAndResolver
	TestUserInteraction testUserInteractor
	InternalVirtualModel virtualModel
	AtomicEmfChangeRecorder changeRecorder

	/**
	 * Determines the {@link ChangePropagationSpecification}s to be used in this test.
	 */
	def protected abstract Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications()

	/**
	 * Determines whether the test uses platform URIs.
	 * Defaults to <code>false</code>. Otherwise file URIs are used.
	 */
	def protected boolean usePlatformURIs() { false }

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
		resourceAt(getPlatformModelUri(modelPathWithinProject))
	}

	/**
	 * Gets the resource at the provided {@link URI}. If the resource does not exist yet, it will be
	 * created virtually, without being persisted. You can use {@link ModelMatchers.exist} to test
	 * whether the resource actually exists on the file system.
	 *
	 * @param modelUri the {@link URI} of the model to load.
	 */
	def protected Resource resourceAt(URI modelUri) {
		synchronized (resourceSet) {
			var Resource resource = null;
			try {
				resource = resourceSet.getResource(modelUri, true)
			} catch (RuntimeException e) {
				// EMF failed during demand creation, usually because loading from the file system failed.
				// If it has created an empty resource, retrieve it, and otherwise create one.
				resource = resourceSet.getResource(modelUri, false)
				if (resource === null) {
					resource = resourceSet.createResource(modelUri)
				}
			}
			return resource
		}
	}

	/** 
	 * Loads the model resource for the provided {@code modelPathInProject}, casts its root element to the provided 
	 * {@code clazz} and returns the casted object.
	 * 
	 * @param modelPathWithinProject A project-relative path to a model.
	 */
	def protected <T> T from(Class<T> clazz, Path modelPathWithinProject) {
		return from(clazz, getAndLoadModelResource(modelPathWithinProject))
	}

	/** 
	 * Casts the root element of the provided {@code resource} to the provided {@code clazz} and returns the casted object.
	 */
	def protected <T> T from(Class<T> clazz, Resource resource) {
		checkState(!resource.contents.isEmpty(), '''The resource at «resource.URI» is empty!''')
		return clazz.cast(resource.contents.get(0))
	}

	/**
	 * Starts recording changes for the resource of the provided {@code notifier}, executes the provided 
	 * {@code consumer} on the {@code notifier} and stops recording changes for the {@code notifier} afterwards.
	 */
	def protected <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		checkState(changeRecorder.recording, "The change recorder is currently not recording!")

		changeRecorder.addToRecording(notifier)
		consumer.accept(notifier)
		changeRecorder.removeFromRecording(notifier)

		return notifier
	}

	/**
	 * {@linkplain #record Records} the changes to {@code notifier} created by the provided {@code consumer}, 
	 * saves the modified resource and propagates all recorded changes (including changes that have been recorded 
	 * before calling this method).
	 * 
	 * @return the changes resulting from propagating the recorded changes.
	 * @see #saveAndPropagateChanges
	 */
	def protected <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		notifier.record(consumer)
		saveAndPropagateChanges()
	}

	/**
	 * Saves the resource that was modified by the recorded changes and propagates all recorded changes.
	 * 
	 * @return the changes resulting from propagating the recorded changes. 
	 */
	def protected List<PropagatedChange> saveAndPropagateChanges() {
		changeRecorder.endRecording()
		var compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changeRecorder.changes)
		assertThat("The recorded change set is not valid!", compositeChange, isValid)
		compositeChange.changedResource?.save(emptyMap)
		var propagationResult = virtualModel.propagateChange(compositeChange)
		renewResourceCache()
		changeRecorder.beginRecording()
		return propagationResult
	}

	override CorrespondenceModel getCorrespondenceModel() { virtualModel.correspondenceModel }

	def protected InternalVirtualModel getVirtualModel() { virtualModel }

	def protected TestUserInteraction getUserInteractor() { testUserInteractor }

	def protected final URI getPlatformModelUri(Path modelPathWithinProject) {
		checkArgument(modelPathWithinProject !== null, "The modelPathWithinProject must not be null!")
		checkArgument(!modelPathWithinProject.isEmpty, "The modelPathWithinProject must not be empty!")
		return if (usePlatformURIs) {
			// Platform URIs need to use '/' as separator, whereas Path uses system-specific separator
			val workspaceRelativePath = testProjectFolder.fileName + "/" +
				modelPathWithinProject.toString.replace(File.separatorChar, '/')
			EMFBridge.createURI(workspaceRelativePath)
		} else {
			val targetFile = testProjectFolder.resolve(modelPathWithinProject).toFile()
			EMFBridge.getEmfFileUriForFile(targetFile)
		}
	}

	def private Resource getAndLoadModelResource(Path modelPathWithinProject) {
		resourceSet.getResource(getPlatformModelUri(modelPathWithinProject), true)
	}

	def private void renewResourceCache() {
		resourceSet.resources.clear()
	}

	/**
	 * Access to the change recorder for the legacy {@link LegacyVitruvApplicationTest}.
	 * Has to be removed as soon as {@link LegacyVitruvApplicationTest} is removed.
	 */
	@Deprecated
	def package getChangeRecorder() {
		changeRecorder;
	}

}
