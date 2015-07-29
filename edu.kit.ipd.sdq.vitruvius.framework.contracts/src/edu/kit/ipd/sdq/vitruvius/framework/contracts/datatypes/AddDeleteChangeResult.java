package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Quadruple;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple;

/**
 * Class for add/delete/change changes. existingObjectsToSave: objects that are changed
 * newRootObjectsToSave: new Objects that should be saved existingObjectsToDelete: objects that
 * should be deleted. The class i also used to hold new/changed/deleted correspondences that are
 * created/updated/deleted in the actual transformation.
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
public class AddDeleteChangeResult<T, U, V> extends ChangeResult {

    private Set<T> existingObjectsToSave;
    private Set<U> newRootObjectsToSave;
    private Set<V> existingObjectsToDelete;

    protected Set<Triple<CorrespondenceInstance, TUID, EObject>> existingCorrespondencesToUpdate;
    protected Set<Triple<CorrespondenceInstance, EObject, EObject>> newCorrespondences;
    protected Set<Pair<CorrespondenceInstance, TUID>> correspondencesToDelete;

    protected void addExistingObjectToSave(final T toSave) {
        this.existingObjectsToSave.add(toSave);
    }

    protected void addNewObjectToSave(final U newToSave) {
        this.newRootObjectsToSave.add(newToSave);
    }

    protected void addExistingObjectToDelete(final V toDelete) {
        this.existingObjectsToDelete.add(toDelete);
    }

    public AddDeleteChangeResult() {
        this(new HashSet<T>(), new HashSet<U>(), new HashSet<V>());
    }

    public AddDeleteChangeResult(final Set<T> existingObjectsToSave, final Set<U> newRootObjectsToSave,
            final Set<V> existingObjectsToDelete) {
        super();
        this.existingObjectsToSave = existingObjectsToSave;
        this.newRootObjectsToSave = newRootObjectsToSave;
        this.existingObjectsToDelete = existingObjectsToDelete;
        this.existingCorrespondencesToUpdate = new HashSet<Triple<CorrespondenceInstance, TUID, EObject>>();
        this.newCorrespondences = new HashSet<Triple<CorrespondenceInstance, EObject, EObject>>();
        this.correspondencesToDelete = new HashSet<Pair<CorrespondenceInstance, TUID>>();
    }

    public void addCorrespondenceChanges(final AddDeleteChangeResult<?, ?, ?> changeResult) {
        this.correspondencesToDelete.addAll(changeResult.correspondencesToDelete);
        this.existingCorrespondencesToUpdate.addAll(changeResult.existingCorrespondencesToUpdate);
        this.newCorrespondences.addAll(changeResult.newCorrespondences);
    }

    public Set<T> getExistingObjectsToSave() {
        return this.existingObjectsToSave;
    }

    public Set<U> getNewRootObjectsToSave() {
        return this.newRootObjectsToSave;
    }

    public void addChangeResult(final AddDeleteChangeResult<T, U, V> addDeleteChangeResult) {
        this.existingObjectsToSave.addAll(addDeleteChangeResult.existingObjectsToSave);
        this.newRootObjectsToSave.addAll(addDeleteChangeResult.newRootObjectsToSave);
        this.existingObjectsToDelete.addAll(addDeleteChangeResult.existingObjectsToDelete);
        addCorrespondenceChanges(addDeleteChangeResult);
    }

    public Set<V> getExistingObjectsToDelete() {
        return this.existingObjectsToDelete;
    }

    public void setExistingObjectsToDelete(final Set<V> existingObjectsToDelete) {
        this.existingObjectsToDelete = existingObjectsToDelete;
    }

    public void addNewCorrespondence(final CorrespondenceInstance correspondenceInstance, final EObject elementA,
            final EObject elementB, final Correspondence parrentCorrespondence) {
        this.newCorrespondences.add(new Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence>(
                correspondenceInstance, elementA, elementB, parrentCorrespondence));
    }

    public void addCorrespondenceToDelete(final CorrespondenceInstance correspondenceInstance,
            final TUID tuidToRemove) {
        this.correspondencesToDelete.add(new Pair<CorrespondenceInstance, TUID>(correspondenceInstance, tuidToRemove));
    }

    public void addCorrespondenceToUpdate(final CorrespondenceInstance correspondenceInstance, final TUID oldTUID,
            final EObject elementB, final Correspondence correspondence) {
        this.existingCorrespondencesToUpdate.add(new Quadruple<CorrespondenceInstance, TUID, EObject, Correspondence>(
                correspondenceInstance, oldTUID, elementB, correspondence));
    }

    public Set<Triple<CorrespondenceInstance, EObject, EObject>> getNewCorrespondences() {
        return this.newCorrespondences;
    }

    public Set<Pair<CorrespondenceInstance, TUID>> getCorrespondencesToDelete() {
        return this.correspondencesToDelete;
    }

    public Set<Triple<CorrespondenceInstance, TUID, EObject>> getCorrespondencesToUpdate() {
        return this.existingCorrespondencesToUpdate;
    }
}
