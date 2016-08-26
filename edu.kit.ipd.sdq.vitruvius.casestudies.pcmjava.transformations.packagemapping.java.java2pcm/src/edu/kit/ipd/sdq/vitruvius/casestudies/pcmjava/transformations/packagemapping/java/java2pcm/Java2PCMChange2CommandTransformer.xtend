package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.AbstractChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util.Java2PcmPackagePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor

class Java2PCMChange2CommandTransformer extends AbstractChange2CommandTransforming {
	
	new() {
		super(
			new TransformationMetamodelPair(VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE),
				VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE)))
	}

	override protected setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting));
		addChangeProcessor(new Java2PCMChangeProcessor(userInteracting));
	}
	
}
