package tools.vitruv.change.atomic;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;

/**
 * Static utility class for the EChange package and subpackages.
 */
@Utility
public class EChangeUtil {
  /**
   * Private constructor for utility class.
   */
  private EChangeUtil() {
  }

  /**
   * Tests if change represents the removal of an {@link EObject} from the containment hierarchy.
   * 
   * <p>This is the case when:
   * <ol>
   *  <li>
   *    change replaces an EObject in a single-valued containment reference, in which case
   *    that EObject is considered to be removed (ReplaceSingleValuedEReference),
   *  </li>
   *  <li>change removes an EObject from a containment reference (RemoveEReference), or</li>
   *  <li>change removes a root object (RemoveRootEObject)</li>
   * </ol>
   *
   * @param change - {@link EChange}
   * @return a boolean value.
   */
  public static boolean isContainmentRemoval(EChange<EObject> change) {
    if (change instanceof ReplaceSingleValuedEReference<?> replaceReference) {
      return replaceReference.getAffectedFeature().isContainment() 
          && (replaceReference.getOldValue() != null 
          && replaceReference.getOldValue() != replaceReference.getNewValue());
    }
    if (change instanceof RemoveEReference<?> removeReference) {
      return removeReference.getAffectedFeature().isContainment();
    }
    return change instanceof RemoveRootEObject;
  }

  /**
   * Tests if change represents the insertion of an {@link EObject} into the containment hierarchy.
   * 
   * <p>This is the case when:
   * <ol>
   *  <li>
   *    change adds an EObject into a containment reference (AdditiveReferenceEChange), or:
   *  </li>
   *  <li>change inserts or removes a root object (InsertRootEObject/RemoveRootEObject).</li>
   * </ol>
   *
   * @param change - {@link EChange}
   * @return a boolean value.
   */
  public static boolean isContainmentInsertion(EChange<EObject> change) {
    if (change instanceof AdditiveReferenceEChange<?> additiveReferenceChange) {
      return additiveReferenceChange.getAffectedFeature().isContainment()
          && additiveReferenceChange.getNewValue() != null;  
    }
    return change instanceof RemoveRootEObject || change instanceof InsertRootEObject;
  }
}
