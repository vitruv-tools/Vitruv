package edu.kit.ipd.sdq.vitruvius.framework.correspondence;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface CorrespondenceProviding {
    CorrespondenceModel getCorrespondenceModel(final VURI mmAVURI, final VURI mmBVURI);

    Set<CorrespondenceModel> getOrCreateAllCorrespondenceModels(final VURI mmVURI);
}
