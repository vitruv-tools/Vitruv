package edu.kit.ipd.sdq.vitruvius.framework.change.processing

import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

interface ChangeProcessor {
	def ChangeProcessorResult transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel);
}