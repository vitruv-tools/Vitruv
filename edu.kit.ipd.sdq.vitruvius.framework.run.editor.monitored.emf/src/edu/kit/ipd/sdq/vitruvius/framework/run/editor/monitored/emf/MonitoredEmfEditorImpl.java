package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.monitorededitor.AbstractMonitoredEditor;

public class MonitoredEmfEditorImpl extends AbstractMonitoredEditor implements ISelectionListener {

    private final Logger logger = Logger.getLogger(MonitoredEmfEditorImpl.class.getSimpleName());

    private final EContentAdapter contentAdapter;
    private final List<IResource> monitoredIResources;
    private final List<Change> changes;
    private static int INITIAL_CHANGE_LIST_SIZE = 128;

    public MonitoredEmfEditorImpl(final ChangeSynchronizing changeSynchronizing,
            final ModelCopyProviding modelCopyProviding) {
        super(changeSynchronizing, modelCopyProviding);
        this.changes = new Vector<Change>(INITIAL_CHANGE_LIST_SIZE);
        this.monitoredIResources = new Vector<IResource>();
        this.contentAdapter = new EContentAdapter() {
            @Override
            public void notifyChanged(final Notification notification) {
                super.notifyChanged(notification);
                MonitoredEmfEditorImpl.this.logger.info("Notify changed called with notification: " + notification);
                System.out.println("Notify changed called with notification: " + notification);
                // TODO: create change from notification and append it to change List
            }
        };
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
                if (null != rootObject && !rootObject.eAdapters().contains(this.contentAdapter)) {
                    rootObject.eAdapters().add(this.contentAdapter);
                }
            }
        }

    }

    private void initializeWorkbenchListener() {
        final MonitoredEmfEditorImpl monitor = this;
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
                        .addSelectionListener(monitor);
            }
        });
    }

    public void addMonitorToIResource(final IResource iResource) {
        this.monitoredIResources.add(iResource);
    }

    public void removeMonitorFromIResource(final IResource iResource) {
        this.monitoredIResources.remove(iResource);
    }
}
