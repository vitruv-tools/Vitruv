package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.preprocessors.java2pcm.POJOJava2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.preprocessors.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public new() {
		super();
		addPreprocessor(new Java2PcmPackagePreprocessor());
		addPreprocessor(new POJOJava2PcmMethodBodyChangePreprocessor()); 
		addPreprocessor(new Java2PcmTransformationsPreprocessor()); 
	}
}
