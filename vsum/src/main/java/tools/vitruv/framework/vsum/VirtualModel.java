package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewTypeProvider;

/** A virtual model in the VSUM framework that provides changeable model repositories and views. */
public interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {

  /**
   * Gets the folder of the virtual model.
   *
   * @return the folder of the virtual model
   */
  Path getFolder();

    /**
     * Reloads all models from the file system, discarding any in memory state and reflecting the current state of
     * the files on disk.
     * This is typically called after a git branch switch to ensure the virtual model reflects the checked-out branch.
     */
  void reload();

  /**
   * Defines how changes are propagated when passed to {@link #propagateChange(VitruviusChange)}. By
   * default, {@link ChangePropagationMode#TRANSITIVE_CYCLIC} is used, i.e., changes are
   * transitively propagated until no further changes are produced.
   */
  void setChangePropagationMode(ChangePropagationMode changePropagationMode);
}
