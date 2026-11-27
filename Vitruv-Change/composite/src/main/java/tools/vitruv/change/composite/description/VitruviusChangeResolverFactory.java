package tools.vitruv.change.composite.description;

import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.change.atomic.hid.AtomicEChangeHierarchicalIdResolver;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.AtomicEChangeUuidResolver;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.impl.VitruviusChangeHierarchicalIdResolver;
import tools.vitruv.change.composite.description.impl.VitruviusChangeUuidResolver;

/**
 * Factory class to instantiate implementations of a {@link VitruviusChangeResolver}.
 *
 * @author Benedikt Jutz
 */
public class VitruviusChangeResolverFactory {
  /** Private constructor to prevent instantiation. */
  private VitruviusChangeResolverFactory() {}

  /**
   * Instantiates a new resolver that uses {@link Uuid UUIDs}.
   *
   * @param uuidResolver the {@link UuidResolver} to resolve UUIDs with.
   */
  public static VitruviusChangeResolver<Uuid> forUuids(UuidResolver uuidResolver) {
    AtomicEChangeUuidResolver resolver = new AtomicEChangeUuidResolver(uuidResolver);
    return new VitruviusChangeUuidResolver(resolver);
  }

  /**
   * Instantiates a new resolver that uses {@link HierarchicalId HierarchicalIds}.
   *
   * @param resourceSet the {@link ResourceSet} to use with this resolver.
   */
  public static VitruviusChangeResolver<HierarchicalId> forHierarchicalIds(
      ResourceSet resourceSet) {
    AtomicEChangeHierarchicalIdResolver resolver =
        new AtomicEChangeHierarchicalIdResolver(resourceSet);
    return new VitruviusChangeHierarchicalIdResolver(resolver);
  }
}
