package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;

// TODO move all methods that don't need direct instance variable access to some kind of util class
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
public class CorrespondenceInstance extends ModelInstance {

    private static final Logger logger = Logger.getLogger(CorrespondenceInstance.class.getSimpleName());

    private Mapping mapping;
    private Correspondences correspondences;
    private ClaimableMap<String, Set<Correspondence>> tuid2CorrespondencesMap;
    private ClaimableMap<String, Set<EObject>> tuid2CorrespondingEObjectsMap;
    private ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap;
    // the following map (tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap) is only used to
    // correctly update the previous map (featureInstance2CorrespondingFIMap)
    private ClaimableMap<String, Set<Set<FeatureInstance>>> tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap;

    private boolean changedAfterLastSave = false;

    public CorrespondenceInstance(final Mapping mapping, final VURI correspondencesVURI,
            final Resource correspondencesResource) {
        super(correspondencesVURI, correspondencesResource);
        this.mapping = mapping;
        // TODO implement lazy loading for correspondences because they may get really big
        EObject correspondences = EcoreResourceBridge.getResourceContentRootIfUnique(correspondencesResource);
        if (correspondences == null) {
            this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
            correspondencesResource.getContents().add(this.correspondences);
        } else {
            if (correspondences instanceof Correspondences) {
                this.correspondences = (Correspondences) correspondences;
            } else {
                throw new RuntimeException("The unique root object '" + correspondences
                        + "' of the correspondence model '" + correspondencesVURI + "' is not correctly typed!");
            }
        }
        // FIXME implement loading of existing correspondences from resources (fill maps)
        // FIXME create TUIDs during loading of existing corresponding from resource
        this.tuid2CorrespondencesMap = new ClaimableHashMap<String, Set<Correspondence>>();
        this.tuid2CorrespondingEObjectsMap = new ClaimableHashMap<String, Set<EObject>>();
        this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance, Set<FeatureInstance>>();
        this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap = new ClaimableHashMap<String, Set<Set<FeatureInstance>>>();
    }

    public Mapping getMapping() {
        return this.mapping;
    }

    /**
     * Returns whether at least one object corresponds to the given object.
     * 
     * @param eObject
     *            the object for which correspondences should be looked up
     * @return # of corresponding objects > 0
     */
    public boolean hasCorrespondences(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuid);
        return correspondences != null && correspondences.size() > 0;
    }

    /**
     * Returns the correspondences for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     * 
     * @param eObject
     *            the object for which correspondences are to be returned
     * @return the correspondences for the specified object
     */
    public Set<Correspondence> claimCorrespondences(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        return claimCorrespondeceSetNotEmpty(eObject, tuid);
    }

    private Set<Correspondence> claimCorrespondeceSetNotEmpty(final EObject eObject, final String tuid) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.claimValueForKey(tuid);
        return claimCorrespondeceSetNotEmpty(eObject, tuid, correspondences);
    }

    private <T> Set<T> claimCorrespondeceSetNotEmpty(final EObject eObject, final String tuid,
            final Set<T> correspondences) {
        if (correspondences.size() > 0) {
            return correspondences;
        } else {
            throw new RuntimeException("The eObject '" + eObject + "' is only mapped to an empty correspondence set: '"
                    + correspondences + "'!");
        }
    }

    /**
     * Returns all correspondences for the specified object and an empty set if the object has no
     * correspondences. Should never return {@link null}.
     * 
     * @param eObject
     * @return all correspondences for the specified object and an empty set if the object has no
     *         correspondences.
     */
    public Set<Correspondence> getAllCorrespondences(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        return getOrCreateCorrespondenceSet(tuid);
    }

    private Set<Correspondence> getOrCreateCorrespondenceSet(final String involvedTUID) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(involvedTUID);
        if (correspondences == null) {
            correspondences = new HashSet<Correspondence>();
            this.tuid2CorrespondencesMap.put(involvedTUID, correspondences);
        }
        return correspondences;
    }

    /**
     * Returns the corresponding objects for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     * 
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return the corresponding objects for the specified object
     */
    public Set<EObject> claimCorrespondingEObjects(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        Set<EObject> correspondingEObjects = this.tuid2CorrespondingEObjectsMap.claimValueForKey(tuid);
        return claimCorrespondeceSetNotEmpty(eObject, tuid, correspondingEObjects);
    }

    /**
     * Returns all corresponding objects for the specified object and an empty set if the object has
     * no correspondences. Should never return {@link null}.
     * 
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return all corresponding objects for the specified object and an empty set if the object has
     *         no correspondences.
     */
    public Set<EObject> getAllCorrespondingEObjects(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        return getOrCreateCorrespondingEObjectsSet(tuid);
    }

    private Set<EObject> getOrCreateCorrespondingEObjectsSet(final String tuid) {
        Set<EObject> correspondingEObjects = this.tuid2CorrespondingEObjectsMap.get(tuid);
        if (correspondingEObjects == null) {
            correspondingEObjects = new HashSet<EObject>();
            this.tuid2CorrespondingEObjectsMap.put(tuid, correspondingEObjects);
        }
        return correspondingEObjects;
    }

    /**
     * Returns the corresponding object for the specified object if there is exactly one
     * corresponding object and throws a {@link java.lang.RuntimeException} otherwise.
     * 
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @return the corresponding object for the specified object if there is exactly one
     *         corresponding object
     */
    public EObject claimUniqueCorrespondingEObject(final EObject eObject) {
        Set<EObject> correspondingEObjects = claimCorrespondingEObjects(eObject);
        if (correspondingEObjects.size() != 1) {
            throw new RuntimeException("The eObjects corresponding to '" + eObject + "' are not unique: "
                    + correspondingEObjects);
        }
        return correspondingEObjects.iterator().next();
    }

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
    public <T> Set<T> claimCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
        Set<EObject> correspondingEObjects = claimCorrespondingEObjects(eObject);
        Set<T> correspondingEObjectsByType = new HashSet<T>();
        for (EObject correspondingEObject : correspondingEObjects) {
            if (type.isInstance(correspondingEObject)) {
                correspondingEObjectsByType.add(type.cast(correspondingEObject));
            }
        }
        if (correspondingEObjectsByType.size() == 0) {
            throw new RuntimeException("There are no eObjects of type '" + type + "' that correspond to the eObject '"
                    + eObject + "!");
        }
        return correspondingEObjectsByType;
    }

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
    public <T> T claimUniqueCorrespondingEObjectByType(final EObject eObject, final Class<T> type) {
        Set<T> correspondingEObjectsByType = claimCorrespondingEObjectsByType(eObject, type);
        if (1 != correspondingEObjectsByType.size()) {
            throw new RuntimeException("claimCorrespondingEObjectForTypeIfUnique failed: "
                    + correspondingEObjectsByType.size() + " corresponding objects found (expected 1)"
                    + correspondingEObjectsByType);
        }
        return correspondingEObjectsByType.iterator().next();
    }

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
    public Correspondence claimUniqueOrNullCorrespondenceForEObject(final EObject eObject) {
        if (!hasCorrespondences(eObject)) {
            return null;
        }
        return claimUniqueCorrespondence(eObject);
    }

    /**
     * Returns the correspondence for the given eObject if it is unique and throws a
     * {@link RuntimeException} if there is not exactly one corresponding object.
     * 
     * @param eObject
     *            the object for which the correspondence is to be returned
     * @return the correspondence for the given eObject if there is exactly one corresponding object
     */
    public Correspondence claimUniqueCorrespondence(final EObject eObject) {
        Set<Correspondence> objectCorrespondences = claimCorrespondences(eObject);
        if (objectCorrespondences.size() != 1) {
            throw new RuntimeException("The correspondence for eObject '" + eObject + "' is not unique: "
                    + objectCorrespondences);
        }
        return objectCorrespondences.iterator().next();
    }

    /**
     * Returns all eObjects that have some correspondence and are an instance of the given class.
     * 
     * @param type
     *            the class for which instances should be returned
     * @return a set containing all eObjects of the given type that have a correspondence
     */
    public <T> Set<T> getAllEObjectsInCorrespondencesWithType(final Class<T> type) {
        Set<T> correspondencesWithType = new HashSet<T>();
        for (Correspondence correspondence : this.correspondences.getCorrespondences()) {
            if (correspondence instanceof EObjectCorrespondence) {
                EObjectCorrespondence eObjectCorrespondence = (EObjectCorrespondence) correspondence;
                if (type.isInstance(eObjectCorrespondence.getElementA())) {
                    @SuppressWarnings("unchecked")
                    T t = (T) eObjectCorrespondence.getElementA();
                    correspondencesWithType.add(t);
                } else if (type.isInstance(eObjectCorrespondence.getElementB())) {
                    @SuppressWarnings("unchecked")
                    T t = (T) eObjectCorrespondence.getElementB();
                    correspondencesWithType.add(t);
                }
            }
        }
        return correspondencesWithType;
    }

    public void addSameTypeCorrespondence(final SameTypeCorrespondence correspondence) {
        addSameTypeCorrespondence(correspondence, null);
    }

    public void addSameTypeCorrespondence(final SameTypeCorrespondence correspondence, final Correspondence parent) {
        EObject elementA = correspondence.getElementA();
        EObject elementB = correspondence.getElementB();
        // add TUIDs
        String tuidA = this.mapping.getMetamodelA().getTUID(elementA);
        String tuidB = this.mapping.getMetamodelB().getTUID(elementB);
        correspondence.setElementATUID(tuidA);
        correspondence.setElementBTUID(tuidB);
        // add correspondence to model
        EList<Correspondence> correspondenceListForAddition;
        if (parent == null) {
            correspondenceListForAddition = this.correspondences.getCorrespondences();
        } else {
            correspondenceListForAddition = parent.getDependentCorrespondences();
        }
        correspondenceListForAddition.add(correspondence);
        List<EObject> allInvolvedEObjects = Arrays.asList(elementA, elementB);
        List<String> allInvolvedTUIDs = Arrays.asList(tuidA, tuidB);
        // add all involved eObjects to the sets for these objects in the map
        for (String involvedTUID : allInvolvedTUIDs) {
            Set<Correspondence> correspondences = getOrCreateCorrespondenceSet(involvedTUID);
            if (!correspondences.contains(correspondence)) {
                correspondences.add(correspondence);
            }
        }
        if (correspondence instanceof EObjectCorrespondence) {
            for (String involvedTUID : allInvolvedTUIDs) {
                Set<EObject> correspondingEObjects = getOrCreateCorrespondingEObjectsSet(involvedTUID);
                correspondingEObjects.addAll(allInvolvedEObjects);
                if (involvedTUID.equals(tuidA)) {
                    correspondingEObjects.remove(elementA);
                } else if (involvedTUID.equals(tuidB)) {
                    correspondingEObjects.remove(elementB);
                } else {
                    throw new RuntimeException("allInvolvedEObjects ('" + allInvolvedEObjects
                            + "' contained a TUID that is neither '" + tuidA + "' nor '" + tuidB + "'!");
                }
            }
        } else if (correspondence instanceof EFeatureCorrespondence) {
            EFeatureCorrespondence<?> featureCorrespondence = (EFeatureCorrespondence<?>) correspondence;
            FeatureInstance featureInstanceA = FeatureInstance.getInstance(featureCorrespondence.getElementA(),
                    featureCorrespondence.getFeatureA());
            FeatureInstance featureInstanceB = FeatureInstance.getInstance(featureCorrespondence.getElementB(),
                    featureCorrespondence.getFeatureB());
            Set<FeatureInstance> featureInstancesCorrespondingToFIA = this.featureInstance2CorrespondingFIMap
                    .get(featureInstanceA);
            if (featureInstancesCorrespondingToFIA == null) {
                featureInstancesCorrespondingToFIA = new HashSet<FeatureInstance>();
                this.featureInstance2CorrespondingFIMap.put(featureInstanceA, featureInstancesCorrespondingToFIA);
            }
            featureInstancesCorrespondingToFIA.add(featureInstanceB);
            storeFeatureInstancesForTUID(tuidB, featureInstancesCorrespondingToFIA);

            Set<FeatureInstance> featureInstancesCorrespondingToFIB = this.featureInstance2CorrespondingFIMap
                    .get(featureInstanceB);
            if (featureInstancesCorrespondingToFIB == null) {
                featureInstancesCorrespondingToFIB = new HashSet<FeatureInstance>();
                this.featureInstance2CorrespondingFIMap.put(featureInstanceB, featureInstancesCorrespondingToFIB);
            }
            featureInstancesCorrespondingToFIB.add(featureInstanceA);
            // store the usage of a feature instance with a parent object that has the tuid tuidA
            storeFeatureInstancesForTUID(tuidA, featureInstancesCorrespondingToFIB);
        }
        setChangeAfterLastSaveFlag();
    }

    private void setChangeAfterLastSaveFlag() {
        this.changedAfterLastSave = true;
    }

    public boolean changedAfterLastSave() {
        return this.changedAfterLastSave;
    }

    public void resetChangedAfterLastSave() {
        this.changedAfterLastSave = false;
    }

    private void storeFeatureInstancesForTUID(final String tuid,
            final Set<FeatureInstance> correspondenceSetWithFIofTUID) {
        Set<Set<FeatureInstance>> correspondeceSetsWithFIsOfTUID = this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap
                .get(tuid);
        if (correspondeceSetsWithFIsOfTUID == null) {
            correspondeceSetsWithFIsOfTUID = new HashSet<Set<FeatureInstance>>();
            this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap.put(tuid, correspondeceSetsWithFIsOfTUID);
        }
        correspondeceSetsWithFIsOfTUID.add(correspondenceSetWithFIofTUID);
    }

    /**
     * Removes all correspondences for the given eObject and all child-correspondences of these
     * correspondences.
     * 
     * @param eObject
     *            from which all correspondences should be removed
     */
    public void removeAllCorrespondences(final EObject eObject) {
        // TODO: Check if it is working
        String tuid = getTUIDFromEObject(eObject);
        Set<Correspondence> correspondencesForEObj = this.tuid2CorrespondencesMap.get(tuid);
        if (null == correspondencesForEObj) {
            return;
        }
        this.tuid2CorrespondencesMap.remove(tuid);
        for (Correspondence correspondence : correspondencesForEObj) {
            removeCorrespondenceAndAllDependentCorrespondences(correspondence);
        }

        // FIXME: remove feature correspondences
    }

    /**
     * Removes correspondence and all child Correspondences of this correspondence
     * 
     * @param correspondence
     */
    public void removeCorrespondenceAndAllDependentCorrespondences(final Correspondence correspondence) {
        Set<Correspondence> dependencyList = new HashSet<Correspondence>();
        removeCorrespondenceAndAllDependentCorrespondences(correspondence, dependencyList);
    }

    /**
     * Does the removing recursively. Marks all correspondences that will be deleted in a
     * dependencyList --> Avoid stack overflow with correspondences that have a mutual dependency
     * 
     * @param correspondence
     * @param dependencyList
     */
    private void removeCorrespondenceAndAllDependentCorrespondences(final Correspondence correspondence,
            final Set<Correspondence> dependencyList) {
        if (null == correspondence || null == correspondence.getDependentCorrespondences()) {
            return;
        }
        dependencyList.add(correspondence);
        for (Correspondence dependentCorrespondence : correspondence.getDependentCorrespondences()) {
            if (null != dependentCorrespondence && !dependencyList.contains(dependentCorrespondence)) {
                removeCorrespondenceFromMaps(dependentCorrespondence);
                removeCorrespondenceAndAllDependentCorrespondences(dependentCorrespondence, dependencyList);
            }
        }
        removeCorrespondenceFromMaps(correspondence);
        EcoreUtil.remove(correspondence);
        setChangeAfterLastSaveFlag();
    }

    private void removeCorrespondenceFromMaps(final Correspondence possibleChildCorrespondence) {
        if (possibleChildCorrespondence instanceof EObjectCorrespondence) {
            EObjectCorrespondence eObjCorrespondence = (EObjectCorrespondence) possibleChildCorrespondence;
            String elementATUID = getTUIDFromEObject(eObjCorrespondence.getElementA());
            String elementBTUID = getTUIDFromEObject(eObjCorrespondence.getElementB());
            this.tuid2CorrespondencesMap.remove(elementATUID);
            this.tuid2CorrespondencesMap.remove(elementBTUID);
            this.tuid2CorrespondingEObjectsMap.remove(elementATUID);
            this.tuid2CorrespondingEObjectsMap.remove(elementBTUID);
        }
    }

    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final EObject parentEObject,
            final EStructuralFeature feature) {
        FeatureInstance featureInstance = FeatureInstance.getInstance(parentEObject, feature);
        return getAllCorrespondingFeatureInstances(featureInstance);
    }

    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.featureInstance2CorrespondingFIMap.get(featureInstance);
    }

    public Set<FeatureInstance> claimCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.featureInstance2CorrespondingFIMap.claimValueForKey(featureInstance);
    }

    public FeatureInstance claimUniqueCorrespondingFeatureInstance(final FeatureInstance featureInstance) {
        Set<FeatureInstance> featureInstances = claimCorrespondingFeatureInstances(featureInstance);
        if (featureInstances.size() != 1) {
            throw new RuntimeException("The feature instance corresponding to '" + featureInstance
                    + "' is not unique: " + featureInstances);
        }
        return featureInstances.iterator().next();
    }

    private String getTUIDFromEObject(final EObject eObject) {
        if (this.mapping.getMetamodelA().hasMetaclassInstance(eObject)) {
            return this.mapping.getMetamodelA().getTUID(eObject);
        }
        if (this.mapping.getMetamodelB().hasMetaclassInstance(eObject)) {
            return this.mapping.getMetamodelB().getTUID(eObject);
        }
        logger.warn("EObject: '" + eObject
                + "' is neither an instance of MM1 nor an instance of MM2. NsURI of object: "
                + eObject.eClass().getEPackage().getNsURI());
        return null;
    }

    public void update(final EObject oldEObject, final EObject newEObject) {
        String oldTUID = getTUIDFromEObject(oldEObject);
        String newTUID = getTUIDFromEObject(newEObject);
        boolean sameTUID = oldTUID != null ? oldTUID.equals(newTUID) : newTUID == null;
        updateTUID2CorrespondencesMap(oldEObject, newEObject, oldTUID, newTUID, sameTUID);

        updateTUID2CorrespondingEObjectsMap(oldTUID, newTUID, sameTUID);

        updateFeatureInstances(oldEObject, newEObject, oldTUID);
    }

    private void updateFeatureInstances(final EObject oldEObject, final EObject newEObject, final String oldTUID) {
        Collection<FeatureInstance> oldFeatureInstances = FeatureInstance.getAllInstances(oldEObject);

        // WARNING: We assume that everybody that uses the FeatureInstance multiton wants to be
        // informed of this update
        FeatureInstance.update(oldEObject, newEObject);

        for (FeatureInstance oldFeatureInstance : oldFeatureInstances) {
            Set<FeatureInstance> correspondingFeatureInstances = this.featureInstance2CorrespondingFIMap
                    .remove(oldFeatureInstance);
            if (correspondingFeatureInstances != null) {
                EStructuralFeature feature = oldFeatureInstance.getFeature();
                FeatureInstance newFeatureInstance = FeatureInstance.getInstance(newEObject, feature);
                this.featureInstance2CorrespondingFIMap.put(newFeatureInstance, correspondingFeatureInstances);
            }
        }

        Set<Set<FeatureInstance>> correspondenceSetsWithFIofOldTUID = this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap
                .get(oldTUID);
        for (Set<FeatureInstance> correspondenceSetWithFIofOldTUID : correspondenceSetsWithFIofOldTUID) {
            for (FeatureInstance featureInstance : correspondenceSetWithFIofOldTUID) {
                if (oldFeatureInstances.contains(featureInstance)) {
                    // featureInstance belongs to oldTUID
                    correspondenceSetWithFIofOldTUID.remove(featureInstance);
                    EStructuralFeature feature = featureInstance.getFeature();
                    FeatureInstance newFeatureInstance = FeatureInstance.getInstance(newEObject, feature);
                    correspondenceSetWithFIofOldTUID.add(newFeatureInstance);
                }
            }
        }
        setChangeAfterLastSaveFlag();
    }

    private void updateTUID2CorrespondingEObjectsMap(final String oldTUID, final String newTUID, final boolean sameTUID) {
        if (!sameTUID) {
            Set<EObject> correspondingEObjects = this.tuid2CorrespondingEObjectsMap.remove(oldTUID);
            this.tuid2CorrespondingEObjectsMap.put(newTUID, correspondingEObjects);
        }
    }

    private void updateTUID2CorrespondencesMap(final EObject oldEObject, final EObject newEObject,
            final String oldTUID, final String newTUID, final boolean sameTUID) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(oldTUID);
        for (Correspondence correspondence : correspondences) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence stc = (SameTypeCorrespondence) correspondence;
                if (oldTUID != null && oldTUID.equals(stc.getElementATUID())) {
                    stc.setElementA(newEObject);
                    if (!sameTUID) {
                        stc.setElementATUID(newTUID);
                    }

                    // update incoming links in tuid2CorrespondingEObjectsMap
                    String elementBTUID = stc.getElementBTUID();
                    updateCorrespondingLinksForUpdatedEObject(oldEObject, newEObject, oldTUID, elementBTUID);
                } else if (oldTUID != null && oldTUID.equals(stc.getElementBTUID())) {
                    stc.setElementB(newEObject);
                    if (!sameTUID) {
                        stc.setElementBTUID(newTUID);
                    }

                    // update incoming links in tuid2CorrespondingEObjectsMap
                    String elementATUID = stc.getElementATUID();
                    updateCorrespondingLinksForUpdatedEObject(oldEObject, newEObject, oldTUID, elementATUID);

                } else {
                    throw new RuntimeException("None of the corresponding elements in '" + correspondence
                            + "' has the TUID '" + oldTUID + "'!");
                }
                if (!sameTUID) {
                    this.tuid2CorrespondencesMap.remove(oldTUID);
                    this.tuid2CorrespondencesMap.put(newTUID, correspondences);
                }
            }
            // TODO handle not same type correspondence case
        }
    }

    private void updateCorrespondingLinksForUpdatedEObject(final EObject oldEObject, final EObject newEObject,
            final String oldTUID, final String elementBTUID) {
        Set<EObject> correspondingEObjects = this.tuid2CorrespondingEObjectsMap.get(elementBTUID);
        for (EObject correspondingEObject : correspondingEObjects) {
            String correspondingTUID = getTUIDFromEObject(correspondingEObject);
            if (correspondingTUID != null && correspondingTUID.equals(oldTUID)) {
                correspondingEObjects.remove(oldEObject);
                correspondingEObjects.add(newEObject);
            }
        }
    }
}
