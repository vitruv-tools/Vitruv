package tools.vitruv.framework.correspondence;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface CorrespondenceModelView<T extends Correspondence> extends GenericCorrespondenceModel<T> {
	/**
	 * Returns all correspondences for the specified object and an empty set if the
	 * object has no correspondences. Should never return {@link null}.
	 *
	 * @param eObjects - the {@link EObject}s to get the correspondences for
	 * @return all correspondences for the specified object and an empty set if the
	 *         object has no correspondences.
	 */
	public Set<T> getCorrespondences(List<EObject> eObjects);

	/**
	 * Returns all correspondences for the specified object having the given tag,
	 * and an empty set if the object has no correspondences. Should never return
	 * {@link null}.
	 *
	 * @param eObjects - the {@link EObject}s to get the correspondences for
	 * @param tag      - the tag to filter correspondences for
	 * @return all correspondences for the specified object and an empty set if the
	 *         object has no correspondences.
	 */
	public Set<T> getCorrespondences(List<EObject> eObjects, String tag);

	/**
	 * Returns the unique correspondence between the given lists of
	 * {@link EObject}s. If there is no or more than one correspondence, an
	 * {@link IllegalStateException} is thrown.
	 * 
	 * @param aEObjects - the first list of {@link EObjects}
	 * @param bEObjects - the second list of {@link EObjects}
	 * @return the unique correspondence between the given lists of elements.
	 */
	public T claimUniqueCorrespondence(final List<EObject> aEObjects, final List<EObject> bEObjects);

	/**
	 * Removes the correspondences between the given lists of {@link EObjects} with
	 * the given tag.
	 * 
	 * @param aEObjects - the first list of {@link EObjects}
	 * @param bEObjects - the second list of {@link EObjects}
	 * @param tag       - the tag to filter correspondences for or <code>null</code>
	 *                  if all correspondences shall be removed
	 * @return the removed correspondences
	 */
	public Set<Correspondence> removeCorrespondencesBetween(List<EObject> aEObjects, List<EObject> bEObjects, String tag);

	/**
	 * Removes the correspondences in which the given set of {@link EObject}s is
	 * references. It also removes dependent correspondences.
	 * 
	 * @param eObjects - the list of {@link EObject}s whose correspondences shall be
	 *                 removed
	 * @param tag      - the tag to filter removed correspondences for or
	 *                 <code>null</code> if all correspondences shall be removed
	 * @return the removed correspondences
	 */
	public Set<Correspondence> removeCorrespondencesFor(List<EObject> eObjects, String tag);

	/**
	 * Returns all eObjects that have some correspondence and are an instance of the
	 * given class.
	 * 
	 * @param type the class for which instances should be returned
	 * @return a set containing all eObjects of the given type that have a
	 *         correspondence
	 */
	public <E> Set<E> getAllEObjectsOfTypeInCorrespondences(Class<E> type);
}
