package tools.vitruv.change.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/** Offers possibilities for resource access and persistence. */
public interface ResourceAccess {
  /**
   * Gets the {@link URI} of a model that stores metadata.
   *
   * @param metadataKey The key uniquely identifying the metadata model. The different parts of the
   *     key can be used to convey some sort of hierarchy in the metadata. The key may contain
   *     arbitrary characters. The last key part contains the metadata model's file name and
   *     extension.
   */
  URI getMetadataModelURI(String... metadataKey);

  /**
   * Provides the resource for storing the specified model in it.
   *
   * @param uri the model's uri, must not be {@code null}
   */
  Resource getModelResource(URI uri);

  /**
   * Persists the given {@code rootObject} at the given {@code uri}.
   *
   * @param rootObject the object to persist, must not be {@code null}
   * @param uri the URI to persist at, must not be {@code null}
   */
  void persistAsRoot(EObject rootObject, URI uri);
}
