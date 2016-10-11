package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.system

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system.RequiredDelegationConnectorMappingTransformationTest

class RequiredDelegationConnectorMappingGplTransformationTest extends RequiredDelegationConnectorMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createPcm2JavaGplImplementationChangePropagationSpecification();
	}
}