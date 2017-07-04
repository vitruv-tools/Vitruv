package tools.vitruv.dsls.reactions.tests.versioning

import allElementTypes.AllElementTypesFactory
import java.util.Map
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractConflictTest
import tools.vitruv.framework.versioning.BranchDiffCreator

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

class ConcreteConflictTest extends AbstractConflictTest {

	static val alternativeContainerId = "TOTALLY_DIFFERENT"

	@Test
	def void conflict() {
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

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
		container1.id = containerId
		rootElement.nonRootObjectContainerHelper = container1

		checkChangeMatchesLength(1, 0)

		val container2 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container2.id = alternativeContainerId
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(1, 1)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container1)]
		checkChangeMatchesLength(4, 1)

		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container2)]
		checkChangeMatchesLength(4, 4)

		assertModelsEqual

		val sourceChanges = stRecorder.getChangeMatches(sourceVURI)
		val targetChanges = stRecorder.getChangeMatches(newSourceVURI)
		val Map<String, String> rootToRootMap = #{sourceVURI.EMFUri.toPlatformString(false) ->
			newSourceVURI.EMFUri.toPlatformString(false)}
		addMap(rootToRootMap)
		val branchDiff = BranchDiffCreator::instance.createVersionDiff(sourceChanges, targetChanges)
		val conflict = conflictDetector.detectConlicts(branchDiff)
		assertThat(conflict.originalChangesLevenshteinDistance, is(1))
	}
}
