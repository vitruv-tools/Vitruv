package tools.vitruv.framework.remote.client.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edapt.internal.common.EcoreUtils;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.selection.ElementViewSelection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RemoteViewSelector implements ViewSelector {

    private final String uuid;
    private final VitruvRemoteConnection remoteConnection;
    private final ModifiableViewSelection viewSelection;

    public RemoteViewSelector(String uuid, Resource selection, VitruvRemoteConnection remoteConnection) {
        this.uuid = uuid;
        this.remoteConnection = remoteConnection;
        this.viewSelection = new ElementViewSelection(selection.getContents());
    }

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
        return viewSelection.getSelectableElements().stream().anyMatch(it ->
                EcoreUtil.equals(eObject, it) && viewSelection.isViewObjectSelected(it));
    }

    String getUUID() {
        return this.uuid;
    }

    List<String> getSelectionIds() {
        var ids = new LinkedList<String>();
        viewSelection.getSelectableElements().forEach(it -> {
            if (viewSelection.isSelected(it)) {
                ids.add(EcoreUtils.getUUID(it));
            }
        });
        return ids;
    }
}
