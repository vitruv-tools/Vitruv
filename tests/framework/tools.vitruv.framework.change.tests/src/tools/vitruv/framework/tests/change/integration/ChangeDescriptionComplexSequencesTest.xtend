package tools.vitruv.framework.tests.change.integration

import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import allElementTypes.AllElementTypesFactory
import static allElementTypes.AllElementTypesPackage.Literals.*;

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
		changes.assertChangeCount(4)
		changes.claimChange(0).assertSetSingleValuedEReference(nonRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			this.rootElement, true, true);
		changes.claimChange(2).assertUnsetSingleValuedEReference(nonRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			this.rootElement, true, true);
		changes.claimChange(3).assertSetSingleValuedEReference(nonRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
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
		changes.assertChangeCount(3);
		val containerChange = changes.claimChange(0);
		containerChange.assertSetSingleValuedEReference(nonRootObjectsContainer, ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER,
			rootElement, true, true);
		val nonRootChange = changes.claimChange(2);
		nonRootChange.assertCreateAndInsertNonRoot(nonRootObjectsContainer, NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT,
			nonRoot, 0);
		
	}
	
}
