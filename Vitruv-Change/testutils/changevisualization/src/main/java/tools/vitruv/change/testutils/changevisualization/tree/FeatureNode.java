package tools.vitruv.change.testutils.changevisualization.tree;

import java.awt.Component;
import java.io.Serializable;

/**
 * Collects all information used to display a StructuralFeature as a node in a JTree.
 *
 * @author Andreas Loeffler
 */
public class FeatureNode implements Serializable {

  /** A serial version id for java object serialization. */
  private static final long serialVersionUID = 2466695811442401829L;

  /** The structural feature name. */
  private final String featureName;

  /** Value is the simple and short string used to display as the nodes text. */
  private final String valueString;

  /** If details in form of a (longer) String exist, they are stored here. */
  private final String details;

  /** If details in the form of a String[][] exist, they are stored here. */
  private final String[][] detailsArray;

  /** If details in the form of a Component exist, they are stored here. */
  private final Component detailsUI;

  /**
   * Constructs a feature node.
   *
   * @param featureName The name of the structural feature this node is derived from
   * @param valueString A string of the structural feature's value
   * @param details A detail-string, may be null
   * @param detailsArray A details array, may be null
   * @param detailsUI A detailsUI, may be null
   */
  public FeatureNode(
      String featureName,
      String valueString,
      String details,
      String[][] detailsArray,
      Component detailsUI) {
    this.featureName = featureName;
    this.valueString = valueString;
    this.details = details;
    this.detailsArray = detailsArray;
    this.detailsUI = detailsUI;
  }

  /**
   * Gets the structural feature's name.
   *
   * @return The feature name
   */
  public String getFeatureName() {
    return featureName;
  }

  /**
   * The simple String suitable as the text of a tree node. It is usually the toString() result of
   * the feature's value.
   *
   * @return The text for the tree node, not null
   */
  public String getValueString() {
    return valueString;
  }

  /**
   * The details String, if any.
   *
   * @return The details String, may be null
   */
  public String getDetails() {
    return details;
  }

  @Override
  public String toString() {
    // toString() is called by the tree node renderer. This text is displayed in the tree.
    return featureName + " : " + valueString;
  }

  /**
   * The detailsUI component, if any.
   *
   * @return The detailsUI component, may be null
   */
  public Component getDetailsUI() {
    return detailsUI;
  }

  /**
   * Returns an array of size [x][2] holding pairs of labels/values used to visualize details for
   * this feature.
   *
   * @return The details array, may be null
   */
  public String[][] getDetailsArray() {
    return detailsArray;
  }
}
