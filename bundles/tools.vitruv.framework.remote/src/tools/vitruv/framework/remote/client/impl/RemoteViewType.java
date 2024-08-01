package tools.vitruv.framework.remote.client.impl;

import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

/**
 * A Vitruv view type representing actual types on the virtual model, but is still capable of providing a view selector and allows creating
 * views by querying the vitruv server.
 */
public class RemoteViewType implements ViewType<ViewSelector> {

    private final String name;
    private final VitruvRemoteConnection remoteConnection;

    RemoteViewType(String name, VitruvRemoteConnection remoteConnection) {
        this.name = name;
        this.remoteConnection = remoteConnection;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the {@link ViewSelector} of the {@link ViewType}, which allows configuring views by delegating the request to the vitruv server.
     * @param viewSource ignored, can be null
     * @return a view selector for the view type represented by this remote view type
     */
    @Override
    public ViewSelector createSelector(ChangeableViewSource viewSource) {
        return remoteConnection.getSelector(name);
    }
}
