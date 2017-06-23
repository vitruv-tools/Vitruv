/** 
 */
package tools.vitruv.framework.versioning.conflict

import org.eclipse.emf.ecore.EFactory

/** 
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage
 * @generated
 */
interface ConflictFactory extends EFactory {
	/** 
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConflictFactory eINSTANCE = tools.vitruv.framework.versioning.conflict.impl.ConflictFactoryImpl::init()

	/** 
	 * Returns a new object of class '<em>Simple Change Conflict</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Change Conflict</em>'.
	 * @generated
	 */
	def SimpleChangeConflict createSimpleChangeConflict()

	/** 
	 * Returns a new object of class '<em>Multi Change Conflict</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multi Change Conflict</em>'.
	 * @generated
	 */
	def MultiChangeConflict createMultiChangeConflict()

	/** 
	 * Returns a new object of class '<em>Detector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Detector</em>'.
	 * @generated
	 */
	def ConflictDetector createConflictDetector()

	/** 
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	def ConflictPackage getConflictPackage()
// ConflictFactory
}
