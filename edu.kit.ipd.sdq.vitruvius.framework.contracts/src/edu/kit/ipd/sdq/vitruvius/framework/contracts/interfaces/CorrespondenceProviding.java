package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface CorrespondenceProviding {
    CorrespondenceModel getCorrespondenceModelOriginal(final VURI mmAVURI, final VURI mmBVURI);

    void saveCorrespondenceModelAndDecorators(final CorrespondenceModel correspondenceModel);

    Set<CorrespondenceModel> getOrCreateAllCorrespondenceModels(final VURI mmVURI);
}
