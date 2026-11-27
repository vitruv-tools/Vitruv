package tools.vitruv.change.testutils.changevisualization.tree.decoder.feature;

import java.awt.Component;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.testutils.changevisualization.utils.ModelHelper;

/**
 * Feature decoder suitable for EObjects. It creates a detailedArray as detailed visualization.
 *
 * @author Andreas Loeffler
 */
public class EObjectFeatureDecoder implements FeatureDecoder {

  /** The first structural feature name to look for when extracting the eObject name. */
  private static final String FIRST_NAME_SF = "entityName";

  /**
   * The second structural feature name to look for when extracting the eObject name. This one is
   * only considered if the first on does not exist.
   */
  private static final String SECOND_NAME_SF = "name";

  @Override
  public Class<?> getDecodedClass() {
    return EObject.class;
  }

  @Override
  public String decodeSimple(Object obj) {
    org.eclipse.emf.ecore.EObject eObj = (org.eclipse.emf.ecore.EObject) obj;
    String name = getName(eObj);
    if (name == null) {
      return eObj.eClass().getName();
    } else {
      return eObj.eClass().getName() + " [" + name + "]";
    }
  }

  /**
   * Extracts the name of the eObject, if existent. It looks for FIRST_NAME_SF and SECOND_NAME_SF to
   * find it.
   *
   * @param eObj The eObject to get the name of
   * @return The name, if existent, or null
   */
  private String getName(EObject eObj) {

    Object fObj = ModelHelper.getStructuralFeatureValue(eObj, FIRST_NAME_SF);
    if (fObj != null) {
      return String.valueOf(fObj);
    }

    fObj = ModelHelper.getStructuralFeatureValue(eObj, SECOND_NAME_SF);
    if (fObj != null) {
      return String.valueOf(fObj);
    }

    // Nothing found
    return null;
  }

  @Override
  public String decodeDetailed(Object obj) {
    return null;
  }

  @Override
  public Component decodeDetailedUI(Object obj) {
    return null;
  }

  @Override
  public String[][] decodeDetailedArray(Object obf) {
    return ModelHelper.extractStructuralFeatureArray((EObject) obf);
  }
}
