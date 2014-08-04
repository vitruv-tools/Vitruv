/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage
 * @generated
 */
public interface ReferenceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReferenceFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferenceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Update Single Valued Non Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Update Single Valued Non Containment EReference</em>'.
	 * @generated
	 */
	<T extends EObject> UpdateSingleValuedNonContainmentEReference<T> createUpdateSingleValuedNonContainmentEReference();

	/**
	 * Returns a new object of class '<em>Insert Non Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Insert Non Containment EReference</em>'.
	 * @generated
	 */
	<T extends EObject> InsertNonContainmentEReference<T> createInsertNonContainmentEReference();

	/**
	 * Returns a new object of class '<em>Replace Non Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Replace Non Containment EReference</em>'.
	 * @generated
	 */
	<T extends EObject> ReplaceNonContainmentEReference<T> createReplaceNonContainmentEReference();

	/**
	 * Returns a new object of class '<em>Remove Non Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove Non Containment EReference</em>'.
	 * @generated
	 */
	<T extends EObject> RemoveNonContainmentEReference<T> createRemoveNonContainmentEReference();

	/**
	 * Returns a new object of class '<em>Permute Non Containment EReference Values</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Permute Non Containment EReference Values</em>'.
	 * @generated
	 */
	<T extends EObject> PermuteNonContainmentEReferenceValues<T> createPermuteNonContainmentEReferenceValues();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ReferencePackage getReferencePackage();

} //ReferenceFactory
