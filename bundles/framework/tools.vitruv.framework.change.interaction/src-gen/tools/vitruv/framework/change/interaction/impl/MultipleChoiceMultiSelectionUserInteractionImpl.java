/**
 */
package tools.vitruv.framework.change.interaction.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import tools.vitruv.framework.change.interaction.InteractionPackage;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multiple Choice Multi Selection User Interaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceMultiSelectionUserInteractionImpl#getSelectedIndices <em>Selected Indices</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultipleChoiceMultiSelectionUserInteractionImpl extends MultipleChoiceSelectionInteractionBaseImpl
		implements MultipleChoiceMultiSelectionUserInteraction {
	/**
	 * The cached value of the '{@link #getSelectedIndices() <em>Selected Indices</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedIndices()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> selectedIndices;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultipleChoiceMultiSelectionUserInteractionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InteractionPackage.Literals.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getSelectedIndices() {
		if (selectedIndices == null) {
			selectedIndices = new EDataTypeUniqueEList<Integer>(Integer.class, this,
					InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES);
		}
		return selectedIndices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES:
			return getSelectedIndices();
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
		case InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES:
			getSelectedIndices().clear();
			getSelectedIndices().addAll((Collection<? extends Integer>) newValue);
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
		case InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES:
			getSelectedIndices().clear();
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
		case InteractionPackage.MULTIPLE_CHOICE_MULTI_SELECTION_USER_INTERACTION__SELECTED_INDICES:
			return selectedIndices != null && !selectedIndices.isEmpty();
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
		result.append(" (selectedIndices: ");
		result.append(selectedIndices);
		result.append(')');
		return result.toString();
	}

} //MultipleChoiceMultiSelectionUserInteractionImpl
