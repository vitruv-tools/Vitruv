package tools.vitruv.framework.applications

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import java.util.Set
import java.util.List
import org.eclipse.core.runtime.Platform

interface VitruvApplication {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.applications.application"
	
	def Set<ChangePropagationSpecification> getChangePropagationSpecifications();
	def Set<VitruvDomain> getVitruvDomains();
	def String getName();
	
	/**
	 * Retrieves all applications from the extensions registered to the VitruvApplication
	 * extension point.
	 */
	def static Iterable<VitruvApplication> getAllApplicationsFromExtensionPoint() {
		val List<VitruvApplication> applications = newArrayList();
		Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID).map [
			it.createExecutableExtension("class")
		].filter(VitruvApplication).forEach[applications.add(it)];
		return applications;
	}
}
