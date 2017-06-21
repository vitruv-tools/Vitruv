/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.SimpleChangeConflict
import tools.vitruv.framework.versioning.ChangeMatch

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.SimpleChangeConflictImpl#getSourceChange <em>Source Change</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.SimpleChangeConflictImpl#getTargetChange <em>Target Change</em>}</li>
 * </ul>
 * @generated
 */
class SimpleChangeConflictImpl extends ConflictImpl implements SimpleChangeConflict {
	/** 
	 * The cached value of the '{@link #getSourceChange() <em>Source Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceChange()
	 * @generated
	 * @ordered
	 */
	protected ChangeMatch sourceChange
	/** 
	 * The cached value of the '{@link #getTargetChange() <em>Target Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetChange()
	 * @generated
	 * @ordered
	 */
	protected ChangeMatch targetChange

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
		return ConflictPackage.Literals.SIMPLE_CHANGE_CONFLICT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ChangeMatch getSourceChange() {
		if (sourceChange !== null && sourceChange.eIsProxy()) {
			var InternalEObject oldSourceChange = (sourceChange as InternalEObject)
			sourceChange = eResolveProxy(oldSourceChange) as EChange
			if (sourceChange !== oldSourceChange) {
				if (eNotificationRequired())
					eNotify(
						new ENotificationImpl(this, Notification.RESOLVE,
							ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE, oldSourceChange, sourceChange))
			}
		}
		return sourceChange
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def EChange basicGetSourceChange() {
		return sourceChange
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setSourceChange(EChange newSourceChange) {
		var EChange oldSourceChange = sourceChange
		sourceChange = newSourceChange
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification.SET, ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE,
					oldSourceChange, sourceChange))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EChange getTargetChange() {
		if (targetChange !== null && targetChange.eIsProxy()) {
			var InternalEObject oldTargetChange = (targetChange as InternalEObject)
			targetChange = eResolveProxy(oldTargetChange) as EChange
			if (targetChange !== oldTargetChange) {
				if (eNotificationRequired())
					eNotify(
						new ENotificationImpl(this, Notification.RESOLVE,
							ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE, oldTargetChange, targetChange))
			}
		}
		return targetChange
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def EChange basicGetTargetChange() {
		return targetChange
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setTargetChange(EChange newTargetChange) {
		var EChange oldTargetChange = targetChange
		targetChange = newTargetChange
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification.SET, ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE,
					oldTargetChange, targetChange))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE: {
				if (resolve) return getSourceChange()
				return basicGetSourceChange()
			}
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE: {
				if (resolve) return getTargetChange()
				return basicGetTargetChange()
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
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE: {
				setSourceChange((newValue as EChange))
				return
			}
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE: {
				setTargetChange((newValue as EChange))
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
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE: {
				setSourceChange((null as EChange))
				return
			}
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE: {
				setTargetChange((null as EChange))
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
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE: {
				return sourceChange !== null
			}
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE: {
				return targetChange !== null
			}
		}
		return super.eIsSet(featureID)
	} // SimpleChangeConflictImpl
}
