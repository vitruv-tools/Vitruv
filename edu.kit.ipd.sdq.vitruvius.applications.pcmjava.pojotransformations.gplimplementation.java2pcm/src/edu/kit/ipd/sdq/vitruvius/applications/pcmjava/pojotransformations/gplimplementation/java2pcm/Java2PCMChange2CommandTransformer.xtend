package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm

import edu.kit.ipd.sdq.vitruvius.domains.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

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
