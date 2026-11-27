package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.testutils.changevisualization.utils.ModelHelper;

/**
 * A basic change decoder which ensures that only change events of a given eClassName are processed
 * and that all required structural features exist. Their values might be null, but the feature
 * itself must exist. Subclasses must implement generateString() to create the actual result of the
 * decoder in case all requirements are met and should not override decode(EChange echange).
 *
 * @author Andreas Loeffler
 */
public abstract class AbstractChangeDecoder implements ChangeDecoder {

  /** The string used to display null values. */
  protected static final String NULL_STRING = "-";

  /**
   * Extracts the name of the affectedFeature.
   *
   * @param structuralFeatures2values The relevant structural features
   * @return The name of the affected eAttribute
   */
  protected static String extractAffectedFeatureName(
      Map<String, Object> structuralFeatures2values) {
    Object feature = structuralFeatures2values.get("affectedFeature");
    if (!(feature instanceof EObject)) {
      return null;
    }
    feature = ModelHelper.getStructuralFeatureValue((EObject) feature, "name");
    return String.valueOf(feature);
  }

  /**
   * Extracts the name of the affectedEObject.
   *
   * @param structuralFeatures2values The relevant structural features
   * @return The name of the affected eObject
   */
  protected static String extractAffectedEObjectName(
      Map<String, Object> structuralFeatures2values) {
    Object eObject = structuralFeatures2values.get("affectedEObject");
    if (!(eObject instanceof EObject)) {
      return null;
    }
    return extractEObjectName((EObject) eObject);
  }

  /**
   * Extracts the name of the given eObject.
   *
   * @param eObject The eObject whose name (=entityName) gets queried
   * @return The name of the eObject
   */
  protected static String extractEObjectName(EObject eObject) {
    if (eObject == null) {
      return null;
    }
    Object sf = ModelHelper.getStructuralFeatureValue(eObject, "entityName");
    return sf == null ? null : sf.toString();
  }

  /** the required eClass name. */
  private final String eClassName;

  /** A List of all required structural features. */
  private final List<String> requiredFeatures;

  /**
   * Creates a ChangeDecoder that ensures only change events of the given eClassName are processed
   * and that all required structural features exist.
   *
   * @param eClassName The required eClassName
   * @param requiredFeatures The required structural features
   */
  protected AbstractChangeDecoder(String eClassName, String[] requiredFeatures) {
    this.eClassName = eClassName;
    this.requiredFeatures = java.util.Arrays.asList(requiredFeatures);
  }

  @Override
  public String getDecodedEClassName() {
    return eClassName;
  }

  @Override
  public String decode(EChange echange) {
    // If null as echange gets here or an echange with no eclass, return a fallback-error. this
    // should never happen.
    // I prefer not throwing RuntimeExceptions or any other candidates that might exit the java vm
    // "just" because of this
    if (echange == null || echange.eClass() == null) {
      return "null value given";
    }

    // If the wrong eChanges get here, return null
    if (!eClassName.equals(echange.eClass().getName())) {
      return null;
    }

    // extract all structural features required
    Map<String, Object> structuralFeatures2values = extractRequiredStructuralFeatures(echange);

    // All found null values have been added by the called method under the given key, remove them.
    // Further details can be found in extractRequiredStructuralFeatures
    int nullValues = (Integer) structuralFeatures2values.remove("NULL_VALUES_FOUND");

    // If not all requiredFeatures where found, return null
    if (structuralFeatures2values.size() + nullValues != requiredFeatures.size()) {
      return null;
    }

    // let the subclass derive the string from the required features.
    // values might be null, then they are not in the hashtable, but at least the feature existed
    return generateString(echange, structuralFeatures2values);
  }

  /**
   * Extracts all required structural features from a given eChange.
   *
   * @param echange The eChange
   * @return Map of structuralFeature names ==> values
   */
  private Map<String, Object> extractRequiredStructuralFeatures(EChange echange) {
    // Generate the result hashtable
    Map<String, Object> structuralFeatures2values = new HashMap<>();

    // It is necessary to count null values, since they will not occur in the hashtable. Without
    // this information
    // it is not possible to decide whether all required structural features existed
    int nullValues = 0;

    // Get all structural features existent
    EList<EStructuralFeature> features = echange.eClass().getEAllStructuralFeatures();

    // Walk them and process the required ones
    for (EStructuralFeature feature : features) {
      if (feature == null) {
        continue;
      }
      if (requiredFeatures.contains(feature.getName())) {
        Object value = echange.eGet(feature);
        if (value == null) {
          nullValues++;
        } else {
          structuralFeatures2values.put(feature.getName(), value);
        }
      }
    }

    // the number of found null values is temporarily stored in the hashtable
    // and gets removed after this method returns
    structuralFeatures2values.put("NULL_VALUES_FOUND", nullValues);

    return structuralFeatures2values;
  }

  /**
   * ChangeDecoder subclassing AbstractChangeDecoder should only implement this method and not
   * override decode(Echange echange) This way the correct eClass is assured as well as all required
   * structural features exist. If any requirement is not met.
   *
   * @param eChange The eChange object decoded
   * @param structuralFeatures2values The extracted values of all required structural features
   * @return The string used for display in a treenode of a jtree, null in case of any error
   */
  protected abstract String generateString(
      EChange eChange, Map<String, Object> structuralFeatures2values);
}
