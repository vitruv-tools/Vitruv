/**
 */
package model.conflict;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.conflict.Conflict#getType <em>Type</em>}</li>
 *   <li>{@link model.conflict.Conflict#getSolvability <em>Solvability</em>}</li>
 * </ul>
 *
 * @see model.conflict.ConflictPackage#getConflict()
 * @model abstract="true"
 * @generated
 */
public interface Conflict extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link model.conflict.ConflictType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see model.conflict.ConflictType
	 * @see #setType(ConflictType)
	 * @see model.conflict.ConflictPackage#getConflict_Type()
	 * @model
	 * @generated
	 */
	ConflictType getType();

	/**
	 * Sets the value of the '{@link model.conflict.Conflict#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see model.conflict.ConflictType
	 * @see #getType()
	 * @generated
	 */
	void setType(ConflictType value);

	/**
	 * Returns the value of the '<em><b>Solvability</b></em>' attribute.
	 * The literals are from the enumeration {@link model.conflict.ConflictSolvability}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solvability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solvability</em>' attribute.
	 * @see model.conflict.ConflictSolvability
	 * @see #setSolvability(ConflictSolvability)
	 * @see model.conflict.ConflictPackage#getConflict_Solvability()
	 * @model
	 * @generated
	 */
	ConflictSolvability getSolvability();

	/**
	 * Sets the value of the '{@link model.conflict.Conflict#getSolvability <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solvability</em>' attribute.
	 * @see model.conflict.ConflictSolvability
	 * @see #getSolvability()
	 * @generated
	 */
	void setSolvability(ConflictSolvability value);

} // Conflict
