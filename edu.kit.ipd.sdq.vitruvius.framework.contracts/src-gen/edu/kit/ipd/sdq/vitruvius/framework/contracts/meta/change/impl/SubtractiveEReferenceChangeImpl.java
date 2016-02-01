/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subtractive EReference Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl#getFeature2OldValueMap <em>Feature2 Old Value Map</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SubtractiveEReferenceChangeImpl extends SubtractiveEChangeImpl<TUID> implements SubtractiveEReferenceChange {
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
     * The cached value of the '{@link #getFeature2OldValueMap() <em>Feature2 Old Value Map</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFeature2OldValueMap()
     * @generated
     * @ordered
     */
    protected Map<EStructuralFeature, Object> feature2OldValueMap;

    /**
     * The default value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsDelete()
     * @generated
     * @ordered
     */
    protected static final boolean IS_DELETE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsDelete()
     * @generated
     * @ordered
     */
    protected boolean isDelete = IS_DELETE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SubtractiveEReferenceChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.SUBTRACTIVE_EREFERENCE_CHANGE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID, oldOldTUID, oldTUID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map<EStructuralFeature, Object> getFeature2OldValueMap() {
        return feature2OldValueMap;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFeature2OldValueMap(Map<EStructuralFeature, Object> newFeature2OldValueMap) {
        Map<EStructuralFeature, Object> oldFeature2OldValueMap = feature2OldValueMap;
        feature2OldValueMap = newFeature2OldValueMap;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP, oldFeature2OldValueMap, feature2OldValueMap));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsDelete() {
        return isDelete;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsDelete(boolean newIsDelete) {
        boolean oldIsDelete = isDelete;
        isDelete = newIsDelete;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE, oldIsDelete, isDelete));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID:
                return getOldTUID();
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP:
                return getFeature2OldValueMap();
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE:
                return isIsDelete();
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
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID:
                setOldTUID((TUID)newValue);
                return;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP:
                setFeature2OldValueMap((Map<EStructuralFeature, Object>)newValue);
                return;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE:
                setIsDelete((Boolean)newValue);
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
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID:
                setOldTUID(OLD_TUID_EDEFAULT);
                return;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP:
                setFeature2OldValueMap((Map<EStructuralFeature, Object>)null);
                return;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE:
                setIsDelete(IS_DELETE_EDEFAULT);
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
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID:
                return OLD_TUID_EDEFAULT == null ? oldTUID != null : !OLD_TUID_EDEFAULT.equals(oldTUID);
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP:
                return feature2OldValueMap != null;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE:
                return isDelete != IS_DELETE_EDEFAULT;
        }
        return super.eIsSet(featureID);
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
        result.append(", feature2OldValueMap: ");
        result.append(feature2OldValueMap);
        result.append(", isDelete: ");
        result.append(isDelete);
        result.append(')');
        return result.toString();
    }

} //SubtractiveEReferenceChangeImpl
