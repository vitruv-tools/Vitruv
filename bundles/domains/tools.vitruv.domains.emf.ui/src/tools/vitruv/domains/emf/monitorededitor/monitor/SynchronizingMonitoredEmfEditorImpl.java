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

package tools.vitruv.domains.emf.monitorededitor.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorPart;

import tools.vitruv.domains.emf.monitorededitor.EditorNotMonitorableException;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;
import tools.vitruv.domains.emf.monitorededitor.IMonitoringDecider;
import tools.vitruv.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor;
import tools.vitruv.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor.IEditorStateListener.EditorStateChange;
import tools.vitruv.domains.emf.monitorededitor.tools.EclipseAdapterProvider;
import tools.vitruv.domains.emf.monitorededitor.tools.EditorManagementListenerMgr;
import tools.vitruv.domains.emf.monitorededitor.tools.IEclipseAdapter;
import tools.vitruv.domains.emf.monitorededitor.tools.IEditorManagementListener;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * <p>
 * A {@link ISynchronizingMonitoredEmfEditor} implementation monitoring all Eclipse EMF/GMF editors
 * for which a suitable {@link IEditorPartAdapter} can be created.
 * </p>
 * 
 * <p>
 * An editor part adapter factory is used to access Eclipse {@link IEditorPart}s. All editors for
 * which such an adapter can be created at editor creation time or during the call to
 * <code>initialize()</code> are automatically monitored after initialization. If an editor is not
 * suitable for being monitored directly after its creation but becomes so later on, it needs to be
 * added explicitly by calling <code>setupMonitorForEditor()</code>.
 * </p>
 * 
 * TODO: update this documentation to regard IMonitoringDecider
 */
public class SynchronizingMonitoredEmfEditorImpl implements ISynchronizingMonitoredEmfEditor {

    /** The logger for {@link SynchronizingMonitoredEmfEditorImpl} instances. */
    private static final Logger LOGGER = Logger.getLogger(SynchronizingMonitoredEmfEditorImpl.class);

    /**
     * The editor part adapter factory used to access Eclipse {@link IEditorPart}s. All editors for
     * which such an adapter can be created at editor creation time or during the call to
     * <code>initialize()</code> are automatically monitored after initialization.
     */
    private final IEditorPartAdapterFactory editorPartAdapterFact;

    /** This object determines whether an adaptable IEditorPart is monitored. */
    private final IMonitoringDecider monitoringDecider;

    /**
     * A {@link ResourceChangeSynchronizing} instance whose synchronization method gets called whenever
     * the user saves a monitored EMF/GMF editor.
     */
    private final ResourceChangeSynchronizing changeSynchronizing;

    /**
     * A map associating monitored Eclipse editors with their respective
     * {@link EMFModelChangeRecordingEditorSaveListener} objects.
     */
    private final Map<IEditorPart, EMFModelChangeRecordingEditorSaveListener> editors;

    /**
     * A listener monitoring Eclipse for editor creations and removals.
     */
    private final IEditorManagementListener editorCreationListener;

    private final EditorManagementListenerMgr editorManagementListenerMgr = new EditorManagementListenerMgr();

    private final Set<IEditorStateListener> editorStateListeners = new HashSet<>();

    /**
     * The {@link IEclipseAdapter} used to access Eclipse.
     */
    private final IEclipseAdapter eclipseAdapter = EclipseAdapterProvider.getInstance().getEclipseAdapter();

    private final VirtualModel virtualModel;

    /**
     * A {@link SynchronizingMonitoredEmfEditorImpl} constructor setting up the new monitor with a
     * synchronization callback object and a suitable {@link IEditorPartAdapter} factory.
     * 
     * @param changeSynchronizing
     *            The synchronization callback to be called whenever a synchronization needs to be
     *            triggered.
     * @param editorPartAdapterFact
     *            The {@link IEditorPartAdapter} factory providing access to Eclipse EMF/GMF editors
     *            which need to be monitored.
     * @param monitoringDecider
     *            An {@link IMonitoringDecider} object telling the new instance which editors need to be
     *            monitored.
     */
    public SynchronizingMonitoredEmfEditorImpl(final VirtualModel virtualModel,
            final ResourceChangeSynchronizing changeSynchronizing,
            final IEditorPartAdapterFactory editorPartAdapterFact, final IMonitoringDecider monitoringDecider) {
        this.virtualModel = virtualModel;
        this.changeSynchronizing = changeSynchronizing;
        this.editorPartAdapterFact = editorPartAdapterFact;
        this.monitoringDecider = monitoringDecider;
        this.editors = new HashMap<>();
        editorCreationListener = new IEditorManagementListener() {
            @Override
            public void onCreated(IEditorPart editorPart) {
                LOGGER.trace("Detected editor open: " + editorPart);
                enableMonitorForEditorIfApplicable(editorPart);
            }

            @Override
            public void onClosed(IEditorPart editorPart) {
                LOGGER.trace("Detected editor close: " + editorPart);
                disableMonitoring(editorPart);
            }
        };
    }

    @Override
    public void initialize() {
        LOGGER.trace("Installing myself in all accessible currently active editors");
        for (IEditorPart editor : eclipseAdapter.getCurrentlyActiveEditors()) {
            enableMonitorForEditorIfApplicable(editor);
        }

        LOGGER.trace("Initializing the editor creation monitor");
        editorManagementListenerMgr.initialize();
        editorManagementListenerMgr.addEditorManagementListener(editorCreationListener);
    }

    private void enableMonitorForEditorIfApplicable(final IEditorPart editorPart) {
        try {
            enableMonitoring(editorPart);
        } catch (EditorNotMonitorableException e) {
            LOGGER.trace(
                    "Not installing a listener for an editor of class " + editorPart.getClass().getCanonicalName());
        }
    }

    @Override
    public void enableMonitoring(final IEditorPart editorPart) {
        // If no such adapter can be created, a runtime exception is thrown by
        // createAdapter(), so no further checking needs to be done here.
        IEditorPartAdapter editorPartAdapter = editorPartAdapterFact.createAdapter(editorPart);
        if (monitoringDecider.isMonitoringEnabled(editorPartAdapter)) {
            LOGGER.debug(
                    "Installing an EMF monitor for an editor of class " + editorPart.getClass().getCanonicalName());
            setupMonitorForEditor(editorPartAdapter);
        } else {
            throw new EditorNotMonitorableException();
        }
    }

    private void addEditor(IEditorPart editorPart, EMFModelChangeRecordingEditorSaveListener listener) {
        LOGGER.debug("Adding editor part " + editorPart + " to set of monitored editors.");
        editors.put(editorPart, listener);
    }

    private void setupMonitorForEditor(final IEditorPartAdapter editorPart) {
        EMFModelChangeRecordingEditorSaveListener listener = new EMFModelChangeRecordingEditorSaveListener(editorPart) {
            @Override
            protected void onSavedResource(final List<? extends TransactionalChange> changeDescriptions) {
                LOGGER.trace("Received change descriptions " + changeDescriptions);
                if (null == changeDescriptions || changeDescriptions.isEmpty()) {
                    LOGGER.trace("changeDescription is null. Change can not be synchronized: " + this);
                    return;
                }
                // final List<List<VitruviusChange>> changes = new ArrayList<>();
                // The following code needs to be executed within the editor's
                // context because even though it does not change the EMF model
                // in the sense of equality, it does apply changes to the EMF
                // model. This is esp. relevant for editors using transactional
                // editing domains.
                // editorPart.executeCommand(new Runnable() {
                // @Override
                // public void run() {
                // changes.add(getChangeList(changeDescriptions,
                // editorPart.getEditedModelResource()));
                // }
                // });
                // changes.add(new ArrayList<EMFModelChange>(changeDescriptions));
                // assert changes.size() == 1;
                // for (int i = changeDescriptions.size() - 1; i >= 0; i--) {
                // changeDescriptions.get(i).getChangeDescription().applyAndReverse();
                // }
                // List<VitruviusChange> transformedChanges = new ArrayList<VitruviusChange>(
                // new ChangeDescription2ChangeTransformation(changeDescriptions,
                // true).getChanges());
                triggerSynchronization(new ArrayList<VitruviusChange>(changeDescriptions),
                        editorPart.getEditedModelResource());
            }
        };

        addEditor(editorPart.getEditorPart(), listener);

        LOGGER.trace("Initializing resource change listener.");
        listener.initialize(virtualModel);
        fireEditorStateListeners(editorPart.getEditorPart(), EditorStateChange.MONITORING_STARTED);
    }

    @Override
    public boolean isMonitoringEditor(IEditorPart editorPart) {
        return editors.containsKey(editorPart);
    }

    private void removeEditor(IEditorPart editorPart) {
        editors.remove(editorPart);
    }

    @Override
    public void disableMonitoring(IEditorPart editorPart) {
        if (isMonitoringEditor(editorPart)) {
            LOGGER.debug("Dismissing monitor for " + editorPart);
            EMFModelChangeRecordingEditorSaveListener listener = editors.get(editorPart);
            if (null != listener) {
                listener.dispose();
            }
            fireEditorStateListeners(editorPart, EditorStateChange.MONITORING_ENDED);
            removeEditor(editorPart);
        }
    }

    @Override
    public void dispose() {
        for (IEditorPart editor : new ArrayList<>(editors.keySet())) { // TODO: refactor, use
                                                                       // ConcurrentHashMap
            disableMonitoring(editor);
        }
        editorManagementListenerMgr.dispose();
    }

    // private List<VitruviusChange> getChangeList(List<EMFModelChange> changeDescriptions, Resource
    // resource) {
    // LOGGER.debug("Triggering synchronization for change description " + changeDescriptions + " on
    // resource "
    // + resource.getURI());
    // /*
    // * ChangeDescription2ChangeTransformation converter = new
    // * ChangeDescription2ChangeTransformation( changeDescriptions, VURI.getInstance(resource));
    // */
    // return converter.getChanges();
    // }

    private void triggerSynchronization(List<VitruviusChange> changes, Resource resource) {
        if (changes.isEmpty()) {
            LOGGER.debug("Not triggering synchronization for " + resource.getURI() + ": No changes detected.");
        } else {
            VURI uri = VURI.getInstance(resource);
            LOGGER.trace("Triggering synchronization for VURI " + uri.toString());
            changeSynchronizing.synchronizeChanges(changes, uri, resource);
        }
    }

    @Override
    public void addEditorStateListener(IEditorStateListener listener) {
        this.editorStateListeners.add(listener);
    }

    private void fireEditorStateListeners(IEditorPart modifiedEditor, EditorStateChange stateChange) {
        for (IEditorStateListener listener : editorStateListeners) {
            listener.monitoringStateChanged(modifiedEditor, editors.get(modifiedEditor).getMonitoredResource(),
                    stateChange);
        }
    }
}
