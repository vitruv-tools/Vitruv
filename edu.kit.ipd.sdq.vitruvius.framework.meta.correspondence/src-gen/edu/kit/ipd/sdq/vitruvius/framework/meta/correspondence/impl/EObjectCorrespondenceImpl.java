/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;

import java.io.Serializable;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
//TODO MK (deco): try to generate use of serializable marker interface from ecore mm 
public class EObjectCorrespondenceImpl extends SameTypeCorrespondenceImpl implements EObjectCorrespondence, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6183080556480686548L;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObjectCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.EOBJECT_CORRESPONDENCE;
	}

} //EObjectCorrespondenceImpl
