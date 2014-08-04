package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for add/delete/change changes. existingObjectsToSave: objects that are changed
 * newRootObjectsToSave: new Objects that should be saved existingObjectsToDelete: objects that
 * should be deleted
 * 
 * @author Langhamm
 *
 * @param <T>
 *            Param for existingObjectsToSave and existingObjectsToDelete, e.g. EObject or VURI
 * @param <U>
 *            Param for newRootObjectsToSave --> differs from T because it is needed in
 *            {@link EMFChangeResult} to use a Pair of EObject and VURI to know where the new
 *            Eobjects should be saved
 */
public class AddDeleteChangeResult<T, U> extends ChangeResult {
    private Set<T> existingObjectsToSave;
    private Set<U> newRootObjectsToSave;
    private Set<T> existingObjectsToDelete;

    public AddDeleteChangeResult() {
        super();
        this.existingObjectsToSave = new HashSet<T>();
        this.newRootObjectsToSave = new HashSet<U>();
        this.existingObjectsToDelete = new HashSet<T>();
    }

    public AddDeleteChangeResult(final Set<T> existingObjectsToSave, final Set<U> newRootObjectsToSave,
            final Set<T> existingObjectsToDelete) {
        super();
        this.existingObjectsToSave = existingObjectsToSave;
        this.newRootObjectsToSave = newRootObjectsToSave;
        this.existingObjectsToDelete = existingObjectsToDelete;
    }

    public Set<T> getExistingObjectsToSave() {
        return this.existingObjectsToSave;
    }

    public Set<U> getNewRootObjectsToSave() {
        return this.newRootObjectsToSave;
    }

    public void addChangeResult(final AddDeleteChangeResult<T, U> addDeleteChangeResult) {
        this.existingObjectsToSave.addAll(addDeleteChangeResult.existingObjectsToSave);
        this.newRootObjectsToSave.addAll(addDeleteChangeResult.newRootObjectsToSave);
        this.existingObjectsToDelete.addAll(addDeleteChangeResult.existingObjectsToDelete);
    }

    public Set<T> getExistingObjectsToDelete() {
        return this.existingObjectsToDelete;
    }

    public void setExistingObjectsToDelete(final Set<T> existingObjectsToDelete) {
        this.existingObjectsToDelete = existingObjectsToDelete;
    }
}
