package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorPart;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.EditorNotMonitorableException;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IEditorPartAdapterFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IMonitoringDecider;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.ISynchronizingMonitoredEmfEditor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IVitruviusEMFEditorMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IEditorPartAdapterFactory.IEditorPartAdapter;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.ISynchronizingMonitoredEmfEditor.IEditorStateListener;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools.EclipseAdapterProvider;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools.IEclipseAdapter;

/**
 * A simple implementation of {@link IVitruviusEMFEditorMonitor} using an {@link IVitruviusAccessor}
 * object to determine whether a resource needs to be monitored.
 * 
 * Whenever the user saves the model, the changes since the last save event rsp. the beginning of
 * the editing session are passed to the provided {@link ChangeSynchronizing} object.
 * 
 * TODO: Logging.
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

    private final Map<VURI, BufferModel> bufferModels;

    private final ChangeSynchronizing summaryChangeSynchronizing;

    /** This object determines whether an adaptable IEditorPart is monitored. */
    private final IMonitoringDecider monitoringDecider = new IMonitoringDecider() {
        @Override
        public boolean isMonitoringEnabled(IEditorPartAdapter editor) {
            return isMonitoredVitruviusResource(editor.getMonitoredResource());
        }
    };

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
            ModelCopyProviding modelCopyProviding, IVitruviusAccessor vitruvAccessor) {
        ResourceChangeSynchronizing internalChangeSync = createInternalChangeSynchronizing();
        changeRecorderMonitor = new SynchronizingMonitoredEmfEditorImpl(internalChangeSync, factory, monitoringDecider);
        this.vitruvAccessor = vitruvAccessor;
        this.editorPartAdapterFactory = factory;
        this.bufferModels = new HashMap<>();
        this.summaryChangeSynchronizing = changeSync;
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
    public VitruviusEMFEditorMonitorImpl(ChangeSynchronizing changeSync, ModelCopyProviding modelCopyProviding,
            IVitruviusAccessor vitruvAccessor) {
        this(new DefaultEditorPartAdapterFactoryImpl(), changeSync, modelCopyProviding, vitruvAccessor);
    }

    private void appendChanges(List<Change> changes, VURI sourceModelURI, Resource changesOrigin) {
        if (!bufferModels.containsKey(sourceModelURI)) {
            throw new IllegalStateException("No buffer model exists for " + sourceModelURI);
        }
        bufferModels.get(sourceModelURI).incorporateChanges(changes, changesOrigin);
    }

    private ResourceChangeSynchronizing createInternalChangeSynchronizing() {
        return new ResourceChangeSynchronizing() {
            @Override
            public void synchronizeChanges(List<Change> changes, VURI sourceModelURI, Resource origin) {
                LOGGER.trace("Adding changes for VURI " + sourceModelURI);
                appendChanges(changes, sourceModelURI, origin);
            }
        };
    }

    private void onStartedMonitoring(IEditorPart editor, Resource modelResource) {
        VURI resourceURI = VURI.getInstance(modelResource);
        if (!bufferModels.containsKey(resourceURI)) {
            bufferModels.put(resourceURI, new BufferModel(modelResource.getURI()));
        }
    }

    private IEditorStateListener createBufferModelManagingEditorStateListener() {
        return new IEditorStateListener() {
            @Override
            public void monitoringStateChanged(IEditorPart editor, Resource modelResource, EditorStateChange stateChange) {
                if (stateChange == EditorStateChange.MONITORING_STARTED) {
                    onStartedMonitoring(editor, modelResource);
                }
            }
        };
    }

    @Override
    public void initialize() {
        changeRecorderMonitor.addEditorStateListener(createBufferModelManagingEditorStateListener());
        changeRecorderMonitor.initialize();
    }

    @Override
    public void dispose() {
        changeRecorderMonitor.dispose();
        for (BufferModel bm : bufferModels.values()) {
            bm.dispose();
        }
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

        if (bufferModels.containsKey(uri)) {
            BufferModel bm = bufferModels.remove(uri);
            bm.dispose();
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
                VURI partVURI = VURI.getInstance(partAdapter.getMonitoredResource());
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

    @Override
    public void triggerSynchronisation(final VURI resourceURI) {
        if (bufferModels.containsKey(resourceURI)) {
            LOGGER.trace("Got changes for " + resourceURI + ", continuing synchronization.");
            List<Change> changes = bufferModels.get(resourceURI).createBufferChangeSnapshot();
            summaryChangeSynchronizing.synchronizeChanges(changes, resourceURI);
        } else {
            LOGGER.trace("No changes for " + resourceURI + ", aborting synchronization.");
        }
    }
}
