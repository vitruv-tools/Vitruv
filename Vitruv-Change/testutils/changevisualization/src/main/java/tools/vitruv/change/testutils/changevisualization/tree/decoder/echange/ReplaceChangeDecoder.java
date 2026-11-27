package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

import java.util.Map;
import tools.vitruv.change.atomic.EChange;

/**
 * Decoder for changes with a replace semantic.
 *
 * @author Andreas Loeffler
 */
public class ReplaceChangeDecoder extends AbstractChangeDecoder {

  /*
   * The maximum length of value-Strings to display.
   */
  protected static final int MAX_LENGTH = 40;
  private static final String OLD_VALUE = "oldValue";
  private static final String NEW_VALUE = "newValue";
  private static final String AFFECTED_FEATURE = "affectedFeature";
  private static final String AFFECTED_EOBJECT = "affectedEObject";

  /**
   * F
   * Constructs a decoder for a given eChange class. This class has to use
   * oldValue and newValue as
   * the structural features holding the respective values.
   *
   * @param eClassName The eclassName
   */
  public ReplaceChangeDecoder(String eClassName) {
    super(eClassName, new String[] { OLD_VALUE, NEW_VALUE, AFFECTED_FEATURE, AFFECTED_EOBJECT });
  }

  @Override
  protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
    // At least oldValue or new Value must exist
    if (!structuralFeatures2values.containsKey(OLD_VALUE)
        && !structuralFeatures2values.containsKey(NEW_VALUE)) {
      return null;
    }

    // affectedFeature and affectedEObject both must exist
    if (!structuralFeatures2values.containsKey(AFFECTED_FEATURE)
        || !structuralFeatures2values.containsKey(AFFECTED_EOBJECT)) {
      return null;
    }

    // Extract old/newValue
    String oldValue = structuralFeatures2values.containsKey(OLD_VALUE)
        ? structuralFeatures2values.get(OLD_VALUE).toString()
        : "-";
    String newValue = structuralFeatures2values.containsKey(NEW_VALUE)
        ? structuralFeatures2values.get(NEW_VALUE).toString()
        : "-";

    if (oldValue.length() > MAX_LENGTH) {
      oldValue = oldValue.substring(0, MAX_LENGTH) + "...";
    }
    if (newValue.length() > MAX_LENGTH) {
      newValue = newValue.substring(0, MAX_LENGTH) + "...";
    }

    // Extract the entityName of the eObject
    String eObjectName = extractAffectedEObjectName(structuralFeatures2values);
    if (eObjectName == null) {
      eObjectName = NULL_STRING;
    }

    // extract the name of the eAttribute
    String eAttributeName = extractAffectedFeatureName(structuralFeatures2values);
    if (eAttributeName == null) {
      eAttributeName = NULL_STRING;
    }

    // Create the result string
    return "\""
        + eObjectName
        + "\" / \""
        + eAttributeName
        + "\" : \""
        + oldValue
        + "\" ==> \""
        + newValue
        + "\"";
  }
}
