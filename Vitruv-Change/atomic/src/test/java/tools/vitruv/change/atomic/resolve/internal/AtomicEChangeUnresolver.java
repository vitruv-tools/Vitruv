package tools.vitruv.change.atomic.resolve.internal;

import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.resolve.AtomicEChangeResolverHelper;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;

/**
 * Utility class to allow unresolving changes that were created using the atomic change factories.
 * Since changes created by the factory are not yet applied, the {@link
 * EChangeUuidResolverAndApplicator#unresolveAndApplyBackward} method cannot be used.
 */
public class AtomicEChangeUnresolver {
  private UuidResolver uuidResolver;
  private ResourceSet uuidResolverResourceSet;

  /**
   * Creates a new instance of the {@link AtomicEChangeUnresolver}.
   *
   * @param uuidResolver The {@link UuidResolver} to use for resolving the changes.
   * @param uuidResolverResourceSet The {@link ResourceSet} to use for resolving the changes.
   */
  public AtomicEChangeUnresolver(UuidResolver uuidResolver, ResourceSet uuidResolverResourceSet) {
    this.uuidResolver = uuidResolver;
    this.uuidResolverResourceSet = uuidResolverResourceSet;
  }

  /**
   * Unresolves the given {@link EChange} using the {@link UuidResolver} of this instance.
   *
   * @param eChange The {@link EChange} to unresolve.
   * @return The unresolving {@link EChange}.
   */
  public EChange<Uuid> unresolve(EChange<? extends EObject> eChange) {
    UuidResolver temporaryUuidResolver = UuidResolver.create(uuidResolverResourceSet);
    return unresolve(eChange, temporaryUuidResolver);
  }

  /**
   * Unresolves the given list of {@link EChange}s using the {@link UuidResolver} of this instance.
   *
   * @param eChanges The list of {@link EChange}s to unresolve.
   * @return The list of unresolving {@link EChange}s.
   */
  public List<EChange<Uuid>> unresolve(List<? extends EChange<? extends EObject>> eChanges) {
    UuidResolver temporaryUuidResolver = UuidResolver.create(uuidResolverResourceSet);
    return eChanges.stream().map(eChange -> unresolve(eChange, temporaryUuidResolver)).toList();
  }

  /**
   * Unresolves the given {@link EChange} using the given {@link UuidResolver}.
   *
   * @param eChange The {@link EChange} to unresolve.
   * @param temporaryUuidResolver The {@link UuidResolver} to use for resolving the changes.
   * @return The unresolving {@link EChange}.
   */
  private EChange<Uuid> unresolve(
      EChange<? extends EObject> eChange, UuidResolver temporaryUuidResolver) {
    return AtomicEChangeResolverHelper.resolveChange(
        eChange,
        eObject -> {
          if (uuidResolver.hasUuid(eObject)) {
            return uuidResolver.getUuid(eObject);
          } else if (temporaryUuidResolver.hasUuid(eObject)) {
            return temporaryUuidResolver.getUuid(eObject);
          } else {
            return temporaryUuidResolver.registerEObject(eObject);
          }
        },
        resource -> uuidResolver.getResource(resource.getURI()));
  }
}
