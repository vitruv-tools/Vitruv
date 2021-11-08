package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A Vitruv viewtype on the virtual model, providing a view selector and allows creating views.
 */
interface ViewType {

    def ViewSelector createSelector()

    def View createView(ViewSelector selector)

    /**
     * Returns the uniquely identifying name of the view type.
     */
    def String getName()
}
