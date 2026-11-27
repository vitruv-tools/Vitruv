package tools.vitruv.change.composite

import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest
import org.junit.jupiter.api.Test
import static extension tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName

class VitruviusChangeURITest extends ChangeDescription2ChangeTransformationTest {
	@Test
	@DisplayName("create model elements and ensure their change URIs match resource URI")
	def void createModelElements() {
		// prepare
		val resource = resourceAt("test")
		
		// test
		val root = aet.Root
		val result = resource.recordComposite [
			contents += root => [
				id = "Root"
				singleValuedContainmentEReference = aet.NonRoot => [
					id = "NonRoot"
				]
			]
		]
		
		result.changedURIs.forEach[assertEquals(it, resource.URI)]
	}
	
	@Test
	@DisplayName("delete resource and ensure change URIs for proxy elements match resource URI")
	def void deleteModel() {
		// prepare
		val resource = resourceAt("test")
		
		// test
		val root = aet.Root
		resource.recordComposite [
			contents += root => [
				id = "Root"
				singleValuedContainmentEReference = aet.NonRoot => [
					id = "NonRoot"
				]
			]
		]
		
		val result = resource.recordComposite [
			delete(null)
		]
		
		resource.allContents.forEach[assertTrue(it.eIsProxy)]
		result.changedURIs.forEach[assertEquals(it, resource.URI)]
	}
	
}