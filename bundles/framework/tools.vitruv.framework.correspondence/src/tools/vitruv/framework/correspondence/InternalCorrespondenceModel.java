package tools.vitruv.framework.correspondence;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;

/**
 * An internal representation of the {@link GenericCorrespondenceModel},
 * providing correspondence handling logic independent from the actual type of
 * correspondence.
 * 
 * @author Heiko Klare
 */
public interface InternalCorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	/**
	 * Saves this correspondence model instance.
	 */
	public void save();

	/**
	 * Creates a correspondence of given type <C> with the given tag between the
	 * given lists of {@link EObject}s.
	 * 
	 * @param eObjects1
	 *            - the first list of {@link EObject}s
	 * @param eObjects2
	 *            - the second list of {@link EObject}s
	 * @param tag
	 *            - the tag to be added to the correspondence or <code>null</code>
	 *            if none shall be added
	 * @param correspondenceCreate
	 *            - a supplier for creating correspondences of the appropriate type
	 * @return the created correspondence
	 */
	public <C extends Correspondence> C createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
			String tag, Supplier<C> correspondenceCreator);

	/**
	 * Returns all correspondences of the given type for the given {@link EObject}s
	 * with the given tag.
	 * 
	 * @param correspondenceType
	 *            - the type of correspondence to filter for
	 * @param correspondencesFilter
	 *            - the filter for getting the correspondences of interest
	 * @param eObjects
	 *            - the {@link EObject}s to find the correspondence for
	 * @param tag
	 *            - the tag to filter the correspondences for
	 * @return all correspondences for the given objects with the given tag
	 */
	public <C extends Correspondence> Set<C> getCorrespondences(Class<C> correspondenceType,
			Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag);

	/**
	 * Returns the elements corresponding to the given ones, if the correspondence is
	 * of the given type and contains the given tag.
	 * 
	 * @param correspondenceType
	 *            - the type of correspondence to filter for
	 * @param correspondencesFilter
	 *            - the filter for getting the correspondences of interest
	 * @param eObjects
	 *            - the objects to get the corresponding ones for
	 * @param tag
	 *            - the tag to filter correspondences for. If the tag is
	 *            <code>null</code>, all correspondences will be returned
	 * @return the elements corresponding to the given ones
	 */
	public <C extends Correspondence> Set<List<EObject>> getCorrespondingEObjects(Class<C> correspondenceType,
			Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag);

	/**
	 * Returns all elements of a given type that are present in any of the stored
	 * correspondences.
	 * 
	 * @param correspondenceType
	 *            - the type of correspondence to filter for
	 * @param correspondencesFilter
	 *            - the filter for getting the correspondences of interest
	 * @param type
	 *            - the object type to search for in the correspondences
	 * @return the elements in any of the correspondences having the specified type
	 */
	public <E, C extends Correspondence> Set<E> getAllEObjectsOfTypeInCorrespondences(Class<C> correspondenceType,
			Predicate<C> correspondencesFilter, Class<E> type);

	/**
	 * Removes the correspondences of the given type and with the given type between
	 * the given sets of {@link EObject}s. It also removes dependent
	 * correspondences.
	 * 
	 * @param correspondenceType
	 *            - the type of correspondence to filter for
	 * @param correspondencesFilter
	 *            - the filter for getting the correspondences of interest
	 * @param aEObjects
	 *            - the first list of corresponding {@link EObject}s
	 * @param bEObjects
	 *            - the second list of corresponding {@link EObject}s
	 * @param tag
	 *            - the tag to filter removed correspondences for or
	 *            <code>null</code> if all correspondences shall be removed
	 * @return the removed correspondences
	 */
	public <C extends Correspondence> Set<Correspondence> removeCorrespondencesBetween(Class<C> correspondenceType,
			Predicate<C> correspondencesFilter, List<EObject> aEObjects, List<EObject> bEObjects, String tag);

	/**
	 * Removes all correspondences of the given type in which the given set of
	 * {@link EObject}s is references. It also removes dependent correspondences.
	 * 
	 * @param correspondenceType
	 *            - the type of correspondence to filter for
	 * @param correspondencesFilter
	 *            - the filter for getting the correspondences of interest
	 * @param eObjects
	 *            - the list of {@link EObject}s whose correspondences shall be
	 *            removed
	 * @param tag
	 *            - the tag to filter removed correspondences for or
	 *            <code>null</code> if all correspondences shall be removed
	 * @return the removed correspondences
	 */
	public <C extends Correspondence> Set<Correspondence> removeCorrespondencesFor(Class<C> correspondenceType,
			Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag);

}
