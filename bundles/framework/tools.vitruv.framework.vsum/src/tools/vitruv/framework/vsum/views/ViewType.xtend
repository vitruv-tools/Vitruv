package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A Vitruv viewtype on the virtual model, providing a view selector and allows creating views.
 */
interface ViewType<S extends ViewSelector> { // TODO TS: Should a view be able to de-register from its viewtype upon closing? HK: No

    /**
     * Returns the view selector of the viewtype, which allows configuring views.
     */
    def S createSelector(ChangeableViewSource viewSource)

    /**
     * Instantiates a view based on a selection made in the specified view selector.
     */
    def View createView(S selector)

    /**
     * Returns the uniquely identifying name of the view type.
     */
    def String getName()
}
