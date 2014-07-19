package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper.ShadowDeletionChangeHelper;

public class ShadowDeletionResolvingPass implements IObjectChangePass {

    private final Collection<EObject> detachedObjects;
    private final Collection<IObjectChange> additionalChangeOperations;

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

    public Collection<IObjectChange> getAdditionalChangeOperations() {
        return additionalChangeOperations;
    }
}
