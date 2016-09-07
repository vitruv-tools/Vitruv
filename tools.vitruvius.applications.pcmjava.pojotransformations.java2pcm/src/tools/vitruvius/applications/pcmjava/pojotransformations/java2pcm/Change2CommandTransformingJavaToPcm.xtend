package tools.vitruvius.applications.pcmjava.pojotransformations.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import tools.vitruvius.applications.pcmjava.pojotransformations.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.Java2PCMChangeProcessor
import tools.vitruvius.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	protected override setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting)); 
		addChangeProcessor(new Java2PCMChangeProcessor(userInteracting));
		super.setup();
	}
}
