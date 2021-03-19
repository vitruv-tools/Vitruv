package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.views.selection.ViewSelector

interface ViewType {

    def ViewSelector createSelector()

    def View createView(ViewSelector selector)

    /**
     * Returns the uniquely identifying name of the view type.
     */
    def String getName()
}
