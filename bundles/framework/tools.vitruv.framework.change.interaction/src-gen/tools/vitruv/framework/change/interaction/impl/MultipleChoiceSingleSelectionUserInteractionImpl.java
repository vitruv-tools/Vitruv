/**
 */
package tools.vitruv.framework.change.interaction.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.interaction.InteractionPackage;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multiple Choice Single Selection User Interaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.impl.MultipleChoiceSingleSelectionUserInteractionImpl#getSelectedIndex <em>Selected Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultipleChoiceSingleSelectionUserInteractionImpl extends MultipleChoiceSelectionInteractionBaseImpl
		implements MultipleChoiceSingleSelectionUserInteraction {
	/**
	 * The default value of the '{@link #getSelectedIndex() <em>Selected Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int SELECTED_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSelectedIndex() <em>Selected Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedIndex()
	 * @generated
	 * @ordered
	 */
	protected int selectedIndex = SELECTED_INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultipleChoiceSingleSelectionUserInteractionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InteractionPackage.Literals.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelectedIndex(int newSelectedIndex) {
		int oldSelectedIndex = selectedIndex;
		selectedIndex = newSelectedIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX,
					oldSelectedIndex, selectedIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX:
			return getSelectedIndex();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX:
			setSelectedIndex((Integer) newValue);
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
		case InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX:
			setSelectedIndex(SELECTED_INDEX_EDEFAULT);
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
		case InteractionPackage.MULTIPLE_CHOICE_SINGLE_SELECTION_USER_INTERACTION__SELECTED_INDEX:
			return selectedIndex != SELECTED_INDEX_EDEFAULT;
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
		result.append(" (selectedIndex: ");
		result.append(selectedIndex);
		result.append(')');
		return result.toString();
	}

} //MultipleChoiceSingleSelectionUserInteractionImpl
