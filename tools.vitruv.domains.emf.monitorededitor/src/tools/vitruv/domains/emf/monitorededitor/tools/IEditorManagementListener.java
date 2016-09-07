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
