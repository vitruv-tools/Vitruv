package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.impl.VitruvRemoteConnection;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.methodologisttemplate.model.model.ModelFactory;
import tools.vitruv.methodologisttemplate.model.model.System;

/**
 * Integration tests for GET /vsum/delta/{branchName}?base={baseBranch} (UI-1: semantic delta).
 *
 * <p>Setup creates a feature branch from master, switches to it, and commits a model element via
 * the Vitruv API so that a semantic changelog is written on the feature branch. All delta assertions
 * run while the server is still on the feature branch so that the changelog files (written to the
 * filesystem during commit) are accessible via readChangelog(). DeltaEndpoint reads changelogs
 * from the filesystem at the current checkout; switching branches before asserting would remove
 * those files from the working tree.
 */
@TestMethodOrder(OrderAnnotation.class)
class DeltaIT extends AbstractServerIntegrationTest {

    private static final String FEATURE_BRANCH  = "feature/delta-test";
    private static final String FEATURE_ENCODED = "feature%2Fdelta-test";
    private static final String MODEL_RESOURCE  = "delta-feature-test.model";

    private Path clientTempDir;

    private Path clientTemp() {
        if (clientTempDir == null) {
            try {
                clientTempDir = Files.createTempDirectory("vitruv-delta-client-");
            } catch (Exception e) {
                fail("Could not create client temp dir: " + e.getMessage());
            }
        }
        return clientTempDir;
    }

    @SuppressWarnings("unchecked")
    private View openDefaultView(VitruvClient client) {
        Collection<ViewType<?>> types = client.getViewTypes();
        ViewType<ViewSelector> type = (ViewType<ViewSelector>) types.stream()
                .filter(t -> "default".equals(t.getName())).findFirst()
                .orElseThrow(() -> new AssertionError("View type 'default' not found"));
        ViewSelector sel = client.createSelector(type);
        sel.getSelectableElements().forEach(e -> sel.setSelected(e, true));
        return sel.createView();
    }

    @Test
    @Order(1)
    @DisplayName("setup: create feature branch from master")
    void setupCreateFeatureBranch() {
        HttpResponse<String> resp = post("/vsum/branch",
                "{\"name\":\"" + FEATURE_BRANCH + "\",\"fromBranch\":\"master\"}");
        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Create branch failed: " + resp.body());
    }

    @Test
    @Order(2)
    @DisplayName("setup: switch to the feature branch")
    void setupSwitchToFeatureBranch() {
        HttpResponse<String> resp = post("/vsum/branch/" + FEATURE_ENCODED + "/switch", null);
        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Switch to feature branch failed: " + resp.body());
    }

    @Test
    @Order(3)
    @DisplayName("setup: commit a model element on the feature branch via Vitruv API to produce a semantic changelog")
    void setupCommitOnFeatureBranch() {
        VitruvClient client = new VitruvRemoteConnection("http", "localhost", server.getPort(), clientTemp());
        View raw = openDefaultView(client);
        CommittableView view = raw.withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        System system = ModelFactory.eINSTANCE.createSystem();
        URI uri = URI.createFileURI(repoRoot.resolve(MODEL_RESOURCE).toString());
        view.registerRoot(system, uri);
        view.commitChanges();

        HttpResponse<String> resp = post("/vsum/commit",
                "{\"message\":\"DeltaIT: feature branch model change\"}");
        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Feature branch commit failed: " + resp.body());
    }

    // Assertions run while the server is still on the feature branch so that
    // readChangelog() can find the changelog files in the current working tree.

    @Test
    @Order(4)
    @DisplayName("GET /vsum/delta/{branch}?base=master returns 200 with branch, baseBranch, and commits fields")
    void deltaReturns200WithStructuredResponse() {
        HttpResponse<String> resp = get("/vsum/delta/" + FEATURE_ENCODED + "?base=master");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Expected 200 but got " + resp.statusCode() + ": " + resp.body());
        String body = resp.body();
        assertTrue(body.contains("\"branch\""),     "Expected 'branch' field: " + body);
        assertTrue(body.contains("\"baseBranch\""), "Expected 'baseBranch' field: " + body);
        assertTrue(body.contains("\"commits\""),    "Expected 'commits' field: " + body);
    }

    @Test
    @Order(5)
    @DisplayName("GET /vsum/delta/{branch}?base=master lists semantic changes from the feature branch commit")
    void deltaListsSemanticChangesFromFeatureBranch() {
        HttpResponse<String> resp = get("/vsum/delta/" + FEATURE_ENCODED + "?base=master");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode());
        String body = resp.body();
        assertFalse(body.contains("\"commits\" : [ ]"),
                "Expected non-empty commits array in delta response but got: " + body);
        assertTrue(body.contains("\"changes\""),
                "Expected 'changes' field inside a delta commit entry but got: " + body);
    }

    @Test
    @Order(6)
    @DisplayName("GET /vsum/delta/{branch} without ?base returns 405 when the default base 'main' does not exist")
    void deltaWithoutBaseReturns405WhenMainMissing() {
        HttpResponse<String> resp = get("/vsum/delta/" + FEATURE_ENCODED);

        // DeltaEndpoint defaults the base to "main"; "main" does not exist in this test repo.
        // repo.resolve("main") returns null -> BranchOperationException -> notFound() -> 405.
        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, resp.statusCode());
    }

    @Test
    @Order(7)
    @DisplayName("GET /vsum/delta/{branch}?base=master returns 405 for a non-existent branch")
    void deltaUnknownBranchReturns405() {
        HttpResponse<String> resp = get("/vsum/delta/no-such-branch?base=master");

        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, resp.statusCode());
    }

    @Test
    @Order(8)
    @DisplayName("GET /vsum/delta/{branch}?base=nonexistent returns 405 for a non-existent base branch")
    void deltaUnknownBaseReturns405() {
        HttpResponse<String> resp = get("/vsum/delta/" + FEATURE_ENCODED + "?base=no-such-base");

        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, resp.statusCode());
    }

    @Test
    @Order(9)
    @DisplayName("GET /vsum/delta/ returns 400 when branch name is missing from path")
    void deltaMissingBranchNameReturns400() {
        HttpResponse<String> resp = get("/vsum/delta/");

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, resp.statusCode());
    }
}
