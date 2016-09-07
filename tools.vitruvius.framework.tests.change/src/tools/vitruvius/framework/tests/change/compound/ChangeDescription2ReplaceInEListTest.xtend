package tools.vitruvius.framework.tests.change.compound

import allElementTypes.AllElementTypesFactory
import tools.vitruvius.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test

import static extension tools.vitruvius.framework.tests.change.util.ChangeAssertHelper.*

class ChangeDescription2ReplaceInEListTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def public void testReplaceInEContainmentList() {
		// prepare
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		val nonRoot2 = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.multiValuedContainmentEReference.add(nonRoot)
		startRecording

		// test
		this.rootElement.multiValuedContainmentEReference.remove(nonRoot)
		this.rootElement.multiValuedContainmentEReference.add(nonRoot2)

		// assert
		val changes = getChanges()
		val insertAndRemoveChange = changes.assertReplaceInEList(2)
		insertAndRemoveChange.first.assertRemoveEReference(this.rootElement,
			MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, true, true)
		insertAndRemoveChange.second.assertInsertEReference(this.rootElement,
			MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot2, 0, true, true)
	}

	@Test
	def public void testReplaceInNonEContainmentList() {
		// prepare
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		val nonRoot2 = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.multiValuedNonContainmentEReference.add(nonRoot)
		startRecording

		// test
		this.rootElement.multiValuedNonContainmentEReference.remove(nonRoot)
		this.rootElement.multiValuedNonContainmentEReference.add(nonRoot2)

		// assert
		val changes = getChanges()
		val insertAndRemoveChange = changes.assertReplaceInEList(2)
		insertAndRemoveChange.first.assertRemoveEReference(this.rootElement,
			MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, false, false)
		insertAndRemoveChange.second.assertInsertEReference(this.rootElement,
			MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot2, 0, false, false) 
	}
	
	@Test
	def public void testReplaceInAttributeList() {
		// prepare
		this.rootElement.multiValuedEAttribute.add(42)
		startRecording

		// test
		this.rootElement.multiValuedEAttribute.remove(0)
		this.rootElement.multiValuedEAttribute.add(21)

		// assert
		val changes = getChanges()
		val insertAndRemoveChange = changes.assertReplaceInEList(2)
		#[insertAndRemoveChange.first].assertRemoveEAttribute(this.rootElement,
			MULTI_VALUE_E_ATTRIBUTE_NAME, 42, 0)
		#[insertAndRemoveChange.second].assertInsertEAttribute(this.rootElement, 
			MULTI_VALUE_E_ATTRIBUTE_NAME, 21, 0)
	}
}
