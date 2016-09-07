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

package tools.vitruv.domains.emf.monitorededitor;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorPart;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.util.datatypes.VURI;

/**
 * An interface for monitors listening for model changes in EMF/GMF editors, triggering model
 * synchronization whenever the models are saved.
 */
public interface ISynchronizingMonitoredEmfEditor {

    /**
     * Initializes the instance. Before calling this method, no changes to EMF models are recorded,
     * and no synchronization is triggered.
     */
    public void initialize();

    /**
     * Disposes the instance. After calling this method, no changes to EMF models are recorded, and
     * no synchronization is triggered.
     */
    public void dispose();

    /**
     * Sets up the monitoring for the given editor. If the editor is already monitored (e.g. because
     * the {@link ISynchronizingMonitoredEmfEditor} automatically added it after the editor's
     * creation), this method has no effect.
     * 
     * @param editorPart
     *            The eclipse editor instance to be monitored.
     * 
     * @throws EditorNotMonitorableException
     *             when the editor part cannot be monitored by this
     *             {@link ISynchronizingMonitoredEmfEditor} instance.
     */
    public void enableMonitoring(IEditorPart editorPart);

    /**
     * Stops the monitoring activity for the given editor part.
     * 
     * @param editorPart
     *            The editor part which is not to be monitored anymore. If this editor is not among
     *            the monitored editors, this method has no effect.
     */
    public void disableMonitoring(IEditorPart editorPart);

    /**
     * Determines whether a given editor is being monitored.
     * 
     * @param editorPart
     *            An eclipse editor.
     * 
     * @return <code>true</code> iff the given editor is among the monitored editors.
     */
    public boolean isMonitoringEditor(IEditorPart editorPart);

    /**
     * Adds a listener getting informed when the monitor attaches itself to an editor or detaches
     * itself from one.
     * 
     * @param listener
     *            The {@link IEditorStateListener} to be installed.
     */
    public void addEditorStateListener(IEditorStateListener listener);

    /**
     * {@link IEditorStateListener} is an interface for listeners observing changes in the set of
     * monitored editors.
     */
    public interface IEditorStateListener {

        /** {@link EditorStateChange} represents changes in the monitoring state of editors. */
        public enum EditorStateChange {
            MONITORING_STARTED, MONITORING_ENDED;
        }

        /**
         * Informs the listener that the monitoring state of an editor has changed.
         * 
         * @param editor
         *            The affected editor part.
         * @param modelResource
         *            The model resource opened in the editor.
         * @param stateChange
         *            The monitoring state change of <code>editor</code>.
         */
        public void monitoringStateChanged(IEditorPart editor, Resource modelResource, EditorStateChange stateChange);
    }

    /**
     * {@link ResourceChangeSynchronizing} is an interface for classes responsible for synchronizing
     * changes in EMF models with other systems.
     */
    public interface ResourceChangeSynchronizing {
        /**
         * Synchronizes the changes in the given EMF model.
         * 
         * @param changes
         *            A list {@link VitruviusChange} objects pertaining to the modified model.
         * @param sourceModelURI
         *            The modified model's {@link VURI}.
         * @param changesOrigin
         *            The modified model's {@link Resource}.
         */
        void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource changesOrigin);
    }
}
