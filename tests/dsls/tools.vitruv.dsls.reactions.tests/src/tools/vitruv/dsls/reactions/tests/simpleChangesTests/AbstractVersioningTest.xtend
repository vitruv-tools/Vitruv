package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRootObjectContainerHelper
import allElementTypes.Root
import java.util.ArrayList
import java.util.Collections
import java.util.List
import mir.reactions.AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes
import org.junit.Assert
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractVersioningTest extends AbstractAllElementTypesReactionsTests {
	protected static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource"
	protected static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget"
	protected static val NON_CONTAINMENT_NON_ROOT_IDS = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"]

	protected override setup() {
		// Create model 
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		TEST_SOURCE_MODEL_NAME.projectModelPath.createAndSynchronizeModel(root)
	}

	protected override cleanup() {
		// Do nothing
	}

	protected final override createChangePropagationSpecifications() {
		#[new AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes {
		}]
	}

	protected def VURI calculateVURI(String path) {
		VURI::getInstance('''«currentTestProject.name»/«path.projectModelPath»''')
	}

	@Test
	def testRecordingWithExecuteCommand() {
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		val recorder = new AtomicEmfChangeRecorderImpl
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		val modelInstance = virtualModel.getModelInstance(resourceVuri)
		recorder.beginRecording(resourceVuri, Collections::singleton(modelInstance.resource))

		rootElement.saveAndSynchronizeChanges

		assertModelsEqual

		val List<TransactionalChange> changes = new ArrayList<TransactionalChange>

		virtualModel.executeCommand [|
			changes += recorder.endRecording
			null
		]
		Assert::assertEquals(4, changes.length)
	}

	protected static final def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	protected final def getRootElement() {
		TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root
	}

	protected def assertModelsEqual() {
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath)
	}

	protected final def void createAndAddNonRoot(String id, NonRootObjectContainerHelper container) {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		nonRoot.id = id
		container.nonRootObjectsContainment.add(nonRoot)
	}
}
