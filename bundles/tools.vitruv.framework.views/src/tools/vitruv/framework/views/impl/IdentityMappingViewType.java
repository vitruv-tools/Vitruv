package tools.vitruv.framework.views.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.change.atomic.EChangeUuidManager;
import tools.vitruv.change.atomic.id.IdResolver;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.changederivation.DeltaBasedResourceUtil;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSource;
import tools.vitruv.framework.views.selectors.DirectViewElementSelector;

/**
 * A view type that allows creating views based on a basic element-wise
 * selection mechanism and providing a one-to-one (identity) mapping of elements
 * within the {@link ViewSource} to a created {@link View}.
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
						// Some resources (like UML) can only be copied as a whole, so no option to select
						// specific root elements
						return Stream.of(resource.getContents().get(0));
					}
					return resource.getContents().stream();
				}).flatMap(Function.identity()).filter(it -> it != null).toList());
	}

	@Override
	public ModifiableView createView(DirectViewElementSelector selector) {
		checkArgument(selector.getViewType() == this, "cannot create view with selector for different view type");
		return new BasicView(selector.getViewType(), selector.getViewSource(), selector.getSelection());
	}

	@Override
	public void updateView(ModifiableView view) {
		view.modifyContents((viewResourceSet) -> {
			viewResourceSet.getResources().forEach(Resource::unload);
			viewResourceSet.getResources().clear();
			createViewResources(view, viewResourceSet);
		});
	}

	@Override
	public void commitViewChanges(ModifiableView view, VitruviusChange viewChange) {
		ResourceSet viewSourceCopyResourceSet = DeltaBasedResourceUtil.withDeltaFactory(new ResourceSetImpl());
		IdResolver viewSourceCopyIdResolver = IdResolver.create(viewSourceCopyResourceSet);
		UuidResolver viewSourceCopyUuidResolver = UuidResolver.create(viewSourceCopyResourceSet);
		Map<Resource, Resource> mapping = createViewResources(view, viewSourceCopyResourceSet);
		view.getViewSource().getUuidResolver().resolveResources(mapping, viewSourceCopyUuidResolver);

		VitruviusChange resolvedChange = viewChange.unresolve().resolveAndApply(viewSourceCopyIdResolver);
		EChangeUuidManager.setOrGenerateIds(resolvedChange.getEChanges(), viewSourceCopyUuidResolver);
		view.getViewSource().propagateChange(resolvedChange.unresolve());
	}

	private Map<Resource, Resource> createViewResources(ModifiableView view, ResourceSet viewResourceSet) {
		Collection<Resource> viewSources = view.getViewSource().getViewSourceModels();
		ViewSelection selection = view.getSelection();
		List<Resource> resourcesWithSelectedElements = viewSources.stream()
				.filter(resource -> resource.getContents().stream().anyMatch(selection::isViewObjectSelected)).toList();
		return ResourceCopier.copyViewSourceResources(resourcesWithSelectedElements, viewResourceSet,
				selection::isViewObjectSelected);
	}
}
