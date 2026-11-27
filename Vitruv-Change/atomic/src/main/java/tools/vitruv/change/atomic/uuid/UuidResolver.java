package tools.vitruv.change.atomic.uuid;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * A UUID resolver manages the mapping of {@link org.eclipse.emf.ecore.EObject} to UUIDs within one
 * resource set. UUIDs are used to uniquely identify an element in changes, independent of the
 * location within a resource set and the actual resource set instance.
 */
public interface UuidResolver {
  /**
   * Returns whether the given {@link org.eclipse.emf.ecore.EObject} has a registered UUID or not.
   */
  public default boolean hasUuid(EObject eObject) {
    try {
      Uuid uuid = getUuid(eObject);
      return uuid != null;
    } catch (IllegalStateException e) {
      return false;
    }
  }

  /**
   * Returns whether an {@link org.eclipse.emf.ecore.EObject} is registered for the given UUID or
   * not.
   */
  public default boolean hasEObject(Uuid uuid) {
    try {
      EObject eObject = getEObject(uuid);
      return eObject != null;
    } catch (IllegalStateException e) {
      return false;
    }
  }

  /**
   * Returns the UUID for the given {@link org.eclipse.emf.ecore.EObject}. If no UUID is registered
   * for it, an {@link IllegalStateException} is thrown.
   */
  public Uuid getUuid(EObject eObject) throws IllegalStateException;

  /**
   * Returns the {@link org.eclipse.emf.ecore.EObject} for the given UUID. If more than one object
   * was registered for the UUID, the last one is returned.
   *
   * @throws IllegalStateException if no {@link org.eclipse.emf.ecore.EObject} was registered for
   *     the UUID
   */
  public EObject getEObject(Uuid uuid) throws IllegalStateException;

  /**
   * Generates a new UUID for the given {@link org.eclipse.emf.ecore.EObject}.
   *
   * @param eObject is the object to generate a UUID for. Must not be <code>null</code> or a proxy.
   */
  public Uuid generateUuid(EObject eObject);

  /**
   * Registers the given {@link org.eclipse.emf.ecore.EObject} for the given UUID.
   *
   * @param uuid is the UUID to register the {@link org.eclipse.emf.ecore.EObject} for. Must not be
   *     <code>null</code>.
   * @param eObject is the {@link org.eclipse.emf.ecore.EObject} to register. Must not be <code>null
   *     </code> or a proxy.
   * @throws IllegalStateException if there is already another UUID registered for the given {@link
   *     org.eclipse.emf.ecore.EObject} or vice versa.
   */
  public void registerEObject(Uuid uuid, EObject eObject) throws IllegalStateException;

  /**
   * Registers the given {@link org.eclipse.emf.ecore.EObject} for a newly generated UUID and
   * returns that UUID. The UUID is generated using {@link UuidResolver#generateUuid}.
   *
   * @param eObject is the object to register. Must not be <code>null</code> or a proxy.
   * @return the UUID registered for the given {@link org.eclipse.emf.ecore.EObject}.
   * @throws IllegalStateException if there is already another UUID registered for the given {@link
   *     org.eclipse.emf.ecore.EObject}.
   */
  public default Uuid registerEObject(EObject eObject) throws IllegalStateException {
    Uuid uuid = generateUuid(eObject);
    registerEObject(uuid, eObject);
    return uuid;
  }

  /** Unregisters the given {@link org.eclipse.emf.ecore.EObject} from the given UUID. */
  public void unregisterEObject(Uuid uuid, EObject eObject) throws IllegalStateException;

  /**
   * Returns the {@link Resource} for the given {@link URI}. If the resource does not exist yet, it
   * gets created.
   */
  public Resource getResource(URI uri);

  /**
   * Ends a transactions such that any registered {@link org.eclipse.emf.ecore.EObject} not being
   * contained in a resource throws an error.
   *
   * @throws IllegalStateException if an uncontained element is registered.
   */
  public void endTransaction() throws IllegalStateException;

  /**
   * Resolves all {@link org.eclipse.emf.ecore.EObject}s contained in any resource of the given
   * mapping's key set to its counterpart {@link org.eclipse.emf.ecore.EObject} in the corresponding
   * resource of the <code>targetUuidResolver</code> and registers the resolved object under the
   * same UUID as in the current resolver. The resource correspondences are determined by the <code>
   * sourceToTargetResourceMapping</code>. Each resource pair in the mapping is expected to be
   * structurally equal.
   *
   * @param sourceToTargetResourceMapping is the mapping between resources from the current resolver
   *     to the given target resolver. Must not be <code>null</code>. The key set must contain only
   *     resources of the current UUID resolver. The values must contain only resources of the
   *     target UUID resolver. Each resource pair is expected to be structurally equal.
   * @param targetUuidResolver is the {@link UuidResolver} to resolve the given resources in. Must
   *     not be <code>null</code>.
   * @throws IllegalStateException if any {@link org.eclipse.emf.ecore.EObject} of the current
   *     resolver is not contained in a resource or a resource pair is not structurally equal.
   */
  public void resolveResources(
      Map<Resource, Resource> sourceToTargetResourceMapping, UuidResolver targetUuidResolver)
      throws IllegalStateException;

  /**
   * Resolves all {@link org.eclipse.emf.ecore.EObject}s contained in the given <code>sourceResource
   * </code> to its counterpart {@link org.eclipse.emf.ecore.EObject} in the <code>targetResource
   * </code> and registers the resolved object under the same UUID as in the current resolver. The
   * source and target resources are expected to be structurally equal.
   *
   * @param sourceResource is the source resource, contained in the current resolver's resource set.
   *     Must not be <code>null</code>.
   * @param targetResource is the target resource, contained in the target resolver's resource set.
   *     Must not be <code>null</code>.
   * @param targetUuidResolver is the {@link UuidResolver} to resolve the given resources in. Must
   *     not be <code>null</code>.
   * @throws IllegalStateException if any {@link org.eclipse.emf.ecore.EObject} of the current
   *     resolver is not contained in a resource or the given resources are not structurally equal.
   */
  public default void resolveResource(
      Resource sourceResource, Resource targetResource, UuidResolver targetUuidResolver)
      throws IllegalStateException {
    checkState(sourceResource != null, "source resource must not be null");
    checkState(targetResource != null, "target resource must not be null");
    resolveResources(Map.of(sourceResource, targetResource), targetUuidResolver);
  }

  /**
   * Creates a new {@link UuidResolver} with the given resource set.
   *
   * @param resourceSet is the resource set the UUID resolver uses.
   * @return a new {@link UuidResolver} instance.
   */
  public static UuidResolver create(ResourceSet resourceSet) {
    return new UuidResolverImpl(resourceSet);
  }

  /**
   * Stores the contents of this resolver at the given {@link URI}.
   *
   * @param uri is the {@link URI} to store the serialization at. Must not be <code>null</code> and
   *     must be a file URI.
   * @throws IOException if saving to file fails.
   * @throws IllegalStateException if any {@link org.eclipse.emf.ecore.EObject} of the current
   *     resolver is not contained in a resource.
   */
  public void storeAtUri(URI uri) throws IOException, IllegalStateException;

  /**
   * Initializes this resolver with the contents at the given {@link URI}. Can only be called before
   * any elements are registered with this resolver.
   *
   * @param uri is the {@link URI} to load the serialization from. Must not be <code>null</code> and
   *     must be a file URI.
   * @throws IOException if reading the file fails.
   * @throws IllegalStateException if this resolver already has elements registered.
   */
  public void loadFromUri(URI uri) throws IOException, IllegalStateException;
}
