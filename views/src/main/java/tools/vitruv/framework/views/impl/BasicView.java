package tools.vitruv.framework.views.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

class BasicView implements ModifiableView, ChangePropagationListener {
  private ViewSelection selection;
  private ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType;
  private ChangeableViewSource viewSource;
  private final ResourceSet viewResourceSet;
  private boolean modelChanged;
  private boolean viewChanged;
  private boolean closed;

  /**
   * Constructor for a basic view.
   *
   * @param viewType the type of the view
   * @param viewSource the source of the view
   * @param selection the selection of the view
   */
  protected BasicView(
      ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
      ChangeableViewSource viewSource,
      ViewSelection selection) {
    checkArgument(viewType != null, "view type must not be null");
    checkArgument(viewSource != null, "view selection must not be null");
    checkArgument(selection != null, "view source must not be null");
    this.viewType = viewType;
    this.viewSource = viewSource;
    this.selection = selection;
    viewSource.addChangePropagationListener(this);
    this.viewResourceSet = withGlobalFactories(new ResourceSetImpl());
    update();
  }

  @Override
  public List<EObject> getRootObjects() {
    checkNotClosed();
    return viewResourceSet.getResources().stream()
        .flatMap(resource -> resource.getContents().stream())
        .toList();
  }

  @Override
  public boolean isModified() {
    return viewChanged;
  }

  @Override
  public boolean isOutdated() {
    return modelChanged;
  }

  @Override
  public void update() {
    checkNotClosed();
    checkState(!isModified(), "cannot update from model when view is modified");
    modelChanged = false;
    viewType.updateView(this);
    viewChanged = false;
    addChangeListeners(viewResourceSet);
  }

  @Override
  public void close() throws Exception {
    if (!closed) {
      closed = true;
      viewResourceSet
          .getResources()
          .forEach(
              resource -> {
                try {
                  resource.unload();
                } catch (Exception e) {
                  e.printStackTrace();
                }
              });
      viewResourceSet.getResources().clear();
      removeChangeListeners(viewResourceSet);
    }
    viewSource.removeChangePropagationListener(this);
  }

  @Override
  public boolean isClosed() {
    return closed;
  }

  @Override
  public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
    modelChanged = true;
  }

  @Override
  public void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {
    // do nothing
  }

  @Override
  public void registerRoot(EObject object, URI persistAt) {
    checkNotClosed();
    checkArgument(object != null, "object to register as root must not be null");
    checkArgument(persistAt != null, "URI for root to register must not be null");
    Resource resource = viewResourceSet.createResource(persistAt);
    resource.getContents().add(object);
  }

  @Override
  public void moveRoot(EObject object, URI newLocation) {
    checkNotClosed();
    checkArgument(object != null, "object to move must not be null");
    checkState(getRootObjects().contains(object), "view must contain element %s to move", object);
    checkArgument(newLocation != null, "URI for new location of root must not be null");
    viewResourceSet.getResources().stream()
        .filter(resource -> resource.getContents().contains(object))
        .findFirst()
        .ifPresent(resource -> resource.setURI(newLocation));
  }

  protected void checkNotClosed() {
    checkState(!closed, "view is already closed!");
  }

  private void addChangeListeners(Notifier notifier) {
    notifier
        .eAdapters()
        .add(
            new AdapterImpl() {
              @Override
              public void notifyChanged(Notification notification) {
                viewChanged = true;
              }
            });
    if (notifier instanceof ResourceSet resourceSet) {
      resourceSet.getResources().forEach(this::addChangeListeners);
    } else if (notifier instanceof Resource resource) {
      resource.getContents().forEach(this::addChangeListeners);
    } else if (notifier instanceof EObject eObject) {
      eObject.eContents().forEach(this::addChangeListeners);
    }
  }

  private void removeChangeListeners(ResourceSet resourceSet) {
    resourceSet.getAllContents().forEachRemaining(eObject -> eObject.eAdapters().clear());
  }

  @Override
  public void modifyContents(Consumer<ResourceSet> modificationFunction) {
    modificationFunction.accept(viewResourceSet);
  }

  @Override
  public ChangeRecordingView withChangeRecordingTrait() {
    checkNotClosed();
    return new ChangeRecordingView(this);
  }

  @Override
  public ChangeDerivingView withChangeDerivingTrait(
      StateBasedChangeResolutionStrategy changeResolutionStrategy) {
    checkNotClosed();
    return new ChangeDerivingView(this, changeResolutionStrategy);
  }

  public ViewSelection getSelection() {
    return selection;
  }

  protected void setSelection(ViewSelection selection) {
    this.selection = selection;
  }

  public ViewCreatingViewType<? extends ViewSelector, HierarchicalId> getViewType() {
    return viewType;
  }

  protected void setViewType(
      ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType) {
    this.viewType = viewType;
  }

  public ChangeableViewSource getViewSource() {
    return viewSource;
  }

  protected void setViewSource(ChangeableViewSource viewSource) {
    this.viewSource = viewSource;
  }

  protected ResourceSet getViewResourceSet() {
    return viewResourceSet;
  }

  protected void setViewChanged(boolean viewChanged) {
    this.viewChanged = viewChanged;
  }
}
