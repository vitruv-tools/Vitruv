package tools.vitruv.framework.vsum.branch;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.ModelInstance;

/**
 * A branch-aware wrapper around {@link InternalVirtualModel} that supports context switching
 * between Git branches by reloading the underlying V-SUM state in place.
 *
 * <p>This class implements the decorator pattern: it wraps a single
 * {@link InternalVirtualModel} instance and delegates all normal V-SUM operations to it.
 * Branch switching is handled by {@link #switchBranch(String, String)}, which reloads the
 * wrapped model in place using the target branch's {@link VsumFileSystemLayout} without
 * disposing or recreating the object.
 *
 * <p><b>There is only one active instance since:</b><br>
 * {@code VirtualModelRegistry} enforces that only one {@code VirtualModelImpl} per storage
 * folder can be active at a time. Holding multiple instances simultaneously would cause
 * registry conflicts. One instance reloaded per branch switch is sufficient and
 * memory-efficient.
 */
public class BranchAwareVirtualModel implements InternalVirtualModel {
  private static final Logger LOGGER = LogManager.getLogger(BranchAwareVirtualModel.class);

  /**
   * The single active V-SUM instance. All method calls are delegated here.
   * Never disposed between branch switches, only on final shutdown via {@link #dispose()}.
   */
  private final InternalVirtualModel activeModel;

  /**
   * Root of the Git repository. Used to construct per-branch
   * {@link VsumFileSystemLayout} instances in {@link #switchBranch(String, String)}.
   */
  private final Path repoRoot;

  /**
   * Name of the currently active branch. Kept in sync with the layout inside
   * {@link #activeModel} after every successful switch.
   * Updated only after a successful reload so that it always reflects real state.
   */
  private String activeBranchName;

  /**
   * Accumulates atomic EChanges between commits so they can be serialized into the
   * semantic changelog. Registered as a {@link ChangePropagationListener} on construction
   * and cleared by {@link CommitManager} at commit time.
   */
  private final SemanticChangeBuffer changeBuffer = new SemanticChangeBuffer();

  /**
   * Creates a new {@link BranchAwareVirtualModel} wrapping the given already-initialized
   * {@link InternalVirtualModel}.
   *
   * <p>The initial branch name is resolved from the repository's current Git HEAD via
   * {@link VsumFileSystemLayout}, so the repository must have at least one commit.
   * The wrapped model is expected to already be fully initialized and loaded —
   * this constructor does not call {@code buildAndInitialize()} or
   * {@code loadExistingModels()}.
   *
   * @param repoRoot    root directory of the Git repository, must not be null.
   * @param activeModel the already-initialized V-SUM instance to wrap, must not be null.
   */
  public BranchAwareVirtualModel(Path repoRoot, InternalVirtualModel activeModel) {
    this.repoRoot = checkNotNull(repoRoot, "repo root must not be null");
    this.activeModel = checkNotNull(activeModel, "active model must not be null");
    // Resolve the current branch from Git HEAD so activeBranchName is always correct
    VsumFileSystemLayout currentLayout = new VsumFileSystemLayout(repoRoot);
    this.activeBranchName = currentLayout.getCurrentBranch();
    activeModel.addChangePropagationListener(changeBuffer);
    LOGGER.info("BranchAwareVirtualModel initialized on branch '{}'", activeBranchName);
  }

  /**
   * Returns the change buffer that accumulates atomic EChanges between commits.
   * {@link CommitManager} calls {@link SemanticChangeBuffer#drainChanges()} on this buffer
   * at commit time to produce the semantic changelog.
   */
  public SemanticChangeBuffer getChangeBuffer() {
    return changeBuffer;
  }

  /**
   * Switches the active V-SUM state to the given target branch by reloading the wrapped
   * {@link InternalVirtualModel} in place with the target branch's
   * {@link VsumFileSystemLayout}.
   *
   * <p>This method is called by
   * {@link tools.vitruv.framework.vsum.branch.handler.VsumReloadWatcher}
   * when a Git {@code post-checkout} hook trigger is detected.
   *
   * <p>Steps performed:
   * <ol>
   *   <li>Constructs a {@link VsumFileSystemLayout} for the target branch using
   *       {@link VsumFileSystemLayout#forBranch(Path, String)}.</li>
   *   <li>Calls {@link VsumFileSystemLayout#prepare()} to create the target branch's
   *       VSUM directory on disk if it does not exist yet.</li>
   *   <li>Calls {@link VsumFileSystemLayout#inheritFromBranchIfEmpty(String)} so a
   *       freshly created branch starts with the parent branch's UUID and
   *       correspondence state.</li>
   *   <li>Calls {@link InternalVirtualModel#reload(VsumFileSystemLayout)} to reset the
   *       in-memory resource set, UUID resolver, and correspondence model to reflect
   *       the target branch's files on disk.</li>
   * </ol>
   *
   * @param oldBranch the branch that was active before the switch, must not be null.
   * @param newBranch the branch to switch to, must not be null.
   * @throws BranchOperationException if layout preparation or reload fails.
   */
  public void switchBranch(String oldBranch, String newBranch) throws BranchOperationException {
    checkNotNull(oldBranch, "oldBranch must not be null");
    checkNotNull(newBranch, "newBranch must not be null");

    if (newBranch.equals(activeBranchName)) {
      LOGGER.debug("Already on branch '{}', skipping switch", newBranch);
      return;
    }
    LOGGER.info("Switching V-VSUM from branch '{}' to '{}'", oldBranch, newBranch);

    try {
      // Use forBranch() to bypass JGit HEAD resolution — the target branch name
      // is already known from the trigger file.
      VsumFileSystemLayout newLayout = VsumFileSystemLayout.forBranch(repoRoot, newBranch);

      // Create .vitruvius/vsum/{newBranch}/ on disk if it does not exist yet.
      newLayout.prepare();

      // If this branch has never been checked out before, copy uuid.uuid and
      // correspondences.correspondence from the parent branch as a starting point.
      // If the branch already has its own state, this is a no-op.
      newLayout.inheritFromBranchIfEmpty(oldBranch);

      // Reload in place: VirtualModelImpl unloads all current branch resources,
      // resets the UUID resolver, recreates the correspondence model pointing to the
      // target branch's file, and reloads from disk.
      // The VirtualModelRegistry entry and CPR configuration are never touched.
      activeModel.reload(newLayout);

      // Update only after successful reload
      activeBranchName = newBranch;
      LOGGER.info("V-SUM switched successfully to branch '{}'", newBranch);
    } catch (Exception e) {
      throw new BranchOperationException(
          "Failed to switch V-SUM to branch '" + newBranch + "'", e);
    }
  }

  /**
   * Returns the name of the currently active Git branch.
   *
   * @return the active branch name, never null.
   */
  public String getActiveBranch() {
    return activeBranchName;
  }

  /**
   * Returns the correspondence model of the currently active branch's V-SUM.
   */
  @Override
  public EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
    return activeModel.getCorrespondenceModel();
  }

  /**
   * Returns the model instance for the given URI from the currently active branch's V-SUM.
   */
  @Override
  public ModelInstance getModelInstance(URI modelUri) {
    return activeModel.getModelInstance(modelUri);
  }

  /**
   * Disposes the wrapped V-SUM, releasing all resources and deregistering from
   * {@code VirtualModelRegistry}.
   * Called only on clean application shutdown, never between branch switches.
   */
  @Override
  public void dispose() {
    LOGGER.info("Disposing BranchAwareVirtualModel (active branch: '{}')", activeBranchName);
    activeModel.dispose();
  }

  /**
   * Same-branch reload — reloads the V-SUM from disk without switching branches.
   * Called by {@link tools.vitruv.framework.vsum.branch.handler.VsumMergeWatcher}
   * after a merge to refresh in-memory state while staying on the current branch.
   */
  @Override
  public void reload() {
    activeModel.reload();
  }

  /**
   * Reloads the V-SUM with a new layout. External callers should prefer
   * {@link #switchBranch(String, String)} which additionally handles inheritance
   * and keeps {@code activeBranchName} consistent.
   * This override exists because {@link tools.vitruv.framework.vsum.VirtualModel}
   * declares it as part of the public interface.
   */
  @Override
  public void reload(VsumFileSystemLayout newLayout) {
    activeModel.reload(newLayout);
    this.activeBranchName = newLayout.getCurrentBranch();
  }

  @Override
  public Path getFolder() {
    return activeModel.getFolder();
  }

  @Override
  public void setChangePropagationMode(ChangePropagationMode changePropagationMode) {
    activeModel.setChangePropagationMode(changePropagationMode);
  }

  @Override
  public List<PropagatedChange> propagateChange(VitruviusChange<Uuid> vitruviusChange) {
    return activeModel.propagateChange(vitruviusChange);
  }

  @Override
  public void addChangePropagationListener(ChangePropagationListener changePropagationListener) {
    activeModel.addChangePropagationListener(changePropagationListener);
  }

  @Override
  public void removeChangePropagationListener(
      ChangePropagationListener changePropagationListener) {
    activeModel.removeChangePropagationListener(changePropagationListener);
  }

  @Override
  public <S extends ViewSelector> S createSelector(ViewType<S> viewType) {
    return activeModel.createSelector(viewType);
  }

  @Override
  public Collection<Resource> getViewSourceModels() {
    return activeModel.getViewSourceModels();
  }

  @Override
  public UuidResolver getUuidResolver() {
    return activeModel.getUuidResolver();
  }

  @Override
  public Collection<ViewType<?>> getViewTypes() {
    return activeModel.getViewTypes();
  }
}
