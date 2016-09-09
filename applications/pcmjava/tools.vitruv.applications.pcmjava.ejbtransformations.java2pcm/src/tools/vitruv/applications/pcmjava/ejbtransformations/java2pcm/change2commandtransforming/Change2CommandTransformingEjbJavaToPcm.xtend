package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.TUIDUpdatePreprocessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingEjbJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public override setup() {
		addChangeProcessor(new TUIDUpdatePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));  
		super.setup();
	}
}
