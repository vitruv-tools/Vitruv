package tools.vitruv.change.atomic.uuid;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.command.internal.ApplyEChangeSwitch;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.resolve.AtomicEChangeResolverHelper;

/** A resolver for resolving a change with {@link Uuid} to {@link EObject} or vice versa. */
public class AtomicEChangeUuidResolver {
  private UuidResolver uuidResolver;

  /**
   * Creates a new {@link AtomicEChangeUuidResolver} with the given {@link UuidResolver}.
   *
   * @param uuidResolver the {@link UuidResolver} to use for resolving and applying atomic changes
   */
  public AtomicEChangeUuidResolver(UuidResolver uuidResolver) {
    this.uuidResolver = uuidResolver;
  }

  /**
   * Resolves the given change using its {@link UuidResolver} and applies it forward. The associated
   * resource set must be in the state before the change has been applied.
   *
   * @param unresolvedEChange the change to resolve and apply.
   * @return Returns the resolved change.
   */
  public EChange<EObject> resolveAndApplyForward(EChange<Uuid> unresolvedEChange) {
    EChange<EObject> resolvedEChange = resolve(unresolvedEChange);
    ApplyEChangeSwitch.applyEChange(resolvedEChange, true);
    updateUuidResolver(resolvedEChange, unresolvedEChange);
    return resolvedEChange;
  }

  private EChange<EObject> resolve(EChange<Uuid> unresolvedChange) {
    return AtomicEChangeResolverHelper.resolveChange(
        unresolvedChange,
        uuid -> {
          if (unresolvedChange instanceof CreateEObject<Uuid> createChange) {
            return EcoreUtil.create(createChange.getAffectedEObjectType());
          } else {
            return uuidResolver.getEObject(uuid);
          }
        },
        this::resourceResolver);
  }

  /**
   * Gets or registers {@link Uuid Uuids} for all elements of the given change and returns the
   * Uuid-assigned change. The associated resource set must be in the state after the change has
   * been applied.
   *
   * @param resolvedEChange the change to assign Uuids for.
   * @return Returns the Uuid-assigned change.
   */
  public EChange<Uuid> assignIds(EChange<EObject> resolvedEChange) {
    EChange<Uuid> unresolvedEChange =
        AtomicEChangeResolverHelper.resolveChange(
            resolvedEChange,
            eObject -> {
              if (uuidResolver.hasUuid(eObject)) {
                return uuidResolver.getUuid(eObject);
              } else {
                if (resolvedEChange instanceof CreateEObject<EObject> createChange
                    && createChange.getAffectedElement() == eObject) {
                  return uuidResolver.registerEObject(eObject);
                } else {
                  throw new IllegalStateException(
                      "trying to assign UUID for unknown element %s of change %s"
                          .formatted(eObject, resolvedEChange));
                }
              }
            },
            this::resourceResolver);
    updateUuidResolver(resolvedEChange, unresolvedEChange);
    return unresolvedEChange;
  }

  /**
   * Ends a transactions such that any registered {@link EObject} not being contained in a resource
   * throws an error.
   *
   * @throws IllegalStateException if an uncontained element is registered in the {@link
   *     UuidResolver}.
   */
  public void endTransaction() {
    uuidResolver.endTransaction();
  }

  private void updateUuidResolver(EChange<EObject> resolvedChange, EChange<Uuid> unresolvedChange) {
    if (resolvedChange instanceof CreateEObject<EObject> createResolvedChange
        && unresolvedChange instanceof CreateEObject<Uuid> createUnresolvedChange) {
      uuidResolver.registerEObject(
          createUnresolvedChange.getAffectedElement(), createResolvedChange.getAffectedElement());
    } else if (resolvedChange instanceof DeleteEObject<EObject> deleteResolvedChange
        && unresolvedChange instanceof DeleteEObject<Uuid> deleteUnresolvedChange) {
      uuidResolver.unregisterEObject(
          deleteUnresolvedChange.getAffectedElement(), deleteResolvedChange.getAffectedElement());
    }
  }

  private Resource resourceResolver(Resource sourceResource) {
    return uuidResolver.getResource(sourceResource.getURI());
  }
}
