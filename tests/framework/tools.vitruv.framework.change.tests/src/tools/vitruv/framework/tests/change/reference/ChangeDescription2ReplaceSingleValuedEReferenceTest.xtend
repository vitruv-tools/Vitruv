package tools.vitruv.framework.tests.change.reference

import allElementTypes.AllElementTypesFactory
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import org.junit.Ignore

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
		changes.get(0).assertSetSingleValuedEReference(nonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true)
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
		changes.get(0).assertReplaceSingleValuedEReference(nonRoot, replaceNonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true, true)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceContainmentWithDefault() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		this.rootElement.singleValuedContainmentEReference = nonRoot
		startRecording
		
		//test
		this.rootElement.singleValuedContainmentEReference = null
		val changes = getChanges()
		changes.get(0).assertUnsetSingleValuedEReference(nonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true)
	}

	@Ignore
	@Test
	def public void testUnsetExistingSingleValuedEReferenceContainment() {
		val nonRoot = createAndAddNonRootToContainment(true)
		this.rootElement.eUnset(this.rootElement.getFeatureByName(SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME))
		val subtractiveChanges = claimChange(0).assertExplicitUnset.subtractiveChanges
		subtractiveChanges.get(0).assertUnsetSingleValuedEReference(nonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true)
	}

	@Test
	def public void testRemoveContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)
		
		// test
		EcoreUtil.delete(nonRoot)
		
		// assert
		val changes = getChanges()
		changes.get(0).assertUnsetSingleValuedEReference(nonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true)
	}

	@Test
	def public void testSetSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(true)

		// test
		this.rootElement.singleValuedNonContainmentEReference = nonRoot

		// assert
		val changes = getChanges()
		changes.get(0).assertSetSingleValuedEReference(nonRoot, SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, false, false)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceNonContainment() {
		// preapare
		val nonRoot = createAndAddNonRootToRootContainer(false)
		this.rootElement.singleValuedNonContainmentEReference = nonRoot
		val replaceNonRoot = createAndAddNonRootToRootContainer(true)
		
		// test
		this.rootElement.singleValuedNonContainmentEReference = replaceNonRoot

		// assert
		val changes = getChanges()
		changes.get(0).assertReplaceSingleValuedEReference(nonRoot, replaceNonRoot,
			SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, this.rootElement, false, false, false)
	}

	@Test
	def public void testRemoveNonContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		this.rootElement.singleValuedNonContainmentEReference = nonRoot
		startRecording;
		
		// test
		EcoreUtil.delete(nonRoot)
		
		// assert
		val changes = getChanges()
		changes.get(0).assertUnsetSingleValuedEReference(nonRoot, SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, false, false)
		changes.get(1).assertUnsetSingleValuedEReference(nonRoot, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true)
	}

	@Test
	def public void testReplaceExistingSingleValuedEReferenceNonContainmentWithDefault() {
		// prepare
		val nonRoot = createAndAddNonRootToContainment(false);
		this.rootElement.singleValuedNonContainmentEReference = nonRoot;
		startRecording;
		
		// test
		this.rootElement.singleValuedNonContainmentEReference = null
		
		// assert
		val changes = getChanges()
		changes.get(0).assertUnsetSingleValuedEReference(nonRoot, SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, false, false)
	}

	@Ignore
	@Test
	def public void testUnsetReplaceExistingSingleValuedEReferenceNonContainment() {
		val nonRoot = createAndAddNonRootToContainment(true)
		this.rootElement.eUnset(this.rootElement.getFeatureByName(SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME))
		getChanges()
		val subtractiveChanges = claimChange(0).assertExplicitUnset.subtractiveChanges
		subtractiveChanges.get(0).assertUnsetSingleValuedEReference(nonRoot,
			SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, this.rootElement, false, false)
	}

}
