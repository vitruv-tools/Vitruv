package edu.kit.ipd.sdq.vitruvius.codeintegration.util

import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance

class IntegrationCorrespondenceHelper {
	private new() {
	}

	def static final CorrespondenceInstance<IntegrationCorrespondence> getEditableView(
		CorrespondenceInstance<?> ci) {
		return ci.getEditableView(typeof(IntegrationCorrespondence), [createIntegrationCorrespondence])
	}

	def static final IntegrationCorrespondence createIntegrationCorrespondence() {
		val integratedCorrespondence = IntegrationFactory::eINSTANCE.
			createIntegrationCorrespondence()
		integratedCorrespondence.setCreatedByIntegration(true)
		return integratedCorrespondence
	}
}
