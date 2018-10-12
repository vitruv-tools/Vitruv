package tools.vitruv.framework.correspondence;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface CorrespondenceModelView<T extends Correspondence> extends GenericCorrespondenceModel<T> {
	/**
     * Returns all correspondences for the specified object and an empty set if the object has no
     * correspondences. Should never return {@link null}.
     *
     * @param eObjects
     * @return all correspondences for the specified object and an empty set if the object has no
     *         correspondences.
     */

    public Set<T> getCorrespondences(List<EObject> eObjects);
    
    /**
     * Returns all correspondences for the specified object having the given tag, and an empty set 
     * if the object has no correspondences. Should never return {@link null}.
     *
     * @param eObjects
     * @param tag the tag to filter correspondences for
     * @return all correspondences for the specified object and an empty set if the object has no
     *         correspondences.
     */

    public Set<T> getCorrespondences(List<EObject> eObjects, String tag);
    
    public T claimUniqueCorrespondence(final List<EObject> aEObjects, final List<EObject> bEObjects);
}
