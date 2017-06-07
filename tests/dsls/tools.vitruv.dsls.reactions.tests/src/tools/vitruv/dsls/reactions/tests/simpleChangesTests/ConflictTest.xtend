package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory
import tools.vitruv.framework.util.datatypes.VURI
import allElementTypes.AllElementTypesFactory

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import org.junit.Test

class ConflictTest extends AbstractVersioningTest {
	var SourceTargetRecorder stRecorder

	override setup() {
		super.setup
		// Setup sourceTargetRecorder 
		stRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		stRecorder.registerObserver
	}

	override unresolveChanges() {
		true
	}

	@Test
	def void testConflict() {
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		val newTestSourceModelName = "Further_Source_Test_Model"
		val newTestTargetModelName = "Further_Target_Test_Model"
		val newSourcePath = '''«currentTestProject.name»/«newTestSourceModelName.projectModelPath»'''
		val newTargetPath = '''«currentTestProject.name»/«newTestTargetModelName.projectModelPath»'''
		val newTargetVURI = VURI::getInstance(newTargetPath)
		val newSourceVURI = VURI::getInstance(newSourcePath)
		val rootElement2 = AllElementTypesFactory::eINSTANCE.createRoot
		rootElement2.id = newTestSourceModelName
		newTestSourceModelName.projectModelPath.createAndSynchronizeModel(rootElement2)
		assertThat(newSourceVURI, not(equalTo(sourceVURI)))
		assertThat(newTargetVURI, not(equalTo(targetVURI)))
		assertThat(newSourceVURI.hashCode, not(is(sourceVURI.hashCode)))
		assertThat(newTargetVURI.hashCode, not(is(targetVURI.hashCode)))

		stRecorder.recordOriginalAndCorrespondentChanges(newSourceVURI, #[newTargetVURI])
//		val roots = #[rootElement2, rootElement]
		// Create container and synchronize 
		val container1 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container1.id = "NonRootObjectContainer1"
		rootElement.nonRootObjectContainerHelper = container1
		rootElement.saveAndSynchronizeChanges
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(0))
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))

		val container2 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container2.id = "NonRootObjectContainer2"
		rootElement2.nonRootObjectContainerHelper = container2
		rootElement2.saveAndSynchronizeChanges
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(1))
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(it + "_1", container1)]
		rootElement.saveAndSynchronizeChanges
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(4))
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(1))

		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(it + "_2", container2)]
		rootElement.saveAndSynchronizeChanges
		rootElement2.saveAndSynchronizeChanges
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(4))
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(4))

		assertModelsEqual

	}

}
