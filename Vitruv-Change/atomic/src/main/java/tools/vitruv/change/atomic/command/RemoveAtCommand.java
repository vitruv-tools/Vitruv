package tools.vitruv.change.atomic.command;

import java.util.ArrayList;
import java.util.Collections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Command which is used to remove a entry of a EList at a specific index.
 * Only single objects are supported.
 */
public class RemoveAtCommand extends RemoveCommand {
  /**
   * Index at which the value is removed in the list.
   */
  private int index;

  /**
   * Constructor for a RemoveAtCommand, which removes an entry of a EStructuralFeature
   * at a specific index.
   *
   * @param editingDomain The used editing domain.
   * @param owner The owning EObject of the EList.
   * @param feature The feature of the owner which is an EList.
   * @param value The value which will be removed.
   * @param index Index at which the value is removed in the EList.
   */
  public RemoveAtCommand(EditingDomain editingDomain, EObject owner, EStructuralFeature feature,
      Object value, int index) {
    super(editingDomain, owner, feature, Collections.singleton(value));
    this.index = index;
  }

  /**
   * Constructor for a RemoveATCommand, which removes an entry of a EList at a specific index.
   *
   * @param editingDomain The used editing domain.
   * @param ownerList The edited EList.
   * @param value The value which will be removed.
   * @param index The Index at which the value is removed in the EList.
   */
  public RemoveAtCommand(EditingDomain editingDomain, EList<?> ownerList, Object value, int index) {
    super(editingDomain, ownerList, Collections.singleton(value));
    this.index = index;
  }

  /**
   * Returns the index at which the value will be removed.
   *
   * @return The index
   */
  public int getIndex() {
    return index;
  }

  @Override
  public void doExecute() {
    ownerList.remove(index);
  }

  @Override
  public boolean prepare() {
    var executable = super.prepare() && 0 <= index && index < ownerList.size() 
        && (collection.size() == 1);
    if (!executable) {
      return false;
    }
    // Check if get(index) == object
    // Assumes collection is an ArrayList
    return ownerList.get(index).equals(((ArrayList<Object>) collection).get(0));
  }

  @Override
  public void doUndo() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void doRedo() {
    throw new UnsupportedOperationException();
  }
}
