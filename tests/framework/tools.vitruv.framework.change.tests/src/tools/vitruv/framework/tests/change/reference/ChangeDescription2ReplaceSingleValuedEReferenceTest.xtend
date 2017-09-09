package tools.vitruv.framework.tests.change.reference

import allElementTypes.AllElementTypesFactory
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*;

class ChangeDescription2ReplaceSingleValuedEReferenceTest extends ChangeDescription2ChangeTransformationTest {
	@Test
	def void testReplaceSingleValuedEReferenceContainment() {
		// prepare 
		startRecording

		// test
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.singleValuedContainmentEReference = nonRoot

		// assert
		val changes = getChanges()
		changes.assertSetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id)
			.assertEmpty;
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		val replaceNonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.singleValuedContainmentEReference = replaceNonRoot

		// assert
		val changes = getChanges()
		changes.assertCreateAndReplaceAndDeleteNonRoot(nonRoot, replaceNonRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, this.rootElement, true)
			.assertReplaceSingleValuedEAttribute(replaceNonRoot, IDENTIFIED__ID, null, replaceNonRoot.id)
			.assertEmpty;
	}
	
	@Test
	def void testReplaceExistingSingleValuedEReferenceContainmentWithDefault() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		this.rootElement.singleValuedContainmentEReference = nonRoot
		startRecording
		
		//test
		this.rootElement.singleValuedContainmentEReference = null
		val changes = getChanges()
		changes.assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true)
			.assertEmpty;
	}

	@Test
	def void testRemoveContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)
		
		// test
		EcoreUtil.delete(nonRoot)
		
		// assert
		val changes = getChanges()
		changes.assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,	nonRoot, true, true)
			.assertEmpty;
	}

	@Test
	def void testSetSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		this.rootElement.singleValuedNonContainmentEReference = nonRoot

		// assert
		val changes = getChanges()
		changes.assertSetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, false, false)
			.assertEmpty;
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceNonContainment() {
		// preapare
		val nonRoot = createAndAddNonRootToRootContainer(false)
		this.rootElement.singleValuedNonContainmentEReference = nonRoot
		val replaceNonRoot = createAndAddNonRootToRootContainer(true)
		
		// test
		this.rootElement.singleValuedNonContainmentEReference = replaceNonRoot

		// assert
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, replaceNonRoot, false)
			.assertEmpty;
	}

	@Test
	def void testRemoveNonContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		this.rootElement.singleValuedNonContainmentEReference = nonRoot
		startRecording;
		
		// test
		EcoreUtil.delete(nonRoot)
		
		// assert
		val changes = getChanges()
		changes.assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, false, false)
			.assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true)
			.assertEmpty;
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceNonContainmentWithDefault() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false);
		this.rootElement.singleValuedNonContainmentEReference = nonRoot;
		startRecording;
		
		// test
		this.rootElement.singleValuedNonContainmentEReference = null
		
		// assert
		val changes = getChanges()
		changes.assertUnsetSingleValuedEReference(this.rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE,	nonRoot, false, false)
			.assertEmpty;
	}
	
	@Test
	def void testUnsetExistingSingleValuedEReferenceContainment() {
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot;
		this.rootElement.singleValuedUnsettableContainmentEReference = nonRoot;
		startRecording;
		
		this.rootElement.eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
		changes.assertChangeCount(1);
		val innerChanges = changes.claimChange(0).assertExplicitUnsetEReference.changes
		innerChanges.assertChangeCount(2);
		innerChanges.assertReplaceAndDeleteNonRoot(nonRoot, rootElement, ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
			.assertEmpty;
	}
	
	@Test
	def void testUnsetReplaceExistingSingleValuedEReferenceNonContainment() {
		val nonRoot = createAndAddNonRootToRootContainer(false)
		this.rootElement.singleValuedUnsettableNonContainmentEReference = nonRoot;
		startRecording;
		
		this.rootElement.eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE)
		changes.assertChangeCount(1);
		val innerChanges = changes.claimChange(0).assertExplicitUnsetEReference.changes
		innerChanges.assertChangeCount(1);
		changes.claimChange(0).assertExplicitUnsetEReference.atomicChanges.assertChangeCount(1);
		innerChanges.assertReplaceSingleValuedEReference(rootElement,
			ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, nonRoot, null, false)
			.assertEmpty;
	}

}
