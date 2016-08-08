package edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.FileChange

class ChangePreparingImpl implements ChangePreparing {
	private static Logger logger = Logger.getLogger(ChangePreparingImpl);
	private val ModelProviding modelProviding;
	
	public new(ModelProviding modelProviding) {
		this.modelProviding = modelProviding;
	}
	
	override List<EChange> prepareChange(Change unpreparedChange) {
		prepareTypedChange(unpreparedChange);
	}
	
	private def dispatch List<EChange> prepareTypedChange(Change unpreparedChange) {
		throw new UnsupportedOperationException("Changes of type " + unpreparedChange.class + " cannot be handled.")
	}
	
	private def dispatch List<EChange> prepareTypedChange(EMFModelChange modelChange) {
		return new ChangeDescription2EChangesTransformation(modelChange.changeDescription).transform()
	}
	
	private def dispatch List<EChange> prepareTypedChange(FileChange fileChange) {
        val VURI sourceModelURI = fileChange.getURI();
        switch (fileChange.getFileChangeKind()) {
        case CREATE:
            return this.prepareFileCreated(sourceModelURI)
        case DELETE:
            return this.prepareFileDeleted(sourceModelURI)
        }
	}
	
	private def List<EChange> prepareFileCreated(VURI sourceModelURI) {
        val ModelInstance newModelInstance = modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        val Resource resource = newModelInstance.getResource();
        var EObject rootElement = null;
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
        val InsertRootEObject<EObject> createRootEObj = RootFactory.eINSTANCE.createInsertRootEObject();
        createRootEObj.setNewValue(rootElement);
        return #[createRootEObj];
    }

    private def List<EChange> prepareFileDeleted(VURI sourceModelURI) {
        val ModelInstance oldModelInstance = modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        val Resource resource = oldModelInstance.getResource();
        if (0 < resource.getContents().size()) {
            val EObject rootElement = resource.getContents().get(0);
            val RemoveRootEObject<EObject> deleteRootObj = RootFactory.eINSTANCE.createRemoveRootEObject();
            deleteRootObj.setOldValue(rootElement);
            return #[deleteRootObj];
        }
        logger.info("Deleted resource " + sourceModelURI + " did not contain any EObject");
        return null;
    }
	
}