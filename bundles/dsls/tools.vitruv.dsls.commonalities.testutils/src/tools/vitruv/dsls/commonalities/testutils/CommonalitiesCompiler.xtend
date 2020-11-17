package tools.vitruv.dsls.commonalities.testutils

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.List

interface CommonalitiesCompiler {
	def List<ChangePropagationSpecification> getChangePropagationSpecifications()
}
