package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change

import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil.*

class ChangeDescription2InsertEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {

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

}
