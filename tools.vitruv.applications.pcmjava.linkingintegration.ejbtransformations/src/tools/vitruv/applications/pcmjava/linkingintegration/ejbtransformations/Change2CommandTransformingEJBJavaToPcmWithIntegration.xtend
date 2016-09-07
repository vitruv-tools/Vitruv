package tools.vitruv.applications.pcmjava.linkingintegration.ejbtransformations

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.Change2CommandTransformingEJBJavaToPCM
import tools.vitruv.applications.pcmjava.linkingintegration.change2command.CodeIntegrationChangeProcessor

class Change2CommandTransformingEJBJavaToPcmWithIntegration extends Change2CommandTransformingEJBJavaToPCM {
	override setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
}