package tools.vitruvius.applications.pcmjava.linkingintegration.ejbtransformations

import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.Change2CommandTransformingEJBJavaToPCM
import tools.vitruvius.applications.pcmjava.linkingintegration.change2command.CodeIntegrationChangeProcessor

class Change2CommandTransformingEJBJavaToPcmWithIntegration extends Change2CommandTransformingEJBJavaToPCM {
	override setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
}