package tools.vitruv.domains.sysml

import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Assert
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.papyrus.sysml14.blocks.BlocksFactory
import org.eclipse.papyrus.sysml14.blocks.Block
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.tuid.TuidUpdateListener
import java.util.ArrayList
import java.util.List

class SysMlMetamodelTests {
	private static val TEST_CLASS_NAME = "Test";
	
	@Test
	public def void testTuidCalculationForUmlElemente() {
		val clazz = UMLFactory.eINSTANCE.createClass();
		clazz.name = TEST_CLASS_NAME;
		testTuid(clazz, TEST_CLASS_NAME);
		val port = UMLFactory.eINSTANCE.createPort();
		port.name = TEST_CLASS_NAME;
		testTuid(port, TEST_CLASS_NAME);
	}
	
	@Test
	public def void testTuidCalculationForSysMlElements() {
		val block = createBlock();
		testTuid(block, TEST_CLASS_NAME);
		Assert.assertEquals(SysMlMetamodel.instance.calculateTuid(block), SysMlMetamodel.instance.calculateTuid(block.base_Class));
	}
	
	@Test
	public def void testResponsibilityChecks() {
		val block = createBlock();
		Assert.assertTrue(SysMlMetamodel.instance.hasMetaclassInstances(#[block]));
		Assert.assertTrue(SysMlMetamodel.instance.hasMetaclassInstances(#[block.base_Class]));
		Assert.assertTrue(SysMlMetamodel.instance.hasTUID(block));
		Assert.assertTrue(SysMlMetamodel.instance.hasTUID(block.base_Class));
	}
	
	@Test
	public def void testTuidUpdate() {
		// We have to initialize the metamodel, so that it is registered for TUID calculation
		SysMlMetamodel.instance;
		val List<String> tuids = new ArrayList<String>();
		val dummyTuidUpdateListener = new TuidUpdateListener() {
			override performPreAction(TUID oldTUID) {
				tuids.add(oldTUID.toString);
			}
			
			override performPostAction(TUID newTuid) {
				tuids.add(newTuid.toString);
			}
		}
		TuidManager.instance.addTuidUpdateListener(dummyTuidUpdateListener);
		val block = createBlock();
		block.base_Class.name = "old";
		TuidManager.instance.registerObjectUnderModification(block);
		block.base_Class.name = "new";
		TuidManager.instance.updateTuidsOfRegisteredObjects();
		TuidManager.instance.flushRegisteredObjectsUnderModification();
		Assert.assertEquals(2, tuids.size);
		testTuid(tuids.get(0), "old");
		testTuid(tuids.get(1), "new");
	}
	
	private def Block createBlock() {
		val clazz = UMLFactory.eINSTANCE.createClass();
		clazz.name = TEST_CLASS_NAME;
		val block = BlocksFactory.eINSTANCE.createBlock();
		block.base_Class = clazz;
		return block;
	}
	
	private def void testTuid(EObject object, String expectedName) {
		assertTuid(object, UMLPackage.eNS_URI, UMLPackage.Literals.NAMED_ELEMENT__NAME.name + "=" + expectedName);
	}
	
	private def void testTuid(String tuid, String expectedName) {
		assertTuid(tuid, UMLPackage.eNS_URI, UMLPackage.Literals.NAMED_ELEMENT__NAME.name + "=" + expectedName);
	}
	
	private def void assertTuid(EObject object, String expectedNamespaceUri, String expectedIdentifier) {
		val tuid = SysMlMetamodel.getInstance().calculateTuid(object).toString;
		assertTuid(tuid, expectedNamespaceUri, expectedIdentifier);
	}
	
	private def void assertTuid(String tuid, String expectedNamespaceUri, String expectedIdentifier) {
		val tuidFragments = tuid.split("#");
		Assert.assertEquals(3, tuidFragments.length);
		Assert.assertEquals(expectedNamespaceUri, tuidFragments.get(0));
		Assert.assertNotNull(tuidFragments.get(1));
		Assert.assertEquals(expectedIdentifier, tuidFragments.get(2));
	}
	
	@Test
	public def void testSingletonRealization() {
		val instance1 = SysMlMetamodel.instance;
		val instance2 = SysMlMetamodel.instance;
		Assert.assertEquals(instance1, instance2);
	}
}