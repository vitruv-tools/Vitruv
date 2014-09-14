package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;

/**
 * An interface for {@link IEditorPartAdapter} factories.
 */
public interface IEditorPartAdapterFactory {

    /**
     * Creates an adapter for the given Eclipse EMF/GMF editor part.
     * 
     * @param editorPart
     *            An eclipse editor part.
     * 
     * @return A matching {@link IEditorPartAdapter} instance.
     * 
     * @throws EditorNotMonitorableException
     *             when the given editor cannot be monitored because no adapter can be created for
     *             the given editor type.
     */
    public IEditorPartAdapter createAdapter(IEditorPart editorPart);

    /**
     * An interface for Eclipse EMF/GMF editor adapters exposing functionality neccessary for
     * monitoring the respective editor.
     */
    public interface IEditorPartAdapter {
        /**
         * Retrieves the adapted editor's {@link EditingDomain}.
         * 
         * @return The adapted editor's {@link EditingDomain} object.
         */
        public EditingDomain getEditingDomain();

        /**
         * Retrieves the adapted editor's EMF model {@link Resource}.
         * 
         * @return The adapted editor's EMF model {@link Resource}.
         */
        public Resource getEditedModelResource();

        /**
         * Retrieves the adapted editor's {@link IEditorPart} instance.
         * 
         * @return the adapted editor's {@link IEditorPart} instance.
         */
        public IEditorPart getEditorPart();

        /**
         * Executes a {@link Runnable} within the adapted editor's context.
         * 
         * @param toBeExecuted
         *            A {@link Runnable} to be executed within the adapted editor's context.
         */
        public void executeCommand(Runnable toBeExecuted); // TODO: rename such that it is clear
                                                           // that the Runnable must leave the EMF
                                                           // models unaltered
    }
}
