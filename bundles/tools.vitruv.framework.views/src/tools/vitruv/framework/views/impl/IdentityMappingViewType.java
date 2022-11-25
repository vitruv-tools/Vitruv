package tools.vitruv.framework.views.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.selectors.DirectViewElementSelector;
import tools.vitruv.framework.views.util.ResourceCopier;

/**
 * A view type that allows creating views based on a basic element-wise selection mechanism
 * and providing a one-to-one (identity) mapping of elements within the {@link ViewSource}
 * to a created {@link View}.
 */
public class IdentityMappingViewType extends AbstractViewType<DirectViewElementSelector> {
	public IdentityMappingViewType(String name) {
		super(name);
	}

	@Override
	public DirectViewElementSelector createSelector(ChangeableViewSource viewSource) {
		return new DirectViewElementSelector(this, viewSource,
				viewSource.getViewSourceModels().stream().map(resource -> {
					if (!resource.getContents().isEmpty() && ResourceCopier.requiresFullCopy(resource)) {
						// We can only copy writable UML resources as a whole, so no option to select
						// specific root elements
						return List.of(resource.getContents().get(0));
					}
					return resource.getContents();
				}).flatMap(List::stream).filter(it -> it != null).collect(Collectors.toList()));
	}

	@Override
	public ModifiableView createView(DirectViewElementSelector selector) {
		checkArgument(selector.getViewType() == this, "cannot create view with selector for different view type");
		return new BasicView(selector.getViewType(), selector.getViewSource(), selector.getSelection());
	}

	@Override
	public void updateView(ModifiableView view) {
		view.modifyContents((viewResourceSet, viewUuidResolver) -> {
			viewResourceSet.getResources().forEach(Resource::unload);
			viewResourceSet.getResources().clear();
			viewUuidResolver.endTransaction();

			Collection<Resource> viewSources = view.getViewSource().getViewSourceModels();
			ViewSelection selection = view.getSelection();
			List<Resource> resourcesWithSelectedElements = viewSources.stream()
					.filter(resource -> resource.getContents().stream().anyMatch(selection::isViewObjectSelected))
					.collect(Collectors.toList());
			Map<Resource, Resource> mapping = new ResourceCopier().copyViewSourceResources(resourcesWithSelectedElements,
					viewResourceSet, selection::isViewObjectSelected);
			view.getViewSource().getUuidResolver().resolveResources(mapping, viewUuidResolver);
		});
	}
}
