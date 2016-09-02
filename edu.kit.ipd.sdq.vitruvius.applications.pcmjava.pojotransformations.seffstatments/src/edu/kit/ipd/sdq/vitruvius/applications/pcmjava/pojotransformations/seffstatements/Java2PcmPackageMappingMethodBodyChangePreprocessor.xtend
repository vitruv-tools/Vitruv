package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.seffstatements

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.seffstatements.code2seff.POJOJava2PCMCode2SEFFFactory
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting

class Java2PcmPackageMappingMethodBodyChangePreprocessor extends Java2PcmMethodBodyChangePreprocessor {
	new(UserInteracting userInteracting) {
		super(userInteracting, new POJOJava2PCMCode2SEFFFactory());
	}
}
