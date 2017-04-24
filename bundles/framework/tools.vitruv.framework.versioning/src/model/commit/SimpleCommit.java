/**
 */
package model.commit;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Commit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.commit.SimpleCommit#getParent <em>Parent</em>}</li>
 * </ul>
 *
 * @see model.commit.CommitPackage#getSimpleCommit()
 * @model
 * @generated
 */
public interface SimpleCommit extends Commit {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Commit)
	 * @see model.commit.CommitPackage#getSimpleCommit_Parent()
	 * @model required="true"
	 * @generated
	 */
	Commit getParent();

	/**
	 * Sets the value of the '{@link model.commit.SimpleCommit#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Commit value);

} // SimpleCommit
