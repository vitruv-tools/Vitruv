package tools.vitruv.framework.views.impl;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.View;

/**
 * A view whose contents can be modified, in particular by a view type
 * implementation.
 */
public interface ModifiableView extends View {
    /**
     * Modifies the contents of the view using the provided modification function.
     *
     * @param modificationFunction a consumer that receives the view's resource set
     */
    void modifyContents(Consumer<ResourceSet> modificationFunction);

    /**
     * Gets the view source associated with this view.
     *
     * @return the changeable view source
     */
    ChangeableViewSource getViewSource();
}