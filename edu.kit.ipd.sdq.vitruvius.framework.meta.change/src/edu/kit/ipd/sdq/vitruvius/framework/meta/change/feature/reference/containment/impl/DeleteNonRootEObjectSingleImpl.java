/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateSingleValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateSingleValuedContainmentEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteEObjectImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete Non Root EObject Single</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl#getOldAffectedEObject <em>Old Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl#getNewAffectedEObject <em>New Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl#getOldValue <em>Old Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeleteNonRootEObjectSingleImpl<T extends EObject> extends DeleteEObjectImpl<T> implements DeleteNonRootEObjectSingle<T> {
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
     * The cached value of the '{@link #getOldAffectedEObject() <em>Old Affected EObject</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getOldAffectedEObject()
     * @generated
     * @ordered
     */
	protected EObject oldAffectedEObject;

	/**
     * The cached value of the '{@link #getNewAffectedEObject() <em>New Affected EObject</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getNewAffectedEObject()
     * @generated
     * @ordered
     */
	protected EObject newAffectedEObject;

	/**
     * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getOldValue()
     * @generated
     * @ordered
     */
	protected T oldValue;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected DeleteNonRootEObjectSingleImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ContainmentPackage.Literals.DELETE_NON_ROOT_EOBJECT_SINGLE;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public T getOldValue() {
        return oldValue;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setOldValue(T newOldValue) {
        T oldOldValue = oldValue;
        oldValue = newOldValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE, oldOldValue, oldValue));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject getOldAffectedEObject() {
        if (oldAffectedEObject != null && oldAffectedEObject.eIsProxy()) {
            InternalEObject oldOldAffectedEObject = (InternalEObject)oldAffectedEObject;
            oldAffectedEObject = eResolveProxy(oldOldAffectedEObject);
            if (oldAffectedEObject != oldOldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT, oldOldAffectedEObject, oldAffectedEObject));
            }
        }
        return oldAffectedEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject basicGetOldAffectedEObject() {
        return oldAffectedEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setOldAffectedEObject(EObject newOldAffectedEObject) {
        EObject oldOldAffectedEObject = oldAffectedEObject;
        oldAffectedEObject = newOldAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT, oldOldAffectedEObject, oldAffectedEObject));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject getNewAffectedEObject() {
        if (newAffectedEObject != null && newAffectedEObject.eIsProxy()) {
            InternalEObject oldNewAffectedEObject = (InternalEObject)newAffectedEObject;
            newAffectedEObject = eResolveProxy(oldNewAffectedEObject);
            if (newAffectedEObject != oldNewAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT, oldNewAffectedEObject, newAffectedEObject));
            }
        }
        return newAffectedEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject basicGetNewAffectedEObject() {
        return newAffectedEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setNewAffectedEObject(EObject newNewAffectedEObject) {
        EObject oldNewAffectedEObject = newAffectedEObject;
        newAffectedEObject = newNewAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT, oldNewAffectedEObject, newAffectedEObject));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT:
                if (resolve) return getOldAffectedEObject();
                return basicGetOldAffectedEObject();
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT:
                if (resolve) return getNewAffectedEObject();
                return basicGetNewAffectedEObject();
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE:
                return getOldValue();
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
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)newValue);
                return;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT:
                setOldAffectedEObject((EObject)newValue);
                return;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT:
                setNewAffectedEObject((EObject)newValue);
                return;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE:
                setOldValue((T)newValue);
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
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE:
                setAffectedFeature((EReference)null);
                return;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT:
                setOldAffectedEObject((EObject)null);
                return;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT:
                setNewAffectedEObject((EObject)null);
                return;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE:
                setOldValue((T)null);
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
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT:
                return oldAffectedEObject != null;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT:
                return newAffectedEObject != null;
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE:
                return oldValue != null;
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
        if (baseClass == UpdateEFeature.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateSingleValuedEFeature.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
                case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
                case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT;
                case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateContainmentEReference.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateSingleValuedContainmentEReference.class) {
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
        if (baseClass == UpdateEFeature.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateSingleValuedEFeature.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == EFeatureChange.class) {
            switch (baseFeatureID) {
                case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE;
                case FeaturePackage.EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT;
                case FeaturePackage.EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT;
                default: return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateContainmentEReference.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == UpdateSingleValuedContainmentEReference.class) {
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
	public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (oldValue: ");
        result.append(oldValue);
        result.append(')');
        return result.toString();
    }

} //DeleteNonRootEObjectSingleImpl
