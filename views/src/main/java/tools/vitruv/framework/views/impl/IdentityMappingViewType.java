package tools.vitruv.framework.views.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSource;
import tools.vitruv.framework.views.selectors.DirectViewElementSelector;

/**
 * A view type that allows creating views based on a basic element-wise selection mechanism and
 * providing a one-to-one (identity) mapping of elements within the {@link ViewSource} to a created
 * {@link View}.
 */
public class IdentityMappingViewType
    extends AbstractViewType<DirectViewElementSelector<HierarchicalId>, HierarchicalId> {
  /**
   * Creates a new {@link IdentityMappingViewType} with the given name.
   *
   * @param name the name of the view type
   */
  public IdentityMappingViewType(String name) {
    super(name);
  }

  /**
   * Creates a new {@link IdentityMappingViewType} with the given name and metamodel EPackage.
   *
   * @param name the name of the view type
   * @param metamodel the metamodel of the view type
   */
  public IdentityMappingViewType(String name, EPackage metamodel) {
    super(name, metamodel);
  }

  @Override
  public DirectViewElementSelector<HierarchicalId> createSelector(ChangeableViewSource viewSource) {
    return new DirectViewElementSelector<>(
        this,
        viewSource,
        viewSource.getViewSourceModels().stream()
            .map(
                resource -> {
                  if (!resource.getContents().isEmpty()
                      && ResourceCopier.requiresFullCopy(resource)) {
                    // Some resources (like UML) can only be copied as a whole, so no option to
                    // select
                    // specific root elements
                    return Stream.of(resource.getContents().get(0));
                  }
                  return resource.getContents().stream();
                })
            .flatMap(Function.identity())
            .filter(Objects::nonNull)
            .toList());
  }

  @Override
  public ModifiableView createView(DirectViewElementSelector<HierarchicalId> selector) {
    checkArgument(
        selector.getViewType() == this, "cannot create view with selector for different view type");
    return new BasicView(selector.getViewType(), selector.getViewSource(), selector.getSelection());
  }

  @Override
  public void updateView(ModifiableView view) {
    view.modifyContents(
        viewResourceSet -> {
          viewResourceSet.getResources().forEach(Resource::unload);
          viewResourceSet.getResources().clear();
          createViewResources(view, viewResourceSet);
        });
  }

  @Override
  public void commitViewChanges(ModifiableView view, VitruviusChange<HierarchicalId> viewChange) {
    ResourceSetImpl viewSourceCopyResourceSet = new ResourceSetImpl();
    // index the resources by URI, so that resolving IDs does not scan all resources
    new ResourceSetImpl.MappedResourceLocator(viewSourceCopyResourceSet);
    withGlobalFactories(viewSourceCopyResourceSet);
    VitruviusChangeResolver<HierarchicalId> idChangeResolver =
        VitruviusChangeResolverFactory.forHierarchicalIds(viewSourceCopyResourceSet);
    UuidResolver viewSourceCopyUuidResolver = UuidResolver.create(viewSourceCopyResourceSet);
    VitruviusChangeResolver<Uuid> uuidChangeResolver =
        VitruviusChangeResolverFactory.forUuids(viewSourceCopyUuidResolver);
    Map<Resource, Resource> mapping =
        createViewResources(
            view, viewSourceCopyResourceSet, getReferencedResourceUris(viewChange)::contains);
    view.getViewSource().getUuidResolver().resolveResources(mapping, viewSourceCopyUuidResolver);

    VitruviusChange<EObject> resolvedChange = idChangeResolver.resolveAndApply(viewChange);
    VitruviusChange<Uuid> unresolvedChanges = uuidChangeResolver.assignIds(resolvedChange);
    view.getViewSource().propagateChange(unresolvedChanges);
  }

  private Map<Resource, Resource> createViewResources(
      ModifiableView view, ResourceSet viewResourceSet) {
    return createViewResources(view, viewResourceSet, uri -> true);
  }

  /**
   * Copies the selected elements of the view source resources whose URIs match the given filter.
   * The filter is ignored if a root element of a selected resource is contained in another
   * resource, because such resources can only be copied together.
   */
  private Map<Resource, Resource> createViewResources(
      ModifiableView view, ResourceSet viewResourceSet, Predicate<URI> resourceFilter) {
    Collection<Resource> viewSources = view.getViewSource().getViewSourceModels();
    ViewSelection selection = view.getSelection();
    List<Resource> resourcesWithSelectedElements =
        viewSources.stream()
            .filter(
                resource ->
                    resource.getContents().stream().anyMatch(selection::isViewObjectSelected))
            .toList();
    boolean crossResourceContainment =
        resourcesWithSelectedElements.stream()
            .flatMap(resource -> resource.getContents().stream())
            .anyMatch(root -> root.eContainer() != null);
    List<Resource> resourcesToCopy =
        crossResourceContainment
            ? resourcesWithSelectedElements
            : resourcesWithSelectedElements.stream()
                .filter(resource -> resourceFilter.test(resource.getURI()))
                .toList();
    return ResourceCopier.copyViewSourceResources(
        resourcesToCopy, viewResourceSet, selection::isViewObjectSelected);
  }

  /**
   * Returns the URIs of the resources that contain an element the given change refers to. Applying
   * the change only requires copies of these resources.
   */
  private static Set<URI> getReferencedResourceUris(VitruviusChange<HierarchicalId> change) {
    Set<URI> uris = new HashSet<>();
    for (EChange<HierarchicalId> eChange : change.getEChanges()) {
      if (eChange instanceof RootEChange<HierarchicalId> rootChange
          && rootChange.getUri() != null) {
        uris.add(URI.createURI(rootChange.getUri()));
      }
    }
    for (EObject element : change.getAffectedAndReferencedEObjects()) {
      if (element instanceof HierarchicalId id && !id.isCache()) {
        uris.add(URI.createURI(id.getId()).trimFragment());
      }
    }
    return uris;
  }
}
