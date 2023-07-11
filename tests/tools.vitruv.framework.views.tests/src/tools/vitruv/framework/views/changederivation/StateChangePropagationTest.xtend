package tools.vitruv.framework.views.changederivation

import java.nio.file.Path
import java.util.stream.Stream
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.utils.UseIdentifiers
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.^extension.ExtendWith
import pcm_mockup.Repository
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.TestLogging
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager
import uml_mockup.UPackage

import static org.junit.jupiter.api.Assertions.*
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

@ExtendWith(TestProjectManager, TestLogging, RegisterMetamodelsInStandalone)
abstract class StateChangePropagationTest {
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"
	var Path testProjectFolder
	@Accessors(PROTECTED_GETTER)
	var Resource umlCheckpoint
	@Accessors(PROTECTED_GETTER)
	var Resource pcmCheckpoint
	@Accessors(PROTECTED_GETTER)
	var Resource umlModel
	@Accessors(PROTECTED_GETTER)
	var Resource pcmModel
	@Accessors(PROTECTED_GETTER)
	var Repository pcmRoot
	@Accessors(PROTECTED_GETTER)
	var UPackage umlRoot
	var ChangeRecorder changeRecorder
	@Accessors(PROTECTED_GETTER)
	var ResourceSet resourceSet
	var ResourceSet checkpointResourceSet

	/**
	 * Creates the strategy, sets up the test model and prepares everything for determining changes.
	 */
	@BeforeEach
	def void setup(@TestProject Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder
		// Setup:
		resourceSet = new ResourceSetImpl().withGlobalFactories()
		checkpointResourceSet = new ResourceSetImpl().withGlobalFactories()
		changeRecorder = new ChangeRecorder(resourceSet)
		// Create mockup models:
		resourceSet.record [
			createPcmMockupModel()
			createUmlMockupModel()
		]
		// create model checkpoints and start recording:
		umlCheckpoint = umlModel.createCheckpoint
		pcmCheckpoint = pcmModel.createCheckpoint
		umlModel.startRecording
		pcmModel.startRecording
	}
	
	static def strategiesToTest() {
	    Stream.of(
	        Named.of("identifiers when available", new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.WHEN_AVAILABLE)),
	        Named.of("only identifiers", new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.ONLY)),
	        Named.of("never identifiers", new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.NEVER))
	    )
	}
	
	/**
	 * Stops recording in case the test does not call getRecordedChanges() or getChangeFromComparisonWithCheckpoint().
	 */
	@AfterEach
	def stopRecording() {
		changeRecorder.close()
	}

	/**
	 * USE THIS METHOD TO COMPARE RESULTS!
	 * Compares two changes: The recorded change sequence and the resolved changes by the state delta based strategy.
	 */
	protected def compareChanges(Resource model, Resource checkpoint, StateBasedChangeResolutionStrategy strategyToTest) {
		model.save(null)
		val deltaBasedChange = resourceSet.endRecording
		val unresolvedStateBasedChange = strategyToTest.getChangeSequenceBetween(model, checkpoint)
		assertNotNull(unresolvedStateBasedChange)
		val stateBasedChange = VitruviusChangeResolver.forHierarchicalIds(checkpoint.resourceSet).resolveAndApply(unresolvedStateBasedChange)
		val message = getTextualRepresentation(stateBasedChange, deltaBasedChange)
		val stateBasedChangedObjects = stateBasedChange.affectedAndReferencedEObjects
		val deltaBasedChangedObjects = deltaBasedChange.affectedAndReferencedEObjects
		assertEquals(stateBasedChangedObjects.size, deltaBasedChangedObjects.size, '''
			Got a different number of changed objects:
			«message»''')
		stateBasedChangedObjects.forEach [ stateBasedChangedObject |
			assertTrue(deltaBasedChangedObjects.exists [EcoreUtil.equals(it, stateBasedChangedObject)], '''
				Could not find this changed object in the delta based change:
				«stateBasedChangedObject»
				
				«message»''')
		]
	}

	/**
	 * Returns the recorded change sequences (the "original" changes) for a specific model instance.
	 */
	private def VitruviusChange<EObject> endRecording(Notifier notifier) {
		changeRecorder.removeFromRecording(notifier)
		return changeRecorder.endRecording
	}

	private def String getTextualRepresentation(VitruviusChange<EObject> stateBasedChange, VitruviusChange<EObject> deltaBasedChange) '''
		State-based «stateBasedChange»
		Delta-based «deltaBasedChange»
	'''

	private def createPcmMockupModel() {
		pcmModel = resourceSet.createResource(getModelURI("My.pcm_mockup")) => [
			contents += (pcmRoot = pcm.Repository => [
				name = "RootRepository"
				interfaces += pcm.Interface
				components += pcm.Component
			])
		]
		pcmModel.save(null)
	}

	private def createUmlMockupModel() {
		umlModel = resourceSet.createResource(getModelURI("My.uml_mockup")) => [
			contents += (umlRoot = uml.Package => [
				name = "RootPackage"
				interfaces += uml.Interface
				classes += uml.Class
			])
		]
		umlModel.save(null)
	}

	private def startRecording(Notifier notifier) {
		changeRecorder.addToRecording(notifier)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}
	
	protected def <T extends Notifier> record(T notifier, (T) => void function) {
		notifier.startRecording
		function.apply(notifier)
		return notifier.endRecording
	}

	private def Resource createCheckpoint(Resource original) {
		return checkpointResourceSet.getResource(original.URI, true)
	}

	protected def URI getModelURI(String modelFileName) {
		return testProjectFolder.resolve("model").resolve(modelFileName).toFile().createFileURI()
	}
}
