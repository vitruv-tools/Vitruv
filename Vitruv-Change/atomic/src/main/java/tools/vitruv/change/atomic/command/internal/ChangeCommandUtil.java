package tools.vitruv.change.atomic.command.internal;

import static com.google.common.base.Preconditions.checkState;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * {@link ChangeCommandUtil} provides miscelleaneous methods to handle commands.
 */
@Utility
public class ChangeCommandUtil {
  /**
   * Private constructor for Utility class.
   */
  private ChangeCommandUtil() {}

  /**
   * Returns the editing domain of the an object. If the object has no 
   * editing domain, it returns a new {@code AdapterFactoryEditingDomain}.
   *
   * @param object the {@link EObject}
   * @return the editing domain of the object
   */
  public static EditingDomain getEditingDomain(EObject object) {
    final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(object);
    return editingDomain != null 
        ? editingDomain 
        : new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), new BasicCommandStack());
  }
    
  /**
   * Returns whether the affected object already contains the given value in the given reference.
   *
   * @param affectedEObject the {@link EObject} whose feature to check
   * @param reference the {@link EReference} of the <code>affectedEObject</code> to check
   * @param value the {@link EObject} to check whether it is already contained 
   *      in the <code>affectedEObject</code>'s reference
   * @return whether the <code>affectedEObject</code> 
   *      contains the given value in the given reference.
   */
  public static boolean alreadyContainsObject(
      EObject affectedEObject, EReference reference, EObject value) {
    checkState(affectedEObject.eClass().getEAllReferences().contains(reference),
        "Given object %s does not contain reference %s", affectedEObject, reference);
    var contents = affectedEObject.eGet(reference);
    
    List<EObject> featureContents; 
    if (reference.isMany()) {
      featureContents = (List<EObject>) contents;
    } else {
      featureContents = new LinkedList<>();
      featureContents.add((EObject) contents);
    }
    
    return featureContents.contains(value);
  }
}