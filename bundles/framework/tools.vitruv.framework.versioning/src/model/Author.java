/**
 */
package model;

import model.branch.Branch;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.Author#getEmail <em>Email</em>}</li>
 *   <li>{@link model.Author#getOwnedBranches <em>Owned Branches</em>}</li>
 *   <li>{@link model.Author#getContributedBranches <em>Contributed Branches</em>}</li>
 * </ul>
 *
 * @see model.ModelPackage#getAuthor()
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
	 * @see model.ModelPackage#getAuthor_Email()
	 * @model required="true"
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link model.Author#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

	/**
	 * Returns the value of the '<em><b>Owned Branches</b></em>' reference list.
	 * The list contents are of type {@link model.branch.Branch}.
	 * It is bidirectional and its opposite is '{@link model.branch.Branch#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Branches</em>' reference list.
	 * @see model.ModelPackage#getAuthor_OwnedBranches()
	 * @see model.branch.Branch#getOwner
	 * @model opposite="owner"
	 * @generated
	 */
	EList<Branch> getOwnedBranches();

	/**
	 * Returns the value of the '<em><b>Contributed Branches</b></em>' reference list.
	 * The list contents are of type {@link model.branch.Branch}.
	 * It is bidirectional and its opposite is '{@link model.branch.Branch#getContributors <em>Contributors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contributed Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contributed Branches</em>' reference list.
	 * @see model.ModelPackage#getAuthor_ContributedBranches()
	 * @see model.branch.Branch#getContributors
	 * @model opposite="contributors"
	 * @generated
	 */
	EList<Branch> getContributedBranches();

} // Author
