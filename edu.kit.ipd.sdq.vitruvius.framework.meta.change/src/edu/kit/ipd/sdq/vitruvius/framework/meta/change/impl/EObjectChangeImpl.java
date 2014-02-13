/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EObjectChangeImpl#getChangedEObject <em>Changed EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EObjectChangeImpl extends EChangeImpl implements EObjectChange {
	/**
	 * The cached value of the '{@link #getChangedEObject() <em>Changed EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangedEObject()
	 * @generated
	 * @ordered
	 */
	protected EObject changedEObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObjectChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangePackage.Literals.EOBJECT_CHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getChangedEObject() {
		if (changedEObject != null && changedEObject.eIsProxy()) {
			InternalEObject oldChangedEObject = (InternalEObject)changedEObject;
			changedEObject = eResolveProxy(oldChangedEObject);
			if (changedEObject != oldChangedEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.EOBJECT_CHANGE__CHANGED_EOBJECT, oldChangedEObject, changedEObject));
			}
		}
		return changedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetChangedEObject() {
		return changedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangedEObject(EObject newChangedEObject) {
		EObject oldChangedEObject = changedEObject;
		changedEObject = newChangedEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.EOBJECT_CHANGE__CHANGED_EOBJECT, oldChangedEObject, changedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChangePackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				if (resolve) return getChangedEObject();
				return basicGetChangedEObject();
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
			case ChangePackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				setChangedEObject((EObject)newValue);
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
			case ChangePackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				setChangedEObject((EObject)null);
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
			case ChangePackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				return changedEObject != null;
		}
		return super.eIsSet(featureID);
	}

} //EObjectChangeImpl
