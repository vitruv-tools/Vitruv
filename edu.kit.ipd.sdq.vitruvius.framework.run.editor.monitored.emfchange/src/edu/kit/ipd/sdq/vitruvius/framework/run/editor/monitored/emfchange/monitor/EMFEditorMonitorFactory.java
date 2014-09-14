package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IEditorPartAdapterFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IVitruviusEMFEditorMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IVitruviusEMFEditorMonitor.IVitruviusAccessor;

/**
 * A factory for classes residing in this package.
 */
public class EMFEditorMonitorFactory {

    /**
     * Creates a new {@link IVitruviusEMFEditorMonitor} instance.
     * 
     * @param factory
     *            The {@link IEditorPartAdapterFactory} used to adapt Eclipse editors.
     * @param changeSync
     *            The Vitruvius {@link ChangeSynchronizing} object receiving change synchronization
     *            calls.
     * @param modelCopyProviding
     *            The Vitruvius model copy provider.
     * @param vitruvAccessor
     *            An {@link IVitruviusAccessor} object providing access to Vitruvius facilities.
     * @return A new {@link IVitruviusEMFEditorMonitor} object.
     */
    public IVitruviusEMFEditorMonitor createVitruviusModelEditorSyncMgr(IEditorPartAdapterFactory factory,
            ChangeSynchronizing changeSync, ModelCopyProviding modelCopyProviding, IVitruviusAccessor vitruvAccessor) {
        return new VitruviusEMFEditorMonitorImpl(factory, changeSync, modelCopyProviding, vitruvAccessor);
    }

    /**
     * Creates a new {@link IVitruviusEMFEditorMonitor} instance able to adapt all EMF/GMF editors.
     * 
     * @param changeSync
     *            The Vitruvius {@link ChangeSynchronizing} object receiving change synchronization
     *            calls.
     * @param modelCopyProviding
     *            The Vitruvius model copy provider.
     * @param vitruvAccessor
     *            An {@link IVitruviusAccessor} object providing access to Vitruvius facilities.
     * @return A new {@link IVitruviusEMFEditorMonitor} object.
     */
    public IVitruviusEMFEditorMonitor createVitruviusModelEditorSyncMgr(ChangeSynchronizing changeSync,
            ModelCopyProviding modelCopyProviding, IVitruviusAccessor vitruvAccessor) {
        return new VitruviusEMFEditorMonitorImpl(changeSync, modelCopyProviding, vitruvAccessor);
    }

    public IEditorPartAdapterFactory createDefaultEditorPartAdapterFactory(Collection<String> acceptedFileSuffixes) {
        return new DefaultEditorPartAdapterFactoryImpl(acceptedFileSuffixes);
    }

    public IEditorPartAdapterFactory createDefaultEditorPartAdapterFactory() {
        return new DefaultEditorPartAdapterFactoryImpl();
    }
}
