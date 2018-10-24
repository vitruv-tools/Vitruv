package tools.vitruv.extensions.integration.correspondence.util;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence;
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruv.framework.correspondence.impl.CorrespondenceModelViewImpl;

class IntegrationCorrespondenceModelViewImpl extends CorrespondenceModelViewImpl<IntegrationCorrespondence> implements IntegrationCorrespondenceModelView {

	public IntegrationCorrespondenceModelViewImpl(InternalCorrespondenceModel correspondenceModel) {
		this(correspondenceModel, null);
	}
	
	public IntegrationCorrespondenceModelViewImpl(InternalCorrespondenceModel correspondenceModel, Supplier<IntegrationCorrespondence> correspondenceCreator) {
		super(IntegrationCorrespondence.class, correspondenceModel, correspondenceCreator);
	}

	@Override
	public Set<List<EObject>> getCorrespondingIntegratedEObjects(List<EObject> objects, String tag) {
		return getCorrespondenceModelDelegate().getCorrespondingEObjects(IntegrationCorrespondence.class,
				correspondence -> correspondence.isCreatedByIntegration(), objects, tag);
	}
	
}
