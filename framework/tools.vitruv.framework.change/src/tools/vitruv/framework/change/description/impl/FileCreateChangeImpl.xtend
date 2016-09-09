package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.impl.AbstractFileChange
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RootFactory
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger

class FileCreateChangeImpl extends AbstractFileChange {
	private static val logger = Logger.getLogger(FileCreateChangeImpl);
	
	new(Resource changedFileResource) {
		super(changedFileResource)
	}
	
	override protected generateEChange(Resource resource) {
		 var EObject rootElement = null;
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException(
                    "The requested model instance resource '" + resource + "' has to contain at most one root element "
                            + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() == null --> no element in newModelInstance
            logger.info("Empty model file created: " + VURI.getInstance(resource)
                    + ". Synchronization for 'root element created' not triggered.");
            return null;
        }
        val InsertRootEObject<EObject> createRootEObj = RootFactory.eINSTANCE.createInsertRootEObject();
        createRootEObj.setNewValue(rootElement);
        return createRootEObj;
	}
	
}