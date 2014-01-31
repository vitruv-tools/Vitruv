package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.monitorededitor.AbstractMonitoredEditor;

public class MonitoredEmfEditorImpl extends AbstractMonitoredEditor implements ISelectionListener {

    private final Logger logger = Logger.getLogger(MonitoredEmfEditorImpl.class.getSimpleName());

    private final EContentAdapter contentAdapter;
    private final List<IResource> monitoredIResources;
    private final Map<VURI, List<Change>> changes;

    public MonitoredEmfEditorImpl(final ChangeSynchronizing changeSynchronizing,
            final ModelCopyProviding modelCopyProviding) {
        super(changeSynchronizing, modelCopyProviding);
        this.changes = new HashMap<VURI, List<Change>>();
        this.monitoredIResources = new Vector<IResource>();
        this.contentAdapter = new EContentAdapter() {
            @Override
            public void notifyChanged(final Notification notification) {
                super.notifyChanged(notification);
                if (notification.getNotifier() instanceof EObject) {
                    MonitoredEmfEditorImpl.this.logger
                            .info("Notifier is not an instance of EObject - notify change does not work.");
                    return;
                }
                MonitoredEmfEditorImpl.this.logger.info("Notify changed called with notification: " + notification);
                System.out.println("Event Type: " + notification.getEventType());
                System.out.println("eNotification.getFeature(): " + notification.getFeature());
                System.out.println("NewValue: " + notification.getNewValue());
                System.out.println("OldValue: " + notification.getOldValue());
                System.out.println("Notify changed called with notification: " + notification);
                // TODO: create change from notification and append it to change List
                System.out.println("this.target:" + this.target);
                final EObject notifier = (EObject) notification.getNotifier();
                final Change change = new Change(notification.getOldValue(), notification.getNewValue());
                final VURI vuri = VURI.getInstance(notifier.eResource().getURI().toString());
                if (!MonitoredEmfEditorImpl.this.changes.containsKey(vuri)) {
                    MonitoredEmfEditorImpl.this.changes.put(vuri, new Vector<Change>());
                }
                MonitoredEmfEditorImpl.this.changes.get(vuri).add(change);
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
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
                        .addSelectionListener(MonitoredEmfEditorImpl.this);
            }
        });
    }

    public void triggerSynchronisation(final VURI vuri) {
        // TODO: implement method synchronizeChanges in changeSynchronizing?
        // TODO: what happens when new elements added to changes during synchronisation? Maybe use a
        // own list for synchronisation...
        if (!this.changes.containsKey(vuri)) {
            this.logger.info("No changes for key: " + vuri.getEMFUri().toString() + ".");
            return;
        }
        this.changeSynchronizing.synchronizeChanges(this.changes.get(vuri), vuri);
    }
}
