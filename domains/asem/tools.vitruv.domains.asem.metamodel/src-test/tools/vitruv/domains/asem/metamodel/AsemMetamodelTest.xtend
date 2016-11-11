package tools.vitruv.domains.asem.metamodel

import org.junit.Test
import org.junit.Assert
import edu.kit.ipd.sdq.ASEM.ASEMPackage
import edu.kit.ipd.sdq.ASEM.base.Identifiable
import edu.kit.ipd.sdq.ASEM.base.Named
import edu.kit.ipd.sdq.ASEM.base.BasePackage
import edu.kit.ipd.sdq.ASEM.classifiers.ClassifiersFactory
import edu.kit.ipd.sdq.ASEM.dataexchange.DataexchangeFactory
import edu.kit.ipd.sdq.ASEM.primitivetypes.PrimitivetypesFactory
import org.eclipse.emf.ecore.EObject

class AsemMetamodelTest {
	private static val TEST_NAME = "Test";
	
	@Test
	public def void testResponsibilityChecks() {
		val clazz = ClassifiersFactory.eINSTANCE.createClass();
		Assert.assertTrue(AsemMetamodel.instance.hasMetaclassInstances(#[clazz]));
		Assert.assertTrue(AsemMetamodel.instance.hasTUID(clazz));
	}
	
	@Test
	def public void testTuidInClassifiersPackage() {
		testNamedTuid(ClassifiersFactory.eINSTANCE.createClass());
		testNamedTuid(ClassifiersFactory.eINSTANCE.createComposedType());
		testNamedTuid(ClassifiersFactory.eINSTANCE.createModule());
	}
	
	@Test
	def public void testTuidInDataExchangePackage() {
		testNamedTuid(DataexchangeFactory.eINSTANCE.createMethod());
		testNamedTuid(DataexchangeFactory.eINSTANCE.createMessage());
		testNamedTuid(DataexchangeFactory.eINSTANCE.createParameter());
		testNamedTuid(DataexchangeFactory.eINSTANCE.createReturnType());
		testNamedTuid(DataexchangeFactory.eINSTANCE.createVariable());
	}
	
	@Test
	def public void testTuidInPrimitiveTypesPackage() {
		testNamedTuid(PrimitivetypesFactory.eINSTANCE.createBooleanType());
		testNamedTuid(PrimitivetypesFactory.eINSTANCE.createContinuousType());
		testNamedTuid(PrimitivetypesFactory.eINSTANCE.createPrimitiveType());
		testNamedTuid(PrimitivetypesFactory.eINSTANCE.createSignedDiscreteType());
		testNamedTuid(PrimitivetypesFactory.eINSTANCE.createUnsignedDiscreteType());
	}
	
	private def testNamedTuid(Named named) {
		named.name = TEST_NAME;
		testTuid(named);
	}
	
	private def dispatch testTuid(Identifiable identifiable) {
		assertTuid(identifiable, ASEMPackage.eNS_URI, BasePackage.Literals.IDENTIFIABLE__ID.name + "=" + identifiable.id);
	}

	private def dispatch testTuid(Named named) {
		assertTuid(named, ASEMPackage.eNS_URI, BasePackage.Literals.NAMED__NAME.name + "=" + named.name);
	}

	
	private def void assertTuid(EObject object, String expectedNamespaceUri, String expectedIdentifier) {
		val tuidFragments = AsemMetamodel.getInstance().calculateTuid(object).toString.split("#");
		Assert.assertEquals(3, tuidFragments.length);
		Assert.assertEquals(expectedNamespaceUri, tuidFragments.get(0));
		Assert.assertNotNull(tuidFragments.get(1));
		Assert.assertEquals(expectedIdentifier, tuidFragments.get(2));
	}
	
	@Test
	public def void testSingletonRealization() {
		val instance1 = AsemMetamodel.instance;
		val instance2 = AsemMetamodel.instance;
		Assert.assertEquals(instance1, instance2);
	}
}