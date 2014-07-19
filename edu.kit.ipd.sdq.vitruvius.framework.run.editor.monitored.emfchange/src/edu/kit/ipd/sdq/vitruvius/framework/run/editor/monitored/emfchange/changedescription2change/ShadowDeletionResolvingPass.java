package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper.ShadowDeletionChangeHelper;

/**
 * {@link ShadowDeletionResolvingPass} resolves implicit delete operations by storing them to a
 * gettable list. The list of change operations passed to {@link #runPass(Collection)} remains
 * unchanged.
 */
public class ShadowDeletionResolvingPass implements IObjectChangePass {

    private final Collection<EObject> detachedObjects;
    private final Collection<IObjectChange> additionalChangeOperations;

    /**
     * Constructs a new {@link ShadowDeletionResolvingPass} object.
     * 
     * @param detachedObjects
     *            The collection of objects already marked as detached from the model.
     */
    public ShadowDeletionResolvingPass(Collection<EObject> detachedObjects) {
        this.detachedObjects = detachedObjects;
        this.additionalChangeOperations = new HashSet<>();
    }

    private IObjectChange createObjectChange(EObject affectedObject, List<Change> eChanges) {
        return new EChangeCompoundObjectChange(affectedObject, eChanges, true);
    }

    @Override
    public List<IObjectChange> runPass(Collection<IObjectChange> changes) {
        List<IObjectChange> result = new ArrayList<>(changes);

        ShadowDeletionChangeHelper helper = new ShadowDeletionChangeHelper(detachedObjects);

        for (EObject detachedObj : detachedObjects) {
            List<Change> additionalChanges = helper.getShadowResolvingChanges(detachedObj);
            additionalChangeOperations.add(createObjectChange(detachedObj, additionalChanges));
        }

        detachedObjects.addAll(helper.getAdditionalDetachedObjects());

        return result;
    }

    /**
     * Gets the additional change operations created by this pass.
     * 
     * @return The additional change operations created by this pass. As these {@link IObjectChange}
     *         may contain delete operations affecting various objects (each affected object getting
     *         deleted by one of the contained delete operations), they report the object during
     *         whose deletion-resolving operation the changes were made as their "affected" object.
     *         The contents of these IObjectChange objects are ordered that no object gets
     *         referenced after its deletion.
     */
    public Collection<IObjectChange> getAdditionalChangeOperations() {
        return additionalChangeOperations;
    }
}
