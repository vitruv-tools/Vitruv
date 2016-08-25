package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.code2seff.POJOJava2PCMCode2SEFFFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

class Java2PcmPackageMappingMethodBodyChangePreprocessor extends Java2PcmMethodBodyChangePreprocessor {
	new(UserInteracting userInteracting) {
		super(userInteracting, new POJOJava2PCMCode2SEFFFactory());
	}
}
