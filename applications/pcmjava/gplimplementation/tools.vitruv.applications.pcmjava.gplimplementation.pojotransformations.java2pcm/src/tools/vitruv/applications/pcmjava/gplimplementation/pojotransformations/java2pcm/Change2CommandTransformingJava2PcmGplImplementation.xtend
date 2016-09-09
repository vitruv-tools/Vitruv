package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm

import tools.vitruv.domains.java.util.JaMoPPNamespace
import tools.vitruv.domains.pcm.util.PCMNamespace
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransforming
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingJava2PcmGplImplementation extends AbstractChange2CommandTransforming {
	
	new() {
		super(VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE),
				VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE))
	}

	override protected setup() {
		addChangePreprocessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeMainprocessor(new Java2PCMChangeProcessor(userInteracting));
	}
	
}
