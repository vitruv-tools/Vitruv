/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.UpdateEAttribute;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteEListImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute EAttribute Values</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PermuteEAttributeValuesImpl<A extends EObject> extends PermuteEListImpl implements PermuteEAttributeValues<A> {
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
    protected A affectedEObject;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PermuteEAttributeValuesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AttributePackage.Literals.PERMUTE_EATTRIBUTE_VALUES;
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public A getAffectedEObject() {
        if (affectedEObject != null && affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject)affectedEObject;
            affectedEObject = (A)eResolveProxy(oldAffectedEObject);
            if (affectedEObject != oldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
            }
        }
        return affectedEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public A basicGetAffectedEObject() {
        return affectedEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAffectedEObject(A newAffectedEObject) {
        A oldAffectedEObject = affectedEObject;
        affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
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
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE:
                setAffectedFeature((EAttribute)newValue);
                return;
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT:
                setAffectedEObject((A)newValue);
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
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE:
                setAffectedFeature((EAttribute)null);
                return;
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT:
                setAffectedEObject((A)null);
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
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE:
                return affectedFeature != null;
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT:
                return affectedEObject != null;
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
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
                case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
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
        if (baseClass == EFeatureChange.class) {
            switch (baseFeatureID) {
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE;
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return AttributePackage.PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT;
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

} //PermuteEAttributeValuesImpl
