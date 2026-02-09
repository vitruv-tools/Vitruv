package tools.vitruv.framework.vsum.branch.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handles post-checkout operations after a Git branch switch. This handler is responsible for
 * reloading models from the file system to reflect the state of the newly checked-out branch.
 *
 * <p>Triggered by {@link BranchManager#switchBranch(String)} after the Git checkout operation
 * completes successfully.
 */
public class PostCheckoutHandler {
    private static final Logger LOGGER = LogManager.getLogger(PostCheckoutHandler.class);

    private final VirtualModel virtualModel;

    /**
     * Creates a new {@link PostCheckoutHandler}
     * @param virtualModel the virtual model whose resources should be handled after checkout
     */
    public PostCheckoutHandler(VirtualModel virtualModel) {
        this.virtualModel = checkNotNull(virtualModel, "VirtualModel muss not be null");
    }

    public void onBranchSwitch(String oldBranch, String newBranch) throws BranchOperationException {
        checkNotNull(oldBranch, "oldBranch muss not be null");
        checkNotNull(newBranch, "newBranch must not be null");

        LOGGER.info("Branch switched from {} to {}", oldBranch, newBranch);

        try {
            LOGGER.debug("Reloading models from file system");
            virtualModel.reload();
            LOGGER.info("Models reloaded successfully for branch {}", newBranch);

        } catch (Exception e) {
            throw new BranchOperationException("Failed to reload models after switching to branch " + newBranch, e);
        }
    }
}
