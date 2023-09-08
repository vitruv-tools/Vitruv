package tools.vitruv.framework.remote.common.util;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.propagation.ProjectMarker;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;

/**
 * Contains functions to transform ids used by the vitruv framework to identify
 * {@link org.eclipse.emf.ecore.EObject EObjects}.
 */
public class IdTransformation {

    private URI root;
    
    IdTransformation(Path vsumPath) {
    	root = URI.createFileURI(ProjectMarker.getProjectRootFolder(vsumPath).toString());
    	
    	var nextToCheck = vsumPath;
    	while ((nextToCheck = nextToCheck.getParent()) != null) {
			try {
				root = URI.createFileURI(ProjectMarker.getProjectRootFolder(nextToCheck).toString());
			} catch (IllegalStateException e) {
				break;
			}
		}
    }

    /**
     * Transforms the given global (absolute path) id to a local id (relative path).
     *
     * @param global the id to transform
     * @return the local id
     */
    public URI toLocal(URI global) {
        if (global == null || global.toString().contains("cache") || 
        		global.toString().equals(JsonFieldName.TEMP_VALUE) || !global.isFile()) {
            return global;
        }
        
        return URI.createURI(global.toString().replace(root.toString(), ""));
    }

    /**
     * Transforms the given local id (relative path) to a global id (absolute path).
     *
     * @param local the id to transform
     * @return the global id
     */
    public URI toGlobal(URI local) {
        if (local == null || local.toString().contains("cache") || 
        		local.toString().equals(JsonFieldName.TEMP_VALUE)) {
            return local;
        }
        
        if (!local.isRelative()) {
			return local;
		}
        
        return URI.createURI(root.toString() + local.toString());
    }

    public void allToGlobal(List<? extends EChange<HierarchicalId>> eChanges) {
        for (var eChange : eChanges) {
            if (eChange instanceof RootEChange<?> change) {
                change.setUri(toGlobal(URI.createURI(change.getUri())).toString());
            }
        } 
    }
    
    public void allToLocal(List<? extends EChange<HierarchicalId>> eChanges) {
        for (var eChange : eChanges) {
            if (eChange instanceof RootEChange<?> change) {
                change.setUri(toLocal(URI.createURI(change.getUri())).toString());
            }
        } 
    }
}
