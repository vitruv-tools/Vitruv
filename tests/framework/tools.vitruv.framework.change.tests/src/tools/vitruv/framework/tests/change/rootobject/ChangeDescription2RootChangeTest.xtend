package tools.vitruv.framework.tests.change.rootobject

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Before

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import org.junit.After
import allElementTypes.Root

class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest {
	var protected Root rootElement2

	@Before
	def override beforeTest() {
		super.beforeTest
		rootElement2 = createRootInResource(2)
	}

	@After
	def override afterTest() {
		super.afterTest
	}

	def protected void assertInsertRoot(int index, boolean isCreate, Resource resource) {
		if (isCreate) {
			changes.claimChange(index).assertCreateAndInsertRootEObject(rootElement, resource.URI.toFileString,
				resource)
		} else {
			changes.claimChange(index).assertInsertRootEObject(rootElement, resource.URI.toFileString, resource)
		}
	}

	def protected void assertInsertRoot2(int index, boolean isCreate, Resource resource) {
		if (isCreate) {
			changes.claimChange(index).assertCreateAndInsertRootEObject(rootElement2, resource.URI.toFileString,
				resource)
		} else {
			changes.claimChange(index).assertInsertRootEObject(rootElement2, resource.URI.toFileString, resource)
		}
	}

	def protected void assertRemoveRoot(int index, boolean isDelete, Resource resource) {
		if (isDelete) {
			changes.claimChange(index).assertRemoveAndDeleteRootEObject(rootElement, resource.URI.toFileString,
				resource)
		} else {
			changes.claimChange(index).assertRemoveRootEObject(rootElement, resource.URI.toFileString, resource)
		}
	}

	def protected void assertRemoveRoot2(int index, boolean isDelete, Resource resource) {
		if (isDelete) {
			changes.claimChange(index).assertRemoveAndDeleteRootEObject(rootElement2, resource.URI.toFileString,
				resource)
		} else {
			changes.claimChange(index).assertRemoveRootEObject(rootElement2, resource.URI.toFileString, resource)
		}
	}

	def protected void insertRootEObjectInResource(Resource resource) {
		resource.contents.add(rootElement)
	}

	def protected void insertRootEObjectInResource2(Resource resource) {
		resource.contents.add(rootElement2)
	}
}
