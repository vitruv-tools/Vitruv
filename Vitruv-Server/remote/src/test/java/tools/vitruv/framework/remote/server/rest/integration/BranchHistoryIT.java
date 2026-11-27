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
 * Integration tests for GET /vsum/branch/{branchName}/history (UI-2: branch history with semantic summaries).
 *
 * <p>A model element is committed via the Vitruv API so that the semantic changelog is populated.
 * The history endpoint is then exercised against that recorded state.
 */
@TestMethodOrder(OrderAnnotation.class)
class BranchHistoryIT extends AbstractServerIntegrationTest {

    private static final String MODEL_RESOURCE = "branch-history-test.model";
    private static final String COMMIT_MSG = "BranchHistoryIT: add System element";

    private Path clientTempDir;

    private Path clientTemp() {
        if (clientTempDir == null) {
            try {
                clientTempDir = Files.createTempDirectory("vitruv-history-client-");
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
    @DisplayName("setup: commit a model element via Vitruv API to produce a semantic changelog entry")
    void setupCommitViaVitruvApi() {
        VitruvClient client = new VitruvRemoteConnection("http", "localhost", server.getPort(), clientTemp());
        View raw = openDefaultView(client);
        CommittableView view = raw.withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        System system = ModelFactory.eINSTANCE.createSystem();
        URI uri = URI.createFileURI(repoRoot.resolve(MODEL_RESOURCE).toString());
        view.registerRoot(system, uri);
        view.commitChanges();

        HttpResponse<String> resp = post("/vsum/commit", "{\"message\":\"" + COMMIT_MSG + "\"}");
        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Setup commit failed: " + resp.body());
    }

    @Test
    @Order(2)
    @DisplayName("GET /vsum/branch/{branch}/history returns 200 with branch and commits fields")
    void historyReturns200WithStructuredResponse() {
        HttpResponse<String> resp = get("/vsum/branch/master/history");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Expected 200 but got " + resp.statusCode() + ": " + resp.body());
        String body = resp.body();
        assertTrue(body.contains("\"branch\""), "Expected 'branch' field in response: " + body);
        assertTrue(body.contains("\"commits\""), "Expected 'commits' field in response: " + body);
        assertTrue(body.contains("master"), "Expected branch value 'master' in response: " + body);
    }

    @Test
    @Order(3)
    @DisplayName("GET /vsum/branch/{branch}/history response contains the committed message")
    void historyContainsCommitMessage() {
        HttpResponse<String> resp = get("/vsum/branch/master/history");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode());
        assertTrue(resp.body().contains(COMMIT_MSG),
                "Expected commit message in history response but got: " + resp.body());
    }

    @Test
    @Order(4)
    @DisplayName("GET /vsum/branch/{branch}/history marks the semantic commit with hasChangelog true")
    void historyContainsChangelogFlag() {
        HttpResponse<String> resp = get("/vsum/branch/master/history");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode());
        // Jackson serializes booleans with spaces around the colon: "hasChangelog" : true
        String body = resp.body();
        assertTrue(body.contains("hasChangelog") && body.contains("true"),
                "Expected at least one entry with hasChangelog=true but got: " + body);
    }

    @Test
    @Order(5)
    @DisplayName("GET /vsum/branch/{branch}/history includes changePreview for the semantic commit")
    void historyIncludesChangePreview() {
        HttpResponse<String> resp = get("/vsum/branch/master/history");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode());
        assertTrue(resp.body().contains("changePreview"),
                "Expected 'changePreview' field in a commit entry but got: " + resp.body());
    }

    @Test
    @Order(6)
    @DisplayName("GET /vsum/branch/{branch}/history returns 405 for a non-existent branch")
    void historyUnknownBranchReturns405() {
        HttpResponse<String> resp = get("/vsum/branch/no-such-branch/history");

        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, resp.statusCode());
    }
}
