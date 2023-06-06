package tools.vitruv.framework.remote.server;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;

/**
 * A Cache used to hold {@link View}s as long as the corresponding remote view has not yet been closed.
 */
public class Cache {

    private Cache() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }

    private static final Map<String, View> viewCache = new HashMap<>();
    private static final Map<String, ViewSelector> selectorCache = new HashMap<>();
    private static final Map<String, BiMap<String, EObject>> perSelectorUuidToEObjectMapping = new HashMap<>();

    public static void addView(String uuid, View view) {
        viewCache.put(uuid, view);
    }

    public static View getView(String uuid) {
        return viewCache.get(uuid);
    }

    public static View removeView(String uuid) {
        return viewCache.remove(uuid);
    }

    public static void addSelectorWithMapping(String selectorUuid, ViewSelector selector, BiMap<String, EObject> mapping) {
        selectorCache.put(selectorUuid, selector);
        perSelectorUuidToEObjectMapping.put(selectorUuid, mapping);
    }

    public static ViewSelector getSelector(String selectorUuid) {
        return selectorCache.get(selectorUuid);
    }

    public static EObject getEObjectFor(String selectorUuid, String objectUuid) {
        return perSelectorUuidToEObjectMapping.get(selectorUuid).get(objectUuid);
    }

    public static String getUuidFor(String selectorUuid, EObject eObject) {
        return perSelectorUuidToEObjectMapping.get(selectorUuid).inverse().get(eObject);
    }

    public static void removeMappingFor(String selectorUuid) {
        perSelectorUuidToEObjectMapping.remove(selectorUuid);
        selectorCache.remove(selectorUuid);
    }
}
