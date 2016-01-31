/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EChangeImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EFeature Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.EFeatureChangeImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.EFeatureChangeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.EFeatureChangeImpl#getOldTUIDOfAffectedEObject <em>Old TUID Of Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EFeatureChangeImpl<T extends EStructuralFeature> extends EChangeImpl implements EFeatureChange<T> {
    /**
     * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAffectedFeature()
     * @generated
     * @ordered
     */
    protected T affectedFeature;

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
    protected EFeatureChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FeaturePackage.Literals.EFEATURE_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public T getAffectedFeature() {
        if (affectedFeature != null && affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
            affectedFeature = (T)eResolveProxy(oldAffectedFeature);
            if (affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
            }
        }
        return affectedFeature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public T basicGetAffectedFeature() {
        return affectedFeature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAffectedFeature(T newAffectedFeature) {
        T oldAffectedFeature = affectedFeature;
        affectedFeature = newAffectedFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT, oldOldTUIDOfAffectedEObject, oldTUIDOfAffectedEObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return getOldTUIDOfAffectedEObject();
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
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                setAffectedFeature((T)newValue);
                return;
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)newValue);
                return;
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
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
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                setAffectedFeature((T)null);
                return;
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)null);
                return;
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
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
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                return affectedEObject != null;
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
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
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (oldTUIDOfAffectedEObject: ");
        result.append(oldTUIDOfAffectedEObject);
        result.append(')');
        return result.toString();
    }

} //EFeatureChangeImpl
