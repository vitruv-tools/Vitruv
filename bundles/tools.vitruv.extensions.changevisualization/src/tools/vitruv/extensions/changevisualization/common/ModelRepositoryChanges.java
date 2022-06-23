package tools.vitruv.extensions.changevisualization.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.extensions.changevisualization.tree.decoder.TreeChangeDataSetDecoder;

public class ModelRepositoryChanges implements ChangePropagationListener, Serializable {
	private static final long serialVersionUID = 4992108309750505218L;

	private final String repositoryName;
	private final List<ChangeDataSet> changeDataSets = new ArrayList<>();
	private transient Set<ChangeDataSetGenerationListener> dataSetGenerationListener;
	private transient Set<ChangeableModelRepository> monitoredRepositories;
	
	public ModelRepositoryChanges(String repositoryName) {
		this.repositoryName = repositoryName;
	}
	
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
	
	public List<ChangeDataSet> getChangeDataSets() {
		return new ArrayList<>(changeDataSets);
	}

	public void startMonitoring(ChangeableModelRepository modelRepository) {
		getMonitoredRepositories().add(modelRepository); 
		modelRepository.addChangePropagationListener(this);
	}

	public void stopMonitoring() {
		getMonitoredRepositories().forEach(repository -> repository.removeChangePropagationListener(this));
		getMonitoredRepositories().clear();
	}

	@Override
	public void startedChangePropagation(VitruviusChange changeToPropagate) {
		// Do nothing
	}

	@Override
	public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
		addChanges(propagatedChanges);
	}

	private synchronized void addChanges(Iterable<PropagatedChange> changes) {
		String changeName = repositoryName + " Change " + (changeDataSets.size() + 1);
		ChangeDataSet extractedDataSet = new TreeChangeDataSetDecoder(changeName, changes).getChangeDataSet();
		changeDataSets.add(extractedDataSet);
		getDataSetGenerationListener().forEach(listener -> listener.changeDataSetGenerated(extractedDataSet));
	}

	public void registerChangeDataSetGenerationListener(ChangeDataSetGenerationListener changeDataSetGenerationListener) {
		getDataSetGenerationListener().add(changeDataSetGenerationListener);
	}

}
