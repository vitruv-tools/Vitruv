/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureEClassifierCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature EClassifier Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureEClassifierCorrespondenceImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureEClassifierCorrespondenceImpl#getEClassifier <em>EClassifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureEClassifierCorrespondenceImpl extends MinimalEObjectImpl.Container implements FeatureEClassifierCorrespondence {
	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature feature;

	/**
	 * The cached value of the '{@link #getEClassifier() <em>EClassifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEClassifier()
	 * @generated
	 * @ordered
	 */
	protected EClassifier eClassifier;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureEClassifierCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.FEATURE_ECLASSIFIER_CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature getFeature() {
		if (feature != null && feature.eIsProxy()) {
			InternalEObject oldFeature = (InternalEObject)feature;
			feature = (EStructuralFeature)eResolveProxy(oldFeature);
			if (feature != oldFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE, oldFeature, feature));
			}
		}
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(EStructuralFeature newFeature) {
		EStructuralFeature oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE, oldFeature, feature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier getEClassifier() {
		if (eClassifier != null && eClassifier.eIsProxy()) {
			InternalEObject oldEClassifier = (InternalEObject)eClassifier;
			eClassifier = (EClassifier)eResolveProxy(oldEClassifier);
			if (eClassifier != oldEClassifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER, oldEClassifier, eClassifier));
			}
		}
		return eClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetEClassifier() {
		return eClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEClassifier(EClassifier newEClassifier) {
		EClassifier oldEClassifier = eClassifier;
		eClassifier = newEClassifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER, oldEClassifier, eClassifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE:
				if (resolve) return getFeature();
				return basicGetFeature();
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER:
				if (resolve) return getEClassifier();
				return basicGetEClassifier();
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
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE:
				setFeature((EStructuralFeature)newValue);
				return;
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER:
				setEClassifier((EClassifier)newValue);
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
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE:
				setFeature((EStructuralFeature)null);
				return;
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER:
				setEClassifier((EClassifier)null);
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
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE:
				return feature != null;
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER:
				return eClassifier != null;
		}
		return super.eIsSet(featureID);
	}

} //FeatureEClassifierCorrespondenceImpl
