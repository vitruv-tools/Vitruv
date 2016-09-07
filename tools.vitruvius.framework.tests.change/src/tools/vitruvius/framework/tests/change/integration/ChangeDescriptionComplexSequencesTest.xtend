package tools.vitruvius.framework.tests.change.integration

import org.junit.Test

import static extension tools.vitruvius.framework.tests.change.util.ChangeAssertHelper.*
import tools.vitruvius.framework.tests.change.ChangeDescription2ChangeTransformationTest
import allElementTypes.AllElementTypesFactory
import tools.vitruvius.framework.change.echange.AdditiveEChange
import allElementTypes.AllElementTypesPackage
import org.junit.Assert

class ChangeDescriptionComplexSequencesTest extends ChangeDescription2ChangeTransformationTest {

	/**
	 * Changes that overwrite each other between two synchronization triggers are not recognized by EMF.
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
		Assert.assertEquals(5, getChanges().size);
		(claimChange(0) as AdditiveEChange<?>).assertOldAndNewValue(null, nonRoot);
		(claimChange(2) as AdditiveEChange<?>).assertOldAndNewValue(nonRoot, null);
		(claimChange(3) as AdditiveEChange<?>).assertOldAndNewValue(null, nonRoot);
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
		containerChange.assertReplaceSingleValueEReference(null, nonRootObjectsContainer);
		val nonRootChange = claimChange(2);
		nonRootChange.assertInsertEReference(nonRootObjectsContainer, AllElementTypesPackage.Literals.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT.name,
			nonRoot, 0, true, false);
		
	}
	
}
