package tools.vitruv.framework.tests.domains

import org.junit.Test
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

class UmlStateChangeTest extends StateChangePropagationTest {

	@Test
	def void testRenameClass() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uClass = uPackage.classes.get(0)
			uClass.name = "RenamedClass"
			return null
		])
		compareWithRecordedChanges(umlModelInstance)
	}

	@Test
	def void testNewMethod() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uClass = uPackage.classes.get(0)
			var uAttribute = Uml_mockupFactory.eINSTANCE.createUAttribute
			uAttribute.attributeName = "NewlyAddedAttribute"
			uClass.attributes.add(uAttribute)
			return null
		])
		compareWithRecordedChanges(umlModelInstance)
	}

	@Test
	def void testNewClass() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val uClass = Uml_mockupFactory.eINSTANCE.createUClass
			uClass.name = "NewlyAddedInterface"
			uClass.id = "NewlyAddedInterface"
			uPackage.classes.add(uClass)
			return null
		])
		compareWithRecordedChanges(umlModelInstance)
	}

	@Test
	def void testNewInterface() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val uInterface = Uml_mockupFactory.eINSTANCE.createUInterface
			uInterface.name = "NewlyAddedInterface"
			uInterface.id = "NewlyAddedInterface"
			uPackage.interfaces.add(uInterface)
			return null
		])
		compareWithRecordedChanges(umlModelInstance)
	}
}
