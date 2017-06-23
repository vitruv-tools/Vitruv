/** 
 */
package tools.vitruv.framework.versioning.branch

import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.Commit

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.branch.Branch#getCurrentHeadCommit <em>Current Head Commit</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.Branch#getContributors <em>Contributors</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.Branch#getChildBranches <em>Child Branches</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch()
 * @model
 * @generated
 */
interface Branch extends Named {
	/** 
	 * Returns the value of the '<em><b>Current Head Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Head Commit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Head Commit</em>' reference.
	 * @see #setCurrentHeadCommit(Commit)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_CurrentHeadCommit()
	 * @model required="true"
	 * @generated
	 */
	def Commit getCurrentHeadCommit()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.Branch#getCurrentHeadCommit <em>Current Head Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Head Commit</em>' reference.
	 * @see #getCurrentHeadCommit()
	 * @generated
	 */
	def void setCurrentHeadCommit(Commit value)

	/** 
	 * Returns the value of the '<em><b>Contributors</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.author.Author}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.author.Author#getContributedBranches <em>Contributed Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contributors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contributors</em>' reference list.
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_Contributors()
	 * @see tools.vitruv.framework.versioning.author.Author#getContributedBranches
	 * @model opposite="contributedBranches"
	 * @generated
	 */
	def EList<Author> getContributors()

	/** 
	 * Returns the value of the '<em><b>Child Branches</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.branch.UserBranch}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.branch.UserBranch#getBranchedFrom <em>Branched From</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Branches</em>' reference list.
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_ChildBranches()
	 * @see tools.vitruv.framework.versioning.branch.UserBranch#getBranchedFrom
	 * @model opposite="branchedFrom"
	 * @generated
	 */
	def EList<UserBranch> getChildBranches()
// Branch
}
