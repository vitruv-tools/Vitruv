package tools.vitruv.framework.tests.change.rootobject

import org.junit.jupiter.api.Test
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

class ChangeDescription2MoveRootTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def void moveRootEObjectBetweenResources() {
		// prepare
		val root = aet.Root
		val resource1 = resourceAt("resource1")
		val resource2 = resourceAt("resource2")
		resource1.contents += root

		// test
		val result = resource1.resourceSet.record [
			resource2 => [
				contents += root
			]
		]

		// assert
		val isDelete = false
		val isCreate = false
		result.assertChangeCount(2).assertRemoveRoot(root, isDelete, resource1).assertInsertRoot(root, isCreate,
			resource2).assertEmpty
	}

	@Test
	def void moveResource() {
		val root = aet.Root
		val resource = resourceAt("resource") => [contents += root]

		val result = resource.record [
			URI = "newLocation".uri
		]

		val isDelete = false
		val isCreate = false
		result.assertChangeCount(2) //
		.assertRemoveRoot(root, isDelete, resource) //
		.assertInsertRoot(root, isCreate, resource) //
		.assertEmpty
	}
}
