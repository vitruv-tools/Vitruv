package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorPart;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

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

    public void addEditorStateListener(IEditorStateListener listener);

    public interface IEditorStateListener {
        public enum EditorStateChange {
            MONITORING_STARTED, MONITORING_ENDED;
        }

        public void monitoringStateChanged(IEditorPart editor, Resource modelResource, EditorStateChange stateChange);
    }

    public interface ResourceChangeSynchronizing {
        void synchronizeChanges(List<Change> changes, VURI sourceModelURI, Resource changesOrigin);
    }
}
