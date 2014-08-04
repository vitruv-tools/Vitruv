/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.RemoveFromEListImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateNonContainmentEReference;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove Non Containment EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.RemoveNonContainmentEReferenceImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.RemoveNonContainmentEReferenceImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemoveNonContainmentEReferenceImpl<T extends EObject> extends RemoveFromEListImpl<T> implements RemoveNonContainmentEReference<T> {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveNonContainmentEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.REMOVE_NON_CONTAINMENT_EREFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setOldValue(T newOldValue) {
		super.setOldValue(newOldValue);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE:
				if (resolve) return getAffectedFeature();
				return basicGetAffectedFeature();
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE:
				setAffectedFeature((EReference)newValue);
				return;
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT:
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
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE:
				setAffectedFeature((EReference)null);
				return;
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT:
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
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE:
				return affectedFeature != null;
			case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT:
				return affectedEObject != null;
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
		if (baseClass == EChange.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (derivedFeatureID) {
				case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
				case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
				default: return -1;
			}
		}
		if (baseClass == UpdateEReference.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateNonContainmentEReference.class) {
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
		if (baseClass == EChange.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (baseFeatureID) {
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE;
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_EOBJECT;
				default: return -1;
			}
		}
		if (baseClass == UpdateEReference.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateNonContainmentEReference.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //RemoveNonContainmentEReferenceImpl
