/**
 */
package tools.vitruv.framework.change.echange.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.EObjectAddedEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Added EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.impl.EObjectAddedEChangeImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.impl.EObjectAddedEChangeImpl#isIsCreate <em>Is Create</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EObjectAddedEChangeImpl<T extends EObject> extends AdditiveEChangeImpl<T> implements EObjectAddedEChange<T> {
    /**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
    protected T newValue;

    /**
	 * The default value of the '{@link #isIsCreate() <em>Is Create</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isIsCreate()
	 * @generated
	 * @ordered
	 */
    protected static final boolean IS_CREATE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isIsCreate() <em>Is Create</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isIsCreate()
	 * @generated
	 * @ordered
	 */
    protected boolean isCreate = IS_CREATE_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EObjectAddedEChangeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return EChangePackage.Literals.EOBJECT_ADDED_ECHANGE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    public T getNewValue() {
		if (newValue != null && newValue.eIsProxy()) {
			InternalEObject oldNewValue = (InternalEObject)newValue;
			newValue = (T)eResolveProxy(oldNewValue);
			if (newValue != oldNewValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE, oldNewValue, newValue));
			}
		}
		return newValue;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public T basicGetNewValue() {
		return newValue;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setNewValue(T newNewValue) {
		T oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE, oldNewValue, newValue));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isIsCreate() {
		return isCreate;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIsCreate(boolean newIsCreate) {
		boolean oldIsCreate = isCreate;
		isCreate = newIsCreate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE, oldIsCreate, isCreate));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE:
				return isIsCreate();
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
			case EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE:
				setNewValue((T)newValue);
				return;
			case EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE:
				setIsCreate((Boolean)newValue);
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
			case EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE:
				setNewValue((T)null);
				return;
			case EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE:
				setIsCreate(IS_CREATE_EDEFAULT);
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
			case EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE:
				return newValue != null;
			case EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE:
				return isCreate != IS_CREATE_EDEFAULT;
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
		result.append(" (isCreate: ");
		result.append(isCreate);
		result.append(')');
		return result.toString();
	}

} //EObjectAddedEChangeImpl
