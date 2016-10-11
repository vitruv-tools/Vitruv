package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.system

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system.AssemblyContextMappingTransformationTest

class AssemblyContextMappingGplTransformationTest extends AssemblyContextMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createPcm2JavaGplImplementationChangePropagationSpecification();
	}
}