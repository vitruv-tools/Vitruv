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

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

import tools.vitruv.framework.change.echange.util.EChangeUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#getOldValue <em>Old Value</em>}</li>
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
		if (oldValue != null && ((EObject)oldValue).eIsProxy()) {
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
	public boolean isResolved() {
		return (super.isResolved() && (Objects.equal(this.getOldValue(), null) || (!this.getOldValue().eIsProxy())));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveApply(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveRevert(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet, final boolean applyChange) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolveApply = super.resolveApply(resourceSet);
			final RemoveEReference<A, T> resolvedChange = ((RemoveEReference<A, T>) _resolveApply);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			if (((!applyChange) && this.isContainment())) {
				resolvedChange.setOldValue(((T) EChangeUtil.objectInProgress));
			}
			else {
				T _oldValue = this.getOldValue();
				EObject _resolveProxy = EChangeUtil.resolveProxy(_oldValue, resourceSet);
				resolvedChange.setOldValue(((T) _resolveProxy));
			}
			if ((Objects.equal(resolvedChange.getOldValue(), null) || (!resolvedChange.getOldValue().eIsProxy()))) {
				if (((!applyChange) && this.isContainment())) {
					EChangeUtil.objectInProgress = null;
				}
				else {
					T _oldValue_1 = resolvedChange.getOldValue();
					EChangeUtil.objectInProgress = _oldValue_1;
				}
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
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
			case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
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
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
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
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (baseFeatureID) {
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE: return ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE;
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
				case EChangePackage.ECHANGE___IS_RESOLVED: return ReferencePackage.REMOVE_EREFERENCE___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE_APPLY__RESOURCESET: return ReferencePackage.REMOVE_EREFERENCE___RESOLVE_APPLY__RESOURCESET;
				case EChangePackage.ECHANGE___RESOLVE_REVERT__RESOURCESET: return ReferencePackage.REMOVE_EREFERENCE___RESOLVE_REVERT__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FeatureEChange.class) {
			switch (baseOperationID) {
				case FeaturePackage.FEATURE_ECHANGE___IS_RESOLVED: return ReferencePackage.REMOVE_EREFERENCE___IS_RESOLVED;
				case FeaturePackage.FEATURE_ECHANGE___RESOLVE_APPLY__RESOURCESET: return ReferencePackage.REMOVE_EREFERENCE___RESOLVE_APPLY__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == UpdateReferenceEChange.class) {
			switch (baseOperationID) {
				case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT: return ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT;
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
			case ReferencePackage.REMOVE_EREFERENCE___IS_RESOLVED:
				return isResolved();
			case ReferencePackage.REMOVE_EREFERENCE___RESOLVE_APPLY__RESOURCESET:
				return resolveApply((ResourceSet)arguments.get(0));
			case ReferencePackage.REMOVE_EREFERENCE___RESOLVE_REVERT__RESOURCESET:
				return resolveRevert((ResourceSet)arguments.get(0));
			case ReferencePackage.REMOVE_EREFERENCE___RESOLVE__RESOURCESET_BOOLEAN:
				return resolve((ResourceSet)arguments.get(0), (Boolean)arguments.get(1));
			case ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT:
				return isContainment();
		}
		return super.eInvoke(operationID, arguments);
	}

} //RemoveEReferenceImpl
