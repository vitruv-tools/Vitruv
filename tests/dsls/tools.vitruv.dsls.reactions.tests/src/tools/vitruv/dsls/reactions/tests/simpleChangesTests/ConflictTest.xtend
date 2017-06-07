package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.Collection
import org.junit.Test
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

class ConflictTest extends AbstractVersioningTest {
	Collection<Root> roots
	SourceTargetRecorder stRecorder
	VURI newSourceVURI
	VURI sourceVURI

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
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		val newTestSourceModelName = "Further_Source_Test_Model"
		val newTestTargetModelName = "Further_Target_Test_Model"
		val newTargetVURI = newTestTargetModelName.calculateVURI
		newSourceVURI = newTestSourceModelName.calculateVURI

		val rootElement2 = AllElementTypesFactory::eINSTANCE.createRoot
		roots = #[rootElement, rootElement2]
		rootElement2.id = newTestSourceModelName
		newTestSourceModelName.projectModelPath.createAndSynchronizeModel(rootElement2)
		stRecorder.recordOriginalAndCorrespondentChanges(newSourceVURI, #[newTargetVURI])

		assertThat(newSourceVURI, not(equalTo(sourceVURI)))
		assertThat(newTargetVURI, not(equalTo(targetVURI)))
		assertThat(newSourceVURI.hashCode, not(is(sourceVURI.hashCode)))
		assertThat(newTargetVURI.hashCode, not(is(targetVURI.hashCode)))

		val container1 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container1.id = "NonRootObjectContainer1"
		rootElement.nonRootObjectContainerHelper = container1

		checkChangeMatchesLength(1, 0)

		val container2 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container2.id = "NonRootObjectContainer2"
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(1, 1)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container1)]
		checkChangeMatchesLength(4, 1)

		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container2)]
		checkChangeMatchesLength(4, 4)

		assertModelsEqual

	}

	private def checkChangeMatchesLength(int l1, int l2) {
		roots.forEach[saveAndSynchronizeChanges]
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(l1))
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(l2))
	}

	private def calculateVURI(String path) {
		VURI::getInstance('''«currentTestProject.name»/«path.projectModelPath»''')
	}

}
