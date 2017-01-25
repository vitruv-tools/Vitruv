/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.root.RootPackage
 * @generated
 */
public interface RootFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RootFactory eINSTANCE = tools.vitruv.framework.change.echange.root.impl.RootFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Insert Root EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Insert Root EObject</em>'.
	 * @generated
	 */
	<T extends EObject> InsertRootEObject<T> createInsertRootEObject();

	/**
	 * Returns a new object of class '<em>Remove Root EObject</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove Root EObject</em>'.
	 * @generated
	 */
	<T extends EObject> RemoveRootEObject<T> createRemoveRootEObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RootPackage getRootPackage();

} //RootFactory
