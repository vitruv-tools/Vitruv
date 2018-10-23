package tools.vitruv.framework.correspondence;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.tuid.Tuid;
import tools.vitruv.framework.util.datatypes.URIHaving;

/**
 * Contains and manages correspondences, each consisting of two pairs of
 * elements in different models. A correspondence describes the sematic relation
 * between two sets of elements in different model. This interface serves as a
 * generic interface that is realized in a concrete implementation
 * {@link InternalCorrespondenceModel} and views on that internal representation
 * {@link CorrespondenceModelView}, which are aware of the concrete type of
 * correspondences to be handled.
 * 
 * @author kramerm
 * @author Heiko Klare
 * 
 * @param <T> - the type of correspondence that is handled
 */
public interface GenericCorrespondenceModel<T extends Correspondence> extends URIHaving {
	/**
	 * Creates a {@link ManualCorresponendce} with the given tag between the given
	 * lists of {@link EObject}s.
	 * 
	 * @param eObjects1 - the first list of {@link EObject}s
	 * @param eObjects2 - the second list of {@link EObject}s
	 * @param tag       - the tag to be added to the correspondence or
	 *                  <code>null</code> if none shall be added
	 * @return the created correspondence
	 */
	public Correspondence createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2, String tag);

	/**
	 * Returns whether at least one object corresponds to the given object.
	 *
	 * @param eObject the object for which correspondences should be looked up
	 * @return true if # of corresponding objects > 0
	 */

	public boolean hasCorrespondences(List<EObject> eObject);

	/**
	 * Returns whether at least one object corresponds to another object.
	 *
	 * @return true if # of correspondences > 0
	 */
	public boolean hasCorrespondences();

	Set<T> getAllCorrespondencesWithoutDependencies();

	public List<T> getAllCorrespondences();

	/**
	 * Returns all elements corresponding to the given one.
	 * 
	 * @param eObjects - the objects to get the corresponding ones for
	 * @return the elements corresponding to the given ones
	 */
	public Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects);

	/**
	 * Returns the elements corresponding to the given one, if the correspondence
	 * contains the given tag.
	 * 
	 * @param eObjects - the objects to get the corresponding ones for
	 * @param tag      - the tag to filter correspondences for. If the tag is
	 *                 <code>null</code>, all correspondences will be returned
	 * @return the elements corresponding to the given ones
	 */
	public Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects, String tag);

	public Set<T> getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects);

	/**
	 * Removes the given correspondence, all correspondences for the eObjects of the
	 * given correspondence, and all correspondences for their children on both
	 * sides. Does <b>not</b> remove any model elements (only correspondences).
	 *
	 * @param correspondence that should be removed
	 * @return a set containing all removed correspondences
	 */
	public Set<Correspondence> removeCorrespondencesAndDependendCorrespondences(T correspondence);

	public EObject resolveEObjectFromTuid(final Tuid tuid);

	public Tuid calculateTuidFromEObject(final EObject eObject);

	/**
	 * Is necessary to make the remove operation possible. TODO: check whether we
	 * can remove this from the public API
	 *
	 * @return
	 */
	public Tuid calculateTuidFromEObject(final EObject eObject, EObject virtualRootObject, String prefix);

	/**
	 * Returns a view on the {@link GenericCorrespondenceModel} restricted to the
	 * specified kind of {@link Correspondence}. The functions of the view will only
	 * act on the given implementation of {@link Correspondence}s.
	 *
	 * @param correspondenceType - the type of {@link Correspondence}s to be handled
	 *                           by the view
	 * @return the restricted view on the {@link GenericCorrespondenceModel}
	 * @author Heiko Klare
	 */
	public <U extends Correspondence> CorrespondenceModelView<U> getView(Class<U> correspondenceType);

	/**
	 * Creates a editable view on the {@link CorrespondenceModel} restricted to the
	 * specified kind of {@link Correspondence}. To enable the creation of new
	 * Correspondences a Supplier method has to be provided to the writable view.
	 *
	 * @param correspondenceType    - the type of {@link Correspondence}s to be
	 *                              handled by the view
	 * @param correspondenceCreator - a supplier that creates a new
	 *                              {@link Correspondence} of appropriate type on
	 *                              demand
	 * @return the restricted editable view on the {@link CorrespondenceModel}
	 * @author Heiko Klare
	 */
	public <U extends Correspondence> CorrespondenceModelView<U> getEditableView(Class<U> correspondenceType,
			Supplier<U> correspondenceCreator);

	/**
	 * Creates a generic {@link CorrespondenceModel} view on the
	 * {@link GenericCorrespondenceModel} dealing only with generic instances of
	 * {@link Correspondence}.
	 *
	 * @return the generic {@link CorrespondenceModel} view
	 * @author Heiko Klare
	 */
	public CorrespondenceModel getGenericView();

}