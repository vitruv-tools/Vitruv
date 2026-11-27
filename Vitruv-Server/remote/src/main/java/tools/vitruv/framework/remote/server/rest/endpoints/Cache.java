package tools.vitruv.framework.remote.server.rest.endpoints;

import com.google.common.collect.BiMap;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;

/**
 * A global cache holding {@link View}s, {@link ViewSelector}s and mappings of the form UUID and the
 * {@link EObject}.
 */
public class Cache {
  private Cache() throws InstantiationException {
    throw new InstantiationException("Cannot be instantiated");
  }

  private static final Map<String, View> viewCache = new HashMap<>();
  private static final Map<String, ViewSelector> selectorCache = new HashMap<>();
  private static final Map<String, BiMap<String, EObject>> perSelectorUuidToEObjectMapping =
      new HashMap<>();

  /**
   * Adds a view to the cache with the given uuid.
   *
   * @param uuid The uuid of the view.
   * @param view The view to add.
   */
  public static void addView(String uuid, View view) {
    viewCache.put(uuid, view);
  }

  /**
   * Retrieves a view from the cache with the given uuid.
   *
   * @param uuid The uuid of the view.
   * @return The view with the given uuid.
   */
  public static View getView(String uuid) {
    return viewCache.get(uuid);
  }

  /**
   * Removes a view from the cache with the given uuid.
   *
   * @param uuid The uuid of the view.
   * @return The removed view.
   */
  public static View removeView(String uuid) {
    return viewCache.remove(uuid);
  }

  /**
   * Adds a selector and its corresponding EObject mapping to the cache.
   *
   * @param selectorUuid The uuid of the selector.
   * @param selector The selector to add.
   * @param mapping The mapping of the form String and the EObject.
   */
  public static void addSelectorWithMapping(
      String selectorUuid, ViewSelector selector, BiMap<String, EObject> mapping) {
    selectorCache.put(selectorUuid, selector);
    perSelectorUuidToEObjectMapping.put(selectorUuid, mapping);
  }

  /**
   * Retrieves a selector from the cache with the given uuid.
   *
   * @param selectorUuid The uuid of the selector.
   * @return The selector with the given uuid.
   */
  public static ViewSelector getSelector(String selectorUuid) {
    return selectorCache.get(selectorUuid);
  }

  /**
   * Retrieves an EObject from the mapping for the given selector uuid and object uuid.
   *
   * @param selectorUuid The uuid of the selector.
   * @param objectUuid The uuid of the object.
   * @return The EObject with the given uuid.
   */
  public static EObject getEObjectFromMapping(String selectorUuid, String objectUuid) {
    return perSelectorUuidToEObjectMapping.get(selectorUuid).get(objectUuid);
  }

  /**
   * Retrieves an object uuid from the mapping for the given selector uuid and EObject.
   *
   * @param selectorUuid The uuid of the selector.
   * @param eObject The EObject to get the uuid for.
   * @return The uuid of the given EObject.
   */
  public static String getUuidFromMapping(String selectorUuid, EObject eObject) {
    return perSelectorUuidToEObjectMapping.get(selectorUuid).inverse().get(eObject);
  }

  /**
   * Removes a selector and its corresponding EObject mapping from the cache.
   *
   * @param selectorUuid The uuid of the selector.
   */
  public static void removeSelectorAndMapping(String selectorUuid) {
    perSelectorUuidToEObjectMapping.remove(selectorUuid);
    selectorCache.remove(selectorUuid);
  }
}
