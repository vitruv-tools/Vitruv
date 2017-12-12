package tools.vitruv.dsls.reactions.tests.complexTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import org.junit.Test

import static org.junit.Assert.assertEquals
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

class ReactionsRollbackTests extends AbstractAllElementTypesReactionsTests {
	private static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource";
	private static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}

	protected override setup() {
		// Do nothing
	}

	override protected cleanup() {
		// Do nothing
	}

	@Test
	public def void testReverse() {
		val root = AllElementTypesFactory.eINSTANCE.createRoot();
		root.setId(TEST_SOURCE_MODEL_NAME);
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root);
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath,
			TEST_SOURCE_MODEL_NAME.projectModelPath);
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
		nonRoot.id = "testId";
		root.singleValuedContainmentEReference = nonRoot;
		val result = saveAndSynchronizeChanges(root);
		this.virtualModel.reverseChanges(result);
//		assertPersistedModelsEqual(FURTHER_SOURCE_TEST_MODEL_NAME.projectModelPath,
//			FURTHER_TARGET_TEST_MODEL_NAME.projectModelPath);
		val testResourceSet = new ResourceSetImpl();
		testResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		val sourceModel = testResourceSet.getResource(TEST_SOURCE_MODEL_NAME.projectModelPath.modelVuri.EMFUri,
			true);
		assertEquals(null, (sourceModel.contents.get(0) as Root).singleValuedContainmentEReference);
		val targetModel = testResourceSet.getResource(TEST_TARGET_MODEL_NAME.projectModelPath.modelVuri.EMFUri,
			true);
		assertEquals(null, (targetModel.contents.get(0) as Root).singleValuedContainmentEReference);
	}
	
}
