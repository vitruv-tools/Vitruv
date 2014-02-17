/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update EAttribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UpdateEAttributeImpl<T extends Object> extends UpdateEFeatureImpl<T> implements UpdateEAttribute<T> {
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
     * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getAffectedEObject()
     * @generated
     * @ordered
     */
	protected EObject affectedEObject;

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
	protected UpdateEAttributeImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ChangePackage.Literals.UPDATE_EATTRIBUTE;
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject getAffectedEObject() {
        if (affectedEObject != null && affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject)affectedEObject;
            affectedEObject = eResolveProxy(oldAffectedEObject);
            if (affectedEObject != oldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
            }
        }
        return affectedEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject basicGetAffectedEObject() {
        return affectedEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setAffectedEObject(EObject newAffectedEObject) {
        EObject oldAffectedEObject = affectedEObject;
        affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.UPDATE_EATTRIBUTE__NEW_VALUE, oldNewValue, newValue));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
            case ChangePackage.UPDATE_EATTRIBUTE__NEW_VALUE:
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
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE:
                setAffectedFeature((EAttribute)newValue);
                return;
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)newValue);
                return;
            case ChangePackage.UPDATE_EATTRIBUTE__NEW_VALUE:
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
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE:
                setAffectedFeature((EAttribute)null);
                return;
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)null);
                return;
            case ChangePackage.UPDATE_EATTRIBUTE__NEW_VALUE:
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
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT:
                return affectedEObject != null;
            case ChangePackage.UPDATE_EATTRIBUTE__NEW_VALUE:
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
                case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE: return ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT: return ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == EAttributeChange.class) {
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
                case ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_FEATURE;
                case ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ChangePackage.UPDATE_EATTRIBUTE__AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == EAttributeChange.class) {
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
        result.append(" (newValue: ");
        result.append(newValue);
        result.append(')');
        return result.toString();
    }

} //UpdateEAttributeImpl
