/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceEObjectImpl#getNewEObject <em>New EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ReplaceEObjectImpl<T extends EObject> extends EObjectChangeImpl<T> implements ReplaceEObject<T> {
	/**
	 * The cached value of the '{@link #getNewEObject() <em>New EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewEObject()
	 * @generated
	 * @ordered
	 */
	protected T newEObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReplaceEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ObjectPackage.Literals.REPLACE_EOBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public T getNewEObject() {
		if (newEObject != null && newEObject.eIsProxy()) {
			InternalEObject oldNewEObject = (InternalEObject)newEObject;
			newEObject = (T)eResolveProxy(oldNewEObject);
			if (newEObject != oldNewEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ObjectPackage.REPLACE_EOBJECT__NEW_EOBJECT, oldNewEObject, newEObject));
			}
		}
		return newEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T basicGetNewEObject() {
		return newEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewEObject(T newNewEObject) {
		T oldNewEObject = newEObject;
		newEObject = newNewEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ObjectPackage.REPLACE_EOBJECT__NEW_EOBJECT, oldNewEObject, newEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ObjectPackage.REPLACE_EOBJECT__NEW_EOBJECT:
				if (resolve) return getNewEObject();
				return basicGetNewEObject();
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
			case ObjectPackage.REPLACE_EOBJECT__NEW_EOBJECT:
				setNewEObject((T)newValue);
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
			case ObjectPackage.REPLACE_EOBJECT__NEW_EOBJECT:
				setNewEObject((T)null);
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
			case ObjectPackage.REPLACE_EOBJECT__NEW_EOBJECT:
				return newEObject != null;
		}
		return super.eIsSet(featureID);
	}

} //ReplaceEObjectImpl
