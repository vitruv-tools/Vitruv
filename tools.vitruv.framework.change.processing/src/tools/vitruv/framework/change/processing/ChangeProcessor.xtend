package tools.vitruv.framework.change.processing

import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.correspondence.CorrespondenceModel

interface ChangeProcessor {
	def ChangeProcessorResult transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel);
}