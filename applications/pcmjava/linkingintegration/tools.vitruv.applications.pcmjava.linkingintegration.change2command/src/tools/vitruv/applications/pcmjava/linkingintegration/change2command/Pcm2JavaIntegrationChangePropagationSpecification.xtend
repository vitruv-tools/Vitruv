package tools.vitruv.applications.pcmjava.linkingintegration.change2command

import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.PcmToJavaChangePropagationSpecification

class Pcm2JavaIntegrationChangePropagationSpecification extends PcmToJavaChangePropagationSpecification {
	
	override protected setup() {
		super.setup();
		addChangePreprocessor(new CodeIntegrationChangeProcessor(userInteracting));
	}
	
}