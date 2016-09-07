package tools.vitruv.applications.pcmjava.tests.pojotransformations.gplimplementation.pcm2java.system

import tools.vitruv.framework.change.processing.Change2CommandTransformingProviding
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import tools.vitruv.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.PCM2JavaChange2CommandTransformer

class Change2CommandTransformingProvidingFactory {
	static def Change2CommandTransformingProviding createPcm2JavaGplImplementationTransformingProviding() {
		return AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
			#[new PCM2JavaChange2CommandTransformer()]
		)
	}
}