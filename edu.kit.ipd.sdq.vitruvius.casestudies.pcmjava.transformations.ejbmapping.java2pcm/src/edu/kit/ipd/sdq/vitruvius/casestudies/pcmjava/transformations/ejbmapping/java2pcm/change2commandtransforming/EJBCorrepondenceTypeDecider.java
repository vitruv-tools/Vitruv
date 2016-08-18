package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.change2commandtransforming;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.codeintegration.CorrespondenceTypeDeciding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

/**
 * This class decides whether an integrated correspondence should be created during the integration
 * of EJB projects. The current default is no, which means, that we assume that the project that
 * should be integrated does complain the EJB mapping rules. If this is not the case the mapping
 * probably won't work.
 *
 * @author langhamm
 *
 */
public class EJBCorrepondenceTypeDecider implements CorrespondenceTypeDeciding {

    @Override
    public boolean useIntegratedCorrespondence(final EObject objectA, final EObject objectB,
            final CorrespondenceInstance<?> cInstance, final List<Resource> jaMoppResources) {
        return false;
    }
}
