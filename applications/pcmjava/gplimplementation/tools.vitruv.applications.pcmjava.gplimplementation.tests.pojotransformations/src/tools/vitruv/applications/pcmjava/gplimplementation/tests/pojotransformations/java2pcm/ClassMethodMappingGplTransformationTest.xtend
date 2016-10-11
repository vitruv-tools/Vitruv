package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.ClassMethodMappingTransformationTest

class ClassMethodMappingGplTransformationTest extends ClassMethodMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createJava2PcmGplImplementationChangePropagationSpecification();
	}
}