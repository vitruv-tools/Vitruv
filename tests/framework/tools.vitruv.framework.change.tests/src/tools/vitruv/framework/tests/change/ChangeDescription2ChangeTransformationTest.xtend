package tools.vitruv.framework.tests.change

import allElementTypes.Root
import java.util.List

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
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
import tools.vitruv.testutils.domains.TestDomainsRepository
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.change.description.TransactionalChange
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
abstract class ChangeDescription2ChangeTransformationTest {
	var ChangeRecorder changeRecorder
	var UuidGeneratorAndResolver uuidGeneratorAndResolver
	var ResourceSet resourceSet
	var Path tempFolder
	
	/** 
	 * Create a new model and initialize the change monitoring
	 */
	@BeforeEach
	def void beforeTest(@TestProject Path tempFolder) {
		this.tempFolder = tempFolder
		this.resourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(TestDomainsRepository.INSTANCE)
		this.uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet)
		this.changeRecorder = new ChangeRecorder(uuidGeneratorAndResolver)
		this.resourceSet.startRecording
	}

	@AfterEach
	def void afterTest() {
		changeRecorder.close()
	}

	protected def <T extends Notifier> record(T objectToRecord, Consumer<T> operationToRecord) {
		val recordedChanges = objectToRecord.recordComposite(operationToRecord)
		return prepareChanges(recordedChanges)
	}
	
	protected def <T extends Notifier> recordComposite(T objectToRecord, Consumer<T> operationToRecord) {
		resourceSet.stopRecording
		objectToRecord.startRecording
		operationToRecord.accept(objectToRecord)
		objectToRecord.stopRecording
		val recordedChanges = changeRecorder.changes
		resourceSet.startRecording
		return recordedChanges
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

	private def List<EChange> prepareChanges(List<? extends TransactionalChange> changeDescriptions) {
		// Revert changes, unresolve them, re-resolve and re-apply them to ensure that this process is idempotent
		val monitoredChanges = changeDescriptions.map[EChanges].flatten
		monitoredChanges.toList.reverseView.forEach[monitoredChange|
			monitoredChange.applyBackward
			EChangeUnresolver.unresolve(monitoredChange)
		]
		return monitoredChanges.map[
			val resolvedChange = resolveBefore(uuidGeneratorAndResolver)
			resolvedChange.applyForward
			resolvedChange
		].toList
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
