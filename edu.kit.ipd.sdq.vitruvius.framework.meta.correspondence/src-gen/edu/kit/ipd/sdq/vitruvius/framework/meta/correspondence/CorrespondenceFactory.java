/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage
 * @generated
 */
public interface CorrespondenceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CorrespondenceFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Correspondences</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Correspondences</em>'.
	 * @generated
	 */
	Correspondences createCorrespondences();

	/**
	 * Returns a new object of class '<em>EObject Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EObject Correspondence</em>'.
	 * @generated
	 */
	EObjectCorrespondence createEObjectCorrespondence();

	/**
	 * Returns a new object of class '<em>EAttribute Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EAttribute Correspondence</em>'.
	 * @generated
	 */
	EAttributeCorrespondence createEAttributeCorrespondence();

	/**
	 * Returns a new object of class '<em>EReference Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EReference Correspondence</em>'.
	 * @generated
	 */
	EReferenceCorrespondence createEReferenceCorrespondence();

	/**
	 * Returns a new object of class '<em>EContainment Reference Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EContainment Reference Correspondence</em>'.
	 * @generated
	 */
	EContainmentReferenceCorrespondence createEContainmentReferenceCorrespondence();

	/**
	 * Returns a new object of class '<em>Partial EAttribute Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Partial EAttribute Correspondence</em>'.
	 * @generated
	 */
	<TValue extends Object> PartialEAttributeCorrespondence<TValue> createPartialEAttributeCorrespondence();

	/**
	 * Returns a new object of class '<em>Partial EReference Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Partial EReference Correspondence</em>'.
	 * @generated
	 */
	<TValue extends EObject> PartialEReferenceCorrespondence<TValue> createPartialEReferenceCorrespondence();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorrespondencePackage getCorrespondencePackage();

} //CorrespondenceFactory
