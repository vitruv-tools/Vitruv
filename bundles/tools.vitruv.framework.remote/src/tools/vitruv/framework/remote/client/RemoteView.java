package tools.vitruv.remote.client;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

/**
 * A {@link View} which is a copy of a {@link View} from the VSUM of a vitruv server.
 * <p>
 * Actions performed on this remote view or to the original view can be synchronized via the network. This view uses
 * a {@link VitruvRemoteConnection} to do so.
 */
public class RemoteView implements View {

    protected final String uuid;
    protected final VitruvRemoteConnection remoteConnection;

    protected ResourceSet viewSource;
    protected boolean modified = false;

    RemoteView(String uuid, ResourceSet viewSource, VitruvRemoteConnection remoteConnection) {
        this.uuid = uuid;
        this.remoteConnection = remoteConnection;
        this.viewSource = viewSource;

        addChangeListeners(viewSource);
    }

    /**
     * Provides the root model elements of this view.
     *
     * @throws IllegalStateException if called on a closed view.
     * @see View#isClosed()
     */
    @Override
    public Collection<EObject> getRootObjects() {
        checkNotClosed();
        return viewSource.getResources().stream().map(Resource::getContents).flatMap(Collection::stream).toList();
    }

    /**
     * Checks whether the view was closed. Closed views cannot be used further. All
     * methods may throw an {@link IllegalStateException}.
     */
    @Override
    public boolean isClosed() {
        return remoteConnection.isViewClosed(uuid);
    }

    /**
     * Returns whether the view was modified.
     */
    @Override
    public boolean isModified() {
        return modified;
    }

    /**
     * Returns whether the view is outdated, i.e., whether the underlying view
     * sources have changed.
     */
    @Override
    public boolean isOutdated() {
        return remoteConnection.isViewOutdated(uuid);
    }

    /**
     * Updates the view via the {@link VitruvRemoteConnection}, thus invalidating its previous state and now providing
     * an updated view. This can only be done for an unmodified view.
     *
     * @throws UnsupportedOperationException if called on a modified view
     * @throws IllegalStateException         if called on a closed view
     * @see #isClosed()
     * @see #isModified()
     */
    @Override
    public void update() {
        checkNotClosed();
        checkState(!isModified(), "cannot update from model when view is modified");
        removeChangeListeners(viewSource);
        viewSource = remoteConnection.updateView(uuid);
        modified = false;
        addChangeListeners(viewSource);
    }

    @Override
    public void close() {
        if (!isClosed()) {
            remoteConnection.closeView(uuid);
            viewSource.getResources().forEach(Resource::unload);
            viewSource.getResources().clear();
            removeChangeListeners(viewSource);
        }
    }

    /**
     * Persists the given object at the given {@link URI} and adds it as view root.
     */
    @Override
    public void registerRoot(EObject object, URI persistAt) {
        checkNotClosed();
        checkArgument(object != null, "object must not be null");
        checkArgument(persistAt != null, "URI for root must not be null");
        viewSource.createResource(persistAt).getContents().add(object);
    }

    /**
     * Moves the given object to the given {@link URI}. The given {@link EObject}
     * must already be a root object of the view, otherwise an
     * {@link IllegalStateException} is thrown.
     */
    @Override
    public void moveRoot(EObject object, URI newLocation) {
        checkNotClosed();
        checkArgument(object != null, "object to move must not be null");
        checkState(getRootObjects().contains(object), "view must contain element to move");
        checkArgument(newLocation != null, "URI for new location of root must not be null");
        viewSource.getResources().stream().filter(it -> it.getContents().contains(object)).findFirst().get().setURI(newLocation);
    }

    /**
     * Returns the {@link ViewSelection} with which this view has been created.
     */
    @Override
    public ViewSelection getSelection() {
        return eObject -> {
            //Currently, the server selects all elements.
            //Therefore, we can just check if the given object is in our resource set
            return viewSource.getResources().stream().anyMatch(it -> it.getContents().stream().anyMatch(eObject::equals));
        };
    }

    /**
     * UNSUPPORTED AT THE MOMENT!!
     */
    @Override
    public ViewType<? extends ViewSelector> getViewType() {
        //The client has no knowledge which view type was used to create the remote view.
        //Additionally, the client is not able to create views.
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link CommittableView} based on the view's configuration.
     * Changes to commit are identified by recording any changes made to the view.
     *
     * @throws UnsupportedOperationException if called on a modified view
     * @throws IllegalStateException         if called on a closed view
     * @see #isClosed()
     * @see #isModified()
     */
    @Override
    public CommittableView withChangeRecordingTrait() {
        checkNotClosed();
        return new ChangeRecordingRemoteView(this);
    }

    /**
     * Returns a {@link CommittableView} based on the view's configuration.
     * Changes to commit are identified by comparing the current view state with its state from the last update.
     *
     * @param changeResolutionStrategy The change resolution strategy to use for view state comparison. Must not be <code>null</code>.
     * @throws UnsupportedOperationException if called on a modified view
     * @throws IllegalStateException         if called on a closed view
     * @see #isClosed()
     * @see #isModified()
     */
    @Override
    public CommittableView withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        checkNotClosed();
        return new ChangeDerivingRemoteView(this, changeResolutionStrategy);
    }

    void checkNotClosed() {
        checkState(!isClosed(), "View is already closed");
    }

    void checkState(boolean state, String msg) {
        if (!state) {
            throw new IllegalStateException(msg);
        }
    }

    void checkArgument(boolean arg, String msg) {
        if (!arg) {
            throw new IllegalArgumentException(msg);
        }
    }

    private void addChangeListeners(Notifier notifier) {
        notifier.eAdapters().add(new AdapterImpl() {
            @Override
            public void notifyChanged(Notification message) {
                modified = true;
            }
        });

        if (notifier instanceof ResourceSet n) {
            n.getResources().forEach(this::addChangeListeners);
        } else if (notifier instanceof Resource n) {
            n.getContents().forEach(this::addChangeListeners);
        } else if (notifier instanceof EObject n) {
            n.eContents().forEach(this::addChangeListeners);
        }
    }

    private void removeChangeListeners(ResourceSet resourceSet) {
        resourceSet.getAllContents().forEachRemaining(it -> it.eAdapters().clear());
    }
}
