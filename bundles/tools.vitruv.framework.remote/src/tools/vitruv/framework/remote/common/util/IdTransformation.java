package tools.vitruv.framework.remote.common.util;

import java.nio.file.Path;
import java.util.List;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.EObjectAddedEChange;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.eobject.EObjectSubtractedEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.root.RootEChange;

/**
 * Contains functions to transform ids used by the vitruv framework to identify
 * {@link org.eclipse.emf.ecore.EObject EObjects}.
 */
public final class IdTransformation {

    private IdTransformation() {
        throw new UnsupportedOperationException("Utility Class Constructor!");
    }

    /**
     * Transforms the given global id to a local id.
     *
     * @param id the id to transform
     * @return the local id
     */
    public static String toLocal(String id) {
        if (id == null) {
            return null;
        }
        // dont change cache ids
        return id.contains("cache") || id.equals(SerializationConstants.TEMP) ? id :
                Path.of("").toAbsolutePath().relativize(Path.of(prepareId(id)))
                        .toString().replace("\\", "/");
    }

    /**
     * Transforms the given local id to a global id.
     *
     * @param id the id to transform
     * @return the global id
     */
    public static String toGlobal(String id) {
        if (id == null) {
            return null;
        }
        return id.contains("cache") || id.equals(SerializationConstants.TEMP) ? id :
                "file:/" + Path.of("").toAbsolutePath().resolve(Path.of(id))
                        .toString().replace("\\", "/");
    }

    private static String prepareId(String id) {
        return id.replace("file:/", "");
    }

    /**
     * Transforms all local ids of the given changes to global ids.
     *
     * @param changes the changes which ids should be transformed
     */
    public static void allToGlobal(List<EChange> changes) {
        for (var eChange : changes) {
            if (eChange instanceof EObjectExistenceEChange<?> change) {
                change.setAffectedEObjectID(toGlobal(change.getAffectedEObjectID()));
            }
            if (eChange instanceof EObjectAddedEChange<?> change) {
                change.setNewValueID(toGlobal(change.getNewValueID()));
            }
            if (eChange instanceof EObjectSubtractedEChange<?> change) {
                change.setOldValueID(toGlobal(change.getOldValueID()));
            }
            if (eChange instanceof FeatureEChange<?, ?> change) {
                change.setAffectedEObjectID(toGlobal(change.getAffectedEObjectID()));
            }
            if (eChange instanceof RootEChange change) {
                change.setUri(toGlobal(change.getUri()));
            }
        }
    }
}
