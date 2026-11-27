package tools.vitruv.change.testutils.changevisualization.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Utility class that does some useful general tasks regarding eCore Models.
 *
 * @author Andreas Loeffler
 */
public final class ModelHelper {

  /** No instances are created. */
  private ModelHelper() {}

  /**
   * Extracts information from a given eobject and creates an array of label/values pairs from it.
   *
   * <p>The information is the runtime class, the eClass and are structural features
   *
   * @param eObj The eObject to extract all information from
   * @return String array of label/value pairs
   */
  public static String[][] extractStructuralFeatureArray(EObject eObj) {
    List<String> labels = new ArrayList<>();
    List<String> values = new ArrayList<>();
    // add general information
    appendEClassInformation(labels, values, eObj);

    // Add runtime class information
    appendRuntimeClassInformation(labels, values, eObj);

    // add strucutral feature information
    appendStructuralFeatureInformation(labels, values, eObj);

    String[][] array = new String[labels.size()][2];
    for (int n = 0; n < array.length; n++) {
      array[n][0] = labels.get(n);
      array[n][1] = values.get(n);
    }
    return array;
  }

  /**
   * Creates the lines for all structural features.
   *
   * @param center The center panel
   * @param left The left panel
   * @param eObj The eObject to extract all structural features from
   */
  private static void appendStructuralFeatureInformation(
      List<String> labels, List<String> values, EObject eObj) {
    for (EStructuralFeature feature : eObj.eClass().getEAllStructuralFeatures()) {
      if (feature == null) {
        continue;
      }
      Object fObj = eObj.eGet(feature);
      if (fObj == null) {
        fObj = "";
      }
      labels.add(feature.getName());
      values.add(String.valueOf(fObj));
    }
  }

  /**
   * Creates the line with the runtime class information.
   *
   * @param center The center panel
   * @param left The left panel
   * @param eObj The eObject to extract the runtime class information from
   */
  private static void appendRuntimeClassInformation(
      List<String> labels, List<String> values, EObject eObj) {
    labels.add("runtime class");
    values.add(eObj.getClass().getName());
  }

  /**
   * Creates the line with the eClass information.
   *
   * @param labels The center panel
   * @param values The left panel
   * @param eObj The eObject to extract the eClass information from
   */
  private static void appendEClassInformation(
      List<String> labels, List<String> values, EObject eObj) {
    labels.add("eClass");
    values.add(eObj.eClass().getName());
  }

  /**
   * Returns if the given structural feature of the given eObject exists.
   *
   * @param eObject The eObject, not null
   * @param sfName The name of the structural feature, not null
   * @return true if structural feature exists, false otherwise
   */
  public static boolean hasStructuralFeature(EObject eObject, String sfName) {
    if (eObject == null || sfName == null) {
      return false;
    }
    EList<EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
    for (EStructuralFeature feature : features) {
      if (feature == null) {
        continue;
      }
      if (sfName.equals(feature.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the value of the given structural feature of the given eObject. Feature not existent
   * returns null, also the feature value==null will return null. If it is necessary to distinguish
   * between the two, use hasStructuralFeature.
   *
   * @param eObject The eObject, nut null
   * @param sfName The name of the structural feature, not null
   * @return The associated value, might be null
   */
  public static Object getStructuralFeatureValue(EObject eObject, String sfName) {
    if (eObject == null || sfName == null) {
      return null;
    }
    EList<EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
    for (EStructuralFeature feature : features) {
      if (feature == null) {
        continue;
      }
      if (sfName.equals(feature.getName())) {
        return eObject.eGet(feature);
      }
    }
    return null;
  }
}
