package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;

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
public class CorrespondenceInstanceImpl extends ModelInstance implements CorrespondenceInstance {

    private static final Logger logger = Logger.getLogger(CorrespondenceInstanceImpl.class.getSimpleName());

    private Mapping mapping;
    private ModelProviding modelProviding;
    private Correspondences correspondences;
    private ClaimableMap<TUID, Set<Correspondence>> tuid2CorrespondencesMap;
    private ClaimableMap<TUID, Set<TUID>> tuid2CorrespondingTUIDsMap;
    private ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap;
    // the following map (tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap) is only used to
    // correctly update the previous map (featureInstance2CorrespondingFIMap)
    private ClaimableMap<TUID, Set<Set<FeatureInstance>>> tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap;

    private boolean changedAfterLastSave = false;

    public CorrespondenceInstanceImpl(final Mapping mapping, final ModelProviding modelProviding,
            final VURI correspondencesVURI, final Resource correspondencesResource) {
        super(correspondencesVURI, correspondencesResource);
        this.mapping = mapping;
        this.modelProviding = modelProviding;
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
        this.tuid2CorrespondencesMap = new ClaimableHashMap<TUID, Set<Correspondence>>();
        this.tuid2CorrespondingTUIDsMap = new ClaimableHashMap<TUID, Set<TUID>>();
        this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance, Set<FeatureInstance>>();
        this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap = new ClaimableHashMap<TUID, Set<Set<FeatureInstance>>>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#getMapping()
     */
    @Override
    public Mapping getMapping() {
        return this.mapping;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#hasCorrespondences
     * (org.eclipse.emf.ecore.EObject)
     */
    @Override
    public boolean hasCorrespondences(final EObject eObject) {
        TUID tuid = calculateTUIDFromEObject(eObject);
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuid);
        return correspondences != null && correspondences.size() > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimCorrespondences(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Set<Correspondence> claimCorrespondences(final EObject eObject) {
        TUID tuid = calculateTUIDFromEObject(eObject);
        return claimCorrespondeceSetNotEmpty(eObject, tuid);
    }

    private Set<Correspondence> claimCorrespondeceSetNotEmpty(final EObject eObject, final TUID tuid) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.claimValueForKey(tuid);
        return claimCorrespondenceSetNotEmpty(eObject, tuid, correspondences);
    }

    private <T> Set<T> claimCorrespondenceSetNotEmpty(final EObject eObject, final TUID tuid,
            final Set<T> correspondences) {
        if (correspondences.size() > 0) {
            return correspondences;
        } else {
            throw new RuntimeException("The eObject '" + eObject + "' is only mapped to an empty correspondence set: '"
                    + correspondences + "'!");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getAllCorrespondences(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Set<Correspondence> getAllCorrespondences(final EObject eObject) {
        TUID tuid = calculateTUIDFromEObject(eObject);
        return getOrCreateCorrespondenceSet(tuid);
    }

    private Set<Correspondence> getOrCreateCorrespondenceSet(final TUID involvedTUID) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(involvedTUID);
        if (correspondences == null) {
            correspondences = new HashSet<Correspondence>();
            this.tuid2CorrespondencesMap.put(involvedTUID, correspondences);
        }
        return correspondences;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimCorrespondingEObjects(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Set<EObject> claimCorrespondingEObjects(final EObject eObject) {
        TUID tuid = calculateTUIDFromEObject(eObject);
        Set<TUID> correspondingTUIDs = this.tuid2CorrespondingTUIDsMap.claimValueForKey(tuid);
        claimCorrespondenceSetNotEmpty(eObject, tuid, correspondingTUIDs);
        return resolveEObjectsFromTUIDs(correspondingTUIDs);
    }

    @Override
    public Set<EObject> resolveEObjectsFromTUIDs(final Set<TUID> tuids) {
        Set<EObject> eObjects = new HashSet<EObject>(tuids.size());
        for (TUID tuid : tuids) {
            eObjects.add(resolveEObjectFromTUID(tuid));
        }
        return eObjects;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getAllCorrespondingEObjects(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Set<EObject> getAllCorrespondingEObjects(final EObject eObject) {
        TUID tuid = calculateTUIDFromEObject(eObject);
        return resolveEObjectsFromTUIDs(getOrCreateCorrespondingEObjectsSet(tuid));
    }

    private Set<TUID> getOrCreateCorrespondingEObjectsSet(final TUID tuid) {
        Set<TUID> correspondingEObjects = this.tuid2CorrespondingTUIDsMap.get(tuid);
        if (correspondingEObjects == null) {
            correspondingEObjects = new HashSet<TUID>();
            this.tuid2CorrespondingTUIDsMap.put(tuid, correspondingEObjects);
        }
        return correspondingEObjects;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimUniqueCorrespondingEObject(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public EObject claimUniqueCorrespondingEObject(final EObject eObject) {
        Set<EObject> correspondingEObjects = claimCorrespondingEObjects(eObject);
        if (correspondingEObjects.size() != 1) {
            throw new RuntimeException("The eObjects corresponding to '" + eObject + "' are not unique: "
                    + correspondingEObjects);
        }
        return correspondingEObjects.iterator().next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimCorrespondingEObjectsByType(org.eclipse.emf.ecore.EObject, java.lang.Class)
     */
    @Override
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

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getCorrespondingEObjectsByType(org.eclipse.emf.ecore.EObject, java.lang.Class)
     */
    @Override
    public <T> Set<T> getCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
        try {
            return claimCorrespondingEObjectsByType(eObject, type);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimUniqueCorrespondingEObjectByType(org.eclipse.emf.ecore.EObject, java.lang.Class)
     */
    @Override
    public <T> T claimUniqueCorrespondingEObjectByType(final EObject eObject, final Class<T> type) {
        Set<T> correspondingEObjectsByType = claimCorrespondingEObjectsByType(eObject, type);
        if (1 != correspondingEObjectsByType.size()) {
            throw new RuntimeException("claimCorrespondingEObjectForTypeIfUnique failed: "
                    + correspondingEObjectsByType.size() + " corresponding objects found (expected 1)"
                    + correspondingEObjectsByType);
        }
        return correspondingEObjectsByType.iterator().next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimUniqueOrNullCorrespondenceForEObject(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Correspondence claimUniqueOrNullCorrespondenceForEObject(final EObject eObject) {
        if (!hasCorrespondences(eObject)) {
            return null;
        }
        return claimUniqueCorrespondence(eObject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimUniqueCorrespondence(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Correspondence claimUniqueCorrespondence(final EObject eObject) {
        Set<Correspondence> objectCorrespondences = claimCorrespondences(eObject);
        if (objectCorrespondences.size() != 1) {
            throw new RuntimeException("The correspondence for eObject '" + eObject + "' is not unique: "
                    + objectCorrespondences);
        }
        return objectCorrespondences.iterator().next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getAllEObjectsInCorrespondencesWithType(java.lang.Class)
     */
    @Override
    public <T> Set<T> getAllEObjectsInCorrespondencesWithType(final Class<T> type) {
        Set<T> correspondencesWithType = new HashSet<T>();
        for (Correspondence correspondence : this.correspondences.getCorrespondences()) {
            if (correspondence instanceof EObjectCorrespondence) {
                EObjectCorrespondence eObjectCorrespondence = (EObjectCorrespondence) correspondence;
                EObject element = resolveEObjectFromTUID(eObjectCorrespondence.getElementATUID());
                if (type.isInstance(element)) {
                    @SuppressWarnings("unchecked")
                    T t = (T) element;
                    correspondencesWithType.add(t);
                } else {
                    element = resolveEObjectFromTUID(eObjectCorrespondence.getElementBTUID());
                    if (type.isInstance(element)) {
                        @SuppressWarnings("unchecked")
                        T t = (T) element;
                        correspondencesWithType.add(t);
                    }
                }
            }
        }
        return correspondencesWithType;
    }

    @Override
    public EObject resolveEObjectFromTUID(final TUID tuid) {
        String tuidString = tuid.toString();
        Metamodel metamodel = null;
        if (this.mapping.getMetamodelA().hasTUID(tuidString)) {
            metamodel = this.mapping.getMetamodelA();
        }
        if (this.mapping.getMetamodelB().hasTUID(tuidString)) {
            metamodel = this.mapping.getMetamodelB();
        }
        if (metamodel == null) {
            throw new IllegalArgumentException("The TUID '" + tuid + "' is neither valid for "
                    + this.mapping.getMetamodelA() + " nor " + this.mapping.getMetamodelB());
        } else {
            VURI vuri = metamodel.getModelVURIContainingIdentifiedEObject(tuidString);
            EObject rootEObject = this.modelProviding.getAndLoadModelInstanceOriginal(vuri).getUniqueRootEObject();
            return metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * addSameTypeCorrespondence
     * (edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence)
     */
    @Override
    public void addSameTypeCorrespondence(final SameTypeCorrespondence correspondence) {
        addSameTypeCorrespondence(correspondence, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * addSameTypeCorrespondence
     * (edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence,
     * edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence)
     */
    @Override
    public void addSameTypeCorrespondence(final SameTypeCorrespondence correspondence, final Correspondence parent) {
        TUID tuidA = correspondence.getElementATUID();
        TUID tuidB = correspondence.getElementBTUID();
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
        List<TUID> allInvolvedTUIDs = Arrays.asList(tuidA, tuidB);
        // add all involved eObjects to the sets for these objects in the map
        for (TUID involvedTUID : allInvolvedTUIDs) {
            Set<Correspondence> correspondences = getOrCreateCorrespondenceSet(involvedTUID);
            if (!correspondences.contains(correspondence)) {
                correspondences.add(correspondence);
            }
        }
        if (correspondence instanceof EObjectCorrespondence) {
            for (TUID involvedTUID : allInvolvedTUIDs) {
                Set<TUID> correspondingTUIDs = getOrCreateCorrespondingEObjectsSet(involvedTUID);
                correspondingTUIDs.addAll(allInvolvedTUIDs);
                if (involvedTUID.equals(tuidA)) {
                    correspondingTUIDs.remove(tuidA);
                } else if (involvedTUID.equals(tuidB)) {
                    correspondingTUIDs.remove(tuidB);
                } else {
                    throw new RuntimeException("allInvolvedTUIDs ('" + allInvolvedTUIDs
                            + "' contained a TUID that is neither '" + tuidA + "' nor '" + tuidB + "'!");
                }
            }
        } else if (correspondence instanceof EFeatureCorrespondence) {
            // EFeatureCorrespondence<?> featureCorrespondence = (EFeatureCorrespondence<?>)
            // correspondence;
            // FeatureInstance featureInstanceA =
            // FeatureInstance.getInstance(featureCorrespondence.getElementA(),
            // featureCorrespondence.getFeatureA());
            // FeatureInstance featureInstanceB =
            // FeatureInstance.getInstance(featureCorrespondence.getElementB(),
            // featureCorrespondence.getFeatureB());
            // Set<FeatureInstance> featureInstancesCorrespondingToFIA =
            // this.featureInstance2CorrespondingFIMap
            // .get(featureInstanceA);
            // if (featureInstancesCorrespondingToFIA == null) {
            // featureInstancesCorrespondingToFIA = new HashSet<FeatureInstance>();
            // this.featureInstance2CorrespondingFIMap.put(featureInstanceA,
            // featureInstancesCorrespondingToFIA);
            // }
            // featureInstancesCorrespondingToFIA.add(featureInstanceB);
            // storeFeatureInstancesForTUID(tuidB, featureInstancesCorrespondingToFIA);
            //
            // Set<FeatureInstance> featureInstancesCorrespondingToFIB =
            // this.featureInstance2CorrespondingFIMap
            // .get(featureInstanceB);
            // if (featureInstancesCorrespondingToFIB == null) {
            // featureInstancesCorrespondingToFIB = new HashSet<FeatureInstance>();
            // this.featureInstance2CorrespondingFIMap.put(featureInstanceB,
            // featureInstancesCorrespondingToFIB);
            // }
            // featureInstancesCorrespondingToFIB.add(featureInstanceA);
            // // store the usage of a feature instance with a parent object that has the tuid tuidA
            // storeFeatureInstancesForTUID(tuidA, featureInstancesCorrespondingToFIB);
            // FIXME AAA MAX reactivate feature instance stuff without EObject
        }
        setChangeAfterLastSaveFlag();
    }

    private void setChangeAfterLastSaveFlag() {
        this.changedAfterLastSave = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * changedAfterLastSave()
     */
    @Override
    public boolean changedAfterLastSave() {
        return this.changedAfterLastSave;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * resetChangedAfterLastSave()
     */
    @Override
    public void resetChangedAfterLastSave() {
        this.changedAfterLastSave = false;
    }

    private void storeFeatureInstancesForTUID(final TUID tuid, final Set<FeatureInstance> correspondenceSetWithFIofTUID) {
        Set<Set<FeatureInstance>> correspondeceSetsWithFIsOfTUID = this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap
                .get(tuid);
        if (correspondeceSetsWithFIsOfTUID == null) {
            correspondeceSetsWithFIsOfTUID = new HashSet<Set<FeatureInstance>>();
            this.tuid2CorrespondenceSetsWithComprisedFeatureInstanceMap.put(tuid, correspondeceSetsWithFIsOfTUID);
        }
        correspondeceSetsWithFIsOfTUID.add(correspondenceSetWithFIofTUID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * removeAllCorrespondences(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void removeAllCorrespondences(final EObject eObject) {
        // TODO: Check if it is working
        TUID tuid = calculateTUIDFromEObject(eObject);
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

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * removeCorrespondenceAndAllDependentCorrespondences
     * (edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence)
     */
    @Override
    public void removeCorrespondenceAndAllDependentCorrespondences(final Correspondence correspondence) {
        Set<Correspondence> dependencyList = new HashSet<Correspondence>();
        Set<Correspondence> deletionList = new HashSet<Correspondence>();
        markCorrespondenceAndAllDependentCorrespondences(correspondence, dependencyList, deletionList);
        for (Correspondence markedCorrespondence : deletionList) {
            removeCorrespondenceFromMaps(markedCorrespondence);
            EcoreUtil.remove(markedCorrespondence);
            setChangeAfterLastSaveFlag();
        }
    }

    /**
     * Does the removing recursively. Marks all correspondences that will be deleted in a
     * dependencyList --> Avoid stack overflow with correspondences that have a mutual dependency
     * 
     * @param correspondence
     * @param dependencyList
     * @param deletionList
     */
    private void markCorrespondenceAndAllDependentCorrespondences(final Correspondence correspondence,
            final Set<Correspondence> dependencyList, final Set<Correspondence> deletionList) {
        if (null == correspondence || null == correspondence.getDependentCorrespondences()) {
            return;
        }
        dependencyList.add(correspondence);
        for (Correspondence dependentCorrespondence : correspondence.getDependentCorrespondences()) {
            if (null != dependentCorrespondence && !dependencyList.contains(dependentCorrespondence)) {
                removeCorrespondenceFromMaps(dependentCorrespondence);
                markCorrespondenceAndAllDependentCorrespondences(dependentCorrespondence, dependencyList, deletionList);
            }
        }
        deletionList.add(correspondence);
    }

    private void removeCorrespondenceFromMaps(final Correspondence possibleChildCorrespondence) {
        if (possibleChildCorrespondence instanceof EObjectCorrespondence) {
            EObjectCorrespondence eObjCorrespondence = (EObjectCorrespondence) possibleChildCorrespondence;
            TUID elementATUID = eObjCorrespondence.getElementATUID();
            TUID elementBTUID = eObjCorrespondence.getElementBTUID();
            this.tuid2CorrespondencesMap.remove(elementATUID);
            this.tuid2CorrespondencesMap.remove(elementBTUID);
            this.tuid2CorrespondingTUIDsMap.remove(elementATUID);
            this.tuid2CorrespondingTUIDsMap.remove(elementBTUID);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getAllCorrespondingFeatureInstances(org.eclipse.emf.ecore.EObject,
     * org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final EObject parentEObject,
            final EStructuralFeature feature) {
        FeatureInstance featureInstance = FeatureInstance.getInstance(parentEObject, feature);
        return getAllCorrespondingFeatureInstances(featureInstance);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getAllCorrespondingFeatureInstances
     * (edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance)
     */
    @Override
    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.featureInstance2CorrespondingFIMap.get(featureInstance);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimCorrespondingFeatureInstances
     * (edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance)
     */
    @Override
    public Set<FeatureInstance> claimCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.featureInstance2CorrespondingFIMap.claimValueForKey(featureInstance);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * claimUniqueCorrespondingFeatureInstance
     * (edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance)
     */
    @Override
    public FeatureInstance claimUniqueCorrespondingFeatureInstance(final FeatureInstance featureInstance) {
        Set<FeatureInstance> featureInstances = claimCorrespondingFeatureInstances(featureInstance);
        if (featureInstances.size() != 1) {
            throw new RuntimeException("The feature instance corresponding to '" + featureInstance
                    + "' is not unique: " + featureInstances);
        }
        return featureInstances.iterator().next();
    }

    @Override
    public TUID calculateTUIDFromEObject(final EObject eObject) {
        Metamodel metamodel = null;
        if (this.mapping.getMetamodelA().hasMetaclassInstance(eObject)) {
            metamodel = this.mapping.getMetamodelA();
        }
        if (this.mapping.getMetamodelB().hasMetaclassInstance(eObject)) {
            metamodel = this.mapping.getMetamodelB();
        }
        if (metamodel == null) {
            logger.warn("EObject: '" + eObject
                    + "' is neither an instance of MM1 nor an instance of MM2. NsURI of object: "
                    + eObject.eClass().getEPackage().getNsURI());
            return null;
        } else {
            return TUID.getInstance(metamodel.calculateTUIDFromEObject(eObject));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(org
     * .eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void update(final EObject oldEObject, final EObject newEObject) {
        TUID oldTUID = calculateTUIDFromEObject(oldEObject);
        TUID newTUID = calculateTUIDFromEObject(newEObject);
        boolean sameTUID = oldTUID != null ? oldTUID.equals(newTUID) : newTUID == null;
        updateTUID2CorrespondencesMap(oldEObject, newEObject, oldTUID, newTUID, sameTUID);

        updateTUID2CorrespondingEObjectsMap(oldTUID, newTUID, sameTUID);

        updateFeatureInstances(oldEObject, newEObject, oldTUID);

        // update the TUID only at last because of the side-effect on all other TUIDs
        oldTUID.updateSingleSegment(newTUID);
    }

    private void updateFeatureInstances(final EObject oldEObject, final EObject newEObject, final TUID oldTUID) {
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
        if (correspondenceSetsWithFIofOldTUID != null) {
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
        }
        setChangeAfterLastSaveFlag();
    }

    private void updateTUID2CorrespondingEObjectsMap(final TUID oldTUID, final TUID newTUID, final boolean sameTUID) {
        if (!sameTUID) {
            Set<TUID> correspondingTUIDs = this.tuid2CorrespondingTUIDsMap.remove(oldTUID);
            if (null == correspondingTUIDs) {
                return;
            }
            this.tuid2CorrespondingTUIDsMap.put(newTUID, correspondingTUIDs);
        }
    }

    private void updateTUID2CorrespondencesMap(final EObject oldEObject, final EObject newEObject, final TUID oldTUID,
            final TUID newTUID, final boolean sameTUID) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(oldTUID);
        if (correspondences == null)
            return;
        for (Correspondence correspondence : correspondences) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence stc = (SameTypeCorrespondence) correspondence;
                if (oldTUID != null && oldTUID.equals(stc.getElementATUID())) {
                    if (!sameTUID) {
                        stc.setElementATUID(newTUID);
                    }
                    // update incoming links in tuid2CorrespondingEObjectsMap
                    TUID elementBTUID = stc.getElementBTUID();
                    updateCorrespondingLinksForUpdatedTUID(newTUID, oldTUID, elementBTUID);
                } else if (oldTUID != null && oldTUID.equals(stc.getElementBTUID())) {
                    if (!sameTUID) {
                        stc.setElementBTUID(newTUID);
                    }
                    // update incoming links in tuid2CorrespondingEObjectsMap
                    TUID elementATUID = stc.getElementATUID();
                    updateCorrespondingLinksForUpdatedTUID(newTUID, oldTUID, elementATUID);

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

    private void updateCorrespondingLinksForUpdatedTUID(final TUID newTUID, final TUID oldTUID, final TUID elementBTUID) {
        Set<TUID> correspondingTUIDs = this.tuid2CorrespondingTUIDsMap.get(elementBTUID);
        TUID objectWithOldTUID = null;
        for (TUID correspondingTUID : correspondingTUIDs) {
            if (correspondingTUID != null && correspondingTUID.equals(oldTUID)) {
                // mark for replacement
                objectWithOldTUID = correspondingTUID;
            }
        }
        if (objectWithOldTUID != null) {
            correspondingTUIDs.remove(objectWithOldTUID);
            correspondingTUIDs.add(newTUID);
        }
    }

    private void setCorrespondenceFeatures(final Correspondence correspondence, final Correspondence parent) {
        correspondence.setParent(parent);
    }

    private void setSameTypeCorrespondenceFeatures(final SameTypeCorrespondence correspondence, EObject a, EObject b,
            final Correspondence parent) {
        setCorrespondenceFeatures(correspondence, parent);
        if (this.mapping.getMetamodelA().hasMetaclassInstance(b)) {
            // swap
            EObject tmp = a;
            a = b;
            b = tmp;
        }
        TUID tuidA = calculateTUIDFromEObject(a);
        correspondence.setElementATUID(tuidA);
        TUID tuidB = calculateTUIDFromEObject(b);
        correspondence.setElementBTUID(tuidB);
    }

    @Override
    public EContainmentReferenceCorrespondence createAndAddEContainmentReferenceCorrespondence(final EObject a,
            final EObject b, final EReference referenceFeatureA, final EReference referenceFeatureB) {
        return createAndAddEContainmentReferenceCorrespondence(a, b, referenceFeatureA, referenceFeatureB, null);
    }

    @Override
    public EContainmentReferenceCorrespondence createAndAddEContainmentReferenceCorrespondence(final EObject a,
            final EObject b, final EReference referenceFeatureA, final EReference referenceFeatureB,
            final Correspondence parent) {
        EContainmentReferenceCorrespondence correspondence = CorrespondenceFactory.eINSTANCE
                .createEContainmentReferenceCorrespondence();
        setSameTypeCorrespondenceFeatures(correspondence, a, b, parent);
        correspondence.setFeatureA(referenceFeatureA);
        correspondence.setFeatureB(referenceFeatureB);
        addSameTypeCorrespondence(correspondence, parent);
        return correspondence;
    }

    @Override
    public EObjectCorrespondence createAndAddEObjectCorrespondence(final EObject a, final EObject b) {
        return createAndAddEObjectCorrespondence(a, b, null);
    }

    @Override
    public EObjectCorrespondence createAndAddEObjectCorrespondence(final EObject a, final EObject b,
            final Correspondence parent) {
        EObjectCorrespondence correspondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
        setSameTypeCorrespondenceFeatures(correspondence, a, b, parent);
        addSameTypeCorrespondence(correspondence);
        return correspondence;
    }
}
