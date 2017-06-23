/** 
 */
package tools.vitruv.framework.versioning.commit

import org.eclipse.emf.common.util.EList

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Merge Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.MergeCommit#getCommitsMergedToThis <em>Commits Merged To This</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getMergeCommit()
 * @model
 * @generated
 */
interface MergeCommit extends Commit {
	/** 
	 * Returns the value of the '<em><b>Commits Merged To This</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.commit.Commit}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.commit.Commit#getCommitsMergedFromThis <em>Commits Merged From This</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commits Merged To This</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commits Merged To This</em>' reference list.
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getMergeCommit_CommitsMergedToThis()
	 * @see tools.vitruv.framework.versioning.commit.Commit#getCommitsMergedFromThis
	 * @model opposite="commitsMergedFromThis" lower="2" upper="2"
	 * @generated
	 */
	def EList<Commit> getCommitsMergedToThis()
// MergeCommit
}
