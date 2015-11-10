package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.HalfEObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

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
    // FIXME MK will be replaced by next method (use extension method toList)
    @Deprecated
    public boolean hasCorrespondences(EObject eObject);

    public boolean hasCorrespondences(List<EObject> eObject);

    /**
     * Returns whether at least one object corresponds to another object.
     *
     * @return true if # of correspondences > 0
     */
    public boolean hasCorrespondences();

    /**
     * Returns the correspondences for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     *
     * @param eObject
     *            the object for which correspondences are to be returned
     * @return the correspondences for the specified object
     */
    // FIXME MK will no longer be provided (use extension method claimNotEmpty)
    @Deprecated
    public Set<Correspondence> claimCorrespondences(EObject eObject);

    // FIXME MK will no longer be provided (use extension method claimNotEmpty)
    @Deprecated
    public Set<Correspondence> claimCorrespondences(List<EObject> eObjects);

    /**
     * Returns all correspondences for the specified object and an empty set if the object has no
     * correspondences. Should never return {@link null}.
     *
     * @param eObject
     * @return all correspondences for the specified object and an empty set if the object has no
     *         correspondences.
     */
    // FIXME MK will be replaced by next method (use extension method toList)
    @Deprecated
    public Set<Correspondence> getAllCorrespondences(EObject eObject);

    public Set<Correspondence> getCorrespondences(List<EObject> eObjects);

    /**
     * Returns all correspondences for the object with the specified tuid and an empty set if the
     * object has no correspondences. Should never return {@link null}.
     *
     * @param tuid
     * @return all correspondences for the object with the specified tuid and an empty set if the
     *         object has no correspondences.
     */
    // FIXME MK will be replaced by next method (use extension method toList)
    @Deprecated
    public Set<Correspondence> getAllCorrespondences(TUID involvedTUID);

    public Set<Correspondence> getCorrespondencesForTUIDs(List<TUID> involvedTUIDs);

    /**
     * Returns the corresponding objects for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return the corresponding objects for the specified object
     */
    // FIXME MK will no longer be provided (use extension method claimNotEmpty)
    @Deprecated
    public Set<EObject> claimCorrespondingEObjects(EObject eObject);

    // FIXME MK will no longer be provided (use extension method claimNotEmpty)
    @Deprecated
    public Set<HalfEObjectCorrespondence> claimCorrespondingEObjects(List<EObject> eObject);

    /**
     * Returns all corresponding objects for the specified object and an empty set if the object has
     * no correspondences. Should never return {@link null}.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return all corresponding objects for the specified object and an empty set if the object has
     *         no correspondences.
     */
    // FIXME MK will be replaced by next method (use extension method toList)
    @Deprecated
    public Set<EObject> getAllCorrespondingEObjects(EObject eObject);

    public Set<HalfEObjectCorrespondence> getCorrespondingEObjects(List<EObject> eObjects);

    /**
     * Returns the corresponding object for the specified object if there is exactly one
     * corresponding object and throws a {@link java.lang.RuntimeException} otherwise.
     *
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @return the corresponding object for the specified object if there is exactly one
     *         corresponding object
     */
    // FIXME MK will no longer be provided (use extension method claimOne)
    @Deprecated
    public EObject claimUniqueCorrespondingEObject(EObject eObject);

    /**
     * Returns the corresponding objects of the specified type for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondences of this type exist.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @param type
     *            the class of which instances are to be returned
     * @return the corresponding objects of the specified type for the specified object
     */
    // FIXME MK will no longer be provided (use filter(typeOf(T)) and extension method
    // claimNotEmpty)
    @Deprecated
    public <T> Set<T> claimCorrespondingEObjectsByType(EObject eObject, Class<T> type);

    /**
     * Returns the corresponding object of the specified type for the specified object if there is
     * exactly one corresponding object of this type and throws a {@link java.lang.RuntimeException}
     * otherwise.
     *
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @param type
     *            the class of which an instance is to be returned
     * @return the corresponding object of the specified type for the specified object if there is
     *         exactly one corresponding object of this type
     */
    // FIXME MK will no longer be provided (use filter(typeOf(T)) and extension method claimOne)
    @Deprecated
    public <T> T claimUniqueCorrespondingEObjectByType(EObject eObject, Class<T> type);

    /**
     * Returns the correspondence for the given eObject if it is unique, or null if no
     * correspondence exists and throws a {@link RuntimeException} if more than one correspondence
     * exists.
     *
     * @param eObject
     *            the object for which the correspondence is to be returned
     * @return the correspondence for the given eObject if it is unique, or null if no
     *         correspondence exists
     */
    // FIXME MK will no longer be provided (use extension method claimNotMany)
    @Deprecated
    public Correspondence claimUniqueOrNullCorrespondenceForEObject(EObject eObject);

    // FIXME MK will no longer be provided (use extension method claimNotMany)
    @Deprecated
    public Correspondence claimUniqueOrNullCorrespondenceForEObject(List<EObject> eObject);

    /**
     * Returns the correspondence for the given eObject if it is unique and throws a
     * {@link RuntimeException} if there is not exactly one corresponding object.
     *
     * @param eObject
     *            the object for which the correspondence is to be returned
     * @return the correspondence for the given eObject if there is exactly one corresponding object
     */
    // FIXME MK will no longer be provided (use extension method claimOne)
    @Deprecated
    public Correspondence claimUniqueCorrespondence(EObject eObject);

    // FIXME MK will no longer be provided (use extension method claimOne)
    @Deprecated
    public Correspondence claimUniqueCorrespondence(List<EObject> eObject);

    /**
     * Returns the SameTypeCorrespondence for the given eObjects a and b and throws a
     * {@link RuntimeException} if there is no such correspondence. Note that a has to be an element
     * of metamodel a and b an instance of metamodel b.
     *
     * @param a
     * @param b
     * @return
     */
    // FIXME MK will no longer be provided (use filter(typeOf(..)) and extension method claimOne)
    @Deprecated
    public SameTypeCorrespondence claimUniqueSameTypeCorrespondence(final EObject a, final EObject b);

    /**
     * Returns all eObjects that have some correspondence and are an instance of the given class.
     *
     * @param type
     *            the class for which instances should be returned
     * @return a set containing all eObjects of the given type that have a correspondence
     */
    // FIXME rename to getAllEObjectsOfTypeInCorrespondences
    public <T> Set<T> getAllEObjectsInCorrespondencesWithType(Class<T> type);

    // FIXME rename and change param to addCorrespondence
    public void addSameTypeCorrespondence(SameTypeCorrespondence correspondence);

    /**
     * Removes all direct correspondences for the given eObject and all correspondences for children
     * of the given eObject and for children of the eObjects corresponding to the given eObject.
     * Does <b>not</b> remove any model elements (only correspondences).
     *
     * @param eObject
     *            for which all correspondences should be removed
     * @return a set containing all removed correspondences
     */
    // FIXME MK rename to removeCorrespondencesOfEObjectAndChildrenOnBothSides
    public Set<Correspondence> removeDirectAndChildrenCorrespondencesOnBothSides(EObject eObject);

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
    // FIXME MK rename to removeCorrespondencesOfEObjectAndChildrenOnBothSides
    public Set<Correspondence> removeDirectAndChildrenCorrespondencesOnBothSides(TUID tuid);

    /**
     * Removes the given correspondence, all correspondences for the eObjects of the given
     * correspondence, and all correspondences for their children on both sides. Does <b>not</b>
     * remove any model elements (only correspondences).
     *
     * @param correspondence
     *            that should be removed
     * @return a set containing all removed correspondences
     */
    // FIXME MK rename to removeCorrespondencesOfEObjectAndChildrenOnBothSides
    public Set<Correspondence> removeNeighborAndChildrenCorrespondencesOnBothSides(Correspondence correspondence);

    // FIXME MK will no longer be provided
    @Deprecated
    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(EObject parentEObject, EStructuralFeature feature);

    // FIXME MK will no longer be provided
    @Deprecated
    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(FeatureInstance featureInstance);

    // FIXME MK will no longer be provided
    @Deprecated
    public Set<FeatureInstance> claimCorrespondingFeatureInstances(FeatureInstance featureInstance);

    // FIXME MK will no longer be provided
    @Deprecated
    public FeatureInstance claimUniqueCorrespondingFeatureInstance(FeatureInstance featureInstance);

    /**
     * updates the TUID of an EObject
     *
     * @param oldEObject
     *            the old EObject
     * @param newEObject
     *            the new EObject
     */
    // FIXME MK rename to updateTUID
    public void update(EObject oldEObject, EObject newEObject);

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
    // FIXME MK rename to updateTUID
    public void update(TUID oldTUID, EObject newEObject);

    // FIXME MK rename to updateTUID
    public void update(TUID oldTUID, TUID newTUID);

    public EObject resolveEObjectFromTUID(final TUID tuid);

    public Set<EObject> resolveEObjectsFromTUIDs(final Set<TUID> tuid);

    public TUID calculateTUIDFromEObject(final EObject eObject);

    /**
     * SWAPS a and b if necessary!
     */
    // FIXME MK will no longer be provided
    @Deprecated
    public EContainmentReferenceCorrespondence createAndAddEContainmentReferenceCorrespondence(EObject a, EObject b,
            EReference referenceFeatureA, EReference referenceFeatureB);

    /**
     * SWAPS a and b if necessary!
     */
    // FIXME MK rename to createAndAddOne2OneCorrespondence
    public EObjectCorrespondence createAndAddEObjectCorrespondence(EObject a, EObject b);

    public SameTypeCorrespondence createAndAddM2NCorrespondence(List<EObject> as, List<EObject> bs);

    // FIXME MK will no longer be provided (use filter(typeOf(T)))
    @Deprecated
    public <T> Set<T> getCorrespondingEObjectsByType(EObject eObject, Class<T> type);

    EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuidString);
}