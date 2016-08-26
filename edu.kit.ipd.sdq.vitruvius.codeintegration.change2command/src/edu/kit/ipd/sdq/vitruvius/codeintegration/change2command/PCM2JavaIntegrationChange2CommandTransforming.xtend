package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.pcm2java.Change2CommandTransformingPcmToJava

class PCM2JavaIntegrationChange2CommandTransforming extends Change2CommandTransformingPcmToJava {
	
	override protected setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
	
}