package tools.vitruvius.applications.pcmjava.linkingintegration.change2command

import tools.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Change2CommandTransformingPcmToJava

class PCM2JavaIntegrationChange2CommandTransforming extends Change2CommandTransformingPcmToJava {
	
	override protected setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
	
}