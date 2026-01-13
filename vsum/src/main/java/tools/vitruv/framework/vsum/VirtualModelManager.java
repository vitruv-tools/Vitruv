package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.VirtualModelRegistry;

/** Manager for virtual models in the VSUM framework. */
public final class VirtualModelManager {
  private static final VirtualModelManager instance = new VirtualModelManager();

  /** Private constructor to prevent instantiation. */
  private VirtualModelManager() {}

  /**
   * Gets the singleton instance of the VirtualModelManager.
   *
   * @return the singleton instance
   */
  public static VirtualModelManager getInstance() {
    return instance;
  }

  /**
   * Gets the virtual model for the specified folder.
   *
   * @param folder the folder of the virtual model
   * @return the virtual model
   */
  public InternalVirtualModel getVirtualModel(Path folder) {
    InternalVirtualModel virtualModel = VirtualModelRegistry.getInstance().getVirtualModel(folder);
    if (virtualModel == null) {
      throw new UnsupportedOperationException("Virtual models cannot be loaded yet");
    }
    return virtualModel;
  }
}
