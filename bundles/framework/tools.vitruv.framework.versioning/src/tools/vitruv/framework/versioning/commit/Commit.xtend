package tools.vitruv.framework.versioning.commit

import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.author.Signed

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.Commit#getChecksum <em>Checksum</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.Commit#getChanges <em>Changes</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.Commit#getCommitmessage <em>Commitmessage</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.Commit#getCommitsBranchedFromThis <em>Commits Branched From This</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.Commit#getCommitsMergedFromThis <em>Commits Merged From This</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.Commit#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit()
 * @model abstract="true"
 * @generated
 */
interface Commit extends Signed {
	/** 
	 * Returns the value of the '<em><b>Checksum</b></em>' attribute.
	 * The default value is <code>"1000"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checksum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checksum</em>' attribute.
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit_Checksum()
	 * @model default="1000" required="true" changeable="false" derived="true"
	 * @generated
	 */
	def long getChecksum()

	/** 
	 * Returns the value of the '<em><b>Changes</b></em>' containment reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.EChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changes</em>' containment reference list.
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit_Changes()
	 * @model containment="true" required="true"
	 * @generated
	 */
	def EList<EChange> getChanges()

	/** 
	 * Returns the value of the '<em><b>Commitmessage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commitmessage</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commitmessage</em>' reference.
	 * @see #setCommitmessage(CommitMessage)
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit_Commitmessage()
	 * @model required="true"
	 * @generated
	 */
	def CommitMessage getCommitmessage()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.commit.Commit#getCommitmessage <em>Commitmessage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commitmessage</em>' reference.
	 * @see #getCommitmessage()
	 * @generated
	 */
	def void setCommitmessage(CommitMessage value)

	/** 
	 * Returns the value of the '<em><b>Commits Branched From This</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.commit.SimpleCommit}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.commit.SimpleCommit#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commits Branched From This</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commits Branched From This</em>' reference list.
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit_CommitsBranchedFromThis()
	 * @see tools.vitruv.framework.versioning.commit.SimpleCommit#getParent
	 * @model opposite="parent"
	 * @generated
	 */
	def EList<SimpleCommit> getCommitsBranchedFromThis()

	/** 
	 * Returns the value of the '<em><b>Commits Merged From This</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.commit.MergeCommit}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.commit.MergeCommit#getCommitsMergedToThis <em>Commits Merged To This</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commits Merged From This</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commits Merged From This</em>' reference list.
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit_CommitsMergedFromThis()
	 * @see tools.vitruv.framework.versioning.commit.MergeCommit#getCommitsMergedToThis
	 * @model opposite="commitsMergedToThis"
	 * @generated
	 */
	def EList<MergeCommit> getCommitsMergedFromThis()

	/** 
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * The default value is <code>"2000"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getCommit_Identifier()
	 * @model default="2000" id="true" required="true" changeable="false"
	 * @generated
	 */
	def int getIdentifier()
// Commit
}
