package tools.vitruv.framework.applications

import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Collection
import java.util.HashSet
import java.util.Collections
import java.util.List
import org.eclipse.core.runtime.Platform
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

class VitruvApplicationsRegistry {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.applications.application"
	static Logger LOGGER = LogManager.getLogger(VitruvApplicationsRegistry)
	
	@Accessors(PUBLIC_GETTER)
	static VitruvApplicationsRegistry instance = new VitruvApplicationsRegistry
	
	Collection<VitruvApplication> applications;
	boolean initialized;
	
	private new() {
		applications = new HashSet
		initialized = false; 
	}
	
	/**
	 * Adds the given application to the registry
	 * 
	 * @param application - the {@link VitruvApplication} to add, must not be null
	 */
	def addApplication(VitruvApplication application) {
		if (application === null) {
			throw new IllegalArgumentException("Application must not be null")
		}
		applications += application;
	}
	
	def getApplications() {
		if (!initialized) {
			applications += allApplicationsFromExtensionPoint
			initialized = true
		}
		Collections.unmodifiableCollection(applications);
	}
	
	/**
	 * Retrieves all applications from the extensions registered to the VitruvApplication
	 * extension point.
	 */
	private def static Iterable<VitruvApplication> getAllApplicationsFromExtensionPoint() {
		val List<VitruvApplication> applications = newArrayList();
		if (Platform.running) {
			Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID).map [
				try {
					it.createExecutableExtension("class")
				} catch (Exception e) {
					LOGGER.warn("Error when loading application for extension " + it)
					null;
				}
			].filter(VitruvApplication).forEach[applications.add(it)];
		}
		return applications;
	}
}