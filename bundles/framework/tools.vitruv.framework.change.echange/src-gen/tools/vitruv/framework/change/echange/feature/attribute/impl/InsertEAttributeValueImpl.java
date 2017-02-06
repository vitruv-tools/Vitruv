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

import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory;

import tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange;
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue;
import tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange;

import tools.vitruv.framework.change.echange.feature.list.impl.InsertInListEChangeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert EAttribute Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.attribute.impl.InsertEAttributeValueImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsertEAttributeValueImpl<A extends EObject, T extends Object> extends InsertInListEChangeImpl<A, EAttribute, T> implements InsertEAttributeValue<A, T> {
	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' attribute.
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
	protected InsertEAttributeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AttributePackage.Literals.INSERT_EATTRIBUTE_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getNewValue() {
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
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE, oldNewValue, newValue));
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
			int _index = this.getIndex();
			T _newValue = this.getNewValue();
			final InsertEAttributeValue<EObject, T> resolvedChange = TypeInferringAtomicEChangeFactory.<EObject, T>createInsertAttributeChange(resolvedObject, _affectedFeature, _index, _newValue, false);
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
		EAttribute _affectedFeature = this.getAffectedFeature();
		T _newValue = this.getNewValue();
		return RemoveCommand.create(editingDomain, _affectedEObject, _affectedFeature, _newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE:
				return getNewValue();
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
			case AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE:
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
			case AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE:
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
			case AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE:
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
		if (baseClass == UpdateAttributeEChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AdditiveAttributeEChange.class) {
			switch (derivedFeatureID) {
				case AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE: return AttributePackage.ADDITIVE_ATTRIBUTE_ECHANGE__NEW_VALUE;
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
		if (baseClass == UpdateAttributeEChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AdditiveAttributeEChange.class) {
			switch (baseFeatureID) {
				case AttributePackage.ADDITIVE_ATTRIBUTE_ECHANGE__NEW_VALUE: return AttributePackage.INSERT_EATTRIBUTE_VALUE__NEW_VALUE;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AttributePackage.INSERT_EATTRIBUTE_VALUE___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
			case AttributePackage.INSERT_EATTRIBUTE_VALUE___GET_APPLY_COMMAND:
				return getApplyCommand();
			case AttributePackage.INSERT_EATTRIBUTE_VALUE___GET_REVERT_COMMAND:
				return getRevertCommand();
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
		result.append(" (newValue: ");
		result.append(newValue);
		result.append(')');
		return result.toString();
	}

} //InsertEAttributeValueImpl
