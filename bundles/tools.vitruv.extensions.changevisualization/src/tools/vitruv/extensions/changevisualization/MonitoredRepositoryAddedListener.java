package tools.vitruv.extensions.changevisualization;

import tools.vitruv.extensions.changevisualization.common.ModelRepositoryChanges;

public interface MonitoredRepositoryAddedListener {
	public void addedMonitoredRepository(ModelRepositoryChanges newModelRepositoryChanges);
	public void addedMonitoredRepositoryFromFile(ModelRepositoryChanges newModelRepositoryChanges);
}
