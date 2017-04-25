/**
 */
package tools.vitruv.framework.versioning.branch;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.Named;

import tools.vitruv.framework.versioning.commit.Commit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.Branch#getCurrentHeadCommit <em>Current Head Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.Branch#getOwner <em>Owner</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.Branch#getContributors <em>Contributors</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.Branch#getBranchedFrom <em>Branched From</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.Branch#getChildBranches <em>Child Branches</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch()
 * @model
 * @generated
 */
public interface Branch extends Named {
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
	Commit getCurrentHeadCommit();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.Branch#getCurrentHeadCommit <em>Current Head Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Head Commit</em>' reference.
	 * @see #getCurrentHeadCommit()
	 * @generated
	 */
	void setCurrentHeadCommit(Commit value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.Author#getOwnedBranches <em>Owned Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' reference.
	 * @see #setOwner(Author)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_Owner()
	 * @see tools.vitruv.framework.versioning.Author#getOwnedBranches
	 * @model opposite="ownedBranches" required="true"
	 * @generated
	 */
	Author getOwner();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.Branch#getOwner <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Author value);

	/**
	 * Returns the value of the '<em><b>Contributors</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.Author}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.Author#getContributedBranches <em>Contributed Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contributors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contributors</em>' reference list.
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_Contributors()
	 * @see tools.vitruv.framework.versioning.Author#getContributedBranches
	 * @model opposite="contributedBranches" required="true"
	 * @generated
	 */
	EList<Author> getContributors();

	/**
	 * Returns the value of the '<em><b>Branched From</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.branch.Branch#getChildBranches <em>Child Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branched From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branched From</em>' reference.
	 * @see #setBranchedFrom(Branch)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_BranchedFrom()
	 * @see tools.vitruv.framework.versioning.branch.Branch#getChildBranches
	 * @model opposite="childBranches" required="true"
	 * @generated
	 */
	Branch getBranchedFrom();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.Branch#getBranchedFrom <em>Branched From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branched From</em>' reference.
	 * @see #getBranchedFrom()
	 * @generated
	 */
	void setBranchedFrom(Branch value);

	/**
	 * Returns the value of the '<em><b>Child Branches</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.branch.Branch}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.branch.Branch#getBranchedFrom <em>Branched From</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Branches</em>' reference list.
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranch_ChildBranches()
	 * @see tools.vitruv.framework.versioning.branch.Branch#getBranchedFrom
	 * @model opposite="branchedFrom"
	 * @generated
	 */
	EList<Branch> getChildBranches();

} // Branch
