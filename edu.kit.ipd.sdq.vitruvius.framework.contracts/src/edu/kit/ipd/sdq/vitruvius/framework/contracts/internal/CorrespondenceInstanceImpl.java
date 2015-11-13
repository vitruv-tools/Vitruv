// package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;
//
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// import org.apache.log4j.Logger;
// import org.eclipse.emf.common.util.EList;
// import org.eclipse.emf.ecore.EObject;
// import org.eclipse.emf.ecore.EReference;
// import org.eclipse.emf.ecore.EStructuralFeature;
// import org.eclipse.emf.ecore.resource.Resource;
// import org.eclipse.emf.ecore.util.EcoreUtil;
//
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance;
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
// import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
// import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
// import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
// import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
// import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
// import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
// import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
// import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
// import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple;
//
//// TODO move all methods that don't need direct instance variable access to some kind of util
// class
// public class CorrespondenceInstanceImpl extends ModelInstance implements
// CorrespondenceInstanceDecorator {
//
// private static final Logger logger =
// Logger.getLogger(CorrespondenceInstanceImpl.class.getSimpleName());
//
// private final Mapping mapping;
// private final ModelProviding modelProviding;
// private final Correspondences correspondences;
// protected final ClaimableMap<List<TUID>, Set<Correspondence>> tuid2CorrespondencesMap;
// private ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap;
//
// private boolean changedAfterLastSave = false;
//
// private final Map<String, String> saveCorrespondenceOptions;
//
// public CorrespondenceInstanceImpl(final Mapping mapping, final ModelProviding modelProviding,
// final VURI correspondencesVURI, final Resource correspondencesResource) {
// super(correspondencesVURI, correspondencesResource);
// this.mapping = mapping;
// this.modelProviding = modelProviding;
//
// this.tuid2CorrespondencesMap = new ClaimableHashMap<List<TUID>, Set<Correspondence>>();
// this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance,
// Set<FeatureInstance>>();
//
// this.saveCorrespondenceOptions = new HashMap<String, String>();
// this.saveCorrespondenceOptions.put(VitruviusConstants.getOptionProcessDanglingHref(),
// VitruviusConstants.getOptionProcessDanglingHrefDiscard());
//
// this.correspondences = loadAndRegisterCorrespondences(correspondencesResource);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * addSameTypeCorrespondence
// * (edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence)
// */
// @Override
// public void addCorrespondence(final Correspondence correspondence) {
// addCorrespondenceToModel(correspondence);
// registerCorrespondence(correspondence);
// setChangeAfterLastSaveFlag();
// }
//
// private void addCorrespondenceToModel(final Correspondence correspondence) {
// EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences();
// correspondenceListForAddition.add(correspondence);
// }
//
// @Override
// public List<TUID> calculateTUIDsFromEObjects(final List<EObject> eObjects) {
// Metamodel metamodel = null;
// if (this.mapping.getMetamodelA().hasMetaclassInstances(eObjects)) {
// metamodel = this.mapping.getMetamodelA();
// }
// if (this.mapping.getMetamodelB().hasMetaclassInstances(eObjects)) {
// metamodel = this.mapping.getMetamodelB();
// }
// if (metamodel == null) {
// logger.warn("EObject: '" + eObjects + "' is neither an instance of MM1 nor an instance of MM2.
// ");
// return null;
// } else {
// return TUID.getInstance(metamodel.calculateTUIDsFromEObjects(eObjects));
// }
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * changedAfterLastSave()
// */
// @Override
// public boolean changedAfterLastSave() {
// return this.changedAfterLastSave;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimCorrespondences(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public Set<Correspondence> claimCorrespondences(final EObject eObject) {
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimCorrespondingEObjects(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public Set<EObject> claimCorrespondingEObjects(final EObject eObject) {
// // TUID tuid = calculateTUIDsFromEObjects(eObject);
// // Set<TUID> correspondingTUIDs = getCorrespondingTUIDs(tuid);
// // claimCorrespondenceSetNotEmpty(eObject, tuid, correspondingTUIDs);
// // return resolveEObjectsFromTUIDs(correspondingTUIDs);
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimCorrespondingEObjectsByType(org.eclipse.emf.ecore.EObject, java.lang.Class)
// */
// @Override
// public <T> Set<T> claimCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
// // Set<EObject> correspondingEObjects = claimCorrespondingEObjects(eObject);
// // Set<T> correspondingEObjectsByType = new HashSet<T>();
// // for (EObject correspondingEObject : correspondingEObjects) {
// // if (type.isInstance(correspondingEObject)) {
// // correspondingEObjectsByType.add(type.cast(correspondingEObject));
// // }
// // }
// // if (correspondingEObjectsByType.size() == 0) {
// // throw new RuntimeException(
// // "There are no eObjects of type '" + type + "' that correspond to the eObject '" + eObject
// // + "!");
// // }
// // return correspondingEObjectsByType;
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimUniqueCorrespondence(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public Correspondence claimUniqueCorrespondence(final EObject eObject) {
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimUniqueCorrespondingEObject(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public EObject claimUniqueCorrespondingEObject(final EObject eObject) {
// // Set<EObject> correspondingEObjects = claimCorrespondingEObjects(eObject);
// // if (correspondingEObjects.size() != 1) {
// // throw new RuntimeException(
// // "The eObjects corresponding to '" + eObject + "' are not unique: " +
// // correspondingEObjects);
// // }
// // return correspondingEObjects.iterator().next();
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimUniqueCorrespondingEObjectByType(org.eclipse.emf.ecore.EObject, java.lang.Class)
// */
// @Override
// public <T> T claimUniqueCorrespondingEObjectByType(final EObject eObject, final Class<T> type) {
// // Set<T> correspondingEObjectsByType = claimCorrespondingEObjectsByType(eObject, type);
// // if (1 != correspondingEObjectsByType.size()) {
// // throw new RuntimeException(
// // "claimCorrespondingEObjectForTypeIfUnique failed: " + correspondingEObjectsByType.size()
// // + " corresponding objects found (expected 1)" + correspondingEObjectsByType);
// // }
// // return correspondingEObjectsByType.iterator().next();
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * claimUniqueOrNullCorrespondenceForEObject(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public Correspondence claimUniqueOrNullCorrespondenceForEObject(final EObject eObject) {
// return null;
// }
//
// @Override
// public Correspondence claimUniqueSameTypeCorrespondence(final EObject a, final EObject b) {
// // Set<Correspondence> correspondences = claimCorrespondences(a);
// // for (Correspondence correspondence : correspondences) {
// // TUID correspondingBTUID = correspondence.getElementBTUID();
// // TUID bTUID = calculateTUIDsFromEObjects(b);
// // if (correspondingBTUID.equals(bTUID)) {
// // return correspondence;
// // }
// // }
// // throw new RuntimeException("No eObject corresponding to '" + a + "' has the TUID of '" +
// // b + "'!");
// return null;
// }
//
// @Override
// public Correspondence createAndAddCorrespondence(final EObject a, final EObject b) {
// return null;
// }
//
// @Override
// public Correspondence createAndAddCorrespondence(final List<EObject> as, final List<EObject> bs)
// {
// Correspondence correspondence = CorrespondenceFactory.eINSTANCE.createCorrespondence();
// setSameTypeCorrespondenceFeatures(correspondence, as, bs);
// addCorrespondence(correspondence);
// return correspondence;
// }
//
// @Override
// public Correspondence createAndAddEContainmentReferenceCorrespondence(final EObject a, final
// EObject b,
// final EReference referenceFeatureA, final EReference referenceFeatureB) {
// // TODO Auto-generated method stub
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getAllCorrespondences(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public Set<Correspondence> getAllCorrespondences(final EObject eObject) {
// List<TUID> tuids = calculateTUIDsFromEObjects(eObject);
// return getAllCorrespondences(tuids);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getAllCorrespondences(edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID)
// */
// @Override
// public Set<Correspondence> getAllCorrespondences(final TUID tuid) {
// Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuid);
// if (correspondences == null) {
// correspondences = new HashSet<Correspondence>();
// this.tuid2CorrespondencesMap.put(tuid, correspondences);
// }
// return correspondences;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getAllCorrespondingEObjects(org.eclipse.emf.ecore.EObject)
// */
// @Override
// public Set<EObject> getAllCorrespondingEObjects(final EObject eObject) {
// TUID tuid = calculateTUIDsFromEObjects(eObject);
// Set<TUID> correspondingTUIDs = getCorrespondingTUIDs(tuid);
// return resolveEObjectsFromTUIDs(correspondingTUIDs);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getAllCorrespondingFeatureInstances(org.eclipse.emf.ecore.EObject,
// * org.eclipse.emf.ecore.EStructuralFeature)
// */
// @Override
// public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final EObject parentEObject,
// final EStructuralFeature feature) {
// FeatureInstance featureInstance = FeatureInstance.getInstance(parentEObject, feature);
// return getAllCorrespondingFeatureInstances(featureInstance);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getAllCorrespondingFeatureInstances
// * (edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance)
// */
// @Override
// public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final FeatureInstance
// featureInstance) {
// Set<FeatureInstance> correspondingFeatureInstances = this.featureInstance2CorrespondingFIMap
// .get(featureInstance);
// if (correspondingFeatureInstances == null) {
// correspondingFeatureInstances = Collections.emptySet();
// }
// return correspondingFeatureInstances;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getAllEObjectsInCorrespondencesWithType(java.lang.Class)
// */
// @Override
// public <T> Set<T> getAllEObjectsOfTypeInCorrespondences(final Class<T> type) {
// Set<T> correspondencesWithType = new HashSet<T>();
// List<Correspondence> allCorrespondences = this.correspondences.getCorrespondences();
// for (Correspondence correspondence : allCorrespondences) {
// EObject element = resolveEObjectFromTUIDWithoutException(correspondence.getElementATUID());
// if (null != element && type.isInstance(element)) {
// @SuppressWarnings("unchecked")
// T t = (T) element;
// correspondencesWithType.add(t);
// } else {
// element = resolveEObjectFromTUIDWithoutException(correspondence.getElementBTUID());
// if (null != element && type.isInstance(element)) {
// @SuppressWarnings("unchecked")
// T t = (T) element;
// correspondencesWithType.add(t);
// }
// }
// // currently nothing else to do as every correspondence is a One2OneCorrespondence
// }
// return correspondencesWithType;
// }
//
// @Override
// public Set<Correspondence> getCorrespondences(final List<EObject> eObjects) {
// // FIXME MK Auto-generated method stub
// return null;
// }
//
// @Override
// public Set<Correspondence> getCorrespondencesForTUIDs(final List<TUID> involvedTUIDs) {
// // FIXME MK Auto-generated method stub
// return null;
// }
//
// @Override
// public Set<List<EObject>> getCorrespondingEObjects(final List<EObject> eObjects) {
// // FIXME MK Auto-generated method stub
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * getCorrespondingEObjectsByType(org.eclipse.emf.ecore.EObject, java.lang.Class)
// */
// @Override
// public <T> Set<T> getCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
// try {
// return claimCorrespondingEObjectsByType(eObject, type);
// } catch (RuntimeException e) {
// return null;
// }
// }
//
// private Set<TUID> getCorrespondingTUIDs(final TUID tuid) {
// Set<Correspondence> allCorrespondences = getAllCorrespondences(tuid);
// Set<TUID> correspondingTUIDs = new HashSet<TUID>(allCorrespondences.size());
// for (Correspondence correspondence : allCorrespondences) {
// TUID atuid = correspondence.getElementATUID();
// TUID btuid = correspondence.getElementBTUID();
// if (atuid == null || btuid == null) {
// throw new IllegalStateException("The correspondence '" + correspondence + "' links to a null TUID
// '"
// + atuid + "' or '" + btuid + "'!");
// }
// if (atuid.equals(tuid)) {
// correspondingTUIDs.add(btuid);
// } else {
// correspondingTUIDs.add(atuid);
// }
// }
// return correspondingTUIDs;
// }
//
// @Override
// public Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
// try {
// EcoreResourceBridge.saveResource(getResource(), this.saveCorrespondenceOptions);
// } catch (IOException e) {
// throw new RuntimeException("Could not save correspondence instance '" + this + "' using the
// resource '"
// + getResource() + "' and the options '" + this.saveCorrespondenceOptions + "': " + e);
// }
// // we do not need to save anything else in a correspondence instance because the
// // involved mapping is fix and everything else can be recomputed from the model
// return new HashMap<String, Object>();
// // do _not_ return an immutable empty map as decorators will add entries
// }
//
// @Override
// public Set<String> getFileExtPrefixesForObjectsToLoad() {
// return new HashSet<String>();
// // do _not_ return an immutable empty map as decorators will add entries
// }
//
// @Override
// public <T extends CorrespondenceInstanceDecorator> T
// getFirstCorrespondenceInstanceDecoratorOfTypeInChain(
// final Class<T> type) {
// // TODO Auto-generated method stub
// return null;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see
// * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#getMapping()
// */
// @Override
// public Mapping getMapping() {
// return this.mapping;
// }
//
// private Metamodel getMetamodelHavingTUID(final String tuidString) {
// Metamodel metamodel = null;
// Metamodel metamodelA = this.mapping.getMetamodelA();
// if (metamodelA.hasTUID(tuidString)) {
// metamodel = metamodelA;
// }
// Metamodel metamodelB = this.mapping.getMetamodelB();
// if (metamodelB.hasTUID(tuidString)) {
// metamodel = metamodelB;
// }
// if (metamodel == null) {
// throw new IllegalArgumentException(
// "The TUID '" + tuidString + "' is neither valid for " + metamodelA + " nor " + metamodelB);
// }
// return metamodel;
// }
//
// @Override
// public boolean hasCorrespondences() {
// for (Set<Correspondence> correspondences : this.tuid2CorrespondencesMap.values()) {
// if (!correspondences.isEmpty()) {
// return true;
// }
// }
// ;
// return false;
// }
//
// @Override
// public boolean hasCorrespondences(final List<EObject> eObjects) {
// // FIXME AAA KEEP ON WORKING HERE
// List<TUID> tuids = calculateTUIDsFromEObjects(eObjects);
// Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids);
// return correspondences != null && correspondences.size() > 0;
// }
//
// @Override
// public void initialize(final Map<String, Object> fileExtPrefix2ObjectMap) {
// // nothing to initialize, everything was done based on the correspondence model
// }
//
// private Correspondences loadAndRegisterCorrespondences(final Resource correspondencesResource) {
// try {
// correspondencesResource.load(this.saveCorrespondenceOptions);
// } catch (Exception e) {
// logger.trace("Could not load correspondence resource - creating new correspondence instance
// resource.");
// }
// // TODO implement lazy loading for correspondences because they may get really big
// EObject correspondences = EcoreResourceBridge.getResourceContentRootIfUnique(getResource());
// if (correspondences == null) {
// correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
// correspondencesResource.getContents().add(correspondences);
// } else if (correspondences instanceof Correspondences) {
// registerLoadedCorrespondences((Correspondences) correspondences);
// } else {
// throw new RuntimeException("The unique root object '" + correspondences + "' of the
// correspondence model '"
// + getURI() + "' is not correctly typed!");
// }
// return (Correspondences) correspondences;
// }
//
// private void markNeighborAndChildrenCorrespondences(final Correspondence correspondence,
// final Set<Correspondence> markedCorrespondences) {
// if (markedCorrespondences.add(correspondence)) {
// TUID elementATUID = correspondence.getElementATUID();
// markNeighborAndChildrenCorrespondences(elementATUID, markedCorrespondences);
// TUID elementBTUID = correspondence.getElementBTUID();
// markNeighborAndChildrenCorrespondences(elementBTUID, markedCorrespondences);
// }
// }
//
// private void markNeighborAndChildrenCorrespondences(final TUID tuid,
// final Set<Correspondence> markedCorrespondences) {
// EObject eObject = resolveEObjectFromTUID(tuid);
// markNeighborAndChildrenCorrespondencesRecursively(eObject, markedCorrespondences);
// }
//
// private void markNeighborAndChildrenCorrespondencesRecursively(final EObject eObject,
// final Set<Correspondence> markedCorrespondences) {
// Set<Correspondence> allCorrespondences = getAllCorrespondences(eObject);
// for (Correspondence correspondence : allCorrespondences) {
// markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences);
// }
// List<EObject> children = eObject.eContents();
// for (EObject child : children) {
// markNeighborAndChildrenCorrespondencesRecursively(child, markedCorrespondences);
// }
// }
//
// private void registerCorrespondence(final Correspondence correspondence) {
// List<TUID> allInvolvedTUIDs = new ArrayList<TUID>(correspondence.getATUIDs());
// allInvolvedTUIDs.addAll(correspondence.getBTUIDs());
// // add all involved eObjects to the sets for these objects in the map
// for (TUID involvedTUID : allInvolvedTUIDs) {
// Set<Correspondence> correspondences = getAllCorrespondences(involvedTUID);
// if (!correspondences.contains(correspondence)) {
// correspondences.add(correspondence);
// }
// }
// }
//
// private void registerLoadedCorrespondences(final Correspondences correspondences) {
// for (Correspondence correspondence : correspondences.getCorrespondences()) {
// registerCorrespondence(correspondence);
// }
// }
//
// private void removeCorrespondenceFromMaps(final Correspondence markedCorrespondence) {
// TUID elementATUID = markedCorrespondence.getElementATUID();
// TUID elementBTUID = markedCorrespondence.getElementBTUID();
// this.tuid2CorrespondencesMap.remove(elementATUID);
// this.tuid2CorrespondencesMap.remove(elementBTUID);
// }
//
// @Override
// public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(
// final Correspondence correspondence) {
// if (correspondence == null) {
// return Collections.emptySet();
// }
// Set<Correspondence> markedCorrespondences = new HashSet<Correspondence>();
// markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences);
// for (Correspondence markedCorrespondence : markedCorrespondences) {
// removeCorrespondenceFromMaps(markedCorrespondence);
// EcoreUtil.remove(markedCorrespondence);
// setChangeAfterLastSaveFlag();
// }
// return markedCorrespondences;
// }
//
// @Override
// public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(final EObject
// eObject) {
// TUID tuid = calculateTUIDsFromEObjects(eObject);
// return removeCorrespondencesOfEObjectAndChildrenOnBothSides(tuid);
// }
//
// @Override
// public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(final TUID tuid)
// {
// Set<Correspondence> directCorrespondences = getAllCorrespondences(tuid);
// Set<Correspondence> directAndChildrenCorrespondences = new HashSet<Correspondence>();
// for (Correspondence correspondence : directCorrespondences) {
// directAndChildrenCorrespondences
// .addAll(removeCorrespondencesOfEObjectAndChildrenOnBothSides(correspondence));
// }
// return directAndChildrenCorrespondences;
// }
//
// /*
// * (non-Javadoc)
// *
// * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#
// * resetChangedAfterLastSave()
// */
// @Override
// public void resetChangedAfterLastSave() {
// this.changedAfterLastSave = false;
// }
//
// @Override
// public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuidString) {
// return getMetamodelHavingTUID(tuidString).resolveEObjectFromRootAndFullTUID(root, tuidString);
// }
//
// @Override
// public EObject resolveEObjectFromTUID(final TUID tuid) {
// String tuidString = tuid.toString();
// Metamodel metamodel = getMetamodelHavingTUID(tuidString);
//
// VURI vuri = metamodel.getModelVURIContainingIdentifiedEObject(tuidString);
// EObject rootEObject = null;
// ModelInstance modelInstance = null;
// if (vuri != null) {
// modelInstance = this.modelProviding.getAndLoadModelInstanceOriginal(vuri);
// rootEObject = modelInstance.getFirstRootEObject();
// }
// EObject resolvedEobject = null;
// try {
// // if the tuid is cached because it has no resource the rootEObject is null
// resolvedEobject = metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
// } catch (IllegalArgumentException iae) {
// // do nothing - just try the solving again
// }
// if (null == resolvedEobject && modelInstance != null) {
// // reload the model and try to solve it again
// modelInstance.load(null, true);
// rootEObject = modelInstance.getUniqueRootEObject();
// resolvedEobject = metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
// if (null == resolvedEobject) {
// // if resolved EObject is still null throw an exception
// // TODO think about something more lightweight than throwing an exception
// throw new RuntimeException("Could not resolve TUID " + tuidString + " in eObject " +
// rootEObject);
// }
// }
// if (null != resolvedEobject && resolvedEobject.eIsProxy()) {
// EcoreUtil.resolve(resolvedEobject, getResource());
// }
// return resolvedEobject;
//
// }
//
// private EObject resolveEObjectFromTUIDWithoutException(final TUID tuid) {
// try {
// return resolveEObjectFromTUID(tuid);
// } catch (Throwable t) {
// // ignore exception
// }
// return null;
// }
//
// @Override
// public Set<EObject> resolveEObjectsFromTUIDs(final Set<TUID> tuids) {
// Set<EObject> eObjects = new HashSet<EObject>(tuids.size());
// for (TUID tuid : tuids) {
// EObject resolvedEObject = resolveEObjectFromTUID(tuid);
// boolean alreadyContained = !eObjects.add(resolvedEObject);
// if (alreadyContained) {
// throw new RuntimeException("The eObject '" + resolvedEObject + "' was resolved for the TUID '" +
// tuid
// + "' and for another TUID in the set '" + tuids + "'!");
// }
// }
// return eObjects;
// }
//
// private void setChangeAfterLastSaveFlag() {
// this.changedAfterLastSave = true;
// }
//
// private void setSameTypeCorrespondenceFeatures(final Correspondence correspondence, List<EObject>
// as,
// List<EObject> bs) {
// // nothing to set for the Correspondence metaclass
// // set sametype-specific features
// if (this.mapping.getMetamodelA().hasMetaclassInstances(bs)) {
// // swap
// List<EObject> tmp = as;
// as = bs;
// bs = tmp;
// }
// List<TUID> aTUIDs = calculateTUIDsFromEObjects(as);
// correspondence.getATUIDs().addAll(aTUIDs);
// List<TUID> bTUIDs = calculateTUIDsFromEObjects(bs);
// correspondence.getBTUIDs().addAll(bTUIDs);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see
// * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(org
// * .eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
// */
// @Override
// public void updateTUID(final EObject oldEObject, final EObject newEObject) {
// TUID oldTUID = calculateTUIDsFromEObjects(oldEObject);
// this.updateTUID(oldTUID, newEObject);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see
// * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(edu
// * .kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID,
// * org.eclipse.emf.ecore.EObject)
// */
// @Override
// public void updateTUID(final TUID oldTUID, final EObject newEObject) {
// TUID newTUID = calculateTUIDsFromEObjects(newEObject);
// updateTUID(oldTUID, newTUID);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see
// * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(edu
// * .kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID,
// * edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID)
// */
// @Override
// public void updateTUID(final TUID oldTUID, final TUID newTUID) {
// boolean sameTUID = oldTUID != null ? oldTUID.equals(newTUID) : newTUID == null;
// if (sameTUID || oldTUID == null) {
// return;
// }
// String oldTUIDString = oldTUID.toString();
// TUID.BeforeHashCodeUpdateLambda before = new TUID.BeforeHashCodeUpdateLambda() {
//
// /**
// * Removes the current entries in the
// * {@link CorrespondenceInstanceImpl#tuid2CorrespondencesMap} map for the given oldTUID
// * before the hash code of it is updated and returns a pair containing the oldTUID and
// * the removed correspondence model elements of the map.
// *
// * @param tuidAndNewSegmentPairs
// * @return
// */
// @Override
// public Triple<TUID, String, Set<Correspondence>> performPreAction(final TUID oldCurrentTUID) {
// // The TUID is used as key in this map. Therefore the entry has to be removed before
// // the hashCode of the TUID changes.
// // remove the old map entries for the tuid before its hashcode changes
// Set<Correspondence> correspondencesForOldTUID =
// CorrespondenceInstanceImpl.this.tuid2CorrespondencesMap
// .remove(oldCurrentTUID);
// // because featureInstance2CorrespondingFIMap uses no TUID as key we do not need to
// // update it
// return new Triple<TUID, String, Set<Correspondence>>(oldCurrentTUID, oldCurrentTUID.toString(),
// correspondencesForOldTUID);
// }
//
// };
//
// TUID.AfterHashCodeUpdateLambda after = new TUID.AfterHashCodeUpdateLambda() {
//
// /**
// * Re-adds all map entries after the hash code of tuids was updated.
// *
// * @param removedMapEntries
// */
// @Override
// public void performPostAction(final Triple<TUID, String, Set<Correspondence>> removedMapEntry) {
// TUID oldCurrentTUID = removedMapEntry.getFirst();
// String oldCurrentTUIDString = removedMapEntry.getSecond();
// Set<Correspondence> correspondencesForOldSegment = removedMapEntry.getThird();
// // re-add the entries using the tuid with the new hashcode
// if (correspondencesForOldSegment != null) {
// for (Correspondence correspondence : correspondencesForOldSegment) {
// if (oldCurrentTUIDString != null
// && oldCurrentTUIDString.equals(correspondence.getElementATUID().toString())) {
// correspondence.setElementATUID(oldCurrentTUID);
// } else if (oldCurrentTUIDString != null
// && oldCurrentTUIDString.equals(correspondence.getElementBTUID().toString())) {
// correspondence.setElementBTUID(oldCurrentTUID);
// } else if (oldCurrentTUID == null || (!oldCurrentTUID.equals(correspondence.getElementATUID())
// && !oldCurrentTUID.equals(correspondence.getElementBTUID()))) {
// throw new RuntimeException("None of the corresponding elements in '" + correspondence
// + "' has the TUID '" + oldCurrentTUID + "'!");
// }
// }
// CorrespondenceInstanceImpl.this.tuid2CorrespondencesMap.put(oldCurrentTUID,
// correspondencesForOldSegment);
// }
// }
// };
//
// oldTUID.renameSegments(newTUID, before, after);
//
// Metamodel metamodel = getMetamodelHavingTUID(oldTUIDString);
// metamodel.removeIfRootAndCached(oldTUIDString);
// }
// }
