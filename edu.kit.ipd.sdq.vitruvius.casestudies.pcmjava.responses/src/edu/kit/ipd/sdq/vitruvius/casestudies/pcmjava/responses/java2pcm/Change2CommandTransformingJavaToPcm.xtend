package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavapojo.transformations.POJOJava2PCMCode2SEFFFactory

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public new() {
		super();
		addPreprocessor(new Java2PcmPackagePreprocessor());
		addPreprocessor(new Java2PcmMethodBodyChangePreprocessor(new POJOJava2PCMCode2SEFFFactory)); 
		addPreprocessor(new Java2PcmTransformationsPreprocessor()); 
	}
}