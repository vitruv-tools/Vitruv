/**
 */
package tools.vitruv.framework.change.interaction;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.interaction.InteractionPackage
 * @generated
 */
public interface InteractionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InteractionFactory eINSTANCE = tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Free Text User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Free Text User Interaction</em>'.
	 * @generated
	 */
	FreeTextUserInteraction createFreeTextUserInteraction();

	/**
	 * Returns a new object of class '<em>Multiple Choice Single Selection User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multiple Choice Single Selection User Interaction</em>'.
	 * @generated
	 */
	MultipleChoiceSingleSelectionUserInteraction createMultipleChoiceSingleSelectionUserInteraction();

	/**
	 * Returns a new object of class '<em>Multiple Choice Multi Selection User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multiple Choice Multi Selection User Interaction</em>'.
	 * @generated
	 */
	MultipleChoiceMultiSelectionUserInteraction createMultipleChoiceMultiSelectionUserInteraction();

	/**
	 * Returns a new object of class '<em>Confirmation User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Confirmation User Interaction</em>'.
	 * @generated
	 */
	ConfirmationUserInteraction createConfirmationUserInteraction();

	/**
	 * Returns a new object of class '<em>Notification User Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Notification User Interaction</em>'.
	 * @generated
	 */
	NotificationUserInteraction createNotificationUserInteraction();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	InteractionPackage getInteractionPackage();

} //InteractionFactory
