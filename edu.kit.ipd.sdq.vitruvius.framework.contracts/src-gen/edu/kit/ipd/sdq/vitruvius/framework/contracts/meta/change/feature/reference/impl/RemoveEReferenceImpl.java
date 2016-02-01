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
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getAffectedFeature
 * <em>Affected Feature</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getAffectedEObject
 * <em>Affected EObject</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getOldTUIDOfAffectedEObject
 * <em>Old TUID Of Affected EObject</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getOldTUID
 * <em>Old TUID</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#getFeature2OldValueMap
 * <em>Feature2 Old Value Map</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl#isIsDelete
 * <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RemoveEReferenceImpl<T extends EObject> extends RemoveFromEListImpl<T>implements RemoveEReference<T> {
    /**
     * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAffectedFeature()
     * @generated
     * @ordered
     */
    protected EReference affectedFeature;

    /**
     * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAffectedEObject()
     * @generated
     * @ordered
     */
    protected EObject affectedEObject;

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
    protected RemoveEReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.REMOVE_EREFERENCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAffectedFeature() {
        if (this.affectedFeature != null && this.affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject) this.affectedFeature;
            this.affectedFeature = (EReference) eResolveProxy(oldAffectedFeature);
            if (this.affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature,
                            this.affectedFeature));
            }
        }
        return this.affectedFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EReference basicGetAffectedFeature() {
        return this.affectedFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAffectedFeature(final EReference newAffectedFeature) {
        EReference oldAffectedFeature = this.affectedFeature;
        this.affectedFeature = newAffectedFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE,
                    oldAffectedFeature, this.affectedFeature));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject getAffectedEObject() {
        if (this.affectedEObject != null && this.affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject) this.affectedEObject;
            this.affectedEObject = eResolveProxy(oldAffectedEObject);
            if (this.affectedEObject != oldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject,
                            this.affectedEObject));
            }
        }
        return this.affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EObject basicGetAffectedEObject() {
        return this.affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAffectedEObject(final EObject newAffectedEObject) {
        EObject oldAffectedEObject = this.affectedEObject;
        this.affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT,
                    oldAffectedEObject, this.affectedEObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TUID getOldTUIDOfAffectedEObject() {
        return this.oldTUIDOfAffectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOldTUIDOfAffectedEObject(final TUID newOldTUIDOfAffectedEObject) {
        TUID oldOldTUIDOfAffectedEObject = this.oldTUIDOfAffectedEObject;
        this.oldTUIDOfAffectedEObject = newOldTUIDOfAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT, oldOldTUIDOfAffectedEObject,
                    this.oldTUIDOfAffectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__OLD_TUID,
                    oldOldTUID, this.oldTUID));
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
                    ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP, oldFeature2OldValueMap,
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__IS_DELETE,
                    oldIsDelete, this.isDelete));
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
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            if (resolve)
                return getAffectedFeature();
            return basicGetAffectedFeature();
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            if (resolve)
                return getAffectedEObject();
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
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            setAffectedFeature((EReference) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            setAffectedEObject((EObject) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            setOldTUIDOfAffectedEObject((TUID) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
            setOldTUID((TUID) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            setFeature2OldValueMap((Map<EStructuralFeature, Object>) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            setAffectedFeature((EReference) null);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            setAffectedEObject((EObject) null);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            setOldTUIDOfAffectedEObject(OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
            setOldTUID(OLD_TUID_EDEFAULT);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            setFeature2OldValueMap((Map<EStructuralFeature, Object>) null);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            return this.affectedFeature != null;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            return this.affectedEObject != null;
        case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            return OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT == null ? this.oldTUIDOfAffectedEObject != null
                    : !OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT.equals(this.oldTUIDOfAffectedEObject);
        case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
            return OLD_TUID_EDEFAULT == null ? this.oldTUID != null : !OLD_TUID_EDEFAULT.equals(this.oldTUID);
        case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
            return this.feature2OldValueMap != null;
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
                return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
                return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.REMOVE_EREFERENCE__OLD_TUID:
                return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID;
            case ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP:
                return ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP;
            case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
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
        if (baseClass == EFeatureChange.class) {
            switch (baseFeatureID) {
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                return ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE;
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                return ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT;
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return ReferencePackage.REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEReferenceChange.class) {
            switch (baseFeatureID) {
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID:
                return ReferencePackage.REMOVE_EREFERENCE__OLD_TUID;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP:
                return ReferencePackage.REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP;
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE:
                return ReferencePackage.REMOVE_EREFERENCE__IS_DELETE;
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
        if (baseClass == EFeatureChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseOperationID) {
            case ReferencePackage.UPDATE_EREFERENCE___IS_CONTAINMENT:
                return ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT;
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveEChange.class) {
            switch (baseOperationID) {
            case ChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE:
                return ReferencePackage.REMOVE_EREFERENCE___GET_OLD_VALUE;
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
        case ReferencePackage.REMOVE_EREFERENCE___GET_OLD_VALUE:
            return getOldValue();
        case ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT:
            return isContainment();
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
        result.append(" (oldTUIDOfAffectedEObject: ");
        result.append(this.oldTUIDOfAffectedEObject);
        result.append(", oldTUID: ");
        result.append(this.oldTUID);
        result.append(", feature2OldValueMap: ");
        result.append(this.feature2OldValueMap);
        result.append(", isDelete: ");
        result.append(this.isDelete);
        result.append(')');
        return result.toString();
    }

} // RemoveEReferenceImpl
