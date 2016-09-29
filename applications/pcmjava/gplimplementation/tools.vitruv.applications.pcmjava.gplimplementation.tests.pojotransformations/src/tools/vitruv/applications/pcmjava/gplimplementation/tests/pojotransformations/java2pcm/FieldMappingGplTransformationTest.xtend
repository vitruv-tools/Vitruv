package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.FieldMappingTransformationTest

class FieldMappingGplTransformationTest extends FieldMappingTransformationTest{
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createJava2PcmGplImplementationChangePropagationSpecification();
	}
}