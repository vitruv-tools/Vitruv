package tools.vitruv.methodologisttemplate.vsum;

import mir.reactions.model2Model2.Model2Model2ChangePropagationSpecification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.interaction.InteractionResultProvider;
import tools.vitruv.change.interaction.UserInteractionOptions;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.branch.data.ModelMergeResult;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.handler.*;
import tools.vitruv.framework.vsum.branch.util.*;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.methodologisttemplate.model.model.ModelFactory;
import tools.vitruv.methodologisttemplate.model.model.System;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end integration test for the complete Vitruvius model versioning workflow.
 *
 * <p>This class contains a single test that walks through the full developer experience
 * in one sequential scenario, exactly as it would happen on the command line:
 *
 * <ol>
 *   <li>Vitruvius starts, installs both Git hooks, and launches both background watchers.</li>
 *   <li>Developer works on main: makes a model change and commits -> pre-commit hook fires
 *       -> VSUM validated -> commit allowed -> changelog written.</li>
 *   <li>Developer switches to a feature branch -> post-checkout hook fires -> VSUM reloads.</li>
 *   <li>Developer makes a model change on the feature branch and commits -> pre-commit hook
 *       fires -> VSUM validated -> commit allowed -> second changelog written.</li>
 *   <li>Developer switches back to main -> post-checkout hook fires -> VSUM reloads again.</li>
 *   <li>Developer makes a final commit on main -> pre-commit hook fires -> VSUM validated
 *       -> commit allowed -> third changelog written.</li>
 * </ol>
 *
 * <p>The one deliberate simplification throughout is that JGit does not execute shell
 * scripts during API calls. Trigger files are therefore written manually in each step
 * to simulate exactly what the installed hook scripts would do on the command line.
 * All other aspects - the real Git repository, real hook files on disk, real VSUM with
 * EMF model files, and both watchers running simultaneously - are genuine.
 */
@DisplayName("Vitruvius model versioning: complete developer workflow")
class VitruviusModelVersioningIntegrationTest {

    private static final String VALIDATION_REQ_MASTER_1 = "aaa1111111111111111111111111111111111111";
    private static final String VALIDATION_REQ_FEATURE_1 = "bbb2222222222222222222222222222222222222";
    private static final String VALIDATION_REQ_MASTER_2 = "ccc3333333333333333333333333333333333333";
    private static final String BRANCH_MASTER = "master";
    private static final String BRANCH_FEAT = "feature-model-update";

    @TempDir
    Path tempDir;

    private Git git;
    private BranchAwareVirtualModel vsum;
    private VsumReloadWatcher reloadWatcher;
    private VsumValidationWatcher validationWatcher;
    private VsumMergeWatcher mergeWatcher;
    private VsumPostCommitWatcher postCommitWatcher;

    @BeforeAll
    static void registerResourceFactory() {
        // Required for EMF to serialize and deserialize .xmi model correctly.
        // Without registration, resource creation might return null and commitChanges might throw NullPointerException inside DefaultStateBasedResolutionStrategy.
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
    }

    @BeforeEach
    void setUp() throws Exception {
        // Explicitly set the initial branch to "master" — JGit's default follows the host git
        // config (often "main" on modern systems), so we pin it to keep the test deterministic.
        git = Git.init().setDirectory(tempDir.toFile()).setInitialBranch(BRANCH_MASTER).call();
        // VsumFileSystemLayout requires at least one commit before the VSUM can initialize.
        // This empty commit satisfies that requirement without interfering with the test scenario.
        git.commit().setMessage("init").setAllowEmpty(true).setAuthor("Vitruvius", "vitruv-dev@example.com").call();
        vsum = createVirtualModel(tempDir);
        addSystem(vsum, tempDir);
    }

    private void addSystem (InternalVirtualModel model, Path projectPath) {
        // create a new root model element and register it in a view at a specific URI
        // modifyView(): propagate changes through view, do not manipulate resources directly
        // registerRoot(): createResource(uri) -> resource.getContents().add(root) -> derive change -> propagate change => create persistent resource inside vsum
        modifyView(getDefaultView(model), view -> view.registerRoot(ModelFactory.eINSTANCE.createSystem(), URI.createFileURI(projectPath.toString() + "/example.model")));
    }

    @AfterEach
    void tearDown() throws Exception {
        // stop both watchers even if test failed to avoid thread leaks.
        if (validationWatcher != null) validationWatcher.stop();
        if (reloadWatcher != null) reloadWatcher.stop();
        if (mergeWatcher != null) mergeWatcher.stop();
        if (postCommitWatcher != null) postCommitWatcher.stop();
        if (git != null) git.close();
    }

    /**
     * Walks through the complete Vitruvius model versioning workflow from start to finish
     * in a single sequential scenario. Each numbered step corresponds to one developer
     * action and is verified before the next step begins.
     */
    @Test
    void modelVersioningWorkflow() throws Exception {
        var reloadTrigger = new ReloadTriggerFile(tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);

        // make an initial commit before hooks are installed so it has no Vitruvius involvement.
        // hooks are not yet active, so neither the pre-commit validation nor the post-commit
        // watcher will fire, keeping the semantic change buffer clean for step 2.
        makeInitialCommit();

        //1.step: Install hooks
        new GitHookInstaller(tempDir).installAllHooks();

        // verify whether hook scripts reference the correct trigger files.
        String preCommit = Files.readString(tempDir.resolve(".git/hooks/pre-commit"));
        assertTrue(preCommit.contains("validate-trigger"), "pre-commit hook must reference the validation trigger file correctly");
        String postCheckout = Files.readString(tempDir.resolve(".git/hooks/post-checkout"));
        assertTrue(postCheckout.contains("reload-trigger"), "post-checkout must reference the reload trigger file correctly");
        String postCommit = Files.readString(tempDir.resolve(".git/hooks/post-commit"));
        assertTrue(postCommit.contains("post-commit-trigger"), "post-commit hook must reference the post-commit trigger file");
        String postMerge = Files.readString(tempDir.resolve(".git/hooks/post-merge"));
        assertTrue(postMerge.contains("merge-trigger"), "post-merge hook must reference the post-merge trigger file");

        // start all watchers after hooks are installed and initial commit is done
        reloadWatcher = new VsumReloadWatcher(vsum, tempDir);
        validationWatcher = new VsumValidationWatcher(vsum, tempDir);
        mergeWatcher = new VsumMergeWatcher(vsum, tempDir);
        postCommitWatcher = new VsumPostCommitWatcher(tempDir);
        postCommitWatcher.attachSemanticChangeTracking(
                vsum.getChangeBuffer(),
                vsum::getUuidResolver,
                vsum::getViewSourceModels);

        reloadWatcher.start();
        validationWatcher.start();
        mergeWatcher.start();
        postCommitWatcher.start();

        //2.step: Commit on master branch - developer modifies the model
        // this is a real change that will be validated before the commit is allowed to proceed.
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = ModelFactory.eINSTANCE.createComponent();
            component.setName("MainComponent");
            system.getComponents().add(component);
        });

        // on the command line, "git commit" would trigger the pre-commit hook script, which writes .vitruvius/validate-trigger containing the commit sha and branch name
        //jgit does not execute hook scripts, so we write the trigger file manually here to simulate exactly what the installed hook script would do.
        String requestId1 = triggerFile.createTrigger(VALIDATION_REQ_MASTER_1, BRANCH_MASTER);

        // in the background: validationWatcher detects the trigger file (within 500ms), call PreCommitHandler.validate() against the real vsum
        // and write the 2 result files: .vitruvius/results/<requestId>.txt & .vitruvius/results/<requestId>.json
        // wait for both files to appear before reading to avoid race condition
        waitForBothResultFiles(resultFile, requestId1);

        // on the command line, the hook script reads the JSON result to decide whether to exit 0 (allow the commit) or exit 1 (block the commit with an error message).
        ValidationResult result1 = resultFile.readResult(requestId1);
        assertNotNull(result1, "result must be written after the first validation");
        assertTrue(result1.isValid(), "the initial model must pass validation on master");
        resultFile.deleteResult(requestId1);

        // on the command line, the pre-commit hook exits 0 and Git creates the commit, assigning a real SHA.
        // the post-commit hook then fires and write .vitruvius/post-commit-trigger with the real SHA from git rev-parse HEAD.
        // we simulate this by making a real JGit commit and using its real SHA.
        git.commit()
                .setMessage("Add MainComponent")
                .setAuthor("Vitruvius", "vitruv-dev@example.com")
                .call();
        // Read the user commit SHA before writing the trigger. The changelog filename is derived
        // from this SHA. After the trigger fires, VsumPostCommitWatcher creates a new changelog
        // commit on top, advancing HEAD — but the changelog is still named after this SHA.
        String realSha1 = git.getRepository().resolve("HEAD").getName();
        var postCommitTrigger = new PostCommitTriggerFile(tempDir);
        postCommitTrigger.createTrigger(realSha1, BRANCH_MASTER);

        Path changelog1 = changelogPath(realSha1, BRANCH_MASTER);
        // Wait until the changelog is committed to the Git object store on master.
        // This guarantees the watcher's commit is done before the branch switch below,
        // preventing it from landing on the wrong branch.
        String changelog1RelPath = ".vitruvius/changelogs/" + BRANCH_MASTER + "/json/" + realSha1.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, BRANCH_MASTER, changelog1RelPath, 5000);

        // The changelog is committed directly to the git object store and deleted from disk
        // (to prevent CheckoutConflictException on branch switch). Read from the object store.
        String changelogContent1 = readChangelogFromObjectStore(tempDir, BRANCH_MASTER, changelog1RelPath);
        assertNotNull(changelogContent1, "a changelog must be written after the first passing validation on main");
        assertTrue(changelogContent1.contains("\"formatVersion\""), "changelog must contain the formatVersion field");
        assertTrue(changelogContent1.contains(realSha1), "changelog must reference the full commit SHA");
        assertTrue(changelogContent1.contains("\"branch\": \"" + BRANCH_MASTER + "\""), "changelog must contain the branch name");
        assertTrue(changelogContent1.contains("\"author\""), "changelog must contain the author field");
        assertTrue(changelogContent1.contains("\"fileChanges\""), "changelog must contain the fileChanges array");


        //3.step: Developer switches to a feature branch
        // this is a real JGit branch switch -> the repository HEAD moves to BRANCH_FEAT
        git.branchCreate().setName(BRANCH_FEAT).call();
        git.checkout().setName(BRANCH_FEAT).call();

        // on the command line, "git checkout" would fire the post-checkout hook script, which writes .vitruvius/reload-trigger containing the new branch name.
        // JGit does not execute hook scripts, so we write the trigger file manually here.
        reloadTrigger.createTrigger(BRANCH_FEAT, BRANCH_MASTER);

        // in the background: VsumReloadWatcher detects the trigger file (within 500ms), calls BranchAwareVirtualModel.switchBranch()
        // -> VirtualModelImpl.reinitialize() -> ResourceRepositoryImpl -> reloads all .xmi model files from disk, then deletes the trigger file.
        waitUntil(() -> !reloadTrigger.exists(), 2000);
        assertFalse(reloadTrigger.exists(), "reload trigger must be consumed by VsumReloadWatcher after the branch switch");

        //4.step: Developer commits on the feature branch
        // After a VSUM reload, existing view references are stale. A fresh view must be obtained.
        CommittableView viewOnFeat = getDefaultView(vsum);
        assertNotNull(viewOnFeat, "a fresh view must be obtainable from the VSUM after the reload");

        // the developer adds a feature-specific component
        // this change exists only on the feature branch and will be isolated from the main branch model state.
        modifyView(viewOnFeat, view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = ModelFactory.eINSTANCE.createComponent();
            component.setName("FeatureComponent");
            system.getComponents().add(component);
        });

        // same pre-commit flow as step 2: trigger written manually -> VsumValidationWatcher
        // validates the VSUM (now containing FeatureComponent) -> result files written.
        String requestId2 = triggerFile.createTrigger(VALIDATION_REQ_FEATURE_1, BRANCH_FEAT);
        waitForBothResultFiles(resultFile, requestId2);

        ValidationResult result2 = resultFile.readResult(requestId2);
        assertNotNull(result2, "result must be written after the feature branch validation");
        assertTrue(result2.isValid(), "the model with FeatureComponent must pass validation on the feature branch");
        resultFile.deleteResult(requestId2);

        // pre-commit passes -> Git creates the commit -> post-commit hook fires with real SHA.
        // we simulate: make a real JGit commit, then write the post-commit trigger.
        git.commit()
                .setMessage("Add FeatureComponent")
                .setAuthor("Vitruvius", "vitruv-dev@example.com")
                .call();
        String realSha2 = git.getRepository().resolve("HEAD").getName();

        postCommitTrigger.createTrigger(realSha2, BRANCH_FEAT);

        Path changelog2 = changelogPath(realSha2, BRANCH_FEAT);
        // Wait until committed to the object store on BRANCH_FEAT before switching back to master.
        String changelog2RelPath = ".vitruvius/changelogs/" + BRANCH_FEAT + "/json/" + realSha2.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, BRANCH_FEAT, changelog2RelPath, 8000);
        String changelogContent2 = readChangelogFromObjectStore(tempDir, BRANCH_FEAT, changelog2RelPath);
        assertNotNull(changelogContent2, "a changelog must be written automatically for the feature branch commit");
        assertTrue(changelogContent2.contains("\"formatVersion\""), "feature changelog must contain the formatVersion field");
        assertTrue(changelogContent2.contains(realSha2), "feature changelog must contain the correct commit SHA");
        assertTrue(changelogContent2.contains("\"branch\": \"" + BRANCH_FEAT + "\""), "feature changelog must reference the feature branch name");
        assertTrue(changelogContent2.contains("\"fileChanges\""), "feature changelog must contain the fileChanges array");

        // each commit produces a distinct changelog file named after its short SHA.
        assertNotEquals(changelog1, changelog2, "each commit must produce a distinct changelog file");


        //5.step: Developer switches back to master
        git.checkout().setName(BRANCH_MASTER).call();
        reloadTrigger.createTrigger(BRANCH_MASTER, BRANCH_FEAT);
        waitUntil(() -> !reloadTrigger.exists(), 2000);
        assertFalse(reloadTrigger.exists(), "reload trigger must be consumed by VsumReloadWatcher after switching back to main");

        // fresh view required after the reload; the VSUM now reflects the main branch state, which does not include FeatureComponent
        CommittableView viewBackOnMain = getDefaultView(vsum);
        assertNotNull(viewBackOnMain, "a fresh view must be obtainable after the reload back to main");

        //6.step: Developer adds another component on main and commits
        modifyView(viewBackOnMain, view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = ModelFactory.eINSTANCE.createComponent();
            component.setName("MainComponent2");
            system.getComponents().add(component);
        });

        // same pre-commit flow as steps 2 and 4: trigger written -> VsumValidationWatcher
        // validates -> result files written -> changelog written automatically.
        String requestId3 = triggerFile.createTrigger(VALIDATION_REQ_MASTER_2, BRANCH_MASTER);
        waitForBothResultFiles(resultFile, requestId3);

        ValidationResult result3 = resultFile.readResult(requestId3);
        assertNotNull(result3, "result must be written after the second main validation");
        assertTrue(result3.isValid(), "the model with MainComponent2 must pass validation after returning to main");
        resultFile.deleteResult(requestId3);

        // pre-commit passes -> real JGit commit -> post-commit hook fires with real SHA
        git.commit()
                .setMessage("Add MainComponent2")
                .setAuthor("Vitruvius", "vitruv-dev@example.com")
                .call();
        String realSha3 = git.getRepository().resolve("HEAD").getName();
        postCommitTrigger.createTrigger(realSha3, BRANCH_MASTER);

        Path changelog3 = changelogPath(realSha3, BRANCH_MASTER);
        String changelog3RelPath = ".vitruvius/changelogs/" + BRANCH_MASTER + "/json/" + realSha3.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, BRANCH_MASTER, changelog3RelPath, 5000);
        String changelogContent3 = readChangelogFromObjectStore(tempDir, BRANCH_MASTER, changelog3RelPath);
        assertNotNull(changelogContent3, "a changelog must be written automatically for the second main commit");
        assertTrue(changelogContent3.contains(realSha3), "second main changelog must contain the correct commit SHA");
        assertTrue(changelogContent3.contains("\"branch\": \"" + BRANCH_MASTER + "\""), "second main changelog must reference the main branch");

        // the .vitruvius/changelogs/ directory now holds three independent files, one per commit SHA, forming the complete VSUM version history for this session.
        assertNotEquals(changelog1, changelog3, "the two main changelogs must be distinct files for different commits");
        assertNotEquals(changelog2, changelog3, "the feature and second main changelogs must be distinct files");


        //7.step: Developer merges the feature branch into master
        // JGit creates a merge commit with two parents, combining both branches' model state.
        MergeResult mergeOutcome  = git.merge()
                .include(git.getRepository().resolve(BRANCH_FEAT))
                .setMessage("merge feature-model-update into master")
                .call();
        // extract the real merge commit SHA from JGit, his is the actual SHA that Git assigned to the merge commit
        String realMergeCommitSha = mergeOutcome.getNewHead().getName();
        assertNotNull(realMergeCommitSha, "JGit must produce a real merge commit SHA after the merge completes");

        // on command line, git merge would fire the post-merge hook script, which writes .vitruvius/merge-trigger containing the merge commit sha, source branch and target branch
        // jgit does not execute hook scripts, so we need to write the trigger file manually here
        var mergeTriggerFile = new MergeTriggerFile(tempDir);
        var mergeResultFile = new MergeResultFile(tempDir);
        String mergeRequestId = mergeTriggerFile.createTrigger(realMergeCommitSha, BRANCH_FEAT, BRANCH_MASTER);

        // in the background: VsumMergeWatcher detects the trigger file within 500ms, calls PostMergeHandler.validate() against the real merged vsum state,
        // writes the results files and writes permanent merge metadata record to .vitruvius/merges/<sha>.metadata
        // unlike pre-commit, this is non-blocking as the merge is already complete regardless of the outcome
        waitForMergeResultFiles(mergeResultFile, mergeRequestId, 3000);

        // the post-merge hook reads the result and displays a warning report if needed.
        // the result is always written - passing or failing - because the merge is done.
        ValidationResult mergeResult = mergeResultFile.readResult(mergeRequestId);
        assertNotNull(mergeResult, "merge result must be written after the merge trigger is processed");
        assertTrue(mergeResult.isValid(), "the merged VSUM state combining main and feature components must be consistent");

        // the permanent merge metadata file must be written to .vitruvius/merges/
        // regardless of the validation outcome - it is the audit trail for the merge (MG-6).
        assertTrue(mergeResultFile.metadataExists(realMergeCommitSha), "merge metadata must be written after the post-merge trigger is processed");

        // read and verify the metadata content captures the full merge context.
        var metadata = mergeResultFile.readMetadata(realMergeCommitSha);
        assertNotNull(metadata, "merge metadata must be readable after being written");
        assertEquals(realMergeCommitSha, metadata.get("mergeCommitSha"), "metadata must record the correct merge commit SHA");
        assertEquals(BRANCH_FEAT, metadata.get("sourceBranch"), "metadata must record the feature branch as the source of the merge");
        assertEquals(BRANCH_MASTER, metadata.get("targetBranch"), "metadata must record main as the target that received the merge");
        assertEquals(true, metadata.get("valid"), "metadata must record that the merged state passed validation");

        // the merge result files are request-scoped and cleaned up by the hook after reading.
        // the metadata file is permanent and must not be deleted.
        mergeResultFile.deleteResult(mergeRequestId);
        assertTrue(mergeResultFile.metadataExists(realMergeCommitSha), "metadata file must survive result file cleanup - it is a permanent record");
    }


    /**
     * Tests XMI merge conflict detection and post-merge validation.
     *
     * <p>This test demonstrates what happens when Git's text-based merge creates conflict
     * markers inside VSUM model files. The scenario creates two branches that modify the
     * same component in conflicting ways, forces Git to insert conflict markers into the
     * XMI file, and verifies that the post-merge hook correctly detects and reports the
     * invalid XML as a validation failure.
     *
     * <p>The workflow is:
     * <ol>
     *   <li>Create a base commit with a Component named "BaseComponent"</li>
     *   <li>Branch A: rename BaseComponent to "ServiceA" and commit</li>
     *   <li>Branch B: rename BaseComponent to "ServiceB" (from same base) and commit</li>
     *   <li>Merge branch-a into branch-b → Git creates conflict markers in XMI</li>
     *   <li>Post-merge validation detects the conflict markers and fails</li>
     *   <li>Merge metadata is written with valid=false</li>
     * </ol>
     */
    @Test
    @DisplayName("Merge with XMI conflicts: conflict markers detected by post-merge validation")
    void mergeWithXmiConflict() throws Exception {
        var reloadTrigger = new ReloadTriggerFile(tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);
        var postCommitTrigger = new PostCommitTriggerFile(tempDir);
        var mergeTrigger = new MergeTriggerFile(tempDir);
        var mergeResultFile = new MergeResultFile(tempDir);

        // create an initial commit before hooks are installed so it has no Vitruvius involvement.
        // Without this ordering, makeInitialCommit's post-commit trigger would drain the change
        // buffer (which holds setUp's addSystem changes) before step 1's modifyView runs,
        // leaving an empty buffer for the first real commit's changelog.
        makeInitialCommit();

        // install all four hooks and start all four watchers
        new GitHookInstaller(tempDir).installAllHooks();
        reloadWatcher = new VsumReloadWatcher(vsum, tempDir);
        validationWatcher = new VsumValidationWatcher(vsum, tempDir);
        postCommitWatcher = new VsumPostCommitWatcher(tempDir);
        postCommitWatcher.attachSemanticChangeTracking(
                vsum.getChangeBuffer(),
                vsum::getUuidResolver,
                vsum::getViewSourceModels);
        mergeWatcher = new VsumMergeWatcher(vsum, tempDir);
        reloadWatcher.start();
        validationWatcher.start();
        postCommitWatcher.start();
        mergeWatcher.start();

        // initialize BranchManager with PostCheckoutHandler so that branch switches
        // automatically trigger VSUM reloads via the handler callback
        var branchMgr = new BranchManager(tempDir);
        var postCheckoutHandler = new PostCheckoutHandler(vsum);
        branchMgr.setPostCheckoutHandler(postCheckoutHandler);

        //1.step: Create base commit with component "BaseComponent" as the common ancestor
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = ModelFactory.eINSTANCE.createComponent();
            component.setName("BaseComponent");
            system.getComponents().add(component);
        });

        // stage all changes before validation to ensure the commit includes only
        // the BaseComponent addition and no unrelated files from previous operations
        git.add().addFilepattern(".").call();

        // trigger pre-commit validation using the real parent commit SHA
        String parentSha1 = git.getRepository().resolve("HEAD").getName();
        String baseRequestId = triggerFile.createTrigger(parentSha1, BRANCH_MASTER);
        waitForBothResultFiles(resultFile, baseRequestId);

        // validation must pass before the commit is allowed to proceed
        ValidationResult baseResult = resultFile.readResult(baseRequestId);
        assertTrue(baseResult.isValid(), "base commit with BaseComponent must pass validation");
        resultFile.deleteResult(baseRequestId);

        // create the real Git commit and extract its SHA for the post-commit trigger
        var baseCommit = git.commit()
                .setMessage("Add BaseComponent")
                .setAuthor("Vitruvius", "vitruv-dev@example.com")
                .call();
        String baseCommitSha = baseCommit.getName();

        // simulate post-commit hook execution: write the trigger with the real commit SHA
        // so VsumPostCommitWatcher can generate a changelog with traceable SHA
        postCommitTrigger.createTrigger(baseCommitSha, BRANCH_MASTER);

        // Wait until the changelog is committed to the Git object store on master.
        // waitForChangelogInObjectStore guarantees the watcher's git.commit() has completed
        // (including the 'master' ref update), so BranchManager.createBranch() cannot
        // race for the same ref lock and produce a LOCK_FAILURE.
        String baseChangelogRelPath = ".vitruvius/changelogs/" + BRANCH_MASTER + "/json/" + baseCommitSha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, BRANCH_MASTER, baseChangelogRelPath, 5000);

        //2.step: Branch A - rename "BaseComponent" to "ServiceA"
        branchMgr.createBranch("branch-a", "master");
        branchMgr.switchBranch("branch-a");

        // after VSUM reload, a fresh view must be obtained to see the current branch state.
        // modifying the Component to ServiceA on this branch while branch-b will independently
        // modify it to ServiceB, creating the conflict.
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = system.getComponents().get(0);
            assertEquals("BaseComponent", component.getName(), "branch-a must start with BaseComponent from the base commit");
            component.setName("ServiceA");
        });

        // stage changes and trigger validation with real parent SHA
        git.add().addFilepattern(".").call();
        String parentShaA = git.getRepository().resolve("HEAD").getName();
        String requestIdA = triggerFile.createTrigger(parentShaA, "branch-a");
        waitForBothResultFiles(resultFile, requestIdA);

        // validation passes: ServiceA is a valid model state
        assertTrue(resultFile.readResult(requestIdA).isValid(), "branch-a commit with ServiceA must pass validation");
        resultFile.deleteResult(requestIdA);

        // commit on branch-a with the real SHA, then trigger changelog generation
        var commitA = git.commit()
                .setMessage("Rename to ServiceA")
                .setAuthor("Vitruvius", "vitruv-dev@example.com")
                .call();
        String commitASha = commitA.getName();
        postCommitTrigger.createTrigger(commitASha, "branch-a");

        // Wait until the branch-a changelog is in the object store and the index is unlocked.
        // branchMgr.switchBranch() does a git ref update; both checks ensure the watcher's
        // git.commit() is fully done before we attempt the switch.
        String changelogARelPath = ".vitruvius/changelogs/branch-a/json/" + commitASha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, "branch-a", changelogARelPath, 5000);
        waitForGitIndexUnlocked(tempDir, 3000);

        //3.step: Branch B - rename "BaseComponent" to "ServiceB" from the same base commit
        branchMgr.switchBranch(BRANCH_MASTER);
        branchMgr.createBranch("branch-b", "master");
        branchMgr.switchBranch("branch-b");

        // after reload, the VSUM reflects the master branch state: BaseComponent is present,
        // and ServiceA does not exist here because it only exists on branch-a.
        // modify BaseComponent to ServiceB, creating a conflicting change.
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = system.getComponents().get(0);
            assertEquals("BaseComponent", component.getName(), "branch-b must start with BaseComponent, not ServiceA from branch-a");
            component.setName("ServiceB");
        });

        // stage changes and trigger validation
        git.add().addFilepattern(".").call();
        String parentShaB = git.getRepository().resolve("HEAD").getName();
        String requestIdB = triggerFile.createTrigger(parentShaB, "branch-b");
        waitForBothResultFiles(resultFile, requestIdB);

        // validation passes: ServiceB is valid on its own branch
        assertTrue(resultFile.readResult(requestIdB).isValid(), "branch-b commit with ServiceB must pass validation");
        resultFile.deleteResult(requestIdB);

        // commit on branch-b and trigger changelog generation
        var commitB = git.commit()
                .setMessage("Rename to ServiceB")
                .setAuthor("Vitruvius", "vitruv-dev@example.com")
                .call();
        String commitBSha = commitB.getName();
        postCommitTrigger.createTrigger(commitBSha, "branch-b");

        // Wait until the branch-b changelog is in the object store and the index is unlocked
        // before the merge, for the same reason as above.
        String changelogBRelPath = ".vitruvius/changelogs/branch-b/json/" + commitBSha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, "branch-b", changelogBRelPath, 5000);
        waitForGitIndexUnlocked(tempDir, 3000);

        //4.step: Merge branch-a into branch-b - Git inserts conflict markers into XMI

        // clean up any stray lock files that might cause the merge to fail with DIRTY_WORKTREE
        Path validationLock = tempDir.resolve(".vitruvius/.validation.lock");
        if (Files.exists(validationLock)) {
            Files.delete(validationLock);
        }

        // perform the merge: Git will create conflict markers in example.model
        MergeResult mergeResult = git.merge()
                .include(git.getRepository().resolve("branch-a"))
                .call();

        // verify that Git detected the conflict and refused to auto-merge
        assertEquals(MergeResult.MergeStatus.CONFLICTING, mergeResult.getMergeStatus(), "Git must detect conflicting changes to the same component");
        assertFalse(mergeResult.getConflicts().isEmpty(), "conflicts map must not be empty when merge fails");

        //5.step: Inspect XMI file and verify that Git inserted conflict markers
        Path xmiFile = tempDir.resolve("example.model");
        assertTrue(Files.exists(xmiFile), "XMI file must exist after the merge");

        String xmiContent = Files.readString(xmiFile);

        // verify that Git inserted all three conflict marker strings into the XMI file
        assertTrue(xmiContent.contains("<<<<<<<"), "XMI must contain Git conflict marker '<<<<<<<'");
        assertTrue(xmiContent.contains("======="), "XMI must contain conflict separator '======='");
        assertTrue(xmiContent.contains(">>>>>>>"), "XMI must contain Git conflict marker '>>>>>>>'");
        assertTrue(xmiContent.contains("ServiceA") || xmiContent.contains("ServiceB"), "XMI must contain at least one of the conflicting component names");

        //6.step: Post-merge validation detects conflict markers
        // For conflicting merges, Git does not create a merge commit. A placeholder SHA is used.
        String conflictMergeSha = "conflict-" +java.lang.System.currentTimeMillis();

        // simulate post-merge hook execution: write the trigger file manually
        String mergeRequestId = mergeTrigger.createTrigger(conflictMergeSha, "branch-a", "branch-b");

        // in the background: VsumMergeWatcher detects the trigger, calls PostMergeHandler.validate(),
        // which scans example.model on disk and finds the conflict markers, then writes the result
        // and metadata files.
        waitForMergeResultFiles(mergeResultFile, mergeRequestId, 3000);

        // read the validation result: must fail because the XMI file contains conflict markers
        ValidationResult mergeValidation = mergeResultFile.readResult(mergeRequestId);
        assertNotNull(mergeValidation, "merge validation result must be written even when validation fails");
        assertFalse(mergeValidation.isValid(), "merge with XMI conflict markers must fail validation");

        // verify that the error message specifically mentions conflict markers or the affected file
        boolean hasConflictError = mergeValidation.getErrors().stream()
                .anyMatch(e -> e.toLowerCase().contains("conflict") || e.contains("example.model"));
        assertTrue(hasConflictError, "error message must mention conflict markers or the conflicted file name");

        //7.step: Verify metadata is written even for the failed merge (audit trail)
        assertTrue(mergeResultFile.metadataExists(conflictMergeSha), "metadata must be written even when merge validation fails");

        // read and verify the metadata content
        var metadata = mergeResultFile.readMetadata(conflictMergeSha);
        assertNotNull(metadata, "metadata must be readable");
        assertEquals(conflictMergeSha, metadata.get("mergeCommitSha"), "metadata must record the placeholder merge SHA");
        assertEquals("branch-a", metadata.get("sourceBranch"), "metadata must record branch-a as the source");
        assertEquals("branch-b", metadata.get("targetBranch"), "metadata must record branch-b as the target");
        assertEquals(false, metadata.get("valid"), "metadata must show valid=false for conflicted merge");
    }

    /**
     * Verifies that the semantic pre-check in {@link MergeManager#merge(String)} detects a
     * direct element-feature conflict and blocks the JGit merge before touching any files.
     *
     * <p>Scenario:
     * <ol>
     *   <li>Base commit: "BaseComponent" added on master.</li>
     *   <li>branch-a renames it to "ServiceA" and commits with changelog.</li>
     *   <li>branch-b renames it to "ServiceB" (from the same base) and commits with changelog.</li>
     *   <li>{@link MergeManager#merge(String)} is called from branch-b to merge branch-a.</li>
     * </ol>
     *
     * <p>Both changelogs record a change to the same element UUID on the same "name" feature
     * with different target values. The semantic pre-check must detect this conflict, return
     * {@link ModelMergeResult.MergeStatus#CONFLICTING} with {@code "semantic://"} descriptors,
     * and leave the working-directory XMI file without conflict markers (JGit was never invoked).
     */
    @Test
    @DisplayName("Semantic pre-check: MergeManager blocks merge when changelogs reveal element-feature conflict")
    void semanticPreCheckBlocksMergeOnElementConflict() throws Exception {
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);
        var postCommitTrigger = new PostCommitTriggerFile(tempDir);

        makeInitialCommit();

        new GitHookInstaller(tempDir).installAllHooks();
        reloadWatcher = new VsumReloadWatcher(vsum, tempDir);
        validationWatcher = new VsumValidationWatcher(vsum, tempDir);
        postCommitWatcher = new VsumPostCommitWatcher(tempDir);
        postCommitWatcher.attachSemanticChangeTracking(
                vsum.getChangeBuffer(),
                vsum::getUuidResolver,
                vsum::getViewSourceModels);
        mergeWatcher = new VsumMergeWatcher(vsum, tempDir);
        reloadWatcher.start();
        validationWatcher.start();
        postCommitWatcher.start();
        mergeWatcher.start();

        var branchMgr = new BranchManager(tempDir);
        var postCheckoutHandler = new PostCheckoutHandler(vsum);
        branchMgr.setPostCheckoutHandler(postCheckoutHandler);

        //1.step: Base commit with "BaseComponent" as common ancestor for both branches
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            var component = ModelFactory.eINSTANCE.createComponent();
            component.setName("BaseComponent");
            system.getComponents().add(component);
        });
        git.add().addFilepattern(".").call();
        String parentSha = git.getRepository().resolve("HEAD").getName();
        String baseReqId = triggerFile.createTrigger(parentSha, BRANCH_MASTER);
        waitForBothResultFiles(resultFile, baseReqId);
        assertTrue(resultFile.readResult(baseReqId).isValid(), "base commit must pass validation");
        resultFile.deleteResult(baseReqId);

        git.commit().setMessage("Add BaseComponent")
                .setAuthor("Vitruvius", "vitruv-dev@example.com").call();
        // Read the user commit SHA before writing the trigger. VsumPostCommitWatcher will later
        // create a new changelog commit on top; the changelog is still named after this SHA.
        String baseSha = git.getRepository().resolve("HEAD").getName();
        postCommitTrigger.createTrigger(baseSha, BRANCH_MASTER);
        // Wait until the changelog is committed to the Git object store on master.
        // SemanticConflictDetector reads via TreeWalk (committed tree), so the file must be
        // in a committed tree before we switch branches.
        String baseChangelogRelPath = ".vitruvius/changelogs/master/json/" + baseSha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, BRANCH_MASTER, baseChangelogRelPath, 10000);

        //2.step: Branch-a renames "BaseComponent" to "ServiceA"
        branchMgr.createBranch("branch-a", "master");
        branchMgr.switchBranch("branch-a");
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            system.getComponents().get(0).setName("ServiceA");
        });
        git.add().addFilepattern(".").call();
        String parentShaA = git.getRepository().resolve("HEAD").getName();
        String reqIdA = triggerFile.createTrigger(parentShaA, "branch-a");
        waitForBothResultFiles(resultFile, reqIdA);
        assertTrue(resultFile.readResult(reqIdA).isValid(), "branch-a commit must pass validation");
        resultFile.deleteResult(reqIdA);

        git.commit().setMessage("Rename to ServiceA")
                .setAuthor("Vitruvius", "vitruv-dev@example.com").call();
        String commitASha = git.getRepository().resolve("HEAD").getName();
        postCommitTrigger.createTrigger(commitASha, "branch-a");
        String changelogARelPath = ".vitruvius/changelogs/branch-a/json/" + commitASha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, "branch-a", changelogARelPath, 10000);

        //3.step: Branch-b renames "BaseComponent" to "ServiceB" from the same base
        branchMgr.switchBranch(BRANCH_MASTER);
        branchMgr.createBranch("branch-b", "master");
        branchMgr.switchBranch("branch-b");
        modifyView(getDefaultView(vsum), view -> {
            var system = view.getRootObjects(System.class).iterator().next();
            system.getComponents().get(0).setName("ServiceB");
        });
        git.add().addFilepattern(".").call();
        String parentShaB = git.getRepository().resolve("HEAD").getName();
        String reqIdB = triggerFile.createTrigger(parentShaB, "branch-b");
        waitForBothResultFiles(resultFile, reqIdB);
        assertTrue(resultFile.readResult(reqIdB).isValid(), "branch-b commit must pass validation");
        resultFile.deleteResult(reqIdB);

        git.commit().setMessage("Rename to ServiceB")
                .setAuthor("Vitruvius", "vitruv-dev@example.com").call();
        String commitBSha = git.getRepository().resolve("HEAD").getName();
        postCommitTrigger.createTrigger(commitBSha, "branch-b");
        String changelogBRelPath = ".vitruvius/changelogs/branch-b/json/" + commitBSha.substring(0, 7) + ".json";
        waitForChangelogInObjectStore(tempDir, "branch-b", changelogBRelPath, 10000);

        // Diagnostic: run the conflict detector directly to verify changelogs are loaded
        // and conflicts are found before testing MergeManager.
        var detector = new tools.vitruv.framework.vsum.branch.SemanticConflictDetector(tempDir);
        var replayResult = detector.analyzeBranches("branch-b", "branch-a");
        assertTrue(replayResult.hasConflicts(),
                "SemanticConflictDetector must find a direct conflict on the 'name' feature "
                + "(changesOnA=" + replayResult.getChangesOnA().size()
                + ", changesOnB=" + replayResult.getChangesOnB().size()
                + ", conflicts=" + replayResult.getConflicts().size() + ")");

        //4.step: MergeManager pre-check must detect the element-feature conflict and block
        MergeManager mergeManager = new MergeManager(tempDir);
        mergeManager.suppressTriggerFile(); // no watcher active for the merge trigger in this test
        ModelMergeResult result = mergeManager.merge("branch-a");

        // The pre-check detected a conflict on the same element UUID / "name" feature.
        // MergeManager must return CONFLICTING without ever invoking JGit's merge command.
        assertEquals(ModelMergeResult.MergeStatus.CONFLICTING, result.getStatus(),
                "MergeManager must return CONFLICTING when semantic pre-check finds a direct conflict");

        // Conflict descriptors must follow the "semantic://uuid/feature" format written by
        // runSemanticPreCheck(), not real file paths from JGit conflict markers.
        assertFalse(result.getConflictingFiles().isEmpty(),
                "at least one semantic conflict descriptor must be reported");
        assertTrue(result.getConflictingFiles().stream()
                .anyMatch(f -> f.startsWith("semantic://")),
                "conflict descriptors must use the 'semantic://' prefix from the pre-check");

        // Because MergeManager returned before invoking git.merge(), the working-directory
        // XMI file must be clean, no Git conflict markers inserted.
        String xmiContent = Files.readString(tempDir.resolve("example.model"));
        assertFalse(xmiContent.contains("<<<<<<<"),
                "XMI file must NOT contain Git conflict markers: pre-check blocked the merge");
        assertFalse(xmiContent.contains("======="),
                "XMI file must not contain conflict separator when blocked by pre-check");
    }

    private static InteractionResultProvider createNonInteractiveProvider() {
        return new InteractionResultProvider() {
            @Override
            public boolean getConfirmationInteractionResult(
                    UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String neg, String cancel) {
                return true;
            }
            @Override
            public void getNotificationInteractionResult(
                    UserInteractionOptions.WindowModality m,
                    String title, String message, String pos,
                    UserInteractionOptions.NotificationType type) {}
            @Override
            public String getTextInputInteractionResult(
                    UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String cancel,
                    UserInteractionOptions.InputValidator validator) {
                return "";
            }
            @Override
            public int getMultipleChoiceSingleSelectionInteractionResult(
                    UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String cancel,
                    Iterable<String> choices) {
                return 0;
            }
            @Override
            public Iterable<Integer> getMultipleChoiceMultipleSelectionInteractionResult(
                    UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String cancel,
                    Iterable<String> choices) {
                return List.of(0);
            }
        };
    }

    /**
     * Polls until {@code relPath} appears in the committed tree of {@code branchName} via TreeWalk.
     * This is the same check SemanticConflictDetector performs, so returning here guarantees
     * the detector will find the changelog file.
     */
    private static void waitForChangelogInObjectStore(Path repoRoot, String branchName,
            String relPath, long timeoutMs) throws InterruptedException, IOException {
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        while (java.lang.System.currentTimeMillis() < deadline) {
            try (org.eclipse.jgit.api.Git g = org.eclipse.jgit.api.Git.open(repoRoot.toFile());
                 org.eclipse.jgit.revwalk.RevWalk walk = new org.eclipse.jgit.revwalk.RevWalk(g.getRepository())) {
                org.eclipse.jgit.lib.ObjectId branchHead = g.getRepository().resolve("refs/heads/" + branchName);
                if (branchHead != null) {
                    org.eclipse.jgit.revwalk.RevCommit headCommit = walk.parseCommit(branchHead);
                    try (org.eclipse.jgit.treewalk.TreeWalk tw = org.eclipse.jgit.treewalk.TreeWalk.forPath(
                            g.getRepository(), relPath, headCommit.getTree())) {
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
    private static void waitForGitIndexUnlocked(Path repoRoot, long timeoutMs) throws InterruptedException {
        Path lockFile = repoRoot.resolve(".git/index.lock");
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        int stableCount = 0;
        while (java.lang.System.currentTimeMillis() < deadline) {
            if (!Files.exists(lockFile)) {
                stableCount++;
                if (stableCount >= 4) return;
            } else {
                stableCount = 0;
            }
            Thread.sleep(50);
        }
    }

    private static void waitUntil(java.util.function.BooleanSupplier condition, long timeoutMs) throws InterruptedException {
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        while (!condition.getAsBoolean() && java.lang.System.currentTimeMillis() < deadline) {
            Thread.sleep(50);
        }
    }

    private static void waitForMergeResultFiles(MergeResultFile resultFile, String requestId, long timeoutMs) throws InterruptedException {
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        while (java.lang.System.currentTimeMillis() < deadline) {
            if (Files.exists(resultFile.getTextResultPath(requestId)) && Files.exists(resultFile.getJsonResultPath(requestId))) {
                return;
            }
            Thread.sleep(50);
        }
    }
    /**
     * Creates an initial Git commit so JGit can create and switch branches.
     * JGit requires at least one commit before branch operations are possible.
     */
    private void makeInitialCommit() throws Exception {
        Files.writeString(tempDir.resolve(".vitruvius-init"), "vitruvius init");
        git.add().addFilepattern(".").call();
        git.commit().setMessage("Initial message").setAuthor("vitruv-user", "vitruv-user@gmail.com").call();
    }

    private static void waitForBothResultFiles(ValidationResultFile resultFile, String requestId) throws InterruptedException {
        long deadline = java.lang.System.currentTimeMillis() + 2000;
        while (java.lang.System.currentTimeMillis() < deadline) {
            if (Files.exists(resultFile.getTextResultPath(requestId)) && Files.exists(resultFile.getJsonResultPath(requestId))) {
                return;
            }
            Thread.sleep(50);
        }
    }

    private Path changelogPath(String commitSha, String branch) {
        return tempDir.resolve(".vitruvius").resolve("changelogs").resolve(branch).resolve("json").resolve(commitSha.substring(0, 7) + ".json");
    }

    private static void waitUntilFileExists(Path path, long timeoutMs) throws InterruptedException {
        long deadline = java.lang.System.currentTimeMillis() + timeoutMs;
        while (!Files.exists(path) && java.lang.System.currentTimeMillis() < deadline) {
            Thread.sleep(50);
        }
    }

    private BranchAwareVirtualModel createVirtualModel(Path projectPath) throws IOException {
        InternalVirtualModel model = new VirtualModelBuilder()
                .withStorageFolder(projectPath)
                .withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(new TestUserInteraction()))
                .withChangePropagationSpecifications(new Model2Model2ChangePropagationSpecification())
                .buildAndInitialize();
        model.setChangePropagationMode(ChangePropagationMode.TRANSITIVE_CYCLIC);
        return new BranchAwareVirtualModel(projectPath, model);
    }

    private CommittableView getDefaultView(InternalVirtualModel model) {
        var selector = model.createSelector(ViewTypeFactory.createIdentityMappingViewType("default"));
        selector.getSelectableElements().stream().filter(element -> element instanceof System).forEach(element -> selector.setSelected(element, true));
        return selector.createView().withChangeRecordingTrait();
    }

    private void modifyView(CommittableView view, Consumer<CommittableView> modification) {
        modification.accept(view);
        view.commitChanges();
    }

    private static String readChangelogFromObjectStore(Path repoRoot, String branchName,
            String relPath) throws IOException {
        try (org.eclipse.jgit.api.Git g = org.eclipse.jgit.api.Git.open(repoRoot.toFile());
             org.eclipse.jgit.revwalk.RevWalk walk = new org.eclipse.jgit.revwalk.RevWalk(g.getRepository())) {
            org.eclipse.jgit.lib.ObjectId branchHead = g.getRepository().resolve("refs/heads/" + branchName);
            if (branchHead == null) return null;
            org.eclipse.jgit.revwalk.RevCommit headCommit = walk.parseCommit(branchHead);
            try (org.eclipse.jgit.treewalk.TreeWalk tw = org.eclipse.jgit.treewalk.TreeWalk.forPath(
                    g.getRepository(), relPath, headCommit.getTree())) {
                if (tw == null) return null;
                org.eclipse.jgit.lib.ObjectLoader loader = g.getRepository().open(tw.getObjectId(0));
                return new String(loader.getBytes(), java.nio.charset.StandardCharsets.UTF_8);
            }
        }
    }

}