package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;

public class Remove2ChangeHelper extends Notification2ChangeHelper {

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final DeleteNonRootEObjectInList<EObject> deleteNonRootEObject = ContainmentFactory.eINSTANCE
                .createDeleteNonRootEObjectInList();
        final EObject oldValue = (EObject) notification.getOldValue();
        // set deleted EObject
        deleteNonRootEObject.setOldValue(oldValue);
        // set affected Reference
        final EObject affectedEObject = (EObject) notification.getNotifier();
        deleteNonRootEObject.setNewAffectedEObject(affectedEObject);
        deleteNonRootEObject.setAffectedFeature((EReference) notification.getFeature());
        final Object newValue = affectedEObject.eGet((EStructuralFeature) notification.getFeature());
        deleteNonRootEObject.setOldValue((EObject) newValue);
        deleteNonRootEObject.setIndex(notification.getPosition());
        this.addChangeToList(changeList, deleteNonRootEObject, VURI.getInstance(oldValue.eResource()));
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        final RemoveEAttributeValue<Object> removeEAttribute = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
        final EObject notifier = (EObject) notification.getNotifier();
        removeEAttribute.setNewAffectedEObject(notifier);
        removeEAttribute.setAffectedFeature((EAttribute) notification.getFeature());
        removeEAttribute.setOldValue(notification.getNewValue());
        this.addChangeToList(changeList, removeEAttribute, VURI.getInstance(notifier.eResource()));
    }

}
