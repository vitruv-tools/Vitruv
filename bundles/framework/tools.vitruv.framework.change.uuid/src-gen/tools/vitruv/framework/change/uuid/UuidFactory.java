/**
 */
package tools.vitruv.framework.change.uuid;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.uuid.UuidPackage
 * @generated
 */
public interface UuidFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UuidFactory eINSTANCE = tools.vitruv.framework.change.uuid.impl.UuidFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>To EObject Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>To EObject Repository</em>'.
	 * @generated
	 */
	UuidToEObjectRepository createUuidToEObjectRepository();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UuidPackage getUuidPackage();

} //UuidFactory
