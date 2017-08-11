package tools.vitruv.framework.correspondence

import tools.vitruv.framework.correspondence.CorrespondenceModel

interface InternalCorrespondenceModel extends CorrespondenceModel {
	def boolean changedAfterLastSave()

	def void resetChangedAfterLastSave()

	/**
	 * Method to prevent the 'TUID management with several virtual models' error
	 * @see https://github.com/vitruv-tools/Vitruv/issues/114
	 * @author Patrick Stoeckle
	 */
	def void registerToTUIDManager()

	/**
	 * Method to prevent the 'TUID management with several virtual models' error
	 * @see https://github.com/vitruv-tools/Vitruv/issues/114
	 * @author Patrick Stoeckle
	 */
	def void deregisterFromTUIDManager()
}
