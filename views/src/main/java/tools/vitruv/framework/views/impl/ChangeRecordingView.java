package tools.vitruv.framework.views.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

/**
 * A view that records changes to its resources and allows to propagate them back to the underlying
 * models using the {@link #commitChanges} method.
 */
public class ChangeRecordingView implements ModifiableView, CommittableView {
  private final BasicView view;
  private ChangeRecorder changeRecorder;

  /**
   * Creates a new instance with the given underlying.
   *
   * @param view the underlying view
   * @throws IllegalArgumentException if view is null
   * @throws IllegalStateException if the view is modified
   * @see #commitChanges()
   */
  protected ChangeRecordingView(BasicView view) {
    checkArgument(view != null, "view must not be null");
    checkState(!view.isModified(), "view must not be modified");
    this.view = view;
    setupChangeRecorder();
  }

  @Override
  public void update() {
    endRecordingAndClose(changeRecorder);
    view.update();
    setupChangeRecorder();
  }

  private void setupChangeRecorder() {
    changeRecorder = new ChangeRecorder(view.getViewResourceSet());
    changeRecorder.addToRecording(view.getViewResourceSet());
    changeRecorder.beginRecording();
  }

  @Override
  public void commitChanges() {
    view.checkNotClosed();
    VitruviusChange<EObject> recordedChange = changeRecorder.endRecording();
    var changeResolver =
        VitruviusChangeResolverFactory.forHierarchicalIds(view.getViewResourceSet());
    VitruviusChange<HierarchicalId> unresolvedChanges = changeResolver.assignIds(recordedChange);
    view.getViewType().commitViewChanges(this, unresolvedChanges);
    view.setViewChanged(false);
    changeRecorder.beginRecording();
  }

  @Override
  public void close() throws Exception {
    try (view) {
      if (!isClosed()) {
        changeRecorder.close();
      }
    }
  }

  private void endRecordingAndClose(ChangeRecorder recorder) {
    try (recorder) {
      if (recorder.isRecording()) {
        recorder.endRecording();
      }
    }
  }

  @Override
  public ChangeRecordingView withChangeRecordingTrait() {
    ChangeRecordingView newView = view.withChangeRecordingTrait();
    changeRecorder.close();
    return newView;
  }

  @Override
  public ChangeDerivingView withChangeDerivingTrait(
      StateBasedChangeResolutionStrategy changeResolutionStrategy) {
    ChangeDerivingView newView = view.withChangeDerivingTrait(changeResolutionStrategy);
    changeRecorder.close();
    return newView;
  }

  // Delegation methods from BasicView
  @Override
  public List<EObject> getRootObjects() {
    return view.getRootObjects();
  }

  @Override
  public boolean isModified() {
    return view.isModified();
  }

  @Override
  public boolean isOutdated() {
    return view.isOutdated();
  }

  @Override
  public void modifyContents(Consumer<ResourceSet> modificationFunction) {
    view.modifyContents(modificationFunction);
  }

  @Override
  public void registerRoot(EObject object, org.eclipse.emf.common.util.URI persistAt) {
    view.registerRoot(object, persistAt);
  }

  @Override
  public void moveRoot(EObject object, org.eclipse.emf.common.util.URI newLocation) {
    view.moveRoot(object, newLocation);
  }

  @Override
  public boolean isClosed() {
    return view.isClosed();
  }

  @Override
  public ChangeableViewSource getViewSource() {
    return view.getViewSource();
  }

  @Override
  public ViewSelection getSelection() {
    return view.getSelection();
  }

  @Override
  public ViewType<? extends ViewSelector> getViewType() {
    return view.getViewType();
  }
}
