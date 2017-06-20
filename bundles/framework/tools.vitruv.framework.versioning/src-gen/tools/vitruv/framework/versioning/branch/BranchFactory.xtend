/** 
 */
package tools.vitruv.framework.versioning.branch

import org.eclipse.emf.ecore.EFactory

/** 
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.branch.BranchPackage
 * @generated
 */
interface BranchFactory extends EFactory {
	/** 
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BranchFactory eINSTANCE = tools.vitruv.framework.versioning.branch.impl.BranchFactoryImpl::init()

	/** 
	 * Returns a new object of class '<em>Diff Creator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff Creator</em>'.
	 * @generated
	 */
	def BranchDiffCreator createBranchDiffCreator()

	/** 
	 * Returns a new object of class '<em>Diff</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff</em>'.
	 * @generated
	 */
	def BranchDiff createBranchDiff()

	/** 
	 * Returns a new object of class '<em>User Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>User Branch</em>'.
	 * @generated
	 */
	def UserBranch createUserBranch()

	/** 
	 * Returns a new object of class '<em>Master Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Master Branch</em>'.
	 * @generated
	 */
	def MasterBranch createMasterBranch()

	/** 
	 * Returns a new object of class '<em>Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Branch</em>'.
	 * @generated
	 */
	def Branch createBranch()

	/** 
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	def BranchPackage getBranchPackage()
// BranchFactory
}
