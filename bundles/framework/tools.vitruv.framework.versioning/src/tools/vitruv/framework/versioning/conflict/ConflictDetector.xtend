/** 
 */
package tools.vitruv.framework.versioning.conflict

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.branch.BranchDiff

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Detector</b></em>'.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getConflictDetector()
 * @model
 * @generated
 */
interface ConflictDetector extends EObject {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	def EList<Conflict> detectConflicts(BranchDiff diff)
// ConflictDetector
}
