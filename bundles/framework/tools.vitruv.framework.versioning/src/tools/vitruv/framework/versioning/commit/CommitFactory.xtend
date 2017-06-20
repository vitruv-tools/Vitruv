/** 
 */
package tools.vitruv.framework.versioning.commit

import org.eclipse.emf.ecore.EFactory

/** 
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.commit.CommitPackage
 * @generated
 */
interface CommitFactory extends EFactory {
	/** 
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CommitFactory eINSTANCE = tools.vitruv.framework.versioning.commit.impl.CommitFactoryImpl::init()

	/** 
	 * Returns a new object of class '<em>Merge Commit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Commit</em>'.
	 * @generated
	 */
	def MergeCommit createMergeCommit()

	/** 
	 * Returns a new object of class '<em>Simple Commit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Commit</em>'.
	 * @generated
	 */
	def SimpleCommit createSimpleCommit()

	/** 
	 * Returns a new object of class '<em>Message</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Message</em>'.
	 * @generated
	 */
	def CommitMessage createCommitMessage()

	/** 
	 * Returns a new object of class '<em>Initial Commit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Commit</em>'.
	 * @generated
	 */
	def InitialCommit createInitialCommit()

	/** 
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	def CommitPackage getCommitPackage()
// CommitFactory
}
