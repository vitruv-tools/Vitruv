package tools.vitruv.framework.views

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.Map
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

/**
 * Stores and manages all ViewTypes.
 */
class ViewTypeRepository implements ViewTypeProvider {

	val Map<String, ViewType<?>> registeredViewTypes

	new() {
		registeredViewTypes = new HashMap
	}

	override Collection<ViewType<?>> getViewTypes() {
		return new ArrayList(registeredViewTypes.values)
	}

	def void register(ViewType<?> viewType) {
		checkArgument(viewType !== null, "null cannot be added as a view type")
		checkState(!registeredViewTypes.containsKey(viewType.name), "view type name '%s' already taken by another view type", viewType.name)
		registeredViewTypes.put(viewType.name, viewType)
	}

	def ViewType<?> findViewType(String viewTypeName) {
		checkArgument(!viewTypeName.nullOrEmpty, "view type name to search for must not be null or empty")
		return registeredViewTypes.get(viewTypeName)
	}

}
