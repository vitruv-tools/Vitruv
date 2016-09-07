package tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm

import tools.vitruvius.domains.java.util.JaMoPPNamespace
import tools.vitruvius.domains.pcm.util.PCMNamespace
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming
import tools.vitruvius.applications.pcmjava.pojotransformations.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor
import tools.vitruvius.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Java2PCMChange2CommandTransformer extends AbstractChange2CommandTransforming {
	
	new() {
		super(VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE),
				VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE))
	}

	override protected setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting));
		addChangeProcessor(new Java2PCMChangeProcessor(userInteracting));
	}
	
}
