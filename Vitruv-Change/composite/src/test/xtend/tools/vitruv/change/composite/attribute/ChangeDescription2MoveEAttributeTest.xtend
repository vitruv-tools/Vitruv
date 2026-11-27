package tools.vitruv.change.composite.attribute

import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest
import org.junit.jupiter.api.Test
import static allElementTypes.AllElementTypesPackage.Literals.*
import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.assertRemoveEAttribute
import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.assertInsertEAttribute
import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.assertEmpty

class ChangeDescription2MoveEAttributeTest extends ChangeDescription2ChangeTransformationTest {
	
	@Test
	def void moveMultiValuedEAttribute() {
		// prepare
		uniquePersistedRoot => [
			multiValuedEAttribute += 42
			multiValuedEAttribute += 55
			multiValuedEAttribute += 66
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedEAttribute.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 0, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedUnsettableEAttribute() {
		// prepare
		uniquePersistedRoot => [
			multiValuedUnsettableEAttribute += 42
			multiValuedUnsettableEAttribute += 55
			multiValuedUnsettableEAttribute += 66
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnsettableEAttribute.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 55, 1)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 55, 0, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedUnorderedEAttribute() {
		// prepare
		uniquePersistedRoot => [
			multiValuedUnorderedEAttribute += 42
			multiValuedUnorderedEAttribute += 55
			multiValuedUnorderedEAttribute += 66
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnorderedEAttribute.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNORDERED_EATTRIBUTE, 55, 1)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNORDERED_EATTRIBUTE, 55, 0, false)
			.assertEmpty
	}
}