package tools.vitruv.extensions.integration.correspondence.util;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence;
import tools.vitruv.framework.correspondence.CorrespondenceModelView;

public interface IntegrationCorrespondenceModelView extends CorrespondenceModelView<IntegrationCorrespondence> {
	public Set<List<EObject>> getCorrespondingIntegratedEObjects(List<EObject> objects, String tag);
}
