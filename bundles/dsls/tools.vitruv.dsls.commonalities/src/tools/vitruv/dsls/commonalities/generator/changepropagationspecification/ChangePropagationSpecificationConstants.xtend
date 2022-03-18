package tools.vitruv.dsls.commonalities.generator.changepropagationspecification

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.domains.VitruvDomain

@Utility
class ChangePropagationSpecificationConstants {
	static val CHANGE_PROPAGATION_PACKAGE_NAME = 'tools.vitruv.commonalities'
	static val CHANGE_PROPAGATION_PROVIDER_NAME = "CommonalitiesChangePropagationSpecificationProvider"

	@Pure
	static def getChangePropagationSpecificationName(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		sourceDomain.name.toFirstUpper + "To" + targetDomain.name.toFirstUpper + "ChangePropagationSpecification"
	}

	@Pure
	static def getChangePropagationSpecificationProviderName() {
		CHANGE_PROPAGATION_PROVIDER_NAME
	}

	@Pure
	static def getChangePropagationSpecificationPackageName() {
		CHANGE_PROPAGATION_PACKAGE_NAME
	}
}
