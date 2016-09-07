package tools.vitruvius.framework.change.processing

import tools.vitruvius.framework.change.description.ConcreteChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel

interface ChangeProcessor {
	def ChangeProcessorResult transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel);
}