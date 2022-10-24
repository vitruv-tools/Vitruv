package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.InternalUserInteractor;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationSpecificationProvider;
import tools.vitruv.change.propagation.impl.ChangePropagator;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeProvider;
import tools.vitruv.framework.views.ViewTypeRepository;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

public class VirtualModelImpl implements InternalVirtualModel {
	private static final Logger LOGGER = Logger.getLogger(VirtualModelImpl.class);

	private final ModelRepository resourceRepository;
	private final VersionedResourceRepositoryImpl versionedResourceRepository;
	private ModelResourcesInstance latestInstance;
	private final ViewTypeProvider viewTypeRepository;
	private final VsumFileSystemLayout fileSystemLayout;
	private final List<ChangePropagationListener> changePropagationListeners = new LinkedList<>();

	private final ChangePropagationSpecificationProvider changePropagationSpecificationProvider;
	private final InternalUserInteractor userInteractor;
	private ChangePropagationMode changePropagationMode = ChangePropagationMode.TRANSITIVE_CYCLIC;

	public VirtualModelImpl(VsumFileSystemLayout fileSystemLayout, InternalUserInteractor userInteractor,
			ViewTypeRepository viewTypeRepository,
			ChangePropagationSpecificationProvider changePropagationSpecificationProvider) {
		this.fileSystemLayout = fileSystemLayout;
		this.viewTypeRepository = viewTypeRepository;
		resourceRepository = new ResourceRepositoryImpl(fileSystemLayout);
		versionedResourceRepository = new VersionedResourceRepositoryImpl(fileSystemLayout);
		latestInstance = versionedResourceRepository.getLatestModelResourcesInstance();

		this.changePropagationSpecificationProvider = changePropagationSpecificationProvider;
		this.userInteractor = userInteractor;
	}
	
	private static final boolean useVersions = true;

	public void loadExistingModels() {
		resourceRepository.loadExistingModels();
	}

	@Override
	public synchronized EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
		if (useVersions) {
			return latestInstance.getCorrespondenceModel();
		}
		return resourceRepository.getCorrespondenceModel();
	}

	@Override
	public synchronized ModelInstance getModelInstance(URI modelUri) {
		if (useVersions) {
			return latestInstance.getModel(modelUri);
		}
		return resourceRepository.getModel(modelUri);
	}

	@Override
	public synchronized List<PropagatedChange> propagateChange(VitruviusChange change) {
		checkNotNull(change, "change to propagate");
		checkArgument(change.containsConcreteChange(), "This change contains no concrete change:%s%s",
				System.lineSeparator(), change);
		VitruviusChange unresolvedChange = change.unresolve();

		LOGGER.info("Starting change propagation");
		startChangePropagation(unresolvedChange);

		if (useVersions) {
			List<String> versionIdentifiers = versionedResourceRepository.getVersionIdentifiers();
			String versionIdentifier = versionIdentifiers.isEmpty() ? null : versionIdentifiers.get(versionIdentifiers.size() - 1);
			ModelResourcesInstance resourcesInstance = versionedResourceRepository.getModelResourcesInstance(versionIdentifier);
			resourcesInstance.loadExistingModels();
			ChangePropagator changePropagator = new ChangePropagator(resourcesInstance,
					changePropagationSpecificationProvider, userInteractor, changePropagationMode);
			List<PropagatedChange> result = changePropagator.propagateChange(unresolvedChange);
			versionedResourceRepository.commitChanges(result, resourcesInstance.getCorrespondenceModelInternal());
			resourcesInstance.saveOrDeleteModels();
			if (latestInstance != null) {
				latestInstance.close();
			}
			latestInstance = resourcesInstance;
			
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Propagated changes: " + result);
			}

			finishChangePropagation(unresolvedChange, result);
			LOGGER.info("Finished change propagation");
			return result;
		}
		
		ChangePropagator changePropagator = new ChangePropagator(resourceRepository,
				changePropagationSpecificationProvider, userInteractor, changePropagationMode);
		List<PropagatedChange> result = changePropagator.propagateChange(unresolvedChange);
		resourceRepository.saveOrDeleteModels();

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Propagated changes: " + result);
		}

		finishChangePropagation(unresolvedChange, result);
		LOGGER.info("Finished change propagation");
		return result;
	}

	private void startChangePropagation(VitruviusChange change) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Started synchronizing change: " + change);
		}
		changePropagationListeners.stream().forEach(it -> it.startedChangePropagation(change));
	}

	private void finishChangePropagation(VitruviusChange inputChange, Iterable<PropagatedChange> generatedChanges) {
		changePropagationListeners.stream().forEach(it -> it.finishedChangePropagation(generatedChanges));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Finished synchronizing change: " + inputChange);
		}
	}

	@Override
	public Path getFolder() {
		return fileSystemLayout.getVsumProjectFolder();
	}

	/**
	 * Registers the given {@link ChangePropagationListener}. The listener must not
	 * be <code>null</code>.
	 */
	@Override
	public synchronized void addChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.add(checkNotNull(propagationListener, "propagationListener"));
	}

	/**
	 * Unregisters the given {@link ChangePropagationListener}. The listener must
	 * not be <code>null</code>.
	 */
	@Override
	public synchronized void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.remove(checkNotNull(propagationListener, "propagationListener"));
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
			latestInstance.close();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		VirtualModelRegistry.getInstance().deregisterVirtualModel(this);
	}

	@Override
	public Collection<Resource> getViewSourceModels() {
		if (useVersions) {
			return latestInstance.getModelResources();
		}
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
	
	@Override
	public void validate(Consumer<ModelRepository> validator) {
		if (useVersions) {
			try (ModelResourcesInstance instance = versionedResourceRepository.getLatestModelResourcesInstance()) {
				validator.accept(instance);
			}
		}
		else {
			validator.accept(resourceRepository);
		}
	}
}
