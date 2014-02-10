/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EFeatureChange;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EFeature Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl.EFeatureChangeImpl#getChangedFeatureType <em>Changed Feature Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EFeatureChangeImpl extends EChangeImpl implements EFeatureChange {
    /**
     * The cached value of the '{@link #getChangedFeatureType() <em>Changed Feature Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChangedFeatureType()
     * @generated
     * @ordered
     */
    protected EObject changedFeatureType;
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
        return ChangePackage.Literals.EFEATURE_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getChangedFeatureType() {
        if (changedFeatureType != null && changedFeatureType.eIsProxy()) {
            InternalEObject oldChangedFeatureType = (InternalEObject)changedFeatureType;
            changedFeatureType = eResolveProxy(oldChangedFeatureType);
            if (changedFeatureType != oldChangedFeatureType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE, oldChangedFeatureType, changedFeatureType));
            }
        }
        return changedFeatureType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject basicGetChangedFeatureType() {
        return changedFeatureType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setChangedFeatureType(EObject newChangedFeatureType) {
        EObject oldChangedFeatureType = changedFeatureType;
        changedFeatureType = newChangedFeatureType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE, oldChangedFeatureType, changedFeatureType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE:
                if (resolve) return getChangedFeatureType();
                return basicGetChangedFeatureType();
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
            case ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE:
                setChangedFeatureType((EObject)newValue);
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
            case ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE:
                setChangedFeatureType((EObject)null);
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
            case ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE:
                return changedFeatureType != null;
        }
        return super.eIsSet(featureID);
    }

} //EFeatureChangeImpl
