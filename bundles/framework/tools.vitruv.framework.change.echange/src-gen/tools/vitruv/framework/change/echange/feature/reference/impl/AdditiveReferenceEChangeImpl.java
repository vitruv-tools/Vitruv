/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.AdditiveEChange;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Additive Reference EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.AdditiveReferenceEChangeImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.AdditiveReferenceEChangeImpl#getNewValueID <em>New Value ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AdditiveReferenceEChangeImpl<A extends EObject, T extends EObject> extends UpdateReferenceEChangeImpl<A> implements AdditiveReferenceEChange<A, T> {
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
	protected AdditiveReferenceEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.ADDITIVE_REFERENCE_ECHANGE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID, oldNewValueID, newValueID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID:
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
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE:
				setNewValue((T)newValue);
				return;
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID:
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
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE:
				setNewValue((T)null);
				return;
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID:
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
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE:
				return newValue != null;
			case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID:
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
				case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE;
				case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID;
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
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE: return ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE;
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID: return ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__NEW_VALUE_ID;
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

} //AdditiveReferenceEChangeImpl
