package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming

import tools.vitruv.applications.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.seff.EJBJava2PCMCode2SEFFFactory
import mir.responses.AbstractChange2CommandTransformingJavaTo5_1
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.TUIDUpdatePreprocessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Change2CommandTransformingEJBJavaToPCM extends AbstractChange2CommandTransformingJavaTo5_1 {
	public override setup() {
		addChangeProcessor(new TUIDUpdatePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));  
		addChangeProcessor(new Java2PcmMethodBodyChangePreprocessor(userInteracting, new EJBJava2PCMCode2SEFFFactory));
		super.setup();
	}
}
