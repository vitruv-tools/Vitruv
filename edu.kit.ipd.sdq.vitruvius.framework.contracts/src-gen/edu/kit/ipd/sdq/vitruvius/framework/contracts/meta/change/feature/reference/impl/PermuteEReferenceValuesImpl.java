/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteEListImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute EReference Values</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl#getOldTUIDOfAffectedEObject <em>Old TUID Of Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PermuteEReferenceValuesImpl<T extends EObject> extends PermuteEListImpl<T> implements PermuteEReferenceValues<T> {
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PermuteEReferenceValuesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.PERMUTE_EREFERENCE_VALUES;
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT, oldOldTUIDOfAffectedEObject, oldTUIDOfAffectedEObject));
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
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
                return getOldTUIDOfAffectedEObject();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
                setAffectedFeature((EReference)newValue);
                return;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)newValue);
                return;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
                setOldTUIDOfAffectedEObject((TUID)newValue);
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
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
                setAffectedFeature((EReference)null);
                return;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)null);
                return;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
                setOldTUIDOfAffectedEObject(OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT);
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
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
                return affectedEObject != null;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
                return OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT == null ? oldTUIDOfAffectedEObject != null : !OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT.equals(oldTUIDOfAffectedEObject);
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
                case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
                case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
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
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE;
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT;
                case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT: return ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
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
                case ReferencePackage.UPDATE_EREFERENCE___IS_CONTAINMENT: return ReferencePackage.PERMUTE_EREFERENCE_VALUES___IS_CONTAINMENT;
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
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES___IS_CONTAINMENT:
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
        result.append(')');
        return result.toString();
    }

} //PermuteEReferenceValuesImpl
