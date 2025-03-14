package tools.vitruv.framework.testutils.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.function.Consumer;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

/** A factory for creating views for testing purposes. */
public class TestViewFactory {
  private final ViewProvider viewProvider;

  /**
   * Creates a new view factory with the given view provider.
   *
   * @param viewProvider The view provider to use.
   */
  public TestViewFactory(ViewProvider viewProvider) {
    this.viewProvider = viewProvider;
  }

  /**
   * Creates a view with the given name containing the provided root types (and its descendants).
   */
  public View createViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
    ViewSelector selector =
        viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
    selector.getSelectableElements().stream()
        .filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
        .forEach(element -> selector.setSelected(element, true));
    View view = selector.createView();
    assertThat("view must not be null", view, not(equalTo(null)));
    return view;
  }

  /**
   * Changes the given view according to the given modification function. Records the performed
   * changes, commits the recorded changes, and closes the view afterwards.
   */
  public void changeViewRecordingChanges(View view, Consumer<CommittableView> modelModification)
      throws Exception {
    changeView(view.withChangeRecordingTrait(), modelModification);
  }

  /**
   * Changes the given view according to the given modification function. Derives the performed
   * changes using the default strategy, commits the derived changes, and closes the view
   * afterwards.
   */
  public void changeViewDerivingChanges(View view, Consumer<CommittableView> modelModification)
      throws Exception {
    changeView(view.withChangeDerivingTrait(), modelModification);
  }

  /**
   * Changes the given view according to the given modification function. Derives the performed
   * changes using the provided strategy, commits the derived changes, and closes the view
   * afterwards.
   */
  public void changeViewDerivingChanges(
      View view,
      StateBasedChangeResolutionStrategy strategy,
      Consumer<CommittableView> modelModification)
      throws Exception {
    changeView(view.withChangeDerivingTrait(strategy), modelModification);
  }

  private void changeView(CommittableView view, Consumer<CommittableView> modelModification)
      throws Exception {
    modelModification.accept(view);
    view.commitChanges();
    view.close();
  }

  /** Validates the given view by applying the validation function. Closes the view afterwards. */
  public void validateView(View view, Consumer<View> viewValidation) throws Exception {
    viewValidation.accept(view);
    view.close();
  }
}
