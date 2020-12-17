package tools.vitruv.framework.tests.change.rootobject

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.resource.Resource

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import allElementTypes.Root
import tools.vitruv.framework.change.echange.EChange
import org.junit.jupiter.api.BeforeEach
import org.eclipse.xtend.lib.annotations.Accessors

class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest {
	@Accessors(PROTECTED_GETTER)
	var Root rootElement2;

	@BeforeEach
	def void prepare() {
		rootElement2 = createRootInResource(2);
	}

	def protected Iterable<? extends EChange> assertInsertRoot(Iterable<? extends EChange> changes, boolean isCreate,
		Resource resource) {
		if (isCreate) {
			return changes.assertCreateAndInsertRootEObject(this.rootElement, resource.URI.toFileString, resource);
		} else {
			return changes.assertInsertRootEObject(this.rootElement, resource.URI.toFileString, resource)
		}
	}

	def protected Iterable<? extends EChange> assertInsertRoot2(Iterable<? extends EChange> changes, boolean isCreate,
		Resource resource) {
		if (isCreate) {
			changes.assertCreateAndInsertRootEObject(this.rootElement2, resource.URI.toFileString, resource);
		} else {
			changes.assertInsertRootEObject(this.rootElement2, resource.URI.toFileString, resource)
		}
	}

	def protected Iterable<? extends EChange> assertRemoveRoot(Iterable<? extends EChange> changes, boolean isDelete,
		Resource resource) {
		if (isDelete) {
			changes.assertRemoveAndDeleteRootEObject(this.rootElement, resource.URI.toFileString, resource)
		} else {
			changes.assertRemoveRootEObject(this.rootElement, resource.URI.toFileString, resource)
		}
	}

	def protected Iterable<? extends EChange> assertRemoveRoot2(Iterable<? extends EChange> changes, boolean isDelete,
		Resource resource) {
		if (isDelete) {
			changes.assertRemoveAndDeleteRootEObject(this.rootElement2, resource.URI.toFileString, resource)
		} else {
			changes.assertRemoveRootEObject(this.rootElement2, resource.URI.toFileString, resource)
		}
	}

	def protected void insertRootEObjectInResource(Resource resource) {
		resource.contents.add(this.rootElement)
	}

	def protected void insertRootEObjectInResource2(Resource resource) {
		resource.contents.add(this.rootElement2)
	}

}
