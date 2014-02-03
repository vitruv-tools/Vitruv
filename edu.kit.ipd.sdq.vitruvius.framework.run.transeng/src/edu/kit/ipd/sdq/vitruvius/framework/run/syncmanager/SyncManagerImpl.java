package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.correspmmprovider.CorrespondenceMMProviderImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.metamodelmanager.MetamodelManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.mir.manager.MIRManager;
import edu.kit.ipd.sdq.vitruvius.framework.design.viewtype.manager.ViewTypeManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.PropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.SyncTransformationProviderImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class SyncManagerImpl implements ChangeSynchronizing {

    private static Logger logger = Logger.getLogger(SyncManagerImpl.class.getSimpleName());

    private final ModelProviding modelProviding;
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;

    private Map<Class<?>, ConcreteChangeSynchronizer> changeSynchonizerMap;

    private static SyncManagerImpl syncManagerImplInstance;

    private SyncManagerImpl(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
        this.changeSynchonizerMap = new HashMap<Class<?>, ConcreteChangeSynchronizer>();
        this.changeSynchonizerMap.put(EMFModelChange.class, new EMFModelSynchronizer(modelProviding, this,
                this.changePropagating, this.correspondenceProviding));
        this.changeSynchonizerMap.put(FileChange.class, new FileChangeSynchronizer(modelProviding, this));
    }

    @Override
    public void synchronizeChanges(final List<Change> changes, final VURI sourceModelURI) {
        for (Change change : changes) {
            synchronizeChange(change, sourceModelURI);
        }
    }

    @Override
    public void synchronizeChange(final Change change, final VURI sourceModelURI) {
        if (!this.changeSynchonizerMap.containsKey(change.getClass())) {
            logger.warn("Could not find ChangeSynchronizer for change " + change.getClass().getSimpleName()
                    + ". Can not synchronize change in source model " + sourceModelURI.toString() + " not synchroized.");
            return;
        }
        this.changeSynchonizerMap.get(change.getClass()).synchronizeChange(change, sourceModelURI);
    }

    public ModelProviding getModelProviding() {
        return this.modelProviding;
    }

    public static synchronized SyncManagerImpl getSyncManagerInstance() {
        if (null == syncManagerImplInstance) {
            // create vsum
            MetaRepositoryImpl mediaRepository = new MetaRepositoryImpl();
            MetamodelManagerImpl metaModelManager = new MetamodelManagerImpl(mediaRepository);
            ViewTypeManagerImpl viewTypeManager = new ViewTypeManagerImpl();
            CorrespondenceMMProviderImpl correspondenceProvider = new CorrespondenceMMProviderImpl();
            MIRManager mirManager = new MIRManager();
            VSUMImpl vsum = new VSUMImpl(metaModelManager, viewTypeManager, mirManager, correspondenceProvider);
            // create syncTransformationProvider
            SyncTransformationProviderImpl syncTransformationProvider = new SyncTransformationProviderImpl();
            PropagationEngineImpl propagatingChange = new PropagationEngineImpl(syncTransformationProvider);
            // create syncManager
            syncManagerImplInstance = new SyncManagerImpl(vsum, propagatingChange, vsum);
        }
        return syncManagerImplInstance;
    }

}
