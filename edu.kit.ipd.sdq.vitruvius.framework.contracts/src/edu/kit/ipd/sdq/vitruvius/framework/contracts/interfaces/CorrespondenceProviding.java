package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface CorrespondenceProviding extends CorrespondenceCopyProviding {
    CorrespondenceModel getCorrespondenceModelOriginal(final VURI mmAVURI, final VURI mmBVURI);

    void saveCorrespondenceModelAndDecorators(final CorrespondenceModel correspondenceModel);

    Set<CorrespondenceModel> getOrCreateAllCorrespondenceModels(final VURI mmVURI);
}
