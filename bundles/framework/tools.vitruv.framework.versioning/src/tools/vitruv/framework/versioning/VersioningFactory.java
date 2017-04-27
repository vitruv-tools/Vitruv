/**
 */
package tools.vitruv.framework.versioning;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.VersioningPackage
 * @generated
 */
public interface VersioningFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VersioningFactory eINSTANCE = tools.vitruv.framework.versioning.impl.VersioningFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Author</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Author</em>'.
	 * @generated
	 */
	Author createAuthor();

	/**
	 * Returns a new object of class '<em>Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tag</em>'.
	 * @generated
	 */
	Tag createTag();

	/**
	 * Returns a new object of class '<em>Signature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Signature</em>'.
	 * @generated
	 */
	Signature createSignature();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	VersioningPackage getVersioningPackage();

} //VersioningFactory
