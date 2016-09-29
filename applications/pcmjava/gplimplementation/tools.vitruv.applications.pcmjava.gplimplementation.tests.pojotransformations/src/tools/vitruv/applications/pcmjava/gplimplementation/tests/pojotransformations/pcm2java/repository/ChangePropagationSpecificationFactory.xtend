package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.repository

import tools.vitruv.framework.change.processing.ChangeProcessor
import java.util.List
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.JavaToPcmGplImplementationChangePropagationSpecification

package class ChangePropagationSpecificationFactory {
	static def List<ChangeProcessor> createPcm2JavaGplImplementationChangePropagationSpecification() {
		return #[new JavaToPcmGplImplementationChangePropagationSpecification()]
	}
}
