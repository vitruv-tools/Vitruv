package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.ejbtransformations.integration

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.Change2CommandTransformingEJBJavaToPCM
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.linkingintegration.change2command.CodeIntegrationChangeProcessor

class Change2CommandTransformingEJBJavaToPcmWithIntegration extends Change2CommandTransformingEJBJavaToPCM {
	override setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
}