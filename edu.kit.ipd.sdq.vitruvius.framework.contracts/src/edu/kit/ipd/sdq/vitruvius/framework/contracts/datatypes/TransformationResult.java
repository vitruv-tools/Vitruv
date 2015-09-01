package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class TransformationResult {

    private final List<VURI> vurisToDelete;
    private final List<Pair<EObject, VURI>> rootEObjectsToSave;
    private final Set<EObject> affectedEObjects;

    public TransformationResult() {
        this.vurisToDelete = new ArrayList<VURI>();
        this.rootEObjectsToSave = new ArrayList<Pair<EObject, VURI>>();
        this.affectedEObjects = new HashSet<EObject>();
    }

    public List<VURI> getVUIRsToDelete() {
        return this.vurisToDelete;
    }

    public void addVURIToDelete(final VURI... vuriToDelete) {
        this.vurisToDelete.addAll(Arrays.asList(vuriToDelete));
    }

    public List<Pair<EObject, VURI>> getRootEObjectsToSave() {
        return this.rootEObjectsToSave;
    }

    public void addRootEObjectToSave(final EObject eObject, final VURI vuri) {
        this.rootEObjectsToSave.add(new Pair<EObject, VURI>(eObject, vuri));
    }

    public Set<EObject> getAffectedEObjects() {
        return this.affectedEObjects;
    }

    public void addAffectedEObject(final EObject eObject) {
        this.affectedEObjects.add(eObject);
    }
}
