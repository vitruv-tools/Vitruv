/**
 */
package model.conflict.impl;

import model.conflict.Conflict;
import model.conflict.ConflictPackage;
import model.conflict.ConflictSolvability;
import model.conflict.ConflictType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link model.conflict.impl.ConflictImpl#getType <em>Type</em>}</li>
 *   <li>{@link model.conflict.impl.ConflictImpl#getSolvability <em>Solvability</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConflictImpl extends MinimalEObjectImpl.Container implements Conflict {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ConflictType TYPE_EDEFAULT = ConflictType.NAMING;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ConflictType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSolvability() <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolvability()
	 * @generated
	 * @ordered
	 */
	protected static final ConflictSolvability SOLVABILITY_EDEFAULT = ConflictSolvability.AUTOMATICALLY;

	/**
	 * The cached value of the '{@link #getSolvability() <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolvability()
	 * @generated
	 * @ordered
	 */
	protected ConflictSolvability solvability = SOLVABILITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConflictPackage.Literals.CONFLICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ConflictType newType) {
		ConflictType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictSolvability getSolvability() {
		return solvability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolvability(ConflictSolvability newSolvability) {
		ConflictSolvability oldSolvability = solvability;
		solvability = newSolvability == null ? SOLVABILITY_EDEFAULT : newSolvability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__SOLVABILITY, oldSolvability, solvability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConflictPackage.CONFLICT__TYPE:
				return getType();
			case ConflictPackage.CONFLICT__SOLVABILITY:
				return getSolvability();
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
			case ConflictPackage.CONFLICT__TYPE:
				setType((ConflictType)newValue);
				return;
			case ConflictPackage.CONFLICT__SOLVABILITY:
				setSolvability((ConflictSolvability)newValue);
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
			case ConflictPackage.CONFLICT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ConflictPackage.CONFLICT__SOLVABILITY:
				setSolvability(SOLVABILITY_EDEFAULT);
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
			case ConflictPackage.CONFLICT__TYPE:
				return type != TYPE_EDEFAULT;
			case ConflictPackage.CONFLICT__SOLVABILITY:
				return solvability != SOLVABILITY_EDEFAULT;
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
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", solvability: ");
		result.append(solvability);
		result.append(')');
		return result.toString();
	}

} //ConflictImpl
