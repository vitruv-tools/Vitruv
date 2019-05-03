package tools.vitruv.framework.tests.domains

import org.junit.Test
import uml_mockup.Uml_mockupFactory

class UmlStateChangeTest extends StateChangePropagationTest {

	@Test
	def void testRenameTypes() {
		var uClass = umlRoot.classes.get(0)
		uClass.name = "RenamedClass"
		var uInterface = umlRoot.interfaces.get(0)
		uInterface.name = "RenamedInterface"
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewAttributes() {
		var uClass = umlRoot.classes.get(0)
		var uAttribute = Uml_mockupFactory.eINSTANCE.createUAttribute
		uAttribute.attributeName = "NewlyAddedAttribute"
		uClass.attributes.add(uAttribute)
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewMethod() {
		var uInterface = umlRoot.interfaces.get(0)
		var uMethod = Uml_mockupFactory.eINSTANCE.createUMethod
		uMethod.name = "NewlyAddedMethod"
		uInterface.methods.add(uMethod)
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewClass() {
		val uClass = Uml_mockupFactory.eINSTANCE.createUClass
		uClass.name = "NewlyAddedClass"
		umlRoot.classes.add(uClass)
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testReplaceClass() {
		umlRoot.classes.remove(0)
		val uClass = Uml_mockupFactory.eINSTANCE.createUClass
		uClass.name = "NewlyAddedClass"
		umlRoot.classes.add(uClass)
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testDeleteClass() {
		umlRoot.classes.remove(0)
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewInterface() {
		val uInterface = Uml_mockupFactory.eINSTANCE.createUInterface
		uInterface.name = "NewlyAddedInterface"
		umlRoot.interfaces.add(uInterface)
		compareChanges(umlModel, umlCheckpoint)
	}
}
