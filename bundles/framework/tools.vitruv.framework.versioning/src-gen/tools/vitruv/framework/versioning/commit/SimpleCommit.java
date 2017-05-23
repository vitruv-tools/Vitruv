/**
 */
package tools.vitruv.framework.versioning.commit;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Commit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.commit.SimpleCommit#getParent <em>Parent</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getSimpleCommit()
 * @model
 * @generated
 */
public interface SimpleCommit extends Commit {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.versioning.commit.Commit#getCommitsBranchedFromThis <em>Commits Branched From This</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Commit)
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#getSimpleCommit_Parent()
	 * @see tools.vitruv.framework.versioning.commit.Commit#getCommitsBranchedFromThis
	 * @model opposite="commitsBranchedFromThis" required="true"
	 * @generated
	 */
	Commit getParent();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.commit.SimpleCommit#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Commit value);

} // SimpleCommit
