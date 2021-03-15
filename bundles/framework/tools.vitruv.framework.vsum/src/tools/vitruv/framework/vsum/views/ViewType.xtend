package tools.vitruv.framework.vsum.views

interface ViewType {

    def ViewSelector createSelector()

    def View createView(ViewSelector selector)
}
