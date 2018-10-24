package tools.vitruv.extensions.integration.correspondence.util

import tools.vitruv.framework.correspondence.CorrespondenceModel

final class IntegrationCorrespondenceHelper {
	private new() {
	}

	def static final IntegrationCorrespondenceModelView getEditableView(
		CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(IntegrationCorrespondenceModelViewFactory.instance)
	}

}
