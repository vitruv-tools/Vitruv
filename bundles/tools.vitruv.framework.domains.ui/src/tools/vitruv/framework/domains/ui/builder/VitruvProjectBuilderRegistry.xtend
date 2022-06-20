package tools.vitruv.framework.domains.ui.builder

import tools.vitruv.framework.domains.VitruvDomain
import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument
import java.util.HashMap
import java.util.Set
import org.eclipse.core.runtime.Platform
import tools.vitruv.framework.domains.AbstractVitruvDomain

final class VitruvProjectBuilderRegistry {
	public static val EXTENSION_POINT_ID = "tools.vitruv.framework.domains.ui.builder"
	static val EXTENSION_DOMAIN_ATTRIBUTE = "domain"
	static val EXTENSION_BUILDER_ATTRIBUTE = "builderId"

	public static val INSTANCE = new VitruvProjectBuilderRegistry
	static val runtimeRegisteredBuilderIds = new HashMap<VitruvDomain, Set<String>>
	
	new() {
	}

	/**
	 * Registers project builder with the given ID for the given {@link VitruvDomain}.
	 */
	def void registerProjectBuilder(VitruvDomain domain, String projectBuilderId) {
		checkNotNull(domain, "Domain must not be null")
		checkArgument(!projectBuilderId.nullOrEmpty, "Project builder ID must not be null or empty")

		runtimeRegisteredBuilderIds.putIfAbsent(domain, newHashSet)
		runtimeRegisteredBuilderIds.get(domain).add(projectBuilderId)
	}

	/**
	 * Unregisters the project builder with the given ID from the given {@link VitruvDomain}.
	 */
	def void unregisterProjectBuilder(VitruvDomain domain, String projectBuilderId) {
		checkNotNull(domain, "Domain must not be null")
		checkArgument(!projectBuilderId.nullOrEmpty, "Project builder ID must not be null or empty")
		
		checkState(runtimeRegisteredBuilderIds.getOrDefault(domain, newHashSet).contains(projectBuilderId),
			"Trying to unregister project builder that was not registered before")
		runtimeRegisteredBuilderIds.get(domain).remove(projectBuilderId)
	}

	/**
	 * Retrieves all project builders registered at runtime or via the extension point for the given {@link VitruvDomain},
	 * or the default ones if none is registered.
	 */
	def Set<String> getProjectBuilderIds(VitruvDomain domain) {
		checkNotNull(domain, "Domain must not be null")
		
		(runtimeRegisteredBuilderIds.getOrDefault(domain, newHashSet) +
			domain.allProjectBuilderIdsForDomainFromExtensionPoint).toSet
	}

	/**
	 * Retrieves all project builder IDs, which are registered for the given {@link VitruvDomain} via the extension point,
	 * or the default ones if none are registered.
	 */
	def static Set<String> getAllProjectBuilderIdsForDomainFromExtensionPoint(VitruvDomain domain) {
		checkNotNull(domain, "Domain must not be null")
		
		val registeredBuilders = domain.class.allProjectBuilderIdsForDomainFromExtensionPoint
		return if (!registeredBuilders.empty) {
			registeredBuilders
		} else {
			defaultProjectBuilderIdsFromExtensionPoint
		}
	}

	/**
	 * Retrieves all default project builder IDs, which are the ones registered for {@link AbstractVitruvDomain} via the extension point.
	 */
	def static Set<String> getDefaultProjectBuilderIdsFromExtensionPoint() {
		AbstractVitruvDomain.allProjectBuilderIdsForDomainFromExtensionPoint
	}
	
	private def static Set<String> getAllProjectBuilderIdsForDomainFromExtensionPoint(Class<? extends VitruvDomain> domainClass) {
		checkState(Platform.running, "Platform must be running to apply Eclipse project builders")
		Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID).filter [
			it.getAttribute(EXTENSION_DOMAIN_ATTRIBUTE) !== null && it.getAttribute("domain").equals(domainClass.name)
		].map [
			it.getAttribute(EXTENSION_BUILDER_ATTRIBUTE)
		].filterNull.toSet
	}
	
}
