/** 
 */
package tools.vitruv.framework.versioning

import org.eclipse.emf.ecore.EFactory

/** 
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.VersioningPackage
 * @generated
 */
interface VersioningFactory extends EFactory {
	/** 
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VersioningFactory eINSTANCE = tools.vitruv.framework.versioning.impl.VersioningFactoryImpl::init()

	/** 
	 * Returns a new object of class '<em>Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root</em>'.
	 * @generated
	 */
	def Root createRoot()

	/** 
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	def VersioningPackage getVersioningPackage()
// VersioningFactory
}
