package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

// TODO move all methods that don't need direct instance variable access to some kind of util class
public class CorrespondenceInstanceImpl extends ModelInstance implements CorrespondenceInstanceDecorator {

    private static final Logger logger = Logger.getLogger(CorrespondenceInstanceImpl.class.getSimpleName());

    private final Mapping mapping;
    private final ModelProviding modelProviding;
    private final Correspondences correspondences;
    protected final ClaimableMap<TUID, Set<Correspondence>> tuid2CorrespondencesMap;
    private ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap;

    private boolean changedAfterLastSave = false;

    private final Map<String, String> saveCorrespondenceOptions;

    public CorrespondenceInstanceImpl(final Mapping mapping, final ModelProviding modelProviding,
            final VURI correspondencesVURI, final Resource correspondencesResource) {
        super(correspondencesVURI, correspondencesResource);
        this.mapping = mapping;
        this.modelProviding = modelProviding;

        this.tuid2CorrespondencesMap = new ClaimableHashMap<TUID, Set<Correspondence>>();
        this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance, Set<FeatureInstance>>();

        this.saveCorrespondenceOptions = new HashMap<String, String>();
        this.saveCorrespondenceOptions.put(VitruviusConstants.getOptionProcessDanglingHref(),
                VitruviusConstants.getOptionProcessDanglingHrefDiscard());

        this.correspondences = loadAndRegisterCorrespondences(correspondencesResource);
    }

    private Correspondences loadAndRegisterCorrespondences(final Resource correspondencesResource) {
        try {
            correspondencesResource.load(this.saveCorrespondenceOptions);
        } catch (Exception e) {
            logger.trace("Could not load correspondence resource - creating new correspondence instance resource.");
        }
        // TODO implement lazy loading for correspondences because they may get really big
        EObject correspondences = EcoreResourceBridge.getResourceContentRootIfUnique(getResource());
        if (correspondences == null) {
            correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
            correspondencesResource.getContents().add(correspondences);
        } else if (correspondences instanceof Correspondences) {
            registerLoadedCorrespondences((Correspondences) correspondences);
        } else {
            throw new RuntimeException("The unique root object '" + correspondences + "' of the correspondence model '"
                    + getURI() + "' is not correctly typed!");
        }
        return (Correspondences) correspondences;
    }

    private void registerLoadedCorrespondences(final Correspondences correspondences) {
        for (Correspondence correspondence : correspondences.getCorrespondences()) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence sameTypeCorrespondence = (SameTypeCorrespondence) correspondence;
                registerSameTypeCorrespondence(sameTypeCorrespondence);
            }
        }
    }

    @Override
    public Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
        try {
            EcoreResourceBridge.saveResource(getResource(), this.saveCorrespondenceOptions);
        } catch (IOException e) {
            throw new RuntimeException("Could not save correspondence instance '" + this + "' using the resource '"
                    + getResource() + "' and the options '" + this.saveCorrespondenceOptions + "': " + e);
        }
        // we do not need to save anything else in a correspondence instance because the
        // involved mapping is fix and everything else can be recomputed from the model
        return new HashMap<String, Object>();
        // do _not_ return an immutable empty map as decorators will add entries
    }

    @Override
    public Set<String> getFileExtPrefixesForObjectsToLoad() {
        return new HashSet<String>();
        // do _not_ return an immutable empty map as decorators will add entries
    }

    @Override
    public void initialize(final Map<String, Object> fileExtPrefix2ObjectMap) {
        // nothing to initialize, everything was done based on the correspondence model
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

    @Override
    public boolean hasCorrespondences() {
        for (Set<Correspondence> correspondences : this.tuid2CorrespondencesMap.values()) {
            if (!correspondences.isEmpty()) {
                return true;
            }
        }
        ;
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * hasCorrespondences (org.eclipse.emf.ecore.EObject)
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
        claimCorrespondenceSetNotEmpty(eObject, tuid, correspondences);
        return correspondences;
    }

    private void claimCorrespondenceSetNotEmpty(final EObject eObject, final TUID tuid,
            final Collection<?> correspondences) {
        if (correspondences.size() == 0) {
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
        return getAllCorrespondences(tuid);
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
     * getAllCorrespondences(edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID)
     */
    @Override
    public Set<Correspondence> getAllCorrespondences(final TUID tuid) {
        Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuid);
        if (correspondences == null) {
            correspondences = new HashSet<Correspondence>();
            this.tuid2CorrespondencesMap.put(tuid, correspondences);
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
        Collection<TUID> correspondingTUIDs = getCorrespondingTUIDs(tuid);
        claimCorrespondenceSetNotEmpty(eObject, tuid, correspondingTUIDs);
        return resolveEObjectsFromTUIDs(correspondingTUIDs);
    }

    private Collection<TUID> getCorrespondingTUIDs(final TUID tuid) {
        Set<Correspondence> allCorrespondences = getAllCorrespondences(tuid);
        Collection<TUID> correspondingTUIDs = new ArrayList<TUID>(allCorrespondences.size());
        for (Correspondence correspondence : allCorrespondences) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence stc = (SameTypeCorrespondence) correspondence;
                TUID atuid = stc.getElementATUID();
                TUID btuid = stc.getElementBTUID();
                if (atuid == null || btuid == null) {
                    throw new IllegalStateException("The correspondence '" + stc + "' links to a null TUID '" + atuid
                            + "' or '" + btuid + "'!");
                }
                if (atuid.equals(tuid)) {
                    correspondingTUIDs.add(btuid);
                } else {
                    correspondingTUIDs.add(atuid);
                }
            }
        }
        return correspondingTUIDs;
    }

    @Override
    public Set<EObject> resolveEObjectsFromTUIDs(final Collection<TUID> tuids) {
        Set<EObject> eObjects = new HashSet<EObject>(tuids.size());
        for (TUID tuid : tuids) {
            EObject resolvedEObject = resolveEObjectFromTUID(tuid);
            boolean alreadyContained = !eObjects.add(resolvedEObject);
            if (alreadyContained) {
                throw new RuntimeException("The eObject '" + resolvedEObject + "' was resolved for the TUID '" + tuid
                        + "' and for another TUID in the set '" + tuids + "'!");
            }
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
        Collection<TUID> correspondingTUIDs = getCorrespondingTUIDs(tuid);
        return resolveEObjectsFromTUIDs(correspondingTUIDs);
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
            throw new RuntimeException(
                    "The eObjects corresponding to '" + eObject + "' are not unique: " + correspondingEObjects);
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
            throw new RuntimeException(
                    "There are no eObjects of type '" + type + "' that correspond to the eObject '" + eObject + "!");
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
            throw new RuntimeException(
                    "claimCorrespondingEObjectForTypeIfUnique failed: " + correspondingEObjectsByType.size()
                            + " corresponding objects found (expected 1)" + correspondingEObjectsByType);
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
            throw new RuntimeException(
                    "The correspondence for eObject '" + eObject + "' is not unique: " + objectCorrespondences);
        }
        return objectCorrespondences.iterator().next();
    }

    @Override
    public SameTypeCorrespondence claimUniqueSameTypeCorrespondence(final EObject a, final EObject b) {
        Set<Correspondence> correspondences = claimCorrespondences(a);
        for (Correspondence correspondence : correspondences) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence sameTypeCorrespondence = (SameTypeCorrespondence) correspondence;
                TUID correspondingBTUID = sameTypeCorrespondence.getElementBTUID();
                TUID bTUID = calculateTUIDFromEObject(b);
                if (correspondingBTUID.equals(bTUID)) {
                    return sameTypeCorrespondence;
                }
            }
        }
        throw new RuntimeException("No eObject corresponding to '" + a + "' has the TUID of '" + b + "'!");
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
        List<Correspondence> allCorrespondences = this.correspondences.getCorrespondences();
        for (Correspondence correspondence : allCorrespondences) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence sameTypeCorrespondence = (SameTypeCorrespondence) correspondence;
                EObject element = resolveEObjectFromTUIDWithoutException(sameTypeCorrespondence.getElementATUID());
                if (null != element && type.isInstance(element)) {
                    @SuppressWarnings("unchecked")
                    T t = (T) element;
                    correspondencesWithType.add(t);
                } else {
                    element = resolveEObjectFromTUIDWithoutException(sameTypeCorrespondence.getElementBTUID());
                    if (null != element && type.isInstance(element)) {
                        @SuppressWarnings("unchecked")
                        T t = (T) element;
                        correspondencesWithType.add(t);
                    }
                }
            }
            // currently nothing else to do as every correspondence is a SameTypeCorrespondence
        }
        return correspondencesWithType;
    }

    private EObject resolveEObjectFromTUIDWithoutException(final TUID tuid) {
        try {
            return resolveEObjectFromTUID(tuid);
        } catch (Throwable t) {
            // ignore exception
        }
        return null;
    }

    @Override
    public EObject resolveEObjectFromTUID(final TUID tuid) {
        String tuidString = tuid.toString();
        Metamodel metamodel = getMetamodelHavingTUID(tuidString);

        VURI vuri = metamodel.getModelVURIContainingIdentifiedEObject(tuidString);
        EObject rootEObject = null;
        ModelInstance modelInstance = null;
        if (vuri != null) {
            modelInstance = this.modelProviding.getAndLoadModelInstanceOriginal(vuri);
            rootEObject = modelInstance.getFirstRootEObject();
        }
        EObject resolvedEobject = null;
        try {
            // if the tuid is cached because it has no resource the rootEObject is null
            resolvedEobject = metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
        } catch (IllegalArgumentException iae) {
            // do nothing - just try the solving again
        }
        if (null == resolvedEobject && modelInstance != null) {
            // reload the model and try to solve it again
            modelInstance.load(null, true);
            rootEObject = modelInstance.getUniqueRootEObject();
            resolvedEobject = metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
            if (null == resolvedEobject) {
                // if resolved EObject is still null throw an exception
                // TODO think about something more lightweight than throwing an exception
                throw new RuntimeException("Could not resolve TUID " + tuidString + " in eObject " + rootEObject);
            }
        }
        if (null != resolvedEobject && resolvedEobject.eIsProxy()) {
            EcoreUtil.resolve(resolvedEobject, getResource());
        }
        return resolvedEobject;

    }

    private Metamodel getMetamodelHavingTUID(final String tuidString) {
        Metamodel metamodel = null;
        Metamodel metamodelA = this.mapping.getMetamodelA();
        if (metamodelA.hasTUID(tuidString)) {
            metamodel = metamodelA;
        }
        Metamodel metamodelB = this.mapping.getMetamodelB();
        if (metamodelB.hasTUID(tuidString)) {
            metamodel = metamodelB;
        }
        if (metamodel == null) {
            throw new IllegalArgumentException(
                    "The TUID '" + tuidString + "' is neither valid for " + metamodelA + " nor " + metamodelB);
        }
        return metamodel;
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
        addCorrespondenceToModel(correspondence);
        registerSameTypeCorrespondence(correspondence);
        setChangeAfterLastSaveFlag();
    }

    private void addCorrespondenceToModel(final Correspondence correspondence) {
        EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences();
        correspondenceListForAddition.add(correspondence);
    }

    private void registerSameTypeCorrespondence(final SameTypeCorrespondence correspondence) {
        List<TUID> allInvolvedTUIDs = Arrays.asList(correspondence.getElementATUID(), correspondence.getElementBTUID());
        // add all involved eObjects to the sets for these objects in the map
        for (TUID involvedTUID : allInvolvedTUIDs) {
            Set<Correspondence> correspondences = getAllCorrespondences(involvedTUID);
            if (!correspondences.contains(correspondence)) {
                correspondences.add(correspondence);
            }
        }
        if (correspondence instanceof EFeatureCorrespondence) {
            EFeatureCorrespondence<?> featureCorrespondence = (EFeatureCorrespondence<?>) correspondence;
            FeatureInstance featureInstanceA = FeatureInstance.getInstance(
                    resolveEObjectFromTUID(featureCorrespondence.getElementATUID()),
                    featureCorrespondence.getFeatureA());
            FeatureInstance featureInstanceB = FeatureInstance.getInstance(
                    resolveEObjectFromTUID(featureCorrespondence.getElementBTUID()),
                    featureCorrespondence.getFeatureB());
            Set<FeatureInstance> featureInstancesCorrespondingToFIA = this.featureInstance2CorrespondingFIMap
                    .get(featureInstanceA);
            if (featureInstancesCorrespondingToFIA == null) {
                featureInstancesCorrespondingToFIA = new HashSet<FeatureInstance>();
                this.featureInstance2CorrespondingFIMap.put(featureInstanceA, featureInstancesCorrespondingToFIA);
            }
            featureInstancesCorrespondingToFIA.add(featureInstanceB);

            Set<FeatureInstance> featureInstancesCorrespondingToFIB = this.featureInstance2CorrespondingFIMap
                    .get(featureInstanceB);
            if (featureInstancesCorrespondingToFIB == null) {
                featureInstancesCorrespondingToFIB = new HashSet<FeatureInstance>();
                this.featureInstance2CorrespondingFIMap.put(featureInstanceB, featureInstancesCorrespondingToFIB);
            }
            featureInstancesCorrespondingToFIB.add(featureInstanceA);
        }
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

    @Override
    public Set<Correspondence> removeDirectAndChildrenCorrespondencesOnBothSides(final EObject eObject) {
        TUID tuid = calculateTUIDFromEObject(eObject);
        return removeDirectAndChildrenCorrespondencesOnBothSides(tuid);
    }

    @Override
    public Set<Correspondence> removeDirectAndChildrenCorrespondencesOnBothSides(final TUID tuid) {
        Set<Correspondence> directCorrespondences = getAllCorrespondences(tuid);
        Set<Correspondence> directAndChildrenCorrespondences = new HashSet<Correspondence>();
        for (Correspondence correspondence : directCorrespondences) {
            directAndChildrenCorrespondences
                    .addAll(removeNeighborAndChildrenCorrespondencesOnBothSides(correspondence));
        }
        return directAndChildrenCorrespondences;
    }

    @Override
    public Set<Correspondence> removeNeighborAndChildrenCorrespondencesOnBothSides(
            final Correspondence correspondence) {
        if (correspondence == null) {
            return Collections.emptySet();
        }
        Set<Correspondence> markedCorrespondences = new HashSet<Correspondence>();
        markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences);
        for (Correspondence markedCorrespondence : markedCorrespondences) {
            removeCorrespondenceFromMaps(markedCorrespondence);
            EcoreUtil.remove(markedCorrespondence);
            setChangeAfterLastSaveFlag();
        }
        return markedCorrespondences;
    }

    private void markNeighborAndChildrenCorrespondences(final TUID tuid,
            final Set<Correspondence> markedCorrespondences) {
        EObject eObject = resolveEObjectFromTUID(tuid);
        markNeighborAndChildrenCorrespondencesRecursively(eObject, markedCorrespondences);
    }

    private void markNeighborAndChildrenCorrespondencesRecursively(final EObject eObject,
            final Set<Correspondence> markedCorrespondences) {
        Set<Correspondence> allCorrespondences = getAllCorrespondences(eObject);
        for (Correspondence correspondence : allCorrespondences) {
            markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences);
        }
        List<EObject> children = eObject.eContents();
        for (EObject child : children) {
            markNeighborAndChildrenCorrespondencesRecursively(child, markedCorrespondences);
        }
    }

    private void markNeighborAndChildrenCorrespondences(final Correspondence correspondence,
            final Set<Correspondence> markedCorrespondences) {
        if (markedCorrespondences.add(correspondence)) {
            if (correspondence instanceof SameTypeCorrespondence) {
                SameTypeCorrespondence sameTypeCorrespondence = (SameTypeCorrespondence) correspondence;
                TUID elementATUID = sameTypeCorrespondence.getElementATUID();
                markNeighborAndChildrenCorrespondences(elementATUID, markedCorrespondences);
                TUID elementBTUID = sameTypeCorrespondence.getElementBTUID();
                markNeighborAndChildrenCorrespondences(elementBTUID, markedCorrespondences);
            }
        }
    }

    private void removeCorrespondenceFromMaps(final Correspondence markedCorrespondence) {
        if (markedCorrespondence instanceof SameTypeCorrespondence) {
            SameTypeCorrespondence sameTypeCorrespondence = (SameTypeCorrespondence) markedCorrespondence;
            TUID elementATUID = sameTypeCorrespondence.getElementATUID();
            TUID elementBTUID = sameTypeCorrespondence.getElementBTUID();
            this.tuid2CorrespondencesMap.remove(elementATUID);
            this.tuid2CorrespondencesMap.remove(elementBTUID);
        }
        if (markedCorrespondence instanceof EFeatureCorrespondence) {
            EFeatureCorrespondence<?> featureCorrespondence = (EFeatureCorrespondence<?>) markedCorrespondence;
            FeatureInstance featureInstanceA = FeatureInstance.getInstance(
                    resolveEObjectFromTUID(featureCorrespondence.getElementATUID()),
                    featureCorrespondence.getFeatureA());
            FeatureInstance featureInstanceB = FeatureInstance.getInstance(
                    resolveEObjectFromTUID(featureCorrespondence.getElementBTUID()),
                    featureCorrespondence.getFeatureB());
            this.featureInstance2CorrespondingFIMap.remove(featureInstanceA);
            this.featureInstance2CorrespondingFIMap.remove(featureInstanceB);
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
        Set<FeatureInstance> correspondingFeatureInstances = this.featureInstance2CorrespondingFIMap
                .get(featureInstance);
        if (correspondingFeatureInstances == null) {
            correspondingFeatureInstances = Collections.emptySet();
        }
        return correspondingFeatureInstances;
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
            throw new RuntimeException("The feature instance corresponding to '" + featureInstance + "' is not unique: "
                    + featureInstances);
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
            logger.warn("EObject: '" + eObject + "' is neither an instance of MM1 nor an instance of MM2. ");
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
        this.update(oldTUID, newEObject);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(edu
     * .kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID,
     * org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void update(final TUID oldTUID, final EObject newEObject) {
        TUID newTUID = calculateTUIDFromEObject(newEObject);
        update(oldTUID, newTUID);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(edu
     * .kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID,
     * edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID)
     */
    @Override
    public void update(final TUID oldTUID, final TUID newTUID) {
        boolean sameTUID = oldTUID != null ? oldTUID.equals(newTUID) : newTUID == null;
        if (sameTUID || oldTUID == null) {
            return;
        }
        String oldTUIDString = oldTUID.toString();
        TUID.BeforeHashCodeUpdateLambda before = new TUID.BeforeHashCodeUpdateLambda() {

            /**
             * Removes the current entries in the
             * {@link CorrespondenceInstanceImpl#tuid2CorrespondencesMap} map for the given oldTUID
             * before the hash code of it is updated and returns a pair containing the oldTUID and
             * the removed correspondence model elements of the map.
             *
             * @param tuidAndNewSegmentPairs
             * @return
             */
            @Override
            public Pair<TUID, Set<Correspondence>> performPreAction(final TUID oldTUID) {
                // The TUID is used as key in this map. Therefore the entry has to be removed before
                // the hashCode of the TUID changes.
                // remove the old map entries for the tuid before its hashcode changes
                Set<Correspondence> correspondencesForOldTUID = CorrespondenceInstanceImpl.this.tuid2CorrespondencesMap
                        .remove(oldTUID);
                // because featureInstance2CorrespondingFIMap uses no TUID as key we do not need to
                // update it
                return new Pair<TUID, Set<Correspondence>>(oldTUID, correspondencesForOldTUID);
            }

        };

        TUID.AfterHashCodeUpdateLambda after = new TUID.AfterHashCodeUpdateLambda() {

            /**
             * Re-adds all map entries after the hash code of tuids was updated.
             *
             * @param removedMapEntries
             */
            @Override
            public void performPostAction(final Pair<TUID, Set<Correspondence>> removedMapEntry) {
                TUID oldTUID = removedMapEntry.getFirst();
                Set<Correspondence> correspondencesForOldSegment = removedMapEntry.getSecond();
                // re-add the entries using the tuid with the new hashcode
                if (correspondencesForOldSegment != null) {
                    for (Correspondence correspondence : correspondencesForOldSegment) {
                        if (correspondence instanceof SameTypeCorrespondence) {
                            SameTypeCorrespondence stc = (SameTypeCorrespondence) correspondence;
                            if (oldTUID != null && oldTUID.equals(stc.getElementATUID())) {
                                stc.setElementATUID(newTUID);
                            } else if (oldTUID != null && oldTUID.equals(stc.getElementBTUID())) {
                                stc.setElementBTUID(newTUID);
                            } else {
                                throw new RuntimeException("None of the corresponding elements in '" + correspondence
                                        + "' has the TUID '" + oldTUID + "'!");
                            }
                        }
                    }
                    CorrespondenceInstanceImpl.this.tuid2CorrespondencesMap.put(newTUID, correspondencesForOldSegment);
                }
            }
        };

        oldTUID.renameSegments(newTUID, before, after);

        Metamodel metamodel = getMetamodelHavingTUID(oldTUIDString);
        metamodel.removeIfRootAndCached(oldTUIDString);
    }

    private void setSameTypeCorrespondenceFeatures(final SameTypeCorrespondence correspondence, EObject a, EObject b) {
        // nothing to set for the Correspondence metaclass
        // set sametype-specific features
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
        EContainmentReferenceCorrespondence correspondence = CorrespondenceFactory.eINSTANCE
                .createEContainmentReferenceCorrespondence();
        setSameTypeCorrespondenceFeatures(correspondence, a, b);
        correspondence.setFeatureA(referenceFeatureA);
        correspondence.setFeatureB(referenceFeatureB);
        addSameTypeCorrespondence(correspondence);
        return correspondence;
    }

    @Override
    public EObjectCorrespondence createAndAddEObjectCorrespondence(final EObject a, final EObject b) {
        EObjectCorrespondence correspondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
        setSameTypeCorrespondenceFeatures(correspondence, a, b);
        addSameTypeCorrespondence(correspondence);
        return correspondence;
    }

    @Override
    public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuidString) {
        return getMetamodelHavingTUID(tuidString).resolveEObjectFromRootAndFullTUID(root, tuidString);
    }
}
