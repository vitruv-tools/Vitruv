package tools.vitruv.applications.pcmjava.linkingintegration.ejbtransformations

import tools.vitruv.applications.pcmjava.linkingintegration.change2command.CodeIntegrationChangeProcessor
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.Change2CommandTransformingEjbJavaToPcm

class Change2CommandTransformingEJBJavaToPcmWithIntegration extends Change2CommandTransformingEjbJavaToPcm {
	override setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
}