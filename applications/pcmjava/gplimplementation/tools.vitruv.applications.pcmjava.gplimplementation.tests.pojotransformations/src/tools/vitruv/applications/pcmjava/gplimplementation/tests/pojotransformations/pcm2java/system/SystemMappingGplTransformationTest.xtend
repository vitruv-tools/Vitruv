package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.system

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system.SystemMappingTransformationTest

class SystemMappingGplTransformationTest extends SystemMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createPcm2JavaGplImplementationChangePropagationSpecification();
	}
}