package tools.vitruv.methodologisttemplate.vsum;

import brakesystem.*;
import edu.kit.ipd.sdq.metamodels.cad.CAD_Model;
import edu.kit.ipd.sdq.metamodels.cad.NumericParameter;
import mir.reactions.brakesystem2cad.Brakesystem2cadChangePropagationSpecification;
import mir.reactions.cad2brakesystem.Cad2brakesystemChangePropagationSpecification;
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
 * <p>Uses the real vehicle braking system metamodel (Brakesystem, BrakeDisk, ABSSensor,
 * BrakeCaliper) with bidirectional consistency rules between M1 (brakesystem) and M2 (CAD_Model).
 */
@DisplayName("Case study (brake system): S1-S4 developer scenarios")
class BrakeCaseStudyIntegrationTest {

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
        // Required for EMF to serialize and deserialize XMI model files (.model, .cad).
        // Without this, Resource.createResource() returns null and commitChanges() throws NPE
        // deep inside DefaultStateBasedResolutionStrategy.
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
    }

    @BeforeEach
    void setUp() throws Exception {
        git = Git.init().setDirectory(tempDir.toFile()).setInitialBranch(BRANCH_MAIN).call();
        git.commit().setMessage("init").setAllowEmpty(true).setAuthor("test", "test@test").call();

        rawModel = new VirtualModelBuilder()
                .withStorageFolder(tempDir)
                .withUserInteractorForResultProvider(createNonInteractiveProvider())
                .withChangePropagationSpecifications(
                        new Brakesystem2cadChangePropagationSpecification(),
                        new Cad2brakesystemChangePropagationSpecification())
                .buildAndInitialize();
        rawModel.setChangePropagationMode(ChangePropagationMode.TRANSITIVE_CYCLIC);

        vsum = new BranchAwareVirtualModel(tempDir, rawModel);

        reloadWatcher = new VsumReloadWatcher(vsum, tempDir);
        validationWatcher = new VsumValidationWatcher(vsum, tempDir);
        mergeWatcher = new VsumMergeWatcher(vsum, tempDir);
        postCommitWatcher = new VsumPostCommitWatcher(tempDir);
        postCommitWatcher.attachSemanticChangeTracking(
                vsum.getChangeBuffer(), vsum::getUuidResolver, vsum::getViewSourceModels);
        reloadWatcher.start();
        validationWatcher.start();
        mergeWatcher.start();
        postCommitWatcher.start();

        // Register M1 root. The BrakesystemInsertedAsRoot reaction creates the CAD_Model
        // automatically and persists it at example.cad.
        modifyView(getBrakesystemView(), v -> v.registerRoot(BrakesystemFactory.eINSTANCE.createBrakesystem(), URI.createFileURI(tempDir.toString() + "/brakesystem.model")));

        // Add disk1 as the common ancestor for all scenarios.
        addDisk("disk1", 120, 30);
        commitAndWaitForChangelog("initial: add disk1", BRANCH_MAIN);
    }

    @AfterEach
    void tearDown() {
        // Stop all background watchers even if the test failed to avoid thread leaks.
        if (validationWatcher != null) validationWatcher.stop();
        if (reloadWatcher != null) reloadWatcher.stop();
        if (mergeWatcher != null) mergeWatcher.stop();
        if (postCommitWatcher != null) postCommitWatcher.stop();
        if (git != null) git.close();
    }


    // S1: Clean merge (non-overlapping changes)


    /**
     * S1: One engineer changes disk1.diameterInMM on a feature branch; the other adds
     * ABSSensor sensor1 on main. The SemanticConflictDetector finds no UUID+feature overlap
     * and the merge succeeds cleanly. M1 and M2 are verified to reflect both changes.
     */
    @Test
    @DisplayName("S1: non-overlapping changes on two branches merge cleanly")
    void s1CleanMerge() throws Exception {
        BranchManager branchMgr = new BranchManager(tempDir);
        branchMgr.setPostCheckoutHandler(new PostCheckoutHandler(vsum));

        //1.step: Create feature branch before either side makes changes (common ancestor = add disk1).
        branchMgr.createBranch("feature-diameter", BRANCH_MAIN);

        //2.step: Feature branch changes disk1.diameterInMM from 120 to 130.
        branchMgr.switchBranch("feature-diameter");
        changeDiameter("disk1", 130);
        commitAndWaitForChangelog("feature-diameter: change diameter to 130", "feature-diameter");

        //3.step: Main branch adds sensor1 (does not touch diameterInMM).
        branchMgr.switchBranch(BRANCH_MAIN);
        addSensor("sensor1", 100, 3);
        commitAndWaitForChangelog("main: add sensor1", BRANCH_MAIN);

        //4.step: Wire SemanticMergeEngine and merge feature-diameter into main.
        var engine = new SemanticMergeEngine(tempDir,
                List.of(new Brakesystem2cadChangePropagationSpecification(),
                        new Cad2brakesystemChangePropagationSpecification()),
                createNonInteractiveProvider());
        MergeManager mm = new MergeManager(tempDir);
        mm.suppressTriggerFile();
        mm.setPostMergeReload(() -> vsum.reload());
        mm.setMergeEngine(engine);
        ModelMergeResult result = mm.merge("feature-diameter");

        //5.step: Verify both changes are present in M1 and M2 after the clean merge.
        assertEquals(ModelMergeResult.MergeStatus.SUCCESS, result.getStatus(), "S1: clean merge must succeed, no overlapping element/feature pairs");
        assertTrue(result.isSuccessful(), "S1: isSuccessful() must return true");
        assertNotNull(result.getMergeCommitSha(), "S1: merge commit sha must not be null");

        // M1: both changes must be visible after merge.
        assertEquals(130, getDisk("disk1").getDiameterInMM(), "S1: disk1.diameterInMM must be 130 (feature branch change) after merge");
        assertTrue(getSensorPresent("sensor1"), "S1: sensor1 must be present (main branch addition) after merge");

        // M2: CAD consistency, reaction must have kept both namespaces in sync.
        assertEquals(130.0f, getNumericParamValue("disk1", "Diameter"), 0.001f, "S1: CAD Diameter parameter for disk1 must be 130.0 after merge");
        assertTrue(getCadNamespacePresent("sensor1"), "S1: CAD namespace for sensor1 must be present after merge");
    }


    // S2: Direct conflict detection and THEIRS auto-resolution


    /**
     * S2: Both engineers change disk1.diameterInMM to different values. The semantic
     * pre-check detects the UUID+feature conflict before JGit touches any files. A
     * second attempt with THEIRS resolution accepts the conflict branch value and
     * produces a consistent M1+M2 state.
     */
    @Test
    @DisplayName("S2: same attribute modified on both branches -> blocked, then auto-resolved with THEIRS")
    void s2ConflictDetectionAndTheirsResolution() throws Exception {
        BranchManager branchMgr = new BranchManager(tempDir);
        branchMgr.setPostCheckoutHandler(new PostCheckoutHandler(vsum));

        //1.step: Create conflict branch before either side makes changes (common ancestor = add disk1).
        branchMgr.createBranch("conflict-diameter", BRANCH_MAIN);

        //2.step: Conflict branch changes disk1.diameterInMM from 120 to 350.
        branchMgr.switchBranch("conflict-diameter");
        changeDiameter("disk1", 350);
        commitAndWaitForChangelog("conflict-diameter: change diameter to 350", "conflict-diameter");

        //3.step: Main branch changes disk1.diameterInMM from 120 to 130 (competing change on same feature).
        branchMgr.switchBranch(BRANCH_MAIN);
        changeDiameter("disk1", 130);
        commitAndWaitForChangelog("main: change diameter to 130", BRANCH_MAIN);

        //4.step: First merge attempt - must be blocked by SemanticConflictDetector.
        MergeManager mm = new MergeManager(tempDir);
        mm.suppressTriggerFile();
        mm.setPostMergeReload(() -> vsum.reload());
        ModelMergeResult blocked = mm.merge("conflict-diameter");

        //5.step: Verify the merge was blocked and no conflict markers touched the XMI file.
        assertEquals(ModelMergeResult.MergeStatus.CONFLICTING, blocked.getStatus(), "S2: first merge must be blocked, semantic pre-check detects UUID+feature conflict");
        assertFalse(blocked.isSuccessful(), "S2: isSuccessful() must return false");
        assertNull(blocked.getMergeCommitSha(), "S2: no merge commit must be created for a blocked merge");
        assertFalse(blocked.getConflictingFiles().isEmpty(), "S2: conflict descriptor list must be non-empty");
        assertTrue(blocked.getConflictingFiles().stream().anyMatch(d -> d.startsWith("semantic://")), "S2: conflict descriptors must use the semantic:// URI scheme");
        assertTrue(blocked.getConflictingFiles().stream().anyMatch(d -> d.contains("diameterInMM")), "S2: conflict descriptor must name the conflicting feature (diameterInMM)");

        // JGit was never invoked, model file must contain no conflict markers.
        String xmiContent = Files.readString(tempDir.resolve("brakesystem.model"));
        assertFalse(xmiContent.contains("<<<<<<<"), "S2: model file must not contain Git conflict markers after blocked merge");

        // Model state must be unchanged (merge did not apply).
        assertEquals(130, getDisk("disk1").getDiameterInMM(), "S2: disk1.diameterInMM must still be 130 (main value) after blocked merge");

        //6.step: Wire THEIRS provider and retry the merge.
        ConflictResolutionProvider provider = ConflictResolutionProvider.chooseAllTheirs();
        var engine = new SemanticMergeEngine(tempDir,
                List.of(new Brakesystem2cadChangePropagationSpecification(),
                        new Cad2brakesystemChangePropagationSpecification()),
                createNonInteractiveProvider(), provider);
        mm.setMergeEngine(engine);
        mm.setConflictResolutionProvider(provider);
        ModelMergeResult resolved = mm.merge("conflict-diameter");

        //7.step: Verify the resolved merge succeeded and THEIRS value wins in M1 and M2.
        assertEquals(ModelMergeResult.MergeStatus.SUCCESS, resolved.getStatus(), "S2: auto-resolved merge must succeed");
        assertTrue(resolved.isSuccessful(), "S2: resolved isSuccessful() must be true");
        assertNotNull(resolved.getMergeCommitSha(), "S2: a merge commit must be created after successful resolution");

        // THEIRS = conflict-diameter branch wins: diameterInMM must be 350.
        assertEquals(350, getDisk("disk1").getDiameterInMM(), "S2: THEIRS value (350) must win after auto-resolution");

        // M2 consistency: CAD Diameter must reflect the winning value.
        assertEquals(350.0f, getNumericParamValue("disk1", "Diameter"), 0.001f, "S2: CAD Diameter must be 350.0 after THEIRS resolution");
    }


    // S3: Versioning and rollback


    /**
     * S3: A stable state containing disk1 and sensor1 is tagged as version v1.0.
     * The thickness attribute is then changed and committed. Rollback preview shows
     * exactly two commits to abandon (1 user commit + 1 changelog auto-commit).
     * Confirmed rollback restores M1 and M2 to the tagged state.
     */
    @Test
    @DisplayName("S3: create version, make further changes, preview and confirm rollback")
    void s3VersioningAndRollback() throws Exception {
        //1.step: Extend the base state with sensor1 to reach the stable configuration.
        addSensor("sensor1", 100, 3);
        commitAndWaitForChangelog("add sensor1: stable configuration", BRANCH_MAIN);

        //2.step: Tag this state as version v1.0.
        VersioningService vs = new VersioningService(tempDir, vsum::reload, new BranchManager(tempDir));
        // BranchManager.<init> uses a low-level commit to write branch metadata, advancing HEAD
        // without updating the on-disk index. Sync the index so git status stays clean.
        git.reset().setMode(org.eclipse.jgit.api.ResetCommand.ResetType.MIXED).setRef("HEAD").call();
        VersionMetadata v1 = vs.createVersion("v1.0", "Stable brake disk and sensor configuration");
        assertEquals("v1.0", v1.getVersionId(), "S3: version ID must be v1.0");
        assertEquals(BRANCH_MAIN, v1.getBranch(), "S3: version branch must be main");

        //3.step: Make a post-version change (reduce thickness from 30 to 25).
        changeThickness("disk1", 25);
        commitAndWaitForChangelog("change thickness: post-version change", BRANCH_MAIN);

        //4.step: Preview rollback - expect exactly 2 commits to abandon.
        // (1 user commit + 1 changelog auto-commit from VsumPostCommitWatcher)
        RollbackPreview preview = vs.previewRollback("v1.0");
        assertEquals(2, preview.getCommitsToAbandon().size(), "S3: rollback preview must show exactly 2 commits to abandon "
                + "(user commit + changelog auto-commit); got: " + preview.getCommitsToAbandon());

        //5.step: Confirm rollback and verify M1 and M2 are restored to the v1.0 state.
        RollbackResult rollbackResult = vs.confirmRollback(preview);
        assertTrue(rollbackResult.isSuccessful(), "S3: rollback must succeed; status=" + rollbackResult.getStatus()
                + " msg=" + rollbackResult.getMessage());

        // M1 assertions: model must reflect the v1.0 state.
        assertEquals(30, getDisk("disk1").getBrakeDiskThicknessInMM(), "S3: brakeDiskThicknessInMM must be rolled back to 30");
        assertEquals(120, getDisk("disk1").getDiameterInMM(), "S3: diameterInMM must be unchanged at 120 after rollback");
        assertTrue(getSensorPresent("sensor1"), "S3: sensor1 must still be present after rollback");

        // M2 assertion: CAD parameter must reflect the rolled-back thickness.
        assertEquals(30.0f, getNumericParamValue("disk1", "Brake Disk Thickness"), 0.001f, "S3: CAD Brake Disk Thickness must be 30.0 after rollback");
    }


    // S4: Branch lifecycle


    /**
     * S4: Full branch lifecycle
     * create from main, switch and add an isolated component,
     * verify isolation on main (M1 + M2), inspect topology, promote maturity, delete.
     */
    @Test
    @DisplayName("S4: full branch lifecycle - create, switch, isolation, topology, maturity, delete")
    void s4BranchLifecycle() throws Exception {
        BranchManager bm = new BranchManager(tempDir);
        bm.setPostCheckoutHandler(new PostCheckoutHandler(vsum));

        //1.step: Create branch (hyphen instead of slash avoids Windows filesystem issues).
        BranchMetadata meta = bm.createBranch("feature-caliper", BRANCH_MAIN);
        assertEquals("feature-caliper", meta.getName(), "S4: created branch name must match");
        assertEquals(BRANCH_MAIN, meta.getParent(), "S4: parent must be main");
        assertEquals(BranchState.ACTIVE, meta.getState(), "S4: new branch must be ACTIVE");
        assertEquals(MaturityLevel.DRAFT, meta.getMaturity(), "S4: initial maturity must be DRAFT");

        //2.step: Switch to feature branch and add caliper1.
        bm.switchBranch("feature-caliper");
        addCaliper("caliper1", 50);
        commitAndWaitForChangelog("feature-caliper: add caliper1", "feature-caliper");

        // Caliper1 must be visible on the feature branch.
        assertTrue(getCaliperPresent("caliper1"), "S4: caliper1 must be present on feature-caliper branch");
        assertTrue(getCadNamespacePresent("caliper1"), "S4: CAD namespace for caliper1 must be present on feature-caliper");

        //3.step: Switch to main and verify caliper1 is not visible (branch isolation).
        bm.switchBranch(BRANCH_MAIN);
        assertFalse(getCaliperPresent("caliper1"), "S4: caliper1 must not be visible on main (branch isolation)");
        assertTrue(getDiskPresent("disk1"), "S4: disk1 must still be present on main after branch switch");
        assertFalse(getCadNamespacePresent("caliper1"), "S4: CAD namespace for caliper1 must not be visible on main");

        //4.step: Inspect topology - feature-caliper must appear as a child of main.
        Map<String, List<String>> topology = bm.getBranchTopology();
        assertTrue(topology.containsKey(BRANCH_MAIN) && topology.get(BRANCH_MAIN).contains("feature-caliper"), "S4: getBranchTopology() must show feature-caliper as child of main; " + "topology=" + topology);


        //5.step: Delete the branch (force=true because it was never merged in this lifecycle test).
        bm.deleteBranch("feature-caliper", true);
        boolean stillPresent = bm.listBranches().stream().anyMatch(b -> b.getName().equals("feature-caliper"));
        assertFalse(stillPresent, "S4: deleted branch must not appear in listBranches()");
    }


    // Private helpers: model manipulation


    private void addDisk(String id, int diameterInMM, int thicknessInMM) {
        modifyView(getBrakesystemView(), v -> {
            Brakesystem bs = v.getRootObjects(Brakesystem.class).iterator().next();
            BrakeDisk disk = BrakesystemFactory.eINSTANCE.createBrakeDisk();
            disk.setId(id);
            disk.setDiameterInMM(diameterInMM);
            disk.setBrakeDiskThicknessInMM(thicknessInMM);
            bs.getBrakeComponents().add(disk);
        });
    }

    private void addSensor(String id, int lengthInMM, int numberOfPins) {
        modifyView(getBrakesystemView(), v -> {
            Brakesystem bs = v.getRootObjects(Brakesystem.class).iterator().next();
            ABSSensor sensor = BrakesystemFactory.eINSTANCE.createABSSensor();
            sensor.setId(id);
            sensor.setLengthInMM(lengthInMM);
            sensor.setNumberOfPins(numberOfPins);
            bs.getBrakeComponents().add(sensor);
        });
    }

    private void addCaliper(String id, int pistonDiameterInMM) {
        modifyView(getBrakesystemView(), v -> {
            Brakesystem bs = v.getRootObjects(Brakesystem.class).iterator().next();
            BrakeCaliper caliper = BrakesystemFactory.eINSTANCE.createBrakeCaliper();
            caliper.setId(id);
            caliper.setPistonDiameterInMM(pistonDiameterInMM);
            bs.getBrakeComponents().add(caliper);
        });
    }

    private void changeDiameter(String diskId, int newDiameter) {
        modifyView(getBrakesystemView(), v -> {
            Brakesystem bs = v.getRootObjects(Brakesystem.class).iterator().next();
            bs.getBrakeComponents().stream()
                    .filter(c -> diskId.equals(c.getId()) && c instanceof BrakeDisk)
                    .map(c -> (BrakeDisk) c)
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Disk '" + diskId + "' not found"))
                    .setDiameterInMM(newDiameter);
        });
    }

    private void changeThickness(String diskId, int newThickness) {
        modifyView(getBrakesystemView(), v -> {
            Brakesystem bs = v.getRootObjects(Brakesystem.class).iterator().next();
            bs.getBrakeComponents().stream()
                    .filter(c -> diskId.equals(c.getId()) && c instanceof BrakeDisk)
                    .map(c -> (BrakeDisk) c)
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Disk '" + diskId + "' not found"))
                    .setBrakeDiskThicknessInMM(newThickness);
        });
    }

    private BrakeDisk getDisk(String id) {
        CommittableView view = getBrakesystemView();
        return view.getRootObjects(Brakesystem.class).stream().findFirst()
                .flatMap(bs -> bs.getBrakeComponents().stream()
                        .filter(c -> id.equals(c.getId()) && c instanceof BrakeDisk)
                        .map(c -> (BrakeDisk) c)
                        .findFirst())
                .orElseThrow(() -> new AssertionError("Disk '" + id + "' not found in M1 view"));
    }

    private boolean getDiskPresent(String id) {
        CommittableView view = getBrakesystemView();
        return view.getRootObjects(Brakesystem.class).stream().findFirst()
                .map(bs -> bs.getBrakeComponents().stream()
                        .anyMatch(c -> id.equals(c.getId()) && c instanceof BrakeDisk))
                .orElse(false);
    }

    private boolean getSensorPresent(String id) {
        CommittableView view = getBrakesystemView();
        return view.getRootObjects(Brakesystem.class).stream().findFirst()
                .map(bs -> bs.getBrakeComponents().stream()
                        .anyMatch(c -> id.equals(c.getId()) && c instanceof ABSSensor))
                .orElse(false);
    }

    private boolean getCaliperPresent(String id) {
        CommittableView view = getBrakesystemView();
        return view.getRootObjects(Brakesystem.class).stream().findFirst()
                .map(bs -> bs.getBrakeComponents().stream()
                        .anyMatch(c -> id.equals(c.getId()) && c instanceof BrakeCaliper))
                .orElse(false);
    }

    private boolean getCadNamespacePresent(String id) {
        CommittableView view = getCadView();
        return view.getRootObjects(CAD_Model.class).stream().findFirst()
                .map(cad -> cad.getNamespaces().stream().anyMatch(ns -> id.equals(ns.getId())))
                .orElse(false);
    }

    private float getNumericParamValue(String namespaceId, String paramName) {
        CommittableView view = getCadView();
        return view.getRootObjects(CAD_Model.class).stream().findFirst()
                .flatMap(cad -> cad.getNamespaces().stream()
                        .filter(ns -> namespaceId.equals(ns.getId()))
                        .findFirst())
                .flatMap(ns -> ns.getParameters().stream()
                        .filter(p -> paramName.equals(p.getName()) && p instanceof NumericParameter)
                        .map(p -> (NumericParameter) p)
                        .findFirst())
                .map(NumericParameter::getValue)
                .orElseThrow(() -> new AssertionError(
                        "NumericParameter '" + paramName + "' not found in CAD namespace '" + namespaceId + "'"));
    }

    private CommittableView getBrakesystemView() {
        var selector = rawModel.createSelector(ViewTypeFactory.createIdentityMappingViewType("brakecase"));
        selector.getSelectableElements().stream().filter(e -> e instanceof Brakesystem).forEach(e -> selector.setSelected(e, true));
        return selector.createView().withChangeRecordingTrait();
    }

    private CommittableView getCadView() {
        var selector = rawModel.createSelector(ViewTypeFactory.createIdentityMappingViewType("brakecase"));
        selector.getSelectableElements().stream().filter(e -> e instanceof CAD_Model).forEach(e -> selector.setSelected(e, true));
        return selector.createView().withChangeRecordingTrait();
    }

    private void modifyView(CommittableView view, Consumer<CommittableView> modification) {
        modification.accept(view);
        view.commitChanges();
    }

    /**
     * Commits all staged changes, writes a {@link PostCommitTriggerFile} so
     * {@link VsumPostCommitWatcher} generates a changelog, and waits until that changelog
     * appears in the Git object store. Brackets the call with {@link #waitForGitIndexUnlocked}
     * to avoid racing with the watcher's own git.add()+git.commit() cycle.
     */
    private void commitAndWaitForChangelog(String message, String branch) throws Exception {
        waitForGitIndexUnlocked(tempDir, 3_000);
        git.add().addFilepattern(".").call();
        git.commit().setMessage(message).setAuthor("test", "test@test").call();
        String sha = git.getRepository().resolve("HEAD").getName();
        new PostCommitTriggerFile(tempDir).createTrigger(sha, branch);
        String relPath = ".vitruvius/changelogs/" + branch + "/json/" + sha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, branch, relPath, 10_000);
        waitForGitIndexUnlocked(tempDir, 3_000);
        // The watcher's changelog commit advances HEAD without updating the index.
        // A mixed reset syncs the index to HEAD so subsequent git add calls see a
        // consistent base and do not accidentally re-stage already-committed files.
        git.reset().setMode(org.eclipse.jgit.api.ResetCommand.ResetType.MIXED).setRef("HEAD").call();
    }

    private static void waitForChangelogInObjectStore(Path repoRoot, String branchName, String relPath, long timeoutMs) throws InterruptedException, IOException {
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
            } catch (Exception ignored) {
            }
            Thread.sleep(100);
        }
    }

    // Polls until the git index lock is absent for 4 consecutive checks (~200 ms total).
    // The background watcher does git.add() then git.commit(), releasing the lock briefly
    // between the two calls. A stable-absence window of 200 ms spans that gap reliably.
    private static void waitForGitIndexUnlocked(Path repoRoot, long timeoutMs) throws InterruptedException {
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
            public boolean getConfirmationInteractionResult(UserInteractionOptions.WindowModality m, String title, String message, String pos, String neg, String cancel) {
                return true;
            }

            @Override
            public void getNotificationInteractionResult(UserInteractionOptions.WindowModality m, String title, String message, String pos, UserInteractionOptions.NotificationType type) {
            }

            @Override
            public String getTextInputInteractionResult(UserInteractionOptions.WindowModality m, String title, String message, String pos, String cancel, UserInteractionOptions.InputValidator v) {
                return "";
            }

            @Override
            public int getMultipleChoiceSingleSelectionInteractionResult(UserInteractionOptions.WindowModality m, String title, String message, String pos, String cancel, Iterable<String> choices) {
                return 0;
            }

            @Override
            public Iterable<Integer> getMultipleChoiceMultipleSelectionInteractionResult(UserInteractionOptions.WindowModality m, String title, String message, String pos, String cancel, Iterable<String> choices) {
                return List.of(0);
            }
        };
    }
}
