/** 
 */
package tools.vitruv.framework.versioning.branch.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.util.Switch
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.branch.BranchDiffCreator
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.MasterBranch
import tools.vitruv.framework.versioning.branch.UserBranch

/** 
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see BranchPackage
 * @generated
 */
class BranchSwitch<T> extends Switch<T> {
	/** 
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BranchPackage modelPackage

	/** 
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = BranchPackage::eINSTANCE
		}
	}

	/** 
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	override protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage === modelPackage
	}

	/** 
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	override protected T doSwitch(int classifierID, EObject theEObject) {

		switch (classifierID) {
			case BranchPackage::BRANCH_DIFF_CREATOR: {
				var BranchDiffCreator branchDiffCreator = (theEObject as BranchDiffCreator)
				var T result = caseBranchDiffCreator(branchDiffCreator)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case BranchPackage::BRANCH_DIFF: {
				var BranchDiff branchDiff = (theEObject as BranchDiff)
				var T result = caseBranchDiff(branchDiff)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case BranchPackage::USER_BRANCH: {
				var UserBranch userBranch = (theEObject as UserBranch)
				var T result = caseUserBranch(userBranch)
				if (result === null) result = caseBranch(userBranch)
				if (result === null) result = caseNamed(userBranch)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case BranchPackage::MASTER_BRANCH: {
				var MasterBranch masterBranch = (theEObject as MasterBranch)
				var T result = caseMasterBranch(masterBranch)
				if (result === null) result = caseBranch(masterBranch)
				if (result === null) result = caseNamed(masterBranch)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case BranchPackage::BRANCH: {
				var Branch branch = (theEObject as Branch)
				var T result = caseBranch(branch)
				if (result === null) result = caseNamed(branch)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			default: {
				return defaultCase(theEObject)
			}
		}
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Diff Creator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diff Creator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseBranchDiffCreator(BranchDiffCreator object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Diff</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diff</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseBranchDiff(BranchDiff object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>User Branch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User Branch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseUserBranch(UserBranch object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Master Branch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Master Branch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseMasterBranch(MasterBranch object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Branch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Branch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseBranch(Branch object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Named</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseNamed(Named object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	override T defaultCase(EObject object) {
		return null
	} // BranchSwitch
}
