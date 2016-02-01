/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Unset EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UnsetEReferenceImpl#getOldTUID
 * <em>Old TUID</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UnsetEReferenceImpl#getFeature2OldValueMap
 * <em>Feature2 Old Value Map</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UnsetEReferenceImpl#isIsDelete
 * <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnsetEReferenceImpl<T extends EObject> extends UnsetEFeatureImpl<EReference>implements UnsetEReference<T> {
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
     * The cached value of the '{@link #getFeature2OldValueMap() <em>Feature2 Old Value Map</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getFeature2OldValueMap()
     * @generated
     * @ordered
     */
    protected Map<EStructuralFeature, Object> feature2OldValueMap;

    /**
     * The default value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isIsDelete()
     * @generated
     * @ordered
     */
    protected static final boolean IS_DELETE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isIsDelete()
     * @generated
     * @ordered
     */
    protected boolean isDelete = IS_DELETE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected UnsetEReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FeaturePackage.Literals.UNSET_EREFERENCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public TUID getOldTUID() {
        return this.oldTUID;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setOldTUID(final TUID newOldTUID) {
        TUID oldOldTUID = this.oldTUID;
        this.oldTUID = newOldTUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.UNSET_EREFERENCE__OLD_TUID, oldOldTUID,
                    this.oldTUID));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Map<EStructuralFeature, Object> getFeature2OldValueMap() {
        return this.feature2OldValueMap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setFeature2OldValueMap(final Map<EStructuralFeature, Object> newFeature2OldValueMap) {
        Map<EStructuralFeature, Object> oldFeature2OldValueMap = this.feature2OldValueMap;
        this.feature2OldValueMap = newFeature2OldValueMap;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP, oldFeature2OldValueMap,
                    this.feature2OldValueMap));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean isIsDelete() {
        return this.isDelete;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setIsDelete(final boolean newIsDelete) {
        boolean oldIsDelete = this.isDelete;
        this.isDelete = newIsDelete;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.UNSET_EREFERENCE__IS_DELETE,
                    oldIsDelete, this.isDelete));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public boolean isContainment() {
        return getAffectedFeature().isContainment();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public TUID getOldValue() {
        return getOldTUID();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
            return getOldTUID();
        case FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            return getFeature2OldValueMap();
        case FeaturePackage.UNSET_EREFERENCE__IS_DELETE:
            return isIsDelete();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
            setOldTUID((TUID) newValue);
            return;
        case FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            setFeature2OldValueMap((Map<EStructuralFeature, Object>) newValue);
            return;
        case FeaturePackage.UNSET_EREFERENCE__IS_DELETE:
            setIsDelete((Boolean) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
            setOldTUID(OLD_TUID_EDEFAULT);
            return;
        case FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            setFeature2OldValueMap((Map<EStructuralFeature, Object>) null);
            return;
        case FeaturePackage.UNSET_EREFERENCE__IS_DELETE:
            setIsDelete(IS_DELETE_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
            return OLD_TUID_EDEFAULT == null ? this.oldTUID != null : !OLD_TUID_EDEFAULT.equals(this.oldTUID);
        case FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            return this.feature2OldValueMap != null;
        case FeaturePackage.UNSET_EREFERENCE__IS_DELETE:
            return this.isDelete != IS_DELETE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
        if (baseClass == SubtractiveEChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (derivedFeatureID) {
            case FeaturePackage.UNSET_EREFERENCE__OLD_TUID:
                return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID;
            case FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
                return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP;
            case FeaturePackage.UNSET_EREFERENCE__IS_DELETE:
                return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
        if (baseClass == SubtractiveEChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (baseFeatureID) {
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID:
                return FeaturePackage.UNSET_EREFERENCE__OLD_TUID;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP:
                return FeaturePackage.UNSET_EREFERENCE__FEATURE2_OLD_VALUE_MAP;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE:
                return FeaturePackage.UNSET_EREFERENCE__IS_DELETE;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public int eDerivedOperationID(final int baseOperationID, final Class<?> baseClass) {
        if (baseClass == SubtractiveEChange.class) {
            switch (baseOperationID) {
            case ChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE:
                return FeaturePackage.UNSET_EREFERENCE___GET_OLD_VALUE;
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        return super.eDerivedOperationID(baseOperationID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eInvoke(final int operationID, final EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
        case FeaturePackage.UNSET_EREFERENCE___IS_CONTAINMENT:
            return isContainment();
        case FeaturePackage.UNSET_EREFERENCE___GET_OLD_VALUE:
            return getOldValue();
        }
        return super.eInvoke(operationID, arguments);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (oldTUID: ");
        result.append(this.oldTUID);
        result.append(", feature2OldValueMap: ");
        result.append(this.feature2OldValueMap);
        result.append(", isDelete: ");
        result.append(this.isDelete);
        result.append(')');
        return result.toString();
    }

} // UnsetEReferenceImpl
