package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.PackageMappingTransformationTest

class PackageMappingGplTransformationTest extends PackageMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createJava2PcmGplImplementationChangePropagationSpecification();
	}
}