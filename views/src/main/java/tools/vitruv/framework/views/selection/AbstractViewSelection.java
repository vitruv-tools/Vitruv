package tools.vitruv.framework.views.selection;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.framework.views.ModifiableViewSelection;

/**
 * An abstract view selection that selects elements. It provides the basic functionality to select
 * and deselect elements.
 */
public abstract class AbstractViewSelection implements ModifiableViewSelection {
  final Map<EObject, Boolean> elementsSelection = new HashMap<>();

  /**
   * Creates a new view selection with the given selectable elements.
   *
   * @param selectableElements the elements that can be selected in this view selection
   */
  protected AbstractViewSelection(Collection<EObject> selectableElements) {
    selectableElements.forEach(
        object ->
            this.elementsSelection.put(
                checkNotNull(object, "element to select must not be null"), false));
  }

  /**
   * Creates a new view selection with the given selectable elements and the selection state of the
   * source view selection.
   *
   * @param sourceViewSelection the source view selection to copy the selection state from
   *     selectable elements.
   */
  protected AbstractViewSelection(ModifiableViewSelection sourceViewSelection) {
    this(sourceViewSelection.getSelectableElements());
    for (EObject selectableElement : sourceViewSelection.getSelectableElements()) {
      setSelected(selectableElement, sourceViewSelection.isSelected(selectableElement));
    }
  }

  private void checkIsSelectable(EObject eObject) {
    checkState(
        isSelectable(eObject),
        "given object %s must be contained in the selector elements",
        eObject);
  }

  @Override
  public boolean isSelected(EObject eObject) {
    return elementsSelection.getOrDefault(eObject, false);
  }

  @Override
  public boolean isSelectable(EObject eObject) {
    return elementsSelection.keySet().contains(eObject);
  }

  @Override
  public Collection<EObject> getSelectableElements() {
    return Collections.unmodifiableSet(elementsSelection.keySet());
  }

  @Override
  public void setSelected(EObject eObject, boolean selected) {
    checkIsSelectable(eObject);
    elementsSelection.put(eObject, selected);
  }
}
