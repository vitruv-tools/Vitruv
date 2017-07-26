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
