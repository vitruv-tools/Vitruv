package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * {@link ObjectDeletionResolvingPass} removes changes to objects which get detached from the model
 * when applying the given changes.
 */
class ObjectDeletionResolvingPass implements IObjectChangePass {
    private final Collection<EObject> removedObjects;

    /**
     * Constructs a new {@link ObjectDeletionResolvingPass} instance.
     * 
     * @param removedObjects
     *            The set of objects detached from the model when applying the changes given to
     *            {@link ObjectDeletionResolvingPass#runPass(Collection)}.
     */
    public ObjectDeletionResolvingPass(Collection<EObject> removedObjects) {
        super();
        this.removedObjects = removedObjects;
    }

    /**
     * Processes the given {@link Collection} of {@link IObjectChange} objects.
     * 
     * @param changes
     *            A {@link Collection} of {@link IObjectChange} objects, which remains unmodified by
     *            {@link IObjectChangePass#runPass(Collection)}.
     * @return A {@link List} containing the processing result. The ordering of the objects given
     *         via <code>changes</code> is maintained.
     */
    @Override
    public List<IObjectChange> runPass(Collection<IObjectChange> changes) {
        List<IObjectChange> result = new ArrayList<>();

        for (IObjectChange change : changes) {
            if (!removedObjects.contains(change.getAffectedObject())) {
                result.add(change);
            }
        }

        return result;
    }
}
