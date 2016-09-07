package tools.vitruvius.framework.util.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.framework.util.datatypes.VURI;

public class TransformationResult {

    private final Set<VURI> vurisToDelete;
    private final List<Pair<EObject, VURI>> rootEObjectsToSave;

    public TransformationResult() {
        this.vurisToDelete = new HashSet<VURI>();
        this.rootEObjectsToSave = new ArrayList<Pair<EObject, VURI>>();
    }

    public Set<VURI> getVUIRsToDelete() {
        return this.vurisToDelete;
    }

    public void addVURIToDeleteIfNotNull(final VURI... vuriToDelete) {
        if (null != vuriToDelete) {
            for (VURI vuri : vuriToDelete) {
                if (null != vuri) {
                    this.vurisToDelete.addAll(Arrays.asList(vuri));
                }
            }
        }
    }

    public List<Pair<EObject, VURI>> getRootEObjectsToSave() {
        return this.rootEObjectsToSave;
    }

    public void addRootEObjectToSave(final EObject eObject, final VURI vuri) {
        this.rootEObjectsToSave.add(new Pair<EObject, VURI>(eObject, vuri));
    }
}
