package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm.seff.EJBJava2PCMCode2SEFFFactory
import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.preprocessors.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingEJBJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public new() {
		super();
		addPreprocessor(new TUIDUpdatePreprocessor());
		addPreprocessor(new Java2PcmPackagePreprocessor());  
		addPreprocessor(new Java2PcmMethodBodyChangePreprocessor(new EJBJava2PCMCode2SEFFFactory));
		// TODO HK Replace this preprocessor with a generic mechanism in the change synchronizer
	}
}
