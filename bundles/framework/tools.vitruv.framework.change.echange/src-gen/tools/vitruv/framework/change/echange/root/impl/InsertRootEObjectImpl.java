/**
 */
package tools.vitruv.framework.change.echange.root.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.AdditiveEChange;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.echange.root.RootPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert Root EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl#getNewValueID <em>New Value ID</em>}</li>
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
	 * The default value of the '{@link #getNewValueID() <em>New Value ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValueID()
	 * @generated
	 * @ordered
	 */
	protected static final String NEW_VALUE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNewValueID() <em>New Value ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValueID()
	 * @generated
	 * @ordered
	 */
	protected String newValueID = NEW_VALUE_ID_EDEFAULT;

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
	public String getNewValueID() {
		return newValueID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValueID(String newNewValueID) {
		String oldNewValueID = newValueID;
		newValueID = newNewValueID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID, oldNewValueID, newValueID));
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID:
				return getNewValueID();
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID:
				setNewValueID((String)newValue);
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID:
				setNewValueID(NEW_VALUE_ID_EDEFAULT);
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
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID:
				return NEW_VALUE_ID_EDEFAULT == null ? newValueID != null : !NEW_VALUE_ID_EDEFAULT.equals(newValueID);
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
				case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE;
				case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID;
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
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE: return RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE;
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID: return RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE_ID;
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
		result.append(" (newValueID: ");
		result.append(newValueID);
		result.append(')');
		return result.toString();
	}

} //InsertRootEObjectImpl
