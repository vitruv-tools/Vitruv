package tools.vitruv.framework.domains.ui.builder

import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument
import java.util.Set
import org.eclipse.core.runtime.Platform
import java.util.HashSet

final class VitruvProjectBuilderRegistry {
	public static val EXTENSION_POINT_ID = "tools.vitruv.framework.domains.ui.builder"
	static val EXTENSION_BUILDER_ATTRIBUTE = "builderId"
	static val EXTENSION_NAME_ATTRIBUTE = "name"
	
	public static val INSTANCE = new VitruvProjectBuilderRegistry
	static val runtimeRegisteredBuilderIds = new HashSet<Pair<String, String>>
	
	new() {
	}

	/**
	 * Registers a project builder with the given name and ID.
	 */
	def void registerProjectBuilder(String name, String projectBuilderId) {
		checkArgument(!name.nullOrEmpty, "Project builder name must not be null or empty")
		checkArgument(!projectBuilderId.nullOrEmpty, "Project builder ID must not be null or empty")
		runtimeRegisteredBuilderIds.add(name -> projectBuilderId)
	}

	/**
	 * Unregisters the project builder with the given name and ID.
	 */
	def void unregisterProjectBuilder(String name, String projectBuilderId) {
		checkArgument(!name.nullOrEmpty, "Project builder name must not be null or empty")
		checkArgument(!projectBuilderId.nullOrEmpty, "Project builder ID must not be null or empty")
		
		checkState(runtimeRegisteredBuilderIds.contains(projectBuilderId),
			"Trying to unregister project builder that was not registered before")
		runtimeRegisteredBuilderIds.remove(name -> projectBuilderId)
	}

	/**
	 * Retrieves all project builders registered at runtime or via the extension point,
	 * or the default ones if none is registered as pairs of their names and builder IDs.
	 */
	def Set<Pair<String, String>> getAllProjectBuilderIds() {
		(runtimeRegisteredBuilderIds +
			getAllProjectBuilderIdsForNamesFromExtensionPoint).toSet
	}

	/**
	 * Retrieves all project builders which are registered via the extension point returned as
	 * pairs of their names and builder IDs.
	 */
	def static Set<Pair<String, String>> getAllProjectBuilderIdsForNamesFromExtensionPoint() {
		checkState(Platform.running, "Platform must be running to apply Eclipse project builders")
		Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID).map [
			it.getAttribute(EXTENSION_NAME_ATTRIBUTE) -> it.getAttribute(EXTENSION_BUILDER_ATTRIBUTE)
		].filter[it.key !== null && it.value !== null].toSet
	}
	
}
