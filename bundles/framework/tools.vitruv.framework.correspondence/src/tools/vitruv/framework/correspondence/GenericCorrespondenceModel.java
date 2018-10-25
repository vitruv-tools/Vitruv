package tools.vitruv.framework.correspondence;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
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
 * @param <T>
 *            - the type of correspondence that is handled
 */
public interface GenericCorrespondenceModel<T extends Correspondence> extends URIHaving {
	/**
	 * Creates a {@link ManualCorresponendce} with the given tag between the given
	 * lists of {@link EObject}s.
	 * 
	 * @param eObjects1
	 *            - the first list of {@link EObject}s
	 * @param eObjects2
	 *            - the second list of {@link EObject}s
	 * @param tag
	 *            - the tag to be added to the correspondence or <code>null</code>
	 *            if none shall be added
	 * @return the created correspondence
	 */
	public Correspondence createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
			String tag);

	/**
	 * Returns whether at least one object corresponds to the given object.
	 *
	 * @param eObject
	 *            - the object for which correspondences should be looked up
	 * @return true if number of corresponding objects > 0
	 */

	public boolean hasCorrespondences(List<EObject> eObject);

	/**
	 * Returns whether at least one object corresponds to another object.
	 *
	 * @return true if # of correspondences > 0
	 */
	public boolean hasCorrespondences();
	
	/**
	 * Returns the elements corresponding to the given ones in the given correspondence.
	 * 
	 * @param correspondence
	 *            - the correspondence to investigate
	 * @param eObjects
	 *            - the objects to get the corresponding ones for
	 * @return the elements corresponding to the given ones
	 */
	public List<EObject> getCorrespondingEObjectsInCorrespondence(Correspondence correspondence, 
			List<EObject> eObjects);
	
	/**
	 * Returns all correspondences in this correspondence model.
	 * 
	 * @return all correspondences in this correspondence model
	 */
	public List<Correspondence> getAllCorrespondences();

	/**
	 * Returns a view on the {@link GenericCorrespondenceModel} restricted to the
	 * specified kind of {@link Correspondence}. The functions of the view will only
	 * act on the given implementation of {@link Correspondence}s.
	 *
	 * @param correspondenceModelViewFactory
	 *            - the factory for creating views for the type of
	 *            {@link Correspondence}s of interest
	 * @return the restricted view on the {@link GenericCorrespondenceModel}
	 * @author Heiko Klare
	 */
	public <V extends CorrespondenceModelView<?>> V getView(
			CorrespondenceModelViewFactory<V> correspondenceModelViewFactory);

	/**
	 * Creates a editable view on the {@link CorrespondenceModel} restricted to the
	 * specified kind of {@link Correspondence}. To enable the creation of new
	 * Correspondences a Supplier method has to be provided to the writable view.
	 *
	* @param correspondenceModelViewFactory
	 *            - the factory for creating views for the type of
	 *            {@link Correspondence}s of interest
	 * @param correspondenceCreator
	 *            - a supplier that creates a new {@link Correspondence} of
	 *            appropriate type on demand
	 * @return the restricted editable view on the {@link CorrespondenceModel}
	 * @author Heiko Klare
	 */
	public <V extends CorrespondenceModelView<?>> V getEditableView(
			CorrespondenceModelViewFactory<V> correspondenceModelViewFactory);

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