/**
 */
package tools.vitruv.framework.versioning.branch;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Branch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.UserBranch#getOwner <em>Owner</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.UserBranch#getBranchedFrom <em>Branched From</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getUserBranch()
 * @model
 * @generated
 */
public interface UserBranch extends Branch {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.author.Author#getOwnedBranches <em>Owned Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' reference.
	 * @see #setOwner(tools.vitruv.framework.versioning.author.Author)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getUserBranch_Owner()
	 * @see tools.vitruv.framework.versioning.author.Author#getOwnedBranches
	 * @model opposite="ownedBranches" required="true"
	 * @generated
	 */
	tools.vitruv.framework.versioning.author.Author getOwner();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.UserBranch#getOwner <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(tools.vitruv.framework.versioning.author.Author value);

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
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getUserBranch_BranchedFrom()
	 * @see tools.vitruv.framework.versioning.branch.Branch#getChildBranches
	 * @model opposite="childBranches" required="true"
	 * @generated
	 */
	Branch getBranchedFrom();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.UserBranch#getBranchedFrom <em>Branched From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branched From</em>' reference.
	 * @see #getBranchedFrom()
	 * @generated
	 */
	void setBranchedFrom(Branch value);

} // UserBranch
