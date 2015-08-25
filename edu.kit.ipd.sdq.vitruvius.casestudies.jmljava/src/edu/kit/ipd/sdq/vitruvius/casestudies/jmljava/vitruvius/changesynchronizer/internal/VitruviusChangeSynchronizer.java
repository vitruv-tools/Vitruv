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
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.Change2CommandTransformingProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.CorrespondenceProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.MappingProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.commandexecuter.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;

/**
 * Wrapper for the Vitruvius synchronization framework. It initializes the framework by using the
 * edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.* extension
 * points.
 */
public class VitruviusChangeSynchronizer implements ChangeSynchronizing {

    private static final Logger LOGGER = Logger.getLogger(VitruviusChangeSynchronizer.class);

    protected final MetaRepositoryImpl metaRepositoryImpl;
    protected final VSUMImplCustom vsumImpl;
    protected final Change2CommandTransformingProvidingImplCustom change2CommandTransformingProvidingImpl;
    protected final ChangeSynchronizerImpl syncManagerImpl;

    /**
     * Constructor.
     */
    public VitruviusChangeSynchronizer() {
        LOGGER.info("Initializing change synchronizer.");
        try {
            this.deleteExistingVSUM();
            this.metaRepositoryImpl = this.constructMetaRepositoryImpl();
            this.vsumImpl = this.constructVSUMImpl(this.metaRepositoryImpl, this.metaRepositoryImpl);
            this.change2CommandTransformingProvidingImpl = this.constructChange2CommandTransformingImpl();
            final CommandExecuting commandExectuing = new CommandExecutingImpl();
            final ChangePreparing changePreparing = new ChangePreparingImpl(this.vsumImpl, this.vsumImpl);
            this.syncManagerImpl = this.constructSyncManagerImpl(this.vsumImpl,
                    this.change2CommandTransformingProvidingImpl, this.vsumImpl, this.vsumImpl, changePreparing,
                    commandExectuing);
        } catch (final Exception e) {
            LOGGER.error("Exception thrown during initialization.", e);
            throw e;
        }
    }

    /**
     * Deletes the serialized information of the VSUM. This is necessary since it is build from
     * scratch on every construction in order to reflect the most recent state.
     */
    private void deleteExistingVSUM() {
        try {
            final IWorkspace workspace = ResourcesPlugin.getWorkspace();
            final IProject project = workspace.getRoot().getProject(VSUMConstants.VSUM_PROJECT_NAME);
            project.delete(true, true, new NullProgressMonitor());
            LOGGER.info("Deleted old VSUM project.");
        } catch (final NullPointerException e) {
            LOGGER.info("No old VSUM project found.");
        } catch (final CoreException e) {
            LOGGER.error("Could not delete old VSUM project.");
        }
    }

    /**
     * Constructs the meta repository by using the meta-model and mapping providers.
     *
     * @return The initialized meta-repository.
     */
    protected MetaRepositoryImpl constructMetaRepositoryImpl() {
        LOGGER.info("Constructing meta repository.");

        final MetaRepositoryImpl result = new MetaRepositoryImpl();

        for (final MetaModelProvider mmProvider : JavaJMLExtensionProvider.getMetaModelProvider()) {
            final Metamodel mm = mmProvider.getMetaModel();
            result.addMetamodel(mm);
            LOGGER.debug("Meta model registered: " + mm.getURI());
        }

        for (final MappingProvider mappingProvider : JavaJMLExtensionProvider.getMappingProviders()) {
            for (final Mapping mapping : mappingProvider.getMappings(result)) {
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
    protected VSUMImplCustom constructVSUMImpl(final MetamodelManaging metamodelManaging,
            final MappingManaging mappingManaging) {
        LOGGER.info("Constructing VSUM.");
        final VSUMImplCustom result = new VSUMImplCustom(metamodelManaging, null, mappingManaging);

        final List<VURI> relevantURIs = new ArrayList<VURI>();
        for (final ModelURIProvider modelURIProvider : JavaJMLExtensionProvider.getModelURIProviders()) {
            final List<VURI> providedRelevantURIs = modelURIProvider.getAllRelevantURIs();
            relevantURIs.addAll(providedRelevantURIs);
            for (final VURI uri : providedRelevantURIs) {
                result.getAndLoadModelInstanceOriginal(uri);
            }
        }
        LOGGER.debug("Found " + relevantURIs.size() + " relevant model URIs.");

        for (final CorrespondenceProvider correspondenceProvider : JavaJMLExtensionProvider
                .getCorrespondenceProviders()) {
            final CorrespondenceInstance ci = result.getCorrespondenceInstanceOriginal(
                    correspondenceProvider.getFirstMetaModelUri(), correspondenceProvider.getSecondMetaModelUri());

            LOGGER.debug("Setting correspondences for " + correspondenceProvider.getFirstMetaModelUri() + " and "
                    + correspondenceProvider.getSecondMetaModelUri());

            correspondenceProvider.setAllCorrespondences(ci, relevantURIs, result, metamodelManaging);
        }

        return result;
    }

    /**
     * Constructs a transformation executing provider and initializes it by using an extension
     * point.
     *
     * @return The initialized transformation executing provider.
     */
    protected Change2CommandTransformingProvidingImplCustom constructChange2CommandTransformingImpl() {
        LOGGER.info("Constructing transformation executing providing.");

        final Change2CommandTransformingProvidingImplCustom result = new Change2CommandTransformingProvidingImplCustom();

        for (final Change2CommandTransformingProvider transProvider : JavaJMLExtensionProvider
                .getEMFModelTransformationExecutingProviders()) {
            for (final Change2CommandTransforming trans : transProvider.getEMFModelTransformationExecutings()) {
                result.addChange2CommandTransforming(trans);
                LOGGER.debug("EMFModelTransformationExecuting registered: " + trans.getClass().getSimpleName());
            }
        }

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
    protected ChangeSynchronizerImpl constructSyncManagerImpl(final ModelProviding modelProviding,
            final Change2CommandTransformingProviding change2CommandTransformingProviding,
            final CorrespondenceProviding correspondenceProviding, final Validating validating,
            final ChangePreparing changePreparing, final CommandExecuting commandExecuting) {
        LOGGER.info("Constructing sync manager.");

        final ChangeSynchronizerImpl result = new ChangeSynchronizerImpl(modelProviding,
                change2CommandTransformingProviding, correspondenceProviding, this.metaRepositoryImpl, validating, null,
                changePreparing, commandExecuting);

        return result;
    }

    @Override
    public void synchronizeChanges(final List<Change> changes) {
        if (changes == null) {
            LOGGER.warn("Ignoring null changes.");
            return;
        }

        try {
            this.syncManagerImpl.synchronizeChanges(changes);
        } catch (final Exception e) {
            LOGGER.error("Exception thrown during synchronisation.", e);
        }
    }

    @Override
    public void synchronizeChange(final Change change) {
        if (change == null) {
            LOGGER.warn("Ignoring null change.");
            return;
        }

        try {
            this.syncManagerImpl.synchronizeChange(change);

        } catch (final Exception e) {
            LOGGER.error("Exception thrown during synchronisation.", e);
        }
    }

    public ModelProvidingDirtyMarker getModelProvidingDirtyMarker() {
        return this.vsumImpl;
    }

}
