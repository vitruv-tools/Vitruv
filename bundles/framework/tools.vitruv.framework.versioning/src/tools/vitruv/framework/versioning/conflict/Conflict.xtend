/** 
 */
package tools.vitruv.framework.versioning.conflict

import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.ChangeMatch

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.conflict.Conflict#getType <em>Type</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.Conflict#getSolvability <em>Solvability</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getConflict()
 * @model abstract="true"
 * @generated
 */
interface Conflict extends EObject {
	def int getOriginalChangesLevenshteinDistance()

	def void setOriginalChangesLevenshteinDistance(int distance)

	def Graph getEChangeDependencyGraph()

	def void setEChangeDependencyGraph(Graph dependencyGraph)

	/** 
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link tools.vitruv.framework.versioning.conflict.ConflictType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictType
	 * @see #setType(ConflictType)
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getConflict_Type()
	 * @model
	 * @generated
	 */
	def ConflictType getType()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.conflict.Conflict#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictType
	 * @see #getType()
	 * @generated
	 */
	def void setType(ConflictType value)

	/** 
	 * Returns the value of the '<em><b>Solvability</b></em>' attribute.
	 * The literals are from the enumeration {@link tools.vitruv.framework.versioning.conflict.ConflictSolvability}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solvability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solvability</em>' attribute.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictSolvability
	 * @see #setSolvability(ConflictSolvability)
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getConflict_Solvability()
	 * @model
	 * @generated
	 */
	def ConflictSolvability getSolvability()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.conflict.Conflict#getSolvability <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solvability</em>' attribute.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictSolvability
	 * @see #getSolvability()
	 * @generated
	 */
	def void setSolvability(ConflictSolvability value)

	def void resolveConflict(Set<ChangeMatch> acceptedLocalChangeMatches, Set<ChangeMatch> rejectedRemoteOperations)
// Conflict
}
