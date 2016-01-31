/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateSingleValuedEFeatureImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getOldTUIDOfAffectedEObject <em>Old TUID Of Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getFeatureName2OldValueMap <em>Feature Name2 Old Value Map</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceSingleValuedEReferenceImpl<T extends EObject> extends UpdateSingleValuedEFeatureImpl<T> implements ReplaceSingleValuedEReference<T> {
    /**
     * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAffectedFeature()
     * @generated
     * @ordered
     */
    protected EReference affectedFeature;

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
     * The default value of the '{@link #getOldTUIDOfAffectedEObject() <em>Old TUID Of Affected EObject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     * @ordered
     */
    protected static final TUID OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOldTUIDOfAffectedEObject() <em>Old TUID Of Affected EObject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     * @ordered
     */
    protected TUID oldTUIDOfAffectedEObject = OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT;

    /**
     * The default value of the '{@link #getOldTUID() <em>Old TUID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOldTUID()
     * @generated
     * @ordered
     */
    protected static final TUID OLD_TUID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOldTUID() <em>Old TUID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOldTUID()
     * @generated
     * @ordered
     */
    protected TUID oldTUID = OLD_TUID_EDEFAULT;

    /**
     * The cached value of the '{@link #getFeatureName2OldValueMap() <em>Feature Name2 Old Value Map</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFeatureName2OldValueMap()
     * @generated
     * @ordered
     */
    protected Map<String, Object> featureName2OldValueMap;

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
    public EReference getAffectedFeature() {
        if (affectedFeature != null && affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
            affectedFeature = (EReference)eResolveProxy(oldAffectedFeature);
            if (affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
            }
        }
        return affectedFeature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference basicGetAffectedFeature() {
        return affectedFeature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAffectedFeature(EReference newAffectedFeature) {
        EReference oldAffectedFeature = affectedFeature;
        affectedFeature = newAffectedFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TUID getOldTUIDOfAffectedEObject() {
        return oldTUIDOfAffectedEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOldTUIDOfAffectedEObject(TUID newOldTUIDOfAffectedEObject) {
        TUID oldOldTUIDOfAffectedEObject = oldTUIDOfAffectedEObject;
        oldTUIDOfAffectedEObject = newOldTUIDOfAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT, oldOldTUIDOfAffectedEObject, oldTUIDOfAffectedEObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TUID getOldTUID() {
        return oldTUID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOldTUID(TUID newOldTUID) {
        TUID oldOldTUID = oldTUID;
        oldTUID = newOldTUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID, oldOldTUID, oldTUID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map<String, Object> getFeatureName2OldValueMap() {
        return featureName2OldValueMap;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFeatureName2OldValueMap(Map<String, Object> newFeatureName2OldValueMap) {
        Map<String, Object> oldFeatureName2OldValueMap = featureName2OldValueMap;
        featureName2OldValueMap = newFeatureName2OldValueMap;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP, oldFeatureName2OldValueMap, featureName2OldValueMap));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TUID getOldValue() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isContainment() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return getOldTUIDOfAffectedEObject();
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID:
                return getOldTUID();
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                return getFeatureName2OldValueMap();
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
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
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)newValue);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)newValue);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                setOldTUIDOfAffectedEObject((TUID)newValue);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID:
                setOldTUID((TUID)newValue);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                setFeatureName2OldValueMap((Map<String, Object>)newValue);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
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
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)null);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)null);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                setOldTUIDOfAffectedEObject(OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID:
                setOldTUID(OLD_TUID_EDEFAULT);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                setFeatureName2OldValueMap((Map<String, Object>)null);
                return;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
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
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT:
                return affectedEObject != null;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT == null ? oldTUIDOfAffectedEObject != null : !OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT.equals(oldTUIDOfAffectedEObject);
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID:
                return OLD_TUID_EDEFAULT == null ? oldTUID != null : !OLD_TUID_EDEFAULT.equals(oldTUID);
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                return featureName2OldValueMap != null;
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE:
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
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
                case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
                case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveReferenceChange.class) {
            switch (derivedFeatureID) {
                case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID: return ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID;
                case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP: return ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP;
                default: return -1;
            }
        }
        if (baseClass == AdditiveChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveReferenceChange.class) {
            switch (derivedFeatureID) {
                case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE: return ChangePackage.ADDITIVE_REFERENCE_CHANGE__NEW_VALUE;
                default: return -1;
            }
        }
        if (baseClass == ReplaciveReferenceChange.class) {
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
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE;
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT;
                case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveReferenceChange.class) {
            switch (baseFeatureID) {
                case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID;
                case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP;
                default: return -1;
            }
        }
        if (baseClass == AdditiveChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveReferenceChange.class) {
            switch (baseFeatureID) {
                case ChangePackage.ADDITIVE_REFERENCE_CHANGE__NEW_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE;
                default: return -1;
            }
        }
        if (baseClass == ReplaciveReferenceChange.class) {
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
        if (baseClass == EFeatureChange.class) {
            switch (baseOperationID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseOperationID) {
                case ReferencePackage.UPDATE_EREFERENCE___IS_CONTAINMENT: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_CONTAINMENT;
                default: return -1;
            }
        }
        if (baseClass == SubtractiveChange.class) {
            switch (baseOperationID) {
                case ChangePackage.SUBTRACTIVE_CHANGE___GET_OLD_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_OLD_VALUE;
                default: return -1;
            }
        }
        if (baseClass == SubtractiveReferenceChange.class) {
            switch (baseOperationID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveChange.class) {
            switch (baseOperationID) {
                case ChangePackage.ADDITIVE_CHANGE___GET_NEW_VALUE: return ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_NEW_VALUE;
                default: return -1;
            }
        }
        if (baseClass == AdditiveReferenceChange.class) {
            switch (baseOperationID) {
                default: return -1;
            }
        }
        if (baseClass == ReplaciveReferenceChange.class) {
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
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___GET_OLD_VALUE:
                return getOldValue();
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE___IS_CONTAINMENT:
                return isContainment();
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
        result.append(" (oldTUIDOfAffectedEObject: ");
        result.append(oldTUIDOfAffectedEObject);
        result.append(", oldTUID: ");
        result.append(oldTUID);
        result.append(", featureName2OldValueMap: ");
        result.append(featureName2OldValueMap);
        result.append(')');
        return result.toString();
    }

} //ReplaceSingleValuedEReferenceImpl
