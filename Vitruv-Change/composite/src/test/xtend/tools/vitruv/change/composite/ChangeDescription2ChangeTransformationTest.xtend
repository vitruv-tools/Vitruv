package tools.vitruv.change.composite

import allElementTypes.Root
import java.nio.file.Path
import java.util.HashMap
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid
import tools.vitruv.change.atomic.uuid.UuidResolver
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.change.testutils.TestProject
import tools.vitruv.change.testutils.TestProjectManager

import static com.google.common.base.Preconditions.checkState
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
abstract class ChangeDescription2ChangeTransformationTest {
	var ChangeRecorder changeRecorder
	var UuidResolver uuidResolver
	var VitruviusChangeResolver<Uuid> changeResolver
	var ResourceSet resourceSet
	var Path tempFolder
	
	/** 
	 * Create a new model and initialize the change monitoring
	 */
	@BeforeEach
	def void beforeTest(@TestProject Path tempFolder) {
		this.tempFolder = tempFolder
		this.resourceSet = new ResourceSetImpl().withGlobalFactories()
		this.uuidResolver = UuidResolver.create(resourceSet)
		this.changeRecorder = new ChangeRecorder(resourceSet)
		this.changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);
		this.resourceSet.startRecording
	}

	@AfterEach
	def void afterTest() {
		changeRecorder.close()
	}

	protected def <T extends Notifier> record(T objectToRecord, Consumer<T> operationToRecord) {
		val recordedChange = objectToRecord.recordComposite(operationToRecord)
		return recordedChange.EChanges
	}
	
	protected def <T extends Notifier> recordComposite(T objectToRecord, Consumer<T> operationToRecord) {
		resourceSet.stopRecording
		changeResolver.assignIds(changeRecorder.change)
		
		val recordedChange = validateChange [ validationCallback |
			objectToRecord.startRecording
			operationToRecord.accept(objectToRecord)
			objectToRecord.stopRecording
			val recordedChange = changeRecorder.change
			val unresolvedChange = changeResolver.assignIds(recordedChange)
			validationCallback.accept(unresolvedChange)
			recordedChange
		]
		resourceSet.startRecording
		return recordedChange
	}

	protected def resourceAt(String name) {
		resourceSet.loadOrCreateResource('''«name».xmi'''.uri)
	}

	protected def getUri(CharSequence relativePath) {
		tempFolder.resolve(relativePath.toString).toFile().createFileURI()
	}

	protected def Root getUniquePersistedRoot() {
		val resource = resourceAt("dummy")
		if (resource.contents.empty) {
			val root = aet.Root
			resource.contents += root
			return root
		} else {
			return resource.contents.get(0) as Root
		}
	}

	private def startRecording(Notifier notifier) {
		checkState(!changeRecorder.isRecording)
		this.changeRecorder.addToRecording(notifier)
		this.changeRecorder.beginRecording
	}

	private def stopRecording(Notifier notifier) {
		checkState(changeRecorder.isRecording)
		this.changeRecorder.endRecording
		this.changeRecorder.removeFromRecording(notifier)
	}
	
	/**
	 * Creates a comparison resource set mirroring the resource set before the given operation is executed.
	 * The provided operation must be called with the change describing the performed changes in the resource set.
	 * Validates that the given change results in the new state by replaying it in the comparison resource set.
	 * Returns the object returned by the given operation.
	 */
	private def <T> T validateChange(Function<Consumer<VitruviusChange<Uuid>>, T> operationToValidate) {
		val comparisonResourceSet = new ResourceSetImpl().withGlobalFactories()
		val originalToComparisonResourceMapping = resourceSet.copyTo(comparisonResourceSet)
		val comparisonUuidResolver = UuidResolver.create(comparisonResourceSet)
		val comparisonChangeResolver = VitruviusChangeResolverFactory.forUuids(comparisonUuidResolver)
		uuidResolver.resolveResources(originalToComparisonResourceMapping, comparisonUuidResolver)
		operationToValidate.apply [ unresolvedChange |
			comparisonChangeResolver.resolveAndApply(unresolvedChange)
			resourceSet.assertContains(comparisonResourceSet)
			comparisonResourceSet.assertContains(resourceSet)
		]
	}
	
	private static def copyTo(ResourceSet original, ResourceSet target) {
		var resourceMapping = new HashMap;
		for (originalResource : original.resources) {
			val comparisonResource = target.createResource(originalResource.URI)
			if (!originalResource.contents.empty) {
				comparisonResource.contents += EcoreUtil.copyAll(originalResource.contents)
			}
			resourceMapping.put(originalResource, comparisonResource)
		}
		return resourceMapping
	}
	
	private static def assertContains(ResourceSet first, ResourceSet second) {
		for (originalResource : second.resources) {
			val comparisonResource = first.getResource(originalResource.URI, false)
			assertNotNull(comparisonResource)
			if (!originalResource.contents.empty) {
				assertThat(comparisonResource.firstRootEObject, equalsDeeply(originalResource.firstRootEObject))	
			} else {
				assertTrue(comparisonResource.contents.empty)
			}	
		}
	}

	static def <E> assertChangeCount(Iterable<? extends EChange<E>> changes, int expectedCount) {
		assertEquals(
			expectedCount,
			changes.size,
			'''There were «changes.size» changes, although «expectedCount» were expected'''
		)
		return changes
	}
}
