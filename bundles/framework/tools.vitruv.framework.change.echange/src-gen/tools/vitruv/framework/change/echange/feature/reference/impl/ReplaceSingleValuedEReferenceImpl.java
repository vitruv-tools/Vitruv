/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

import tools.vitruv.framework.change.echange.feature.single.impl.ReplaceSingleValuedFeatureEChangeImpl;

import tools.vitruv.framework.change.echange.util.EChangeUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getOldValue <em>Old Value</em>}</li>
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
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected T oldValue;

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
		if (newValue != null && ((EObject)newValue).eIsProxy()) {
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
	@SuppressWarnings("unchecked")
	public T getOldValue() {
		if (oldValue != null && ((EObject)oldValue).eIsProxy()) {
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
	public boolean isResolved() {
		return ((super.isResolved() && (Objects.equal(this.getOldValue(), null) || (!this.getOldValue().eIsProxy()))) && (Objects.equal(this.getNewValue(), null) || (!this.getNewValue().eIsProxy())));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveApply(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolveApply = super.resolveApply(resourceSet);
			final ReplaceSingleValuedEReference<A, T> resolvedChange = ((ReplaceSingleValuedEReference<A, T>) _resolveApply);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			T _oldValue = this.getOldValue();
			EObject _resolveProxy = EChangeUtil.resolveProxy(_oldValue, resourceSet);
			resolvedChange.setOldValue(((T) _resolveProxy));
			T _newValue = this.getNewValue();
			EObject _resolveProxy_1 = EChangeUtil.resolveProxy(_newValue, resourceSet);
			resolvedChange.setNewValue(((T) _resolveProxy_1));
			if (((Objects.equal(resolvedChange.getNewValue(), null) || (!resolvedChange.getNewValue().eIsProxy())) && (Objects.equal(resolvedChange.getOldValue(), null) || (!resolvedChange.getOldValue().eIsProxy())))) {
				return resolvedChange;
			}
		}
		return this;
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				if (resolve) return getOldValue();
				return basicGetOldValue();
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				setOldValue((T)newValue);
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				setOldValue((T)null);
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE:
				return oldValue != null;
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
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
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
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseFeatureID) {
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_VALUE;
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
		if (baseClass == EChange.class) {
			switch (baseOperationID) {
				case EChangePackage.ECHANGE___IS_RESOLVED: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE_APPLY__RESOURCESET: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE_APPLY__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FeatureEChange.class) {
			switch (baseOperationID) {
				case FeaturePackage.FEATURE_ECHANGE___IS_RESOLVED: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_RESOLVED;
				case FeaturePackage.FEATURE_ECHANGE___RESOLVE_APPLY__RESOURCESET: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE_APPLY__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_RESOLVED:
				return isResolved();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE_APPLY__RESOURCESET:
				return resolveApply((ResourceSet)arguments.get(0));
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_CONTAINMENT:
				return isContainment();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ReplaceSingleValuedEReferenceImpl
