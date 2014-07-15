package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * {@link ForwardReferenceResolvingPass} resolves forward references in containment changes, i.e.
 * sorts the given list of {@link IObjectChange} objects such that no references to inserted objects
 * occur in changes preceding its insertion change.
 */
class ForwardReferenceResolvingPass implements IObjectChangePass {
    private final Collection<EObject> addedObjects;

    /**
     * Constructs a new {@link ForwardReferenceResolvingPass} object.
     * 
     * @param addedObjects
     *            The set of objects attached to the model instance by the changes passed to
     *            {@link ForwardReferenceResolvingPass#runPass(Collection)}.
     */
    public ForwardReferenceResolvingPass(Collection<EObject> addedObjects) {
        super();
        this.addedObjects = addedObjects;
    }

    @Override
    public List<IObjectChange> runPass(Collection<IObjectChange> changes) {
        return resolveForwardReferences(changes, addedObjects);
    }

    private List<IObjectChange> resolveForwardReferences(Collection<IObjectChange> containmentChanges,
            Collection<EObject> addedObjects) {

        // 0. Make changes accessible by changed object.
        Map<EObject, List<IObjectChange>> changesByChanged = Util.getAffectedObjectMap(containmentChanges);

        // 1. Gather containers of new objects.
        Map<EObject, Set<EObject>> containerMap = Util.getContainers(addedObjects, containmentChanges);

        // 2. Collect already-existing containments of new objects.
        List<EObject> addableContainerObjects = new ArrayList<EObject>(containerMap.keySet());
        addableContainerObjects.removeAll(addedObjects);

        // 3. Gather object changes in a BFS manner such that no forward references occur.
        List<IObjectChange> result = new ArrayList<IObjectChange>();
        Set<EObject> processedContainers = new HashSet<EObject>();
        while (!addableContainerObjects.isEmpty()) {
            EObject head = addableContainerObjects.remove(0);
            // Add the changes to this object
            result.addAll(changesByChanged.get(head));
            if (containerMap.containsKey(head)) {
                // Now that a containment change exists, these objects can be set up, too.
                addableContainerObjects.addAll(containerMap.get(head));
                addableContainerObjects.retainAll(containerMap.keySet()); // TODO: optimize
            }

            assert !processedContainers.contains(head) : "Detected a circular containment";
            processedContainers.add(head);
        }
        assert processedContainers.size() == containerMap.keySet().size() : "Failed to process all containers";

        // 4. Add all changes not dealing with new objects, i.e. purely consisting of
        // deletions or movements

        Set<IObjectChange> nonAddingChanges = new HashSet<IObjectChange>(containmentChanges);
        nonAddingChanges.removeAll(result);
        result.addAll(nonAddingChanges);

        return result;
    }
}
