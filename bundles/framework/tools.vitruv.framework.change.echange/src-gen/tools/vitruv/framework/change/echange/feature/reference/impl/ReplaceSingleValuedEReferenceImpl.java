/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

import tools.vitruv.framework.change.echange.feature.single.impl.ReplaceSingleValuedFeatureEChangeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getNewValueID <em>New Value ID</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#isWasUnset <em>Was Unset</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getOldValueID <em>Old Value ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceSingleValuedEReferenceImpl<A extends EObject, T extends EObject> extends ReplaceSingleValuedFeatureEChangeImpl<A, EReference, T> implements ReplaceSingleValuedEReference<A, T> {
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
	 * The default value of the '{@link #isWasUnset() <em>Was Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWasUnset()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WAS_UNSET_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWasUnset() <em>Was Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWasUnset()
	 * @generated
	 * @ordered
	 */
	protected boolean wasUnset = WAS_UNSET_EDEFAULT;

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
	protected ReplaceSingleValuedEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID, oldNewValueID, newValueID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWasUnset() {
		return wasUnset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWasUnset(boolean newWasUnset) {
		boolean oldWasUnset = wasUnset;
		wasUnset = newWasUnset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET, oldWasUnset, wasUnset));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID, oldOldValueID, oldValueID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isContainment() {
		EReference _affectedFeature = this.getAffectedFeature();
		return _affectedFeature.isContainment();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID:
				return getNewValueID();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET:
				return isWasUnset();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				if (resolve) return getOldValue();
				return basicGetOldValue();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID:
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
				setNewValue((T)newValue);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID:
				setNewValueID((String)newValue);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET:
				setWasUnset((Boolean)newValue);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID:
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
				setNewValue((T)null);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID:
				setNewValueID(NEW_VALUE_ID_EDEFAULT);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET:
				setWasUnset(WAS_UNSET_EDEFAULT);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				setOldValue((T)null);
				return;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID:
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
				return newValue != null;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID:
				return NEW_VALUE_ID_EDEFAULT == null ? newValueID != null : !NEW_VALUE_ID_EDEFAULT.equals(newValueID);
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET:
				return wasUnset != WAS_UNSET_EDEFAULT;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				return oldValue != null;
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID:
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
		if (baseClass == UpdateReferenceEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectAddedEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE;
				case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID;
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET: return ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__WAS_UNSET;
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
				case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE_ID;
				default: return -1;
			}
		}
		if (baseClass == SubtractiveReferenceEChange.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == UpdateReferenceEChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectAddedEChange.class) {
			switch (baseFeatureID) {
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE;
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE_ID;
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (baseFeatureID) {
				case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__WAS_UNSET: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__WAS_UNSET;
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseFeatureID) {
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE;
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE_ID: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE_ID;
				default: return -1;
			}
		}
		if (baseClass == SubtractiveReferenceEChange.class) {
			switch (baseFeatureID) {
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
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == UpdateReferenceEChange.class) {
			switch (baseOperationID) {
				case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_CONTAINMENT;
				default: return -1;
			}
		}
		if (baseClass == EObjectAddedEChange.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == SubtractiveReferenceEChange.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_CONTAINMENT:
				return isContainment();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(", wasUnset: ");
		result.append(wasUnset);
		result.append(", oldValueID: ");
		result.append(oldValueID);
		result.append(')');
		return result.toString();
	}

} //ReplaceSingleValuedEReferenceImpl
