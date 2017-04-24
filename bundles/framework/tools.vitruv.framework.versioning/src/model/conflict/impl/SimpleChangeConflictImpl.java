/**
 */
package model.conflict.impl;

import model.conflict.ConflictPackage;
import model.conflict.SimpleChangeConflict;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link model.conflict.impl.SimpleChangeConflictImpl#getSourceChange <em>Source Change</em>}</li>
 *   <li>{@link model.conflict.impl.SimpleChangeConflictImpl#getTargetChange <em>Target Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleChangeConflictImpl extends ConflictImpl implements SimpleChangeConflict {
	/**
	 * The cached value of the '{@link #getSourceChange() <em>Source Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceChange()
	 * @generated
	 * @ordered
	 */
	protected EChange sourceChange;

	/**
	 * The cached value of the '{@link #getTargetChange() <em>Target Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetChange()
	 * @generated
	 * @ordered
	 */
	protected EChange targetChange;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleChangeConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConflictPackage.Literals.SIMPLE_CHANGE_CONFLICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange getSourceChange() {
		if (sourceChange != null && sourceChange.eIsProxy()) {
			InternalEObject oldSourceChange = (InternalEObject)sourceChange;
			sourceChange = (EChange)eResolveProxy(oldSourceChange);
			if (sourceChange != oldSourceChange) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE, oldSourceChange, sourceChange));
			}
		}
		return sourceChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange basicGetSourceChange() {
		return sourceChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceChange(EChange newSourceChange) {
		EChange oldSourceChange = sourceChange;
		sourceChange = newSourceChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE, oldSourceChange, sourceChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange getTargetChange() {
		if (targetChange != null && targetChange.eIsProxy()) {
			InternalEObject oldTargetChange = (InternalEObject)targetChange;
			targetChange = (EChange)eResolveProxy(oldTargetChange);
			if (targetChange != oldTargetChange) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE, oldTargetChange, targetChange));
			}
		}
		return targetChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange basicGetTargetChange() {
		return targetChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetChange(EChange newTargetChange) {
		EChange oldTargetChange = targetChange;
		targetChange = newTargetChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE, oldTargetChange, targetChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE:
				if (resolve) return getSourceChange();
				return basicGetSourceChange();
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE:
				if (resolve) return getTargetChange();
				return basicGetTargetChange();
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
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE:
				setSourceChange((EChange)newValue);
				return;
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE:
				setTargetChange((EChange)newValue);
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
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE:
				setSourceChange((EChange)null);
				return;
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE:
				setTargetChange((EChange)null);
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
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE:
				return sourceChange != null;
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE:
				return targetChange != null;
		}
		return super.eIsSet(featureID);
	}

} //SimpleChangeConflictImpl
