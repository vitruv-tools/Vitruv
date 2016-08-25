package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util.Java2PcmPackagePreprocessor

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public override setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting)); 
		addChangeProcessor(new Java2PcmTransformationsPreprocessor(userInteracting));
		super.setup();
	}
}
