package tools.vitruv.framework.remote.common.util;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

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
    
    private static HashMap<String, String> localToGlobalMapping = new HashMap<String, String>();

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
        
        var root = ProjectMarker.getProjectRootFolder(Path.of(removeFilePrefix(global)));
        var local = root.toAbsolutePath().relativize(Path.of(removeFilePrefix(global))).toString().replace("\\", "/");
        localToGlobalMapping.put(local, global);
        return local;
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
        
        return localToGlobalMapping.getOrDefault(local, local);
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
