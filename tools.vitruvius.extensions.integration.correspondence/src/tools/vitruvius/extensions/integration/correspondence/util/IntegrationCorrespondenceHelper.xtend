package tools.vitruvius.extensions.integration.correspondence.util

import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.correspondence.GenericCorrespondenceModel
import tools.vitruvius.extensions.integration.correspondence.integration.IntegrationCorrespondence
import tools.vitruvius.extensions.integration.correspondence.integration.IntegrationFactory

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
