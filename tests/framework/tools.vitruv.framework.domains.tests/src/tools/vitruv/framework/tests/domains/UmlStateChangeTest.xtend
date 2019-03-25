package tools.vitruv.framework.tests.domains

import org.junit.Test
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory
import org.junit.Ignore

class UmlStateChangeTest extends StateChangePropagationTest {

	@Test
	def void testRenameTypes() {
		vsum.executeCommand [
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uClass = uPackage.classes.get(0)
			uClass.name = "RenamedClass"
			var uInterface = uPackage.interfaces.get(0)
			uInterface.name = "RenamedInterface"
			return null
		]
		umlModelInstance.compareRecordedChanges
	}

	@Test
	def void testNewAttributes() {
		vsum.executeCommand [
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uClass = uPackage.classes.get(0)
			var uAttribute = Uml_mockupFactory.eINSTANCE.createUAttribute
			uAttribute.attributeName = "NewlyAddedAttribute"
			uClass.attributes.add(uAttribute)
			return null
		]
		umlModelInstance.compareRecordedChanges
	}

	@Test
	def void testNewMethod() {
		vsum.executeCommand [
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uInterface = uPackage.interfaces.get(0)
			var uMethod = Uml_mockupFactory.eINSTANCE.createUMethod
			uMethod.name = "NewlyAddedMethod"
			uInterface.methods.add(uMethod)
			return null
		]
		umlModelInstance.compareRecordedChanges
	}

	@Test
	def void testNewClass() {
		vsum.executeCommand [
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val uClass = Uml_mockupFactory.eINSTANCE.createUClass
			uClass.name = "NewlyAddedClass"
			uPackage.classes.add(uClass)
			return null
		]
		umlModelInstance.compareRecordedChanges
	}

	@Ignore // TS (HIGH) EMFCompare does not register any changes
	@Test
	def void testDeleteClass() {
		vsum.executeCommand [
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			uPackage.classes.remove(0)
			return null
		]
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testNewInterface() {
		vsum.executeCommand [
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val uInterface = Uml_mockupFactory.eINSTANCE.createUInterface
			uInterface.name = "NewlyAddedInterface"
			// uInterface.id = "NewlyAddedInterface"
			uPackage.interfaces.add(uInterface)
			return null
		]
		umlModelInstance.compareRecordedChanges
	}
}
