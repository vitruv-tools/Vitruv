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
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;

public class Set2ChangeHelper extends Notification2ChangeHelper {

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final CreateNonRootEObjectInList<EObject> setEReference = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectInList();
        final EObject affectedEObject = (EObject) notification.getNotifier();
        setEReference.setNewAffectedEObject(affectedEObject);
        setEReference.setAffectedFeature((EReference) notification.getFeature());
        setEReference.setNewValue((EObject) affectedEObject.eGet((EStructuralFeature) notification.getFeature()));
        setEReference.setIndex(notification.getPosition());
        this.addChangeToList(changeList, setEReference, VURI.getInstance(affectedEObject.eResource()));
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateSingleValuedEAttribute<Object> setAttribute = AttributeFactory.eINSTANCE
                .createUpdateSingleValuedEAttribute();
        final EObject notifier = (EObject) notification.getNotifier();
        setAttribute.setNewAffectedEObject(notifier);
        setAttribute.setAffectedFeature((EAttribute) notification.getFeature());
        setAttribute.setNewValue(notification.getNewValue());
        this.addChangeToList(changeList, setAttribute, VURI.getInstance(notifier.eResource()));
    }

}
