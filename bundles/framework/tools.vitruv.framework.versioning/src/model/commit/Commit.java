/**
 */
package model.commit;

import model.Signed;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.commit.Commit#getChecksum <em>Checksum</em>}</li>
 *   <li>{@link model.commit.Commit#getChanges <em>Changes</em>}</li>
 *   <li>{@link model.commit.Commit#getCommitmessage <em>Commitmessage</em>}</li>
 *   <li>{@link model.commit.Commit#getCommitsBranchedFromThis <em>Commits Branched From This</em>}</li>
 *   <li>{@link model.commit.Commit#getCommitsMergedFromThis <em>Commits Merged From This</em>}</li>
 *   <li>{@link model.commit.Commit#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 *
 * @see model.commit.CommitPackage#getCommit()
 * @model abstract="true"
 * @generated
 */
public interface Commit extends Signed {
	/**
	 * Returns the value of the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checksum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checksum</em>' attribute.
	 * @see #setChecksum(long)
	 * @see model.commit.CommitPackage#getCommit_Checksum()
	 * @model required="true"
	 * @generated
	 */
	long getChecksum();

	/**
	 * Sets the value of the '{@link model.commit.Commit#getChecksum <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checksum</em>' attribute.
	 * @see #getChecksum()
	 * @generated
	 */
	void setChecksum(long value);

	/**
	 * Returns the value of the '<em><b>Changes</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.EChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changes</em>' reference list.
	 * @see model.commit.CommitPackage#getCommit_Changes()
	 * @model required="true"
	 * @generated
	 */
	EList<EChange> getChanges();

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
	 * @see model.commit.CommitPackage#getCommit_Commitmessage()
	 * @model required="true"
	 * @generated
	 */
	CommitMessage getCommitmessage();

	/**
	 * Sets the value of the '{@link model.commit.Commit#getCommitmessage <em>Commitmessage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commitmessage</em>' reference.
	 * @see #getCommitmessage()
	 * @generated
	 */
	void setCommitmessage(CommitMessage value);

	/**
	 * Returns the value of the '<em><b>Commits Branched From This</b></em>' reference list.
	 * The list contents are of type {@link model.commit.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commits Branched From This</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commits Branched From This</em>' reference list.
	 * @see model.commit.CommitPackage#getCommit_CommitsBranchedFromThis()
	 * @model
	 * @generated
	 */
	EList<Commit> getCommitsBranchedFromThis();

	/**
	 * Returns the value of the '<em><b>Commits Merged From This</b></em>' reference list.
	 * The list contents are of type {@link model.commit.MergeCommit}.
	 * It is bidirectional and its opposite is '{@link model.commit.MergeCommit#getCommitsMergedToThis <em>Commits Merged To This</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commits Merged From This</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commits Merged From This</em>' reference list.
	 * @see model.commit.CommitPackage#getCommit_CommitsMergedFromThis()
	 * @see model.commit.MergeCommit#getCommitsMergedToThis
	 * @model opposite="commitsMergedToThis"
	 * @generated
	 */
	EList<MergeCommit> getCommitsMergedFromThis();

	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(int)
	 * @see model.commit.CommitPackage#getCommit_Identifier()
	 * @model required="true"
	 * @generated
	 */
	int getIdentifier();

	/**
	 * Sets the value of the '{@link model.commit.Commit#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(int value);

} // Commit
