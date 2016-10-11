package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.MethodMappingTransformationTest

class MethodMappingGplTransformationTest extends MethodMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createJava2PcmGplImplementationChangePropagationSpecification();
	}
}