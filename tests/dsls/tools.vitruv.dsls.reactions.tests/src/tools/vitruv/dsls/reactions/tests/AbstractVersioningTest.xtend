package tools.vitruv.dsls.reactions.tests

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRootObjectContainerHelper
import allElementTypes.Root
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangeReactionsCompiler
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.extensions.URIRemapper

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
abstract class AbstractVersioningTest extends AbstractAllElementTypesReactionsTests {

	protected static extension AllElementTypesFactory = AllElementTypesFactory::eINSTANCE
	protected static extension URIRemapper = URIRemapper::instance
	protected static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource"
	protected static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget"
	protected static val NON_CONTAINMENT_NON_ROOT_IDS = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"]

	protected static final def void createAndAddNonRoot(String id, NonRootObjectContainerHelper container) {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		nonRoot.id = id
		container.nonRootObjectsContainment.add(nonRoot)
	}

	@Inject
	SimpleChangeReactionsCompiler reactionCompiler

	protected override setup() {
		// Create model 
		val root = createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		TEST_SOURCE_MODEL_NAME.projectModelPath.createAndSynchronizeModel(root)
	}

	protected override cleanup() {
		// Do nothing
	}

	override protected createChangePropagationSpecifications() {
		#[reactionCompiler.newConcreteChangePropagationSpecification]
	}

	protected static def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	protected def VURI calculateVURI(String path) {
		VURI::getInstance('''«currentTestProjectFolder.name»/«path.projectModelPath»''')
	}

	protected def assertModelsEqual() {
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath)
	}

	protected final def getRootElement() {
		TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root
	}
}
