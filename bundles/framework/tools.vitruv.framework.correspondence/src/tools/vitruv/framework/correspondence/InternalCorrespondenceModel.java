package tools.vitruv.framework.correspondence;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface InternalCorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	public boolean changedAfterLastSave();
    public void resetChangedAfterLastSave();
    public void saveModel();
    
    /**
     * Returns the elements corresponding to the given one, if the correspondence is of the given type and contains the given tag.
     * 
     * @param correspondenceType - the type of correspondence to filter
     * @param eObjects - the objects to get the corresponding ones for
     * @param tag - the tag to filter correspondences for. If the tag is <code>null</code>, all correspondences will be returned
     * @return the elements corresponding to the given ones
     */
    public Set<List<EObject>> getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType, List<EObject> eObjects, String tag);

    public Set<Correspondence> getCorrespondences(List<EObject> eObjects, String tag);

    public void removeCorrespondencesBetween(Class<? extends Correspondence> correspondenceType, List<EObject> aEObjects, List<EObject> bEObjects, String tag);
}
