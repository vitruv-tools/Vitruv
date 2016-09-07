package tools.vitruvius.framework.correspondence;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;

import tools.vitruvius.framework.correspondence.Correspondence;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.util.datatypes.URIHaving;

/**
 * Contains all correspondences for all model instances that conform to the metamodels of a give
 * mapping. The correspondences do not store any information on metamodels. Only correspondence
 * instances link to the metamodels. Therefore every elementA of a correspondence has to be an
 * instance of a metaclass of the first metamodel of the containing correspondence instance. And
 * every elementB of a correspondence has to be an instance of a metaclass of the second metamodel
 * of the containing correspondence instance.
 *
 * @author kramerm
 *
 */
public interface GenericCorrespondenceModel<T extends Correspondence> extends URIHaving {

    /**
     * Returns whether at least one object corresponds to the given object.
     *
     * @param eObject
     *            the object for which correspondences should be looked up
     * @return true if # of corresponding objects > 0
     */

    public boolean hasCorrespondences(List<EObject> eObject);

    /**
     * Returns whether at least one object corresponds to another object.
     *
     * @return true if # of correspondences > 0
     */
    public boolean hasCorrespondences();

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
     * Returns all correspondences for the object with the specified tuid and an empty set if the
     * object has no correspondences. Should never return {@link null}.
     *
     * @param tuids
     * @return all correspondences for the object with the specified tuid and an empty set if the
     *         object has no correspondences.
     */
    public Set<T> getCorrespondencesForTUIDs(List<TUID> tuids);

    public Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects);

    public Set<List<EObject>> getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType,
            List<EObject> eObjects);

    public T claimUniqueCorrespondence(final List<EObject> aEObjects, final List<EObject> bEObjects);

    public Set<T> getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects);

    public Set<T> getCorrespondencesThatInvolveAtLeastTUIDs(Set<TUID> tuids);

    // renamed from addSameTypeCorrespondence
    public void addCorrespondence(T correspondence);

    /**
     * React to the deletion of an object o by deleting all correspondences that
     *
     * Removes all direct correspondences for the given eObject and all correspondences for children
     * of the given eObject and for children of the eObjects corresponding to the given eObject.
     * Does <b>not</b> remove any model elements (only correspondences).
     *
     * @param eObject
     *            for which all correspondences should be removed
     * @return a set containing all removed correspondences
     */
    public Set<Correspondence> removeCorrespondencesThatInvolveAtLeastAndDependend(Set<EObject> eObjects);

    /**
     * Removes all direct correspondences for the eObject with the given tuid and all
     * correspondences for children of the eObject and for children of the eObjects corresponding to
     * the eObject with the given tuid. Does <b>not</b> remove any model elements (only
     * correspondences).
     *
     * @param tuid
     *            for which all correspondences should be removed
     * @return a set containing all removed correspondences
     */
    public Set<Correspondence> removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(Set<TUID> tuids);

    /**
     * Removes the given correspondence, all correspondences for the eObjects of the given
     * correspondence, and all correspondences for their children on both sides. Does <b>not</b>
     * remove any model elements (only correspondences).
     *
     * @param correspondence
     *            that should be removed
     * @return a set containing all removed correspondences
     */
    public Set<T> removeCorrespondencesAndDependendCorrespondences(T correspondence);

    /**
     * updates the TUID of an EObject
     *
     * @param oldEObject
     *            the old EObject
     * @param newEObject
     *            the new EObject
     */
    // renamed from update
    public void updateTUID(EObject oldEObject, EObject newEObject);

    /**
     * Updates the old TUID with the TUID of the EObject. This method can be used when the TUID of
     * the old Object is known and the old Object can not be passed to the update(EObject, EObject)
     * method. However, this requires that the user a) is able to calculate TUIDs, and b) is aware
     * of TUIDs.
     *
     * Note: The oldTUIDString has to be created with the same TUIDCalculator and resolver that is
     * used for the newEObject
     *
     * @param oldTUID
     *            the old TUID
     * @param newEObject
     *            the new EObject
     */
    // renamed from update
    public void updateTUID(TUID oldTUID, EObject newEObject);

    // renamed from update
    public void updateTUID(TUID oldTUID, TUID newTUID);

    public EObject resolveEObjectFromTUID(final TUID tuid);

    /**
     * syntactic sugar for map[{@link #resolveEObjectFromTUID(TUID)}]
     *
     * @param tuid
     * @return
     */
    public List<EObject> resolveEObjectsFromTUIDs(final List<TUID> tuids);

    public Set<List<EObject>> resolveEObjectsSetsFromTUIDsSets(final Set<List<TUID>> tuidLists);

    public TUID calculateTUIDFromEObject(final EObject eObject);

    /**
     * Is necessary to make the remove operation possible. TODO: check whether we can remove this
     * from the public API
     *
     * @return
     */
    public TUID calculateTUIDFromEObject(final EObject eObject, EObject virtualRootObject, String prefix);

    /**
     * syntactic sugar for map[{@link #calculateTUIDFromEObject(EObject)}]
     *
     * @param eObjects
     * @return
     */
    public List<TUID> calculateTUIDsFromEObjects(final List<EObject> eObjects);

    /**
     * SWAPS eObjects1 and eObjects2 to obtain first as and then bs if necessary!
     */
    public Correspondence createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2);

    public Correspondence createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
            Supplier<Correspondence> correspondenceCreator);

    public Correspondence createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2);

    EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuidString);

    Set<T> getAllCorrespondencesWithoutDependencies();

    public List<T> getAllCorrespondences();

    public Mapping getMapping();
    
    /**
     * Returns the TUIDs for a correspondence that belong to the side that has a metamodel whose
     * namespace URIs include the given <code>metamodelNamespaceUri</code>
     *
     * @param metamodelNamespaceUri
     *            the namespace URI for which the correct side should be returned
     * @return
     * @author Dominik Werle
     */
    public List<TUID> getTUIDsForMetamodel(T correspondence, String metamodelNamespaceUri);

    /**
     * Returns a view on the {@link CorrespondenceModel} restricted to the specified kind of
     * {@link Correspondence}. The functions of the view will only act on the given implementation
     * of {@link Correspondence}s.
     *
     * @param correspondenceType
     *            the type of {@link Correspondence}s to be handled by the view
     * @return the restricted view on the {@link CorrespondenceModel}
     * @author Heiko Klare
     */
    public <U extends Correspondence> GenericCorrespondenceModel<U> getView(Class<U> correspondenceType);

    /**
     * Creates a editable view on the {@link CorrespondenceModel} restricted to the specified
     * kind of {@link Correspondence}. To enable the creation of new Correspondences a Supplier
     * method has to be provided to the writable view.
     *
     * @param correspondenceType
     *            the type of {@link Correspondence}s to be handled by the view
     * @param correspondenceCreator
     * @return the restricted editable view on the {@link CorrespondenceModel}
     */
    public <U extends Correspondence> GenericCorrespondenceModel<U> getEditableView(Class<U> correspondenceType,
            Supplier<U> correspondenceCreator);

}