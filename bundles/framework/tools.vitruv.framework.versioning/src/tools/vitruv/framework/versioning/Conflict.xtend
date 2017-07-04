/** 
 */
package tools.vitruv.framework.versioning

import org.eclipse.emf.common.util.EList

interface Conflict {
	def ConflictType getType()

	def void setType(ConflictType value)

	def ConflictSolvability getSolvability()

	def void setSolvability(ConflictSolvability value)

	def void resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches, EList<ChangeMatch> rejectedRemoteOperations)
}
