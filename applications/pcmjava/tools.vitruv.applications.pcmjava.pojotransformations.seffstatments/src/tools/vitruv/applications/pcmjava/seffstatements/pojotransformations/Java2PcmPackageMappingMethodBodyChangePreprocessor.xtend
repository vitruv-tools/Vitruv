package tools.vitruv.applications.pcmjava.seffstatements.pojotransformations

import tools.vitruv.applications.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor
import tools.vitruv.applications.pcmjava.seffstatements.pojotransformations.code2seff.POJOJava2PCMCode2SEFFFactory
import tools.vitruv.framework.userinteraction.UserInteracting

class Java2PcmPackageMappingMethodBodyChangePreprocessor extends Java2PcmMethodBodyChangePreprocessor {
	new(UserInteracting userInteracting) {
		super(userInteracting, new POJOJava2PCMCode2SEFFFactory());
	}
}
