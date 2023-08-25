package tools.vitruv.framework.remote.common.util;

import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.List;

import com.google.common.base.Charsets;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.propagation.ProjectMarker;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;

/**
 * Contains functions to transform ids used by the vitruv framework to identify
 * {@link org.eclipse.emf.ecore.EObject EObjects}.
 */
public class IdTransformation {

    private IdTransformation() {
        throw new UnsupportedOperationException("Utility Class Constructor!");
    }
    
    private static Path root;
    
    public static void initializeRootFolder(Path vsumPath) {
    	root = ProjectMarker.getProjectRootFolder(vsumPath);
    	
    	var nextToCheck = vsumPath;
    	while ((nextToCheck = nextToCheck.getParent()) != null) {
			try {
				root = ProjectMarker.getProjectRootFolder(nextToCheck);
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
    public static String toLocal(String global) {
        if (global == null || global.contains("cache") || global.equals(JsonFieldName.TEMP_VALUE)) {
            return global;
        }

        return root.toAbsolutePath().relativize(
        		Path.of(removeFilePrefix(URLDecoder.decode(global, Charsets.UTF_8)))).toString().replace("\\", "/");
    }

    /**
     * Transforms the given local id (relative path) to a global id (absolute path).
     *
     * @param local the id to transform
     * @return the global id
     */
    public static String toGlobal(String local) {
        if (local == null || local.contains("cache") || local.equals(JsonFieldName.TEMP_VALUE)) {
            return local;
        }
        var path = Path.of(removeFilePrefix(URLDecoder.decode(local, Charsets.UTF_8)));
        
        if (path.isAbsolute()) {
			return local;
		}
        
        return root.resolve(path).toUri().toString().replaceAll("file:/+", "file:/");
    }

    private static String removeFilePrefix(String id) {
        return id.replace("file:/", "");
    }

    public static void allToGlobal(List<? extends EChange<?>> eChanges) {
        for (var eChange : eChanges) {
            if (eChange instanceof RootEChange<?> change) {
                change.setUri(toGlobal(change.getUri()));
            }
        }
    }
}
