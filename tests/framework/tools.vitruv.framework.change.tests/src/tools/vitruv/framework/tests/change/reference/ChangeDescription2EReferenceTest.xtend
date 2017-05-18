package tools.vitruv.framework.tests.change.reference

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import allElementTypes.NonRoot
import allElementTypes.AllElementTypesFactory

class ChangeDescription2EReferenceTest extends ChangeDescription2ChangeTransformationTest {

	def protected NonRoot createAndAddNonRootToRootMultiReference(int index) {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		rootElement.multiValuedContainmentEReference.add(index, nonRoot)
		nonRoot
	}

	def protected NonRoot createAndSetNonRootToRootSingleReference() {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		rootElement.singleValuedContainmentEReference = nonRoot
		nonRoot
	}
}
