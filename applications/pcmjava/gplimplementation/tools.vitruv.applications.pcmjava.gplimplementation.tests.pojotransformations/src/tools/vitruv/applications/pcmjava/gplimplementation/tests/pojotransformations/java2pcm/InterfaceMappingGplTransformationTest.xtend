package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.InterfaceMappingTransformationTest

class InterfaceMappingGplTransformationTest extends InterfaceMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createJava2PcmGplImplementationChangePropagationSpecification();
	}
}