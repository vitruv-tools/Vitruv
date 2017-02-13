/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;

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
	public boolean isResolved() {
		return ((super.isResolved() && 
			(!Objects.equal(this.getNewValue(), null))) && (!this.getNewValue().eIsProxy()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet) {
		T _newValue = this.getNewValue();
		boolean _equals = Objects.equal(_newValue, null);
		if (_equals) {
			return null;
		}
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolve = super.resolve(resourceSet);
			final InsertEReference<A, T> resolvedChange = ((InsertEReference<A, T>) _resolve);
			boolean _equals_1 = Objects.equal(resolvedChange, null);
			if (_equals_1) {
				return null;
			}
			T _newValue_1 = this.getNewValue();
			EObject _resolve_1 = EcoreUtil.resolve(_newValue_1, resourceSet);
			resolvedChange.setNewValue(((T) _resolve_1));
			T _newValue_2 = resolvedChange.getNewValue();
			boolean _eIsProxy = _newValue_2.eIsProxy();
			if (_eIsProxy) {
				return this;
			}
			return resolvedChange;
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Command getApplyCommand() {
		ComposedAdapterFactory _composedAdapterFactory = new ComposedAdapterFactory();
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(_composedAdapterFactory, null);
		A _affectedEObject = this.getAffectedEObject();
		EReference _affectedFeature = this.getAffectedFeature();
		T _newValue = this.getNewValue();
		int _index = this.getIndex();
		return AddCommand.create(editingDomain, _affectedEObject, _affectedFeature, _newValue, _index);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Command getRevertCommand() {
		ComposedAdapterFactory _composedAdapterFactory = new ComposedAdapterFactory();
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(_composedAdapterFactory, null);
		A _affectedEObject = this.getAffectedEObject();
		EReference _affectedFeature = this.getAffectedFeature();
		T _newValue = this.getNewValue();
		return RemoveCommand.create(editingDomain, _affectedEObject, _affectedFeature, _newValue);
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
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
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
				case EobjectPackage.EOBJECT_ADDED_ECHANGE__NEW_VALUE: return ReferencePackage.INSERT_EREFERENCE__NEW_VALUE;
				default: return -1;
			}
		}
		if (baseClass == AdditiveReferenceEChange.class) {
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
				case EChangePackage.ECHANGE___IS_RESOLVED: return ReferencePackage.INSERT_EREFERENCE___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE__RESOURCESET: return ReferencePackage.INSERT_EREFERENCE___RESOLVE__RESOURCESET;
				case EChangePackage.ECHANGE___GET_APPLY_COMMAND: return ReferencePackage.INSERT_EREFERENCE___GET_APPLY_COMMAND;
				case EChangePackage.ECHANGE___GET_REVERT_COMMAND: return ReferencePackage.INSERT_EREFERENCE___GET_REVERT_COMMAND;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FeatureEChange.class) {
			switch (baseOperationID) {
				case FeaturePackage.FEATURE_ECHANGE___IS_RESOLVED: return ReferencePackage.INSERT_EREFERENCE___IS_RESOLVED;
				case FeaturePackage.FEATURE_ECHANGE___RESOLVE__RESOURCESET: return ReferencePackage.INSERT_EREFERENCE___RESOLVE__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
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
			case ReferencePackage.INSERT_EREFERENCE___IS_RESOLVED:
				return isResolved();
			case ReferencePackage.INSERT_EREFERENCE___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
			case ReferencePackage.INSERT_EREFERENCE___GET_APPLY_COMMAND:
				return getApplyCommand();
			case ReferencePackage.INSERT_EREFERENCE___GET_REVERT_COMMAND:
				return getRevertCommand();
			case ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT:
				return isContainment();
		}
		return super.eInvoke(operationID, arguments);
	}

} //InsertEReferenceImpl
