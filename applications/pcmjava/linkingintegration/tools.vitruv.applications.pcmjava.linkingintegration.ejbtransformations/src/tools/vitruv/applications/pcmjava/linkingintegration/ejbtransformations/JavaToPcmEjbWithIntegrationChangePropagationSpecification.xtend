package tools.vitruv.applications.pcmjava.linkingintegration.ejbtransformations

import tools.vitruv.applications.pcmjava.linkingintegration.change2command.CodeIntegrationChangeProcessor
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.JavaToPcmEjbChangePropagationSpecification

class JavaToPcmEjbWithIntegrationChangePropagationSpecification extends JavaToPcmEjbChangePropagationSpecification {
	override setup() {
		super.setup();
		addChangePreprocessor(new CodeIntegrationChangeProcessor(userInteracting));
	}
}