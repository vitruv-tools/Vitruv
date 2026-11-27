package tools.vitruv.framework.remote.server.rest.integration;

import mir.reactions.model2Model2.Model2Model2ChangePropagationSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import tools.vitruv.change.correspondence.CorrespondencePackage;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.framework.remote.server.VitruvServer;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.branch.handler.PostMergeHandler;
import tools.vitruv.framework.vsum.branch.handler.PreCommitHandler;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.versioning.VersioningService;
import tools.vitruv.methodologisttemplate.model.model.ModelPackage;
import tools.vitruv.methodologisttemplate.model.model2.Model2Package;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Base class for integration tests that boot a real {@link VitruvServer} backed by a real V-SUM.
 *
 * <p>Each subclass gets its own server started on a dynamic port. The server is started once
 * before all tests in the class and stopped after all tests complete.
 *
 * <p>Use {@link #get}, {@link #post}, {@link #delete}, and {@link #patch} helpers to make HTTP
 * requests. Use {@link #writeModelFile} to place raw XMI content into the repo for commit tests.
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractServerIntegrationTest {

    protected VitruvServer server;
    protected BranchAwareVirtualModel branchModel;
    protected Path repoRoot;
    protected String baseUrl;

    private HttpClient http;

    @BeforeAll
    void startServer() throws Exception {
        // Create an isolated temp directory that acts as both the Git repo root
        // and the V-SUM storage folder. Each subclass gets its own directory so
        // test classes cannot interfere with one another.
        repoRoot = Files.createTempDirectory("vitruv-it-");

        // Bootstrap a minimal Git repo with one empty commit.
        // CommitManager and BranchManager require an existing repo with at least one commit so that
        // HEAD points to a valid ref (otherwise git commands like "git log" fail).
        initGitRepo(repoRoot);

        // EMF package registration
        // required in standalone (non-OSGi) mode.
        // Without this, Ecore cannot resolve the package URIs used inside XMI
        // files, and the V-SUM will throw "Package not found" errors at runtime.
        CorrespondencePackage.eINSTANCE.eClass();
        ModelPackage.eINSTANCE.eClass();
        Model2Package.eINSTANCE.eClass();

        // Register a catch-all XMI resource factory so that ResourceSet.createResource()
        // succeeds for any file extension (.model, .model2, etc.) in standalone mode.
        // Without this, DefaultStateBasedChangeResolutionStrategy.getChangeSequenceForCreated()
        // calls createResource() and gets null back, causing a NullPointerException.
        // This mirrors the registration in ManualTest in the Vitruvius-Branching-Test project.
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
                .put("*", new XMIResourceFactoryImpl());

        // Build the core V-SUM. The predefined interaction result provider with
        // null means "auto-accept all interactions", which avoids blocking on
        // dialogs. The change propagation spec wires the Model <-> Model2 reaction.
        // Register the "default" identity-mapping view type so that:
        //   a) GET /vsum/view/types returns a non-empty array, and
        //   b) GET /vsum/view/selector can find the type by name when clients
        //      use VitruvRemoteConnection to open views in behavioral tests.
        // Without this registration the ViewTypeRepository is empty and the
        // selector endpoint always returns 404.
        InternalVirtualModel innerModel = new VirtualModelBuilder()
                .withStorageFolder(repoRoot)
                .withUserInteractorForResultProvider(
                        UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))
                .withChangePropagationSpecifications(new Model2Model2ChangePropagationSpecification())
                .withViewType(ViewTypeFactory.createIdentityMappingViewType("default"))
                .buildAndInitialize();

        // BranchManager handles Git branch lifecycle (create, delete, switch, topology).
        BranchManager branchManager = new BranchManager(repoRoot);

        // Wrap the inner model with branch awareness so that checkouts reload the
        // in-memory model state from the newly checked-out branch on disk.
        // Pass the shared BranchManager so only one instance exists per repository.
        branchModel = new BranchAwareVirtualModel(repoRoot, innerModel, branchManager);

        // CommitManager handles Git commits. Attaching semantic change tracking
        // makes it listen to the model's change buffer so it can write a JSON
        // changelog and XMI delta snapshots alongside each commit.
        CommitManager commitManager = new CommitManager(repoRoot);
        commitManager.attachSemanticChangeTracking(
                branchModel.getChangeBuffer(),
                branchModel::getUuidResolver,
                branchModel::getViewSourceModels);
        commitManager.attachValidation(new PreCommitHandler(branchModel));

        // MergeManager handles three-way Git merges between branches.
        MergeManager mergeManager = new MergeManager(repoRoot);
        mergeManager.suppressTriggerFile();
        mergeManager.setPostMergeHandler(new PostMergeHandler(branchModel, repoRoot));
        mergeManager.setPostMergeReload(branchModel::reload);

        // VersioningService manages named versions (annotated Git tags) and
        // rollback operations on top of the commit history.
        VersioningService versioningService = new VersioningService(repoRoot, innerModel::reload, branchManager);

        // Start the server on port 0 so the OS assigns a free port automatically.
        // This avoids port conflicts when multiple test classes run in parallel or
        // when a previous run left a port occupied. The actual port is retrieved
        // via server.getPort() after start().
        server = new VitruvServer(() -> branchModel, 0, "localhost",
                branchManager, commitManager, mergeManager, versioningService);
        server.start();
        baseUrl = "http://localhost:" + server.getPort();

        // Single shared HttpClient instance - thread-safe and reused for all requests.
        http = HttpClient.newHttpClient();
    }

    @AfterAll
    void stopServer() {
        // Null checks guard against a failed @BeforeAll that left fields uninitialized.
        if (server != null) {
            server.stop();
        }
        if (branchModel != null) {
            // Releasing the branch model closes open EMF resources and file handles,
            // which also lets the JVM clean up the temp directory on Windows.
            branchModel.dispose();
        }
    }

    // HTTP helpers

    /**
     * Sends a GET request to {@code baseUrl + path} with the given headers.
     *
     * @param path    the endpoint path (e.g., {@code "/health"}).
     * @param headers alternating key/value header pairs, may be empty.
     * @return the HTTP response.
     */
    protected HttpResponse<String> get(String path, String... headers) {
        HttpRequest.Builder req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET();
        applyHeaders(req, headers);
        return send(req.build());
    }

    /**
     * Sends a POST request with a JSON body.
     *
     * @param path    the endpoint path.
     * @param body    the JSON request body, or {@code null} for an empty body.
     * @param headers alternating key/value header pairs.
     * @return the HTTP response.
     */
    protected HttpResponse<String> post(String path, String body, String... headers) {
        String payload = body != null ? body : "";
        HttpRequest.Builder req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload));
        applyHeaders(req, headers);
        return send(req.build());
    }

    /**
     * Sends a DELETE request.
     *
     * @param path    the endpoint path.
     * @param headers alternating key/value header pairs.
     * @return the HTTP response.
     */
    protected HttpResponse<String> delete(String path, String... headers) {
        HttpRequest.Builder req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .DELETE();
        applyHeaders(req, headers);
        return send(req.build());
    }

    /**
     * Sends a PATCH request with a JSON body.
     *
     * @param path    the endpoint path.
     * @param body    the JSON request body.
     * @param headers alternating key/value header pairs.
     * @return the HTTP response.
     */
    protected HttpResponse<String> patch(String path, String body, String... headers) {
        HttpRequest.Builder req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body));
        applyHeaders(req, headers);
        return send(req.build());
    }

    /**
     * Writes a file with the given content into the repo at {@code repoRoot/relativePath}.
     * Creates parent directories as needed. Use this to stage files for commit tests.
     *
     * @param relativePath path relative to the repo root (e.g., {@code "model/system.xmi"}).
     * @param content      the file content to write.
     * @throws IOException if the file cannot be written.
     */
    protected void writeModelFile(String relativePath, String content) throws IOException {
        Path target = repoRoot.resolve(relativePath);
        Files.createDirectories(target.getParent());
        Files.writeString(target, content);
    }


    // Attaches each key/value pair in the headers vararg to the request builder.
    // The vararg is expected to be an even-length alternating sequence like
    // ("Branch-Name", "master", "Commit-Sha", "abc123").
    private void applyHeaders(HttpRequest.Builder req, String[] headers) {
        if (headers.length % 2 != 0) {
            throw new IllegalArgumentException("Headers must be key/value pairs");
        }
        for (int i = 0; i < headers.length; i += 2) {
            req.header(headers[i], headers[i + 1]);
        }
    }

    // Sends the request synchronously and fails the test on transport errors
    // (connection refused, thread interruption). This converts checked exceptions
    // into a clean JUnit failure message rather than a stack trace.
    private HttpResponse<String> send(HttpRequest req) {
        try {
            return http.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            fail("HTTP request failed: " + e.getMessage());
            throw new RuntimeException(e); // unreachable, but required by compiler
        }
    }

    // Prepares a minimal Git repo suitable for CommitManager and BranchManager.
    // Steps:
    //   1. git init: creates .git/ in the temp directory
    //   2. git config: sets a local identity so commits don't fail on machines that have no global git config (e.g. CI)
    //   3. git commit --allow-empty: creates the initial commit so HEAD points
    //   to a real ref; without this, "git log" and branch commands fail with "fatal: bad default revision"
    private static void initGitRepo(Path dir) throws IOException, InterruptedException {
        run(dir, "git", "init");
        run(dir, "git", "config", "user.email", "test@vitruv.tools");
        run(dir, "git", "config", "user.name", "Vitruv Test");
        run(dir, "git", "commit", "--allow-empty", "-m", "init");
    }

    // Runs an external command in the given directory and waits for it to finish.
    // Stderr is merged into stdout (redirectErrorStream) so that error output
    // is captured if we need to include it in a failure message.
    private static void run(Path dir, String... cmd) throws IOException, InterruptedException {
        int exit = new ProcessBuilder(cmd)
                .directory(dir.toFile())
                .redirectErrorStream(true)
                .start()
                .waitFor();
        if (exit != 0) {
            throw new IOException("Command failed (exit " + exit + "): " + String.join(" ", cmd));
        }
    }
}
