package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.Collection;
import java.util.List;

/**
 * {@link IObjectChangePass} is an interface for classes processing a {@link Collection} of
 * {@link IObjectChange} objects.
 */
interface IObjectChangePass {
    /**
     * Processes the given {@link Collection} of {@link IObjectChange} objects.
     * 
     * @param changes
     *            A {@link Collection} of {@link IObjectChange} objects, which remains unmodified by
     *            {@link IObjectChangePass#runPass(Collection)}.
     * @return A {@link List} containing the processing result.
     */
    public List<IObjectChange> runPass(Collection<IObjectChange> changes);
}
