/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EAttribute Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class EAttributeCorrespondenceImpl extends EFeatureCorrespondenceImpl<EAttribute> implements EAttributeCorrespondence {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAttributeCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.EATTRIBUTE_CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setFeatureA(EAttribute newFeatureA) {
		super.setFeatureA(newFeatureA);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setFeatureB(EAttribute newFeatureB) {
		super.setFeatureB(newFeatureB);
	}

} //EAttributeCorrespondenceImpl
