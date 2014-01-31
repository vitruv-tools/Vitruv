package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
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
    private final ModelProviding modelProviding;
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;
    private final CorrespondenceMMProviding correspondenceMMProviding;

    private static SyncManagerImpl syncManagerImplInstance;

    private SyncManagerImpl(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding,
            final CorrespondenceMMProviding correspondenceMMProviding) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
        this.correspondenceMMProviding = correspondenceMMProviding;
    }

    @Override
    public void synchronizeChanges(final List<Change> changes, final VURI sourceModelURI) {
        for (Change change : changes) {
            synchronizeChange(change, sourceModelURI);
        }
    }

    @Override
    public void synchronizeChange(final Change change, final VURI sourceModelURI) {
        ModelInstance model = this.modelProviding.getModelInstanceOriginal(sourceModelURI);

        // CorrespondenceMM correspondenceMM =
        // this.correspondenceMMProviding.getCorrespondenceMM(null, uriMM2)
        // CorrespondenceModelInstance correspondenceModelInstance = this.correspondenceProviding
        // .getCorrespondenceInstance(sourceModel.getURI(), model2URI);
        // this.changePropagating.propagateChange(change, sourceModel, correspondenceModelInstance,
        // targetModel);
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
            syncManagerImplInstance = new SyncManagerImpl(vsum, propagatingChange, vsum, correspondenceProvider);
        }
        return syncManagerImplInstance;
    }

}
