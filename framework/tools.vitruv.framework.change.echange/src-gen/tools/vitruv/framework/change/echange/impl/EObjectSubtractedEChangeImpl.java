/**
 */
package tools.vitruv.framework.change.echange.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.EObjectSubtractedEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Subtracted EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.impl.EObjectSubtractedEChangeImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.impl.EObjectSubtractedEChangeImpl#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EObjectSubtractedEChangeImpl<T extends EObject> extends SubtractiveEChangeImpl<T> implements EObjectSubtractedEChange<T> {
    /**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
    protected T oldValue;

    /**
	 * The default value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isIsDelete()
	 * @generated
	 * @ordered
	 */
    protected static final boolean IS_DELETE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isIsDelete()
	 * @generated
	 * @ordered
	 */
    protected boolean isDelete = IS_DELETE_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EObjectSubtractedEChangeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return EChangePackage.Literals.EOBJECT_SUBTRACTED_ECHANGE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    public T getOldValue() {
		if (oldValue != null && oldValue.eIsProxy()) {
			InternalEObject oldOldValue = (InternalEObject)oldValue;
			oldValue = (T)eResolveProxy(oldOldValue);
			if (oldValue != oldOldValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE, oldOldValue, oldValue));
			}
		}
		return oldValue;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public T basicGetOldValue() {
		return oldValue;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOldValue(T newOldValue) {
		T oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE, oldOldValue, oldValue));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isIsDelete() {
		return isDelete;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIsDelete(boolean newIsDelete) {
		boolean oldIsDelete = isDelete;
		isDelete = newIsDelete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE, oldIsDelete, isDelete));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE:
				if (resolve) return getOldValue();
				return basicGetOldValue();
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE:
				return isIsDelete();
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
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE:
				setIsDelete((Boolean)newValue);
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
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE:
				setOldValue((T)null);
				return;
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE:
				setIsDelete(IS_DELETE_EDEFAULT);
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
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE:
				return oldValue != null;
			case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE:
				return isDelete != IS_DELETE_EDEFAULT;
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
		result.append(" (isDelete: ");
		result.append(isDelete);
		result.append(')');
		return result.toString();
	}

} //EObjectSubtractedEChangeImpl
