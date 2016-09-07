package tools.vitruv.applications.pcmjava.linkingintegration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.correspondence.CorrespondenceModel;

public interface CorrespondenceTypeDeciding {

    String ID = "tools.vitruv.applications.pcmjava.linkingintegration.correspondencetypedeciding";

    default boolean useIntegratedCorrespondence(final EObject objectA, final EObject objectB,
            final CorrespondenceModel cInstance, final List<Resource> jaMoppResources) {
        return true;
    }
}
