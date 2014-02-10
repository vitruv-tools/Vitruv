package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEReference;

public class Set2ChangeHelper extends Notification2ChangeHelper {

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEReference<EObject> updateEReference = ChangeFactory.eINSTANCE.createUpdateEReference();
        updateEReference.setChangedFeatureType((EObject) notification.getFeature());
        updateEReference.setNewValue((EObject) notification.getNewValue());
        this.addChangeToList(changeList, updateEReference);
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEAttribute<Object> updateAttribute = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        updateAttribute.setChangedFeatureType((EObject) notification.getFeature());
        updateAttribute.setNewValue(notification.getNewValue());
        this.addChangeToList(changeList, updateAttribute);
    }

}
