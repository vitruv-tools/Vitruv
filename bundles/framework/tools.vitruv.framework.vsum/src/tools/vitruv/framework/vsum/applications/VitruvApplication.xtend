package tools.vitruv.framework.vsum.applications

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.vsum.domains.VitruvDomain

interface VitruvApplication<S extends VitruvDomain<?>,T extends VitruvDomain<?>> {
	def ChangePropagationSpecification getChangePropagationSpecification();
	def S getSourceDomain();
	def T getTargetDomain();
}