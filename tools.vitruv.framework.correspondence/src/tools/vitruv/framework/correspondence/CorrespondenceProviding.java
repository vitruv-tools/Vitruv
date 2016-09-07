package tools.vitruv.framework.correspondence;

import java.util.Set;

import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.util.datatypes.VURI;

public interface CorrespondenceProviding {
    CorrespondenceModel getCorrespondenceModel(final VURI mmAVURI, final VURI mmBVURI);

    Set<CorrespondenceModel> getOrCreateAllCorrespondenceModels(final VURI mmVURI);
}
