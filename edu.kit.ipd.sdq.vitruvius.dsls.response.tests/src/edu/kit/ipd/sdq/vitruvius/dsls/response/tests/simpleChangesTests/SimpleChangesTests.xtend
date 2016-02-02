package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Identified
import allElementTypes.NonRoot
import allElementTypes.Root
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractAllElementTypesResponseTests
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor.ChangeType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.ecore.EObject
import org.junit.Test
import responses.ResponseChange2CommandTransformingProviding

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import allElementTypes.AllElementTypesPackage

class SimpleChangesTests extends AbstractAllElementTypesResponseTests {
	private static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource";
	private static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget";
	private static val FURTHER_SOURCE_TEST_MODEL_NAME = "Further_Source_Test_Model";
	private static val FURTHER_TARGET_TEST_MODEL_NAME = "Further_Target_Test_Model"
	
	new() {
		super(new ResponseChange2CommandTransformingProviding());
	}
	
	private String[] nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"];
	
	private def Root getRootElement() {
		return edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTests.TEST_SOURCE_MODEL_NAME.root;
	}
	
	private def assertModelsEqual() {
		assertModelsEqual(TEST_SOURCE_MODEL_NAME, TEST_TARGET_MODEL_NAME);
	}
	
	protected override initializeTestModel() {
		val root = AllElementTypesFactory.eINSTANCE.createRoot();
		root.setId(edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTests.TEST_SOURCE_MODEL_NAME);
		createAndSychronizeModel(edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTests.TEST_SOURCE_MODEL_NAME, root);
		prepareTestModel();
		assertModelsEqual();
	}
		
	private def prepareTestModel() {
		val container = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper();
		container.setId("NonRootObjectContainer");
		for (nonRootId : nonContainmentNonRootIds) {
			val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
			nonRoot.id = nonRootId;
			container.nonRootObjectsContainment.add(nonRoot);
		}
		rootElement.nonRootObjectContainerHelper = container;
		saveAndSynchronizeChanges(rootElement);
	}
	
		
	private def unsetSingleValuedEAttribute(Root rootObject) {
		rootObject.eUnset(AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_EATTRIBUTE);
		saveAndSynchronizeChanges(rootElement)
	}
	
	private def void unsetSingleValuedNonContainmentNonRootObject(Root rootObject) {
		rootObject.singleValuedNonContainmentEReference = null;
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def addMultiValuedEAttribute(Root rootObject, Integer value) {
		rootObject.multiValuedEAttribute.add(value);
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def void replaceMultiValuedEAttribute(Root rootObject, Integer oldValue, Integer newValue) {
		val oldIndex = rootObject.multiValuedEAttribute.indexOf(oldValue);
		if (oldIndex == -1) {
			throw new IllegalStateException("There is no element with the specified old element value.");
		}
		rootObject.multiValuedEAttribute.set(oldIndex, newValue);
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def removeMultiValuedEAttribute(Root rootObject, Integer value) {
		val oldIndex = rootObject.multiValuedEAttribute.indexOf(value);
		if (oldIndex == -1) {
			throw new IllegalStateException("There is no element with the specified old element value.");
		}
		rootObject.multiValuedEAttribute.remove(oldIndex);
		saveAndSynchronizeChanges(rootObject);
	}

	private def NonRoot addMultiValuedContainmentNonRootObject(Root rootObject, String id) {
		val nonRootObject = AllElementTypesFactory.eINSTANCE.createNonRoot();
		nonRootObject.setId(id);
		rootObject.multiValuedContainmentEReference.add(nonRootObject);
		saveAndSynchronizeChanges(nonRootObject);
		return nonRootObject;
	}
	
	private def void replaceMultiValuedContainmentNonRootObject(Root rootObject, String oldId, String newId) {
		val oldElement = rootObject.multiValuedContainmentEReference.findFirst(nonRoot | nonRoot.id == oldId);
		if (oldElement == null) {
			throw new IllegalStateException("There is no element with the specified old element id.");
		}
		val oldIndex = rootObject.multiValuedContainmentEReference.indexOf(oldElement);
		val newElement = AllElementTypesFactory.eINSTANCE.createNonRoot();
		newElement.id = newId;
		rootObject.multiValuedContainmentEReference.set(oldIndex, newElement);
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def removeMultiValuedContainmentNonRootObject(Root rootObject, String id) {
		val objectToRemove = rootObject.multiValuedContainmentEReference.findFirst(nonRoot | nonRoot.id == id);
		if (objectToRemove == null) {
			throw new IllegalStateException("There is no element with the specified element id.");
		}
		rootObject.multiValuedContainmentEReference.remove(objectToRemove);
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def void insertMultiValuedNonContainmentNonRootObject(Root rootObject, String id) {
		val nonRoot = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == id);
		if (nonRoot == null) {
			throw new IllegalStateException("There is no element with the specified element id.");
		}
		rootObject.multiValuedNonContainmentEReference.add(nonRoot);
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def void replaceMultiValuedNonContainmentNonRootObject(Root rootObject, String oldId, String newId) {
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
		saveAndSynchronizeChanges(rootObject);
	}
	
	private def void removeMultiValuedNonContainmentNonRootObject(Root rootObject, String id) {
		val objectToRemove = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == id);
		if (objectToRemove == null) {
			throw new IllegalStateException("There is no element with the specified element id.");
		}
		if (!rootObject.multiValuedNonContainmentEReference.contains(objectToRemove)) {
			throw new IllegalStateException("The specified element is not contained in the root.");
		}
		rootObject.multiValuedNonContainmentEReference.remove(objectToRemove);
		saveAndSynchronizeChanges(rootObject);
	}
		
	private def void permuteMultiValuedNonContamentNonRootObjects(Root rootObject) {
		ECollections.sort(rootObject.multiValuedNonContainmentEReference, [a, b | - a.id.compareTo(b.id)]);
		saveAndSynchronizeChanges(rootObject);
	}



	private def NonRoot setSingleValuedNonContainmentNonRootObject(Root rootObject, String id) {
		val nonRootObject = rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst(nonRoot | nonRoot.id == id);
		rootObject.singleValuedNonContainmentEReference = nonRootObject;
		saveAndSynchronizeChanges(nonRootObject);
		return nonRootObject;
	}

	private def NonRoot setSingleValuedContainmentNonRootObject(Root rootObject, String id) {
		val nonRootObject = AllElementTypesFactory.eINSTANCE.createNonRoot();
		rootObject.singleValuedContainmentEReference = nonRootObject;
		setElementId(nonRootObject, id);
		return nonRootObject;
	}

	private def void unsetSingleValuedContainmentNonRootObject(Root rootObject) {
		rootObject.singleValuedContainmentEReference = null;
		saveAndSynchronizeChanges(rootObject)
	}
	
	private def void setElementId(Identified element, String newId) {
		element.setId(newId);
		saveAndSynchronizeChanges(element);
	}
	
	private def void setSingleValuedEAttribute(Root root, Integer value) {
		root.singleValuedEAttribute = value;
		saveAndSynchronizeChanges(root);
	}
	
	private def void saveAndSynchronizeChanges(EObject object) {
		EcoreResourceBridge.saveResource(object.eResource());
		this.triggerSynchronization(VURI.getInstance(object.eResource()));
	}

	// TODO HK Unset does not produce any change event at the moment
	//@Test
	public def void testUnsetSingleValuedEAttribute() {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		unsetSingleValuedEAttribute(rootElement);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UnsetEAttribute);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
		
	//@Test
	public def void testUnsetSingleValuedNonContainmentEReference() throws Throwable {
		setSingleValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		unsetSingleValuedNonContainmentNonRootObject(rootElement);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UnsetNonContainmentEReference);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}

	@Test
	public def void testUpdateSingleValuedEAttribute() {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedEAttribute(rootElement, -1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UpdateSingleValuedEAttribute);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testCreateSingleValuedContainmentEReference() throws Throwable {
		unsetSingleValuedContainmentNonRootObject(rootElement);
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedContainmentNonRootObject(rootElement, "singleValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.CreateNonRootEObjectSingle);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteSingleValuedContainmentEReference() throws Throwable {
		setSingleValuedContainmentNonRootObject(rootElement, "singleValuedContainmentNonRoot");
		SimpleChangesTestsExecutionMonitor.reinitialize();
		unsetSingleValuedContainmentNonRootObject(rootElement);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectSingle);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceSingleValuedContainmentEReference() throws Throwable {
		setSingleValuedContainmentNonRootObject(rootElement, "singleValuedContainmentNonRoot");
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedContainmentNonRootObject(rootElement, "singleValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectSingle);
		compareMonitor.set(ChangeType.CreateNonRootEObjectSingle);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testSetSingleValuedNonContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UpdateSingleValuedNonContainmentEReference);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(nonContainmentNonRootIds.get(1), rootElement.singleValuedNonContainmentEReference.id);
		assertModelsEqual();
	}

	
	@Test
	public def void testReplaceSingleValuedNonContainmentEReference() throws Throwable {
		setSingleValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(0));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		setSingleValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.UpdateSingleValuedNonContainmentEReference);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(nonContainmentNonRootIds.get(1), rootElement.singleValuedNonContainmentEReference.id);
		assertModelsEqual();
	}
	
	
	@Test
	public def void testAddMultiValuedEAttribute() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		addMultiValuedEAttribute(rootElement, 1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.InsertEAttributeValue);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteMultiValuedEAttribute() throws Throwable {
		addMultiValuedEAttribute(rootElement, 1);
		addMultiValuedEAttribute(rootElement, 2);
		SimpleChangesTestsExecutionMonitor.reinitialize();
		removeMultiValuedEAttribute(rootElement, 1);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.RemoveEAttributeValue);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(2, rootElement.multiValuedEAttribute.get(0));
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceMultiValuedEAttribute() throws Throwable {
		addMultiValuedEAttribute(rootElement, 1);
		addMultiValuedEAttribute(rootElement, 2);
		SimpleChangesTestsExecutionMonitor.reinitialize();
		replaceMultiValuedEAttribute(rootElement, 2, 3);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		// TODO HK This should not be, should be one event
		compareMonitor.set(ChangeType.RemoveEAttributeValue);
		compareMonitor.set(ChangeType.InsertEAttributeValue);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals(3, rootElement.multiValuedEAttribute.get(1));
		assertModelsEqual();
	}
	
	@Test
	public def void testAddMultiValuedContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		addMultiValuedContainmentNonRootObject(rootElement, "multiValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.CreateNonRootEObjectInList);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteMultiValuedContainmentEReference() throws Throwable {
		addMultiValuedContainmentNonRootObject(rootElement, "multiValuedContainmentNonRootTest");
		SimpleChangesTestsExecutionMonitor.reinitialize();
		removeMultiValuedContainmentNonRootObject(rootElement, "multiValuedContainmentNonRootTest");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectInList);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceMultiValuedContainmentEReference() throws Throwable {
		addMultiValuedContainmentNonRootObject(rootElement, "multiValuedContainmentNonRootTest");
		SimpleChangesTestsExecutionMonitor.reinitialize();
		replaceMultiValuedContainmentNonRootObject(rootElement, "multiValuedContainmentNonRootTest", "multiValuedContainmentNonRootTest2");
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.DeleteNonRootEObjectInList);
		compareMonitor.set(ChangeType.CreateNonRootEObjectInList);
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertEquals("multiValuedContainmentNonRootTest2", rootElement.multiValuedContainmentEReference.last.id);
		assertModelsEqual();
	}
	
	@Test
	public def void testInsertMultiValuedNonContainmentEReference() throws Throwable {
		SimpleChangesTestsExecutionMonitor.reinitialize();
		insertMultiValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(0));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.InsertNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testRemoveMultiValuedNonContainmentEReference() throws Throwable {
		insertMultiValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		removeMultiValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1));
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.RemoveNonContainmentEReference);
		val mon = SimpleChangesTestsExecutionMonitor.instance;
		compareMonitor.assertEqualWithStatic();
		assertEquals(compareMonitor, mon);
		assertModelsEqual();
	}
	
	@Test
	public def void testReplaceMultiValuedNonContainmentEReference() throws Throwable {
		insertMultiValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(0));
		insertMultiValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1));
		SimpleChangesTestsExecutionMonitor.reinitialize();
		replaceMultiValuedNonContainmentNonRootObject(rootElement, nonContainmentNonRootIds.get(1), nonContainmentNonRootIds.get(2))
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		// TODO HK this should not be... should be one event!
		//compareMonitor.set(ChangeType.ReplaceNonContainmentEReference);
		compareMonitor.set(ChangeType.RemoveNonContainmentEReference);
		compareMonitor.set(ChangeType.InsertNonContainmentEReference);
		compareMonitor.assertEqualWithStatic();
		assertTrue(rootElement.multiValuedNonContainmentEReference.size == 2);
		assertTrue(rootElement.multiValuedNonContainmentEReference.get(0).id == nonContainmentNonRootIds.get(0));
		assertTrue(rootElement.multiValuedNonContainmentEReference.get(1).id == nonContainmentNonRootIds.get(2));
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	// TODO HK Permute operations are not supported by now? No EChange produced
	//@Test
	public def void testPermuteMultiValuedNonContainmentEReference() throws Throwable {
		for (nonRootId : nonContainmentNonRootIds) {
			insertMultiValuedNonContainmentNonRootObject(rootElement, nonRootId);
		}
		SimpleChangesTestsExecutionMonitor.reinitialize();
		permuteMultiValuedNonContamentNonRootObjects(rootElement);
		val compareMonitor = new SimpleChangesTestsExecutionMonitor();
		compareMonitor.set(ChangeType.PermuteNonContainmentEReference);
		assertEquals(compareMonitor, SimpleChangesTestsExecutionMonitor.instance);
		assertModelsEqual();
	}
	
	@Test
	public def void testDeleteEachTestModel() throws Throwable {
		assertModelExists(TEST_SOURCE_MODEL_NAME);
		deleteAndSychronizeModel(TEST_SOURCE_MODEL_NAME);
		assertModelNotExists(TEST_SOURCE_MODEL_NAME);
		assertModelNotExists(TEST_TARGET_MODEL_NAME);
	}
	
	@Test
	public def void testCreateFurtherModel() throws Throwable {
		val root = AllElementTypesFactory.eINSTANCE.createRoot();
		root.setId(FURTHER_SOURCE_TEST_MODEL_NAME);
		createAndSychronizeModel(FURTHER_SOURCE_TEST_MODEL_NAME, root);
		assertModelsEqual(FURTHER_SOURCE_TEST_MODEL_NAME, FURTHER_TARGET_TEST_MODEL_NAME);
	}
	
	@Test
	public def void testDeleteFurtherModel() throws Throwable {
		val root = AllElementTypesFactory.eINSTANCE.createRoot();
		root.setId(FURTHER_SOURCE_TEST_MODEL_NAME);
		createAndSychronizeModel(FURTHER_SOURCE_TEST_MODEL_NAME, root);
		assertModelExists(FURTHER_TARGET_TEST_MODEL_NAME);
		assertModelsEqual(FURTHER_SOURCE_TEST_MODEL_NAME, FURTHER_TARGET_TEST_MODEL_NAME);
		deleteAndSychronizeModel(FURTHER_SOURCE_TEST_MODEL_NAME);
		assertModelNotExists(FURTHER_SOURCE_TEST_MODEL_NAME);
		assertModelNotExists(FURTHER_TARGET_TEST_MODEL_NAME);
	}
}
