package tools.vitruv.framework.vsum.branch.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

/**
 * Handles post-checkout operations after a Git branch switch.
 * This handler is responsible for reloading VSUM from the file system so that its
 * in-memory state reflects the content of the newly checked-out branch.
 *
 * <p>The handler is invoked from two different paths depending on how the branch switch
 * occurred:
 * <ul>
 *   <li>directly via {@link BranchManager#switchBranch(String)}, which calls
 *       {@link #onBranchSwitch(String, String)} synchronously after the Git checkout.</li>
 *   <li>via the file-based inter-process communication path, where the {@code post-checkout}
 *       Git hook writes a reload trigger file and the background watcher thread picks it up
 *       and invokes this handler asynchronously.</li>
 * </ul>
 *
 * <p>In both cases the effect is the same: the VirtualModel discards its current in-memory
 * state and reloads all resources from disk.
 * This ensures consistency between the branch content visible on the file system and the
 * model state used by Vitruvius consistency rules.
 */
public class PostCheckoutHandler {

  private static final Logger LOGGER = LogManager.getLogger(PostCheckoutHandler.class);

  private final VirtualModel virtualModel;

  /**
   * Creates a new {@link PostCheckoutHandler} backed by the given VirtualModel.
   *
   * @param virtualModel the VirtualModel whose resources will be reloaded after each branch
   *                     switch, must not be null.
   */
  public PostCheckoutHandler(VirtualModel virtualModel) {
    this.virtualModel = checkNotNull(virtualModel, "VirtualModel must not be null");
  }

  /**
   * Reloads the VirtualModel after a successful branch switch.
   * This method should be called once the Git working directory already reflects the new
   * branch content, so that the reload reads the correct files from disk.
   *
   * <p>If the reload fails, the exception is wrapped in a {@link BranchOperationException}
   * so that callers receive a consistent error type regardless of what the VirtualModel
   * throws internally.
   *
   * @param oldBranch the name of the branch that was active before the switch.
   * @param newBranch the name of the branch that is now checked out.
   * @throws BranchOperationException if the VirtualModel reload fails for any reason.
   */
  public void onBranchSwitch(String oldBranch, String newBranch) throws BranchOperationException {
    checkNotNull(oldBranch, "old branch name must not be null");
    checkNotNull(newBranch, "new branch name must not be null");

    LOGGER.info("branch switched from '{}' to '{}'", oldBranch, newBranch);

    try {
      // Git already reflects newBranch at this point; JGit resolves it automatically.
      VsumFileSystemLayout newLayout = new VsumFileSystemLayout(virtualModel.getFolder());
      newLayout.prepare();
      newLayout.inheritFromBranchIfEmpty(oldBranch);

      LOGGER.debug("reloading VirtualModel for branch '{}'", newBranch);
      virtualModel.reload(newLayout);
      LOGGER.info("VirtualModel reloaded successfully for branch '{}'", newBranch);

    } catch (Exception e) {
      throw new BranchOperationException(
          "failed to reload VirtualModel after switching to branch '" + newBranch + "'", e);
    }
  }
}
