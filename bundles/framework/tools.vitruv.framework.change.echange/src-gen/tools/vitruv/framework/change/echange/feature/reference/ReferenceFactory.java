/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage
 * @generated
 */
public interface ReferenceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReferenceFactory eINSTANCE = tools.vitruv.framework.change.echange.feature.reference.impl.ReferenceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Insert EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Insert EReference</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> InsertEReference<A, T> createInsertEReference();

	/**
	 * Returns a new object of class '<em>Remove EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove EReference</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> RemoveEReference<A, T> createRemoveEReference();

	/**
	 * Returns a new object of class '<em>Replace Single Valued EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Replace Single Valued EReference</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A, T> createReplaceSingleValuedEReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ReferencePackage getReferencePackage();

} //ReferenceFactory
