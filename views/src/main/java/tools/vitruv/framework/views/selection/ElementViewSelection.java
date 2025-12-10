package tools.vitruv.framework.views.selection;

import java.util.Collection;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.framework.views.ModifiableViewSelection;

/** A view selection that selects elements. */
public class ElementViewSelection extends AbstractViewSelection {

  /**
   * Creates a new view selection with the given selectable elements.
   *
   * @param selectableElements the elements to select
   */
  public ElementViewSelection(Collection<EObject> selectableElements) {
    super(selectableElements);
  }

  /**
   * Creates a new view selection with the given selectable elements and the selection state.
   *
   * @param sourceViewSelection the source view selection to copy the selection state from
   *     selectable elements.
   */
  public ElementViewSelection(ModifiableViewSelection sourceViewSelection) {
    super(sourceViewSelection);
  }

  @Override
  public boolean isViewObjectSelected(EObject eObject) {
    return isSelected(eObject);
  }
}
