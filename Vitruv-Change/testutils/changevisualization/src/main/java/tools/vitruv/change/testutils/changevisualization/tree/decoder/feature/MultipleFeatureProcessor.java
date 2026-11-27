package tools.vitruv.change.testutils.changevisualization.tree.decoder.feature;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.tree.DefaultMutableTreeNode;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.testutils.changevisualization.tree.FeatureNode;

/**
 * MultipleFeatureProcessors are used to process more than one structural
 * feature at once.
 *
 * <p>
 * This is the basic class for all MultipleFeatureProcessors. It implements some
 * generally useful
 * methods for all implementations. The processing itself is done be the
 * subclasses.
 *
 * @author Andreas Loeffler
 */
public abstract class MultipleFeatureProcessor {

  /**
   * The structural feature (names) that must be present for this processor to
   * work.
   */
  private final String[] requiredStructuralFeatures;

  /**
   * Constructs a MultipleFeatureProcessor that implement a general accepts()
   * method assuring all
   * given required structural features exist.
   *
   * @param requiredStructuralFeatures The required structural features
   */
  protected MultipleFeatureProcessor(String[] requiredStructuralFeatures) {
    this.requiredStructuralFeatures = requiredStructuralFeatures;
  }

  /**
   * Assures that all required structural features exist. This way the subclasses
   * implementation can
   * blindly rely on their existence.
   *
   * @param structuralFeatureNames The existent structural feature names
   * @return True if all required SFs exist
   */
  public boolean accepts(Set<String> structuralFeatureNames) {
    return Arrays.stream(requiredStructuralFeatures)
        .allMatch(structuralFeatureNames::contains);
  }

  /**
   * Processes a given eChanges structural feature nodes. Every change has to
   * result in a consistent
   * state of all given arguments.
   *
   * @param eChange           The eChange whose sf are processed
   * @param node              The parent node of the eChange
   * @param featureName2index FeatureName ==> Index of feature value in
   *                          featureValues
   * @param featureValues     Feature Values (The value of each feature is found
   *                          at featureName2index
   *                          index)
   */
  public abstract void process(
      EChange<?> eChange,
      DefaultMutableTreeNode node,
      Map<String, Integer> featureName2index,
      List<Object> featureValues);

  /**
   * Adds a node in a consistent way. This means it is added to the parent node
   * and all other
   * arguments are updated accordingly.
   *
   * @param parentNode        The parent node
   * @param newNode           The new node
   * @param featureName2index FeatureName ==> Index of feature value in
   *                          featureValues
   * @param featureValues     Feature Values (The value of each feature is found
   *                          at featureName2index
   *                          index)
   */
  protected void addNode(
      DefaultMutableTreeNode parentNode,
      DefaultMutableTreeNode newNode,
      Map<String, Integer> featureName2index,
      List<Object> featureValues) {

    int index = parentNode.getChildCount(); // has to be done prior to adding or -1 is necessary

    // Add the node
    parentNode.add(newNode);

    // Update the other arguments
    FeatureNode fn = (FeatureNode) newNode.getUserObject();
    featureName2index.put(fn.getFeatureName(), index);
    featureValues.add(fn.getValueString());
  }

  /**
   * Removes nodes addressed by their feature names in a consistent way. This
   * means they are removed
   * the parent node and all other arguments are updated accordingly.
   *
   * @param featuresToRemove  The features to remove
   * @param parentNode        The parent node
   * @param featureName2index FeatureName ==> Index of feature value in
   *                          featureValues
   * @param featureValues     Feature Values (The value of each feature is found
   *                          at featureName2index
   *                          index)
   */
  protected void removeNodes(
      List<String> featuresToRemove,
      DefaultMutableTreeNode parentNode,
      Map<String, Integer> featureName2index,
      List<Object> featureValues) {

    for (String featureToRemove : featuresToRemove) {
      // get the index to remove
      int indexToRemove = featureName2index.get(featureToRemove);

      // get all features whose indices are decreased
      List<String> featuresToDecrease = getAllFeaturesWhoseIndicesAreDecreased(indexToRemove, featureName2index);

      // remove the feature from all relevant arguments.
      removeFeature(featureToRemove, indexToRemove, featureName2index, featureValues, parentNode);

      // Now all features whose indices are after the one removed have to be decreased
      // by one
      for (String featureToDecrease : featuresToDecrease) {
        int newIndex = featureName2index.get(featureToDecrease) - 1;
        featureName2index.put(featureToDecrease, newIndex);
      }
    }
  }

  /**
   * Removes a feature addressed by its feature name in a consistent way. This
   * means it is removed.
   *
   * @param featureToRemove   The feature to remove
   * @param indexToRemove     The index to remove
   * @param featureName2index FeatureName ==> Index of feature value in
   *                          featureValues
   * @param featureValues     Feature Values (The value of each feature is found
   *                          at featureName2index
   *                          index)
   * @param parentNode        The parent node
   */
  private void removeFeature(
      final String featureToRemove,
      final int indexToRemove,
      Map<String, Integer> featureName2index,
      List<Object> featureValues,
      DefaultMutableTreeNode parentNode) {
    // remove from featureName2index
    featureName2index.remove(featureToRemove);
    // remove from featureValues
    featureValues.remove(indexToRemove);
    // and finally from parentNode
    parentNode.remove(indexToRemove);
  }

  /**
   * Lists all featureNames that point to indices coming after the one to remove.
   * They have to be
   * decreased by one after removing the indexToRemove.
   *
   * @param indexToRemove     The index to remove
   * @param featureName2index FeatureName ==> Index of feature value in
   *                          featureValues
   * @return All features whose indices have to be decreased
   */
  private List<String> getAllFeaturesWhoseIndicesAreDecreased(
      final int indexToRemove, Map<String, Integer> featureName2index) {

    return featureName2index.entrySet().stream()
        .filter(entry -> entry.getValue() > indexToRemove)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }
}
