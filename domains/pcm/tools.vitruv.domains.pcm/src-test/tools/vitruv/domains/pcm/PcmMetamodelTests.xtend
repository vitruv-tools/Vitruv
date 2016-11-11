package tools.vitruv.domains.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.core.entity.NamedElement
import de.uka.ipd.sdq.identifier.Identifier
import org.palladiosimulator.pcm.PcmPackage
import de.uka.ipd.sdq.identifier.IdentifierPackage
import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.palladiosimulator.pcm.core.entity.EntityPackage
import org.junit.Test

class PcmMetamodelTests {
	private static val TEST_NAME = "Test";
	
	@Test
	public def void testResponsibilityChecks() {
		val component = RepositoryFactory.eINSTANCE.createBasicComponent();
		val pcmMetamodel = new PcmMetamodel();
		Assert.assertTrue("Metamodel support only " + pcmMetamodel.nsURIs + ", but not " + component.eClass.EPackage.nsURI + " of component",
			pcmMetamodel.hasMetaclassInstances(#[component])
		);
		Assert.assertTrue("Metamodel has TUID only for elements of " + pcmMetamodel.nsURIs + ", but not of component's " + component.eClass.EPackage.nsURI,
			pcmMetamodel.hasTUID(component)
		);
	}
	
	@Test
	def public void testIdentifierTuidInRepositoryPackage() {
		testIdentifierTuid(RepositoryFactory.eINSTANCE.createBasicComponent());
		testIdentifierTuid(RepositoryFactory.eINSTANCE.createCompositeComponent());
		testIdentifierTuid(RepositoryFactory.eINSTANCE.createCollectionDataType());
		testIdentifierTuid(RepositoryFactory.eINSTANCE.createOperationInterface());
		testIdentifierTuid(RepositoryFactory.eINSTANCE.createOperationProvidedRole());
	}
	
	@Test
	def public void testNameTuidInRepositoryPackage() {
		testNamedTuid(RepositoryFactory.eINSTANCE.createParameter());
	}
	
	private def testIdentifierTuid(Identifier identifier) {
		identifier.id = TEST_NAME;
		assertTuid(identifier, PcmPackage.eNS_URI, IdentifierPackage.Literals.IDENTIFIER__ID.name + "=" + identifier.id);
	}
	
	private def testNamedTuid(NamedElement named) {
		named.entityName = TEST_NAME;
		assertTuid(named, PcmPackage.eNS_URI, EntityPackage.Literals.NAMED_ELEMENT__ENTITY_NAME.name + "=" + named.entityName);
	}
	
	private def void assertTuid(EObject object, String expectedNamespaceUri, String expectedIdentifier) {
		val tuidFragments = new PcmMetamodel().calculateTuid(object).toString.split("#");
		Assert.assertEquals(3, tuidFragments.length);
		Assert.assertEquals(expectedNamespaceUri, tuidFragments.get(0));
		Assert.assertNotNull(tuidFragments.get(1));
		Assert.assertEquals(expectedIdentifier, tuidFragments.get(2));
	}
	
}