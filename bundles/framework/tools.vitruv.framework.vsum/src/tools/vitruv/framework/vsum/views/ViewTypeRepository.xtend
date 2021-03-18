package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.Map
import java.util.UUID

/**
 * Stores and manages all ViewTypes.
 */
class ViewTypeRepository {

    val Map<UUID, ViewType> registeredViewTypes

    new() {
        registeredViewTypes = new HashMap
    }

    def Collection<ViewType> getViewTypes() {
        return new ArrayList(registeredViewTypes.values)
    }

    def UUID register(ViewType viewType) { // TODO (TS) the design specifies retrieval via an ID. What kind of ID do we want here? (and why?)
        val viewTypeID = UUID.randomUUID
        registeredViewTypes.put(viewTypeID, viewType)
        return viewTypeID
    }

    def ViewType findViewType(UUID viewTypeID) {
        return registeredViewTypes.get(viewTypeID)
    }

}
