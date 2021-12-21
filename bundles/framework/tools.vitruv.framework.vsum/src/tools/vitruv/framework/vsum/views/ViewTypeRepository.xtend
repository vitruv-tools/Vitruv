package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.Map

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
        if(registeredViewTypes.containsKey(viewType.name)) {
            throw new IllegalArgumentException("ViewType name already taken by another ViewType: " + viewType.name)
        }
        registeredViewTypes.put(viewType.name, viewType)
    }

    def ViewType<?> findViewType(String viewTypeName) {
        return registeredViewTypes.get(viewTypeName)
    }

}
