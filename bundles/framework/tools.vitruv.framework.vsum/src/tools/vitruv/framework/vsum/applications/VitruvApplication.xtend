package tools.vitruv.framework.vsum.applications

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.vsum.domains.VitruvDomain

interface VitruvApplication<S extends VitruvDomain<?>,T extends VitruvDomain<?>> {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.vsum.application"
	
	def ChangePropagationSpecification getChangePropagationSpecification();
	def S getSourceDomain();
	def T getTargetDomain();
}