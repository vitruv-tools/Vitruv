package tools.vitruv.framework.remote.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import mir.reactions.model2Model2.Model2Model2ChangePropagationSpecification;
import tools.vitruv.change.atomic.AtomicPackage;
import tools.vitruv.change.correspondence.CorrespondencePackage;
import tools.vitruv.change.interaction.InteractionPackage;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.branch.handler.PostMergeHandler;
import tools.vitruv.framework.vsum.branch.handler.PreCommitHandler;
import tools.vitruv.framework.vsum.branch.merge.SemanticMergeEngine;
import tools.vitruv.framework.vsum.branch.util.GitHookInstaller;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.versioning.VersioningService;
import tools.vitruv.methodologisttemplate.model.model.ModelPackage;
import tools.vitruv.methodologisttemplate.model.model2.Model2Package;

/**
 * Launches a local Vitruv server for development and testing.
 *
 * <p>Starts the server on port 8080 backed by a {@link BranchAwareVirtualModel}
 * with the Methodologist-Template consistency rules, suitable for full REST API testing.
 *
 * <p>Usage:
 * <pre>
 *   mvn install -pl remote -Dmaven.test.skip=true &amp;&amp; \
 *   mvn exec:java \
 *     -pl remote \
 *     -Dexec.classpathScope=test \
 *     -Dexec.mainClass=tools.vitruv.framework.remote.server.ServerMain \
 *     -Dexec.args="&lt;absolute-path-to-git-repo&gt;"
 * </pre>
 *
 * <p>The path must point to an existing Git repository (contains a {@code .git} directory).
 * For a fresh test repository, create one with {@code git init &lt;path&gt;} first.
 */
public class ServerMain {

  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length < 1) {
      System.err.println("Usage: ServerMain <repo-root-path>");
      System.err.println("Example: ServerMain C:/Users/user/vitruv-api-test");
      System.exit(1);
    }

    Path repoRoot = Paths.get(args[0]).toAbsolutePath();

    if (!Files.isDirectory(repoRoot.resolve(".git"))) {
      System.err.println("Error: not a Git repository: " + repoRoot);
      System.exit(1);
    }

    // Register EMF packages required for standalone (non-OSGi) execution.
    // Without these, EMF cannot resolve model URIs when loading the V-SUM from disk.
    CorrespondencePackage.eINSTANCE.eClass();
    ModelPackage.eINSTANCE.eClass();
    Model2Package.eINSTANCE.eClass();
    // Required so that the server's JsonMapper can resolve eClass URIs in incoming PATCH bodies
    // (e.g. "http://vitruv.tools/metamodels/change/atomic/2.0#//eobject/CreateEObject")
    // without triggering an HTTP demand-load of those URIs.
    AtomicPackage.eINSTANCE.eClass();
    InteractionPackage.eINSTANCE.eClass();
    // Required to reload saved V-SUM state: the reactions framework writes correspondence objects
    // referencing this package into correspondences.correspondence.
    tools.vitruv.dsls.reactions.runtime.correspondence.CorrespondencePackage.eINSTANCE.eClass();

    System.out.println("Initializing V-SUM at: " + repoRoot);

    InternalVirtualModel innerModel = new VirtualModelBuilder()
        .withStorageFolder(repoRoot)
        .withUserInteractorForResultProvider(
            UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))
        .withChangePropagationSpecifications(new Model2Model2ChangePropagationSpecification())
        .withViewType(ViewTypeFactory.createIdentityMappingViewType("default"))
        .buildAndInitialize();

    BranchManager branchManager = new BranchManager(repoRoot);
    BranchAwareVirtualModel branchModel = new BranchAwareVirtualModel(repoRoot, innerModel,
        branchManager);
    System.out.println("Active branch: " + branchModel.getActiveBranch());

    GitHookInstaller hookInstaller = new GitHookInstaller(repoRoot);
    if (hookInstaller.areAllHooksInstalled()) {
      System.out.println("Git hooks already installed.");
    } else {
      hookInstaller.installAllHooks();
      System.out.println("Git hooks installed.");
    }

    CommitManager commitManager = new CommitManager(repoRoot);
    commitManager.attachSemanticChangeTracking(
        branchModel.getChangeBuffer(),
        branchModel::getUuidResolver,
        branchModel::getViewSourceModels);
    commitManager.attachValidation(new PreCommitHandler(branchModel));

    MergeManager mergeManager = new MergeManager(repoRoot);
    mergeManager.suppressTriggerFile();
    mergeManager.setPostMergeHandler(new PostMergeHandler(branchModel, repoRoot));
    mergeManager.setPostMergeReload(branchModel::reload);
    // Wire in the merge engine so that (a) semantically-clean merges that produce JGit text
    // conflicts fall back to engine replay, and (b) REST clients can request auto-resolution
    // via the "resolutionStrategy" field in POST /vsum/merge.
    SemanticMergeEngine mergeEngine = new SemanticMergeEngine(
        repoRoot,
        List.of(new Model2Model2ChangePropagationSpecification()),
        UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null));
    mergeManager.setMergeEngine(mergeEngine);

    VersioningService versioningService = new VersioningService(repoRoot, innerModel::reload, branchManager);

    VitruvServer server = new VitruvServer(() -> branchModel, branchManager, commitManager,
        mergeManager, versioningService);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("\nShutting down...");
      server.stop();
      branchModel.dispose();
    }));

    server.start();
    System.out.println("Vitruv Server running on http://localhost:8080");
    System.out.println("Press Ctrl+C to stop.");

    // Keep the main thread alive
    Thread.currentThread().join();
  }
}
