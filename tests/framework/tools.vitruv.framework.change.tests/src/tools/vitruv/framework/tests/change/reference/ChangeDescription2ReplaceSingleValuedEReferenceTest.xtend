package tools.vitruv.framework.tests.change.reference

import allElementTypes.AllElementTypesFactory
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*

class ChangeDescription2ReplaceSingleValuedEReferenceTest extends ChangeDescription2ChangeTransformationTest {
	@Test
	def void testReplaceSingleValuedEReferenceContainment() {
		// prepare 
		startRecording

		// test
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		rootElement.singleValuedContainmentEReference = nonRoot

		// assert
		val changes = changes
		changes.get(0).assertSetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot,
			true, true)
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		val replaceNonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		rootElement.singleValuedContainmentEReference = replaceNonRoot

		// assert
		val changes = getChanges()
		changes.get(0).assertCreateAndReplaceAndDeleteNonRoot(nonRoot, replaceNonRoot,
			ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, rootElement, true)
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceContainmentWithDefault() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		rootElement.singleValuedContainmentEReference = nonRoot
		startRecording

		// test
		rootElement.singleValuedContainmentEReference = null
		val changes = changes
		changes.get(0).assertUnsetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, true, true)
	}

	@Test
	def void testRemoveContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		EcoreUtil::delete(nonRoot)

		// assert
		val changes = changes
		changes.get(0).assertUnsetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, true, true)
	}

	@Test
	def void testSetSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		rootElement.singleValuedNonContainmentEReference = nonRoot

		// assert
		val changes = changes
		changes.get(0).assertSetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE,
			nonRoot, false, false)
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceNonContainment() {
		// preapare
		val nonRoot = createAndAddNonRootToRootContainer(false)
		rootElement.singleValuedNonContainmentEReference = nonRoot
		val replaceNonRoot = createAndAddNonRootToRootContainer(true)

		// test
		rootElement.singleValuedNonContainmentEReference = replaceNonRoot

		// assert
		val changes = changes
		changes.get(0).assertReplaceSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE,
			nonRoot, replaceNonRoot, false)
	}

	@Test
	def void testRemoveNonContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		rootElement.singleValuedNonContainmentEReference = nonRoot
		startRecording

		// test
		EcoreUtil::delete(nonRoot)

		// assert
		val changes = changes
		changes.get(0).assertUnsetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE,
			nonRoot, false, false)
		changes.get(1).assertUnsetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, true, true)
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceNonContainmentWithDefault() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		rootElement.singleValuedNonContainmentEReference = nonRoot
		startRecording

		// test
		rootElement.singleValuedNonContainmentEReference = null

		// assert
		val changes = changes
		changes.get(0).assertUnsetSingleValuedEReference(rootElement, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE,
			nonRoot, false, false)
	}

	@Test
	def void testUnsetExistingSingleValuedEReferenceContainment() {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		rootElement.singleValuedUnsettableContainmentEReference = nonRoot
		startRecording

		rootElement.eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
		changes.assertChangeCount(1)
		val innerChanges = changes.claimChange(0).assertExplicitUnsetEReference.changes
		innerChanges.assertChangeCount(1)
		innerChanges.get(0).assertReplaceAndDeleteNonRoot(nonRoot, rootElement,
			ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
	}

	@Test
	def void testUnsetReplaceExistingSingleValuedEReferenceNonContainment() {
		val nonRoot = createAndAddNonRootToRootContainer(false)
		rootElement.singleValuedUnsettableNonContainmentEReference = nonRoot
		startRecording

		rootElement.eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE)
		changes.assertChangeCount(1)
		val innerChanges = changes.claimChange(0).assertExplicitUnsetEReference.changes
		innerChanges.assertChangeCount(1)
		changes.claimChange(0).assertExplicitUnsetEReference.atomicChanges.assertChangeCount(1)
		innerChanges.get(0).assertReplaceSingleValuedEReference(rootElement,
			ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, nonRoot, null, false)
	}

}
