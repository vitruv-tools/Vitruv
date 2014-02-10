package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEContainmentReference;

public class Add2ChangeHelper extends Notification2ChangeHelper {

    private static final Logger logger = Logger.getLogger(Add2ChangeHelper.class.getSimpleName());

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEContainmentReference<EObject> createEContainmentReference = ChangeFactory.eINSTANCE
                .createUpdateEContainmentReference();
        createEContainmentReference.setChangedFeatureType((EObject) notification.getFeature());
        createEContainmentReference.setNewValue((EObject) notification.getNewValue());

        final CreateNonRootEObject<EObject> createdObject = ChangeFactory.eINSTANCE.createCreateNonRootEObject();
        createdObject.setChangedEObject((EObject) notification.getNewValue());
        createdObject.setChangedFeatureType((EObject) notification.getFeature());
        createdObject.setNewValue((EObject) notification.getNewValue());

        this.addChangeToList(changeList, createdObject);
        this.addChangeToList(changeList, createEContainmentReference);
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        logger.warn("createChangeFromAttributeChangeNotification in Add2ChangeHelper called. "
                + "Check if the call is correct for notification " + notification);
        final UpdateEAttribute<Object> updateAttribute = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        updateAttribute.setChangedFeatureType((EObject) notification.getFeature());
        updateAttribute.setNewValue(notification.getNewValue());
        this.addChangeToList(changeList, updateAttribute);
    }

}
