package tools.vitruv.change.composite.rootobject

import org.junit.jupiter.api.Test
import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*
import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest

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
		result.assertChangeCount(2) //
		.assertRemoveRoot(root, isDelete, resource1) //
		.assertInsertRoot(root, isCreate, resource2) //
		.assertEmpty
	}

	@Test
	def void moveResource() {
		val originalResourceName = "resource"
		val changedResourceName = "newLocation"

		val root = aet.Root
		val resource = resourceAt(originalResourceName) => [contents += root]
		val originalUri = resource.URI

		val result = resource.record [
			URI = '''«changedResourceName».xmi'''.uri
		]

		val isDelete = false
		val isCreate = false
		result.assertChangeCount(2) //
		.assertRemoveRoot(root, isDelete, resourceAt(originalResourceName), originalUri) //
		.assertInsertRoot(root, isCreate, resourceAt(changedResourceName)) //
		.assertEmpty
	}
}
