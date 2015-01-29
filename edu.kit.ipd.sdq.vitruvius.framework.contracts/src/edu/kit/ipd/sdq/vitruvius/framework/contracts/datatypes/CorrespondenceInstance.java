package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
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

    public Resource getResource();

    public Mapping getMapping();

    /**
     * Returns whether at least one object corresponds to the given object.
     *
     * @param eObject
     *            the object for which correspondences should be looked up
     * @return # of corresponding objects > 0
     */
    public boolean hasCorrespondences(EObject eObject);

    /**
     * Returns the correspondences for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     *
     * @param eObject
     *            the object for which correspondences are to be returned
     * @return the correspondences for the specified object
     */
    public Set<Correspondence> claimCorrespondences(EObject eObject);

    /**
     * Returns all correspondences for the specified object and an empty set if the object has no
     * correspondences. Should never return {@link null}.
     *
     * @param eObject
     * @return all correspondences for the specified object and an empty set if the object has no
     *         correspondences.
     */
    public Set<Correspondence> getAllCorrespondences(EObject eObject);

    /**
     * Returns the corresponding objects for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return the corresponding objects for the specified object
     */
    public Set<EObject> claimCorrespondingEObjects(EObject eObject);

    /**
     * Returns all corresponding objects for the specified object and an empty set if the object has
     * no correspondences. Should never return {@link null}.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return all corresponding objects for the specified object and an empty set if the object has
     *         no correspondences.
     */
    public Set<EObject> getAllCorrespondingEObjects(EObject eObject);

    /**
     * Returns the corresponding object for the specified object if there is exactly one
     * corresponding object and throws a {@link java.lang.RuntimeException} otherwise.
     *
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @return the corresponding object for the specified object if there is exactly one
     *         corresponding object
     */
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
    public Correspondence claimUniqueOrNullCorrespondenceForEObject(EObject eObject);

    /**
     * Returns the correspondence for the given eObject if it is unique and throws a
     * {@link RuntimeException} if there is not exactly one corresponding object.
     *
     * @param eObject
     *            the object for which the correspondence is to be returned
     * @return the correspondence for the given eObject if there is exactly one corresponding object
     */
    public Correspondence claimUniqueCorrespondence(EObject eObject);

    /**
     * Returns the SameTypeCorrespondence for the given eObjects a and b and throws a
     * {@link RuntimeException} if there is no such correspondence. Note that a has to be an element
     * of metamodel a and b an instance of metamodel b.
     * 
     * @param a
     * @param b
     * @return
     */
    public SameTypeCorrespondence claimUniqueSameTypeCorrespondence(final EObject a, final EObject b);

    /**
     * Returns all eObjects that have some correspondence and are an instance of the given class.
     *
     * @param type
     *            the class for which instances should be returned
     * @return a set containing all eObjects of the given type that have a correspondence
     */
    public <T> Set<T> getAllEObjectsInCorrespondencesWithType(Class<T> type);

    public void addSameTypeCorrespondence(SameTypeCorrespondence correspondence);

    public void addSameTypeCorrespondence(SameTypeCorrespondence correspondence, Correspondence parent);

    public boolean changedAfterLastSave();

    public void resetChangedAfterLastSave();

    /**
     * Removes all correspondences for the given eObject and all child-correspondences of these
     * correspondences.
     *
     * @param eObject
     *            from which all correspondences should be removed
     */
    public void removeAllCorrespondences(EObject eObject);

    /**
     * Removes all correspondences for the given tuid and all child-correspondences of these
     * correspondences.
     *
     * @param eObject
     *            from which all correspondences should be removed
     */
    public void removeCorrespondenceAndAllDependentCorrespondences(TUID tuid);

    /**
     * Removes correspondence and all child Correspondences of this correspondence
     *
     * @param correspondence
     */
    public void removeCorrespondenceAndAllDependentCorrespondences(Correspondence correspondence);

    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(EObject parentEObject, EStructuralFeature feature);

    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(FeatureInstance featureInstance);

    public Set<FeatureInstance> claimCorrespondingFeatureInstances(FeatureInstance featureInstance);

    public FeatureInstance claimUniqueCorrespondingFeatureInstance(FeatureInstance featureInstance);

    /**
     * updates the TUID of an EObject
     *
     * @param oldEObject
     *            the old EObject
     * @param newEObject
     *            the new EObject
     */
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
    public void update(TUID oldTUID, EObject newEObject);

    public void update(TUID oldTUID, TUID newTUID);

    public EObject resolveEObjectFromTUID(final TUID tuid);

    public Set<EObject> resolveEObjectsFromTUIDs(final Set<TUID> tuid);

    public TUID calculateTUIDFromEObject(final EObject eObject);

    /**
     * SWAPS a and b if necessary!
     */
    public EContainmentReferenceCorrespondence createAndAddEContainmentReferenceCorrespondence(EObject a, EObject b,
            EReference referenceFeatureA, EReference referenceFeatureB);

    /**
     * SWAPS a and b if necessary!
     */
    public EContainmentReferenceCorrespondence createAndAddEContainmentReferenceCorrespondence(EObject a, EObject b,
            EReference referenceFeatureA, EReference referenceFeatureB, Correspondence parent);

    /**
     * SWAPS a and b if necessary!
     */
    public EObjectCorrespondence createAndAddEObjectCorrespondence(EObject a, EObject b);

    /**
     * SWAPS a and b if necessary!
     */
    public EObjectCorrespondence createAndAddEObjectCorrespondence(EObject a, EObject b, Correspondence parent);

    public <T> Set<T> getCorrespondingEObjectsByType(EObject eObject, Class<T> type);

}