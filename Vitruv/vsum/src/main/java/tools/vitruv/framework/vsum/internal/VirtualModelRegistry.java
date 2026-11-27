package tools.vitruv.framework.vsum.internal;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** A registry for virtual models. */
public class VirtualModelRegistry {
  private static VirtualModelRegistry instance = new VirtualModelRegistry();

  private Map<Path, InternalVirtualModel> folderToVirtualModelMap = new ConcurrentHashMap<>();

  private VirtualModelRegistry() {}

  /**
   * Returns the singleton instance of the registry.
   *
   * @return the singleton instance
   */
  public static VirtualModelRegistry getInstance() {
    return instance;
  }

  /**
   * Returns the virtual model for the given folder.
   *
   * @param folder the folder
   * @return the virtual model or null if no virtual model is registered for the folder
   * @see #registerVirtualModel(InternalVirtualModel)
   */
  public InternalVirtualModel getVirtualModel(Path folder) {
    return folderToVirtualModelMap.get(folder);
  }

  /**
   * Registers the given virtual model.
   *
   * @param model the virtual model to register
   */
  public void registerVirtualModel(InternalVirtualModel model) {
    folderToVirtualModelMap.put(model.getFolder(), model);
  }

  /**
   * Deregisters the given virtual model.
   *
   * @param model the virtual model to deregister
   */
  public void deregisterVirtualModel(InternalVirtualModel model) {
    folderToVirtualModelMap.remove(model.getFolder());
  }
}
