package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A Vitruv viewtype on the virtual model, providing a view selector and allows creating views.
 */
interface ViewType { // TODO TS: Should a view be able to de-register from its viewtype upon closing?

    /**
     * Returns the view selector of the viewtype, which allows configuring views.
     */
    def ViewSelector createSelector()

    /**
     * Instantiates a view based on a selection made in the specified view selector.
     */
    def View createView(ViewSelector selector)

    /**
     * Returns the uniquely identifying name of the view type.
     */
    def String getName()

    /**
     * Updates a view that is created from this viewtype to ensure it is consistent with the virtual model.
     * @param view is the view to be updated.
     */
    def ResourceSet updateView(View view)
}
