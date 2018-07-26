/**
 */
package tools.vitruv.framework.change.interaction.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.interaction.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.interaction.InteractionPackage
 * @generated
 */
public class InteractionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InteractionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = InteractionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InteractionSwitch<Adapter> modelSwitch = new InteractionSwitch<Adapter>() {
		@Override
		public Adapter caseFreeTextUserInteraction(FreeTextUserInteraction object) {
			return createFreeTextUserInteractionAdapter();
		}

		@Override
		public Adapter caseMultipleChoiceSingleSelectionUserInteraction(
				MultipleChoiceSingleSelectionUserInteraction object) {
			return createMultipleChoiceSingleSelectionUserInteractionAdapter();
		}

		@Override
		public Adapter caseMultipleChoiceMultiSelectionUserInteraction(
				MultipleChoiceMultiSelectionUserInteraction object) {
			return createMultipleChoiceMultiSelectionUserInteractionAdapter();
		}

		@Override
		public Adapter caseConfirmationUserInteraction(ConfirmationUserInteraction object) {
			return createConfirmationUserInteractionAdapter();
		}

		@Override
		public Adapter caseUserInteractionBase(UserInteractionBase object) {
			return createUserInteractionBaseAdapter();
		}

		@Override
		public Adapter caseMultipleChoiceSelectionInteractionBase(MultipleChoiceSelectionInteractionBase object) {
			return createMultipleChoiceSelectionInteractionBaseAdapter();
		}

		@Override
		public Adapter caseNotificationUserInteraction(NotificationUserInteraction object) {
			return createNotificationUserInteractionAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.FreeTextUserInteraction <em>Free Text User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.FreeTextUserInteraction
	 * @generated
	 */
	public Adapter createFreeTextUserInteractionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction <em>Multiple Choice Single Selection User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction
	 * @generated
	 */
	public Adapter createMultipleChoiceSingleSelectionUserInteractionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction <em>Multiple Choice Multi Selection User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction
	 * @generated
	 */
	public Adapter createMultipleChoiceMultiSelectionUserInteractionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.ConfirmationUserInteraction <em>Confirmation User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.ConfirmationUserInteraction
	 * @generated
	 */
	public Adapter createConfirmationUserInteractionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.UserInteractionBase <em>User Interaction Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.UserInteractionBase
	 * @generated
	 */
	public Adapter createUserInteractionBaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase <em>Multiple Choice Selection Interaction Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase
	 * @generated
	 */
	public Adapter createMultipleChoiceSelectionInteractionBaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.interaction.NotificationUserInteraction <em>Notification User Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.interaction.NotificationUserInteraction
	 * @generated
	 */
	public Adapter createNotificationUserInteractionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //InteractionAdapterFactory
