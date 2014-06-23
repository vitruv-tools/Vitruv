package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.Collections;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;

class FileChangeSynchronizer extends ConcreteChangeSynchronizer {

    private static final Logger logger = Logger.getLogger(FileChangeSynchronizer.class.getSimpleName());

    public FileChangeSynchronizer(final ModelProviding modelProviding,
            final ConcreteChangeSynchronizer concreteChangeSynchronizer) {
        super(modelProviding, concreteChangeSynchronizer);
    }

    /**
     * If the change is a file created change then we create model instance in VSUM and start the
     * synchronizeChange process for the root element in the new model.
     * 
     * @param change
     *            The incoming change. It has to be an instanceof {@link FileChange} otherwise a
     *            class cast exception will occur.
     * @param sourceModelURI
     *            the model where the change occured
     */
    @Override
    public Set<VURI> synchronizeChange(final Change change, final VURI sourceModelURI) {
        FileChange fileChange = (FileChange) change;
        switch (fileChange.getFileChangeKind()) {
        case CREATE:
            return synchronizeFileCreated(sourceModelURI);
        case DELETE:
            return synchronizeFileDeleted(sourceModelURI);
        default:
            logger.warn("No change action for kind: " + fileChange.getFileChangeKind() + ". Change "
                    + change.toString() + " in source model " + sourceModelURI.toString() + " not synchronized.");
            return Collections.emptySet();
        }
    }

    private Set<VURI> synchronizeFileCreated(final VURI sourceModelURI) {
        ModelInstance newModelInstance = this.modelProviding.getModelInstanceOriginal(sourceModelURI);
        Resource resource = newModelInstance.getResource();
        EObject rootElement = null;
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException("The requested model instance resource '" + resource
                    + "' has to contain at most one root element "
                    + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() == null --> no element in newModelInstance
            logger.info("Empty model file created: " + sourceModelURI
                    + ". Synchronization for 'root element created' not triggerd.");
            return Collections.emptySet();
        }
        CreateRootEObject createRootEObj = ChangeFactory.eINSTANCE.createCreateRootEObject();
        createRootEObj.setChangedEObject(rootElement);
        EMFModelChange rootAdd = new EMFModelChange(createRootEObj);
        return syncChange(rootAdd, sourceModelURI);
    }

    private Set<VURI> synchronizeFileDeleted(final VURI sourceModelURI) {
        ModelInstance oldModelInstance = this.modelProviding.getModelInstanceOriginal(sourceModelURI);
        Resource resource = oldModelInstance.getResource();
        if (0 < resource.getContents().size()) {
            EObject rootElement = resource.getContents().get(0);
            DeleteNonRootEObject<EObject> deleteRootObj = ChangeFactory.eINSTANCE.createDeleteNonRootEObject();
            deleteRootObj.setNewValue(rootElement);
            EMFModelChange rootDeleted = new EMFModelChange(deleteRootObj);
            return syncChange(rootDeleted, sourceModelURI);
        }
        return Collections.emptySet();
    }
}
