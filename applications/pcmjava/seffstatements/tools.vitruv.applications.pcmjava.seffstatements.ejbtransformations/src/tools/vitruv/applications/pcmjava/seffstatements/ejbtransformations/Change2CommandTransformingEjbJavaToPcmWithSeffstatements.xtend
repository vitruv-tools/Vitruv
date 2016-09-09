package tools.vitruv.applications.pcmjava.seffstatements.ejbtransformations

import tools.vitruv.applications.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import tools.vitruv.applications.pcmjava.seffstatements.ejbtransformations.java2pcm.EJBJava2PCMCode2SEFFFactory
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.Change2CommandTransformingEjbJavaToPcm

class Change2CommandTransformingEjbJavaToPcmWithSeffstatements extends Change2CommandTransformingEjbJavaToPcm {
	public override setup() {
		// FIXME HK This should be a preprocessor after the inherited ones
		addChangeProcessor(new Java2PcmMethodBodyChangePreprocessor(userInteracting, new EJBJava2PCMCode2SEFFFactory));
		super.setup();
	}
}
