package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEContainmentReference;

public class Remove2ChangeHelper extends Notification2ChangeHelper {

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEContainmentReference<EObject> removeEContainmentReference = ChangeFactory.eINSTANCE
                .createUpdateEContainmentReference();
        removeEContainmentReference.setChangedFeatureType((EObject) notification.getFeature());
        removeEContainmentReference.setNewValue((EObject) notification.getNewValue());

        final DeleteNonRootEObject<EObject> deleteNonRootEObject = ChangeFactory.eINSTANCE.createDeleteNonRootEObject();
        deleteNonRootEObject.setChangedEObject((EObject) notification.getOldValue());
        deleteNonRootEObject.setNewValue(null);
        deleteNonRootEObject.setChangedFeatureType((EObject) notification.getFeature());

        this.addChangeToList(changeList, deleteNonRootEObject);
        this.addChangeToList(changeList, removeEContainmentReference);
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEAttribute<Object> removeEAttribute = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        removeEAttribute.setChangedFeatureType((EObject) notification.getFeature());
        removeEAttribute.setNewValue(notification.getNewValue());
        this.addChangeToList(changeList, removeEAttribute);
    }

}
