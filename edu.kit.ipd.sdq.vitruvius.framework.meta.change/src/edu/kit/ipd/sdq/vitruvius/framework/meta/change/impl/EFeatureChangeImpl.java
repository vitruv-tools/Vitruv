/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange;

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
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EFeatureChangeImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EFeatureChangeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 * </p>
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
	@SuppressWarnings("unchecked")
	public T getAffectedFeature() {
        if (affectedFeature != null && affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
            affectedFeature = (T)eResolveProxy(oldAffectedFeature);
            if (affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                if (resolve) return getAffectedFeature();
                return basicGetAffectedFeature();
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                if (resolve) return getAffectedEObject();
                return basicGetAffectedEObject();
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
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                setAffectedFeature((T)newValue);
                return;
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)newValue);
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
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                setAffectedFeature((T)null);
                return;
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                setAffectedEObject((EObject)null);
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
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                return affectedFeature != null;
            case ChangePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                return affectedEObject != null;
        }
        return super.eIsSet(featureID);
    }

} //EFeatureChangeImpl
