package tools.vitruv.framework.change.processing

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.description.TransactionalChange

interface ChangeProcessor {
	def ChangeProcessorResult transformChange(TransactionalChange change, CorrespondenceModel correspondenceModel);
}