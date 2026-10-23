package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.InternalUserInteractor;
import tools.vitruv.change.interaction.UserInteractionBase;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationObservableRegistry;
import tools.vitruv.change.propagation.ChangePropagationObserver;
import tools.vitruv.change.propagation.ChangePropagationSpecificationProvider;
import tools.vitruv.change.propagation.QueuedChange;
import tools.vitruv.change.propagation.QueuedPropagationStep;
import tools.vitruv.change.propagation.QueuedPropagationStep.Outcome;
import tools.vitruv.change.propagation.impl.ChangePropagationQueue;
import tools.vitruv.change.propagation.impl.ChangePropagator;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeProvider;
import tools.vitruv.framework.views.ViewTypeRepository;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.messages.ErrorMessages;
import tools.vitruv.framework.vsum.internal.messages.InfoMessages;

/** The implementation of the {@link InternalVirtualModel} interface. */
public class VirtualModelImpl implements InternalVirtualModel, ChangePropagationObservableRegistry {
  private static final Logger LOGGER = LogManager.getLogger(VirtualModelImpl.class);

  private final ModelRepository resourceRepository;
  private final ViewTypeProvider viewTypeRepository;
  private final VsumFileSystemLayout fileSystemLayout;
  private final List<ChangePropagationListener> changePropagationListeners = new LinkedList<>();
  private final List<ChangePropagationObserver> changePropagationObservers = new LinkedList<>();

  private final ChangePropagationSpecificationProvider changePropagationSpecificationProvider;
  private final InternalUserInteractor userInteractor;
  private ChangePropagationMode changePropagationMode = ChangePropagationMode.TRANSITIVE_CYCLIC;

  // Queued steps run under the monitor of this object, just like propagateChange, so the two never
  // modify the models at the same time. Enqueuing, looking at the queue and answering user
  // interactions do not take that monitor, so they never wait for a running step.
  private volatile ChangePropagationQueue changePropagationQueue;

  // Only setChangePropagationMode replaces the queue, and only while it is empty. Without this
  // lock, a change enqueued at that very moment could end up in the queue being thrown away.
  private final Object queueReplacementLock = new Object();

  // Task ids of enqueued changes that have been applied to the models in a deferred step and wait
  // to be propagated again. Only used by queued steps, under the monitor of this object.
  private final Set<Integer> deferredAppliedChanges = new HashSet<>();

  /**
   * Creates a new instance of the {@link VirtualModelImpl} class.
   *
   * @param fileSystemLayout The file system layout of the virtual model.
   * @param userInteractor The user interactor.
   * @param viewTypeRepository The view type repository.
   * @param changePropagationSpecificationProvider The change propagation specification provider.
   */
  public VirtualModelImpl(
      VsumFileSystemLayout fileSystemLayout,
      InternalUserInteractor userInteractor,
      ViewTypeRepository viewTypeRepository,
      ChangePropagationSpecificationProvider changePropagationSpecificationProvider) {
    this.fileSystemLayout = fileSystemLayout;
    this.viewTypeRepository = viewTypeRepository;
    resourceRepository = new ResourceRepositoryImpl(fileSystemLayout);

    this.changePropagationSpecificationProvider = changePropagationSpecificationProvider;
    this.userInteractor = userInteractor;
    this.changePropagationQueue = createChangePropagationQueue();
  }

  /** Loads the existing models of the virtual model. */
  public void loadExistingModels() {
    resourceRepository.loadExistingModels();
  }

  @Override
  public synchronized EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
    return resourceRepository.getCorrespondenceModel();
  }

  @Override
  public synchronized ModelInstance getModelInstance(URI modelUri) {
    return resourceRepository.getModel(modelUri);
  }

  @Override
  public UuidResolver getUuidResolver() {
    return resourceRepository.getUuidResolver();
  }

  private synchronized void save() {
    resourceRepository.saveOrDeleteModels();
  }

  @Override
  public synchronized List<PropagatedChange> propagateChange(VitruviusChange<Uuid> change) {
    checkNotNull(change, ErrorMessages.CHANGE_NULL);
    checkArgument(
        change.containsConcreteChange(),
        ErrorMessages.CHANGE_HAS_NO_CONCRETE_CHANGE,
        System.lineSeparator(),
        change);

    LOGGER.info(InfoMessages.START_PROPAGATION);
    startChangePropagation(change);

    ChangePropagator changePropagator =
        new ChangePropagator(
            resourceRepository,
            changePropagationSpecificationProvider,
            userInteractor,
            changePropagationMode);
    List<PropagatedChange> result =
        changePropagator.propagateChange(change, changePropagationObservers);
    save();

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(InfoMessages.TRACE_PROPAGATED_CHANGES, result);
    }

    finishChangePropagation(change, result);
    LOGGER.info(InfoMessages.FINISH_PROPAGATION);
    return result;
  }

  private void startChangePropagation(VitruviusChange<Uuid> change) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(InfoMessages.DEBUG_STARTED_SYNC, change);
    }
    changePropagationListeners.stream().forEach(it -> it.startedChangePropagation(change));
  }

  private void finishChangePropagation(
      VitruviusChange<Uuid> inputChange, Iterable<PropagatedChange> generatedChanges) {
    changePropagationListeners.stream()
        .forEach(it -> it.finishedChangePropagation(generatedChanges));
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(InfoMessages.DEBUG_FINISHED_SYNC, inputChange);
    }
  }

  @Override
  public int enqueueChange(VitruviusChange<Uuid> change) {
    checkChangeToEnqueue(change);
    synchronized (queueReplacementLock) {
      return changePropagationQueue.enqueueChange(change);
    }
  }

  @Override
  public int enqueueChange(VitruviusChange<Uuid> change, int taskId) {
    checkChangeToEnqueue(change);
    synchronized (queueReplacementLock) {
      return changePropagationQueue.enqueueChange(change, taskId);
    }
  }

  private static void checkChangeToEnqueue(VitruviusChange<Uuid> change) {
    checkNotNull(change, ErrorMessages.CHANGE_NULL);
    checkArgument(
        change.containsConcreteChange(),
        ErrorMessages.CHANGE_HAS_NO_CONCRETE_CHANGE,
        System.lineSeparator(),
        change);
  }

  @Override
  public synchronized Optional<QueuedPropagationStep> propagateNextQueuedChange() {
    // No other step runs while this monitor is held, and only steps reorder the queue; enqueuing
    // just appends to it. So the first queued change is the one this step takes.
    List<QueuedChange> queuedChanges = changePropagationQueue.getQueuedChanges();
    if (queuedChanges.isEmpty()) {
      return Optional.empty();
    }
    QueuedChange next = queuedChanges.get(0);
    // The first step of an enqueued change applies it to the models, whatever happens afterwards.
    boolean appliesEnqueuedChange =
        next.parentTaskId() == null && !deferredAppliedChanges.contains(next.taskId());
    Optional<QueuedPropagationStep> step;
    try {
      step = changePropagationQueue.propagateNext(changePropagationObservers);
    } catch (RuntimeException e) {
      // The queue has dropped the whole enqueued change, not only the change this step took.
      deferredAppliedChanges.remove(next.rootTaskId());
      throw e;
    }
    // If saving or a listener fails here, the step has gone through and the queue keeps the rest of
    // the enqueued change, so nothing is dropped.
    step.ifPresent(it -> finishQueuedPropagationStep(it, appliesEnqueuedChange));
    return step;
  }

  private void finishQueuedPropagationStep(
      QueuedPropagationStep step, boolean appliesEnqueuedChange) {
    if (step.outcome() == Outcome.PROPAGATED) {
      deferredAppliedChanges.remove(step.taskId());
      save();
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(InfoMessages.TRACE_PROPAGATED_CHANGES, step.propagatedChanges());
      }
      changePropagationListeners.stream()
          .forEach(it -> it.finishedChangePropagation(step.propagatedChanges()));
    }
    if (step.outcome() == Outcome.DEFERRED && appliesEnqueuedChange) {
      // The enqueued change stays applied while it waits for the answer, so views are outdated
      // already. Nothing has been propagated yet, and the models are saved once it is.
      deferredAppliedChanges.add(step.taskId());
      changePropagationListeners.stream().forEach(it -> it.finishedChangePropagation(List.of()));
    }
    if (step.outcome() == Outcome.SKIPPED_BLOCKED) {
      // A worker may come across the same blocked change again and again, so keep this out of the
      // info log.
      LOGGER.debug(InfoMessages.QUEUED_PROPAGATION_STEP, step.taskId(), step.outcome());
    } else {
      LOGGER.info(InfoMessages.QUEUED_PROPAGATION_STEP, step.taskId(), step.outcome());
    }
  }

  @Override
  public List<QueuedChange> getQueuedChanges() {
    return changePropagationQueue.getQueuedChanges();
  }

  @Override
  public boolean isChangePending(int taskId) {
    return changePropagationQueue.isPending(taskId);
  }

  @Override
  public void registerUserInteractions(int taskId, UserInteractionBase... interactions) {
    changePropagationQueue.registerUserInteractions(taskId, interactions);
  }

  private ChangePropagationQueue createChangePropagationQueue() {
    return new ChangePropagationQueue(
        new ChangePropagator(
            resourceRepository,
            changePropagationSpecificationProvider,
            userInteractor,
            changePropagationMode));
  }

  @Override
  public Path getFolder() {
    return fileSystemLayout.getVsumProjectFolder();
  }

  /**
   * Registers the given {@link ChangePropagationListener}. The listener must not be <code>null
   * </code>.
   */
  @Override
  public synchronized void addChangePropagationListener(
      ChangePropagationListener propagationListener) {
    this.changePropagationListeners.add(
        checkNotNull(propagationListener, ErrorMessages.PROPAGATION_LISTENER_NULL));
  }

  /**
   * Unregisters the given {@link ChangePropagationListener}. The listener must not be <code>null
   * </code>.
   */
  @Override
  public synchronized void removeChangePropagationListener(
      ChangePropagationListener propagationListener) {
    this.changePropagationListeners.remove(
        checkNotNull(propagationListener, ErrorMessages.PROPAGATION_LISTENER_NULL));
  }

  @Override
  public void deregisterObserver(ChangePropagationObserver observer) {
    this.changePropagationObservers.remove(
        checkNotNull(observer, ErrorMessages.PROPAGATION_OBSERVER_NULL));
  }

  @Override
  public void registerObserver(ChangePropagationObserver observer) {
    this.changePropagationObservers.add(
        checkNotNull(observer, ErrorMessages.PROPAGATION_OBSERVER_NULL));
  }

  /**
   * Returns the name of the virtual model.
   *
   * @return The name of the virtual model
   */
  public String getName() {
    return getFolder().getFileName().toString();
  }

  @Override
  public void dispose() {
    try {
      resourceRepository.close();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    VirtualModelRegistry.getInstance().deregisterVirtualModel(this);
  }

  @Override
  public Collection<Resource> getViewSourceModels() {
    return resourceRepository.getModelResources();
  }

  @Override
  public Collection<ViewType<?>> getViewTypes() {
    return viewTypeRepository.getViewTypes();
  }

  @Override
  public <S extends ViewSelector> S createSelector(ViewType<S> viewType) {
    /*
     * Note that ViewType.createSelector() accepts a ChangeableViewSource, which
     * VirtualModelImpl implements but not its publicly used interface VitualModel.
     * Thus calling viewType.createSelector(virtualModel) with virtualModel having
     * the static type VirtualModel is not possible, i.e., this method hides
     * implementation details and is not a convenience method.
     */
    return viewType.createSelector(this);
  }

  @Override
  public synchronized void setChangePropagationMode(ChangePropagationMode changePropagationMode) {
    synchronized (queueReplacementLock) {
      checkState(
          changePropagationQueue.getQueuedChanges().isEmpty(),
          ErrorMessages.CHANGES_QUEUED_ON_MODE_CHANGE);
      this.changePropagationMode = changePropagationMode;
      // A change propagator keeps the mode it was created with, so the queue needs a new one.
      changePropagationQueue = createChangePropagationQueue();
    }
  }
}
