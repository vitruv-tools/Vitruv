package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.Collection
import java.util.Map
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.ConflictDetector

class ConflictTest extends AbstractVersioningTest {
	static val newTestSourceModelName = "Further_Source_Test_Model"
	static val newTestTargetModelName = "Further_Target_Test_Model"
	static val containerId = "NonRootObjectContainer"
	Map<String, String> modelPairs
	Collection<Root> roots
	SourceTargetRecorder stRecorder
	VURI newSourceVURI
	VURI sourceVURI

	override setup() {
		super.setup
		// Setup sourceTargetRecorder 
		stRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		stRecorder.registerObserver
		modelPairs = #{
			TEST_SOURCE_MODEL_NAME -> newTestSourceModelName,
			TEST_TARGET_MODEL_NAME -> newTestTargetModelName
		}
	}

	override unresolveChanges() {
		true
	}

	@Test
	def void testConflict() {
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
		container2.id = containerId
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(1, 1)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container1)]
		checkChangeMatchesLength(4, 1)

		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container2)]
		checkChangeMatchesLength(4, 4)

		assertModelsEqual
		assertMappedModelsAreEqual

		val sourceChanges = stRecorder.getChangeMatches(sourceVURI)
		val targetChanges = stRecorder.getChangeMatches(newSourceVURI)
		val conflictDetector = new ConflictDetector(sourceChanges, targetChanges)

		conflictDetector.detectConlicts
	}

	private def checkChangeMatchesLength(int l1, int l2) {
		roots.forEach[saveAndSynchronizeChanges]
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(l1))
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(l2))
	}

	private final def assertMappedModelsAreEqual() {
		assertMappedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, newTestSourceModelName.projectModelPath)
		assertMappedModelsEqual(TEST_TARGET_MODEL_NAME.projectModelPath, newTestTargetModelName.projectModelPath)
	}

	private final def void assertMappedModelsEqual(String firstModelPathWithinProject,
		String secondModelPathWithinProject) {
		val testResourceSet = new ResourceSetImpl
		val firstRoot = getFirstRootElement(firstModelPathWithinProject, testResourceSet) as Root
		val secondRoot = getFirstRootElement(secondModelPathWithinProject, testResourceSet) as Root

		assertThat(firstRoot.eContents.length, is(secondRoot.eContents.length))
		firstRoot.eContents.forEach [ firstElement |
			val element = secondRoot.eContents.findFirst[EcoreUtil::equals(it, firstElement)]
			assertThat(element, not(equalTo(null)))
		]
		val id = firstRoot.id
		if (modelPairs.containsKey(id)) {
			val mappedId = modelPairs.get(id)
			assertThat(secondRoot.id, equalTo(mappedId))
		} else
			throw new IllegalStateException("ID should be contained in the map.")
	}

}
