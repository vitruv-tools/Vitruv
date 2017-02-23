/**
 */
package tools.vitruv.framework.change.echange.root.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.root.RemoveRootEObject;
import tools.vitruv.framework.change.echange.root.RootPackage;

import tools.vitruv.framework.change.echange.util.EChangeUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove Root EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl#getOldValue <em>Old Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RemoveRootEObjectImpl<T extends EObject> extends RootEChangeImpl implements RemoveRootEObject<T> {
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
	protected RemoveRootEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RootPackage.Literals.REMOVE_ROOT_EOBJECT;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE, oldOldValue, oldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResolved() {
		T _oldValue = this.getOldValue();
		boolean _eIsProxy = _oldValue.eIsProxy();
		return (!_eIsProxy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet, final T oldRootObject) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolve = super.resolve(resourceSet);
			final RemoveRootEObject<T> resolvedChange = ((RemoveRootEObject<T>) _resolve);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			boolean _equals_1 = Objects.equal(oldRootObject, null);
			if (_equals_1) {
				T _oldValue = this.getOldValue();
				EObject _resolveProxy = EChangeUtil.resolveProxy(_oldValue, resourceSet);
				resolvedChange.setOldValue(((T) _resolveProxy));
			}
			else {
				resolvedChange.setOldValue(oldRootObject);
			}
			URI _uri = this.getUri();
			Resource _resource = resourceSet.getResource(_uri, false);
			resolvedChange.setResource(_resource);
			if ((((!Objects.equal(resolvedChange.getOldValue(), null)) && (!resolvedChange.getOldValue().eIsProxy())) && (!Objects.equal(resolvedChange.getResource(), null)))) {
				return resolvedChange;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE:
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
			case RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE:
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
			case RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE:
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
			case RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE:
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
		if (baseClass == SubtractiveEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
			switch (derivedFeatureID) {
				case RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE: return EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
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
				case EobjectPackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE: return RootPackage.REMOVE_ROOT_EOBJECT__OLD_VALUE;
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
				case EChangePackage.ECHANGE___IS_RESOLVED: return RootPackage.REMOVE_ROOT_EOBJECT___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE__RESOURCESET: return RootPackage.REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SubtractiveEChange.class) {
			switch (baseOperationID) {
				case EChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE: return RootPackage.REMOVE_ROOT_EOBJECT___GET_OLD_VALUE;
				default: return -1;
			}
		}
		if (baseClass == EObjectSubtractedEChange.class) {
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
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case RootPackage.REMOVE_ROOT_EOBJECT___IS_RESOLVED:
				return isResolved();
			case RootPackage.REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
			case RootPackage.REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET_EOBJECT:
				return resolve((ResourceSet)arguments.get(0), (T)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RemoveRootEObjectImpl
