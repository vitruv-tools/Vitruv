package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.system

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.system.SystemMappingTransformationTest

class SystemMappingGplTransformationTest extends SystemMappingTransformationTest {
	override protected createChange2CommandTransformingProviding() {
		Change2CommandTransformingProvidingFactory.createPcm2JavaGplImplementationTransformingProviding();
	}
}