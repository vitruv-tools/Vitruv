package tools.vitruv.framework.versioning

import java.util.List

import tools.vitruv.framework.tests.ChangeObserver
import tools.vitruv.framework.util.datatypes.VURI

interface VersioningFacade extends ChangeObserver {
	def void recordOriginalAndCorrespondentChanges(VURI orignal, List<VURI> targets)

	def List<ChangeMatch> getChangesMatches()
}
