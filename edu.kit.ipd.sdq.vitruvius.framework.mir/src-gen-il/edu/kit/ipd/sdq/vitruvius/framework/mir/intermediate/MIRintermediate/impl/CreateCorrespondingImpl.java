/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.CreateCorresponding;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create Corresponding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.CreateCorrespondingImpl#getFeatureLeft <em>Feature Left</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.CreateCorrespondingImpl#getFeatureRight <em>Feature Right</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CreateCorrespondingImpl extends InitializerImpl implements CreateCorresponding {
	/**
	 * The cached value of the '{@link #getFeatureLeft() <em>Feature Left</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureLeft()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> featureLeft;

	/**
	 * The cached value of the '{@link #getFeatureRight() <em>Feature Right</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureRight()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> featureRight;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateCorrespondingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.CREATE_CORRESPONDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EStructuralFeature> getFeatureLeft() {
		if (featureLeft == null) {
			featureLeft = new EObjectResolvingEList<EStructuralFeature>(EStructuralFeature.class, this, MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_LEFT);
		}
		return featureLeft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EStructuralFeature> getFeatureRight() {
		if (featureRight == null) {
			featureRight = new EObjectResolvingEList<EStructuralFeature>(EStructuralFeature.class, this, MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_RIGHT);
		}
		return featureRight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_LEFT:
				return getFeatureLeft();
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_RIGHT:
				return getFeatureRight();
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
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_LEFT:
				getFeatureLeft().clear();
				getFeatureLeft().addAll((Collection<? extends EStructuralFeature>)newValue);
				return;
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_RIGHT:
				getFeatureRight().clear();
				getFeatureRight().addAll((Collection<? extends EStructuralFeature>)newValue);
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
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_LEFT:
				getFeatureLeft().clear();
				return;
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_RIGHT:
				getFeatureRight().clear();
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
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_LEFT:
				return featureLeft != null && !featureLeft.isEmpty();
			case MIRintermediatePackage.CREATE_CORRESPONDING__FEATURE_RIGHT:
				return featureRight != null && !featureRight.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CreateCorrespondingImpl
