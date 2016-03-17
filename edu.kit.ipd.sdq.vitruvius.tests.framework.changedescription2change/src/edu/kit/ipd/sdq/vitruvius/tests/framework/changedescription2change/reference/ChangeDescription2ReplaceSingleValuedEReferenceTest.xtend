package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference

import allElementTypes.AllElementTypesFactory
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2ReplaceSingleValuedEReferenceTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def public void testReplaceSingleValuedEReferenceContainment() {
		// prepare 
		startRecording

		// test
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.singleValuedContainmentEReference = nonRoot

		// assert
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(null, nonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true, true)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		val replaceNonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.singleValuedContainmentEReference = replaceNonRoot

		// assert
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(nonRoot, replaceNonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true, true)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceContainmentWithDefault() {
		val nonRoot = createAndAddNonRootToContainment(true)
		this.rootElement.singleValuedContainmentEReference = null
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(nonRoot, null, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, false, true)
	}

	@Test
	def public void testUnsetExistingSingleValuedEReferenceContainment() {
		val nonRoot = createAndAddNonRootToContainment(true)
		this.rootElement.eUnset(this.rootElement.getFeautreByName(SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME))
		val changes = getChanges()
		val subtractiveChanges = changes.assertExplicitUnset
		subtractiveChanges.assertReplaceSingleValuedEReference(nonRoot, null, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, false, true)
	}

	@Test
	def public void testRemoveContainmentReferenceWithDelete() {
		val nonRoot = createAndAddNonRootToContainment(true)
		EcoreUtil.delete(nonRoot)
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(nonRoot, null, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, false, true)
	}

	@Test
	def public void testReplaceSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		this.rootElement.singleValuedNonContainmentEReference = nonRoot

		// assert
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(null, nonRoot, SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, false, true, false)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceNonContainment() {
		// preapare
		val nonRoot = createAndAddNonRootToContainment(false)
		this.rootElement.singleValuedNonContainmentEReference = nonRoot
		val replaceNonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		startRecording

		// test
		this.rootElement.singleValuedNonContainmentEReference = replaceNonRoot

		// assert
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(nonRoot, replaceNonRoot,
			SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, this.rootElement, false, false, false)
	}

	@Test
	def public void testRemoveNonContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		this.rootElement.singleValuedNonContainmentEReference = nonRoot
		// test
		EcoreUtil.delete(nonRoot)
		// assert
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(nonRoot, null, SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, false, false, false)
		changes.assertReplaceSingleValuedEReference(nonRoot, null, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, false, true)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceNonContainmentWithDefault() {
		val nonRoot = createAndAddNonRootToContainment(true)
		this.rootElement.singleValuedNonContainmentEReference = null
		val changes = getChanges()
		changes.assertReplaceSingleValuedEReference(nonRoot, null, SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, false, false, false)
	}

	@Test
	def public void testUnsetReplaceExistingSingleValuedEReferenceNonContainment() {
		val nonRoot = createAndAddNonRootToContainment(true)
		this.rootElement.eUnset(this.rootElement.getFeautreByName(SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME))
		val changes = getChanges()
		val subtractiveChanges = changes.assertExplicitUnset()
		subtractiveChanges.assertReplaceSingleValuedEReference(nonRoot, null,
			SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, this.rootElement, false, false, false)
	}

}
