package tools.vitruv.extensions.integration.correspondence.util

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.GenericCorrespondenceModel
import tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence
import tools.vitruv.extensions.integration.correspondence.integration.IntegrationFactory

final class IntegrationCorrespondenceHelper {
	private new() {
	}

	def static final GenericCorrespondenceModel<IntegrationCorrespondence> getEditableView(
		CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(typeof(IntegrationCorrespondence), [createIntegrationCorrespondence])
	}

	def static final IntegrationCorrespondence createIntegrationCorrespondence() {
		val integratedCorrespondence = IntegrationFactory::eINSTANCE.
			createIntegrationCorrespondence()
		integratedCorrespondence.setCreatedByIntegration(true)
		return integratedCorrespondence
	}
}
