/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage
 * @generated
 */
public interface ObjectFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ObjectFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Create Root EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Root EObject</em>'.
	 * @generated
	 */
	<T extends EObject> CreateRootEObject<T> createCreateRootEObject();

	/**
	 * Returns a new object of class '<em>Replace Root EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Replace Root EObject</em>'.
	 * @generated
	 */
	<T extends EObject> ReplaceRootEObject<T> createReplaceRootEObject();

	/**
	 * Returns a new object of class '<em>Delete Root EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delete Root EObject</em>'.
	 * @generated
	 */
	<T extends EObject> DeleteRootEObject<T> createDeleteRootEObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ObjectPackage getObjectPackage();

} //ObjectFactory
