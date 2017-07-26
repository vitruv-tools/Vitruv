/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruv.domains.emf.monitorededitor.tools;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.IEditorPart;

/**
 * {@link SaveActionExecutionListener} is an {@link IExecutionListener} monitoring the workbench for
 * for FILE_SAVE, FILE_SAVE_ALL and FILE_SAVE_AS events. It can be restricted to monitor these
 * events for a single {@link IEditorPart}.
 */
class SaveActionExecutionListener implements IExecutionListener {

    private boolean restrictToEditorPart = false;
    private IEditorPart editorPartRestriction = null;
    private final IEclipseAdapter eclipseAdapter = EclipseAdapterProvider.getInstance().getEclipseAdapter();
    private final ISaveEventListener saveEventListener;

    /**
     * Constructs a new {@link SaveActionExecutionListener} object.
     * 
     * @param saveEventListener
     *            The {@link ISaveEventListener} to be called on save events.
     */
    public SaveActionExecutionListener(ISaveEventListener saveEventListener) {
        this.saveEventListener = saveEventListener;
    }

    private boolean isSavingCommand(final String commandId) {
        return commandId.equals(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE)
                || commandId.equals(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE_ALL)
                || commandId.equals(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE_AS);
    }

    private boolean isSaveAllCommand(String commandId) {
        return commandId.equals(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE_ALL);
    }

    /**
     * Restricts the listener such that is only fired when the given Eclipse editor is currently
     * being edited.
     * 
     * @param editorPart
     *            The {@link IEditorPart} to which the listener is to be restricted.
     */
    public void enableEditorRestriction(IEditorPart editorPart) {
        editorPartRestriction = editorPart;
        restrictToEditorPart = true;
    }

    private boolean isEnabledForCurrentEditor() {
        IEditorPart activeEditor = eclipseAdapter.getActiveEditorPart();
        return !restrictToEditorPart || (activeEditor == editorPartRestriction);
    }

    @Override
    public void preExecute(String commandId, ExecutionEvent event) {
        if (isSavingCommand(commandId) && (isSaveAllCommand(commandId) || isEnabledForCurrentEditor())) {
            onPreSave();
        }
    }

    @Override
    public void postExecuteSuccess(String commandId, Object returnValue) {
        if (isSavingCommand(commandId) && (isSaveAllCommand(commandId) || isEnabledForCurrentEditor())) {
            onPostSave();
        }
    }

    @Override
    public void postExecuteFailure(String commandId, ExecutionException exception) {
        saveEventListener.postExecuteFailure(commandId, exception);
    }

    private void onPreSave() {
        saveEventListener.onPreSave();
    }

    private void onPostSave() {
        saveEventListener.onPostSave();
    }

    @Override
    public void notHandled(String commandId, NotHandledException exception) {

    }

}
