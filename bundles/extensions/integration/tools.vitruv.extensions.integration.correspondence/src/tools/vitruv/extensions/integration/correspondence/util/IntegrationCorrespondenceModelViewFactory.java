package tools.vitruv.extensions.integration.correspondence.util;

import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory;
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence;
import tools.vitruv.extensions.integration.correspondence.integration.IntegrationFactory;

public class IntegrationCorrespondenceModelViewFactory implements
		CorrespondenceModelViewFactory<IntegrationCorrespondenceModelView> {
	private static IntegrationCorrespondenceModelViewFactory instance;
	
	private IntegrationCorrespondenceModelViewFactory() {}
	
	public static synchronized IntegrationCorrespondenceModelViewFactory getInstance() {
		if (instance == null) {
			instance = new IntegrationCorrespondenceModelViewFactory();
		}
		return instance;
	}
	
	@Override
	public IntegrationCorrespondenceModelView createCorrespondenceModelView(
			InternalCorrespondenceModel correspondenceModel) {
		return new IntegrationCorrespondenceModelViewImpl(correspondenceModel);
	}

	@Override
	public IntegrationCorrespondenceModelView createEditableCorrespondenceModelView(
			InternalCorrespondenceModel correspondenceModel) {
		return new IntegrationCorrespondenceModelViewImpl(correspondenceModel, () -> {
					IntegrationCorrespondence correspondence = IntegrationFactory.eINSTANCE.createIntegrationCorrespondence();
					correspondence.setCreatedByIntegration(true);
					return correspondence;
				});
	}

}
