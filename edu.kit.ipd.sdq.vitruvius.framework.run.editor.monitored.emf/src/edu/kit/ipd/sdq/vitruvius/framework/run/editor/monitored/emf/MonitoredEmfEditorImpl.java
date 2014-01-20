package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.monitorededitor.AbstractMonitoredEditor;

public class MonitoredEmfEditorImpl extends AbstractMonitoredEditor {

    private final Logger logger = Logger.getLogger(MonitoredEmfEditorImpl.class.getSimpleName());

    private EContentAdapter contentAdapter;

    public MonitoredEmfEditorImpl(ChangeSynchronizing changeSynchronizing, ModelCopyProviding modelCopyProviding) {
        super(changeSynchronizing, modelCopyProviding);
        contentAdapter = new EContentAdapter() {
            public void notifyChanged(Notification notification) {
                super.notifyChanged(notification);
                logger.info("Notify changed called with notification: " + notification);
                // TODO: Start Change sync process via syncManaging:
                // changeSynchronizing.synchronizeChange(change, sourceModel);
            }
        };
    }

    public EContentAdapter getContentAdapter() {
        return contentAdapter;
    }

}
