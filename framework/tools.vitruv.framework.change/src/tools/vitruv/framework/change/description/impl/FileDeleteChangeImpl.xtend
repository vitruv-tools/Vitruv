package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.impl.AbstractFileChange
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.RootFactory
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

class FileDeleteChangeImpl extends AbstractFileChange {
	private static val logger = Logger.getLogger(FileDeleteChangeImpl);
	
	new(Resource changedFileResource) {
		super(changedFileResource)
	}
	
	override protected generateEChange(Resource resource) {
		if (0 < resource.getContents().size()) {
            val EObject rootElement = resource.getContents().get(0);
            val RemoveRootEObject<EObject> deleteRootObj = RootFactory.eINSTANCE.createRemoveRootEObject();
            deleteRootObj.setOldValue(rootElement);
            return deleteRootObj;
        }
        logger.info("Deleted resource " + VURI.getInstance(resource) + " did not contain any EObject");
        return null;
	}
	
}