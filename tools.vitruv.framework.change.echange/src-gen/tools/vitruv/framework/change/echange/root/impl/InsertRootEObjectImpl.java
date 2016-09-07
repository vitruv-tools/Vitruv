/**
 */
package tools.vitruv.framework.change.echange.root.impl;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.echange.root.RootPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert Root EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl#isIsCreate <em>Is Create</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsertRootEObjectImpl<T extends EObject> extends RootEChangeImpl implements InsertRootEObject<T> {
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
    protected InsertRootEObjectImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return RootPackage.Literals.INSERT_ROOT_EOBJECT;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE, oldIsCreate, isCreate));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE:
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE:
				setNewValue((T)newValue);
				return;
			case RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE:
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE:
				setNewValue((T)null);
				return;
			case RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE:
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE:
				return newValue != null;
			case RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE:
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
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == AdditiveEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectAddedEChange.class) {
			switch (derivedFeatureID) {
				case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE: return EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE;
				case RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE: return EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == AdditiveEChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectAddedEChange.class) {
			switch (baseFeatureID) {
				case EChangePackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE: return RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE;
				case EChangePackage.EOBJECT_ADDED_ECHANGE__IS_CREATE: return RootPackage.INSERT_ROOT_EOBJECT__IS_CREATE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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

} //InsertRootEObjectImpl
