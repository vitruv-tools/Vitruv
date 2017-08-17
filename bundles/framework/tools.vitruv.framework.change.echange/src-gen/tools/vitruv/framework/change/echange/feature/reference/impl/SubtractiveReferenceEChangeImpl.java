/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subtractive Reference EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.SubtractiveReferenceEChangeImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.SubtractiveReferenceEChangeImpl#getOldValueID <em>Old Value ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SubtractiveReferenceEChangeImpl<A extends EObject, T extends EObject> extends UpdateReferenceEChangeImpl<A> implements SubtractiveReferenceEChange<A, T> {
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
	 * The default value of the '{@link #getOldValueID() <em>Old Value ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValueID()
	 * @generated
	 * @ordered
	 */
	protected static final String OLD_VALUE_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOldValueID() <em>Old Value ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValueID()
	 * @generated
	 * @ordered
	 */
	protected String oldValueID = OLD_VALUE_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubtractiveReferenceEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.SUBTRACTIVE_REFERENCE_ECHANGE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE, oldOldValue, oldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOldValueID() {
		return oldValueID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValueID(String newOldValueID) {
		String oldOldValueID = oldValueID;
		oldValueID = newOldValueID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID, oldOldValueID, oldValueID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE:
				if (resolve) return getOldValue();
				return basicGetOldValue();
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID:
				return getOldValueID();
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
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID:
				setOldValueID((String)newValue);
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
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE:
				setOldValue((T)null);
				return;
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID:
				setOldValueID(OLD_VALUE_ID_EDEFAULT);
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
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE:
				return oldValue != null;
			case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID:
				return OLD_VALUE_ID_EDEFAULT == null ? oldValueID != null : !OLD_VALUE_ID_EDEFAULT.equals(oldValueID);
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
		if (baseClass == SubtractiveEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
				case ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE_ID;
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
		if (baseClass == SubtractiveEChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseFeatureID) {
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE: return ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE;
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE_ID: return ReferencePackage.SUBTRACTIVE_REFERENCE_ECHANGE__OLD_VALUE_ID;
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
		result.append(" (oldValueID: ");
		result.append(oldValueID);
		result.append(')');
		return result.toString();
	}

} //SubtractiveReferenceEChangeImpl
