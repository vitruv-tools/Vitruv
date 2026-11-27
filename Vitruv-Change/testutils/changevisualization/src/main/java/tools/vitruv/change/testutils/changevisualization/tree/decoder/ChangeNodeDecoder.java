package tools.vitruv.change.testutils.changevisualization.tree.decoder;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.testutils.changevisualization.tree.ChangeNode;
import tools.vitruv.change.testutils.changevisualization.tree.ChangeNode.EChangeClass;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.ChangeDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.CreateEObjectDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.DeleteEObjectDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.InsertEAttributeValueDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.InsertEReferenceDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.InsertRootEObjectDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.RemoveEAttributeValueDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.RemoveEReferenceDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.RemoveRootEObjectDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.ReplaceSingleValuedEAttributeDecoder;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.echange.ReplaceSingleValuedEReferenceDecoder;
import tools.vitruv.change.testutils.changevisualization.utils.ModelHelper;

/**
 * Helper class to generate {@link ChangeNode}s from eChanges. Central place to
 * register new {@link
 * ChangeDecoder}.
 *
 * @author Andreas Loeffler
 */
public class ChangeNodeDecoder {

  /**
   * Decoders which extract the information to display from given Object of
   * specific eChanges.
   */
  private static Map<String, ChangeDecoder> decoders = new HashMap<>();

  // register additional decoders, default instances
  static {
    registerChangeDecoder(new ReplaceSingleValuedEAttributeDecoder());
    registerChangeDecoder(new ReplaceSingleValuedEReferenceDecoder());
    registerChangeDecoder(new CreateEObjectDecoder());
    registerChangeDecoder(new InsertRootEObjectDecoder());
    registerChangeDecoder(new InsertEReferenceDecoder());
    registerChangeDecoder(new InsertEAttributeValueDecoder());
    registerChangeDecoder(new RemoveEAttributeValueDecoder());
    registerChangeDecoder(new DeleteEObjectDecoder());
    registerChangeDecoder(new RemoveRootEObjectDecoder());
    registerChangeDecoder(new RemoveEReferenceDecoder());
  }
  final static String REMOVE_ROOT_OBJECT = "RemoveRootEObject";
  final static String INSERT_ROOT_OBJECT = "InsertRootEObject";
  final static String CREATE_EOBJECT = "CreateEObject";
  final static String DELETE_EOBJECT = "DeleteEObject";
  final static String INSERT_EREFERENCE = "InsertEReference";
  final static String REPLACE_SINGLE_VALUED_EREFERENCE = "ReplaceSingleValuedEReference";
  final static String REMOVE_EREFERENCE = "RemoveEReference";
  final static String INSERT_EATTRIBUTE_VALUE = "InsertEAttributeValue";
  final static String REPLACE_SINGLE_VALUED_EATTRIBUTE = "ReplaceSingleValuedEAttribute";
  final static String REMOVE_EATTRIBUTE_VALUE = "RemoveEAttributeValue";

  /**
   * Can be called to register new decoders for given EChange classes.
   *
   * @param dec The decoder used to decode objects of the class
   */
  public static void registerChangeDecoder(ChangeDecoder dec) {
    decoders.put(dec.getDecodedEClassName(), dec);
  }

  /**
   * Constructs a |@link ChangeNode} for a given {@link EChange}.
   *
   * @param eChange The change to visualize, not null
   * @return The changeNode representing the change
   */
  public static ChangeNode generateChangeNode(EChange<?> eChange) {

    String eClassName = eChange.eClass().getName();

    String individualText = null;
    if (decoders.containsKey(eClassName)) {
      // Use the special decoder to derive the full text
      individualText = decoders.get(eClassName).decode(eChange);
    }

    String[][] structuralFeatureLabelValues = ModelHelper.extractStructuralFeatureArray(eChange);

    EChangeClass changeClass = determineEChangeClass(eChange);

    String affectedClass = getAffectedClass(eChange);

    // extract eObjectID
    String eObjectID = getAffectedEObjectID(eChange);

    return new ChangeNode(
        individualText,
        structuralFeatureLabelValues,
        changeClass,
        eClassName,
        affectedClass,
        eObjectID);

  }

  /**
   * Determine the type of eChange for a given instance.
   *
   * @param eChange The eChange
   * @return The type of eChange
   */
  private static EChangeClass determineEChangeClass(EChange<?> eChange) {
    // No ReferenceEChange or AttributeEChange class found to check for instanceof
    // so class recognition is done by name so far
    switch (eChange.eClass().getName()) {
      case CREATE_EOBJECT, DELETE_EOBJECT -> {
        return EChangeClass.EXISTENCE_ECHANGE;
      }
      case INSERT_ROOT_OBJECT, REMOVE_ROOT_OBJECT -> {
        return EChangeClass.ROOT_ECHANGE;
      }
      case INSERT_EREFERENCE, REPLACE_SINGLE_VALUED_EREFERENCE, REMOVE_EREFERENCE -> {
        return EChangeClass.REFERENCE_ECHANGE;
      }
      case INSERT_EATTRIBUTE_VALUE, REPLACE_SINGLE_VALUED_EATTRIBUTE, REMOVE_EATTRIBUTE_VALUE -> {
        return EChangeClass.ATTRIBUTE_ECHANGE;
      }
      default -> {
        return null;
      }

    }
  }

  /**
   * Returns the affectedEObjectID of a given echange.
   *
   * @param eChange The eChange
   * @return The affectedEObjects id
   */
  private static String getAffectedEObjectID(EChange<?> eChange) {
    String featureName = null;
    switch (eChange.eClass().getName()) {
      case INSERT_ROOT_OBJECT:
        // InsertRootEObject eChanges don't have affectedEObjectIDs, they use newValueID
        featureName = "newValueID";
        break;
      case REMOVE_ROOT_OBJECT:
        // RemoveRootEObject eChanges don't have affectedEObjectIDs, they use oldValueID
        featureName = "oldValueID";
        break;
      default:
        featureName = "affectedEObjectID";
        break;
    }
    return (String) ModelHelper.getStructuralFeatureValue(eChange, featureName);
  }

  /**
   * Returns the classname of the affectedEObject of the given eChange. For
   * EObjects, this is the
   * eClass-name, for all other objects the java class name.
   *
   * @param eChange The eChange
   * @return The classname
   */
  private static String getAffectedClass(EChange<?> eChange) {
    String featureName = null;
    switch (eChange.eClass().getName()) {
      case INSERT_ROOT_OBJECT:
      case REMOVE_ROOT_OBJECT:
        // Insert- and RemoveRootEObject eChanges don't have affectedEObject, they use
        // resource
        featureName = "resource";
        break;
      default:
        featureName = "affectedEObject";
        break;
    }

    Object obj = ModelHelper.getStructuralFeatureValue(eChange, featureName);

    if (obj == null) {
      // feature not existent or the feature's value is null
      return null;
    } else if (obj instanceof EObject) {
      // for eObjects, use their eclasses name
      return ((EObject) obj).eClass().getName();
    } else {
      // for all others, use the simple java class name
      return obj.getClass().getSimpleName();
    }
  }

  /** Constructor not used. */
  private ChangeNodeDecoder() {
    // not used
  }
}
