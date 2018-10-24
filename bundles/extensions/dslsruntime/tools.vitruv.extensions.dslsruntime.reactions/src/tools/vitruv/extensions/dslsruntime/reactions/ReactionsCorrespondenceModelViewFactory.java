package tools.vitruv.extensions.dslsruntime.reactions;

import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence;
import tools.vitruv.framework.correspondence.CorrespondenceModelView;
import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory;
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruv.framework.correspondence.impl.CorrespondenceModelViewImpl;
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsFactory;

public class ReactionsCorrespondenceModelViewFactory implements
		CorrespondenceModelViewFactory<CorrespondenceModelView<ReactionsCorrespondence>> {
	private static ReactionsCorrespondenceModelViewFactory instance;
	
	private ReactionsCorrespondenceModelViewFactory() {}
	
	public static synchronized ReactionsCorrespondenceModelViewFactory getInstance() {
		if (instance == null) {
			instance = new ReactionsCorrespondenceModelViewFactory();
		}
		return instance;
	}
	
	@Override
	public CorrespondenceModelView<ReactionsCorrespondence> createCorrespondenceModelView(
			InternalCorrespondenceModel correspondenceModel) {
		return new CorrespondenceModelViewImpl<ReactionsCorrespondence>(ReactionsCorrespondence.class,
				correspondenceModel, null);
	}

	@Override
	public CorrespondenceModelView<ReactionsCorrespondence> createEditableCorrespondenceModelView(
			InternalCorrespondenceModel correspondenceModel) {
		return new CorrespondenceModelViewImpl<ReactionsCorrespondence>(ReactionsCorrespondence.class,
				correspondenceModel, () -> ReactionsFactory.eINSTANCE.createReactionsCorrespondence());
	}

}
