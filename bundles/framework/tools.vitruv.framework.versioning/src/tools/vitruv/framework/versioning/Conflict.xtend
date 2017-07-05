/** 
 */
package tools.vitruv.framework.versioning

import org.eclipse.emf.common.util.EList

interface Conflict {
	def ConflictType getType()

	def ConflictSolvability getSolvability()

	def void resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches, EList<ChangeMatch> rejectedRemoteOperations)
}
