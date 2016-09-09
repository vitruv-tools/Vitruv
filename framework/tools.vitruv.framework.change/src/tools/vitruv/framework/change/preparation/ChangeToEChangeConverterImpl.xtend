package tools.vitruv.framework.change.preparation

import org.apache.log4j.Logger
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.RootFactory
import tools.vitruv.framework.change.description.EMFModelChange
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.description.FileChange

class ChangeToEChangeConverterImpl implements ChangeToEChangeConverter {
	private static Logger logger = Logger.getLogger(ChangeToEChangeConverterImpl);
	
	override List<EChange> calculateEChangesForChange(EMFModelChange modelChange) {
		return new ChangeDescription2EChangesTransformation(modelChange.changeDescription).transform()
	}
	
	override List<EChange> calculateEChangesForChange(FileChange fileChange) {
		switch (fileChange.getFileChangeKind()) {
        case CREATE:
            return this.prepareFileCreated(fileChange.changedFileResource)
        case DELETE:
            return this.prepareFileDeleted(fileChange.changedFileResource)
        }
	}
	
	private def List<EChange> prepareFileCreated(Resource resource) {
        var EObject rootElement = null;
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException(
                    "The requested model instance resource '" + resource + "' has to contain at most one root element "
                            + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() == null --> no element in newModelInstance
            logger.info("Empty model file created: " + VURI.getInstance(resource)
                    + ". Synchronization for 'root element created' not triggerd.");
            return null;
        }
        val InsertRootEObject<EObject> createRootEObj = RootFactory.eINSTANCE.createInsertRootEObject();
        createRootEObj.setNewValue(rootElement);
        return #[createRootEObj];
    }

    private def List<EChange> prepareFileDeleted(Resource resource) {
        if (0 < resource.getContents().size()) {
            val EObject rootElement = resource.getContents().get(0);
            val RemoveRootEObject<EObject> deleteRootObj = RootFactory.eINSTANCE.createRemoveRootEObject();
            deleteRootObj.setOldValue(rootElement);
            return #[deleteRootObj];
        }
        logger.info("Deleted resource " + VURI.getInstance(resource) + " did not contain any EObject");
        return null;
    }
	
}