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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.IEditorPart;

/**
 * {@link SaveEventListenerMgr} is responsible for installing, managing and deinstalling
 * {@link ISaveEventListener} instances.
 */
public class SaveEventListenerMgr {
    private final Map<ISaveEventListener, SaveActionExecutionListener> listeners = new HashMap<>();

    private final IEclipseAdapter eclipseAdapter = EclipseAdapterProvider.getInstance().getEclipseAdapter();
    private IEditorPart editorPartRestriction = null;

    /**
     * Install the given {@link ISaveEventListener}.
     * 
     * @param it
     *            The listener to be installed.
     */
    public void install(ISaveEventListener it) {
        SaveActionExecutionListener innerListener = new SaveActionExecutionListener(it);
        listeners.put(it, innerListener);
        eclipseAdapter.addCommandServiceListener(innerListener);
        if (editorPartRestriction != null) {
            innerListener.enableEditorRestriction(editorPartRestriction);
        }
    }

    /**
     * Removes all {@link SaveActionExecutionListener}s from the instance and shuts down the
     * {@link SaveEventListenerMgr} instance.
     */
    public void dispose() {
        for (SaveActionExecutionListener iel : listeners.values()) {
            eclipseAdapter.removeCommandServiceListener(iel);
        }
        listeners.clear();
    }

    /**
     * Restricts the listeners such that only the given {@link IEditorPart} is monitored for saving
     * actions.
     * 
     * @param editorPart
     *            The monitored {@link IEditorPart}.
     */
    public void restrictSaveActionsToEditorPart(IEditorPart editorPart) {
        this.editorPartRestriction = editorPart;
        for (SaveActionExecutionListener listener : listeners.values()) {
            listener.enableEditorRestriction(editorPart);
        }
    }
}
