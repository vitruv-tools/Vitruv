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
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.list.impl.InsertInListEChangeImpl;

import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.InsertEReferenceImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.InsertEReferenceImpl#getNewValueID <em>New Value ID</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.InsertEReferenceImpl#isWasUnset <em>Was Unset</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsertEReferenceImpl<A extends EObject, T extends EObject> extends InsertInListEChangeImpl<A, EReference, T> implements InsertEReference<A, T> {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InsertEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.INSERT_EREFERENCE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.INSERT_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID, oldNewValueID, newValueID));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__WAS_UNSET, oldWasUnset, wasUnset));
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
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID:
				return getNewValueID();
			case ReferencePackage.INSERT_EREFERENCE__WAS_UNSET:
				return isWasUnset();
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
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
				setNewValue((T)newValue);
				return;
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID:
				setNewValueID((String)newValue);
				return;
			case ReferencePackage.INSERT_EREFERENCE__WAS_UNSET:
				setWasUnset((Boolean)newValue);
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
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
				setNewValue((T)null);
				return;
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID:
				setNewValueID(NEW_VALUE_ID_EDEFAULT);
				return;
			case ReferencePackage.INSERT_EREFERENCE__WAS_UNSET:
				setWasUnset(WAS_UNSET_EDEFAULT);
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
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
				return newValue != null;
			case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID:
				return NEW_VALUE_ID_EDEFAULT == null ? newValueID != null : !NEW_VALUE_ID_EDEFAULT.equals(newValueID);
			case ReferencePackage.INSERT_EREFERENCE__WAS_UNSET:
				return wasUnset != WAS_UNSET_EDEFAULT;
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
				case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE;
				case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID: return EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID;
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.INSERT_EREFERENCE__WAS_UNSET: return ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__WAS_UNSET;
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
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE: return ReferencePackage.INSERT_EREFERENCE__NEW_VALUE;
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE_ID: return ReferencePackage.INSERT_EREFERENCE__NEW_VALUE_ID;
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (baseFeatureID) {
				case ReferencePackage.ADDITIVE_REFERENCE_ECHANGE__WAS_UNSET: return ReferencePackage.INSERT_EREFERENCE__WAS_UNSET;
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
				case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT: return ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT;
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
			case ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT:
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
		result.append(')');
		return result.toString();
	}

} //InsertEReferenceImpl
