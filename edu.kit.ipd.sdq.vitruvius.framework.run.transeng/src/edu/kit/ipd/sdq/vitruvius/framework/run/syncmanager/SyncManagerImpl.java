package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SyncTransformationProviding;
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

    private static SyncManagerImpl syncManagerImplInstance;

    private SyncManagerImpl(ModelProviding modelProviding, ChangePropagating changePropagating) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
    }

    @Override
    public void synchronizeChange(Change change, ModelInstance sourceModel) {
        // TODO Auto-generated method stub
        /*ModelInstance model = modelProviding.getModelInstanceOriginal(sourceModel.getVURI());
        changePropagating.propagateChange(change, model, correspondenceModelInstance, model);*/
    }

    public ModelProviding getModelProviding() {
        return modelProviding;
    }

    public static synchronized SyncManagerImpl getSyncManagerInstance() {
        if(null == syncManagerImplInstance){
            //create vsum
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
            syncManagerImplInstance = new SyncManagerImpl(vsum, propagatingChange);
        }
        return syncManagerImplInstance;
    }

}
