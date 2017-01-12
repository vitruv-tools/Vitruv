package tools.vitruv.framework.tests.change.attribute

import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*;
import allElementTypes.AllElementTypesFactory

class ChangeDescription2InsertEAttributeValueTest extends ChangeDescription2ChangeEAttributeTest {
	@Test
	def void testInsertEAttributeValue() {
		this.testInsertEAttributeValue(0, 42, -1)
	}

	@Test
	def void testMultipleInsertEAttributeValue() {
		this.testInsertEAttributeValue(0, 42, -1)
		this.testInsertEAttributeValue(1, 21, -1)
		this.testInsertEAttributeValue(2, 10, -1)
	}

	@Test
	def void testInsertAtPositionInsertEAttributeValue() {
		this.testInsertEAttributeValue(0, 42, -1)
		this.testInsertEAttributeValue(0, 21, 0)
		this.testInsertEAttributeValue(1, 10, 1)
	}

	@Test
	def public testInsertEAttributeValueSequence() {
		// test
		startRecording
		this.rootElement.multiValuedEAttribute.add(42)
		this.rootElement.multiValuedEAttribute.add(55)
		this.rootElement.multiValuedEAttribute.add(66)

		changes.assertChangeCount(3);
		changes.claimChange(0).assertInsertEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
		changes.claimChange(1).assertInsertEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1)
		changes.claimChange(2).assertInsertEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2)
	}

	@Test
	def public testTreeInsertMultiValuedEAttribute() {
		val recursiveRoot = AllElementTypesFactory.eINSTANCE.createRoot();
		recursiveRoot.multiValuedEAttribute += 1;
		recursiveRoot.multiValuedEAttribute += 2;
		startRecording();
		this.rootElement.recursiveRoot = recursiveRoot;
		
		changes.assertChangeCount(4);
		changes.claimChange(0).assertReplaceSingleValuedEReference(this.rootElement, ROOT__RECURSIVE_ROOT, null, recursiveRoot, true);
		changes.claimChange(1).assertReplaceSingleValuedEAttribute(recursiveRoot, IDENTIFIED__ID, null, recursiveRoot.id);
		changes.claimChange(2).assertInsertEAttribute(recursiveRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 1, 0);
		changes.claimChange(3).assertInsertEAttribute(recursiveRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 2, 1);
	}

}
