package tools.vitruv.framework.tests.change.integration

import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import org.junit.Assert

class ChangeDescriptionComplexSequencesTest extends ChangeDescription2ChangeTransformationTest {

	/**
	 * Changes that overwrite each other between two change propagation triggers are not recognized by EMF.
	 */
	@Test
	def public void testOverwritingSequence() {
		// prepare
		startRecording
		
		// test
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
		this.rootElement.singleValuedContainmentEReference = nonRoot;
		this.rootElement.singleValuedContainmentEReference = null;
		this.rootElement.singleValuedContainmentEReference = nonRoot;
		//this.rootElement.singleValuedContainmentEReference = null;
		
		// assert
		val changes = getChanges();
		Assert.assertEquals(4, getChanges().size);
		changes.get(0).assertSetSingleValuedEReference(nonRoot, AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE.name,
			this.rootElement, true, true);
		changes.get(2).assertUnsetSingleValuedEReference(nonRoot, AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE.name,
			this.rootElement, true, true);
		changes.get(3).assertSetSingleValuedEReference(nonRoot, AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE.name,
			this.rootElement, true, true);
	}
	
	
	@Test
	def public void testInsertTreeInContainment() {
		// prepare
		this.rootElement.nonRootObjectContainerHelper = null;
		startRecording
		
		// test
		val nonRootObjectsContainer = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper();
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
		nonRootObjectsContainer.nonRootObjectsContainment += nonRoot;
		this.rootElement.nonRootObjectContainerHelper = nonRootObjectsContainer;
				
		// assert
		Assert.assertEquals(4, getChanges().size);
		val containerChange = claimChange(0);
		containerChange.assertSetSingleValuedEReference(nonRootObjectsContainer, AllElementTypesPackage.Literals.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER.name,
			rootElement, true, true);
		val nonRootChange = claimChange(2);
		nonRootChange.assertInsertEReference(nonRootObjectsContainer, AllElementTypesPackage.Literals.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT.name,
			nonRoot, 0, true, true);
		
	}
	
}
