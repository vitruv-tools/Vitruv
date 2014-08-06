package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;

public class Add2ChangeHelper extends Notification2ChangeHelper {

    private static final Logger logger = Logger.getLogger(Add2ChangeHelper.class.getSimpleName());

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final CreateNonRootEObjectInList<EObject> createdObject = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectInList();
        createdObject.setNewValue((EObject) notification.getNewValue());
        // TODO use old value of affectedEObject as affectedEObject
        final EObject affectedEObject = (EObject) notification.getNotifier();
        createdObject.setNewAffectedEObject(affectedEObject);
        createdObject.setNewValue((EObject) notification.getNewValue());
        createdObject.setAffectedFeature((EReference) notification.getFeature());
        createdObject.setIndex(notification.getPosition());
        this.addChangeToList(changeList, createdObject, VURI.getInstance(affectedEObject.eResource()));
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        logger.warn("createChangeFromAttributeChangeNotification in Add2ChangeHelper called. "
                + "Check if the call is correct for notification " + notification);
        final UpdateSingleValuedEAttribute<EObject> updateAttribute = AttributeFactory.eINSTANCE
                .createUpdateSingleValuedEAttribute();
        final EObject notifier = (EObject) notification.getNotifier();
        updateAttribute.setNewAffectedEObject(notifier);
        updateAttribute.setAffectedFeature((EAttribute) notification.getFeature());
        updateAttribute.setNewValue((EObject) notification.getNewValue());

        this.addChangeToList(changeList, updateAttribute, VURI.getInstance(notifier.eResource()));
    }

}
