package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface CorrespondenceProviding extends CorrespondenceCopyProviding {
    CorrespondenceInstance getCorrespondenceInstanceOriginal(final VURI mmAVURI, final VURI mmBVURI);

    void saveCorrespondenceInstanceAndDecorators(final CorrespondenceInstanceDecorator correspondenceInstance);

    Set<CorrespondenceInstanceDecorator> getOrCreateAllCorrespondenceInstances(final VURI mmVURI);
}
