/**
 */
package tools.vitruv.framework.change.interaction.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import tools.vitruv.framework.change.interaction.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.interaction.InteractionPackage
 * @generated
 */
public class InteractionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InteractionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractionSwitch() {
		if (modelPackage == null) {
			modelPackage = InteractionPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case InteractionPackage.FREE_TEXT_USER_INTERACTION: {
			FreeTextUserInteraction freeTextUserInteraction = (FreeTextUserInteraction) theEObject;
			T result = caseFreeTextUserInteraction(freeTextUserInteraction);
			if (result == null)
				result = caseUserInteractionBase(freeTextUserInteraction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION: {
			MultipleChoiceSingleSelectionUserInteraction multipleChoiceSingleSelectionUserInteraction = (MultipleChoiceSingleSelectionUserInteraction) theEObject;
			T result = caseMultipleChoiceSingleSelectionUserInteraction(multipleChoiceSingleSelectionUserInteraction);
			if (result == null)
				result = caseMultipleChoiceSelectionInteractionBase(multipleChoiceSingleSelectionUserInteraction);
			if (result == null)
				result = caseUserInteractionBase(multipleChoiceSingleSelectionUserInteraction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION: {
			MultipleChoiceMultiSelectionUserInteraction multipleChoiceMultiSelectionUserInteraction = (MultipleChoiceMultiSelectionUserInteraction) theEObject;
			T result = caseMultipleChoiceMultiSelectionUserInteraction(multipleChoiceMultiSelectionUserInteraction);
			if (result == null)
				result = caseMultipleChoiceSelectionInteractionBase(multipleChoiceMultiSelectionUserInteraction);
			if (result == null)
				result = caseUserInteractionBase(multipleChoiceMultiSelectionUserInteraction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case InteractionPackage.CONFIRMATION_USER_INTERACTION: {
			ConfirmationUserInteraction confirmationUserInteraction = (ConfirmationUserInteraction) theEObject;
			T result = caseConfirmationUserInteraction(confirmationUserInteraction);
			if (result == null)
				result = caseUserInteractionBase(confirmationUserInteraction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case InteractionPackage.USER_INTERACTION_BASE: {
			UserInteractionBase userInteractionBase = (UserInteractionBase) theEObject;
			T result = caseUserInteractionBase(userInteractionBase);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case InteractionPackage.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE: {
			MultipleChoiceSelectionInteractionBase multipleChoiceSelectionInteractionBase = (MultipleChoiceSelectionInteractionBase) theEObject;
			T result = caseMultipleChoiceSelectionInteractionBase(multipleChoiceSelectionInteractionBase);
			if (result == null)
				result = caseUserInteractionBase(multipleChoiceSelectionInteractionBase);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case InteractionPackage.NOTIFICATION_USER_INTERACTION: {
			NotificationUserInteraction notificationUserInteraction = (NotificationUserInteraction) theEObject;
			T result = caseNotificationUserInteraction(notificationUserInteraction);
			if (result == null)
				result = caseUserInteractionBase(notificationUserInteraction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Free Text User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Free Text User Interaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFreeTextUserInteraction(FreeTextUserInteraction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multiple Choice Single Selection User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multiple Choice Single Selection User Interaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultipleChoiceSingleSelectionUserInteraction(MultipleChoiceSingleSelectionUserInteraction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multiple Choice Multi Selection User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multiple Choice Multi Selection User Interaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultipleChoiceMultiSelectionUserInteraction(MultipleChoiceMultiSelectionUserInteraction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Confirmation User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Confirmation User Interaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfirmationUserInteraction(ConfirmationUserInteraction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>User Interaction Base</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User Interaction Base</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserInteractionBase(UserInteractionBase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multiple Choice Selection Interaction Base</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multiple Choice Selection Interaction Base</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultipleChoiceSelectionInteractionBase(MultipleChoiceSelectionInteractionBase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Notification User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Notification User Interaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNotificationUserInteraction(NotificationUserInteraction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //InteractionSwitch
