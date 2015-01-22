package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.JavaJMLExtensionProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.CorrespondenceProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.EMFModelTransformationExecutingProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.MappingProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.utils.EclipseUtilities;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecutingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;

/**
 * Wrapper for the Vitruvius synchronization framework. It initializes the
 * framework by using the edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.*
 * extension points.
 */
public class VitruviusChangeSynchronizer implements ChangeSynchronizing {

	private static final Logger LOGGER = Logger
			.getLogger(VitruviusChangeSynchronizer.class);

	protected final MetaRepositoryImpl metaRepositoryImpl;
	protected final VSUMImplCustom vsumImpl;
	protected final TransformationExecutingProvidingImplCustom transformationExecutingProvidingImpl;
	protected final EMFModelPropagationEngineImpl propagationEngineImpl;
	protected final SyncManagerImpl syncManagerImpl;

	/**
	 * Constructor.
	 */
	public VitruviusChangeSynchronizer() {
		LOGGER.info("Initializing change synchronizer.");
		try {
			deleteExistingVSUM();
			metaRepositoryImpl = constructMetaRepositoryImpl();
			vsumImpl = constructVSUMImpl(metaRepositoryImpl, metaRepositoryImpl);
			transformationExecutingProvidingImpl = constructTransformationExecutingProvidingImpl();
			propagationEngineImpl = constructPropagationEngineImpl(transformationExecutingProvidingImpl);
			syncManagerImpl = constructSyncManagerImpl(vsumImpl,
					propagationEngineImpl, vsumImpl, vsumImpl);
		} catch (Exception e) {
			LOGGER.error("Exception thrown during initialization.", e);
			throw e;
		}
	}

	/**
	 * Deletes the serialized information of the VSUM. This is necessary since
	 * it is build from scratch on every construction in order to reflect the
	 * most recent state.
	 */
	private void deleteExistingVSUM() {
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject project = workspace.getRoot().getProject(
					VSUMConstants.VSUM_PROJECT_NAME);
			project.delete(true, true, new NullProgressMonitor());
			LOGGER.info("Deleted old VSUM project.");
		} catch (NullPointerException e) {
			LOGGER.info("No old VSUM project found.");
		} catch (CoreException e) {
			LOGGER.error("Could not delete old VSUM project.");
		}
	}

	/**
	 * Constructs the meta repository by using the meta-model and mapping
	 * providers.
	 * 
	 * @return The initialized meta-repository.
	 */
	protected MetaRepositoryImpl constructMetaRepositoryImpl() {
		LOGGER.info("Constructing meta repository.");

		MetaRepositoryImpl result = new MetaRepositoryImpl();

		for (MetaModelProvider mmProvider : JavaJMLExtensionProvider.getMetaModelProvider()) {
			Metamodel mm = mmProvider.getMetaModel();
			result.addMetamodel(mm);
			LOGGER.debug("Meta model registered: " + mm.getURI());
		}

		for (MappingProvider mappingProvider : JavaJMLExtensionProvider.getMappingProviders()) {
			for (Mapping mapping : mappingProvider.getMappings(result)) {
				result.addMapping(mapping);
				LOGGER.debug("Mapping registered: " + mapping);
			}
		}

		return result;
	}

	/**
	 * Constructs the VSUM and initializes it with models and correspondences.
	 * 
	 * @param metamodelManaging
	 *            An initialized implementation of a meta-model managing.
	 * @param mappingManaging
	 *            An initialized implementation of a mapping managing.
	 * @return The initialized VSUM.
	 */
	protected VSUMImplCustom constructVSUMImpl(
			final MetamodelManaging metamodelManaging,
			final MappingManaging mappingManaging) {
		LOGGER.info("Constructing VSUM.");
		VSUMImplCustom result = new VSUMImplCustom(metamodelManaging, null,
				mappingManaging);

		final List<VURI> relevantURIs = new ArrayList<VURI>();
		for (ModelURIProvider modelURIProvider : JavaJMLExtensionProvider.getModelURIProviders()) {
			List<VURI> providedRelevantURIs = modelURIProvider
					.getAllRelevantURIs();
			relevantURIs.addAll(providedRelevantURIs);
			for (VURI uri : providedRelevantURIs) {
				result.getAndLoadModelInstanceOriginal(uri);
			}
		}
		LOGGER.debug("Found " + relevantURIs.size() + " relevant model URIs.");

		for (CorrespondenceProvider correspondenceProvider : JavaJMLExtensionProvider.getCorrespondenceProviders()) {
			CorrespondenceInstance ci = result
					.getCorrespondenceInstanceOriginal(
							correspondenceProvider.getFirstMetaModelUri(),
							correspondenceProvider.getSecondMetaModelUri());

			LOGGER.debug("Setting correspondences for "
					+ correspondenceProvider.getFirstMetaModelUri() + " and "
					+ correspondenceProvider.getSecondMetaModelUri());

			correspondenceProvider.setAllCorrespondences(ci, relevantURIs,
					result, metamodelManaging);
		}

		return result;
	}

	/**
	 * Constructs a transformation executing provider and initializes it by
	 * using an extension point.
	 * 
	 * @return The initialized transformation executing provider.
	 */
	protected TransformationExecutingProvidingImplCustom constructTransformationExecutingProvidingImpl() {
		LOGGER.info("Constructing transformation executing providing.");

		TransformationExecutingProvidingImplCustom result = new TransformationExecutingProvidingImplCustom();

		for (EMFModelTransformationExecutingProvider transProvider : JavaJMLExtensionProvider.getEMFModelTransformationExecutingProviders()) {
			for (EMFModelTransformationExecuting trans : transProvider
					.getEMFModelTransformationExecutings()) {
				result.addEMFModelTransformationExecuting(trans);
				LOGGER.debug("EMFModelTransformationExecuting registered: "
						+ trans.getClass().getSimpleName());
			}
		}

		return result;
	}

	/**
	 * Constructs the propagation engine and initializes it.
	 * 
	 * @param syncTransformationProviding
	 *            An initialized implementation of a transformation executing
	 *            provider.
	 * @return The initialized propagation engine.
	 */
	protected EMFModelPropagationEngineImpl constructPropagationEngineImpl(
			final TransformationExecutingProviding syncTransformationProviding) {
		LOGGER.info("Constructing propagation engine.");

		EMFModelPropagationEngineImpl result = new EMFModelPropagationEngineImpl(
				syncTransformationProviding);

		return result;
	}

	/**
	 * Constructs and initializes a sync manager.
	 * 
	 * @param modelProviding
	 *            An initialized implementation of a model providing.
	 * @param changePropagating
	 *            An initialized implementation of a change propagating.
	 * @param correspondenceProviding
	 *            An initialized implementation of a change providing.
	 * @param validating
	 *            An initialized implementation of a validating.
	 * @return An initialized sync manager.
	 */
	protected SyncManagerImpl constructSyncManagerImpl(
			final ModelProviding modelProviding,
			final ChangePropagating changePropagating,
			final CorrespondenceProviding correspondenceProviding,
			final Validating validating) {
		LOGGER.info("Constructing sync manager.");

		SyncManagerImpl result = new SyncManagerImpl(modelProviding,
				changePropagating, correspondenceProviding, null, validating);

		return result;
	}

	@Override
	public void synchronizeChanges(List<Change> changes) {
		if (changes == null) {
			LOGGER.warn("Ignoring null changes.");
			return;
		}

		try {
			syncManagerImpl.synchronizeChanges(changes);
		} catch (Exception e) {
			LOGGER.error("Exception thrown during synchronisation.", e);
		}
	}

	@Override
	public void synchronizeChange(Change change) {
		if (change == null) {
			LOGGER.warn("Ignoring null change.");
			return;
		}

		try {
			syncManagerImpl.synchronizeChange(change);

		} catch (Exception e) {
			LOGGER.error("Exception thrown during synchronisation.", e);
		}
	}
	
	public ModelProvidingDirtyMarker getModelProvidingDirtyMarker() {
		return vsumImpl;
	}

}
