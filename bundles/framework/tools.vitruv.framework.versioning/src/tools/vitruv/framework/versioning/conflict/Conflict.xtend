/** 
 */
package tools.vitruv.framework.versioning.conflict

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.commit.ChangeMatch
import org.graphstream.graph.Graph

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
 * <li>{@link tools.vitruv.framework.versioning.conflict.Conflict#getOriginalChangesLevenshteinDistance <em>Original Changes Levenshtein Distance</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getConflict()
 * @model abstract="true"
 * @generated
 */
interface Conflict extends EObject {
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

	/** 
	 * Returns the value of the '<em><b>Original Changes Levenshtein Distance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Changes Levenshtein Distance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Changes Levenshtein Distance</em>' attribute.
	 * @see #setOriginalChangesLevenshteinDistance(int)
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#getConflict_OriginalChangesLevenshteinDistance()
	 * @model
	 * @generated
	 */
	def int getOriginalChangesLevenshteinDistance()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.conflict.Conflict#getOriginalChangesLevenshteinDistance <em>Original Changes Levenshtein Distance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Changes Levenshtein Distance</em>' attribute.
	 * @see #getOriginalChangesLevenshteinDistance()
	 * @generated
	 */
	def void setOriginalChangesLevenshteinDistance(int value)

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model acceptedLocalChangeMatchesMany="true" rejectedRemoteOperationsMany="true"
	 * @generated
	 */
	def void resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches, EList<ChangeMatch> rejectedRemoteOperations)
// Conflict
}
