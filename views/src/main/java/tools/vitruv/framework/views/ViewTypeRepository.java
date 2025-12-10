package tools.vitruv.framework.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Stores and manages all ViewTypes.
 */
public class ViewTypeRepository implements ViewTypeProvider {

    private final Map<String, ViewType<?>> registeredViewTypes;

    /**
     * Creates a new ViewTypeRepository.
     */
    public ViewTypeRepository() {
        this.registeredViewTypes = new HashMap<>();
    }

    @Override
    public Collection<ViewType<?>> getViewTypes() {
        return new ArrayList<>(registeredViewTypes.values());
    }

    /**
     * Registers a view type in this repository.
     *
     * @param viewType the view type to register, must not be null
     * @throws IllegalArgumentException if viewType is null
     * @throws IllegalStateException    if a view type with the same name is already
     *                                  registered
     */
    public void register(ViewType<?> viewType) {
        checkArgument(viewType != null, "null cannot be added as a view type");
        checkState(!registeredViewTypes.containsKey(viewType.getName()),
                "view type name '%s' already taken by another view type", viewType.getName());
        registeredViewTypes.put(viewType.getName(), viewType);
    }

    /**
     * Finds a view type by its name.
     *
     * @param viewTypeName the name of the view type to search for, must not be null
     *                     or empty
     * @return the view type with the given name, or null if not found
     * @throws IllegalArgumentException if viewTypeName is null or empty
     */
    public ViewType<?> findViewType(String viewTypeName) {
        checkArgument(viewTypeName != null && !viewTypeName.isEmpty(),
                "view type name to search for must not be null or empty");
        return registeredViewTypes.get(viewTypeName);
    }
}