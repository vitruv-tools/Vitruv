package tools.vitruv.framework.tests.change.attribute

import allElementTypes.AllElementTypesFactory
import org.junit.Test

import static allElementTypes.AllElementTypesPackage.Literals.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*

class ChangeDescription2InsertEAttributeValueTest extends ChangeDescription2ChangeEAttributeTest {
	@Test
	def void testInsertEAttributeValue() {
		testInsertEAttributeValue(0, 42, -1)
	}

	@Test
	def void testMultipleInsertEAttributeValue() {
		testInsertEAttributeValue(0, 42, -1)
		testInsertEAttributeValue(1, 21, -1)
		testInsertEAttributeValue(2, 10, -1)
	}

	@Test
	def void testInsertAtPositionInsertEAttributeValue() {
		testInsertEAttributeValue(0, 42, -1)
		testInsertEAttributeValue(0, 21, 0)
		testInsertEAttributeValue(1, 10, 1)
	}

	@Test
	def testInsertEAttributeValueSequence() {
		// test
		startRecording
		rootElement.multiValuedEAttribute.add(42)
		rootElement.multiValuedEAttribute.add(55)
		rootElement.multiValuedEAttribute.add(66)

		changes.assertChangeCount(3)
		changes.claimChange(0).assertInsertEAttribute(rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
		changes.claimChange(1).assertInsertEAttribute(rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1)
		changes.claimChange(2).assertInsertEAttribute(rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2)
	}

	@Test
	def testTreeInsertMultiValuedEAttribute() {
		val recursiveRoot = AllElementTypesFactory.eINSTANCE.createRoot
		recursiveRoot.multiValuedEAttribute += 1
		recursiveRoot.multiValuedEAttribute += 2	
		startRecording
		rootElement.recursiveRoot = recursiveRoot
		
		changes.assertChangeCount(4)
		changes.claimChange(0).assertCreateAndReplaceNonRoot(recursiveRoot, rootElement, ROOT__RECURSIVE_ROOT, unresolveAndResolveRecordedEChanges)
		changes.claimChange(1).assertReplaceSingleValuedEAttribute(recursiveRoot, IDENTIFIED__ID, null, recursiveRoot.id)
		changes.claimChange(2).assertInsertEAttribute(recursiveRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 1, 0)
		changes.claimChange(3).assertInsertEAttribute(recursiveRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 2, 1)
	}

}
