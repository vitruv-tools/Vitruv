package tools.vitruv.framework.correspondence;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;

public interface InternalCorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	public boolean changedAfterLastSave();

	public void resetChangedAfterLastSave();

	public void saveModel();

	/**
	 * Returns the elements corresponding to the given one, if the correspondence is
	 * of the given type and contains the given tag.
	 * 
	 * @param correspondenceType - the type of correspondence to filter
	 * @param eObjects           - the objects to get the corresponding ones for
	 * @param tag                - the tag to filter correspondences for. If the tag
	 *                           is <code>null</code>, all correspondences will be
	 *                           returned
	 * @return the elements corresponding to the given ones
	 */
	public Set<List<EObject>> getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType,
			List<EObject> eObjects, String tag);

	public Set<Correspondence> getCorrespondences(List<EObject> eObjects, String tag);

	public <E> Set<E> getAllEObjectsOfTypeInCorrespondences(Class<? extends Correspondence> correspondenceType,
			Class<E> type);

	public <C extends Correspondence> C createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
			Supplier<C> correspondenceCreator);

	/**
	 * Removes the correspondences of the given type and with the given type between
	 * the given sets of {@link EObject}s. It also removes dependent
	 * correspondences.
	 * 
	 * @param correspondenceType - the type correspondence to remove
	 * @param aEObjects          - the first list of corresponding {@link EObject}s
	 * @param bEObjects          - the second list of corresponding {@link EObject}s
	 * @param tag                - the tag to filter removed correspondences for or
	 *                           <code>null</code> if all correspondences shall be
	 *                           removed
	 * @return the removed correspondences
	 */
	public Set<Correspondence> removeCorrespondencesBetween(Class<? extends Correspondence> correspondenceType,
			List<EObject> aEObjects, List<EObject> bEObjects, String tag);

	/**
	 * Removes all correspondences of the given type in which the given set of
	 * {@link EObject}s is references. It also removes dependent correspondences.
	 * 
	 * @param correspondenceType - the type correspondence to remove
	 * @param eObjects           - the list of {@link EObject}s whose
	 *                           correspondences shall be removed
	 * @param tag                - the tag to filter removed correspondences for or
	 *                           <code>null</code> if all correspondences shall be
	 *                           removed
	 * @return the removed correspondences
	 */
	public Set<Correspondence> removeCorrespondencesFor(Class<? extends Correspondence> correspondenceType,
			List<EObject> eObjects, String tag);
}
