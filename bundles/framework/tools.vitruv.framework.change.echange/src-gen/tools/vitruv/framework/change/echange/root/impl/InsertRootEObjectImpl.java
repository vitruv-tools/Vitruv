/**
 */
package tools.vitruv.framework.change.echange.root.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.echange.root.RootEChange;
import tools.vitruv.framework.change.echange.root.RootPackage;

import tools.vitruv.framework.change.echange.util.EChangeUtil;
import tools.vitruv.framework.change.echange.util.StagingArea;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert Root EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl#getNewValue <em>New Value</em>}</li>
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
	public boolean isResolved() {
		return (super.isResolved() && (!this.getNewValue().eIsProxy()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolve = super.resolve(resourceSet);
			final InsertRootEObject<T> resolvedChange = ((InsertRootEObject<T>) _resolve);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			final Resource stagingArea = StagingArea.getStagingArea(resourceSet);
			EList<EObject> _contents = stagingArea.getContents();
			boolean _isEmpty = _contents.isEmpty();
			boolean _not_1 = (!_isEmpty);
			if (_not_1) {
				EList<EObject> _contents_1 = stagingArea.getContents();
				EObject _get = _contents_1.get(0);
				resolvedChange.setNewValue(((T) _get));
			}
			else {
				T _newValue = this.getNewValue();
				EObject _resolveProxy = EChangeUtil.resolveProxy(_newValue, resourceSet);
				resolvedChange.setNewValue(((T) _resolveProxy));
			}
			if (((!Objects.equal(resolvedChange.getNewValue(), null)) && (!resolvedChange.getNewValue().eIsProxy()))) {
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
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RootPackage.INSERT_ROOT_EOBJECT__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
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
				case EChangePackage.ECHANGE___IS_RESOLVED: return RootPackage.INSERT_ROOT_EOBJECT___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE__RESOURCESET: return RootPackage.INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == RootEChange.class) {
			switch (baseOperationID) {
				case RootPackage.ROOT_ECHANGE___IS_RESOLVED: return RootPackage.INSERT_ROOT_EOBJECT___IS_RESOLVED;
				case RootPackage.ROOT_ECHANGE___RESOLVE__RESOURCESET: return RootPackage.INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == AdditiveEChange.class) {
			switch (baseOperationID) {
				case EChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE: return RootPackage.INSERT_ROOT_EOBJECT___GET_NEW_VALUE;
				default: return -1;
			}
		}
		if (baseClass == EObjectAddedEChange.class) {
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
			case RootPackage.INSERT_ROOT_EOBJECT___IS_RESOLVED:
				return isResolved();
			case RootPackage.INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //InsertRootEObjectImpl
