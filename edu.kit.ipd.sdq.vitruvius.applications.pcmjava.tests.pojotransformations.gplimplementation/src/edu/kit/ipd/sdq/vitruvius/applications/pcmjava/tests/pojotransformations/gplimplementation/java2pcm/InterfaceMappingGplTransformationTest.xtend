package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.java2pcm.InterfaceMappingTransformationTest

class InterfaceMappingGplTransformationTest extends InterfaceMappingTransformationTest {
	override protected createChange2CommandTransformingProviding() {
		Change2CommandTransformingProvidingFactory.createJava2PcmGplImplementationTransformingProviding();
	}
}