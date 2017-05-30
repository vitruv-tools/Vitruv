package tools.vitruv.framework.versioning

import java.util.List

import tools.vitruv.framework.tests.ChangeObserver
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Interface to track changes on different {@link VURI}s. This  is registerred as {@link ChangeObserver}
 * and tracks changes of an original and the correspondent changes at several targets. 
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-05-30
 */
interface VersioningFacade extends ChangeObserver {
	/**
	 * Starts a recorder on every target and stores the {@link VURI}s as to be tracked. 
	 * To get the changes on the original, an object of this class has to be added as 
	 * {@link ChangeObserver} to an object implementing the {@link ChangeObservable} interface.
	 * @param orignal source of the changes
	 * @param targets the targets to be observed
	 */
	def void recordOriginalAndCorrespondentChanges(VURI orignal, List<VURI> targets)

	/**
	 * Returns the change matches.
	 * @return the change matches which are recorded until this moment
	 */
	def List<ChangeMatch> getChangesMatches()
}
