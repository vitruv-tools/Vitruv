package tools.vitruv.change.composite.rootobject

import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Test
import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest
import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.*

class ChangeDescription2RemoveRootEObjectTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def void removeDeleteRootEObjectInResource(){
		// prepare
		val root = uniquePersistedRoot
		val resource = root.eResource

		// test
		val result = resource.record [
			EcoreUtil.delete(root)
		]

		// assert
		val isDelete = true
		result.assertChangeCount(2)
			.assertRemoveRoot(root, isDelete, resource)
	}

}
