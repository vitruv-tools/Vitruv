package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;

public class ShadowDeletionChangeHelper {
    private static final Logger LOGGER = Logger.getLogger(ShadowDeletionChangeHelper.class);

    private final List<EObject> additionalDetachedObjects = new ArrayList<>();
    private final Collection<EObject> detachedObjects;

    public ShadowDeletionChangeHelper(Collection<EObject> detachedObjects) {
        this.detachedObjects = detachedObjects;
    }

    public List<Change> getShadowResolvingChanges(EObject object) {
        LOGGER.trace("Adding shadowed delete operations for " + object);
        List<Change> result = new ArrayList<Change>();
        addShadowResolvingChanges(object, result);
        return result;
    }

    private void addShadowResolvingChanges(EObject object, List<Change> changeCollector) {
        for (EReference feature : object.eClass().getEAllReferences()) {
            // eIsSet does not work here since it does not return true for eGenericType features
            // in EOperation objects. This may be a bug in EMF.
            // TODO: Investigate possible EMF bug.
            if (feature.isContainment() && object.eGet(feature) != null) {
                if (feature.isMany()) {
                    processMultiplicityManyFeature(object, feature, changeCollector);
                } else {
                    processMultiplicityOneFeature(object, feature, changeCollector);
                }
            }
        }
    }

    private void processMultiplicityManyFeature(EObject object, EReference feature, List<Change> changeCollector) {
        EList<?> children = (EList<?>) object.eGet(feature);
        for (Object childObj : children) {
            if (detachedObjects.contains(childObj)) {
                continue;
            }

            EObject child = (EObject) childObj;

            DeleteNonRootEObject<EObject> deleteChange = EChangeFactory.createDeleteNonRootObject(object, feature,
                    child, null);
            RemoveFromEList<EReference> listChange = EChangeFactory.createRemoveFromEList(object, feature, child, 0);
            deleteChange.setListUpdate(listChange);

            additionalDetachedObjects.add(child);
            addShadowResolvingChanges(child, changeCollector);
            LOGGER.trace("\tAdding synthesized delete change for " + child + ", contained in " + object);
            changeCollector.add(EMFModelChangeFactory.createEMFModelChange(deleteChange));
        }
    }

    private void processMultiplicityOneFeature(EObject object, EReference feature, List<Change> changeCollector) {
        EObject child = (EObject) object.eGet(feature);
        if (child == null || detachedObjects.contains(child)) {
            return;
        }
        DeleteNonRootEObject<EObject> deleteChange = EChangeFactory.createDeleteNonRootObject(object, feature, child,
                null);

        additionalDetachedObjects.add(object);
        addShadowResolvingChanges(child, changeCollector);
        LOGGER.trace("\tAdding synthesized delete change for " + child + ", contained in " + object);
        changeCollector.add(EMFModelChangeFactory.createEMFModelChange(deleteChange));
    }

    public List<EObject> getAdditionalDetachedObjects() {
        return additionalDetachedObjects;
    }
}
