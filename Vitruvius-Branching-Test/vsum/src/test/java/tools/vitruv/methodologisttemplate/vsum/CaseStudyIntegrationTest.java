package tools.vitruv.methodologisttemplate.vsum;

import mir.reactions.model2Model2.Model2Model2ChangePropagationSpecification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.interaction.InteractionResultProvider;
import tools.vitruv.change.interaction.UserInteractionOptions;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;
import tools.vitruv.framework.vsum.branch.data.ModelMergeResult;
import tools.vitruv.framework.vsum.branch.handler.*;
import tools.vitruv.framework.vsum.branch.merge.ConflictResolutionProvider;
import tools.vitruv.framework.vsum.branch.merge.SemanticMergeEngine;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.versioning.VersioningService;
import tools.vitruv.framework.vsum.versioning.data.RollbackPreview;
import tools.vitruv.framework.vsum.versioning.data.RollbackResult;
import tools.vitruv.framework.vsum.versioning.data.VersionMetadata;
import tools.vitruv.methodologisttemplate.model.model.ModelFactory;
import tools.vitruv.methodologisttemplate.model.model.System;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end case study integration tests covering the four thesis scenarios S1-S4.
 *
 * <p>Each test exercises the complete production stack (VSUM, BranchManager, MergeManager,
 * VersioningService, and the changelog watcher pipeline) in a real JGit repository
 * with real EMF models.
 *
 * <p>Uses the existing {@code model.ecore} simple metamodel (System, Component) with
 * bidirectional change propagation via {@code Model2Model2ChangePropagationSpecification}.
 * The domain maps directly to the thesis brake system scenarios: Component corresponds to
 * BrakeDisk/ABSSensor/BrakeCaliper; the {@code name} attribute corresponds to component
 * identifiers such as {@code diameterInMM}.
 */
@DisplayName("Case study: S1-S4 developer scenarios")
class CaseStudyIntegrationTest {

    private static final String BRANCH_MAIN = "main";

    @TempDir
    Path tempDir;

    private Git git;
    private InternalVirtualModel rawModel;
    private BranchAwareVirtualModel vsum;
    private VsumReloadWatcher reloadWatcher;
    private VsumValidationWatcher validationWatcher;
    private VsumMergeWatcher mergeWatcher;
    private VsumPostCommitWatcher postCommitWatcher;

    @BeforeAll
    static void registerResourceFactory() {
        // Required for EMF to serialize and deserialize XMI model files (.model).
        // Without this, Resource.createResource() returns null and commitChanges() throws NPE
        // deep inside DefaultStateBasedResolutionStrategy.
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
                .put("*", new XMIResourceFactoryImpl());
    }

    @BeforeEach
    void setUp() throws Exception {
        git = Git.init()
                .setDirectory(tempDir.toFile())
                .setInitialBranch(BRANCH_MAIN)
                .call();
        // An empty commit satisfies the JGit requirement that HEAD is not null
        // before VsumFileSystemLayout initialises.
        git.commit()
                .setMessage("init")
                .setAllowEmpty(true)
                .setAuthor("test", "test@test")
                .call();

        // Build the raw InternalVirtualModel and keep a reference for VersioningService.
        // BranchAwareVirtualModel holds it as a private field with no public getter.
        rawModel = new VirtualModelBuilder()
                .withStorageFolder(tempDir)
                .withUserInteractorForResultProvider(createNonInteractiveProvider())
                .withChangePropagationSpecifications(new Model2Model2ChangePropagationSpecification())
                .buildAndInitialize();
        rawModel.setChangePropagationMode(ChangePropagationMode.TRANSITIVE_CYCLIC);

        vsum = new BranchAwareVirtualModel(tempDir, rawModel);

        // Start all 4 watchers so changelog generation is active from the first commit.
        reloadWatcher    = new VsumReloadWatcher(vsum, tempDir);
        validationWatcher = new VsumValidationWatcher(vsum, tempDir);
        mergeWatcher     = new VsumMergeWatcher(vsum, tempDir);
        postCommitWatcher = new VsumPostCommitWatcher(tempDir);
        postCommitWatcher.attachSemanticChangeTracking(
                vsum.getChangeBuffer(),
                vsum::getUuidResolver,
                vsum::getViewSourceModels);
        reloadWatcher.start();
        validationWatcher.start();
        mergeWatcher.start();
        postCommitWatcher.start();

        // Register the System root (creates example.model on disk) and add the base
        // component that all scenarios build on, then commit with changelog tracking.
        addSystem();
        addComponent("c1");
        commitAndWaitForChangelog("initial: add c1", BRANCH_MAIN);
    }

    @AfterEach
    void tearDown() {
        // Stop all background watchers even if the test failed to avoid thread leaks.
        if (validationWatcher != null) validationWatcher.stop();
        if (reloadWatcher != null)    reloadWatcher.stop();
        if (mergeWatcher != null)     mergeWatcher.stop();
        if (postCommitWatcher != null) postCommitWatcher.stop();
        if (git != null)              git.close();
    }

    // S1: Clean merge (non-overlapping changes)

    /**
     * S1: Two engineers work on different elements. One renames an existing component; the
     * other adds a brand-new one on a separate branch. {@link MergeManager} detects no
     * UUID+feature overlaps and completes the merge cleanly.
     */
    @Test
    @DisplayName("S1: non-overlapping changes on two branches merge cleanly")
    void s1CleanMergeNonOverlapping() throws Exception {
        var branchMgr = new BranchManager(tempDir);
        branchMgr.setPostCheckoutHandler(new PostCheckoutHandler(vsum));

        //1.step: Create feature branch before either side makes changes (common ancestor = add c1).
        branchMgr.createBranch("feature", BRANCH_MAIN);

        //2.step: Main branch renames c1 to c1-main.
        renameComponent("c1", "c1-main");
        commitAndWaitForChangelog("main: rename c1", BRANCH_MAIN);

        //3.step: Feature branch adds c2 (c1 is still c1 here).
        branchMgr.switchBranch("feature");
        addComponent("c2");
        commitAndWaitForChangelog("feature: add c2", "feature");

        //4.step: Switch back to main for the merge.
        branchMgr.switchBranch(BRANCH_MAIN);

        //5.step: Wire SemanticMergeEngine for changelog-replay fallback and call merge.
        // JGit RECURSIVE may fail on XMI regions; the engine replays feature EChanges onto main.
        var engine = new SemanticMergeEngine(
                tempDir,
                List.of(new Model2Model2ChangePropagationSpecification()),
                createNonInteractiveProvider());
        MergeManager mm = new MergeManager(tempDir);
        mm.suppressTriggerFile();
        mm.setPostMergeReload(() -> vsum.reload());
        mm.setMergeEngine(engine);
        ModelMergeResult result = mm.merge("feature");

        //6.step: Verify model state after clean merge.
        assertEquals(ModelMergeResult.MergeStatus.SUCCESS, result.getStatus(),
                "S1: clean merge must succeed, no overlapping element/feature pairs");
        List<String> names = componentNames();
        assertTrue(names.contains("c1-main"),
                "S1: renamed component from main branch must be present after merge");
        assertTrue(names.contains("c2"),
                "S1: new component from feature branch must be present after merge");
        assertEquals(2, names.size(),
                "S1: exactly two components expected after merge");
    }

    // S2: Conflict detection and THEIRS auto-resolution

    /**
     * S2: Both branches rename the same component to different values. The semantic pre-check
     * in {@link MergeManager} detects the UUID+feature conflict and blocks JGit before
     * touching any files. A second attempt with {@link ConflictResolutionProvider#chooseAllTheirs()}
     * resolves the conflict and produces a successful merge commit.
     */
    @Test
    @DisplayName("S2: same element modified on both branches: blocked, then auto-resolved with THEIRS")
    void s2ConflictDetectionAndTheirsResolution() throws Exception {
        var branchMgr = new BranchManager(tempDir);
        branchMgr.setPostCheckoutHandler(new PostCheckoutHandler(vsum));

        //1.step: Create conflict branch before either side makes changes (common ancestor = add c1).
        branchMgr.createBranch("conflict", BRANCH_MAIN);

        //2.step: Main branch renames c1 to c1-main.
        renameComponent("c1", "c1-main");
        commitAndWaitForChangelog("main: rename c1", BRANCH_MAIN);

        //3.step: Conflict branch renames c1 to c1-conflict (same element, same feature, different value).
        branchMgr.switchBranch("conflict");
        renameComponent("c1", "c1-conflict");
        commitAndWaitForChangelog("conflict: rename c1", "conflict");

        //4.step: Switch back to main and attempt the first (blocked) merge.
        branchMgr.switchBranch(BRANCH_MAIN);

        MergeManager mm = new MergeManager(tempDir);
        mm.suppressTriggerFile();
        mm.setPostMergeReload(() -> vsum.reload());
        ModelMergeResult blocked = mm.merge("conflict");

        //5.step: Verify the merge was blocked by the semantic pre-check.
        assertEquals(ModelMergeResult.MergeStatus.CONFLICTING, blocked.getStatus(),
                "S2: first merge must be blocked: semantic pre-check detects UUID+feature conflict");
        assertFalse(blocked.getConflictingFiles().isEmpty(),
                "S2: conflict descriptors must be non-empty");
        assertTrue(blocked.getConflictingFiles().stream().anyMatch(d -> d.startsWith("semantic://")),
                "S2: conflict descriptors must use the 'semantic://' URI scheme");
        // JGit was never invoked, so the XMI file must be clean
        String xmiContent = Files.readString(tempDir.resolve("example.model"));
        assertFalse(xmiContent.contains("<<<<<<<"),
                "S2: XMI file must not contain Git conflict markers: pre-check blocked the merge");

        //6.step: Wire engine with THEIRS provider and retry the merge.
        var provider = ConflictResolutionProvider.chooseAllTheirs();
        var engine = new SemanticMergeEngine(
                tempDir,
                List.of(new Model2Model2ChangePropagationSpecification()),
                createNonInteractiveProvider(),
                provider);
        mm.setMergeEngine(engine);
        mm.setConflictResolutionProvider(provider);
        ModelMergeResult resolved = mm.merge("conflict");

        //7.step: Verify the resolved merge succeeded and THEIRS value wins.
        assertEquals(ModelMergeResult.MergeStatus.SUCCESS, resolved.getStatus(),
                "S2: auto-resolved merge must succeed");
        assertNotNull(resolved.getMergeCommitSha(),
                "S2: a merge commit must be created");
        // THEIRS = conflict branch = "c1-conflict" must win
        List<String> names = componentNames();
        assertTrue(names.contains("c1-conflict"),
                "S2: THEIRS value (conflict branch) must win after auto-resolution");
        assertFalse(names.contains("c1-main"),
                "S2: OURS value (main branch) must be overwritten by THEIRS");
    }

    // S3: Versioning and rollback

    /**
     * S3: After reaching a stable state with two components, a version tag is created.
     * A further change is then made and committed. Rollback preview shows exactly two
     * commits to abandon (1 user commit + 1 changelog auto-commit). Confirmed rollback
     * restores the model to the tagged state.
     */
    @Test
    @DisplayName("S3: create version, make further changes, preview and confirm rollback")
    void s3VersioningAndRollback() throws Exception {
        //1.step: Reach a stable state with c1 (from setUp) and a newly added c2.
        addComponent("c2");
        commitAndWaitForChangelog("add c2: stable configuration", BRANCH_MAIN);

        //2.step: Tag this state as version v1.0.
        VersioningService vs = new VersioningService(tempDir, vsum::reload, new BranchManager(tempDir));
        // BranchManager.<init> uses a low-level commit to write branch metadata, advancing HEAD
        // without updating the on-disk index. Sync the index so git status stays clean.
        git.reset().setMode(org.eclipse.jgit.api.ResetCommand.ResetType.MIXED).setRef("HEAD").call();
        VersionMetadata v1 = vs.createVersion("v1.0", "c1 + c2 stable configuration");
        assertEquals("v1.0", v1.getVersionId(),
                "S3: created version ID must match the requested ID");

        //3.step: Make a post-version change by renaming c2.
        renameComponent("c2", "c2-modified");
        commitAndWaitForChangelog("rename c2: post-version change", BRANCH_MAIN);

        //4.step: Preview rollback - expect exactly 2 commits to abandon.
        // (1 user rename commit + 1 changelog auto-commit from VsumPostCommitWatcher)
        RollbackPreview preview = vs.previewRollback("v1.0");
        assertEquals(2, preview.getCommitsToAbandon().size(),
                "S3: rollback preview must show exactly 2 commits to abandon "
                + "(user commit + changelog auto-commit); got: " + preview.getCommitsToAbandon());

        //5.step: Confirm rollback and verify the model is restored to v1.0.
        RollbackResult rollbackResult = vs.confirmRollback(preview);
        assertTrue(rollbackResult.isSuccessful(),
                "S3: rollback must succeed; status=" + rollbackResult.getStatus()
                + " msg=" + rollbackResult.getMessage());

        // Verify the model state is back at v1.0 (c2 not renamed, c1 still present)
        List<String> names = componentNames();
        assertTrue(names.contains("c2"),
                "S3: component must be back to original name 'c2' after rollback");
        assertFalse(names.contains("c2-modified"),
                "S3: post-version name 'c2-modified' must not be present after rollback");
        assertTrue(names.contains("c1"),
                "S3: unrelated component 'c1' must be unaffected by rollback");
    }

    // S4: Branch lifecycle

    /**
     * S4: Full branch lifecycle: create from main, switch and make an isolated change,
     * verify isolation on main, inspect topology, promote maturity, delete. Each step
     * verifies both the API return value and the persisted state.
     */
    @Test
    @DisplayName("S4: full branch lifecycle: create, switch, isolation, topology, maturity, delete")
    void s4BranchLifecycleCreateIsolateTopologyDelete() throws Exception {
        BranchManager bm = new BranchManager(tempDir);
        bm.setPostCheckoutHandler(new PostCheckoutHandler(vsum));

        //1.step: Create branch from main (hyphen avoids Windows filesystem ambiguity).
        BranchMetadata meta = bm.createBranch("feature-pump", BRANCH_MAIN);
        assertEquals("feature-pump", meta.getName(),
                "S4: created branch name must match");
        assertEquals(BRANCH_MAIN, meta.getParent(),
                "S4: parent must be main");
        assertEquals(BranchState.ACTIVE, meta.getState(),
                "S4: new branch must start as ACTIVE");

        //2.step: Switch to feature branch and add an isolated component.
        bm.switchBranch("feature-pump");
        addComponent("pump");
        commitAndWaitForChangelog("feature-pump: add pump component", "feature-pump");

        //3.step: Switch back to main and verify branch isolation (pump must not be visible).
        bm.switchBranch(BRANCH_MAIN);
        List<String> mainComponents = componentNames();
        assertFalse(mainComponents.contains("pump"),
                "S4: component added on feature branch must not be visible on main");
        assertTrue(mainComponents.contains("c1"),
                "S4: base component must still be present on main");

        //4.step: Inspect topology - feature-pump must appear as a child of main.
        Map<String, List<String>> topology = bm.getBranchTopology();
        assertTrue(topology.containsKey(BRANCH_MAIN)
                        && topology.get(BRANCH_MAIN).contains("feature-pump"),
                "S4: getBranchTopology() must show 'feature-pump' as a child of 'main'; "
                + "topology=" + topology);

        //5.step: Promote maturity to REVIEWED.
        bm.setBranchMaturity("feature-pump", MaturityLevel.REVIEWED);
        BranchMetadata updated = bm.listBranches().stream()
                .filter(b -> b.getName().equals("feature-pump"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("feature-pump not found in listBranches()"));
        assertEquals(MaturityLevel.REVIEWED, updated.getMaturity(),
                "S4: maturity must be REVIEWED after setBranchMaturity()");

        //6.step: Delete the branch (force=true because it was never merged in this lifecycle test).
        bm.deleteBranch("feature-pump", true);
        boolean stillPresent = bm.listBranches().stream()
                .anyMatch(b -> b.getName().equals("feature-pump"));
        assertFalse(stillPresent,
                "S4: deleted branch must not appear in listBranches()");
    }

    // Private helpers

    private void addSystem() {
        modifyView(getDefaultView(), view ->
                view.registerRoot(ModelFactory.eINSTANCE.createSystem(),
                        URI.createFileURI(tempDir.toString() + "/example.model")));
    }

    private void addComponent(String name) {
        modifyView(getDefaultView(), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var c = ModelFactory.eINSTANCE.createComponent();
            c.setName(name);
            system.getComponents().add(c);
        });
    }

    private void renameComponent(String oldName, String newName) {
        modifyView(getDefaultView(), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            system.getComponents().stream()
                    .filter(c -> c.getName().equals(oldName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError(
                            "No component named '" + oldName + "' found"))
                    .setName(newName);
        });
    }

    private List<String> componentNames() {
        CommittableView view = getDefaultView();
        return view.getRootObjects(System.class).stream()
                .findFirst()
                .map(s -> s.getComponents().stream()
                        .map(c -> c.getName())
                        .toList())
                .orElse(List.of());
    }

    private CommittableView getDefaultView() {
        var selector = rawModel.createSelector(
                ViewTypeFactory.createIdentityMappingViewType("default"));
        selector.getSelectableElements().stream()
                .filter(e -> e instanceof System)
                .forEach(e -> selector.setSelected(e, true));
        return selector.createView().withChangeRecordingTrait();
    }

    private void modifyView(CommittableView view, Consumer<CommittableView> modification) {
        modification.accept(view);
        view.commitChanges();
    }

    /**
     * Commits all staged changes, writes a {@link PostCommitTriggerFile} so the watcher
     * generates a changelog, and waits until that changelog appears in the Git object store.
     * Also brackets the operation with {@link #waitForGitIndexUnlocked} to avoid racing
     * with the watcher's own git.add()+git.commit() cycle.
     */
    private void commitAndWaitForChangelog(String message, String branch) throws Exception {
        waitForGitIndexUnlocked(tempDir, 3_000);
        git.add().addFilepattern(".").call();
        git.commit()
                .setMessage(message)
                .setAuthor("test", "test@test")
                .call();
        String sha = git.getRepository().resolve("HEAD").getName();
        new PostCommitTriggerFile(tempDir).createTrigger(sha, branch);
        String relPath = ".vitruvius/changelogs/" + branch + "/json/"
                + sha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, branch, relPath, 10_000);
        waitForGitIndexUnlocked(tempDir, 3_000);
        // The low-level changelog commit advances HEAD without updating the index.
        // A mixed reset syncs the index to HEAD so subsequent git add calls see a
        // consistent base and do not accidentally re-stage already-committed files.
        git.reset().setMode(org.eclipse.jgit.api.ResetCommand.ResetType.MIXED).setRef("HEAD").call();
    }

    private static void waitForChangelogInObjectStore(Path repoRoot, String branchName,
            String relPath, long timeoutMs) throws InterruptedException, IOException {
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        while (java.lang.System.currentTimeMillis() < deadline) {
            try (org.eclipse.jgit.api.Git g = org.eclipse.jgit.api.Git.open(repoRoot.toFile());
                 org.eclipse.jgit.revwalk.RevWalk walk =
                         new org.eclipse.jgit.revwalk.RevWalk(g.getRepository())) {
                org.eclipse.jgit.lib.ObjectId head =
                        g.getRepository().resolve("refs/heads/" + branchName);
                if (head != null) {
                    org.eclipse.jgit.revwalk.RevCommit commit = walk.parseCommit(head);
                    try (org.eclipse.jgit.treewalk.TreeWalk tw =
                                 org.eclipse.jgit.treewalk.TreeWalk.forPath(
                                         g.getRepository(), relPath, commit.getTree())) {
                        if (tw != null) return;
                    }
                }
            } catch (Exception ignored) {}
            Thread.sleep(100);
        }
    }

    // Polls until the git index lock is absent for 4 consecutive checks (~200 ms total).
    // The background watcher does git.add() then git.commit(), releasing the lock briefly
    // between the two calls. A stable-absence window of 200 ms spans that gap reliably.
    private static void waitForGitIndexUnlocked(Path repoRoot, long timeoutMs)
            throws InterruptedException {
        Path lockFile = repoRoot.resolve(".git/index.lock");
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        int stable = 0;
        while (java.lang.System.currentTimeMillis() < deadline) {
            if (!Files.exists(lockFile)) {
                if (++stable >= 4) return;
            } else {
                stable = 0;
            }
            Thread.sleep(50);
        }
    }

    // Returns a provider that auto-confirms all interactions so tests never block waiting for user input.
    private static InteractionResultProvider createNonInteractiveProvider() {
        return new InteractionResultProvider() {
            @Override
            public boolean getConfirmationInteractionResult(
                    UserInteractionOptions.WindowModality m, String title, String message,
                    String pos, String neg, String cancel) { return true; }
            @Override
            public void getNotificationInteractionResult(
                    UserInteractionOptions.WindowModality m, String title, String message,
                    String pos, UserInteractionOptions.NotificationType type) {}
            @Override
            public String getTextInputInteractionResult(
                    UserInteractionOptions.WindowModality m, String title, String message,
                    String pos, String cancel, UserInteractionOptions.InputValidator v) { return ""; }
            @Override
            public int getMultipleChoiceSingleSelectionInteractionResult(
                    UserInteractionOptions.WindowModality m, String title, String message,
                    String pos, String cancel, Iterable<String> choices) { return 0; }
            @Override
            public Iterable<Integer> getMultipleChoiceMultipleSelectionInteractionResult(
                    UserInteractionOptions.WindowModality m, String title, String message,
                    String pos, String cancel, Iterable<String> choices) { return List.of(0); }
        };
    }
}
