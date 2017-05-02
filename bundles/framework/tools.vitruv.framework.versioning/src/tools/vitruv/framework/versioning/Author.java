/**
 */
package tools.vitruv.framework.versioning;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.UserBranch;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.SimpleCommit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.Author#getEmail <em>Email</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#getOwnedBranches <em>Owned Branches</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#getContributedBranches <em>Contributed Branches</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#getCommits <em>Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#getRepository <em>Repository</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#getCurrentBranch <em>Current Branch</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor()
 * @model
 * @generated
 */
public interface Author extends Named {
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
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_Email()
	 * @model required="true"
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.Author#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

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
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_OwnedBranches()
	 * @see tools.vitruv.framework.versioning.branch.UserBranch#getOwner
	 * @model opposite="owner"
	 * @generated
	 */
	EList<UserBranch> getOwnedBranches();

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
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_ContributedBranches()
	 * @see tools.vitruv.framework.versioning.branch.Branch#getContributors
	 * @model opposite="contributors"
	 * @generated
	 */
	EList<Branch> getContributedBranches();

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
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_Commits()
	 * @model
	 * @generated
	 */
	EList<Commit> getCommits();

	/**
	 * Returns the value of the '<em><b>Repository</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.Repository#getAuthors <em>Authors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository</em>' container reference.
	 * @see #setRepository(Repository)
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_Repository()
	 * @see tools.vitruv.framework.versioning.Repository#getAuthors
	 * @model opposite="authors" required="true" transient="false"
	 * @generated
	 */
	Repository getRepository();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.Author#getRepository <em>Repository</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository</em>' container reference.
	 * @see #getRepository()
	 * @generated
	 */
	void setRepository(Repository value);

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
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_CurrentBranch()
	 * @model required="true"
	 * @generated
	 */
	Branch getCurrentBranch();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.Author#getCurrentBranch <em>Current Branch</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Branch</em>' reference.
	 * @see #getCurrentBranch()
	 * @generated
	 */
	void setCurrentBranch(Branch value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model messageRequired="true" parentRequired="true" changesMany="true"
	 * @generated
	 */
	SimpleCommit createSimpleCommit(String message, Commit parent, EList<EChange> changes);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model branchNameRequired="true"
	 * @generated
	 */
	UserBranch createBranch(String branchName);

} // Author
