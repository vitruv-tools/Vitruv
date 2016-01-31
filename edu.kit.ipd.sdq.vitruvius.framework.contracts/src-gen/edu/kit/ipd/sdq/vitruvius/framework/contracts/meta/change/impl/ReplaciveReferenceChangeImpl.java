/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replacive Reference Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveReferenceChangeImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ReplaciveReferenceChangeImpl<T extends EObject> extends SubtractiveReferenceChangeImpl implements ReplaciveReferenceChange<T> {
    /**
     * The cached value of the '{@link #getNewValue() <em>New Value</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNewValue()
     * @generated
     * @ordered
     */
    protected T newValue;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ReplaciveReferenceChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.REPLACIVE_REFERENCE_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public T getNewValue() {
        if (newValue != null && newValue.eIsProxy()) {
            InternalEObject oldNewValue = (InternalEObject)newValue;
            newValue = (T)eResolveProxy(oldNewValue);
            if (newValue != oldNewValue) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE, oldNewValue, newValue));
            }
        }
        return newValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public T basicGetNewValue() {
        return newValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNewValue(T newNewValue) {
        T oldNewValue = newValue;
        newValue = newNewValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE, oldNewValue, newValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE:
                if (resolve) return getNewValue();
                return basicGetNewValue();
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
            case ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE:
                setNewValue((T)newValue);
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
            case ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE:
                setNewValue((T)null);
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
            case ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE:
                return newValue != null;
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
        if (baseClass == AdditiveChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveReferenceChange.class) {
            switch (derivedFeatureID) {
                case ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE: return ChangePackage.ADDITIVE_REFERENCE_CHANGE__NEW_VALUE;
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
        if (baseClass == AdditiveChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == AdditiveReferenceChange.class) {
            switch (baseFeatureID) {
                case ChangePackage.ADDITIVE_REFERENCE_CHANGE__NEW_VALUE: return ChangePackage.REPLACIVE_REFERENCE_CHANGE__NEW_VALUE;
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} //ReplaciveReferenceChangeImpl
