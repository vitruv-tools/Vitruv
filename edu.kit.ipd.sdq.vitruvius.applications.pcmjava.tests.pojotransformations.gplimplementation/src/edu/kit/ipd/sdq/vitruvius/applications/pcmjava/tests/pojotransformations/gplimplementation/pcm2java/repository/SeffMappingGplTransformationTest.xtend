package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.repository.SEFFMappingTransformationTest

class SeffMappingGplTransformationTest extends SEFFMappingTransformationTest {
	override protected createChange2CommandTransformingProviding() {
		Change2CommandTransformingProvidingFactory.createPcm2JavaGplImplementationTransformingProviding();
	}
}