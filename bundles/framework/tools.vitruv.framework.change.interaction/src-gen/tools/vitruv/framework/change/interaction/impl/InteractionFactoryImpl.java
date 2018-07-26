/**
 */
package tools.vitruv.framework.change.interaction.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.change.interaction.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InteractionFactoryImpl extends EFactoryImpl implements InteractionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InteractionFactory init() {
		try {
			InteractionFactory theInteractionFactory = (InteractionFactory) EPackage.Registry.INSTANCE
					.getEFactory(InteractionPackage.eNS_URI);
			if (theInteractionFactory != null) {
				return theInteractionFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InteractionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case InteractionPackage.FREE_TEXT_USER_INTERACTION:
			return createFreeTextUserInteraction();
		case InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION:
			return createMultipleChoiceSingleSelectionUserInteraction();
		case InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION:
			return createMultipleChoiceMultiSelectionUserInteraction();
		case InteractionPackage.CONFIRMATION_USER_INTERACTION:
			return createConfirmationUserInteraction();
		case InteractionPackage.NOTIFICATION_USER_INTERACTION:
			return createNotificationUserInteraction();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FreeTextUserInteraction createFreeTextUserInteraction() {
		FreeTextUserInteractionImpl freeTextUserInteraction = new FreeTextUserInteractionImpl();
		return freeTextUserInteraction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipleChoiceSingleSelectionUserInteraction createMultipleChoiceSingleSelectionUserInteraction() {
		MultipleChoiceSingleSelectionUserInteractionImpl multipleChoiceSingleSelectionUserInteraction = new MultipleChoiceSingleSelectionUserInteractionImpl();
		return multipleChoiceSingleSelectionUserInteraction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipleChoiceMultiSelectionUserInteraction createMultipleChoiceMultiSelectionUserInteraction() {
		MultipleChoiceMultiSelectionUserInteractionImpl multipleChoiceMultiSelectionUserInteraction = new MultipleChoiceMultiSelectionUserInteractionImpl();
		return multipleChoiceMultiSelectionUserInteraction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfirmationUserInteraction createConfirmationUserInteraction() {
		ConfirmationUserInteractionImpl confirmationUserInteraction = new ConfirmationUserInteractionImpl();
		return confirmationUserInteraction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationUserInteraction createNotificationUserInteraction() {
		NotificationUserInteractionImpl notificationUserInteraction = new NotificationUserInteractionImpl();
		return notificationUserInteraction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractionPackage getInteractionPackage() {
		return (InteractionPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InteractionPackage getPackage() {
		return InteractionPackage.eINSTANCE;
	}

} //InteractionFactoryImpl
