/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AtomicEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AdditiveAttributeEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.UpdateAttributeEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.InsertInListEChangeImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert EAttribute Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsertEAttributeValueImpl<A extends EObject, T extends Object> extends InsertInListEChangeImpl implements InsertEAttributeValue<A, T> {
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
    public EAttribute getAffectedFeature() {
        if (affectedFeature != null && affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
            affectedFeature = (EAttribute)eResolveProxy(oldAffectedFeature);
            if (affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
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
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE:
                setAffectedFeature((EAttribute)newValue);
                return;
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT:
                setAffectedEObject((A)newValue);
                return;
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
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE:
                setAffectedFeature((EAttribute)null);
                return;
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT:
                setAffectedEObject((A)null);
                return;
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
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT:
                return affectedEObject != null;
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
        if (baseClass == EChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveEChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (derivedFeatureID) {
                case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE: return FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE;
                case AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT: return FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT;
                default: return -1;
            }
        }
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
        if (baseClass == EChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveEChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (baseFeatureID) {
                case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE: return AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE;
                case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT: return AttributePackage.INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT;
                default: return -1;
            }
        }
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
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (newValue: ");
        result.append(newValue);
        result.append(')');
        return result.toString();
    }

} //InsertEAttributeValueImpl
