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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl#isIsCreate <em>Is Create</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsertEReferenceImpl<A extends EObject, T extends EObject> extends InsertInEListImpl
        implements InsertEReference<A, T> {
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
     * @generated
     */
    protected InsertEReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.INSERT_EREFERENCE;
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getNewValue() {
        if (newValue != null && newValue.eIsProxy()) {
            InternalEObject oldNewValue = (InternalEObject)newValue;
            newValue = (T)eResolveProxy(oldNewValue);
            if (newValue != oldNewValue) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.INSERT_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
            }
        }
        return newValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public T basicGetNewValue() {
        return newValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setNewValue(T newNewValue) {
        T oldNewValue = newValue;
        newValue = newNewValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__NEW_VALUE, oldNewValue, newValue));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isIsCreate() {
        return isCreate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setIsCreate(boolean newIsCreate) {
        boolean oldIsCreate = isCreate;
        isCreate = newIsCreate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.INSERT_EREFERENCE__IS_CREATE, oldIsCreate, isCreate));
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
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
            case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
                if (resolve) return getNewValue();
                return basicGetNewValue();
            case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
                return isIsCreate();
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
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)newValue);
                return;
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
                setAffectedEObject((A)newValue);
                return;
            case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
                setNewValue((T)newValue);
                return;
            case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
                setIsCreate((Boolean)newValue);
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
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)null);
                return;
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
                setAffectedEObject((A)null);
                return;
            case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
                setNewValue((T)null);
                return;
            case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
                setIsCreate(IS_CREATE_EDEFAULT);
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
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT:
                return affectedEObject != null;
            case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE:
                return newValue != null;
            case ReferencePackage.INSERT_EREFERENCE__IS_CREATE:
                return isCreate != IS_CREATE_EDEFAULT;
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
                case ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveEChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveEReferenceChange.class) {
            switch (derivedFeatureID) {
                case ReferencePackage.INSERT_EREFERENCE__NEW_VALUE: return ChangePackage.ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE;
                case ReferencePackage.INSERT_EREFERENCE__IS_CREATE: return ChangePackage.ADDITIVE_EREFERENCE_CHANGE__IS_CREATE;
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
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ReferencePackage.INSERT_EREFERENCE__AFFECTED_FEATURE;
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ReferencePackage.INSERT_EREFERENCE__AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveEChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveEReferenceChange.class) {
            switch (baseFeatureID) {
                case ChangePackage.ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE: return ReferencePackage.INSERT_EREFERENCE__NEW_VALUE;
                case ChangePackage.ADDITIVE_EREFERENCE_CHANGE__IS_CREATE: return ReferencePackage.INSERT_EREFERENCE__IS_CREATE;
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
                case ReferencePackage.UPDATE_EREFERENCE___IS_CONTAINMENT: return ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT;
                default: return -1;
            }
        }
        if (baseClass == AdditiveEChange.class) {
            switch (baseOperationID) {
                case ChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE: return ReferencePackage.INSERT_EREFERENCE___GET_NEW_VALUE;
                default: return -1;
            }
        }
        if (baseClass == AdditiveEReferenceChange.class) {
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
            case ReferencePackage.INSERT_EREFERENCE___IS_CONTAINMENT:
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
        result.append(" (isCreate: ");
        result.append(isCreate);
        result.append(')');
        return result.toString();
    }

} // InsertEReferenceImpl
