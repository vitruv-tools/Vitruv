/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage
 * @generated
 */
public interface EobjectFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EobjectFactory eINSTANCE = tools.vitruv.framework.change.echange.eobject.impl.EobjectFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Create EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create EObject</em>'.
	 * @generated
	 */
	<A extends EObject> CreateEObject<A> createCreateEObject();

	/**
	 * Returns a new object of class '<em>Delete EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delete EObject</em>'.
	 * @generated
	 */
	<A extends EObject> DeleteEObject<A> createDeleteEObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EobjectPackage getEobjectPackage();

} //EobjectFactory
