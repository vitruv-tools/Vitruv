package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

abstract class Notification2ChangeHelper {

    private static final Logger logger = Logger.getLogger(Notification2ChangeHelper.class.getSimpleName());

    Notification2ChangeHelper() {
    }

    void createChangeFromNotification(final Notification notification, final List<Change> changeList) {
        if (notification.getFeature() instanceof EReference) {
            this.createChangeFromRefernceChangeNotification(notification, changeList);
        } else if (notification.getFeature() instanceof EAttribute) {
            this.createChangeFromAttributeChangeNotification(notification, changeList);
        } else {
            logger.warn("No change from notification " + notification
                    + " created: Can not create change from feature: " + notification.getFeature());
        }
    }

    abstract void createChangeFromRefernceChangeNotification(final Notification notification,
            final List<Change> changeList);

    abstract void createChangeFromAttributeChangeNotification(final Notification notification,
            final List<Change> changeList);

    protected void addChangeToList(final List<Change> changeList, final EChange eChange) {
        final EMFModelChange emfModelChange = new EMFModelChange(eChange);
        changeList.add(emfModelChange);
    }
}
