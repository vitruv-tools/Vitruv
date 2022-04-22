package tools.vitruv.testutils

import java.util.Collection
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.views.CommittableView
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewProvider
import tools.vitruv.framework.views.ViewTypeFactory
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.MatcherAssert.assertThat

@FinalFieldsConstructor
class TestViewFactory {
    val ViewProvider viewProvider

    /**
     * Creates a view with the given name containing the provided root types (and its descendants).
     */
    def View createViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
        val selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName))

        for (rootElement : selector.selectableElements.filter[element|rootTypes.exists[it.isInstance(element)]]) {
            selector.setSelected(rootElement, true)
        }
        val view = selector.createView()
        assertThat("view must not be null", view, not(equalTo(null)))
        return view
    }

    /**
     * Changes the given view according to the given modification function. 
     * Records the performed changes, commits the recorded changes, and closes the view afterwards.
     */
    def void changeViewRecordingChanges(View view, (CommittableView)=>void modelModification) {
        changeView(view.withChangeRecordingTrait, modelModification)
    }

    /**
     * Changes the given view according to the given modification function. 
     * Derives the performed changes using the default strategy, commits the derived changes, and closes the view afterwards.
     */
    def void changeViewDerivingChanges(View view, (CommittableView)=>void modelModification) {
        changeView(view.withChangeDerivingTrait, modelModification)
    }

    /**
     * Changes the given view according to the given modification function. 
     * Derives the performed changes using the provided strategy, commits the derived changes, and closes the view afterwards.
     */
    def void changeViewDerivingChanges(View view, StateBasedChangeResolutionStrategy strategy,
        (CommittableView)=>void modelModification) {
        changeView(view.withChangeDerivingTrait(strategy), modelModification)
    }

    private def void changeView(CommittableView view, (CommittableView)=>void modelModification) {
        modelModification.apply(view)
        view.commitChanges()
        view.close()
    }

    /**
     * Validates the given view by applying the validation function. Closes the view afterwards.
     */
    def void validateView(View view, (View)=>void viewValidation) {
        viewValidation.apply(view)
        view.close()
    }
}
