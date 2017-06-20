/** 
 */
package tools.vitruv.framework.versioning.conflict

import tools.vitruv.framework.change.echange.EChange

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getSourceChange <em>Source Change</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getTargetChange <em>Target Change</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getSimpleChangeConflict()
 * @model
 * @generated
 */
interface SimpleChangeConflict extends Conflict {
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
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getSimpleChangeConflict_SourceChange()
	 * @model required="true"
	 * @generated
	 */
	def EChange getSourceChange()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getSourceChange <em>Source Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Change</em>' reference.
	 * @see #getSourceChange()
	 * @generated
	 */
	def void setSourceChange(EChange value)

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
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getSimpleChangeConflict_TargetChange()
	 * @model required="true"
	 * @generated
	 */
	def EChange getTargetChange()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getTargetChange <em>Target Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Change</em>' reference.
	 * @see #getTargetChange()
	 * @generated
	 */
	def void setTargetChange(EChange value)
// SimpleChangeConflict
}
