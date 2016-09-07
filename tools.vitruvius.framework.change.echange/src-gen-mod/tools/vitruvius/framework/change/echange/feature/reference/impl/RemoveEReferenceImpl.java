/**
 */
package tools.vitruvius.framework.change.echange.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruvius.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruvius.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruvius.framework.change.echange.feature.reference.UpdateReferenceEChange;
import tools.vitruvius.framework.change.echange.AtomicEChange;
import tools.vitruvius.framework.change.echange.ChangePackage;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.EObjectSubtractedEChange;
import tools.vitruvius.framework.change.echange.SubtractiveEChange;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.change.echange.feature.FeaturePackage;
import tools.vitruvius.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Remove EReference</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#getOldValue
 * <em>Old Value</em>}</li>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#isIsDelete
 * <em>Is Delete</em>}</li>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#getAffectedFeature
 * <em>Affected Feature</em>}</li>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl#getAffectedEObject
 * <em>Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RemoveEReferenceImpl<A extends EObject, T extends EObject> extends RemoveFromListEChangeImpl<A, EReference>
        implements RemoveEReference<A, T> {
    /**
     * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOldValue()
     * @generated
     * @ordered
     */
    protected T oldValue;

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
    protected A affectedEObject;

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
    @SuppressWarnings("unchecked")
    public T getOldValue() {
        if (this.oldValue != null && this.oldValue.eIsProxy()) {
            InternalEObject oldOldValue = (InternalEObject) this.oldValue;
            this.oldValue = (T) eResolveProxy(oldOldValue);
            if (this.oldValue != oldOldValue) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE, oldOldValue, this.oldValue));
            }
        }
        return this.oldValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public T basicGetOldValue() {
        return this.oldValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOldValue(final T newOldValue) {
        T oldOldValue = this.oldValue;
        this.oldValue = newOldValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE,
                    oldOldValue, this.oldValue));
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
    @Override
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
    @SuppressWarnings("unchecked")
    public A getAffectedEObject() {
        if (this.affectedEObject != null && this.affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject) this.affectedEObject;
            this.affectedEObject = (A) eResolveProxy(oldAffectedEObject);
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
    @Override
    public A basicGetAffectedEObject() {
        return this.affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAffectedEObject(final A newAffectedEObject) {
        A oldAffectedEObject = this.affectedEObject;
        this.affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT,
                    oldAffectedEObject, this.affectedEObject));
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
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
            if (resolve)
                return getOldValue();
            return basicGetOldValue();
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
            return isIsDelete();
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            if (resolve)
                return getAffectedFeature();
            return basicGetAffectedFeature();
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            if (resolve)
                return getAffectedEObject();
            return basicGetAffectedEObject();
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
        case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
            setOldValue((T) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
            setIsDelete((Boolean) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            setAffectedFeature((EReference) newValue);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            setAffectedEObject((A) newValue);
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
        case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
            setOldValue((T) null);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
            setIsDelete(IS_DELETE_EDEFAULT);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            setAffectedFeature((EReference) null);
            return;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            setAffectedEObject((A) null);
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
        case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
            return this.oldValue != null;
        case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
            return this.isDelete != IS_DELETE_EDEFAULT;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
            return this.affectedFeature != null;
        case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
            return this.affectedEObject != null;
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
        if (baseClass == EChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
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
        if (baseClass == EObjectSubtractedEChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE:
                return ChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE;
            case ReferencePackage.REMOVE_EREFERENCE__IS_DELETE:
                return ChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE;
            default:
                return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE:
                return FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE;
            case ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT:
                return FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateReferenceEChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveReferenceEChange.class) {
            switch (derivedFeatureID) {
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
        if (baseClass == EChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
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
        if (baseClass == EObjectSubtractedEChange.class) {
            switch (baseFeatureID) {
            case ChangePackage.EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE:
                return ReferencePackage.REMOVE_EREFERENCE__OLD_VALUE;
            case ChangePackage.EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE:
                return ReferencePackage.REMOVE_EREFERENCE__IS_DELETE;
            default:
                return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (baseFeatureID) {
            case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE:
                return ReferencePackage.REMOVE_EREFERENCE__AFFECTED_FEATURE;
            case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT:
                return ReferencePackage.REMOVE_EREFERENCE__AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateReferenceEChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveReferenceEChange.class) {
            switch (baseFeatureID) {
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
        if (baseClass == EChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
            switch (baseOperationID) {
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
        if (baseClass == EObjectSubtractedEChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == UpdateReferenceEChange.class) {
            switch (baseOperationID) {
            case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT:
                return ReferencePackage.REMOVE_EREFERENCE___IS_CONTAINMENT;
            default:
                return -1;
            }
        }
        if (baseClass == SubtractiveReferenceEChange.class) {
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
        result.append(" (isDelete: ");
        result.append(this.isDelete);
        result.append(')');
        return result.toString();
    }

} // RemoveEReferenceImpl
