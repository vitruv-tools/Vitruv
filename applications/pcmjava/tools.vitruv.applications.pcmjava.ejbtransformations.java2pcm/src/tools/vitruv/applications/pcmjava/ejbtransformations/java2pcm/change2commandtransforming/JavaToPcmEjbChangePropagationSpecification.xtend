package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.TUIDUpdatePreprocessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor
import mir.reactions.AbstractChangePropagationSpecificationJavaTo5_1

class JavaToPcmEjbChangePropagationSpecification extends AbstractChangePropagationSpecificationJavaTo5_1 {
	public override setup() {
		addChangePreprocessor(new TUIDUpdatePreprocessor(userInteracting));
		addChangePreprocessor(new Java2PcmPackagePreprocessor(userInteracting));  
		super.setup();
	}
}
