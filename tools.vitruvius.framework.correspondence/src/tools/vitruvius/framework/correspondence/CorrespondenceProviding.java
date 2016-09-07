package tools.vitruvius.framework.correspondence;

import java.util.Set;

import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.util.datatypes.VURI;

public interface CorrespondenceProviding {
    CorrespondenceModel getCorrespondenceModel(final VURI mmAVURI, final VURI mmBVURI);

    Set<CorrespondenceModel> getOrCreateAllCorrespondenceModels(final VURI mmVURI);
}
