package tools.vitruv.applications.pcmjava.linkingintegration.change2command

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Change2CommandTransformingJavaToPcm

class Java2PCMIntegrationChange2CommandTransforming extends Change2CommandTransformingJavaToPcm {
	
	override protected setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
	
}