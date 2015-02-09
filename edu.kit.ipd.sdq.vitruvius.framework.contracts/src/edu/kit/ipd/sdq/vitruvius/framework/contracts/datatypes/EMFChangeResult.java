package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class EMFChangeResult extends AddDeleteChangeResult<VURI, Pair<EObject, VURI>, VURI> {

    private boolean isLastChangeResultInList;

    public EMFChangeResult() {
        super();
        this.isLastChangeResultInList = false;
    }

    public EMFChangeResult(final Set<VURI> existingVURIsToSave, final Set<Pair<EObject, VURI>> newRootEObjectsToSave,
            final Set<VURI> existingVURIsToDelete) {
        super(existingVURIsToSave, newRootEObjectsToSave, existingVURIsToDelete);
    }

    public boolean isLastChangeResultInList() {
        return this.isLastChangeResultInList;
    }

    public void setLastChangeResultInList(final boolean isLastChangeResultInList) {
        this.isLastChangeResultInList = isLastChangeResultInList;
    }

}
