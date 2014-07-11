package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.monitorededitor.AbstractMonitoredEditor;

public class MonitoredEmfEditorImpl extends AbstractMonitoredEditor implements ISelectionListener {

    private final Logger logger = Logger.getLogger(MonitoredEmfEditorImpl.class.getSimpleName());

    private final EMFMonitorAdapter emfMonitorAdapter;

    public MonitoredEmfEditorImpl(final ChangeSynchronizing changeSynchronizing,
            final ModelCopyProviding modelCopyProviding) {
        super(changeSynchronizing, modelCopyProviding);

        this.emfMonitorAdapter = new EMFMonitorAdapter();
        this.initializeWorkbenchListener();
    }

    @Override
    public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
        this.logger.trace("IWorkbenchPart: " + part + " ISelection " + selection);
        if (selection instanceof StructuredSelection) {
            final StructuredSelection structuredSelection = (StructuredSelection) selection;
            if (structuredSelection.getFirstElement() instanceof EObject) {
                final EObject eObj = (EObject) structuredSelection.getFirstElement();
                final EObject rootObject = EcoreUtil.getRootContainer(eObj, true);
                if (null != rootObject && !rootObject.eAdapters().contains(this.emfMonitorAdapter)) {
                    rootObject.eAdapters().add(this.emfMonitorAdapter);
                }
            }
        }
    }

    private void initializeWorkbenchListener() {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
                        .addSelectionListener(MonitoredEmfEditorImpl.this);
            }
        });
    }

    public void triggerSynchronisation(final VURI vuri) {
        // TODO: what happens when new elements added to changes during synchronisation? Maybe use a
        // own list for synchronisation...
        if (!this.emfMonitorAdapter.getChanges().containsKey(vuri)) {
            this.logger.info("No changes for key: " + vuri.getEMFUri().toString() + ".");
            return;
        }
        final List<Change> changes = this.emfMonitorAdapter.getChanges().get(vuri);
        this.changeSynchronizing.synchronizeChanges(changes, vuri);
        this.emfMonitorAdapter.removeChanges(vuri);

    }
}
