package tools.vitruv.framework.vsum.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A resource set that creates, looks up and loads its resources while holding its own monitor, so
 * that threads can do so concurrently. This includes the demand-loading of a resource when a proxy
 * into it is resolved, so reading a model from several threads does not corrupt the resource list.
 *
 * <p>Code that iterates or changes {@link #getResources()} directly must synchronize on this
 * resource set as well. The resolution of a proxy still replaces the proxy in the referencing list
 * without a lock; concurrent readers of the same reference write the same resolved object, and
 * writers must exclude readers of the elements they change anyway.
 */
class SynchronizedResourceSet extends ResourceSetImpl {
  @Override
  public synchronized Resource getResource(URI uri, boolean loadOnDemand) {
    return super.getResource(uri, loadOnDemand);
  }

  @Override
  public synchronized EObject getEObject(URI uri, boolean loadOnDemand) {
    return super.getEObject(uri, loadOnDemand);
  }

  @Override
  public synchronized Resource createResource(URI uri) {
    return super.createResource(uri);
  }

  @Override
  public synchronized Resource createResource(URI uri, String contentType) {
    return super.createResource(uri, contentType);
  }
}
