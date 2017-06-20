/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.ConflictSolvability
import tools.vitruv.framework.versioning.conflict.ConflictType

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl#getType <em>Type</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl#getSolvability <em>Solvability</em>}</li>
 * </ul>
 * @generated
 */
abstract class ConflictImpl extends MinimalEObjectImpl.Container implements Conflict {
	/** 
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ConflictType TYPE_EDEFAULT = ConflictType.NAMING
	/** 
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ConflictType type = TYPE_EDEFAULT
	/** 
	 * The default value of the '{@link #getSolvability() <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolvability()
	 * @generated
	 * @ordered
	 */
	protected static final ConflictSolvability SOLVABILITY_EDEFAULT = ConflictSolvability.AUTOMATICALLY
	/** 
	 * The cached value of the '{@link #getSolvability() <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolvability()
	 * @generated
	 * @ordered
	 */
	protected ConflictSolvability solvability = SOLVABILITY_EDEFAULT

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return ConflictPackage.Literals.CONFLICT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictType getType() {
		return type
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setType(ConflictType newType) {
		var ConflictType oldType = type
		type = if (newType === null) TYPE_EDEFAULT else newType
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__TYPE, oldType, type))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictSolvability getSolvability() {
		return solvability
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setSolvability(ConflictSolvability newSolvability) {
		var ConflictSolvability oldSolvability = solvability
		solvability = if (newSolvability === null) SOLVABILITY_EDEFAULT else newSolvability
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__SOLVABILITY, oldSolvability,
				solvability))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case ConflictPackage.CONFLICT__TYPE: {
				return getType()
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				return getSolvability()
			}
		}
		return super.eGet(featureID, resolve, coreType)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eSet(int featureID, Object newValue) {

		switch (featureID) {
			case ConflictPackage.CONFLICT__TYPE: {
				setType((newValue as ConflictType))
				return
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				setSolvability((newValue as ConflictSolvability))
				return
			}
		}
		super.eSet(featureID, newValue)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eUnset(int featureID) {

		switch (featureID) {
			case ConflictPackage.CONFLICT__TYPE: {
				setType(TYPE_EDEFAULT)
				return
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				setSolvability(SOLVABILITY_EDEFAULT)
				return
			}
		}
		super.eUnset(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override boolean eIsSet(int featureID) {

		switch (featureID) {
			case ConflictPackage.CONFLICT__TYPE: {
				return type !== TYPE_EDEFAULT
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				return solvability !== SOLVABILITY_EDEFAULT
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String toString() {
		if (eIsProxy()) return super.toString()
		var StringBuffer result = new StringBuffer(super.toString())
		result.append(" (type: ")
		result.append(type)
		result.append(", solvability: ")
		result.append(solvability)
		result.append(Character.valueOf(')').charValue)
		return result.toString()
	} // ConflictImpl
}
