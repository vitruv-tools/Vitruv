/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateSingleValuedEFeatureImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update Single Valued EAttribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl#getOldAffectedEObject <em>Old Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl#getNewAffectedEObject <em>New Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UpdateSingleValuedEAttributeImpl<T extends Object> extends UpdateSingleValuedEFeatureImpl<T> implements UpdateSingleValuedEAttribute<T> {
	/**
	 * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedFeature()
	 * @generated
	 * @ordered
	 */
	protected EAttribute affectedFeature;

	/**
	 * The cached value of the '{@link #getOldAffectedEObject() <em>Old Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldAffectedEObject()
	 * @generated
	 * @ordered
	 */
	protected EObject oldAffectedEObject;

	/**
	 * The cached value of the '{@link #getNewAffectedEObject() <em>New Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewAffectedEObject()
	 * @generated
	 * @ordered
	 */
	protected EObject newAffectedEObject;

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
	protected UpdateSingleValuedEAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AttributePackage.Literals.UPDATE_SINGLE_VALUED_EATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffectedFeature() {
		if (affectedFeature != null && affectedFeature.eIsProxy()) {
			InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
			affectedFeature = (EAttribute)eResolveProxy(oldAffectedFeature);
			if (affectedFeature != oldAffectedFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
			}
		}
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetAffectedFeature() {
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedFeature(EAttribute newAffectedFeature) {
		EAttribute oldAffectedFeature = affectedFeature;
		affectedFeature = newAffectedFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getOldAffectedEObject() {
		if (oldAffectedEObject != null && oldAffectedEObject.eIsProxy()) {
			InternalEObject oldOldAffectedEObject = (InternalEObject)oldAffectedEObject;
			oldAffectedEObject = eResolveProxy(oldOldAffectedEObject);
			if (oldAffectedEObject != oldOldAffectedEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT, oldOldAffectedEObject, oldAffectedEObject));
			}
		}
		return oldAffectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetOldAffectedEObject() {
		return oldAffectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldAffectedEObject(EObject newOldAffectedEObject) {
		EObject oldOldAffectedEObject = oldAffectedEObject;
		oldAffectedEObject = newOldAffectedEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT, oldOldAffectedEObject, oldAffectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getNewAffectedEObject() {
		if (newAffectedEObject != null && newAffectedEObject.eIsProxy()) {
			InternalEObject oldNewAffectedEObject = (InternalEObject)newAffectedEObject;
			newAffectedEObject = eResolveProxy(oldNewAffectedEObject);
			if (newAffectedEObject != oldNewAffectedEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT, oldNewAffectedEObject, newAffectedEObject));
			}
		}
		return newAffectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetNewAffectedEObject() {
		return newAffectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewAffectedEObject(EObject newNewAffectedEObject) {
		EObject oldNewAffectedEObject = newAffectedEObject;
		newAffectedEObject = newNewAffectedEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT, oldNewAffectedEObject, newAffectedEObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE, oldNewValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE:
				if (resolve) return getAffectedFeature();
				return basicGetAffectedFeature();
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT:
				if (resolve) return getOldAffectedEObject();
				return basicGetOldAffectedEObject();
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT:
				if (resolve) return getNewAffectedEObject();
				return basicGetNewAffectedEObject();
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
				return getOldValue();
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE:
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
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE:
				setAffectedFeature((EAttribute)newValue);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT:
				setOldAffectedEObject((EObject)newValue);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT:
				setNewAffectedEObject((EObject)newValue);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE:
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
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE:
				setAffectedFeature((EAttribute)null);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT:
				setOldAffectedEObject((EObject)null);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT:
				setNewAffectedEObject((EObject)null);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
				setOldValue((T)null);
				return;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE:
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
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE:
				return affectedFeature != null;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT:
				return oldAffectedEObject != null;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT:
				return newAffectedEObject != null;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE:
				return oldValue != null;
			case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE:
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
		if (baseClass == EChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (derivedFeatureID) {
				case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
				case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT;
				case AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT;
				default: return -1;
			}
		}
		if (baseClass == UpdateEAttribute.class) {
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
		if (baseClass == EChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (baseFeatureID) {
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE;
				case FeaturePackage.EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT: return AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_AFFECTED_EOBJECT;
				case FeaturePackage.EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT: return AttributePackage.UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_AFFECTED_EOBJECT;
				default: return -1;
			}
		}
		if (baseClass == UpdateEAttribute.class) {
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
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (oldValue: ");
		result.append(oldValue);
		result.append(", newValue: ");
		result.append(newValue);
		result.append(')');
		return result.toString();
	}

} //UpdateSingleValuedEAttributeImpl
