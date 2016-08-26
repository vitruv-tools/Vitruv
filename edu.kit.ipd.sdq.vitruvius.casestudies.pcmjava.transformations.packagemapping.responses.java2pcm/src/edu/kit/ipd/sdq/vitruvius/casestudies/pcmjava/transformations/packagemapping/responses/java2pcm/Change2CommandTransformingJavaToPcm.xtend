package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util.Java2PcmPackagePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.Java2PCMChangeProcessor

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	protected override setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting)); 
		addChangeProcessor(new Java2PCMChangeProcessor(userInteracting));
		super.setup();
	}
}
