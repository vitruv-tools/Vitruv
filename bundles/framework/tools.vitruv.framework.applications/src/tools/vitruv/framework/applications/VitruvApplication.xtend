package tools.vitruv.framework.applications

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import java.util.Set

interface VitruvApplication {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.applications.application"
	
	def Set<ChangePropagationSpecification> getChangePropagationSpecifications();
	def Set<VitruvDomain> getVitruvDomains();
}
