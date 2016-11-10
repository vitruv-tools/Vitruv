package tools.vitruv.domains.sysml

import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Assert
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.papyrus.sysml14.blocks.BlocksFactory

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
	public def void testTuidCalculationForSysMlElemente() {
		val clazz = UMLFactory.eINSTANCE.createClass();
		clazz.name = TEST_CLASS_NAME;
		val block = BlocksFactory.eINSTANCE.createBlock();
		block.base_Class = clazz;
		testTuid(block, TEST_CLASS_NAME);
	}
	
	private def void testTuid(EObject object, String expectedName) {
		assertTuid(object, UMLPackage.eNS_URI, UMLPackage.Literals.NAMED_ELEMENT__NAME.name + "=" + expectedName);
	}
	
	private def void assertTuid(EObject object, String expectedNamespaceUri, String expectedIdentifier) {
		val tuidFragments = SysMlMetamodel.getInstance().calculateTuid(object).toString.split("#");
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