package tools.vitruv.framework.applications

import tools.vitruv.change.propagation.ChangePropagationSpecification
import java.util.Set

interface VitruvApplication {
	def Set<ChangePropagationSpecification> getChangePropagationSpecifications();
	def String getName();
}
