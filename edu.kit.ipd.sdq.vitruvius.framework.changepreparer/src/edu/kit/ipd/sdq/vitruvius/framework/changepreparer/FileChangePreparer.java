package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory;

class FileChangePreparer extends ConcreteChangePreparer {

    private static final Logger logger = Logger.getLogger(FileChangePreparer.class.getSimpleName());

    private final ModelProviding modelProviding;

    FileChangePreparer(final ModelProviding modelProviding) {
        this.modelProviding = modelProviding;
    }

    /**
     * If the change is a file created change then we create model instance in VSUM and return the
     * emf Change for the file change
     *
     * @param change
     *            The incoming change. It has to be an instanceof {@link FileChange} otherwise a
     *            class cast exception will occur.
     */
    @Override
    public Change prepareChange(final Change change) {
        final FileChange fileChange = (FileChange) change;
        final VURI sourceModelURI = fileChange.getURI();
        switch (fileChange.getFileChangeKind()) {
        case CREATE:
            return this.prepareFileCreated(sourceModelURI);
        case DELETE:
            return this.prepareFileDeleted(sourceModelURI);
        default:
            logger.warn("No change action for kind: " + fileChange.getFileChangeKind() + ". Change " + change.toString()
                    + " in source model " + sourceModelURI.toString() + " not synchronized.");
            return change;
        }
    }

    private Change prepareFileCreated(final VURI sourceModelURI) {
        final ModelInstance newModelInstance = this.modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        final Resource resource = newModelInstance.getResource();
        EObject rootElement = null;
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException(
                    "The requested model instance resource '" + resource + "' has to contain at most one root element "
                            + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() == null --> no element in newModelInstance
            logger.info("Empty model file created: " + sourceModelURI
                    + ". Synchronization for 'root element created' not triggerd.");
            return null;
        }
        final InsertRootEObject<EObject> createRootEObj = RootFactory.eINSTANCE.createInsertRootEObject();
        createRootEObj.setNewValue(rootElement);
        final EMFModelChange rootAdd = new EMFModelChange(createRootEObj, sourceModelURI);
        return rootAdd;
    }

    private Change prepareFileDeleted(final VURI sourceModelURI) {
        final ModelInstance oldModelInstance = this.modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        final Resource resource = oldModelInstance.getResource();
        if (0 < resource.getContents().size()) {
            final EObject rootElement = resource.getContents().get(0);
            final RemoveRootEObject<EObject> deleteRootObj = RootFactory.eINSTANCE.createRemoveRootEObject();
            deleteRootObj.setOldValue(rootElement);
            final EMFModelChange rootDeleted = new EMFModelChange(deleteRootObj, sourceModelURI);
            return rootDeleted;
        }
        logger.info("Deleted resource " + sourceModelURI + " did not contain any EObject");
        return null;
    }
}
