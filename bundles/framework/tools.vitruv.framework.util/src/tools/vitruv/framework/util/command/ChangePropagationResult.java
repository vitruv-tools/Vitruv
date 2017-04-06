package tools.vitruv.framework.util.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.util.datatypes.VURI;

public class ChangePropagationResult {

    private final Set<VURI> vurisToDelete;
    private final Map<VURI, List<EObject>> rootEObjectsToSave;

    public ChangePropagationResult() {
        this.vurisToDelete = new HashSet<VURI>();
        this.rootEObjectsToSave = new HashMap<VURI, List<EObject>>();
    }

    public Set<VURI> getVurisToDelete() {
        return this.vurisToDelete;
    }

    public void addVuriToDeleteIfNotNull(final VURI vuriToDelete) {
    	if (null != vuriToDelete) {
    		this.vurisToDelete.addAll(Arrays.asList(vuriToDelete));
        }
    }

    public Map<VURI, List<EObject>> getRootEObjectsToSave() {
        return this.rootEObjectsToSave;
    }

    public void addRootEObjectToSave(final EObject eObject, final VURI vuri) {
    	if (!rootEObjectsToSave.containsKey(vuri)) {
    		rootEObjectsToSave.put(vuri, new ArrayList<EObject>());
    	}
        this.rootEObjectsToSave.get(vuri).add(eObject);
    }
    
    public void integrateResult(ChangePropagationResult transformationResult) {
    	if (transformationResult == null) {
    		return;
    	}
    	transformationResult.vurisToDelete.forEach(vuriToDelete -> addVuriToDeleteIfNotNull(vuriToDelete));
    	for (VURI vuri : transformationResult.rootEObjectsToSave.keySet()) {
    		for (EObject root : transformationResult.rootEObjectsToSave.get(vuri)) {
    			this.addRootEObjectToSave(root, vuri);
    		}
    	}
    }
}
