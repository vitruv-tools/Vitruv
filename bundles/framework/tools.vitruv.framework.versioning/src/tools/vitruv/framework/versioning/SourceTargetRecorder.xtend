package tools.vitruv.framework.versioning

import java.util.Collection
import java.util.List
import tools.vitruv.framework.tests.ChangeObserver
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.commit.ChangeMatch

/**
 * Interface to track changes on different {@link VURI}s. Instationeions of this interface are registerred as {@link ChangeObserver}
 * and track changes of an original and the correspondent changes at several targets. 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-05-30
 */
interface SourceTargetRecorder extends ChangeObserver {
	/**
	 * Starts a recorder on every target and stores the {@link VURI}s as to be tracked. 
	 * To get the changes on the original, an object of this class has to be added as 
	 * {@link ChangeObserver} to an object implementing the {@link ChangeObservable} interface.
	 * @param orignal source of the changes
	 * @param targets the targets to be observed
	 */
	def void recordOriginalAndCorrespondentChanges(VURI orignal, Collection<VURI> targets)

	/**
	 * Returns the change matches.
	 * @return the change matches which are recorded until this moment
	 */
	def List<ChangeMatch> getChangeMatches(VURI source)
}
