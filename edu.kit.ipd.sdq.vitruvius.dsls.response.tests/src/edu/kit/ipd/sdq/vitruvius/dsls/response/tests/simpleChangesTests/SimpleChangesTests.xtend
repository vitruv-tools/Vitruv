package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests

import org.junit.Test
import responses.ResponseChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor.ChangeType
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractAllElementTypesResponseTests
import allElementTypes.NonRoot
import allElementTypes.Root
import allElementTypes.AllElementTypesFactory
import org.eclipse.emf.ecore.EObject
import allElementTypes.Identified
import org.eclipse.emf.common.util.ECollections

class SimpleChangesTests extends AbstractAllElementTypesResponseTests {
	private static val TEST_MODEL_NAME = "TestModel";
	private static val TEST_MODEL2_NAME = "TestModel2";
	
	new() {
		super(new ResponseChange2CommandTransformingProviding());
	}
	
	private String[] nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"];
	
	private def Root getRootElement1() {
		return TEST_MODEL_NAME.root;
	}
	
	private def Root getRootElement2() {
		return TEST_MODEL2_NAME.root;
	}
	
	private def assertModelsEqual() {
		assertModelsEqual(TEST_MODEL_NAME, TEST_MODEL2_NAME);
	}
	
	protected override initializeTestModel() {
		val resource = createModelResource(TEST_MODEL_NAME);
		val root1 = AllElementTypesFactory.eINSTANCE.createRoot();
		root1.setId("Root");
		resource.getContents().add(root1);
		EcoreResourceBridge.saveResource(resource);
		val resource2 = createModelResource(TEST_MODEL2_NAME);
		val root2 = AllElementTypesFactory.eINSTANCE.createRoot();
		root2.setId("Root");
		resource2.getContents().add(root2);
		prepareTestModel();
		saveAndSynchronizeChanges(rootElement1, false);
		saveAndSynchronizeChanges(rootElement2, false);
		val corIn = this.vsum.getCorrespondenceInstanceOriginal(metaRepository.allMetamodels.get(0).URI, metaRepository.allMetamodels.get(0).URI);
		corIn.createAndAddCorrespondence(#[root1], #[root2]);
		assertModelsEqual();
		this.changeRecorder.beginRecording(#[rootElement1, rootElement2]);
	}
		
	private def prepareTestModel() {
		initializeNonRootObjectContainer(rootElement1);
		initializeNonRootObjectContainer(rootElement2);
		rootElement1.setSingleValuedContainmentNonRootObject("singleValuedContainmentNonRoot", false);
		rootElement2.setSingleValuedContainmentNonRootObject("singleValuedContainmentNonRoot", false);
		rootElement1.addMultiValuedContainmentNonRootObject("multiValuedContainmentNonRoot0", false);
		rootElement2.addMultiValuedContainmentNonRootObject("multiValuedContainmentNonRoot0", false);
		rootElement1.addMultiValuedContainmentNonRootObject("multiValuedContainmentNonRoot1", false);
		rootElement2.addMultiValuedContainmentNonRootObject("multiValuedContainmentNonRoot1", false);
		rootElement1.addMultiValuedContainmentNonRootObject("multiValuedContainmentNonRoot2", false);
		rootElement2.addMultiValuedContainmentNonRootObject("multiValuedContainmentNonRoot2", false);
	}
	
	private def void initializeNonRootObjectContainer(Root rootElement) {
		val container = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper();
		container.setId("NonRootObjectContainer");
		for (nonRootId : nonContainmentNonRootIds) {
			val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
			nonRoot.id = nonRootId;
			container.nonRootObjectsContainment.add(nonRoot);
		}
		rootElement.nonRootObjectContainerHelper = container;
		saveAndSynchronizeChanges(rootElement, false);
	}
	
	private def addMultiValuedEAttribute(Root rootObject, Integer value) {
		addMultiValuedEAttribute(rootObject, value, true);
	}

	private def addMultiValuedEAttribute(Root rootObject, Integer value, boolean synchronize) {
		rootObject.multiValuedEAttribute.add(value);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
	
	private def void replaceMultiValuedEAttribute(Root rootObject, Integer oldValue, Integer newValue) {
		replaceMultiValuedEAttribute(rootObject, oldValue, newValue, true);
	}
	
	private def void replaceMultiValuedEAttribute(Root rootObject, Integer oldValue, Integer newValue, boolean synchronize) {
		val oldIndex = rootObject.multiValuedEAttribute.indexOf(oldValue);
		if (oldIndex == -1) {
			throw new IllegalStateException("There is no element with the specified old element value.");
		}
		rootObject.multiValuedEAttribute.set(oldIndex, newValue);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
	
	private def removeMultiValuedEAttribute(Root rootObject, Integer value) {
		removeMultiValuedEAttribute(rootObject, value, true);
	}

	private def removeMultiValuedEAttribute(Root rootObject, Integer value, boolean synchronize) {
		val oldIndex = rootObject.multiValuedEAttribute.indexOf(value);
		if (oldIndex == -1) {
			throw new IllegalStateException("There is no element with the specified old element value.");
		}
		rootObject.multiValuedEAttribute.remove(oldIndex);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}

	private def NonRoot addMultiValuedContainmentNonRootObject(Root rootObject, String id) {
		return addMultiValuedContainmentNonRootObject(rootObject, id, true);
	}

	private def NonRoot addMultiValuedContainmentNonRootObject(Root rootObject, String id, boolean synchronize) {
		val nonRootObject = AllElementTypesFactory.eINSTANCE.createNonRoot();
		nonRootObject.setId(id);
		rootObject.multiValuedContainmentEReference.add(nonRootObject);
		//setElementId(nonRootObject, id, synchronize);
		saveAndSynchronizeChanges(nonRootObject, synchronize);
		return nonRootObject;
	}
	
	private def void replaceMultiValuedContainmentNonRootObject(Root rootObject, String oldId, String newId) {
		replaceMultiValuedContainmentNonRootObject(rootObject, oldId, newId, true);
	}
	
	private def void replaceMultiValuedContainmentNonRootObject(Root rootObject, String oldId, String newId, boolean synchronize) {
		val oldElement = rootObject.multiValuedContainmentEReference.findFirst(nonRoot | nonRoot.id == oldId);
		if (oldElement == null) {
			throw new IllegalStateException("There is no element with the specified old element id.");
		}
		val oldIndex = rootObject.multiValuedContainmentEReference.indexOf(oldElement);
		val newElement = AllElementTypesFactory.eINSTANCE.createNonRoot();
		newElement.id = newId;
		rootObject.multiValuedContainmentEReference.set(oldIndex, newElement);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
	
	private def removeMultiValuedContainmentNonRootObject(Root rootObject, String id) {
		removeMultiValuedContainmentNonRootObject(rootObject, id, true);
	}

	private def removeMultiValuedContainmentNonRootObject(Root rootObject, String id, boolean synchronize) {
		val objectToRemove = rootObject.multiValuedContainmentEReference.findFirst(nonRoot | nonRoot.id == id);
		if (objectToRemove == null) {
			throw new IllegalStateException("There is no element with the specified element id.");
		}
		rootObject.multiValuedContainmentEReference.remove(objectToRemove);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
	
	private def void insertMultiValuedNonContainmentNonRootObject(Root rootObject, String id) {
		insertMultiValuedNonContainmentNonRootObject(rootObject, id, true);
	}
	
	private def void insertMultiValuedNonContainmentNonRootObject(Root rootObject, String id, boolean synchronize) {
		val nonRoot = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == id);
		if (nonRoot == null) {
			throw new IllegalStateException("There is no element with the specified element id.");
		}
		rootObject.multiValuedNonContainmentEReference.add(nonRoot);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
	
	private def void replaceMultiValuedNonContainmentNonRootObject(Root rootObject, String oldId, String newId) {
		replaceMultiValuedNonContainmentNonRootObject(rootObject, oldId, newId, true);
	}
	
	private def void replaceMultiValuedNonContainmentNonRootObject(Root rootObject, String oldId, String newId, boolean synchronize) {
		val oldElement = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == oldId);
		val newElement = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == newId);
		if (oldElement == null) {
			throw new IllegalStateException("There is no element with the specified old element id.");
		}
		if (newElement == null) {
			throw new IllegalStateException("There is no element with the specified new element id.");
		}
		val oldIndex = rootObject.multiValuedNonContainmentEReference.indexOf(oldElement);
		if (oldIndex == -1) {
			throw new IllegalStateException("Old element was not in the reference list.");
		}
		rootObject.multiValuedNonContainmentEReference.set(oldIndex, newElement);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
	
	private def void removeMultiValuedNonContainmentNonRootObject(Root rootObject, String id) {
		removeMultiValuedNonContainmentNonRootObject(rootObject, id, true);
	}
	
	private def void removeMultiValuedNonContainmentNonRootObject(Root rootObject, String id, boolean synchronize) {
		val objectToRemove = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == id);
		if (objectToRemove == null) {
			throw new IllegalStateException("There is no element with the specified element id.");
		}
		if (!rootObject.multiValuedNonContainmentEReference.contains(objectToRemove)) {
			throw new IllegalStateException("The specified element is not contained in the root.");
		}
		rootObject.multiValuedNonContainmentEReference.remove(objectToRemove);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}
		
	private def void permuteMultiValuedNonContamentNonRootObjects(Root rootObject) {
		permuteMultiValuedNonContamentNonRootObjects(rootObject, true);
	}
	
	private def void permuteMultiValuedNonContamentNonRootObjects(Root rootObject, boolean synchronize) {
		ECollections.sort(rootObject.multiValuedNonContainmentEReference, [a, b | - a.id.compareTo(b.id)]);
		saveAndSynchronizeChanges(rootObject, synchronize);
	}

	private def void unsetSingleValuedNonContainmentNonRootObject(Root rootObject) {
		unsetSingleValuedNonContainmentNonRootObject(rootObject, true);
	}

	private def void unsetSingleValuedNonContainmentNonRootObject(Root rootObject, boolean synchronize) {
		rootObject.singleValuedNonContainmentEReference = null;
		saveAndSynchronizeChanges(rootObject, synchronize);
	}

	private def NonRoot setSingleValuedNonContainmentNonRootObject(Root rootObject, String id) {
		setSingleValuedNonContainmentNonRootObject(rootObject, id, true);
	}

	private def NonRoot setSingleValuedNonContainmentNonRootObject(Root rootObject, String id, boolean synchronize) {
		val nonRootObject = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == id);
		rootObject.singleValuedNonContainmentEReference = nonRootObject;
		saveAndSynchronizeChanges(nonRootObject, synchronize);
		return nonRootObject;
	}

	private def NonRoot setSingleValuedContainmentNonRootObject(Root rootObject, String id) {
		setSingleValuedContainmentNonRootObject(rootObject, id, true);
	}

	private def NonRoot setSingleValuedContainmentNonRootObject(Root rootObject, String id, boolean synchronize) {
		val nonRootObject = AllElementTypesFactory.eINSTANCE.createNonRoot();
		rootObject.singleValuedContainmentEReference = nonRootObject;
		setElementId(nonRootObject, id, synchronize);
		return nonRootObject;
	}

	private def void unsetSingleValuedContainmentNonRootObject(Root rootObject) {
		unsetSingleValuedContainmentNonRootObject(rootObject, true);
	}

	private def void unsetSingleValuedContainmentNonRootObject(Root rootObject, boolean synchronize) {
		rootObject.singleValuedContainmentEReference = null;
		saveAndSynchronizeChanges(rootObject, synchronize)
	}
	
	private def void setElementId(Identified element, String newId, boolean synchronize) {
		element.setId(newId);
		saveAndSynchronizeChanges(element, synchronize);
	}
	
	private def void setSingleValuedEAttribute(Root root, Integer value) {
		setSingleValuedEAttribute(root, value, true);
	}
	
	private def void setSingleValuedEAttribute(Root root, Integer value, boolean synchronize) {
		root.singleValuedEAttribute = value;
		saveAndSynchronizeChanges(root, synchronize);
	}
	
	private def void saveAndSynchronizeChanges(EObject object, boolean synchronize) {
		EcoreResourceBridge.saveResource(object.eResource());
		if (synchronize) {
			this.triggerSynchronization(VURI.getInstance(object.eResource()));
		}
	}

	@Test
	public def void testUpdateSingleValuedEAttribute() {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedEAttribute(rootElement1, -1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UpdateSingleValuedEAttribute);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	// TODO HK Unset does not produce any change event at the moment
	//@Test
	public def void testUnsetSingleValuedEAttribute() {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		rootElement1.eUnset(rootElement1.eClass.EStructuralFeatures.findFirst[name.equals("singleValuedEAttribute")]);
		saveAndSynchronizeChanges(rootElement1, true);
		//setSingleValuedEAttribute(rootElement, null);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UnsetEAttribute);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testCreateSingleValuedContainmentEReference() throws Throwable {
		unsetSingleValuedContainmentNonRootObject(rootElement1);
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedContainmentNonRootObject(rootElement1, "singleValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.CreateNonRootEObjectSingle);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteSingleValuedContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		unsetSingleValuedContainmentNonRootObject(rootElement1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectSingle);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceSingleValuedContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedContainmentNonRootObject(rootElement1, "singleValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectSingle);
		compareMonitor.set(ChangeType.CreateNonRootEObjectSingle);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testSetSingleValuedNonContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UpdateSingleValuedNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(nonContainmentNonRootIds.get(1), rootElement1.singleValuedNonContainmentEReference.id);
		assertModelsEqual();
	}
	
	@Test
	public def void testUnsetSingleValuedNonContainmentEReference() throws Throwable {
		setSingleValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		unsetSingleValuedNonContainmentNonRootObject(rootElement1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UnsetNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceSingleValuedNonContainmentEReference() throws Throwable {
		setSingleValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(0));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UpdateSingleValuedNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(nonContainmentNonRootIds.get(1), rootElement1.singleValuedNonContainmentEReference.id);
		assertModelsEqual();
	}
	
	
	@Test
	public def void testAddMultiValuedEAttribute() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		addMultiValuedEAttribute(rootElement1, 1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.InsertEAttributeValue);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteMultiValuedEAttribute() throws Throwable {
		addMultiValuedEAttribute(rootElement1, 1);
		addMultiValuedEAttribute(rootElement1, 2);
		SimpleChangesTestsExecutionMonitor.reinitialize();
		removeMultiValuedEAttribute(rootElement1, 1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.RemoveEAttributeValue);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(2, rootElement1.multiValuedEAttribute.get(0));
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceMultiValuedEAttribute() throws Throwable {
		addMultiValuedEAttribute(rootElement1, 1);
		addMultiValuedEAttribute(rootElement1, 2);
		SimpleChangesTestsExecutionMonitor.reinitialize();
		replaceMultiValuedEAttribute(rootElement1, 2, 3);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		// TODO HK This should not be, should be one event
		compareMonitor.set(ChangeType.RemoveEAttributeValue);
		compareMonitor.set(ChangeType.InsertEAttributeValue);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(3, rootElement1.multiValuedEAttribute.get(1));
		assertModelsEqual();
	}
	
	@Test
	public def void testAddMultiValuedContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		addMultiValuedContainmentNonRootObject(rootElement1, "multiValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.CreateNonRootEObjectInList);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteMultiValuedContainmentEReference() throws Throwable {
		addMultiValuedContainmentNonRootObject(rootElement1, "multiValuedContainmentNonRootTest");
		SimpleChangesTestsExecutionMonitor.reinitialize();
		removeMultiValuedContainmentNonRootObject(rootElement1, "multiValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectInList);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceMultiValuedContainmentEReference() throws Throwable {
		addMultiValuedContainmentNonRootObject(rootElement1, "multiValuedContainmentNonRootTest");
		SimpleChangesTestsExecutionMonitor.reinitialize();
		replaceMultiValuedContainmentNonRootObject(rootElement1, "multiValuedContainmentNonRootTest", "multiValuedContainmentNonRootTest2");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectInList);
		compareMonitor.set(ChangeType.CreateNonRootEObjectInList);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals("multiValuedContainmentNonRootTest2", rootElement1.multiValuedContainmentEReference.last.id);
		assertModelsEqual();
	}
	
	@Test
	public def void testInsertMultiValuedNonContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		insertMultiValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(0));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.InsertNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testRemoveMultiValuedNonContainmentEReference() throws Throwable {
		insertMultiValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		removeMultiValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.RemoveNonContainmentEReference);
		val mon = SimpleChangesTestsExecutionMonitor.instance;
		assertEquals(compareMonitor, mon);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceMultiValuedNonContainmentEReference() throws Throwable {
		insertMultiValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(0));
		insertMultiValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		replaceMultiValuedNonContainmentNonRootObject(rootElement1, nonContainmentNonRootIds.get(1), nonContainmentNonRootIds.get(2))
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		// TODO HK this should not be... should be one event!
		//compareMonitor.set(ChangeType.ReplaceNonContainmentEReference);
		compareMonitor.set(ChangeType.RemoveNonContainmentEReference);
		compareMonitor.set(ChangeType.InsertNonContainmentEReference);
		assertTrue(rootElement1.multiValuedNonContainmentEReference.size == 2);
		assertTrue(rootElement1.multiValuedNonContainmentEReference.get(0).id == nonContainmentNonRootIds.get(0));
		assertTrue(rootElement1.multiValuedNonContainmentEReference.get(1).id == nonContainmentNonRootIds.get(2));
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	// TODO HK Permute operations are not supported by now? No EChange produced
	//@Test
	public def void testPermuteMultiValuedNonContainmentEReference() throws Throwable {
		for (nonRootId : nonContainmentNonRootIds) {
			insertMultiValuedNonContainmentNonRootObject(rootElement1, nonRootId, false);
		}
		SimpleChangesTestsExecutionMonitor.reinitialize();
		permuteMultiValuedNonContamentNonRootObjects(rootElement1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.PermuteNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
}
