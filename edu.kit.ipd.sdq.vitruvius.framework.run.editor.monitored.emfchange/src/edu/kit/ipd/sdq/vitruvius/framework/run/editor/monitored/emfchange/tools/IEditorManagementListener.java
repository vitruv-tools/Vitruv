package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools;

import org.eclipse.ui.IEditorPart;

/**
 * {@link IEditorManagementListener} is an interface for listeners getting notified when Eclipse
 * {@link IEditorPart}s are opened rsp. closed.
 */
public interface IEditorManagementListener {
    /**
     * This method gets called when an {@link IEditorPart} is opened.
     * 
     * @param editorPart
     *            The opened {@link IEditorPart}.
     */
    public void onCreated(IEditorPart editorPart);

    /**
     * This method gets called when an {@link IEditorPart} is closed.
     * 
     * @param editorPart
     *            The closed {@link IEditorPart}.
     */
    public void onClosed(IEditorPart editorPart);
}
