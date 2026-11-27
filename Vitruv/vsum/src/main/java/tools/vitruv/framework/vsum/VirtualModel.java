package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewTypeProvider;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

/** A virtual model in the VSUM framework that provides changeable model repositories and views. */
public interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {

  /**
   * Gets the folder of the virtual model.
   *
   * @return the folder of the virtual model
   */
  Path getFolder();

  /**
   * Binds this virtual model to a different branch's storage directory, discarding all
   * in-memory state and reloading from the new layout's files on disk.
   * Used when switching to a different branch whose VSUM directory differs from the current one.
   */
  void reinitialize(VsumFileSystemLayout newLayout);

  /**
   * Defines how changes are propagated when passed to {@link #propagateChange(VitruviusChange)}. By
   * default, {@link ChangePropagationMode#TRANSITIVE_CYCLIC} is used, i.e., changes are
   * transitively propagated until no further changes are produced.
   */
  void setChangePropagationMode(ChangePropagationMode changePropagationMode);
}
