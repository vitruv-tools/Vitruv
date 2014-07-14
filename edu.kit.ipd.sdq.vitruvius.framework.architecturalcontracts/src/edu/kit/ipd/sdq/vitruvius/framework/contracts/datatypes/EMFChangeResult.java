package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

public class EMFChangeResult extends ChangeResult {
    private Set<VURI> existingVURIsToSave;
    private Set<Pair<EObject, VURI>> newRootEObjectsToSave;
    private Set<VURI> existingVURIsToDelete;

    public EMFChangeResult() {
        super();
        this.existingVURIsToSave = new HashSet<VURI>();
        this.newRootEObjectsToSave = new HashSet<Pair<EObject, VURI>>();
    }

    public EMFChangeResult(final Set<VURI> existingVURIsToSave, final Set<Pair<EObject, VURI>> newRootEObjectsToSave,
            final Set<VURI> existingVURIsToDelete) {
        super();
        this.existingVURIsToSave = existingVURIsToSave;
        this.newRootEObjectsToSave = newRootEObjectsToSave;
        this.existingVURIsToDelete = existingVURIsToDelete;
    }

    public Set<VURI> getExistingVURIsToSave() {
        return this.existingVURIsToSave;
    }

    public Set<Pair<EObject, VURI>> getNewRootEObjectsToSave() {
        return this.newRootEObjectsToSave;
    }

    public void addEMFChangeResult(final EMFChangeResult emfChangeResult) {
        this.existingVURIsToSave.addAll(emfChangeResult.existingVURIsToSave);
        this.newRootEObjectsToSave.addAll(emfChangeResult.newRootEObjectsToSave);
    }

    public Set<VURI> getExistingVURIsToDelete() {
        return this.existingVURIsToDelete;
    }

    public void setExistingVURIsToDelete(final Set<VURI> existingVURIsToDelete) {
        this.existingVURIsToDelete = existingVURIsToDelete;
    }
}
