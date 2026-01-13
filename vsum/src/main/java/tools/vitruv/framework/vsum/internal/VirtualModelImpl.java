package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationObservableRegistry;
import tools.vitruv.change.propagation.ChangePropagationObserver;
import tools.vitruv.change.propagation.ChangePropagationSpecificationProvider;
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
  public void setChangePropagationMode(ChangePropagationMode changePropagationMode) {
    this.changePropagationMode = changePropagationMode;
  }
}
