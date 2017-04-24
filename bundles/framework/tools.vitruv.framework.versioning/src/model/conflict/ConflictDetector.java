/**
 */
package model.conflict;

import model.branch.BranchDiff;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Detector</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see model.conflict.ConflictPackage#getConflictDetector()
 * @model
 * @generated
 */
public interface ConflictDetector extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<Conflict> detectConflicts(BranchDiff diff);

} // ConflictDetector
