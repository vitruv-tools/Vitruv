package tools.vitruv.framework.tests.change

import allElementTypes.Root
import java.util.List

import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.resource.ResourceSet
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import org.eclipse.emf.common.notify.Notifier
import java.util.function.Consumer
import static com.google.common.base.Preconditions.checkState
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.change.description.TransactionalChange
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import org.eclipse.emf.ecore.util.EcoreUtil
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertNotNull
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject
import tools.vitruv.framework.change.echange.id.IdResolver

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
abstract class ChangeDescription2ChangeTransformationTest {
	var ChangeRecorder changeRecorder
	var IdResolver idResolver
	var ResourceSet resourceSet
	var Path tempFolder
	
	/** 
	 * Create a new model and initialize the change monitoring
	 */
	@BeforeEach
	def void beforeTest(@TestProject Path tempFolder) {
		this.tempFolder = tempFolder
		this.resourceSet = new ResourceSetImpl().withGlobalFactories()
		this.idResolver = IdResolver.create(resourceSet)
		this.changeRecorder = new ChangeRecorder(resourceSet)
		this.resourceSet.startRecording
	}

	@AfterEach
	def void afterTest() {
		changeRecorder.close()
	}

	protected def <T extends Notifier> record(T objectToRecord, Consumer<T> operationToRecord) {
		val recordedChanges = objectToRecord.recordComposite(operationToRecord)
		return validateChange(recordedChanges)
	}
	
	protected def <T extends Notifier> recordComposite(T objectToRecord, Consumer<T> operationToRecord) {
		resourceSet.stopRecording
		objectToRecord.startRecording
		operationToRecord.accept(objectToRecord)
		objectToRecord.stopRecording
		val recordedChange = changeRecorder.change
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

	private def List<EChange> validateChange(TransactionalChange change) {
		// Rollback changes, copy the state before their execution, reapply the changes to restore the state
		// and re-resolve the changes for the copied state and apply them to check whether they can properly
		// be applied to a different state
		val monitoredChanges = change.EChanges
		monitoredChanges.reverseView.forEach[monitoredChange|
			monitoredChange.applyBackward
		]
		val comparisonResourceSet = new ResourceSetImpl().withGlobalFactories()
		val comparisonIdResolver = IdResolver.create(comparisonResourceSet)
		resourceSet.copyTo(comparisonResourceSet)
		monitoredChanges.map[
			applyForward(idResolver)
			EcoreUtil.copy(it)
		].forEach[
			val unresolvedChange = it.unresolve()
			val resolvedChange = unresolvedChange.resolveBefore(comparisonIdResolver)
			resolvedChange.applyForward(comparisonIdResolver)
		]
		resourceSet.assertContains(comparisonResourceSet)
		comparisonResourceSet.assertContains(resourceSet)
		return monitoredChanges
	}
	
	private static def copyTo(ResourceSet original, ResourceSet target) {
		for (originalResource : original.resources) {
			val comparisonResource = target.createResource(originalResource.URI)
			if (!originalResource.contents.empty) {
				comparisonResource.contents += EcoreUtil.copyAll(originalResource.contents)
			}
		}
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

	static def assertChangeCount(Iterable<? extends EChange> changes, int expectedCount) {
		assertEquals(
			expectedCount,
			changes.size,
			'''There were «changes.size» changes, although «expectedCount» were expected'''
		)
		return changes
	}

}
