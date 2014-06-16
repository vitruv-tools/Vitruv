package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.InvariantProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.correspmmprovider.CorrespondenceMMProviderImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.metamodelmanager.MetamodelManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.viewtype.manager.ViewTypeManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.PropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class SyncManagerImpl implements ChangeSynchronizing {

    private static Logger logger = Logger.getLogger(SyncManagerImpl.class.getSimpleName());

    private final ModelProviding modelProviding;
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;
    private final InvariantProviding invariantProviding;
    private final Validating validating;

    private Map<Class<?>, ConcreteChangeSynchronizer> changeSynchonizerMap;

    // FIXME: use correct instantiation instead of static instantiation
    private static SyncManagerImpl syncManagerImplInstance;
    private static MetaRepositoryImpl metaRepositoryImpl;

    private SyncManagerImpl(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding, final InvariantProviding invariantProviding,
            final Validating validating) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
        this.changeSynchonizerMap = new HashMap<Class<?>, ConcreteChangeSynchronizer>();
        this.changeSynchonizerMap.put(EMFModelChange.class, new EMFModelSynchronizer(modelProviding, this,
                this.changePropagating, this.correspondenceProviding));
        this.changeSynchonizerMap.put(FileChange.class, new FileChangeSynchronizer(modelProviding, this));
        this.invariantProviding = invariantProviding;
        this.validating = validating;
    }

    @Override
    public Set<VURI> synchronizeChanges(final List<Change> changes, final VURI sourceModelURI) {
        Set<VURI> changedVURIs = new HashSet<VURI>();
        for (Change change : changes) {
            changedVURIs.addAll(synchronizeChange(change, sourceModelURI));
        }
        return changedVURIs;
    }

    @Override
    public Set<VURI> synchronizeChange(final Change change, final VURI sourceModelURI) {
        if (!this.changeSynchonizerMap.containsKey(change.getClass())) {
            logger.warn("Could not find ChangeSynchronizer for change " + change.getClass().getSimpleName()
                    + ". Can not synchronize change in source model " + sourceModelURI.toString() + " not synchroized.");
            return Collections.emptySet();
        }
        // TODO: extend synchronizeChange: Should return changed EObjects/ChangedModels
        return this.changeSynchonizerMap.get(change.getClass()).synchronizeChange(change, sourceModelURI);
        // TODO: Check invariants:
        // Get invariants from Invariant providing
        // Validate models with Validating
        // TODO: Execute responses for violated invariants --> Classified response actions
    }

    public ModelProviding getModelProviding() {
        return this.modelProviding;
    }

    public static synchronized SyncManagerImpl getSyncManagerInstance() {
        if (null == syncManagerImplInstance) {
            // create vsum
            if (null == metaRepositoryImpl) {
                setMetaRepositoryImpl(new MetaRepositoryImpl());
            }
            MetamodelManagerImpl metaModelManager = new MetamodelManagerImpl(metaRepositoryImpl);
            ViewTypeManagerImpl viewTypeManager = new ViewTypeManagerImpl();
            CorrespondenceMMProviderImpl correspondenceProvider = new CorrespondenceMMProviderImpl();
            VSUMImpl vsum = new VSUMImpl(metaModelManager, viewTypeManager, metaRepositoryImpl, correspondenceProvider);
            // create syncTransformationProvider
            TransformationExecutingProvidingImpl syncTransformationProvider = new TransformationExecutingProvidingImpl();
            PropagationEngineImpl propagatingChange = new PropagationEngineImpl(syncTransformationProvider);
            // create dummy invariant and dummy validating providing
            InvariantProviding iv = new InvariantProviding() {
                @Override
                public Invariants getInvariants(final VURI mmURI) {
                    throw new RuntimeException("dummy of getInvariants called. Use real InvariantProviding");
                }
            };
            Validating validating = new Validating() {

                @Override
                public boolean validate(final ModelInstance modelInstanceA, final ModelInstance modelInstanceB,
                        final Invariants invariants) {
                    throw new RuntimeException("dummy of Validating called. Use real InvariantProviding");
                }

                @Override
                public boolean validate(final ModelInstance modelInstance, final Invariants invariants) {
                    throw new RuntimeException("dummy of Validating called. Use real InvariantProviding");
                }

                @Override
                public boolean validate(final Invariants invariants) {
                    throw new RuntimeException("dummy of Validating called. Use real InvariantProviding");
                }
            };

            // create syncManager
            syncManagerImplInstance = new SyncManagerImpl(vsum, propagatingChange, vsum, iv, validating);
        }
        return syncManagerImplInstance;
    }

    public static void setMetaRepositoryImpl(final MetaRepositoryImpl metaRepositoryImpl) {
        SyncManagerImpl.metaRepositoryImpl = metaRepositoryImpl;
    }

}
