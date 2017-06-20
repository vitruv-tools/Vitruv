/** 
 */
package tools.vitruv.framework.versioning.commit

import java.util.Date
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.author.Author

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.CommitMessage#getDate <em>Date</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.CommitMessage#getMessage <em>Message</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.CommitMessage#getAuthor <em>Author</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommitMessage()
 * @model
 * @generated
 */
interface CommitMessage extends EObject {
	/** 
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommitMessage_Date()
	 * @model required="true"
	 * @generated
	 */
	def Date getDate()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.commit.CommitMessage#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	def void setDate(Date value)

	/** 
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommitMessage_Message()
	 * @model required="true"
	 * @generated
	 */
	def String getMessage()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.commit.CommitMessage#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	def void setMessage(String value)

	/** 
	 * Returns the value of the '<em><b>Author</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' reference.
	 * @see #setAuthor(Author)
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommitMessage_Author()
	 * @model required="true"
	 * @generated
	 */
	def Author getAuthor()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.commit.CommitMessage#getAuthor <em>Author</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' reference.
	 * @see #getAuthor()
	 * @generated
	 */
	def void setAuthor(Author value)
// CommitMessage
}
