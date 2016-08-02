package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm.seff.EJBJava2PCMCode2SEFFFactory
import mir.responses.AbstractChange2CommandTransformingJavaTo5_1

class Change2CommandTransformingEJBJavaToPcm extends AbstractChange2CommandTransformingJavaTo5_1 {
	public new() {
		super();
		addPreprocessor(new Java2PcmMethodBodyChangePreprocessor(new EJBJava2PCMCode2SEFFFactory));
	}
}
