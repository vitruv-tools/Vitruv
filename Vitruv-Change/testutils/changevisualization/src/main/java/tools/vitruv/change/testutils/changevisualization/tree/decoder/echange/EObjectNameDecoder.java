package tools.vitruv.change.testutils.changevisualization.tree.decoder.echange;

import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;

/**
 * Decoder for eChanges that have a name feature to display.
 *
 * @author Andreas Loeffler
 */
public class EObjectNameDecoder extends AbstractChangeDecoder {

  /** The name of the structural feature holding the name value. */
  private final String nameFeature;

  /**
   * Creates a {@link ChangeDecoder} that ensures only change events of the given eClassName are
   * processed and that the structuralFeature from which to extract the name exists. The feature
   * must have a value that is an instance of {@link EObject} for this implementation to work.
   *
   * @param eClassName The class name for the eChanges this decoder can process
   * @param nameFeature The structural feature that hold the eObject to extract the name from
   */
  protected EObjectNameDecoder(String eClassName, String nameFeature) {
    super(eClassName, new String[] {nameFeature});
    this.nameFeature = nameFeature;
  }

  @Override
  protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
    // only the nameFeature must exist
    if (!structuralFeatures2values.containsKey(nameFeature)) {
      return null;
    }

    // Extract the eObject
    Object sfValue = structuralFeatures2values.get(nameFeature);
    if (!(sfValue instanceof EObject)) {
      return null;
    }

    // Extract the name of the eObject
    String nameString = extractEObjectName((EObject) sfValue);
    if (nameString == null) {
      nameString = NULL_STRING;
    }

    // Create the result string
    return "\"" + nameString + "\"";
  }
}
