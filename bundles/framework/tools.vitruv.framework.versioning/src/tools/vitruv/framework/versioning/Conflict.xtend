/** 
 */
package tools.vitruv.framework.versioning

import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.util.datatypes.VURI

interface Conflict {
	def ConflictType getType()

	def ConflictSeverity getSolvability()

	def void resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches, EList<ChangeMatch> rejectedRemoteOperations)

	def List<EChange> getSourceDefaultSolution()

	def List<EChange> getTriggeredDefaultSolution()

	def VURI getMyOriginalVURI()

	def VURI getTheirOriginalVURI()

	def List<VURI> getMyTriggeredVURIs()

	def List<VURI> getTheirTriggeredVURIs()
}
