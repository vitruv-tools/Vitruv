package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.Java2PCMChange2CommandTransformer

package class Change2CommandTransformingProvidingFactory {
	static def Change2CommandTransformingProviding createJava2PcmGplImplementationTransformingProviding() {
		return AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
			#[new Java2PCMChange2CommandTransformer()]
		)
	}
}