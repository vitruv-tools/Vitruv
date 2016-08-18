package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util.Java2PcmPackagePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public new() {
		super();
		addPreprocessor(new Java2PcmPackagePreprocessor());
		addPreprocessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor()); 
		addPreprocessor(new Java2PcmTransformationsPreprocessor()); 
	}
}
