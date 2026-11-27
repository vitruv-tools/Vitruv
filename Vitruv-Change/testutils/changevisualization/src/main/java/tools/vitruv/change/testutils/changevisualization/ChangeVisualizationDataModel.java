package tools.vitruv.change.testutils.changevisualization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.testutils.changevisualization.common.ModelRepositoryChanges;
import tools.vitruv.change.testutils.changevisualization.persistence.ChangeDataSetPersistenceHelper;

/** This class manages the data used for change visualization. */
public final class ChangeVisualizationDataModel {
  private final Map<String, ModelRepositoryChanges> modelRepositoryChanges = new HashMap<>();
  private final List<MonitoredRepositoryAddedListener> repositoryAddedListener = new ArrayList<>();

  /**
   * Starts monitoring the changes of the given repository.
   *
   * @param repositoryName The name of the repository to monitor
   * @param modelRepository The repository to monitor
   */
  public void startMonitoringRepositoryChanges(
      String repositoryName, ChangeableModelRepository modelRepository) {
    ModelRepositoryChanges modelRepositoryChanges = new ModelRepositoryChanges(repositoryName);
    this.modelRepositoryChanges.put(repositoryName, modelRepositoryChanges);
    modelRepositoryChanges.startMonitoring(modelRepository);
    this.repositoryAddedListener.forEach(
        listener -> listener.addedMonitoredRepository(modelRepositoryChanges));
  }

  /**
   * Registers a listener that is called when a new repository is added.
   *
   * @param repositoryAddedListener The listener to register
   */
  public void registerMonitoredRepositoryAddedListener(
      MonitoredRepositoryAddedListener repositoryAddedListener) {
    this.repositoryAddedListener.add(repositoryAddedListener);
  }

  /**
   * Stops monitoring the changes of the given repository.
   *
   * @param repositoryName The name of the repository to stop monitoring
   */
  public void removeMonitoredRepository(String repositoryName) {
    this.modelRepositoryChanges.get(repositoryName).stopMonitoring();
  }

  /**
   * Loads all model repository changes from given file.
   *
   * @param file The file to load from, must not be <code>null</code>
   * @throws IOException If anything went wrong loading the file
   */
  public void loadFromFile(File file) throws IOException {
    for (ModelRepositoryChanges modelRepositoryChanges :
        ChangeDataSetPersistenceHelper.load(file)) {
      this.modelRepositoryChanges.put(
          modelRepositoryChanges.getRepositoryName(), modelRepositoryChanges);
      this.repositoryAddedListener.forEach(
          listener -> listener.addedMonitoredRepositoryFromFile(modelRepositoryChanges));
    }
  }

  /**
   * Saves all model repository changes to given file.
   *
   * @param file The file to save to, must not be <code>null</code>
   * @throws IOException If anything went wrong saving to the file
   */
  public void saveToFile(File file) throws IOException {
    ChangeDataSetPersistenceHelper.save(file, new ArrayList<>(modelRepositoryChanges.values()));
  }
}
