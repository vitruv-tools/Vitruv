package tools.vitruv.testutils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Consumer;

import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class TestViewFactory {
	private final ViewProvider viewProvider;
	private VitruvClient client;

	public TestViewFactory(ViewProvider viewProvider) {
		this.viewProvider = viewProvider;
		if (RemoteUsageUtil.shouldUseRemote()) {
			var vsumPath = ((InternalVirtualModel) viewProvider).getFolder();
			var clientPath = Path.of(vsumPath.getParent().getParent().toString(), "client");
			this.client = VitruvClientFactory.create("localhost", clientPath);
		}	
	}

	/**
	 * Creates a view with the given name containing the provided root types (and
	 * its descendants).
	 */
	public View createViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		ViewSelector selector;
		if (RemoteUsageUtil.shouldUseRemote()) {
			//we need to set the name of the only registered test view type to the given view name
			//because when querying the remote selector the requested view type name must match the registered one
			var model = (InternalVirtualModel) viewProvider;
			var type = (TestViewType) model.getViewTypes().stream().findFirst().get();
			type.setName(viewName);
			var remoteType = client.getViewTypes().stream().findFirst().get();
			selector = client.createSelector(remoteType);
		} else {
			selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
		}
		selector.getSelectableElements().stream()
				.filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
				.forEach(element -> selector.setSelected(element, true));
		View view = selector.createView();
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}

	/**
	 * Changes the given view according to the given modification function. Records
	 * the performed changes, commits the recorded changes, and closes the view
	 * afterwards.
	 */
	public void changeViewRecordingChanges(View view, Consumer<CommittableView> modelModification) throws Exception {
		changeView(view.withChangeRecordingTrait(), modelModification);
	}

	/**
	 * Changes the given view according to the given modification function. Derives
	 * the performed changes using the default strategy, commits the derived
	 * changes, and closes the view afterwards.
	 */
	public void changeViewDerivingChanges(View view, Consumer<CommittableView> modelModification) throws Exception {
		changeView(view.withChangeDerivingTrait(), modelModification);
	}

	/**
	 * Changes the given view according to the given modification function. Derives
	 * the performed changes using the provided strategy, commits the derived
	 * changes, and closes the view afterwards.
	 */
	public void changeViewDerivingChanges(View view, StateBasedChangeResolutionStrategy strategy,
			Consumer<CommittableView> modelModification) throws Exception {
		changeView(view.withChangeDerivingTrait(strategy), modelModification);
	}

	private void changeView(CommittableView view, Consumer<CommittableView> modelModification) throws Exception {
		modelModification.accept(view);
		view.commitChanges();
		view.close();
	}

	/**
	 * Validates the given view by applying the validation function. Closes the view
	 * afterwards.
	 */
	public void validateView(View view, Consumer<View> viewValidation) throws Exception {
		viewValidation.accept(view);
		view.close();
	}
}
