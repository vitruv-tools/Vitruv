/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EFeature Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getMappedFeature <em>Mapped Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EFeatureCorrespondenceImpl<T extends Object, TFeature extends EStructuralFeature> extends SameTypeCorrespondenceImpl<T> implements EFeatureCorrespondence<T, TFeature> {
	/**
	 * The cached value of the '{@link #getMappedFeature() <em>Mapped Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedFeature()
	 * @generated
	 * @ordered
	 */
	protected TFeature mappedFeature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EFeatureCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.EFEATURE_CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setElementA(T newElementA) {
		super.setElementA(newElementA);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setElementB(T newElementB) {
		super.setElementB(newElementB);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public TFeature getMappedFeature() {
		if (mappedFeature != null && mappedFeature.eIsProxy()) {
			InternalEObject oldMappedFeature = (InternalEObject)mappedFeature;
			mappedFeature = (TFeature)eResolveProxy(oldMappedFeature);
			if (mappedFeature != oldMappedFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.EFEATURE_CORRESPONDENCE__MAPPED_FEATURE, oldMappedFeature, mappedFeature));
			}
		}
		return mappedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFeature basicGetMappedFeature() {
		return mappedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMappedFeature(TFeature newMappedFeature) {
		TFeature oldMappedFeature = mappedFeature;
		mappedFeature = newMappedFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.EFEATURE_CORRESPONDENCE__MAPPED_FEATURE, oldMappedFeature, mappedFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__MAPPED_FEATURE:
				if (resolve) return getMappedFeature();
				return basicGetMappedFeature();
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
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__MAPPED_FEATURE:
				setMappedFeature((TFeature)newValue);
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
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__MAPPED_FEATURE:
				setMappedFeature((TFeature)null);
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
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__MAPPED_FEATURE:
				return mappedFeature != null;
		}
		return super.eIsSet(featureID);
	}

} //EFeatureCorrespondenceImpl
