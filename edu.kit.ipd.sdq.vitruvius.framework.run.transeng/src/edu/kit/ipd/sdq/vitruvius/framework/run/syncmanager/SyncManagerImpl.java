package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
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
    private final ModelProviding modelProviding;
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;

    private static SyncManagerImpl syncManagerImplInstance;

    private SyncManagerImpl(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
    }

    @Override
    public void synchronizeChanges(final List<Change> changes, final VURI sourceModelURI) {
        for (Change change : changes) {
            synchronizeChange(change, sourceModelURI);
        }
    }

    @Override
    public void synchronizeChange(final Change change, final VURI sourceModelURI) {
        if (change instanceof EMFModelChange) {
            synchronizeChange((EMFModelChange) change, sourceModelURI);
        } else if (change instanceof FileChange) {
            synchronizeChange((FileChange) change, sourceModelURI);
        }

    }

    // if the change is a file created change then
    // VSUM.getModelInstance(); // also creates all correspondence models
    // syncRootCreatedChange:
    // EObject rootElement = null;
    // if (resourceContents.size() == 1) {
    // rootElement = resourceContents.get(0);
    // } else if (resourceContents.size() > 1) {
    // throw new RuntimeException("The requested model instance resource '" + resource
    // + "' has to contain at most one root element "
    // + "in order to be added to the VSUM without an explicit import!");
    // }
    // importSoloRoot
    /**
     * If the change is a file created change then we create model instance in VSUM
     * 
     * @param change
     * @param sourceModelURI
     */
    private void synchronizeChange(final FileChange change, final VURI sourceModelURI) {
        // if (KIND.CREATE == change.getKind()) {
        synchronizeFileCreated(sourceModelURI);
        // }

    }

    private void synchronizeFileCreated(final VURI sourceModelURI) {
        ModelInstance newModelInstance = this.modelProviding.getModelInstanceOriginal(sourceModelURI);
        Resource resource = newModelInstance.getResource();
        EObject rootElement = null;
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException("The requested model instance resource '" + resource
                    + "' has to contain at most one root element "
                    + "in order to be added to the VSUM without an explicit import!");
        }
        EMFModelChange rootChange = new EMFModelChange();
        synchronizeChange(rootChange, sourceModelURI);
    }

    private void synchronizeChange(final EMFModelChange change, final VURI sourceModelURI) {
        ModelInstance sourceModel = this.modelProviding.getModelInstanceOriginal(sourceModelURI);
        Set<CorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getAllCorrespondenceInstances(sourceModelURI);
        for (CorrespondenceInstance correspondenceInstance : correspondenceInstances) {
            this.changePropagating.propagateChange(change, sourceModel, correspondenceInstance);
        }
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
