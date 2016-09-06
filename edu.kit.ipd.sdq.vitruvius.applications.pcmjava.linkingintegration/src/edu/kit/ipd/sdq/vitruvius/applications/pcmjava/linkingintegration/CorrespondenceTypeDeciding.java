package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.linkingintegration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;

public interface CorrespondenceTypeDeciding {

    String ID = "edu.kit.ipd.sdq.vitruvius.applications.pcmjava.linkingintegration.correspondencetypedeciding";

    default boolean useIntegratedCorrespondence(final EObject objectA, final EObject objectB,
            final CorrespondenceModel cInstance, final List<Resource> jaMoppResources) {
        return true;
    }
}
