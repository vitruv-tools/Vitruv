/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
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
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory;

import tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Single Valued EAttribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl#getOldValue <em>Old Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceSingleValuedEAttributeImpl<A extends EObject, T extends Object> extends AdditiveAttributeEChangeImpl<A, T> implements ReplaceSingleValuedEAttribute<A, T> {
	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' attribute.
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
	protected ReplaceSingleValuedEAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getOldValue() {
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
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE, oldOldValue, oldValue));
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
			InternalEObject _proxyObject = this.getProxyObject();
			final EObject resolvedObject = EcoreUtil.resolve(_proxyObject, resourceSet);
			EAttribute _affectedFeature = this.getAffectedFeature();
			T _oldValue = this.getOldValue();
			T _newValue = this.getNewValue();
			final ReplaceSingleValuedEAttribute<EObject, T> resolvedChange = TypeInferringAtomicEChangeFactory.<EObject, T>createReplaceSingleAttributeChange(resolvedObject, _affectedFeature, _oldValue, _newValue, false);
			resolvedChange.setResolved(true);
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
		EAttribute _affectedFeature = this.getAffectedFeature();
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
		EAttribute _affectedFeature = this.getAffectedFeature();
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
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
				return getOldValue();
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
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
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
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
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
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
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
		if (baseClass == SubtractiveAttributeEChange.class) {
			switch (derivedFeatureID) {
				case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE: return AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE;
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
		if (baseClass == SubtractiveAttributeEChange.class) {
			switch (baseFeatureID) {
				case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE;
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
				case EChangePackage.ECHANGE___RESOLVE__RESOURCESET: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___RESOLVE__RESOURCESET;
				case EChangePackage.ECHANGE___GET_APPLY_COMMAND: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_APPLY_COMMAND;
				case EChangePackage.ECHANGE___GET_REVERT_COMMAND: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_REVERT_COMMAND;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SubtractiveEChange.class) {
			switch (baseOperationID) {
				case EChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_OLD_VALUE;
				default: return -1;
			}
		}
		if (baseClass == SubtractiveAttributeEChange.class) {
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
				case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_FROM_NON_DEFAULT_VALUE: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___IS_FROM_NON_DEFAULT_VALUE;
				case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_TO_NON_DEFAULT_VALUE: return AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___IS_TO_NON_DEFAULT_VALUE;
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
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_APPLY_COMMAND:
				return getApplyCommand();
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_REVERT_COMMAND:
				return getRevertCommand();
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___IS_FROM_NON_DEFAULT_VALUE:
				return isFromNonDefaultValue();
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE___IS_TO_NON_DEFAULT_VALUE:
				return isToNonDefaultValue();
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
		result.append(" (oldValue: ");
		result.append(oldValue);
		result.append(')');
		return result.toString();
	}

} //ReplaceSingleValuedEAttributeImpl
