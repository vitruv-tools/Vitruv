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
	def void testOverwritingSequence() {
		// prepare
		startRecording
		
		// test
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
		this.rootElement.singleValuedContainmentEReference = nonRoot;
		this.rootElement.singleValuedContainmentEReference = null;
		this.rootElement.singleValuedContainmentEReference = nonRoot;
				
		// assert
		changes.assertChangeCount(5)
		changes.assertSetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true, false)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
			.assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, false, false)
			.assertSetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, false, false)
			.assertEmpty;
		// There is no 5th change setting the ID again, because the element is stored in a ChangeDescription in between, which means 
		// that is always contained somewhere. When editing real models, such a problem will not arise
		// changes.claimChange(4).assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id);
	}
	
	
	@Test
	def void testInsertTreeInContainment() {
		// prepare
		this.rootElement.nonRootObjectContainerHelper = null;
		startRecording
		
		// test
		val nonRootObjectsContainer = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper();
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
		nonRootObjectsContainer.nonRootObjectsContainment += nonRoot;
		this.rootElement.nonRootObjectContainerHelper = nonRootObjectsContainer;
				
		// assert
		changes.assertChangeCount(6);
		changes.assertSetSingleValuedEReference(rootElement, ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER, nonRootObjectsContainer, true, true, false)
			.assertReplaceSingleValuedEAttribute(nonRootObjectsContainer, IDENTIFIED__ID, null, nonRootObjectsContainer.id, false, false)
			.assertCreateAndInsertNonRoot(nonRootObjectsContainer, NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT, nonRoot, 0, false)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
			.assertEmpty;
	}
	
	@Test
	def void testInsertComplexTreeInContainment() {
		// prepare
		this.rootElement.recursiveRoot = null;
		
		startRecording
		
		// test
		val secondRoot = AllElementTypesFactory.eINSTANCE.createRoot();
		val nonRootObjectsContainer = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper();
		secondRoot.nonRootObjectContainerHelper = nonRootObjectsContainer;
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
		secondRoot.singleValuedNonContainmentEReference = nonRoot;
		nonRootObjectsContainer.nonRootObjectsContainment += nonRoot;
		this.rootElement.recursiveRoot = secondRoot;
				
		// assert
		changes.assertChangeCount(10);
		changes.assertSetSingleValuedEReference(rootElement, ROOT__RECURSIVE_ROOT, secondRoot, true, true, false)
			.assertReplaceSingleValuedEAttribute(secondRoot, IDENTIFIED__ID, null, secondRoot.id, false, false)
			.assertSetSingleValuedEReference(secondRoot, ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER, nonRootObjectsContainer, true, true, false)
			.assertReplaceSingleValuedEAttribute(nonRootObjectsContainer, IDENTIFIED__ID, null, nonRootObjectsContainer.id, false, false)
			.assertCreateAndInsertNonRoot(nonRootObjectsContainer, NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT, nonRoot, 0, false)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
			.assertReplaceSingleValuedEReference(secondRoot, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, null, nonRoot, false, false, false)
			.assertEmpty;		
	}
	
}
