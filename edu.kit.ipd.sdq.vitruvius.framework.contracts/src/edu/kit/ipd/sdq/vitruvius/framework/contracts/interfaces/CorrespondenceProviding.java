package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface CorrespondenceProviding extends CorrespondenceCopyProviding {
    CorrespondenceModel getCorrespondenceInstanceOriginal(final VURI mmAVURI, final VURI mmBVURI);

    void saveCorrespondenceInstanceAndDecorators(final CorrespondenceModel correspondenceInstance);

    Set<CorrespondenceModel> getOrCreateAllCorrespondenceInstances(final VURI mmVURI);
}
