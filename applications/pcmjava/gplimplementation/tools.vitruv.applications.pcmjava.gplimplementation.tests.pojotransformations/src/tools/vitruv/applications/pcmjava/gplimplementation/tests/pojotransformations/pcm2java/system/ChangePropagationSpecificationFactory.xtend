package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.system

import java.util.List
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.JavaToPcmGplImplementationChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

package class ChangePropagationSpecificationFactory {
	static def List<ChangePropagationSpecification> createPcm2JavaGplImplementationChangePropagationSpecification() {
		return #[new JavaToPcmGplImplementationChangePropagationSpecification()]
	}
}
