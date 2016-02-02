/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.InsertInEListImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Insert EReference</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getAffectedFeature
 * <em>Affected Feature</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getAffectedEObject
 * <em>Affected EObject</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getOldTUIDOfAffectedEObject
 * <em>Old TUID Of Affected EObject</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getNewValue
 * <em>New Value</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#isIsCreate
 * <em>Is Create</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsertEReferenceImpl<T extends EObject> extends InsertInEListImpl<T>implements InsertEReference<T> {
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
     * The cached value of the '{@link #getNewValue() <em>New Value</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNewValue()
     * @generated
     * @ordered
     */
    protected T newValue;

    /**
     * The default value of the '{@link #isIsCreate() <em>Is Create</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isIsCreate()
     * @generated
     * @ordered
     */
    protected static final boolean IS_CREATE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsCreate() <em>Is Create</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isIsCreate()
     * @generated
     * @ordered
     */
    protected boolean isCreate = IS_CREATE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected InsertEReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.INSERT_EREFERENCE;
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
                            ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature,
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE,
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
                            ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject,
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT,
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
                    ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT, oldOldTUIDOfAffectedEObject,
                    this.oldTUIDOfAffectedEObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getNewValue() {
        if (this.newValue != null && this.newValue.eIsProxy()) {
            InternalEObject oldNewValue = (InternalEObject) this.newValue;
            this.newValue = (T) eResolveProxy(oldNewValue);
            if (this.newValue != oldNewValue) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.INSERT_EREFERENCE__NEW_VALUE, oldNewValue, this.newValue));
            }
        }
        return this.newValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public T basicGetNewValue() {
        return this.newValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setNewValue(final T newNewValue) {
        T oldNewValue = this.newValue;
        this.newValue = newNewValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__NEW_VALUE,
                    oldNewValue, this.newValue));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean isIsCreate() {
        return this.isCreate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setIsCreate(final boolean newIsCreate) {
        boolean oldIsCreate = this.isCreate;
        this.isCreate = newIsCreate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__IS_CREATE,
                    oldIsCreate, this.isCreate));
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
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
            if (resolve)
                return getAffectedFeature();
            return basicGetAffectedFeature();
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
            if (resolve)
                return getAffectedEObject();
            return basicGetAffectedEObject();
        case ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            return getOldTUIDOfAffectedEObject();
        case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
            if (resolve)
                return getNewValue();
            return basicGetNewValue();
        case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
            return isIsCreate();
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
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
            setAffectedFeature((EReference) newValue);
            return;
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
            setAffectedEObject((EObject) newValue);
            return;
        case ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            setOldTUIDOfAffectedEObject((TUID) newValue);
            return;
        case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
            setNewValue((T) newValue);
            return;
        case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
            setIsCreate((Boolean) newValue);
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
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
            setAffectedFeature((EReference) null);
            return;
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
            setAffectedEObject((EObject) null);
            return;
        case ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            setOldTUIDOfAffectedEObject(OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT);
            return;
        case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
            setNewValue((T) null);
            return;
        case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
            setIsCreate(IS_CREATE_EDEFAULT);
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
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
            return this.affectedFeature != null;
        case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
            return this.affectedEObject != null;
        case ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
            return OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT == null ? this.oldTUIDOfAffectedEObject != null
                    : !OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT.equals(this.oldTUIDOfAffectedEObject);
        case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
            return this.newValue != null;
        case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
            return this.isCreate != IS_CREATE_EDEFAULT;
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
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
                return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
                return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
            case ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT:
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
        if (baseClass == AdditiveEChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == AdditiveEReferenceChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
                return ChangePackage.ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE;
            case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
                return ChangePackage.ADDITIVE_EREFERENCE_CHANGE__IS_CREATE;
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
                return ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE;
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                return ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT;
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return ReferencePackage.INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT;
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
        if (baseClass == AdditiveEChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == AdditiveEReferenceChange.class) {
            switch (baseFeatureID) {
            case ChangePackage.ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE:
                return ReferencePackage.INSERT_EREFERENCE__NEW_VALUE;
            case ChangePackage.ADDITIVE_EREFERENCE_CHANGE__IS_CREATE:
                return ReferencePackage.INSERT_EREFERENCE__IS_CREATE;
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
                return ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT;
            default:
                return -1;
            }
        }
        if (baseClass == AdditiveEChange.class) {
            switch (baseOperationID) {
            case ChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE:
                return ReferencePackage.INSERT_EREFERENCE___GET_NEW_VALUE;
            default:
                return -1;
            }
        }
        if (baseClass == AdditiveEReferenceChange.class) {
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
        case ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT:
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
        result.append(", isCreate: ");
        result.append(this.isCreate);
        result.append(')');
        return result.toString();
    }

} // InsertEReferenceImpl
