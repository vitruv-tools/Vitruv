package tools.vitruv.change.composite.rootobject

import org.junit.jupiter.api.Test
import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.*

class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def void insertCreateRootEObjectInResource() {
		// prepare
		val resource = resourceAt("insertRoot")
		
		// test
		val root = aet.Root
		val result = resource.record [
			contents += root
		]
		
		// assert
		val isCreate = true
		result.assertChangeCount(3)
			.assertInsertRoot(root, isCreate, resource)
			.assertReplaceSingleValuedEAttribute(root, IDENTIFIED__ID, null, root.id, false, false)
			.assertEmpty
	}

	@Test
	def void insertCreateTwoRootEObjectsInResource() {
		// prepare
		val resource1 = resourceAt("insertRoot1")
		val resource2 = resourceAt("insertRoot2")
		
		// test
		val root = aet.Root
		val result1 = resource1.record [
			contents += root
		]
				
		// assert
		val isCreate = true
		result1.assertChangeCount(3)
			.assertInsertRoot(root, isCreate, resource1)
			.assertReplaceSingleValuedEAttribute(root, IDENTIFIED__ID, null, root.id, false, false)
			.assertEmpty

		// test
		val root2 = aet.Root
		val result2 = resource2.record [
			contents += root2
		]
				
		// assert
		result2.assertChangeCount(3)
			.assertInsertRoot(root2, isCreate, resource2)
			.assertReplaceSingleValuedEAttribute(root2, IDENTIFIED__ID, null, root2.id, false, false)
			.assertEmpty
	}
}
