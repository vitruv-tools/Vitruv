/**
 */
package tools.vitruv.framework.change.interaction.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction;
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.InteractionPackage;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction;
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction;
import tools.vitruv.framework.change.interaction.NotificationUserInteraction;
import tools.vitruv.framework.change.interaction.UserInteractionBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InteractionPackageImpl extends EPackageImpl implements InteractionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass freeTextUserInteractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multipleChoiceSingleSelectionUserInteractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multipleChoiceMultiSelectionUserInteractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass confirmationUserInteractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userInteractionBaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multipleChoiceSelectionInteractionBaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass notificationUserInteractionEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see tools.vitruv.framework.change.interaction.InteractionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InteractionPackageImpl() {
		super(eNS_URI, InteractionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link InteractionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InteractionPackage init() {
		if (isInited)
			return (InteractionPackage) EPackage.Registry.INSTANCE.getEPackage(InteractionPackage.eNS_URI);

		// Obtain or create and register package
		InteractionPackageImpl theInteractionPackage = (InteractionPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof InteractionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new InteractionPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theInteractionPackage.createPackageContents();

		// Initialize created meta-data
		theInteractionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInteractionPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InteractionPackage.eNS_URI, theInteractionPackage);
		return theInteractionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFreeTextUserInteraction() {
		return freeTextUserInteractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFreeTextUserInteraction_Text() {
		return (EAttribute) freeTextUserInteractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultipleChoiceSingleSelectionUserInteraction() {
		return multipleChoiceSingleSelectionUserInteractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultipleChoiceSingleSelectionUserInteraction_SelectedIndex() {
		return (EAttribute) multipleChoiceSingleSelectionUserInteractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultipleChoiceMultiSelectionUserInteraction() {
		return multipleChoiceMultiSelectionUserInteractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultipleChoiceMultiSelectionUserInteraction_SelectedIndices() {
		return (EAttribute) multipleChoiceMultiSelectionUserInteractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfirmationUserInteraction() {
		return confirmationUserInteractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfirmationUserInteraction_Confirmed() {
		return (EAttribute) confirmationUserInteractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserInteractionBase() {
		return userInteractionBaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserInteractionBase_Message() {
		return (EAttribute) userInteractionBaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultipleChoiceSelectionInteractionBase() {
		return multipleChoiceSelectionInteractionBaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultipleChoiceSelectionInteractionBase_Choices() {
		return (EAttribute) multipleChoiceSelectionInteractionBaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNotificationUserInteraction() {
		return notificationUserInteractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractionFactory getInteractionFactory() {
		return (InteractionFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		freeTextUserInteractionEClass = createEClass(FREE_TEXT_USER_INTERACTION);
		createEAttribute(freeTextUserInteractionEClass, FREE_TEXT_USER_INTERACTION__TEXT);

		multipleChoiceSingleSelectionUserInteractionEClass = createEClass(
				MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION);
		createEAttribute(multipleChoiceSingleSelectionUserInteractionEClass,
				MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX);

		multipleChoiceMultiSelectionUserInteractionEClass = createEClass(
				MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION);
		createEAttribute(multipleChoiceMultiSelectionUserInteractionEClass,
				MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES);

		confirmationUserInteractionEClass = createEClass(CONFIRMATION_USER_INTERACTION);
		createEAttribute(confirmationUserInteractionEClass, CONFIRMATION_USER_INTERACTION__CONFIRMED);

		userInteractionBaseEClass = createEClass(USER_INTERACTION_BASE);
		createEAttribute(userInteractionBaseEClass, USER_INTERACTION_BASE__MESSAGE);

		multipleChoiceSelectionInteractionBaseEClass = createEClass(MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE);
		createEAttribute(multipleChoiceSelectionInteractionBaseEClass,
				MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES);

		notificationUserInteractionEClass = createEClass(NOTIFICATION_USER_INTERACTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		freeTextUserInteractionEClass.getESuperTypes().add(this.getUserInteractionBase());
		multipleChoiceSingleSelectionUserInteractionEClass.getESuperTypes()
				.add(this.getMultipleChoiceSelectionInteractionBase());
		multipleChoiceMultiSelectionUserInteractionEClass.getESuperTypes()
				.add(this.getMultipleChoiceSelectionInteractionBase());
		confirmationUserInteractionEClass.getESuperTypes().add(this.getUserInteractionBase());
		multipleChoiceSelectionInteractionBaseEClass.getESuperTypes().add(this.getUserInteractionBase());
		notificationUserInteractionEClass.getESuperTypes().add(this.getUserInteractionBase());

		// Initialize classes, features, and operations; add parameters
		initEClass(freeTextUserInteractionEClass, FreeTextUserInteraction.class, "FreeTextUserInteraction",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFreeTextUserInteraction_Text(), ecorePackage.getEString(), "text", null, 0, 1,
				FreeTextUserInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multipleChoiceSingleSelectionUserInteractionEClass,
				MultipleChoiceSingleSelectionUserInteraction.class, "MultipleChoiceSingleSelectionUserInteraction",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultipleChoiceSingleSelectionUserInteraction_SelectedIndex(), ecorePackage.getEInt(),
				"selectedIndex", null, 0, 1, MultipleChoiceSingleSelectionUserInteraction.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multipleChoiceMultiSelectionUserInteractionEClass, MultipleChoiceMultiSelectionUserInteraction.class,
				"MultipleChoiceMultiSelectionUserInteraction", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultipleChoiceMultiSelectionUserInteraction_SelectedIndices(), ecorePackage.getEInt(),
				"selectedIndices", null, 0, -1, MultipleChoiceMultiSelectionUserInteraction.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(confirmationUserInteractionEClass, ConfirmationUserInteraction.class, "ConfirmationUserInteraction",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfirmationUserInteraction_Confirmed(), ecorePackage.getEBoolean(), "confirmed", null, 0, 1,
				ConfirmationUserInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userInteractionBaseEClass, UserInteractionBase.class, "UserInteractionBase", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserInteractionBase_Message(), ecorePackage.getEString(), "message", null, 0, 1,
				UserInteractionBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(multipleChoiceSelectionInteractionBaseEClass, MultipleChoiceSelectionInteractionBase.class,
				"MultipleChoiceSelectionInteractionBase", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultipleChoiceSelectionInteractionBase_Choices(), ecorePackage.getEString(), "choices", null,
				0, -1, MultipleChoiceSelectionInteractionBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notificationUserInteractionEClass, NotificationUserInteraction.class, "NotificationUserInteraction",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //InteractionPackageImpl
