package tools.vitruv.framework.correspondence

import tools.vitruv.framework.correspondence.CorrespondenceModel

interface InternalCorrespondenceModel extends CorrespondenceModel {
	def boolean changedAfterLastSave()

	def void resetChangedAfterLastSave()

}
