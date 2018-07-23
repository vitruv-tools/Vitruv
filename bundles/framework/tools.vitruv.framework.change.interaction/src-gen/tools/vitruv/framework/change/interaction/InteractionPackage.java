/**
 */
package tools.vitruv.framework.change.interaction;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.interaction.InteractionFactory
 * @model kind="package"
 * @generated
 */
public interface InteractionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "interaction";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/Interaction/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "interaction";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InteractionPackage eINSTANCE = tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.UserInteractionBaseImpl <em>User Interaction Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.UserInteractionBaseImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getUserInteractionBase()
	 * @generated
	 */
	int USER_INTERACTION_BASE = 4;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_INTERACTION_BASE__MESSAGE = 0;

	/**
	 * The number of structural features of the '<em>User Interaction Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_INTERACTION_BASE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>User Interaction Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_INTERACTION_BASE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.FreeTextUserInteractionImpl <em>Free Text User Interaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.FreeTextUserInteractionImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getFreeTextUserInteraction()
	 * @generated
	 */
	int FREE_TEXT_USER_INTERACTION = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FREE_TEXT_USER_INTERACTION__MESSAGE = USER_INTERACTION_BASE__MESSAGE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FREE_TEXT_USER_INTERACTION__TEXT = USER_INTERACTION_BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Free Text User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FREE_TEXT_USER_INTERACTION_FEATURE_COUNT = USER_INTERACTION_BASE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Free Text User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FREE_TEXT_USER_INTERACTION_OPERATION_COUNT = USER_INTERACTION_BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceSelectionInteractionBaseImpl <em>Multiple Choice Selection Interaction Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.MultipleChoiceSelectionInteractionBaseImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getMultipleChoiceSelectionInteractionBase()
	 * @generated
	 */
	int MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE = 5;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__MESSAGE = USER_INTERACTION_BASE__MESSAGE;

	/**
	 * The feature id for the '<em><b>Choices</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES = USER_INTERACTION_BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Multiple Choice Selection Interaction Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_FEATURE_COUNT = USER_INTERACTION_BASE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Multiple Choice Selection Interaction Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_OPERATION_COUNT = USER_INTERACTION_BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceSingleSelectionUserInteractionImpl <em>Multiple Choice Single Selection User Interaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.MultipleChoiceSingleSelectionUserInteractionImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getMultipleChoiceSingleSelectionUserInteraction()
	 * @generated
	 */
	int MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION = 1;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__MESSAGE = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__MESSAGE;

	/**
	 * The feature id for the '<em><b>Choices</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__CHOICES = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES;

	/**
	 * The feature id for the '<em><b>Selected Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_FEATURE_COUNT
			+ 0;

	/**
	 * The number of structural features of the '<em>Multiple Choice Single Selection User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION_FEATURE_COUNT = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_FEATURE_COUNT
			+ 1;

	/**
	 * The number of operations of the '<em>Multiple Choice Single Selection User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION_OPERATION_COUNT = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceMultiSelectionUserInteractionImpl <em>Multiple Choice Multi Selection User Interaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.MultipleChoiceMultiSelectionUserInteractionImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getMultipleChoiceMultiSelectionUserInteraction()
	 * @generated
	 */
	int MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION = 2;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__MESSAGE = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__MESSAGE;

	/**
	 * The feature id for the '<em><b>Choices</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__CHOICES = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES;

	/**
	 * The feature id for the '<em><b>Selected Indices</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_FEATURE_COUNT
			+ 0;

	/**
	 * The number of structural features of the '<em>Multiple Choice Multi Selection User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION_FEATURE_COUNT = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_FEATURE_COUNT
			+ 1;

	/**
	 * The number of operations of the '<em>Multiple Choice Multi Selection User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION_OPERATION_COUNT = MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.ConfirmationUserInteractionImpl <em>Confirmation User Interaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.ConfirmationUserInteractionImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getConfirmationUserInteraction()
	 * @generated
	 */
	int CONFIRMATION_USER_INTERACTION = 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIRMATION_USER_INTERACTION__MESSAGE = USER_INTERACTION_BASE__MESSAGE;

	/**
	 * The feature id for the '<em><b>Confirmed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIRMATION_USER_INTERACTION__CONFIRMED = USER_INTERACTION_BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Confirmation User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIRMATION_USER_INTERACTION_FEATURE_COUNT = USER_INTERACTION_BASE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Confirmation User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIRMATION_USER_INTERACTION_OPERATION_COUNT = USER_INTERACTION_BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.interaction.impl.NotificationUserInteractionImpl <em>Notification User Interaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.interaction.impl.NotificationUserInteractionImpl
	 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getNotificationUserInteraction()
	 * @generated
	 */
	int NOTIFICATION_USER_INTERACTION = 6;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_USER_INTERACTION__MESSAGE = USER_INTERACTION_BASE__MESSAGE;

	/**
	 * The number of structural features of the '<em>Notification User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_USER_INTERACTION_FEATURE_COUNT = USER_INTERACTION_BASE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Notification User Interaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_USER_INTERACTION_OPERATION_COUNT = USER_INTERACTION_BASE_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.FreeTextUserInteraction <em>Free Text User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Free Text User Interaction</em>'.
	 * @see tools.vitruv.framework.change.interaction.FreeTextUserInteraction
	 * @generated
	 */
	EClass getFreeTextUserInteraction();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.interaction.FreeTextUserInteraction#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see tools.vitruv.framework.change.interaction.FreeTextUserInteraction#getText()
	 * @see #getFreeTextUserInteraction()
	 * @generated
	 */
	EAttribute getFreeTextUserInteraction_Text();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction <em>Multiple Choice Single Selection User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multiple Choice Single Selection User Interaction</em>'.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction
	 * @generated
	 */
	EClass getMultipleChoiceSingleSelectionUserInteraction();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction#getSelectedIndex <em>Selected Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Selected Index</em>'.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction#getSelectedIndex()
	 * @see #getMultipleChoiceSingleSelectionUserInteraction()
	 * @generated
	 */
	EAttribute getMultipleChoiceSingleSelectionUserInteraction_SelectedIndex();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction <em>Multiple Choice Multi Selection User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multiple Choice Multi Selection User Interaction</em>'.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction
	 * @generated
	 */
	EClass getMultipleChoiceMultiSelectionUserInteraction();

	/**
	 * Returns the meta object for the attribute list '{@link tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction#getSelectedIndices <em>Selected Indices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Selected Indices</em>'.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction#getSelectedIndices()
	 * @see #getMultipleChoiceMultiSelectionUserInteraction()
	 * @generated
	 */
	EAttribute getMultipleChoiceMultiSelectionUserInteraction_SelectedIndices();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.ConfirmationUserInteraction <em>Confirmation User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Confirmation User Interaction</em>'.
	 * @see tools.vitruv.framework.change.interaction.ConfirmationUserInteraction
	 * @generated
	 */
	EClass getConfirmationUserInteraction();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.interaction.ConfirmationUserInteraction#isConfirmed <em>Confirmed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Confirmed</em>'.
	 * @see tools.vitruv.framework.change.interaction.ConfirmationUserInteraction#isConfirmed()
	 * @see #getConfirmationUserInteraction()
	 * @generated
	 */
	EAttribute getConfirmationUserInteraction_Confirmed();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.UserInteractionBase <em>User Interaction Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Interaction Base</em>'.
	 * @see tools.vitruv.framework.change.interaction.UserInteractionBase
	 * @generated
	 */
	EClass getUserInteractionBase();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.interaction.UserInteractionBase#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see tools.vitruv.framework.change.interaction.UserInteractionBase#getMessage()
	 * @see #getUserInteractionBase()
	 * @generated
	 */
	EAttribute getUserInteractionBase_Message();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase <em>Multiple Choice Selection Interaction Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multiple Choice Selection Interaction Base</em>'.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase
	 * @generated
	 */
	EClass getMultipleChoiceSelectionInteractionBase();

	/**
	 * Returns the meta object for the attribute list '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase#getChoices <em>Choices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Choices</em>'.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase#getChoices()
	 * @see #getMultipleChoiceSelectionInteractionBase()
	 * @generated
	 */
	EAttribute getMultipleChoiceSelectionInteractionBase_Choices();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.interaction.NotificationUserInteraction <em>Notification User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notification User Interaction</em>'.
	 * @see tools.vitruv.framework.change.interaction.NotificationUserInteraction
	 * @generated
	 */
	EClass getNotificationUserInteraction();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InteractionFactory getInteractionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.FreeTextUserInteractionImpl <em>Free Text User Interaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.FreeTextUserInteractionImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getFreeTextUserInteraction()
		 * @generated
		 */
		EClass FREE_TEXT_USER_INTERACTION = eINSTANCE.getFreeTextUserInteraction();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FREE_TEXT_USER_INTERACTION__TEXT = eINSTANCE.getFreeTextUserInteraction_Text();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceSingleSelectionUserInteractionImpl <em>Multiple Choice Single Selection User Interaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.MultipleChoiceSingleSelectionUserInteractionImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getMultipleChoiceSingleSelectionUserInteraction()
		 * @generated
		 */
		EClass MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION = eINSTANCE
				.getMultipleChoiceSingleSelectionUserInteraction();

		/**
		 * The meta object literal for the '<em><b>Selected Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX = eINSTANCE
				.getMultipleChoiceSingleSelectionUserInteraction_SelectedIndex();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceMultiSelectionUserInteractionImpl <em>Multiple Choice Multi Selection User Interaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.MultipleChoiceMultiSelectionUserInteractionImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getMultipleChoiceMultiSelectionUserInteraction()
		 * @generated
		 */
		EClass MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION = eINSTANCE
				.getMultipleChoiceMultiSelectionUserInteraction();

		/**
		 * The meta object literal for the '<em><b>Selected Indices</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES = eINSTANCE
				.getMultipleChoiceMultiSelectionUserInteraction_SelectedIndices();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.ConfirmationUserInteractionImpl <em>Confirmation User Interaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.ConfirmationUserInteractionImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getConfirmationUserInteraction()
		 * @generated
		 */
		EClass CONFIRMATION_USER_INTERACTION = eINSTANCE.getConfirmationUserInteraction();

		/**
		 * The meta object literal for the '<em><b>Confirmed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIRMATION_USER_INTERACTION__CONFIRMED = eINSTANCE.getConfirmationUserInteraction_Confirmed();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.UserInteractionBaseImpl <em>User Interaction Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.UserInteractionBaseImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getUserInteractionBase()
		 * @generated
		 */
		EClass USER_INTERACTION_BASE = eINSTANCE.getUserInteractionBase();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_INTERACTION_BASE__MESSAGE = eINSTANCE.getUserInteractionBase_Message();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceSelectionInteractionBaseImpl <em>Multiple Choice Selection Interaction Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.MultipleChoiceSelectionInteractionBaseImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getMultipleChoiceSelectionInteractionBase()
		 * @generated
		 */
		EClass MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE = eINSTANCE.getMultipleChoiceSelectionInteractionBase();

		/**
		 * The meta object literal for the '<em><b>Choices</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES = eINSTANCE
				.getMultipleChoiceSelectionInteractionBase_Choices();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.interaction.impl.NotificationUserInteractionImpl <em>Notification User Interaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.interaction.impl.NotificationUserInteractionImpl
		 * @see tools.vitruv.framework.change.interaction.impl.InteractionPackageImpl#getNotificationUserInteraction()
		 * @generated
		 */
		EClass NOTIFICATION_USER_INTERACTION = eINSTANCE.getNotificationUserInteraction();

	}

} //InteractionPackage
