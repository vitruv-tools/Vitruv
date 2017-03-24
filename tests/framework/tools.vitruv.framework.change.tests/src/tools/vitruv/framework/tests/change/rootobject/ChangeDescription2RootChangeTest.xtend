package tools.vitruv.framework.tests.change.rootobject

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Before

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import org.junit.After
import allElementTypes.Root

class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest{
	var protected Root rootElement2;
	
	@Before
	def override beforeTest(){
		super.beforeTest
		rootElement2 = createRootInResource(2);
	}
	
	@After
	def override afterTest() {
		super.afterTest();
	}
		
	def protected void assertInsertRoot(int index, boolean isCreate, String uri) {
		if (isCreate) {
			changes.claimChange(index).assertCreateAndInsertRootEObject(this.rootElement, uri);
		} else {
			changes.claimChange(index).assertInsertRootEObject(this.rootElement, uri)
		}
	}
	
	def protected void assertInsertRoot2(int index, boolean isCreate, String uri) {
		if (isCreate) {
			changes.claimChange(index).assertCreateAndInsertRootEObject(this.rootElement2, uri);
		} else {
			changes.claimChange(index).assertInsertRootEObject(this.rootElement2, uri)
		}
	}
	
	def protected void assertRemoveRoot(int index, boolean isDelete, String uri) {
		if (isDelete) {
			changes.claimChange(index).assertRemoveAndDeleteRootEObject(this.rootElement, uri)
		} else {
			changes.claimChange(index).assertRemoveRootEObject(this.rootElement, uri)
		}
	}
	
	def protected void assertRemoveRoot2(int index, boolean isDelete, String uri) {
		if (isDelete) {
			changes.claimChange(index).assertRemoveAndDeleteRootEObject(this.rootElement2, uri)
		} else {
			changes.claimChange(index).assertRemoveRootEObject(this.rootElement2, uri)
		}
	}
	
	def protected void insertRootEObjectInResource(Resource resource){
		resource.contents.add(this.rootElement)
	}
	
	def protected void insertRootEObjectInResource2(Resource resource){
		resource.contents.add(this.rootElement2)
	}
}