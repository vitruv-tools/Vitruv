package tools.vitruv.change.testutils.changevisualization.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.testutils.changevisualization.tree.decoder.TreeChangeDataSetDecoder;

/**
 * The ModelRepositoryChanges class is used to monitor the changes of a model repository and to
 * generate ChangeDataSets from them.
 */
public class ModelRepositoryChanges implements ChangePropagationListener, Serializable {
  private static final long serialVersionUID = 4992108309750505218L;

  private final String repositoryName;
  private final List<ChangeDataSet> changeDataSets = new ArrayList<>();
  private transient Set<ChangeDataSetGenerationListener> dataSetGenerationListener;
  private transient Set<ChangeableModelRepository> monitoredRepositories;

  /**
   * Creates a new instance of the ModelRepositoryChanges.
   *
   * @param repositoryName The name of the repository
   */
  public ModelRepositoryChanges(String repositoryName) {
    this.repositoryName = repositoryName;
  }

  /**
   * Returns the name of the repository.
   *
   * @return The name of the repository
   */
  public String getRepositoryName() {
    return repositoryName;
  }

  private Set<ChangeDataSetGenerationListener> getDataSetGenerationListener() {
    if (dataSetGenerationListener == null) {
      dataSetGenerationListener = new HashSet<>();
    }
    return dataSetGenerationListener;
  }

  private Set<ChangeableModelRepository> getMonitoredRepositories() {
    if (monitoredRepositories == null) {
      monitoredRepositories = new HashSet<>();
    }
    return monitoredRepositories;
  }

  /**
   * Returns a list of all ChangeDataSets that have been generated so far.
   *
   * @return The list of ChangeDataSets
   */
  public List<ChangeDataSet> getChangeDataSets() {
    return new ArrayList<>(changeDataSets);
  }

  /**
   * Starts monitoring the changes of the given repository.
   *
   * @param modelRepository The repository to monitor
   */
  public void startMonitoring(ChangeableModelRepository modelRepository) {
    getMonitoredRepositories().add(modelRepository);
    modelRepository.addChangePropagationListener(this);
  }

  /** Stops monitoring the changes of the repositories. */
  public void stopMonitoring() {
    getMonitoredRepositories()
        .forEach(repository -> repository.removeChangePropagationListener(this));
    getMonitoredRepositories().clear();
  }

  @Override
  public void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {
    // Do nothing
  }

  @Override
  public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
    addChanges(propagatedChanges);
  }

  /**
   * Adds the given changes to the list of changes.
   *
   * @param changes The changes to add
   */
  private synchronized void addChanges(Iterable<PropagatedChange> changes) {
    String changeName = repositoryName + " Change " + (changeDataSets.size() + 1);
    ChangeDataSet extractedDataSet =
        new TreeChangeDataSetDecoder(changeName, changes).getChangeDataSet();
    changeDataSets.add(extractedDataSet);
    getDataSetGenerationListener()
        .forEach(listener -> listener.changeDataSetGenerated(extractedDataSet));
  }

  /**
   * Registers a listener for the generation of a ChangeDataSet.
   *
   * @param changeDataSetGenerationListener The listener to register
   */
  public void registerChangeDataSetGenerationListener(
      ChangeDataSetGenerationListener changeDataSetGenerationListener) {
    getDataSetGenerationListener().add(changeDataSetGenerationListener);
  }
}
