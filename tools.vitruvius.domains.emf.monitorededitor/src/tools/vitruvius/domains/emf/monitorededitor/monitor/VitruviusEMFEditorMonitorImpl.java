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

package tools.vitruvius.domains.emf.monitorededitor.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorPart;

import tools.vitruvius.domains.emf.monitorededitor.EditorNotMonitorableException;
import tools.vitruvius.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruvius.domains.emf.monitorededitor.IMonitoringDecider;
import tools.vitruvius.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor;
import tools.vitruvius.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor;
import tools.vitruvius.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;
import tools.vitruvius.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import tools.vitruvius.domains.emf.monitorededitor.tools.EclipseAdapterProvider;
import tools.vitruvius.domains.emf.monitorededitor.tools.IEclipseAdapter;
import tools.vitruvius.framework.change.description.CompositeChange;
import tools.vitruvius.framework.change.description.VitruviusChangeFactory;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import tools.vitruvius.framework.util.bridges.EMFBridge;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * A simple implementation of {@link IVitruviusEMFEditorMonitor} using an {@link IVitruviusAccessor}
 * object to determine whether a resource needs to be monitored.
 * 
 * Whenever the user saves the model, the changes since the last save event rsp. the beginning of
 * the editing session are passed to the provided {@link ChangeSynchronizing} object.
 * 
 */
public class VitruviusEMFEditorMonitorImpl implements IVitruviusEMFEditorMonitor {

    private static final Logger LOGGER = Logger.getLogger(VitruviusEMFEditorMonitorImpl.class);

    /** The EMF editor change listener. */
    private final ISynchronizingMonitoredEmfEditor changeRecorderMonitor;

    /**
     * An accessor to the Vitruvius framework used to determine which resources need to be
     * monitored.
     */
    private final IVitruviusAccessor vitruvAccessor;

    /** The object used to access Eclipse. */
    private final IEclipseAdapter eclipseAdapter = EclipseAdapterProvider.getInstance().getEclipseAdapter();

    /** The editor part adapter factory used to access Eclipse IEditorPart objects. */
    private final IEditorPartAdapterFactory editorPartAdapterFactory;

    private final ChangeSynchronizing summaryChangeSynchronizing;

    private final Map<VURI, Long> lastSynchronizationRequestTimestamps;

    /**
     * Setting this flag to <code>true</code> disables timestamp-based recognition of
     * synchronization lags, i.e. situations when triggerSynchronization() is called for a file
     * before all corresponding changes for that have been applied to that file up to the point of
     * (file modification) time of calling triggerSynchronization().
     */
    private boolean isSynchronizationLagRecognitionDisabled = false;

    /** This object determines whether an adaptable IEditorPart is monitored. */
    private final IMonitoringDecider monitoringDecider = new IMonitoringDecider() {
        @Override
        public boolean isMonitoringEnabled(IEditorPartAdapter editor) {
            return isMonitoredVitruviusResource(editor.getEditedModelResource());
        }
    };

    private boolean reportChanges;

    private final List<VitruviusChange> collectedChanges;

    /**
     * A constructor for {@link VitruviusEMFEditorMonitorImpl} instances.
     * 
     * @param factory
     *            The {@link IEditorPartAdapterFactory} providing access to the EMF editors.
     * @param changeSync
     *            The {@link ChangeSynchronizing} object getting passed the changes to the EMF model
     *            when the user saves the editing session.
     * @param modelCopyProviding
     *            The Vitruvius model copy provider.
     * @param vitruvAccessor
     *            A {@link IVitruviusAccessor} instance providing information about which EMF models
     *            need to be monitored.
     */
    public VitruviusEMFEditorMonitorImpl(IEditorPartAdapterFactory factory, ChangeSynchronizing changeSync,
            ModelProviding modelProviding, IVitruviusAccessor vitruvAccessor) {
        ResourceChangeSynchronizing internalChangeSync = createInternalChangeSynchronizing();
        changeRecorderMonitor = new SynchronizingMonitoredEmfEditorImpl(internalChangeSync, factory, monitoringDecider);
        this.vitruvAccessor = vitruvAccessor;
        this.editorPartAdapterFactory = factory;
        this.summaryChangeSynchronizing = changeSync;
        this.lastSynchronizationRequestTimestamps = new HashMap<>();
        this.collectedChanges = new ArrayList<>();
    }

    /**
     * A convenience constructor for {@link VitruviusEMFEditorMonitorImpl} instances initializing
     * the instance with an {@link IEditorPartAdapterFactory} such that all editors are monitored
     * whose resource set contains an EMF model registered with the Vitruvius framework.
     * 
     * @param changeSync
     *            The {@link ChangeSynchronizing} object getting passed the changes to the EMF model
     *            when the user saves the editing session.
     * @param modelCopyProviding
     *            The Vitruvius model copy provider.
     * @param vitruvAccessor
     *            A {@link IVitruviusAccessor} instance providing information about which EMF models
     *            need to be monitored.
     */
    public VitruviusEMFEditorMonitorImpl(ChangeSynchronizing changeSync, ModelProviding modelProviding,
            IVitruviusAccessor vitruvAccessor) {
        this(new DefaultEditorPartAdapterFactoryImpl(), changeSync, modelProviding, vitruvAccessor);
    }

    private boolean isPendingSynchronizationRequest(VURI resourceURI) {
        if (isSynchronizationLagRecognitionDisabled) {
            return false;
        }

        synchronized (lastSynchronizationRequestTimestamps) {
            if (lastSynchronizationRequestTimestamps.containsKey(resourceURI)) {
                IFile resourceFile = EMFBridge.getIFileForEMFUri(resourceURI.getEMFUri());
                long currentSynchroTimestamp = resourceFile.getModificationStamp();
                return currentSynchroTimestamp <= lastSynchronizationRequestTimestamps.get(resourceURI);
            } else {
                return false;
            }
        }
    }

    private ResourceChangeSynchronizing createInternalChangeSynchronizing() {
        return new ResourceChangeSynchronizing() {
            @Override
            public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource origin) {
                LOGGER.trace("Adding changes for VURI " + sourceModelURI);
                collectedChanges.addAll(changes);
                if (isPendingSynchronizationRequest(sourceModelURI)) {
                    triggerSynchronisation(sourceModelURI);
                }
            }
        };
    }

    @Override
    public void initialize() {
        changeRecorderMonitor.initialize();
        reportChanges = true;
    }

    @Override
    public void dispose() {
        changeRecorderMonitor.dispose();
    }

    @Override
    public void addModel(VURI uri) {
        for (IEditorPart editorPart : findEditorsForModel(uri)) {
            if (!changeRecorderMonitor.isMonitoringEditor(editorPart)) {
                changeRecorderMonitor.enableMonitoring(editorPart);
            }
        }
    }

    @Override
    public void removeModel(VURI uri) {
        for (IEditorPart editorPart : findEditorsForModel(uri)) {
            if (changeRecorderMonitor.isMonitoringEditor(editorPart)) {
                changeRecorderMonitor.disableMonitoring(editorPart);
            }
        }
    }

    private boolean isMonitoredVitruviusResource(Resource r) {
        return vitruvAccessor.isModelMonitored(VURI.getInstance(r.getURI()));
    }

    /**
     * Retrieves the Eclipse editors currently editing an EMF model with the given {@link VURI}.
     * 
     * This is a private method made protected for testing purposes only.
     * 
     * @param searchURI
     *            The {@link VURI} for which the associated editors are to be looked up.
     * @return The editors currently editing an EMF model having the given <code>searchURI</code>.
     */
    protected Set<IEditorPart> findEditorsForModel(VURI searchURI) {
        Set<IEditorPart> result = new HashSet<>();

        Set<IEditorPart> activeEditors = this.eclipseAdapter.getCurrentlyActiveEditors();
        for (IEditorPart part : activeEditors) {
            try {
                IEditorPartAdapter partAdapter = this.editorPartAdapterFactory.createAdapter(part);
                VURI partVURI = VURI.getInstance(partAdapter.getEditedModelResource());
                if (partVURI == searchURI) {
                    result.add(part);
                }
            } catch (EditorNotMonitorableException e) {
                continue;
            }
        }

        return result;
    }

    /**
     * This is a private method made protected for testing purposes only.
     * 
     * @return The {@link ISynchronizingMonitoredEmfEditor} used to monitor Eclipse editors.
     */
    protected ISynchronizingMonitoredEmfEditor getChangeRecorderMonitor() {
        return this.changeRecorderMonitor;
    }

    private void updateSynchronizationTimestamp(VURI resourceURI) {
        if (isSynchronizationLagRecognitionDisabled) {
            return;
        }

        long currentSynchroTimestamp;
        IFile resourceFile;
        synchronized (lastSynchronizationRequestTimestamps) {
            resourceFile = EMFBridge.getIFileForEMFUri(resourceURI.getEMFUri());
            currentSynchroTimestamp = resourceFile.getModificationStamp();
            lastSynchronizationRequestTimestamps.put(resourceURI, currentSynchroTimestamp);

        }
        LOGGER.trace("Setting synchronization timestamp for " + resourceFile + " to " + currentSynchroTimestamp);
    }

    /*
     * The internal change synchronizing is passed to the SynchronizingMonitoredEmfEditorImpl and
     * gets called when a resource is saved. It adds the changes to the collected changes that are
     * interpreted whenever triggerSynchronization is called here
     */
    @Override
    public void triggerSynchronisation(final VURI resourceURI) {
        if (!reportChanges) {
            return;
        }
        updateSynchronizationTimestamp(resourceURI);
        if (collectedChanges != null && !collectedChanges.isEmpty()) {
            LOGGER.trace("Got a change for " + resourceURI + ", continuing synchronization.");
            CompositeChange compositeChange = VitruviusChangeFactory.getInstance()
                    .createCompositeChange(collectedChanges);
            summaryChangeSynchronizing.synchronizeChange(compositeChange);
            this.collectedChanges.clear();
        }
    }

    /**
     * Calling this method timestamp-based recognition of synchronization lags, i.e. situations when
     * triggerSynchronization() is called for a file before all corresponding changes for that have
     * been applied to that file up to the point of (file modification) time of calling
     * triggerSynchronization().
     * 
     * This method should ONLY be called from single-threaded testing code.
     */
    protected void disableSynchronizationLagRecognition() {
        LOGGER.warn("Disabling the timestamp-based recognition of synchronization lag. This is only okay"
                + " in single-threaded testing environments.");
        isSynchronizationLagRecognitionDisabled = true;
    }

    @Override
    public void setReportChanges(boolean reportChanges) {
        this.reportChanges = reportChanges;
    }
}
