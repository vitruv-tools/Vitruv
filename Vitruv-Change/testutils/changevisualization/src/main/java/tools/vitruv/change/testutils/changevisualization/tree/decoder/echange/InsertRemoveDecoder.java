package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

import java.util.Map;
import tools.vitruv.change.atomic.EChange;

/**
 * Decoder for eChanges with a insert or remove semantic.
 *
 * @author Andreas Loeffler
 */
public class InsertRemoveDecoder extends AbstractChangeDecoder {

  /*
   * The maximum length of value-Strings to display.
   */
  private static final int MAX_LENGTH = 60;

  /** The structural feature name holding the value to display. */
  private final String valueString;

  /**
   * Creates a decoder for the given echange class and extracts the inserted/removed value from the
   * feature named valueString.
   *
   * @param eClassName The eClass name
   * @param valueString The structural features name holding the inserted/removed value
   */
  public InsertRemoveDecoder(String eClassName, String valueString) {
    super(
        eClassName,
        new String[] {"affectedFeature", "affectedEObject", "affectedEObjectID", valueString});
    this.valueString = valueString;
  }

  @Override
  protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {

    // affectedFeature must exist
    if (!structuralFeatures2values.containsKey("affectedFeature")) {
      return null;
    }

    // one of affectedEObject or affectedEObjectID must exist
    if (!structuralFeatures2values.containsKey("affectedEObjectID")
        && !structuralFeatures2values.containsKey("affectedEObject")) {
      return null;
    }

    // Extract newValue
    String value =
        structuralFeatures2values.containsKey(valueString)
            ? structuralFeatures2values.get(valueString).toString()
            : "-";

    // value is sometimes really long, truncate it
    if (value != null && value.length() > MAX_LENGTH) {
      value = value.substring(0, MAX_LENGTH) + "...";
    }

    // Extract the entityName of the eObject
    String displayName = extractAffectedEObjectName(structuralFeatures2values);
    if (displayName == null) {
      // If name is not existent, i use the id
      displayName = NULL_STRING;
    }

    // extract the name of the eAttribute
    String eAttributeName = extractAffectedFeatureName(structuralFeatures2values);
    if (eAttributeName == null) {
      eAttributeName = NULL_STRING;
    }

    // Create the result string
    return "\"" + displayName + "\" / \"" + eAttributeName + "\" : \"" + value + "\"";
  }
}
