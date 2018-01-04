package tools.vitruv.dsls.reactions.tests.complexTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange

class BidirectionalExecutionTests extends AbstractAllElementTypesReactionsTests {
	private static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource";
	private static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget";
	
	private String[] nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"];

	private def Root getRootElement() {
		return TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root;
	}

	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}

	private def assertModelsEqual() {
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath);
	}

	protected override setup() {
		val root = AllElementTypesFactory.eINSTANCE.createRoot()
		root.setId(TEST_SOURCE_MODEL_NAME)
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
		
		prepareTestModel();
		assertModelsEqual();
	}

	override protected cleanup() {
		// Do nothing
	}

	private def prepareTestModel() {
		val container = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper();
		container.setId("NonRootObjectContainer");
		rootElement.nonRootObjectContainerHelper = container;
		for (nonRootId : nonContainmentNonRootIds) {
			val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
			nonRoot.id = nonRootId;
			container.nonRootObjectsContainment.add(nonRoot);
		}
		saveAndSynchronizeChanges(rootElement);
	}
	
	private def VitruviusChange getSourceModelChanges(PropagatedChange propagatedChange) {
		return (propagatedChange.consequentialChanges as CompositeContainerChange).changes.findFirst[URI.toString.endsWith(TEST_SOURCE_MODEL_NAME.projectModelPath)];
	}
	
	@Test
	public def void testBasicBidirectionalApplication() {
		val targetRoot = TEST_TARGET_MODEL_NAME.projectModelPath.firstRootElement as Root;
		startRecordingChanges(targetRoot);
		val newNonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot;
		newNonRoot.id = "bidirectionalId";
		targetRoot.singleValuedContainmentEReference = newNonRoot;
		val propagatedChanges = saveAndSynchronizeChanges(targetRoot);
		assertEquals(1, propagatedChanges.size);
		val consequentialSourceModelChange = propagatedChanges.get(0).sourceModelChanges;
		assertTrue(consequentialSourceModelChange.EChanges.get(0) instanceof CreateEObject<?>)
		assertTrue(consequentialSourceModelChange.EChanges.get(1) instanceof ReplaceSingleValuedEReference<?,?>)
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath);
		val testResourceSet = new ResourceSetImpl();
		testResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		val sourceModel = testResourceSet.getResource(TEST_SOURCE_MODEL_NAME.projectModelPath.modelVuri.EMFUri, true);
		assertEquals(newNonRoot.id, (sourceModel.contents.get(0) as Root).singleValuedContainmentEReference.id);
		val targetModel = testResourceSet.getResource(TEST_TARGET_MODEL_NAME.projectModelPath.modelVuri.EMFUri, true);
		assertEquals(newNonRoot.id, (targetModel.contents.get(0) as Root).singleValuedContainmentEReference.id);
	}

	/** Regression test for #175:
	 *  Removing an object from its container for which the UUID is not cached in the local UUID resolver,
	 *  the UUID resolution failed: Local and global resolution both failed, because the object
	 *  has a changed URI (due to removal from container).
	 */
	@Test
	public def void testApplyRemoveInOtherModel() {
		val targetRoot = TEST_TARGET_MODEL_NAME.projectModelPath.firstRootElement as Root;
		startRecordingChanges(targetRoot);
		targetRoot.nonRootObjectContainerHelper.nonRootObjectsContainment.remove(0);
		val propagatedChanges = saveAndSynchronizeChanges(targetRoot);
		assertEquals(1, propagatedChanges.size);
		val consequentialSourceModelChange = propagatedChanges.get(0).sourceModelChanges;
		assertTrue(consequentialSourceModelChange.EChanges.get(0) instanceof RemoveEReference<?,?>)
		assertTrue(consequentialSourceModelChange.EChanges.get(1) instanceof DeleteEObject<?>)
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath);
		val testResourceSet = new ResourceSetImpl();
		testResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		val sourceModel = testResourceSet.getResource(TEST_SOURCE_MODEL_NAME.projectModelPath.modelVuri.EMFUri, true);
		assertEquals(2, (sourceModel.contents.get(0) as Root).nonRootObjectContainerHelper.nonRootObjectsContainment.size);
		val targetModel = testResourceSet.getResource(TEST_TARGET_MODEL_NAME.projectModelPath.modelVuri.EMFUri, true);
		assertEquals(2, (targetModel.contents.get(0) as Root).nonRootObjectContainerHelper.nonRootObjectsContainment.size);
	}
	
	/** Regression test for #175:
	 *  Removing a root object from a resource for which the UUID is not cached in the local UUID resolver,
	 *  the UUID resolution failed: Local and global resolution both failed, because the object
	 *  has a changed URI (due to removal from container).
	 */
	@Test
	public def void testApplyRemoveRootInOtherModel() {
		val targetRoot = TEST_TARGET_MODEL_NAME.projectModelPath.firstRootElement as Root;
		startRecordingChanges(targetRoot);
		val propagatedChanges = deleteAndSynchronizeModel(TEST_TARGET_MODEL_NAME.projectModelPath);
		assertEquals(1, propagatedChanges.size);
		val consequentialSourceModelChange = propagatedChanges.get(0).sourceModelChanges;
		assertTrue(consequentialSourceModelChange.EChanges.get(0) instanceof RemoveRootEObject<?>)
		assertTrue(consequentialSourceModelChange.EChanges.get(1) instanceof DeleteEObject<?>)
		assertModelNotExists(TEST_SOURCE_MODEL_NAME.projectModelPath)
		assertModelNotExists(TEST_TARGET_MODEL_NAME.projectModelPath)
	}
}
