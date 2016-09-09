package tools.vitruv.applications.pcmjava.seffstatements.pojotransformations

import tools.vitruv.applications.pcmjava.seffstatements.pojotransformations.Java2PcmPackageMappingMethodBodyChangePreprocessor
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Change2CommandTransformingJavaToPcm

class Change2CommandTransformingJavaToPcmWithSeffstatements extends Change2CommandTransformingJavaToPcm {
	protected override setup() {
		// FIXME HK This should be added after the other preprocessors
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting)); 
		super.setup();
	}
}
