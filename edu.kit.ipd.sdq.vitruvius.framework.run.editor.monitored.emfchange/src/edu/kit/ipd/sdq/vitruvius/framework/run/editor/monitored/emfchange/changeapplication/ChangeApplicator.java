package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;

/**
 * {@link ChangeApplicator} applies a list of {@link Change} objects (relative to a model instance
 * A) to a model instance B.
 */
public class ChangeApplicator {
    private static final Logger LOGGER = Logger.getLogger(ChangeApplicator.class);

    private final Resource source;
    private final List<Change> changesInSource;

    /**
     * Constructs a new {@link ChangeApplicator} instance.
     * 
     * @param source
     *            The resource of the model instance to which <code>changesInSource</code> is
     *            relative. This model instance must be in a state where
     *            <code>changesInSource</code> contains the most recent changes.
     * @param changesInSource
     *            The list of changes to be applied, relative to the model instance represented by
     *            <code>source</code>. The changes must be ordered such that, if linearly applied,
     *            no affected object is referenced by any change such that the object does not exist
     *            yet after having applied the changes up to that point.
     */
    public ChangeApplicator(Resource source, List<Change> changesInSource) {
        this.source = source;
        this.changesInSource = changesInSource;
    }

    /**
     * Applies the changes supplied to {@link #ChangeApplicator(Resource, List)} to the given target
     * model instance.
     * 
     * @param target
     *            The resource of the model instance receiving the changes. All pre-existing objects
     *            referenced by the changes must be contained in this instance at structurally
     *            equivalent positions
     * 
     * 
     * @return
     */
    public List<Change> applyChanges(Resource target) {

        if (changesInSource.isEmpty()) {
            return new ArrayList<>();
        }

        ApplyingVisitor visitor = new ApplyingVisitor(target, changesInSource);
        ChangeRecorder cr = new ChangeRecorder(target);

        for (Change change : changesInSource) {
            if (change instanceof EMFModelChange) {
                EMFModelChange emfChange = (EMFModelChange) change;
                EChangeDispatcher.dispatch(emfChange.getEChange(), visitor);
            }
        }

        ChangeDescription2ChangeConverter converter = new ChangeDescription2ChangeConverter();
        ChangeDescription newCD = cr.endRecording();

        newCD.applyAndReverse();
        List<Change> result = converter.getChanges(newCD);
        newCD.applyAndReverse();

        return result;
    }

    public class ApplyingVisitor implements IEChangeVisitor {
        private final ModelTranslator translator;
        private final TransModelObjectCopier objectCopier;

        private final Set<Object> floatingAddedObjects = new HashSet<>();
        private final Map<Object, Object> floatingRemovedObjects = new HashMap<>();
        private final Map<Object, EObject> removedObjMap = new HashMap<>();
        private final Map<RemoveFromEList<?>, EObject> removalOp2RemovedObj = new HashMap<>();

        public ApplyingVisitor(Resource target, Collection<Change> sourceChanges) {
            this.translator = new ModelTranslator(source, target);
            this.objectCopier = new TransModelObjectCopier(translator);
            setupDeletedObjMap(target, sourceChanges);
        }

        private void setupDeletedObjMap(Resource target, Collection<Change> sourceChanges) {
            for (Change c : sourceChanges) {
                if (c instanceof EMFModelChange) {
                    EMFModelChange modelC = (EMFModelChange) c;
                    if (modelC.getEChange() instanceof RemoveFromEList<?>) {
                        RemoveFromEList<?> removeOp = (RemoveFromEList<?>) modelC.getEChange();
                        if (removeOp.getAffectedFeature() instanceof EReference
                                && ((EReference) removeOp.getAffectedFeature()).isContainment()) {
                            EObject removedObjectInTarget = target.getEObject(removeOp.getRemovedObjectURIFragment());
                            assert removedObjectInTarget != null;
                            removedObjMap.put(removeOp.getUpdate(), removedObjectInTarget);
                            LOGGER.trace("Matching in list remove op: source=" + removeOp.getUpdate() + " with target="
                                    + removedObjectInTarget);
                            removalOp2RemovedObj.put(removeOp, removedObjectInTarget);
                        }
                    }
                }
            }
        }

        public void setFeature(EObject object, String featureName, Object newValue) {
            EStructuralFeature affectedFeature = object.eClass().getEStructuralFeature(featureName);

            assert affectedFeature != null;
            assert newValue != null;
            object.eSet(affectedFeature, newValue);
        }

        private boolean isFeatureExemptFromExplicitUpdates(EStructuralFeature it) {
            return it.getName().equals("eContainingClass");
        }

        private void applyMultiplicityOneContainmentChange(UpdateEReference<?> change, EObject targetAffectedObj) {
            if (change.getNewValue() == null) {
                targetAffectedObj.eUnset(change.getAffectedFeature()); // TODO: is this correct?
            } else {
                EObject newValue = (EObject) change.getNewValue();
                if (floatingAddedObjects.contains(newValue)) {
                    objectCopier.addCopyInTarget(newValue);
                    floatingAddedObjects.remove(newValue);
                } else {
                    Object targetInsertObj;
                    if (floatingRemovedObjects.containsKey(newValue)) {
                        targetInsertObj = floatingRemovedObjects.get(newValue);
                    } else {
                        targetInsertObj = removedObjMap.get(newValue);
                    }

                    assert targetInsertObj != null;

                    setFeature(targetAffectedObj, change.getAffectedFeature().getName(), targetInsertObj);
                }
            }
        }

        @Override
        public void visit(UpdateEReference<?> it) {
            if (isFeatureExemptFromExplicitUpdates(it.getAffectedFeature())) {
                LOGGER.trace("Ignoring update reference: " + it + " (feature is exempt from explicit updates)");
            } else {
                LOGGER.trace("Got an update reference: " + it + " on feature " + it.getAffectedFeature().getName());
                EObject targetObject = translator.lookupInTarget(it.getAffectedEObject());

                LOGGER.trace("\tApplying it to: " + targetObject);
                LOGGER.trace("\tSource affected object is: " + it.getAffectedEObject());

                LOGGER.trace("\tNew value: " + it.getNewValue());

                if (it.getAffectedFeature().isContainment()) {
                    applyMultiplicityOneContainmentChange(it, targetObject);
                } else {
                    EObject targetValue = translator.lookupInTarget((EObject) it.getNewValue());
                    setFeature(targetObject, it.getAffectedFeature().getName(), targetValue);
                }

            }
        }

        @Override
        public void visit(UpdateEAttribute<?> it) {
            LOGGER.trace("Got an update attribute: " + it + " for feature " + it.getAffectedFeature().getName());
            EObject targetObject = translator.lookupInTarget(it.getAffectedEObject());

            LOGGER.trace("\tApplying it to: " + targetObject);
            LOGGER.trace("\tSource affected object is: " + it.getAffectedEObject());

            setFeature(targetObject, it.getAffectedFeature().getName(), it.getNewValue());
        }

        @Override
        public void visit(CreateNonRootEObject<?> it) {
            LOGGER.trace("Got a create nonroot object: " + it + " creating " + it.getChangedEObject());
            EObject createdObject = it.getChangedEObject();

            floatingAddedObjects.add(createdObject);

            if (it.getListUpdate() != null) {
                visit(it.getListUpdate());
            } else {
                visit((UpdateEReference<?>) it);
            }
        }

        @Override
        public void visit(InsertInEList<? extends EStructuralFeature> it) {
            if (it.getAffectedFeature() instanceof EReference) {
                insertInEReferenceList(it);
            } else if (it.getAffectedFeature() instanceof EAttribute) {
                insertInEAttributeList(it);
            } else {
                throw new UnsupportedOperationException(
                        "Insert in list feature is supported only for attributes and references.");
            }
        }

        private void insertInEAttributeList(InsertInEList<? extends EStructuralFeature> it) {
            Object insertedObject = it.getUpdate();

            String targetFeatureName = it.getAffectedFeature().getName();
            EObject affectedContainer = translator.lookupInTarget(it.getAffectedEObject());
            EStructuralFeature targetFeature = affectedContainer.eClass().getEStructuralFeature(targetFeatureName);

            @SuppressWarnings("unchecked")
            EList<Object> featureList = (EList<Object>) affectedContainer.eGet(targetFeature);
            featureList.add(it.getIndex(), insertedObject);
        }

        private void insertInEReferenceList(InsertInEList<? extends EStructuralFeature> it) {
            EObject insertedObj = (EObject) it.getUpdate();
            if (floatingAddedObjects.contains(insertedObj)) {
                LOGGER.trace("Creating an object: " + it);
                // Case 0: This object has been created.
                objectCopier.addCopyInTarget(insertedObj, it.getIndex());
                floatingRemovedObjects.remove(insertedObj);
            } else {
                EReference referenceFeature = (EReference) it.getAffectedFeature(); // TODO
                EObject targetInsertedObj;
                if (referenceFeature.isContainment()) {
                    // Case 1: The feature is a container, and it has not been created.
                    // Thus, this is a move operation.
                    LOGGER.trace("Inserting a previously removed object into a containment: " + it);
                    if (floatingRemovedObjects.containsKey(insertedObj)) {
                        // When it has been deleted before, it is among the floating removed
                        // objects.
                        targetInsertedObj = (EObject) floatingRemovedObjects.remove(insertedObj);
                    } else {
                        // Otherwise, the delete operation happens deeper down in the model, in
                        // which case the deleted object is not floating yet. Thus, the delete
                        // operation must be looked up.
                        assert removedObjMap.containsKey(insertedObj);
                        targetInsertedObj = removedObjMap.get(insertedObj);
                    }
                } else {
                    // Case 2: The feature is not a containment. Thus, the translator can
                    // directly yield the object since it can be located via its containment.
                    LOGGER.trace("Inserting an object into a non-containment reference list: " + it);
                    targetInsertedObj = translator.lookupInTarget((EObject) it.getUpdate());
                }

                assert targetInsertedObj != null;

                if (referenceFeature.isContainment() && targetInsertedObj.eContainer() != null) {
                    // There is a matching remove operation later on. When adding it to the new
                    // container, the "old" container gets modified, and the matching remove
                    // operation
                    // will cause the indices of remove operations on that list to be wrong.
                    // Thus, insert a dummy copy there which can be removed regularly later on.
                    EObject previousContainer = targetInsertedObj.eContainer();
                    @SuppressWarnings("unchecked")
                    EList<EObject> previousContainingList = (EList<EObject>) previousContainer.eGet(it
                            .getAffectedFeature());
                    int oldIndex = previousContainingList.indexOf(targetInsertedObj);

                    // The copier should not copy referenced items.
                    EcoreUtil.Copier copier = new EcoreUtil.Copier(false, false);
                    EObject dummy = copier.copy(targetInsertedObj);
                    previousContainingList.add(oldIndex + 1, dummy);
                    LOGGER.trace("\tUsing a dummy object to preserve indices: " + dummy);
                }

                String targetFeatureName = it.getAffectedFeature().getName();
                EObject affectedContainer = translator.lookupInTarget(it.getAffectedEObject());
                EStructuralFeature targetFeature = affectedContainer.eClass().getEStructuralFeature(targetFeatureName);
                @SuppressWarnings("unchecked")
                EList<EObject> featureList = (EList<EObject>) affectedContainer.eGet(targetFeature);
                featureList.add(it.getIndex(), targetInsertedObj);
            }

        }

        @Override
        public void visit(DeleteNonRootEObject<?> it) {
            LOGGER.trace("Got a delete nonroot object: " + it.getChangedEObject());
            floatingRemovedObjects.remove(it.getChangedEObject());

            if (it.getListUpdate() != null) {
                visit(it.getListUpdate());
            } else {
                visit((UpdateEReference<?>) it);
            }
        }

        @Override
        public void visit(RemoveFromEList<? extends EStructuralFeature> it) {
            LOGGER.trace("Got a remove in attribute list: " + it);
            LOGGER.trace("\tSource deleted object is: " + it.getUpdate());
            // Look up the target feature list
            EObject targetContainerObject = translator.lookupInTarget(it.getAffectedEObject());
            String containerFeatureName = it.getAffectedFeature().getName();
            EStructuralFeature targetFeature = targetContainerObject.eClass().getEStructuralFeature(
                    containerFeatureName);

            EList<?> feature = (EList<?>) targetContainerObject.eGet(targetFeature);

            Object removedObj = feature.remove(it.getIndex());
            feature.remove(removedObj);
            if (it.getAffectedFeature() instanceof EReference && ((EReference) it.getAffectedFeature()).isContainment()) {
                EObject eRemovedObj = (EObject) removedObj;
                assert eRemovedObj.eContainer() == null;
                floatingRemovedObjects.put(it.getUpdate(), eRemovedObj);
            }

            LOGGER.trace("\tRemoved: " + removedObj);

        }

        @Override
        public void visit(UnsetEFeature<?> it) {
            EObject affectedObj = translator.lookupInTarget(it.getAffectedEObject());
            String featureName = it.getAffectedFeature().getName();
            EStructuralFeature targetFeature = affectedObj.eClass().getEStructuralFeature(featureName);
            affectedObj.eUnset(targetFeature);
        }

        @Override
        public void visit(EChange it) {
            LOGGER.trace("Got an unknown change: " + it);
        }
    }
}
