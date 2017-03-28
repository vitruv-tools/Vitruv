package tools.vitruv.framework.tests.change.integration

import org.junit.Test

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import allElementTypes.AllElementTypesFactory
import static allElementTypes.AllElementTypesPackage.Literals.*;
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*

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
				
		// assert
		changes.assertChangeCount(4)
		changes.claimChange(0).assertSetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, true, true);
		changes.claimChange(1).assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id);
		changes.claimChange(2).assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, true, true);
		changes.claimChange(3).assertSetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, true, true);
		// There is no 5th change setting the ID again, because the element is stored in a ChangeDescription in between, which means 
		// that is always contained somewhere. When editing real models, such a problem will not arise
		// changes.claimChange(4).assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id);
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
		changes.assertChangeCount(4);
		val containerChange = changes.claimChange(0);
		containerChange.assertSetSingleValuedEReference(rootElement, ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER,
			nonRootObjectsContainer, true, true);
		changes.claimChange(1).assertReplaceSingleValuedEAttribute(nonRootObjectsContainer, IDENTIFIED__ID, null, nonRootObjectsContainer.id);
		val nonRootChange = changes.claimChange(2);
		nonRootChange.assertCreateAndInsertNonRoot(nonRootObjectsContainer, NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT,
			nonRoot, 0);
		changes.claimChange(3).assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id);
		
		
	}
	
}
