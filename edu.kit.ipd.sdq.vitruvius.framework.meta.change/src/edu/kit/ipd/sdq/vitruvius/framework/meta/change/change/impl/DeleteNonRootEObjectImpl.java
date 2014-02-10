/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEContainmentReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEReference;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete Non Root EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl.DeleteNonRootEObjectImpl#getChangedFeatureType <em>Changed Feature Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl.DeleteNonRootEObjectImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeleteNonRootEObjectImpl<T extends EObject> extends DeleteRootEObjectImpl implements DeleteNonRootEObject<T> {
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
    protected DeleteNonRootEObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.DELETE_NON_ROOT_EOBJECT;
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE, oldChangedFeatureType, changedFeatureType));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE, oldChangedFeatureType, changedFeatureType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public T getNewValue() {
        if (newValue != null && ((EObject)newValue).eIsProxy()) {
            InternalEObject oldNewValue = (InternalEObject)newValue;
            newValue = (T)eResolveProxy(oldNewValue);
            if (newValue != oldNewValue) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE, oldNewValue, newValue));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE, oldNewValue, newValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE:
                if (resolve) return getChangedFeatureType();
                return basicGetChangedFeatureType();
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE:
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
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE:
                setChangedFeatureType((EObject)newValue);
                return;
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE:
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
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE:
                setChangedFeatureType((EObject)null);
                return;
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE:
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
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE:
                return changedFeatureType != null;
            case ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE:
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
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
                case ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE: return ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE;
                default: return -1;
            }
        }
        if (baseClass == UpdateEFeature.class) {
            switch (derivedFeatureID) {
                case ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE: return ChangePackage.UPDATE_EFEATURE__NEW_VALUE;
                default: return -1;
            }
        }
        if (baseClass == EReferenceChange.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateEContainmentReference.class) {
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
                case ChangePackage.EFEATURE_CHANGE__CHANGED_FEATURE_TYPE: return ChangePackage.DELETE_NON_ROOT_EOBJECT__CHANGED_FEATURE_TYPE;
                default: return -1;
            }
        }
        if (baseClass == UpdateEFeature.class) {
            switch (baseFeatureID) {
                case ChangePackage.UPDATE_EFEATURE__NEW_VALUE: return ChangePackage.DELETE_NON_ROOT_EOBJECT__NEW_VALUE;
                default: return -1;
            }
        }
        if (baseClass == EReferenceChange.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateEContainmentReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} //DeleteNonRootEObjectImpl
