package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;

public class Remove2ChangeHelper extends Notification2ChangeHelper {

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final DeleteNonRootEObject<EObject> deleteNonRootEObject = ChangeFactory.eINSTANCE.createDeleteNonRootEObject();
        // set deleted EObject
        deleteNonRootEObject.setChangedEObject((EObject) notification.getOldValue());
        // set affected Reference
        deleteNonRootEObject.setAffectedEObject((EObject) notification.getNotifier());
        deleteNonRootEObject.setAffectedFeature((EReference) notification.getFeature());
        deleteNonRootEObject.setNewValue((EObject) notification.getNewValue());

        this.addChangeToList(changeList, deleteNonRootEObject);
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEAttribute<Object> removeEAttribute = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        removeEAttribute.setAffectedEObject((EObject) notification.getNotifier());
        removeEAttribute.setAffectedFeature((EAttribute) notification.getFeature());
        removeEAttribute.setNewValue(notification.getNewValue());
        this.addChangeToList(changeList, removeEAttribute);
    }

}
