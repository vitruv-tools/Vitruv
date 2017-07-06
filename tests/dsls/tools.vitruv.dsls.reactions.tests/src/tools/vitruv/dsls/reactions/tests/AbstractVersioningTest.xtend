package tools.vitruv.dsls.reactions.tests

import allElementTypes.AllElementTypesFactory
import mir.reactions.AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes
import allElementTypes.Root
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

	protected static def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	protected def VURI calculateVURI(String path) {
		VURI::getInstance('''«currentTestProject.name»/«path.projectModelPath»''')
	}

	protected def assertModelsEqual() {
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath)
	}

	protected final def getRootElement() {
		TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root
	}
}
