package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java

import tools.vitruv.domains.java.util.JaMoPPNamespace
import tools.vitruv.domains.pcm.util.PCMNamespace
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransforming
import tools.vitruv.framework.change.processing.ChangeProcessor

class PCM2JavaChange2CommandTransformer extends AbstractChange2CommandTransforming {
	
	new() {
		super(VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE),
				VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE))
	}

	private def ChangeProcessor createTransformationExecutorChangeProcessor() {
		val changeProcessor = new Pcm2JavaChangeProcessor(userInteracting);
        return changeProcessor;
	}
	
	override protected setup() {
		addChangeMainprocessor(createTransformationExecutorChangeProcessor());
	}
	
}
