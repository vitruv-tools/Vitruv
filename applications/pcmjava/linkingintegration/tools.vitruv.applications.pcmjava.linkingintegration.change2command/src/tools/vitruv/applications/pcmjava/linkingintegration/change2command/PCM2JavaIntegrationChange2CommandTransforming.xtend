package tools.vitruv.applications.pcmjava.linkingintegration.change2command

import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Change2CommandTransformingPcmToJava

class PCM2JavaIntegrationChange2CommandTransforming extends Change2CommandTransformingPcmToJava {
	
	override protected setup() {
		super.setup();
		addChangePreprocessor(new CodeIntegrationChangeProcessor(userInteracting));
	}
	
}