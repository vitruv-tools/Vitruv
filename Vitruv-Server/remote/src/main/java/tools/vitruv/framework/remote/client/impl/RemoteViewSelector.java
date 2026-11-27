package tools.vitruv.framework.remote.client.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.selection.ElementViewSelection;

/**
 * A selector for selecting the elements to be represented in a view, but on the Vitruvius client
 * itself. It is capable of acting as a builder for a view by providing an appropriate creation
 * method, handling the remote connection.
 */
public class RemoteViewSelector implements ViewSelector {
  private final String uuid;
  private final VitruvRemoteConnection remoteConnection;
  private final ModifiableViewSelection viewSelection;

  /**
   * Creates a new {@link RemoteViewSelector}.
   *
   * @param uuid the unique identifier of the view
   * @param selection the selection defining the elements to be included in the view
   * @param remoteConnection the remote connection to the Vitruvius server
   */
  public RemoteViewSelector(
      String uuid, Resource selection, VitruvRemoteConnection remoteConnection) {
    this.uuid = uuid;
    this.remoteConnection = remoteConnection;
    this.viewSelection = new ElementViewSelection(selection.getContents());
  }

  /**
   * Creates a view by delegating the request to the Vitruvius server, performing the selection done
   * by this selector.
   *
   * @return The created view.
   */
  @Override
  public View createView() {
    return remoteConnection.getView(this);
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public ViewSelection getSelection() {
    return viewSelection;
  }

  @Override
  public Collection<EObject> getSelectableElements() {
    return viewSelection.getSelectableElements();
  }

  @Override
  public boolean isSelected(EObject eObject) {
    return viewSelection.isSelected(eObject);
  }

  @Override
  public boolean isSelectable(EObject eObject) {
    return viewSelection.isSelectable(eObject);
  }

  @Override
  public void setSelected(EObject eObject, boolean selected) {
    viewSelection.setSelected(eObject, selected);
  }

  @Override
  public boolean isViewObjectSelected(EObject eObject) {
    return viewSelection.getSelectableElements().stream()
        .anyMatch(it -> EcoreUtil.equals(eObject, it) && viewSelection.isViewObjectSelected(it));
  }

  /**
   * Gets the UUID of the view to be created.
   *
   * @return The UUID.
   */
  String getUUID() {
    return this.uuid;
  }

  /**
   * Gets the IDs of the selected elements in the selection.
   *
   * @return The list of IDs.
   */
  List<String> getSelectionIds() {
    var ids = new LinkedList<String>();
    viewSelection
        .getSelectableElements()
        .forEach(
            it -> {
              if (viewSelection.isSelected(it)) {
                var resource = (JsonResource) it.eResource();
                ids.add(resource.getID(it));
              }
            });
    return ids;
  }
}
