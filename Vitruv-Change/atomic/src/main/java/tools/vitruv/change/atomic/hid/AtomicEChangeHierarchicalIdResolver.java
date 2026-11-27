package tools.vitruv.change.atomic.hid;

import static com.google.common.base.Preconditions.checkState;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.command.internal.ApplyEChangeSwitch;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.change.atomic.feature.reference.UpdateReferenceEChange;
import tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver;
import tools.vitruv.change.atomic.resolve.AtomicEChangeResolverHelper;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;

/**
 * A resolver for resolving a change with {@link HierarchicalId} to {@link EObject} or vice versa.
 */
public class AtomicEChangeHierarchicalIdResolver {
  private HierarchicalIdResolver idResolver;

  /**
   * Creates a new {@link AtomicEChangeHierarchicalIdResolver} with the given {@link
   * HierarchicalIdResolver}.
   *
   * @param resourceSet the {@link ResourceSet} to use for resolving and applying atomic changes
   */
  public AtomicEChangeHierarchicalIdResolver(ResourceSet resourceSet) {
    this.idResolver = HierarchicalIdResolver.create(resourceSet);
  }

  /**
   * Resolves the given change using its {@link HierarchicalIdResolver} and applies it forward. The
   * associated resource set must be in the state before the change has been applied.
   *
   * @param unresolvedEChange the change to resolve and apply.
   * @return Returns the resolved change.
   */
  public EChange<EObject> resolveAndApplyForward(EChange<HierarchicalId> unresolvedEChange) {
    EChange<EObject> resolvedChange = resolve(unresolvedEChange);
    applyForward(resolvedChange);
    return resolvedChange;
  }

  /**
   * Applies a change backward. Since hierarchical Ids can only be assigned when the resource set is
   * in the state before the change, this method might be used to reverse some change to allow Id
   * assignment.
   *
   * @param resolvedEChange the change to reverse.
   * @see AtomicEChangeHierarchicalIdResolver#applyForwardAndAssignIds(EChange)
   */
  public void applyBackward(EChange<EObject> resolvedEChange) {
    ApplyEChangeSwitch.applyEChange(resolvedEChange, false);
  }

  /**
   * Gets {@link HierarchicalId HierarchicalIds} for all elements of the given change, applies it
   * forward, and returns the Id-assigned change. The associated resource set must be in the state
   * before the change has been applied.
   *
   * @param resolvedEChange the change to assign hierarchical Ids for.
   * @return Returns the Id-assigned change.
   * @see AtomicEChangeHierarchicalIdResolver#applyBackward(EChange)
   */
  public EChange<HierarchicalId> applyForwardAndAssignIds(EChange<EObject> resolvedEChange) {
    EChange<HierarchicalId> unresolvedChange = unresolve(resolvedEChange);
    applyForward(resolvedEChange);
    return unresolvedChange;
  }

  /**
   * Ends a transactions such that all {@link EObject}s not being contained in a resource, which is
   * contained in a resource set, are removed from the hierarchical ID mapping.
   */
  public void endTransaction() {
    idResolver.endTransaction();
  }

  private EChange<EObject> resolve(EChange<HierarchicalId> unresolvedChange) {
    return AtomicEChangeResolverHelper.resolveChange(
        unresolvedChange,
        id -> {
          if (unresolvedChange instanceof CreateEObject<HierarchicalId> createChange) {
            EObject createdElement = EcoreUtil.create(createChange.getAffectedEObjectType());
            HierarchicalId createdId = idResolver.getAndUpdateId(createdElement);
            checkState(
                createdId.equals(id),
                "generated ID %s does not match the original ID %s on element creation",
                createdId,
                id);
            return createdElement;
          } else {
            return idResolver.getEObject(id);
          }
        },
        resource -> idResolver.getResource(resource.getURI()));
  }

  private EChange<HierarchicalId> unresolve(EChange<EObject> resolvedChange) {
    return AtomicEChangeResolverHelper.resolveChange(
        resolvedChange,
        eObject -> idResolver.getAndUpdateId(eObject),
        resource -> idResolver.getResource(resource.getURI()));
  }

  private void applyForward(EChange<EObject> resolvedChange) {
    EObject affectedEObject = getAffectedEObject(resolvedChange);
    HierarchicalId affectedId = idResolver.getAndUpdateId(affectedEObject);
    EObject oldObject = getOldContainedEObject(resolvedChange);
    ApplyEChangeSwitch.applyEChange(resolvedChange, true);
    if (isContainmentChange(resolvedChange)
        || affectedId != idResolver.getAndUpdateId(affectedEObject)) {
      refreshIds(affectedEObject);
    }
    if (oldObject != null) {
      refreshIds(oldObject);
    }
  }

  private static EObject getAffectedEObject(EChange<EObject> eChange) {
    if (eChange instanceof FeatureEChange<EObject, ?> featureChange) {
      return featureChange.getAffectedElement();
    } else if (eChange instanceof EObjectExistenceEChange<EObject> existenceChange) {
      return existenceChange.getAffectedElement();
    } else if (eChange instanceof InsertRootEObject<EObject> insertChange) {
      return insertChange.getNewValue();
    } else if (eChange instanceof RemoveRootEObject<EObject> removeChange) {
      return removeChange.getOldValue();
    }
    throw new IllegalArgumentException(
        "cannot identify affected EObject of change %s".formatted(eChange));
  }

  private static EObject getOldContainedEObject(EChange<EObject> eChange) {
    if ((eChange instanceof SubtractiveReferenceEChange<EObject> subtractiveChange)
        && (subtractiveChange.isContainment())) {
      return subtractiveChange.getOldValue();
    }
    return null;
  }

  private static boolean isContainmentChange(EChange<EObject> eChange) {
    if (eChange instanceof UpdateReferenceEChange<EObject> referenceChange) {
      return referenceChange.isContainment();
    }
    return false;
  }

  private void refreshIds(EObject eObject) {
    idResolver.getAndUpdateId(eObject);
    eObject.eContents().forEach(this::refreshIds);
  }
}
