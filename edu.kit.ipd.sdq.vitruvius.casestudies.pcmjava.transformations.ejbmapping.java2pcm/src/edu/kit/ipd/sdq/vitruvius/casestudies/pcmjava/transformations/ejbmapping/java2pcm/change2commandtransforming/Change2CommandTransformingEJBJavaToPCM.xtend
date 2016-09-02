package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.change2commandtransforming

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff.EJBJava2PCMCode2SEFFFactory
import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.TUIDUpdatePreprocessor
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingEJBJavaToPCM extends AbstractChange2CommandTransformingJavaTo5_1 {
	public override setup() {
		addChangeProcessor(new TUIDUpdatePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));  
		addChangeProcessor(new Java2PcmMethodBodyChangePreprocessor(userInteracting, new EJBJava2PCMCode2SEFFFactory));
		super.setup();
		// TODO HK Replace this preprocessor with a generic mechanism in the change synchronizer
	}
}
