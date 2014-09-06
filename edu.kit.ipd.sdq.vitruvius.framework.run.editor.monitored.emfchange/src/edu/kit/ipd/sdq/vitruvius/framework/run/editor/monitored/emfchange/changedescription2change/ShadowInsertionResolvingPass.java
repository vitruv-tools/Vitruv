package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper.ShadowInsertionChangeHelper;

/**
 * {@link ShadowInsertionResolvingPass} resolves implicit ("shadow") object insertions, i.e. creates
 * {@link IObjectChange} objects such that changes to objects made before inserting them into the
 * model instance are reflected by the change list.
 * 
 * This resolution is necessary since changes to objects are only captured as long as they are part
 * of the model instance. For example, if an {@link EObject} is created, and a new child object is
 * added to a containment reference before adding the {@link EObject} to the model instance, neither
 * the creation of the child nor its addition are registered by
 * {@link org.eclipse.emf.ecore.change.util.ChangeRecorder}. Thus, these changes must be crafted
 * explicitly.
 */
class ShadowInsertionResolvingPass implements IObjectChangePass {

    private final Collection<EObject> attachedObjects;

    /**
     * Constructs a new {@link ShadowInsertionResolvingPass} instance.
     * 
     * @param attachedObjects
     *            The set of objects attached to the model instance in the period of capturing
     *            changes. This set may be modified by the {@link ShadowInsertionResolvingPass}
     *            since shadowed created objects may be found.
     */
    public ShadowInsertionResolvingPass(Collection<EObject> attachedObjects) {
        super();
        this.attachedObjects = attachedObjects;
    }

    private IObjectChange createObjectChange(EObject affectedObject, List<EChange> eChanges, boolean isContainmentChange) {
        return new EChangeCompoundObjectChange(affectedObject, eChanges, isContainmentChange);
    }

    private void handleShadowInsertion(EObject attachedObject, List<EObject> newAttachedObjectsCollector,
            List<IObjectChange> resultCollector) {
        Map<EObject, List<EChange>> containmentChanges = new HashMap<>();
        Map<EObject, List<EChange>> nonContainmentChanges = new HashMap<>();

        ShadowInsertionChangeHelper helper = new ShadowInsertionChangeHelper();
        helper.setAttachedObjectCollector(newAttachedObjectsCollector);
        helper.setContainmentChangeCollector(containmentChanges);
        helper.setNonContainmentChangeCollector(nonContainmentChanges);
        helper.addShadowResolvingChanges(attachedObject);

        for (EObject key : containmentChanges.keySet()) {
            resultCollector.add(createObjectChange(key, containmentChanges.get(key), true));
        }
        for (EObject key : nonContainmentChanges.keySet()) {
            resultCollector.add(createObjectChange(key, nonContainmentChanges.get(key), false));
        }
    }

    /**
     * Processes the given {@link Collection} of {@link IObjectChange} objects.
     * 
     * @param changes
     *            A {@link Collection} of {@link IObjectChange} objects, which remains unmodified by
     *            {@link IObjectChangePass#runPass(Collection)}.
     * @return A {@link List} containing the processing result. The ordering of the objects given
     *         via <code>changes</code> is maintained.
     */
    @Override
    public List<IObjectChange> runPass(Collection<IObjectChange> changes) {
        List<IObjectChange> result = new ArrayList<>(changes);
        List<EObject> addedObjects = new ArrayList<>();

        for (EObject attachedObject : attachedObjects) {
            if (ShadowInsertionChangeHelper.isShadowInsertionObject(attachedObject)) {
                handleShadowInsertion(attachedObject, addedObjects, result);
            }
        }
        attachedObjects.addAll(addedObjects);

        return result;
    }
}
