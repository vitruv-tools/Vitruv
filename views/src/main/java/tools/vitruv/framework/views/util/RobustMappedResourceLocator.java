package tools.vitruv.framework.views.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A {@link ResourceSetImpl.MappedResourceLocator}, which indexes the resources of a resource set by
 * their URIs, that tolerates removing resources it has no index entry for.
 *
 * <p>EMF's locator fails with a {@link NullPointerException} when a resource without an index entry
 * is removed from the resource set. This happens, for example, when several resources are removed
 * at once (like with {@code getResources().clear()}) and the locator rebuilds its index while
 * handling the first of them: it then builds the index from the resource set, which no longer
 * contains any of the removed resources.
 */
public class RobustMappedResourceLocator extends ResourceSetImpl.MappedResourceLocator {
  /**
   * Creates the locator and registers it at the given resource set.
   *
   * @param resourceSet the resource set whose resources to index
   */
  public RobustMappedResourceLocator(ResourceSetImpl resourceSet) {
    super(resourceSet);
    // replace the adapter that the super constructor registered
    resourceSet.eAdapters().removeIf(ResourceAdapter.class::isInstance);
    resourceSet.eAdapters().add(new RobustResourceAdapter());
  }

  private class RobustResourceAdapter extends ResourceAdapter {
    @Override
    protected void unsetTarget(Resource target) {
      URI uri = target.getURI();
      URI normalizedUri = uri != null ? normalizationMap.get(uri) : null;
      EList<Resource> resources = normalizedUri != null ? resourceMap.get(normalizedUri) : null;
      if (resources != null && resources.contains(target)) {
        super.unsetTarget(target);
      } else {
        basicUnsetTarget(target);
      }
    }
  }
}
