package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.preprocessors.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.preprocessors.java2pcm.code2seff.POJOJava2PCMCode2SEFFFactory

class POJOJava2PcmMethodBodyChangePreprocessor extends Java2PcmMethodBodyChangePreprocessor {
	new() {
		super(new POJOJava2PCMCode2SEFFFactory());
	}
}
