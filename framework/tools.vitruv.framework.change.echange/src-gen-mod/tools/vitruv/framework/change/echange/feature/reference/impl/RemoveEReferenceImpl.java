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

import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.EObjectSubtractedEChange;

import tools.vitruv.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RemoveEReferenceImpl<A extends EObject, T extends EObject> extends RemoveFromListEChangeImpl<A, EReference, T> implements RemoveEReference<A, T> {
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
	protected RemoveEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.REMOVE_EREFERENCE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__IS_DELETE, oldIsDelete, isDelete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isContainment() {
		return getAffectedFeature().isContainment();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
				if (resolve) return getOldValue();
				return basicGetOldValue();
			case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
				setOldValue((T)null);
				return;
			case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
				return oldValue != null;
			case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE: return EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
				case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE: return EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE;
				default: return -1;
			}
		}
		if (baseClass == UpdateReferenceEChange.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseFeatureID) {
				case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE: return ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE;
				case EChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE: return ReferencePackage.REMOVE_EREFERENCE__IS_DELETE;
				default: return -1;
			}
		}
		if (baseClass == UpdateReferenceEChange.class) {
			switch (baseFeatureID) {
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
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateReferenceEChange.class) {
			switch (baseOperationID) {
				case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT: return ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT;
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
			case ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT:
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
		result.append(" (isDelete: ");
		result.append(isDelete);
		result.append(')');
		return result.toString();
	}

} //RemoveEReferenceImpl
