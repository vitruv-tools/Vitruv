package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.Java2PCMChangeProcessor
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	protected override setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting)); 
		addChangeProcessor(new Java2PCMChangeProcessor(userInteracting));
		super.setup();
	}
}
