/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEReference;

import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unset EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UnsetEReferenceImpl#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UnsetEReferenceImpl#getFeatureName2OldValueMap <em>Feature Name2 Old Value Map</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnsetEReferenceImpl<T extends EObject> extends UnsetEFeatureImpl<EReference> implements UnsetEReference<T> {
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UnsetEReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FeaturePackage.Literals.UNSET_EREFERENCE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.UNSET_EREFERENCE__OLD_TUID, oldOldTUID, oldTUID));
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
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP, oldFeatureName2OldValueMap, featureName2OldValueMap));
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
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
                return getOldTUID();
            case FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                return getFeatureName2OldValueMap();
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
            case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
                setOldTUID((TUID)newValue);
                return;
            case FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                setFeatureName2OldValueMap((Map<String, Object>)newValue);
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
            case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
                setOldTUID(OLD_TUID_EDEFAULT);
                return;
            case FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                setFeatureName2OldValueMap((Map<String, Object>)null);
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
            case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
                return OLD_TUID_EDEFAULT == null ? oldTUID != null : !OLD_TUID_EDEFAULT.equals(oldTUID);
            case FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP:
                return featureName2OldValueMap != null;
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
        if (baseClass == SubtractiveChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveReferenceChange.class) {
            switch (derivedFeatureID) {
                case FeaturePackage.UNSET_EREFERENCE__OLD_TUID: return ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID;
                case FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP: return ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP;
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
        if (baseClass == SubtractiveChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveReferenceChange.class) {
            switch (baseFeatureID) {
                case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID: return FeaturePackage.UNSET_EREFERENCE__OLD_TUID;
                case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP: return FeaturePackage.UNSET_EREFERENCE__FEATURE_NAME2_OLD_VALUE_MAP;
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
        if (baseClass == SubtractiveChange.class) {
            switch (baseOperationID) {
                case ChangePackage.SUBTRACTIVE_CHANGE___GET_OLD_VALUE: return FeaturePackage.UNSET_EREFERENCE___GET_OLD_VALUE;
                default: return -1;
            }
        }
        if (baseClass == SubtractiveReferenceChange.class) {
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
            case FeaturePackage.UNSET_EREFERENCE___IS_CONTAINMENT:
                return isContainment();
            case FeaturePackage.UNSET_EREFERENCE___GET_OLD_VALUE:
                return getOldValue();
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
        result.append(" (oldTUID: ");
        result.append(oldTUID);
        result.append(", featureName2OldValueMap: ");
        result.append(featureName2OldValueMap);
        result.append(')');
        return result.toString();
    }

} //UnsetEReferenceImpl
