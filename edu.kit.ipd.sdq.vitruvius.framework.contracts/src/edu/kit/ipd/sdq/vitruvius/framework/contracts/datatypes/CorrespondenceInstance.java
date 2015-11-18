package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;

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
public interface CorrespondenceInstance {

    public Mapping getMapping();

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
    public Set<Correspondence> getCorrespondences(List<EObject> eObjects);

    /**
     * Returns all correspondences for the object with the specified tuid and an empty set if the
     * object has no correspondences. Should never return {@link null}.
     *
     * @param tuids
     * @return all correspondences for the object with the specified tuid and an empty set if the
     *         object has no correspondences.
     */
    public Set<Correspondence> getCorrespondencesForTUIDs(List<TUID> tuids);

    public Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects);

    public Correspondence claimUniqueCorrespondence(final List<EObject> aEObjects, final List<EObject> bEObjects);

    // renamed from addSameTypeCorrespondence
    public void addCorrespondence(Correspondence correspondence);

    /**
     * Removes all direct correspondences for the given eObject and all correspondences for children
     * of the given eObject and for children of the eObjects corresponding to the given eObject.
     * Does <b>not</b> remove any model elements (only correspondences).
     *
     * @param eObject
     *            for which all correspondences should be removed
     * @return a set containing all removed correspondences
     */
    // renamed from removeDirectAndChildrenCorrespondencesOnBothSides
    public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(EObject eObject);

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
    // renamed from removeDirectAndChildrenCorrespondencesOnBothSides
    public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(TUID tuid);

    /**
     * Removes the given correspondence, all correspondences for the eObjects of the given
     * correspondence, and all correspondences for their children on both sides. Does <b>not</b>
     * remove any model elements (only correspondences).
     *
     * @param correspondence
     *            that should be removed
     * @return a set containing all removed correspondences
     */
    // renamed from removeNeighborAndChildrenCorrespondencesOnBothSides
    public Set<Correspondence> removeCorrespondencesOfEObjectsAndChildrenOnBothSides(Correspondence correspondence);

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

    EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuidString);

    Set<Correspondence> getAllCorrespondencesWithoutDependencies();
}