/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EChangeImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.EObjectChangeImpl#getChangedEObject <em>Changed EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EObjectChangeImpl<T extends EObject> extends EChangeImpl implements EObjectChange<T> {
	/**
	 * The cached value of the '{@link #getChangedEObject() <em>Changed EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangedEObject()
	 * @generated
	 * @ordered
	 */
	protected T changedEObject;

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
		return ObjectPackage.Literals.EOBJECT_CHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public T getChangedEObject() {
		if (changedEObject != null && changedEObject.eIsProxy()) {
			InternalEObject oldChangedEObject = (InternalEObject)changedEObject;
			changedEObject = (T)eResolveProxy(oldChangedEObject);
			if (changedEObject != oldChangedEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ObjectPackage.EOBJECT_CHANGE__CHANGED_EOBJECT, oldChangedEObject, changedEObject));
			}
		}
		return changedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T basicGetChangedEObject() {
		return changedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangedEObject(T newChangedEObject) {
		T oldChangedEObject = changedEObject;
		changedEObject = newChangedEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ObjectPackage.EOBJECT_CHANGE__CHANGED_EOBJECT, oldChangedEObject, changedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ObjectPackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ObjectPackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				setChangedEObject((T)newValue);
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
			case ObjectPackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				setChangedEObject((T)null);
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
			case ObjectPackage.EOBJECT_CHANGE__CHANGED_EOBJECT:
				return changedEObject != null;
		}
		return super.eIsSet(featureID);
	}

} //EObjectChangeImpl
