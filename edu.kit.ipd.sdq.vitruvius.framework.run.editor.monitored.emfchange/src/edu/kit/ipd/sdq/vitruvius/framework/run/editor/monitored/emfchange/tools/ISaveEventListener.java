package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools;

import org.eclipse.core.commands.ExecutionException;

/**
 * A listener interface for Eclipse save events.
 */
public interface ISaveEventListener {
    /**
     * This method gets called when a save action is about to be executed.
     */
    public void onPreSave();

    /**
     * This method gets called when a save action has been executed.
     */
    public void onPostSave();

    /**
     * This method gets called when a save action has been aborted with errors.
     * 
     * @param commandId
     *            The command's ID.
     * @param exception
     *            The Eclipse exception describing the error.
     */
    public abstract void postExecuteFailure(String commandId, ExecutionException exception);
}
