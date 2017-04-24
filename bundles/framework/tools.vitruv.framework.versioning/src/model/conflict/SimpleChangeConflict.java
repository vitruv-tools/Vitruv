/**
 */
package model.conflict;

import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.conflict.SimpleChangeConflict#getSourceChange <em>Source Change</em>}</li>
 *   <li>{@link model.conflict.SimpleChangeConflict#getTargetChange <em>Target Change</em>}</li>
 * </ul>
 *
 * @see model.conflict.ConflictPackage#getSimpleChangeConflict()
 * @model
 * @generated
 */
public interface SimpleChangeConflict extends Conflict {
	/**
	 * Returns the value of the '<em><b>Source Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Change</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Change</em>' reference.
	 * @see #setSourceChange(EChange)
	 * @see model.conflict.ConflictPackage#getSimpleChangeConflict_SourceChange()
	 * @model required="true"
	 * @generated
	 */
	EChange getSourceChange();

	/**
	 * Sets the value of the '{@link model.conflict.SimpleChangeConflict#getSourceChange <em>Source Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Change</em>' reference.
	 * @see #getSourceChange()
	 * @generated
	 */
	void setSourceChange(EChange value);

	/**
	 * Returns the value of the '<em><b>Target Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Change</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Change</em>' reference.
	 * @see #setTargetChange(EChange)
	 * @see model.conflict.ConflictPackage#getSimpleChangeConflict_TargetChange()
	 * @model required="true"
	 * @generated
	 */
	EChange getTargetChange();

	/**
	 * Sets the value of the '{@link model.conflict.SimpleChangeConflict#getTargetChange <em>Target Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Change</em>' reference.
	 * @see #getTargetChange()
	 * @generated
	 */
	void setTargetChange(EChange value);

} // SimpleChangeConflict
