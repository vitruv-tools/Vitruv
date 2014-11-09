package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
public class EcoreHelper {
  /**
   * returns a list of all ancestors of obj, starting with
   * the object itself
   */
  public static List<EObject> getContainerHierarchy(final EObject obj) {
    final List<EObject> result = new ArrayList<EObject>();
    EObject iterator = obj;
    while ((!Objects.equal(iterator, null))) {
      {
        result.add(iterator);
        EObject _eContainer = iterator.eContainer();
        iterator = _eContainer;
      }
    }
    return result;
  }
}
