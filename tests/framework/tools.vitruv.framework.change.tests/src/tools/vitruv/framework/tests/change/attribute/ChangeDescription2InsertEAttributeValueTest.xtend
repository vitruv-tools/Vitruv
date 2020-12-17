package tools.vitruv.framework.tests.change.attribute

import allElementTypes.AllElementTypesFactory

import static allElementTypes.AllElementTypesPackage.Literals.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import org.junit.jupiter.api.Test

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
	def void testInsertEAttributeValueSequence() {
		// test
		startRecording
		this.rootElement.multiValuedEAttribute.add(42)
		this.rootElement.multiValuedEAttribute.add(55)
		this.rootElement.multiValuedEAttribute.add(66)

		changes.assertChangeCount(3);
		changes.assertInsertEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0, false)
			.assertInsertEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1, false)
			.assertInsertEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2, false)
			.assertEmpty
	}

	@Test
	def void testTreeInsertMultiValuedEAttribute() {
		val recursiveRoot = AllElementTypesFactory.eINSTANCE.createRoot();
		recursiveRoot.multiValuedEAttribute += 1;
		recursiveRoot.multiValuedEAttribute += 2;	
		startRecording();
		this.rootElement.recursiveRoot = recursiveRoot;
		
		changes.assertChangeCount(5);
		changes.assertCreateAndReplaceNonRoot(recursiveRoot, this.rootElement, ROOT__RECURSIVE_ROOT, false)
			.assertReplaceSingleValuedEAttribute(recursiveRoot, IDENTIFIED__ID, null, recursiveRoot.id, false, false)
			.assertInsertEAttribute(recursiveRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 1, 0, false)
			.assertInsertEAttribute(recursiveRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 2, 1, false)
			.assertEmpty;
	}

}
