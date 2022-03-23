package tools.vitruv.dsls.commonalities.generator.changepropagationspecification

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EPackage

@Utility
class ChangePropagationSpecificationConstants {
	static val CHANGE_PROPAGATION_PACKAGE_NAME = 'tools.vitruv.commonalities'
	static val CHANGE_PROPAGATION_PROVIDER_NAME = "CommonalitiesChangePropagationSpecificationProvider"

	@Pure
	static def getChangePropagationSpecificationName(EPackage sourceMetamodel, EPackage targetMetamodel) {
		sourceMetamodel.name.toFirstUpper + "To" + targetMetamodel.name.toFirstUpper + "ChangePropagationSpecification"
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
