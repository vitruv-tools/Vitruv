/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.RemoveFromEListImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Remove EReference</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getOldTUIDOfAffectedEObject <em>Old TUID Of Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getFeature2OldValueMap <em>Feature2 Old Value Map</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RemoveEReferenceImpl<A extends EObject> extends RemoveFromEListImpl implements RemoveEReference<A> {
    /**
     * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAffectedFeature()
     * @generated
     * @ordered
     */
    protected EReference affectedFeature;

    /**
     * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAffectedEObject()
     * @generated
     * @ordered
     */
    protected A affectedEObject;

    /**
     * The default value of the '{@link #getOldTUIDOfAffectedEObject()
     * <em>Old TUID Of Affected EObject</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     * @ordered
     */
    protected static final TUID OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOldTUIDOfAffectedEObject()
     * <em>Old TUID Of Affected EObject</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     * @ordered
     */
    protected TUID oldTUIDOfAffectedEObject = OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT;

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
     * The cached value of the '{@link #getFeature2OldValueMap() <em>Feature2 Old Value Map</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @generated
     */
    protected RemoveEReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.REMOVE_EREFERENCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAffectedFeature() {
        if (affectedFeature != null && affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
            affectedFeature = (EReference)eResolveProxy(oldAffectedFeature);
            if (affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
            }
        }
        return affectedFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference basicGetAffectedFeature() {
        return affectedFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAffectedFeature(EReference newAffectedFeature) {
        EReference oldAffectedFeature = affectedFeature;
        affectedFeature = newAffectedFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public A getAffectedEObject() {
        if (affectedEObject != null && affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject)affectedEObject;
            affectedEObject = (A)eResolveProxy(oldAffectedEObject);
            if (affectedEObject != oldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
            }
        }
        return affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public A basicGetAffectedEObject() {
        return affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAffectedEObject(A newAffectedEObject) {
        A oldAffectedEObject = affectedEObject;
        affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public TUID getOldTUIDOfAffectedEObject() {
        return oldTUIDOfAffectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOldTUIDOfAffectedEObject(TUID newOldTUIDOfAffectedEObject) {
        TUID oldOldTUIDOfAffectedEObject = oldTUIDOfAffectedEObject;
        oldTUIDOfAffectedEObject = newOldTUIDOfAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT, oldOldTUIDOfAffectedEObject, oldTUIDOfAffectedEObject));
    }

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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__OLD_TUID, oldOldTUID, oldTUID));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Map<EStructuralFeature, Object> getFeature2OldValueMap() {
        return feature2OldValueMap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setFeature2OldValueMap(Map<EStructuralFeature, Object> newFeature2OldValueMap) {
        Map<EStructuralFeature, Object> oldFeature2OldValueMap = feature2OldValueMap;
        feature2OldValueMap = newFeature2OldValueMap;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP, oldFeature2OldValueMap, feature2OldValueMap));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isIsDelete() {
        return isDelete;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setIsDelete(boolean newIsDelete) {
        boolean oldIsDelete = isDelete;
        isDelete = newIsDelete;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__IS_DELETE, oldIsDelete, isDelete));
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
     * @generated NOT
     */
    @Override
    public boolean isContainment() {
        return getAffectedFeature().isContainment();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return getOldTUIDOfAffectedEObject();
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
                return getOldTUID();
            case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
                return getFeature2OldValueMap();
            case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
                return isIsDelete();
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
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)newValue);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
                setAffectedEObject((A)newValue);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                setOldTUIDOfAffectedEObject((TUID)newValue);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
                setOldTUID((TUID)newValue);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
                setFeature2OldValueMap((Map<EStructuralFeature, Object>)newValue);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
                setIsDelete((Boolean)newValue);
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
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)null);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
                setAffectedEObject((A)null);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                setOldTUIDOfAffectedEObject(OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
                setOldTUID(OLD_TUID_EDEFAULT);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
                setFeature2OldValueMap((Map<EStructuralFeature, Object>)null);
                return;
            case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
                setIsDelete(IS_DELETE_EDEFAULT);
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
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
                return affectedEObject != null;
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT == null ? oldTUIDOfAffectedEObject != null : !OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT.equals(oldTUIDOfAffectedEObject);
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
                return OLD_TUID_EDEFAULT == null ? oldTUID != null : !OLD_TUID_EDEFAULT.equals(oldTUID);
            case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
                return feature2OldValueMap != null;
            case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
                return isDelete != IS_DELETE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
                case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
                case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveEChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (derivedFeatureID) {
                case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID: return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID;
                case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP: return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP;
                case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE: return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE;
                default: return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == EFeatureChange.class) {
            switch (baseFeatureID) {
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE;
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT;
                case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT: return ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveEChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (baseFeatureID) {
                case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID: return ReferencePackage.REMOVE_EREFERENCE__OLD_TUID;
                case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP: return ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP;
                case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE: return ReferencePackage.REMOVE_EREFERENCE__IS_DELETE;
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
                case ReferencePackage.UPDATE_EREFERENCE___IS_CONTAINMENT: return ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT;
                default: return -1;
            }
        }
        if (baseClass == SubtractiveEChange.class) {
            switch (baseOperationID) {
                case ChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE: return ReferencePackage.REMOVE_EREFERENCE___GET_OLD_VALUE;
                default: return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (baseOperationID) {
                default: return -1;
            }
        }
        return super.eDerivedOperationID(baseOperationID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
            case ReferencePackage.REMOVE_EREFERENCE___GET_OLD_VALUE:
                return getOldValue();
            case ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT:
                return isContainment();
        }
        return super.eInvoke(operationID, arguments);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
        result.append(", feature2OldValueMap: ");
        result.append(feature2OldValueMap);
        result.append(", isDelete: ");
        result.append(isDelete);
        result.append(')');
        return result.toString();
    }

} // RemoveEReferenceImpl
