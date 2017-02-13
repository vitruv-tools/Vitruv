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

import org.eclipse.emf.edit.command.SetCommand;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;
import tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getOldValue <em>Old Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceSingleValuedEReferenceImpl<A extends EObject, T extends EObject> extends AdditiveReferenceEChangeImpl<A, T> implements ReplaceSingleValuedEReference<A, T> {
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
		return ((((super.isResolved() && 
			(!Objects.equal(this.getOldValue(), null))) && (!this.getOldValue().eIsProxy())) && 
			(!Objects.equal(this.getNewValue(), null))) && (!this.getNewValue().eIsProxy()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet) {
		if ((Objects.equal(this.getOldValue(), null) || Objects.equal(this.getNewValue(), null))) {
			return null;
		}
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolve = super.resolve(resourceSet);
			final ReplaceSingleValuedEReference<A, T> resolvedChange = ((ReplaceSingleValuedEReference<A, T>) _resolve);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			T _oldValue = this.getOldValue();
			EObject _resolve_1 = EcoreUtil.resolve(_oldValue, resourceSet);
			resolvedChange.setOldValue(((T) _resolve_1));
			T _newValue = this.getNewValue();
			EObject _resolve_2 = EcoreUtil.resolve(_newValue, resourceSet);
			resolvedChange.setNewValue(((T) _resolve_2));
			if ((resolvedChange.getNewValue().eIsProxy() || resolvedChange.getOldValue().eIsProxy())) {
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
		return SetCommand.create(editingDomain, _affectedEObject, _affectedFeature, _newValue);
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
		T _oldValue = this.getOldValue();
		return SetCommand.create(editingDomain, _affectedEObject, _affectedFeature, _oldValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFromNonDefaultValue() {
		return !java.util.Objects.equals(getOldValue(), getAffectedFeature().getDefaultValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isToNonDefaultValue() {
		return !java.util.Objects.equals(getNewValue(), getAffectedFeature().getDefaultValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
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
		if (baseClass == SubtractiveEChange.class) {
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
		if (baseClass == UpdateSingleValuedFeatureEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ReplaceSingleValuedFeatureEChange.class) {
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
		if (baseClass == SubtractiveEChange.class) {
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
		if (baseClass == UpdateSingleValuedFeatureEChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ReplaceSingleValuedFeatureEChange.class) {
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
				case EChangePackage.ECHANGE___RESOLVE__RESOURCESET: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE__RESOURCESET;
				case EChangePackage.ECHANGE___GET_APPLY_COMMAND: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_APPLY_COMMAND;
				case EChangePackage.ECHANGE___GET_REVERT_COMMAND: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_REVERT_COMMAND;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FeatureEChange.class) {
			switch (baseOperationID) {
				case FeaturePackage.FEATURE_ECHANGE___IS_RESOLVED: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_RESOLVED;
				case FeaturePackage.FEATURE_ECHANGE___RESOLVE__RESOURCESET: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SubtractiveEChange.class) {
			switch (baseOperationID) {
				case EChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_OLD_VALUE;
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
		if (baseClass == UpdateSingleValuedFeatureEChange.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ReplaceSingleValuedFeatureEChange.class) {
			switch (baseOperationID) {
				case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_FROM_NON_DEFAULT_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_FROM_NON_DEFAULT_VALUE;
				case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_TO_NON_DEFAULT_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_TO_NON_DEFAULT_VALUE;
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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_APPLY_COMMAND:
				return getApplyCommand();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_REVERT_COMMAND:
				return getRevertCommand();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_FROM_NON_DEFAULT_VALUE:
				return isFromNonDefaultValue();
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_TO_NON_DEFAULT_VALUE:
				return isToNonDefaultValue();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ReplaceSingleValuedEReferenceImpl
