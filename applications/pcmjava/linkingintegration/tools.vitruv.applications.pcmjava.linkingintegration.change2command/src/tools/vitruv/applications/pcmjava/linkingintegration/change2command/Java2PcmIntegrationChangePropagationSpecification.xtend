package tools.vitruv.applications.pcmjava.linkingintegration.change2command

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.JavaToPcmChangePropagationSpecification

class Java2PcmIntegrationChangePropagationSpecification extends JavaToPcmChangePropagationSpecification {
	
	override protected setup() {
		super.setup();
		addChangePreprocessor(new CodeIntegrationChangeProcessor(userInteracting));
	}
	
}