/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Subtractive Reference Change</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveReferenceChangeImpl#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveReferenceChangeImpl#getFeatureName2OldValueMap <em>Feature Name2 Old Value Map</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SubtractiveReferenceChangeImpl extends SubtractiveChangeImpl<TUID>
        implements SubtractiveReferenceChange {
    /**
     * The default value of the '{@link #getOldTUID() <em>Old TUID</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOldTUID()
     * @generated
     * @ordered
     */
    protected static final TUID OLD_TUID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOldTUID() <em>Old TUID</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOldTUID()
     * @generated
     * @ordered
     */
    protected TUID oldTUID = OLD_TUID_EDEFAULT;

    /**
     * The cached value of the '{@link #getFeatureName2OldValueMap()
     * <em>Feature Name2 Old Value Map</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getFeatureName2OldValueMap()
     * @generated
     * @ordered
     */
    protected Map<String, Object> featureName2OldValueMap;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected SubtractiveReferenceChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.SUBTRACTIVE_REFERENCE_CHANGE;
    }

    /**
     * @generated NOT
     */
    @Override
    public TUID getOldValue() {
        return getOldTUID();
    };

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public TUID getOldTUID() {
        return oldTUID;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOldTUID(TUID newOldTUID) {
        TUID oldOldTUID = oldTUID;
        oldTUID = newOldTUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID, oldOldTUID, oldTUID));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Map<String, Object> getFeatureName2OldValueMap() {
        return featureName2OldValueMap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setFeatureName2OldValueMap(Map<String, Object> newFeatureName2OldValueMap) {
        Map<String, Object> oldFeatureName2OldValueMap = featureName2OldValueMap;
        featureName2OldValueMap = newFeatureName2OldValueMap;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP, oldFeatureName2OldValueMap, featureName2OldValueMap));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID:
                return getOldTUID();
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP:
                return getFeatureName2OldValueMap();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID:
                setOldTUID((TUID)newValue);
                return;
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP:
                setFeatureName2OldValueMap((Map<String, Object>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID:
                setOldTUID(OLD_TUID_EDEFAULT);
                return;
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP:
                setFeatureName2OldValueMap((Map<String, Object>)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID:
                return OLD_TUID_EDEFAULT == null ? oldTUID != null : !OLD_TUID_EDEFAULT.equals(oldTUID);
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP:
                return featureName2OldValueMap != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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

} // SubtractiveReferenceChangeImpl
