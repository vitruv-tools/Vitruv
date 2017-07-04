package tools.vitruv.dsls.reactions.tests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.Collection
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.extensions.GraphExtension

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

abstract class AbstractConflictTest extends AbstractVersioningTest {
	protected BranchDiff branchDiff
	protected Collection<Root> roots
	protected Conflict conflict
	protected Graph graph
	protected List<EChange> echanges
	protected List<TransactionalChange> changes
	protected Map<String, String> modelPairs
	protected Root rootElement2
	protected SourceTargetRecorder stRecorder
	protected VURI newSourceVURI
	protected VURI newTargetVURI
	protected VURI sourceVURI
	protected VURI targetVURI
	protected static extension ConflictDetector conflictDetector = ConflictDetector::instance
	protected static extension DependencyGraphCreator = DependencyGraphCreator::instance
	protected static extension GraphExtension = GraphExtension::instance
	protected static val containerId = "NonRootObjectContainer"
	protected static val newTestSourceModelName = "Further_Source_Test_Model"
	protected static val newTestTargetModelName = "Further_Target_Test_Model"

	override setup() {
		super.setup
		// Setup sourceTargetRecorder 
		targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI
		newTargetVURI = newTestTargetModelName.calculateVURI
		newSourceVURI = newTestSourceModelName.calculateVURI
		val rootToRootMap = #{
			sourceVURI.EMFUri.toPlatformString(false) -> newSourceVURI.EMFUri.toPlatformString(false),
			targetVURI.EMFUri.toPlatformString(false) -> newTargetVURI.EMFUri.toPlatformString(false)
		}
		addMap(rootToRootMap)
		stRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		stRecorder.registerObserver
		modelPairs = #{
			TEST_SOURCE_MODEL_NAME -> newTestSourceModelName,
			TEST_TARGET_MODEL_NAME -> newTestTargetModelName
		}

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])
		rootElement2 = AllElementTypesFactory::eINSTANCE.createRoot
		roots = #[rootElement, rootElement2]
		rootElement2.id = newTestSourceModelName
		newTestSourceModelName.projectModelPath.createAndSynchronizeModel(rootElement2)
		stRecorder.recordOriginalAndCorrespondentChanges(newSourceVURI, #[newTargetVURI])
		assertThat(newSourceVURI, not(equalTo(sourceVURI)))
		assertThat(newTargetVURI, not(equalTo(targetVURI)))
		assertThat(newSourceVURI.hashCode, not(is(sourceVURI.hashCode)))
		assertThat(newTargetVURI.hashCode, not(is(targetVURI.hashCode)))
	}

	override unresolveChanges() {
		true
	}

	protected def checkChangeMatchesLength(int l1, int l2) {
		roots.forEach[saveAndSynchronizeChanges]
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(l1))
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(l2))
	}

	protected final def assertMappedModelsAreEqual() {
		assertMappedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, newTestSourceModelName.projectModelPath)
		assertMappedModelsEqual(TEST_TARGET_MODEL_NAME.projectModelPath, newTestTargetModelName.projectModelPath)
	}

	protected final def void assertMappedModelsEqual(String firstModelPathWithinProject,
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

	protected final def assertDifferentNonRootContainment() {
		differentNonRootContainment(TEST_SOURCE_MODEL_NAME.projectModelPath, newTestSourceModelName.projectModelPath)
		differentNonRootContainment(TEST_TARGET_MODEL_NAME.projectModelPath, newTestTargetModelName.projectModelPath)
	}

	protected final def void differentNonRootContainment(String firstModelPathWithinProject,
		String secondModelPathWithinProject) {
		val testResourceSet = new ResourceSetImpl
		val firstRoot = getFirstRootElement(firstModelPathWithinProject, testResourceSet) as Root
		val secondRoot = getFirstRootElement(secondModelPathWithinProject, testResourceSet) as Root
		val firstContainer = firstRoot.nonRootObjectContainerHelper
		val secondContainer = secondRoot.nonRootObjectContainerHelper
		assertThat(firstContainer.id, equalTo(secondContainer.id))
		assertThat(firstContainer.nonRootObjectsContainment.size, is(secondContainer.nonRootObjectsContainment.size))
		val unmatchedElements = firstContainer.nonRootObjectsContainment.filter [ nonRoot1 |
			secondContainer.nonRootObjectsContainment.forall[!EcoreUtil::equals(it, nonRoot1)]
		]
		assertThat(unmatchedElements.size, is(1))
	}
}
