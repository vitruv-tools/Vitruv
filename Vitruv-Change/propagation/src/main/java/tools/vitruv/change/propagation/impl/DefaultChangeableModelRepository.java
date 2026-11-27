package tools.vitruv.change.propagation.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.interaction.InternalUserInteractor;
import tools.vitruv.change.propagation.ChangePropagationObservableRegistry;
import tools.vitruv.change.propagation.ChangePropagationObserver;
import tools.vitruv.change.propagation.ChangePropagationSpecificationProvider;
import tools.vitruv.change.propagation.PersistableChangeRecordingModelRepository;

/**
 * A default implementation of a {@link ChangeableModelRepository} that requires a {@link
 * PersistableChangeRecordingModelRepository} and a {@link ChangePropagationSpecificationProvider}
 * to propagate changes. It uses the default {@link ChangePropagator} to propagate changes passed to
 * the {@link #propagateChange(VitruviusChange)} method.
 */
public class DefaultChangeableModelRepository implements ChangeableModelRepository,
    ChangePropagationObservableRegistry {
  private static final Logger LOGGER = LogManager.getLogger(DefaultChangeableModelRepository.class);

  private final Set<ChangePropagationListener> changePropagationListeners = new HashSet<>();
  private final Set<ChangePropagationObserver> changePropagationObservers = new HashSet<>();
  private final PersistableChangeRecordingModelRepository modelRepository;
  private final ChangePropagator changePropagator;

  /**
   * Creates a new instance of the {@link DefaultChangeableModelRepository}.
   *
   * @param modelRepository the {@link PersistableChangeRecordingModelRepository} to use for
   * @param changePropagationSpecificationProvider the {@link
   *     ChangePropagationSpecificationProvider}
   * @param userInteractor the {@link InternalUserInteractor} to use for interactions during change
   */
  public DefaultChangeableModelRepository(
      PersistableChangeRecordingModelRepository modelRepository,
      ChangePropagationSpecificationProvider changePropagationSpecificationProvider,
      InternalUserInteractor userInteractor) {
    this.modelRepository = modelRepository;
    this.changePropagator =
        new ChangePropagator(
            modelRepository, changePropagationSpecificationProvider, userInteractor);
  }

  @Override
  public List<PropagatedChange> propagateChange(VitruviusChange<Uuid> change) {
    checkArgument(change != null, "change to propagate must not be null");
    checkArgument(
        change.containsConcreteChange(),
        "change to propagate must contain concrete changes:%s%s",
        System.lineSeparator(),
        change);

    // Set up logging/reporting
    LOGGER.info("Start change propagation");
    notifyChangePropagationStarted(change);    
    List<PropagatedChange> resultChanges =
        changePropagator.propagateChange(change, changePropagationObservers);
    modelRepository.saveOrDeleteModels();
    notifyChangePropagationFinished(change, resultChanges);
    LOGGER.info("Finished change propagation");
    return resultChanges;
  }

  private void notifyChangePropagationStarted(VitruviusChange<Uuid> change) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Started synchronizing change: " + change);
    }
    changePropagationListeners.stream()
        .forEach((listener) -> listener.startedChangePropagation(change));
  }

  private void notifyChangePropagationFinished(
      VitruviusChange<Uuid> inputChange, Iterable<PropagatedChange> generatedChanges) {
    changePropagationListeners.stream()
        .forEach((listener) -> listener.finishedChangePropagation(generatedChanges));
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Finished synchronizing change: " + inputChange);
    }
  }

  @Override
  public void addChangePropagationListener(ChangePropagationListener propagationListener) {
    checkArgument(propagationListener != null, "propagation listener must not be null");
    changePropagationListeners.add(propagationListener);
  }

  @Override
  public void removeChangePropagationListener(ChangePropagationListener propagationListener) {
    checkArgument(propagationListener != null, "propagation listener must not be null");
    changePropagationListeners.remove(propagationListener);
  }

  @Override
  public void registerObserver(ChangePropagationObserver observer) {
    checkArgument(observer != null, "propagation observer must not be null");
    changePropagationObservers.add(observer);
  }

  @Override
  public void deregisterObserver(ChangePropagationObserver observer) {
    checkArgument(observer != null, "propagation observer must not be null");
    changePropagationObservers.remove(observer);
  }
}
