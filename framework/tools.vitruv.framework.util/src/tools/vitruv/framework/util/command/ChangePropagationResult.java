package tools.vitruv.framework.util.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.util.datatypes.Pair;
import tools.vitruv.framework.util.datatypes.VURI;

public class ChangePropagationResult {

    private final Set<VURI> vurisToDelete;
    private final List<Pair<EObject, VURI>> rootEObjectsToSave;

    public ChangePropagationResult() {
        this.vurisToDelete = new HashSet<VURI>();
        this.rootEObjectsToSave = new ArrayList<Pair<EObject, VURI>>();
    }

    public Set<VURI> getVurisToDelete() {
        return this.vurisToDelete;
    }

    public void addVuriToDeleteIfNotNull(final VURI vuriToDelete) {
    	if (null != vuriToDelete) {
    		this.vurisToDelete.addAll(Arrays.asList(vuriToDelete));
        }
    }

    public List<Pair<EObject, VURI>> getRootEObjectsToSave() {
        return this.rootEObjectsToSave;
    }

    public void addRootEObjectToSave(final EObject eObject, final VURI vuri) {
        this.rootEObjectsToSave.add(new Pair<EObject, VURI>(eObject, vuri));
    }
    
    public void integrateResult(ChangePropagationResult transformationResult) {
    	if (transformationResult == null) {
    		return;
    	}
    	this.vurisToDelete.addAll(transformationResult.vurisToDelete);
    	this.rootEObjectsToSave.addAll(transformationResult.rootEObjectsToSave);
    }
}
