package tools.vitruv.change.testutils.changevisualization;

import tools.vitruv.change.testutils.changevisualization.common.ModelRepositoryChanges;

/**
 * Listener for adding a monitored repository.
 *
 * @see ModelRepositoryChanges
 * @see tools.vitruv.change.testutils.changevisualization.common.ModelRepositoryChanges
 * @see tools.vitruv.change.testutils.changevisualization.common.ModelRepositoryChanges
 */
public interface MonitoredRepositoryAddedListener {
  /**
   * Adds a monitored repository.
   *
   * @param newModelRepositoryChanges The new monitored repository.
   */
  public void addedMonitoredRepository(ModelRepositoryChanges newModelRepositoryChanges);

  /**
   * Adds a monitored repository from a file.
   *
   * @param newModelRepositoryChanges The new monitored repository.
   */
  public void addedMonitoredRepositoryFromFile(ModelRepositoryChanges newModelRepositoryChanges);
}
