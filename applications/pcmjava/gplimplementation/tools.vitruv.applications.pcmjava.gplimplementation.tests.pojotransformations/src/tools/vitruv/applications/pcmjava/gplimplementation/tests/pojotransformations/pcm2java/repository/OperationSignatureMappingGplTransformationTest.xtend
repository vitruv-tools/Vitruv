package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.repository

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository.OperationSignatureMappingTransformationTest

class OperationSignatureMappingGplTransformationTest extends OperationSignatureMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createPcm2JavaGplImplementationChangePropagationSpecification();
	}
}