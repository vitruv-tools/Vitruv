package tools.vitruv.change.testutils.changevisualization.tree.decoder;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet.ChangeType;
import tools.vitruv.change.testutils.changevisualization.tree.TreeChangeDataSet;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.feature.MultipleFeatureProcessor;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.feature.OldValueNewValueProcessor;

/**
 * Helper class to generate {@link TreeChangeDataSet}s from propagation results and register {@link
 * MultipleFeatureProcessor}s.
 *
 * @author Andreas Loeffler
 */
public class TreeChangeDataSetDecoder {

  /** The string used to name propagated changes in the list. */
  private static final String PROPAGATED_CHANGE_STRING = "Propagated Change";

  /** The string used to name original changes in the list. */
  private static final String ORIGINAL_CHANGE_STRING = "Original Change";

  /** The string used to name consequential changes in the list. */
  private static final String CONSEQUENTIAL_CHANGE_STRING = "Consequential Change";

  /**
   * Processors which apply multiple structural feature enhancements to structural feature leaf
   * nodes.
   */
  private static List<MultipleFeatureProcessor> multipleFeatureProcessors =
      new Vector<MultipleFeatureProcessor>();

  // register additional multiple feature processors
  static {
    // processor that combines oldValue / newValue pairs
    registerMultipleFeatureProcessor(new OldValueNewValueProcessor());
  }

  /**
   * Can be called to register new processors implementing multiple structural feature enhancements.
   *
   * @param proc The processor for multi feature enhancements
   */
  public static void registerMultipleFeatureProcessor(MultipleFeatureProcessor proc) {
    multipleFeatureProcessors.add(proc);
  }

  /** The generated changeDataSet. */
  private final TreeChangeDataSet changeDataSet;

  /** The root node for the changeDataSet . */
  private final DefaultMutableTreeNode rootNode;

  /** The number of propagated changes . */
  private int nrPropagatedChanges = 0;

  /** The number of consequential changes . */
  private int nrConsequentialChanges = 0;

  /** The number of original changes. */
  private int nrOriginalChanges = 0;

  /**
   * Constructs a new TreeChangeDataSetDecoder instance from which a generated {@link ChangeDataSet}
   * can be acquired.
   *
   * @param id The id of the changeDataSet to create
   * @param changes The changes to present, must not be <code>null</code>
   */
  public TreeChangeDataSetDecoder(String id, Iterable<PropagatedChange> changes) {
    rootNode = new DefaultMutableTreeNode(id);
    for (PropagatedChange propChange : changes) {
      DefaultMutableTreeNode propChangeNode = encodeTree(propChange);
      rootNode.add(propChangeNode);
    }
    changeDataSet = new TreeChangeDataSet(id, rootNode);
    changeDataSet.setNrPropagatedChanges(getNrPropagatedChanges());
    changeDataSet.setNrOriginalChanges(getNrOriginalChanges());
    changeDataSet.setNrConsequentialChanges(getNrConsequentialChanges());
  }

  /**
   * Return the generated ChangeDataSet.
   *
   * @return The generated ChangeDataSet
   */
  public ChangeDataSet getChangeDataSet() {
    return changeDataSet;
  }

  /**
   * Sets the number of propagated changes.
   *
   * @param nrPropagatedChanges The number of propagated changes
   */
  protected void setNrPropagatedChanges(int nrPropagatedChanges) {
    this.nrPropagatedChanges = nrPropagatedChanges;
  }

  /**
   * Sets the number of original changes.
   *
   * @param nrOriginalChanges The number of original changes
   */
  protected void setNrOriginalChanges(int nrOriginalChanges) {
    this.nrOriginalChanges = nrOriginalChanges;
  }

  /**
   * Sets the number of consequential changes.
   *
   * @param nrConsequentialChanges The number of consequential changes
   */
  protected void setNrConsequentialChanges(int nrConsequentialChanges) {
    this.nrConsequentialChanges = nrConsequentialChanges;
  }

  /**
   * Gets the number of propagated changes.
   *
   * @return The number of propagated changes
   */
  public int getNrPropagatedChanges() {
    return nrPropagatedChanges;
  }

  /**
   * Gets the number of original changes.
   *
   * @return The number of original changes
   */
  public int getNrOriginalChanges() {
    return nrOriginalChanges;
  }

  /**
   * Gets the number of consequential changes.
   *
   * @return The number of consequential changes
   */
  public int getNrConsequentialChanges() {
    return nrConsequentialChanges;
  }

  /**
   * Creates the node for the given propagated change and appends it to the root node. Calls methods
   * that create the original/consequential change child nodes for the propagted change.
   *
   * @param propChange The propagated change
   * @return The node representing the given propagated change
   */
  private DefaultMutableTreeNode encodeTree(PropagatedChange propChange) {
    // Count the propagated changes in the list, prior to creating the propChangeNode so it gets
    // counted from 1 upwards.
    setNrPropagatedChanges(getNrPropagatedChanges() + 1);

    // Create a propagated change childnode
    String sourceMetamodelsString =
        propChange.getOriginalChange().getAffectedEObjectsMetamodelDescriptors().stream()
            .flatMap(descriptor -> descriptor.getNsUris().stream())
            .collect(Collectors.joining(", "));
    String targetMetamodelsString =
        propChange.getConsequentialChanges().getAffectedEObjectsMetamodelDescriptors().stream()
            .flatMap(descriptor -> descriptor.getNsUris().stream())
            .collect(Collectors.joining(", "));
    DefaultMutableTreeNode propChangeNode =
        new DefaultMutableTreeNode(
            PROPAGATED_CHANGE_STRING
                + " "
                + getNrPropagatedChanges()
                + "(from: "
                + sourceMetamodelsString
                + "; to: "
                + targetMetamodelsString
                + ")");

    // Process original changes
    DefaultMutableTreeNode origNode =
        encodeTree(ChangeType.ORIGINAL_CHANGE, propChange.getOriginalChange());
    setNrOriginalChanges(
        getNrOriginalChanges() + origNode.getChildCount()); // Count the original changes
    propChangeNode.add(origNode);

    // Process consequential changes
    DefaultMutableTreeNode consequentialNode =
        encodeTree(ChangeType.CONSEQUENTIAL_CHANGE, propChange.getConsequentialChanges());
    setNrConsequentialChanges(
        getNrConsequentialChanges()
            + consequentialNode.getChildCount()); // Count the consequential changes
    propChangeNode.add(consequentialNode);

    return propChangeNode;
  }

  /**
   * Creates the node that represent original changes or consequential changes. It calls a method
   * that creates the child nodes representing eChanges.
   *
   * @param changeType The type of change
   * @param change The vitruvius change that gets encoded
   * @return A TreeNode representing the given arguments
   */
  private DefaultMutableTreeNode encodeTree(
      ChangeType changeType, VitruviusChange<EObject> change) {
    // Create the change-node
    DefaultMutableTreeNode node = new DefaultMutableTreeNode();

    // Walk all eChanges and create their sub-trees
    for (EChange eChange : change.getEChanges()) {
      encodeTree(eChange, node);
    }

    // Depending on the type of change and its echange-count, set the correct change node name
    switch (changeType) {
      case ORIGINAL_CHANGE:
        node.setUserObject(ORIGINAL_CHANGE_STRING + (node.getChildCount() > 1 ? "s" : ""));
        break;
      case CONSEQUENTIAL_CHANGE:
        node.setUserObject(CONSEQUENTIAL_CHANGE_STRING + (node.getChildCount() > 1 ? "s" : ""));
        break;
      default:
        // this should never happens
        throw new RuntimeException("Unknown change type : " + changeType);
    }

    return node;
  }

  /**
   * Creates a node representing an eChange and the nodes for the eChanges structural features.
   * Multiple feature processors are applied here before the new eChange node is added to the
   * parentNode.
   *
   * @param eChange The eChange
   * @param parentNode The parent node to add the new eChange node to
   */
  private void encodeTree(EChange eChange, DefaultMutableTreeNode parentNode) {
    // Create the eChange node
    DefaultMutableTreeNode node = createEChangeNode(eChange);

    Map<String, Integer> featureName2index = new Hashtable<String, Integer>();
    List<Object> featureValues = new Vector<Object>();

    // Encode Structural Feature infos
    encodeStructuralFeatures(eChange, node, featureName2index, featureValues);

    // Apply multiple structural feature processors
    applyMultipleFeatureProcessors(eChange, node, featureName2index, featureValues);
    // Attention: Any code that comes after applyMultipleFeatureProcessor should be aware that there
    // may now be nodes
    // that dont exist as basic structural features but are the result of multipleFeatureProcessors

    parentNode.add(node);
  }

  /**
   * Applies the registered multiple structural feature decoders until a stable state is reached.
   * MultipleFeatureProcessors can potentially rely on the results of other processors, which
   * therefore have to run first. This is accomplished by rerunning the process until no changes
   * occurred (=stable state reached).
   *
   * @param eChange The eChange whose structural features are processed
   * @param parentNode The parent node
   * @param featureName2index FeatureName ==> Index
   * @param featureValues Feature Values (The value of each feature is found at featureName2index
   *     index)
   */
  private void applyMultipleFeatureProcessors(
      EChange eChange,
      DefaultMutableTreeNode parentNode,
      Map<String, Integer> featureName2index,
      List<Object> featureValues) {

    // Needed for multiple feature processors that rely on the result of other processors to work
    // correctly
    // if anything happened, again is set to true and the whole process is started over with the
    // resulting features and nodes
    boolean again = true;

    while (again) {
      again = false; // is set to true if any processor accepted the features and changed something
      // if no processor accepted anything, we are finished and while is exited because again is
      // still false

      for (MultipleFeatureProcessor proc : multipleFeatureProcessors) {
        if (proc.accepts(featureName2index.keySet())) {
          again = true; // rerun while

          // The process method updates all relevant information. For example removes the replace
          // child nodes
          // or similar stuff. This can lead to "race conditions" if different multi feature
          // decoders use at least
          // partially overlapping features which are then removed by the one first called. This
          // cannot be prevented
          // and is regarded as a design fault for the registered multipleFeatureDecoders.
          // If this should not be the case sometime in the future, one has to implement
          // a more advanced sequence algorithm here
          proc.process(eChange, parentNode, featureName2index, featureValues);
        }
      }
    }
  }

  /**
   * Creates nodes for all structural features a given eChange has. featureName2index and
   * featureValues is build up during creation to reflect the final state.
   *
   * @param eChange The eChange
   * @param parentNode The parentNode to add the child nodes to
   * @param featureName2index FeatureName ==> Index of the features value in featureValues
   * @param featureValues Feature Values
   */
  private void encodeStructuralFeatures(
      EChange eChange,
      DefaultMutableTreeNode parentNode,
      Map<String, Integer> featureName2index,
      List<Object> featureValues) {
    int index = 0;
    for (EStructuralFeature feature : eChange.eClass().getEAllStructuralFeatures()) {
      if (feature == null) {
        continue;
      }
      Object obj = eChange.eGet(feature);
      if (obj == null) {
        // Assures no null values exist in the FeatureNode-Constructor
        obj = "-";
      }
      try {
        DefaultMutableTreeNode featureNode =
            new DefaultMutableTreeNode(FeatureNodeDecoder.generateFeatureNode(feature, obj));
        parentNode.add(featureNode);
        featureName2index.put(feature.getName(), index);
        featureValues.add(obj);
        index++;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Create a node for a given eChange.
   *
   * @param eChange The eChange
   * @return The created TreeNode
   */
  private DefaultMutableTreeNode createEChangeNode(EChange eChange) {
    DefaultMutableTreeNode node =
        new DefaultMutableTreeNode(ChangeNodeDecoder.generateChangeNode(eChange));
    return node;
  }
}
