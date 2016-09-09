package tools.vitruv.applications.pcmjava.seffstatements.pojotransformations

import tools.vitruv.applications.pcmjava.seffstatements.pojotransformations.Java2PcmPackageMappingMethodBodyChangePreprocessor
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Change2CommandTransformingJavaToPcm

class Change2CommandTransformingJavaToPcmWithSeffstatements extends Change2CommandTransformingJavaToPcm {
	protected override setup() {
		super.setup();
		addChangePreprocessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting)); 
	}
}
