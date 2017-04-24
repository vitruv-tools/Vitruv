/**
 */
package model;

import model.commit.Commit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.Tag#getCommit <em>Commit</em>}</li>
 * </ul>
 *
 * @see model.ModelPackage#getTag()
 * @model
 * @generated
 */
public interface Tag extends Named, Signed {
	/**
	 * Returns the value of the '<em><b>Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit</em>' reference.
	 * @see #setCommit(Commit)
	 * @see model.ModelPackage#getTag_Commit()
	 * @model required="true"
	 * @generated
	 */
	Commit getCommit();

	/**
	 * Sets the value of the '{@link model.Tag#getCommit <em>Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit</em>' reference.
	 * @see #getCommit()
	 * @generated
	 */
	void setCommit(Commit value);

} // Tag
