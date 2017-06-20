/** 
 */
package tools.vitruv.framework.versioning.author

import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.repository.Repository

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.author.Author#getEmail <em>Email</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.Author#getOwnedBranches <em>Owned Branches</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.Author#getContributedBranches <em>Contributed Branches</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.Author#getCommits <em>Commits</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.Author#getCurrentRepository <em>Current Repository</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.author.Author#getCurrentBranch <em>Current Branch</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor()
 * @model
 * @generated
 */
interface Author extends Named {
	/** 
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(String)
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor_Email()
	 * @model required="true"
	 * @generated
	 */
	def String getEmail()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.author.Author#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	def void setEmail(String value)

	/** 
	 * Returns the value of the '<em><b>Owned Branches</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.branch.UserBranch}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.branch.UserBranch#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Branches</em>' reference list.
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor_OwnedBranches()
	 * @see tools.vitruv.framework.versioning.branch.UserBranch#getOwner
	 * @model opposite="owner"
	 * @generated
	 */
	def EList<UserBranch> getOwnedBranches()

	/** 
	 * Returns the value of the '<em><b>Contributed Branches</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.branch.Branch}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.branch.Branch#getContributors <em>Contributors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contributed Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contributed Branches</em>' reference list.
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor_ContributedBranches()
	 * @see tools.vitruv.framework.versioning.branch.Branch#getContributors
	 * @model opposite="contributors"
	 * @generated
	 */
	def EList<Branch> getContributedBranches()

	/** 
	 * Returns the value of the '<em><b>Commits</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.commit.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commits</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commits</em>' reference list.
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor_Commits()
	 * @model
	 * @generated
	 */
	def EList<Commit> getCommits()

	/** 
	 * Returns the value of the '<em><b>Current Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Repository</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Repository</em>' reference.
	 * @see #setCurrentRepository(Repository)
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor_CurrentRepository()
	 * @model
	 * @generated
	 */
	def Repository getCurrentRepository()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.author.Author#getCurrentRepository <em>Current Repository</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Repository</em>' reference.
	 * @see #getCurrentRepository()
	 * @generated
	 */
	def void setCurrentRepository(Repository value)

	/** 
	 * Returns the value of the '<em><b>Current Branch</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Branch</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Branch</em>' reference.
	 * @see #setCurrentBranch(Branch)
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getAuthor_CurrentBranch()
	 * @model
	 * @generated
	 */
	def Branch getCurrentBranch()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.author.Author#getCurrentBranch <em>Current Branch</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Branch</em>' reference.
	 * @see #getCurrentBranch()
	 * @generated
	 */
	def void setCurrentBranch(Branch value)

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model messageRequired="true" parentRequired="true" changesMany="true"
	 * @generated
	 */
	def SimpleCommit createSimpleCommit(String message, Commit parent, EList<EChange> changes)

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model branchNameRequired="true"
	 * @generated
	 */
	def UserBranch createBranch(String branchName)

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model targetBranchRequired="true"
	 * @generated
	 */
	def void switchToBranch(Branch targetBranch)

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model repositoryRequired="true"
	 * @generated
	 */
	def void switchToRepository(Repository repository)
// Author
}
