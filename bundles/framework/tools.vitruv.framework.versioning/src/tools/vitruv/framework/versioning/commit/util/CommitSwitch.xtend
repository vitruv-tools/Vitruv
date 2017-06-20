/** 
 */
package tools.vitruv.framework.versioning.commit.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.util.Switch
import tools.vitruv.framework.versioning.author.Signed
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit

/** 
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see CommitPackage
 * @generated
 */
class CommitSwitch<T> extends Switch<T> {
	/** 
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CommitPackage modelPackage

	/** 
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = CommitPackage::eINSTANCE
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
			case CommitPackage::MERGE_COMMIT: {
				var MergeCommit mergeCommit = (theEObject as MergeCommit)
				var T result = caseMergeCommit(mergeCommit)
				if (result === null) result = caseCommit(mergeCommit)
				if (result === null) result = caseSigned(mergeCommit)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case CommitPackage::SIMPLE_COMMIT: {
				var SimpleCommit simpleCommit = (theEObject as SimpleCommit)
				var T result = caseSimpleCommit(simpleCommit)
				if (result === null) result = caseCommit(simpleCommit)
				if (result === null) result = caseSigned(simpleCommit)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case CommitPackage::COMMIT: {
				var Commit commit = (theEObject as Commit)
				var T result = caseCommit(commit)
				if (result === null) result = caseSigned(commit)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case CommitPackage::COMMIT_MESSAGE: {
				var CommitMessage commitMessage = (theEObject as CommitMessage)
				var T result = caseCommitMessage(commitMessage)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case CommitPackage::INITIAL_COMMIT: {
				var InitialCommit initialCommit = (theEObject as InitialCommit)
				var T result = caseInitialCommit(initialCommit)
				if (result === null) result = caseCommit(initialCommit)
				if (result === null) result = caseSigned(initialCommit)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			default: {
				return defaultCase(theEObject)
			}
		}
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Merge Commit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge Commit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseMergeCommit(MergeCommit object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Simple Commit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Commit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseSimpleCommit(SimpleCommit object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Commit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Commit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseCommit(Commit object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseCommitMessage(CommitMessage object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Initial Commit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Commit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseInitialCommit(InitialCommit object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Signed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseSigned(Signed object) {
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
	} // CommitSwitch
}
