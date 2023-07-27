package tools.vitruv.framework.remote.common.util;

import java.nio.file.Path;
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

    /**
     * Transforms the given global (absolute path) id to a local id (relative path).
     *
     * @param id the id to transform
     * @return the local id
     */
    public static String toLocal(String id) {
    	return id;
//        if (id == null || id.contains("cache") || id.equals(JsonFieldName.TEMP)) {
//            return id;
//        }
//        
//        var root = ProjectMarker.getProjectRootFolder(Path.of(removeFilePrefix(id)));
//        return root.toAbsolutePath().relativize(Path.of(removeFilePrefix(id))).toString().replace("\\", "/");
    }

    /**
     * Transforms the given local id (relative path) to a global id (absolute path).
     *
     * @param id the id to transform
     * @return the global id
     */
    public static String toGlobal(String id) {
    	return id;
//        if (id == null || id.contains("cache") || id.equals(JsonFieldName.TEMP)) {
//            return null;
//        }
//        
//        var root = ProjectMarker.getProjectRootFolder(Path.of(id));
//        return "file:/" + root.toAbsolutePath().resolve(Path.of(id)).toString().replace("\\", "/");
    }

    private static String removeFilePrefix(String id) {
        return id.replace("file://", "");
    }

    public static void allToGlobal(List<? extends EChange<?>> eChanges) {
        for (var eChange : eChanges) {
            if (eChange instanceof RootEChange<?> change) {
                change.setUri(toGlobal(change.getUri()));
            }
        }
    }
}
