package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

class EMFMonitorAdapter extends EContentAdapter {

    private static final Logger logger = Logger.getLogger(EMFMonitorAdapter.class);

    private final Map<VURI, List<Change>> changes;
    private final Map<Integer, Notification2ChangeHelper> notification2ChangeMap;

    public EMFMonitorAdapter() {
        super();
        this.changes = new HashMap<VURI, List<Change>>();
        this.notification2ChangeMap = new HashMap<Integer, Notification2ChangeHelper>();
        this.notification2ChangeMap.put(Notification.ADD, new Add2ChangeHelper());
        this.notification2ChangeMap.put(Notification.REMOVE, new Remove2ChangeHelper());
        this.notification2ChangeMap.put(Notification.SET, new Set2ChangeHelper());
        // TODO: add helper for remaining Notification changes

    }

    @Override
    public void notifyChanged(final Notification notification) {
        super.notifyChanged(notification);
        logger.debug("Notify changed called with notification: " + notification);
        if (!(notification.getNotifier() instanceof EObject)) {
            logger.warn("Notifier is not an instance of EObject - notify change does not work.");
            return;
        }
        if (!this.notification2ChangeMap.containsKey(notification.getEventType())) {
            logger.warn("No change helper for type: " + notification.getEventType() + " found. Notification "
                    + notification + " not synchronized.");
            return;
        }
        final EObject notifier = (EObject) notification.getNotifier();
        final VURI vuri = VURI.getInstance(notifier.eResource().getURI().toString());
        if (!this.changes.containsKey(vuri)) {
            this.changes.put(vuri, new Vector<Change>());
        }
        this.notification2ChangeMap.get(notification.getEventType()).createChangeFromNotification(notification,
                this.changes.get(vuri));

    }

    public Map<VURI, List<Change>> getChanges() {
        return this.changes;
    }

}
