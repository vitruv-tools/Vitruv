/**
 */
package tools.vitruv.framework.change.interaction.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.change.interaction.InteractionPackage;
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multiple Choice Selection Interaction Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceSelectionInteractionBaseImpl#getChoices <em>Choices</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MultipleChoiceSelectionInteractionBaseImpl extends UserInteractionBaseImpl
		implements MultipleChoiceSelectionInteractionBase {
	/**
	 * The cached value of the '{@link #getChoices() <em>Choices</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChoices()
	 * @generated
	 * @ordered
	 */
	protected EList<String> choices;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultipleChoiceSelectionInteractionBaseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InteractionPackage.Literals.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getChoices() {
		if (choices == null) {
			choices = new EDataTypeUniqueEList.Unsettable<String>(String.class, this,
					InteractionPackage.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES);
		}
		return choices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetChoices() {
		if (choices != null)
			((InternalEList.Unsettable<?>) choices).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetChoices() {
		return choices != null && ((InternalEList.Unsettable<?>) choices).isSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES:
			return getChoices();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES:
			getChoices().clear();
			getChoices().addAll((Collection<? extends String>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES:
			unsetChoices();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_SELECTION_INTERACTION_BASE__CHOICES:
			return isSetChoices();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (choices: ");
		result.append(choices);
		result.append(')');
		return result.toString();
	}

} //MultipleChoiceSelectionInteractionBaseImpl
