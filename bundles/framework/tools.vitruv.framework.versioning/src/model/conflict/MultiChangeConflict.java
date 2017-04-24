/**
 */
package model.conflict;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.conflict.MultiChangeConflict#getSourceChanges <em>Source Changes</em>}</li>
 *   <li>{@link model.conflict.MultiChangeConflict#getTargetChanges <em>Target Changes</em>}</li>
 * </ul>
 *
 * @see model.conflict.ConflictPackage#getMultiChangeConflict()
 * @model
 * @generated
 */
public interface MultiChangeConflict extends Conflict {
	/**
	 * Returns the value of the '<em><b>Source Changes</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.EChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Changes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Changes</em>' reference list.
	 * @see model.conflict.ConflictPackage#getMultiChangeConflict_SourceChanges()
	 * @model required="true"
	 * @generated
	 */
	EList<EChange> getSourceChanges();

	/**
	 * Returns the value of the '<em><b>Target Changes</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.EChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Changes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Changes</em>' reference list.
	 * @see model.conflict.ConflictPackage#getMultiChangeConflict_TargetChanges()
	 * @model required="true"
	 * @generated
	 */
	EList<EChange> getTargetChanges();

} // MultiChangeConflict
