package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm

import mir.responses.AbstractChange2CommandTransformingJavaTo5_1

class Change2CommandTransformingJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public new() {
		super();
		addPreprocessor(new Java2PcmPackagePreprocessor());
		addPreprocessor(new Java2PcmMethodBodyChangePreprocessor());
		addPreprocessor(new Java2PcmTransformationsPreprocessor());
	}
}