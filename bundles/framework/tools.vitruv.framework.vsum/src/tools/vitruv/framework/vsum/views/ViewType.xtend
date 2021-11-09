package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A Vitruv viewtype on the virtual model, providing a view selector and allows creating views.
 */
interface ViewType {

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
}
