/**
 */
package tools.vitruv.framework.versioning;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.InitialCommit;
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
	 * The list contents are of type {@link tools.vitruv.framework.versioning.branch.Branch}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.branch.Branch#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Branches</em>' reference list.
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getAuthor_OwnedBranches()
	 * @see tools.vitruv.framework.versioning.branch.Branch#getOwner
	 * @model opposite="owner"
	 * @generated
	 */
	EList<Branch> getOwnedBranches();

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	InitialCommit createInitialCommit();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model messageRequired="true" parentRequired="true" changesMany="true"
	 * @generated
	 */
	SimpleCommit createSimpleCommit(String message, Commit parent, EList<EChange> changes);

} // Author
