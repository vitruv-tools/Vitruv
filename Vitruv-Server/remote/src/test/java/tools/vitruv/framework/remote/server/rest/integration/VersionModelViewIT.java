package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * Integration tests for the version model and view endpoints (UI-2: model snapshot inspection).
 *
 * <p>GET /vsum/version/{versionId}/model -- returns model state at a version in text, JSON, or Mermaid format.
 * <p>GET /vsum/version/{versionId}/view  -- returns an HTML page with a Mermaid containment graph.
 *
 * <p>A model element is committed via the Vitruv API so that VSUM XMI files are present in the git tree.
 * A version tag is then created at that commit before the format assertions run.
 */
@TestMethodOrder(OrderAnnotation.class)
class VersionModelViewIT extends AbstractServerIntegrationTest {

    private static final String VERSION_ID    = "v1.0-vis";
    private static final String MODEL_RESOURCE = "version-model-view-test.model";

    private Path clientTempDir;

    private static final Path OUTPUT_DIR =
            Paths.get(java.lang.System.getProperty("user.dir"), "target", "ui-test-output");

    private static void saveTestOutput(String filename, String content) {
        try {
            Files.createDirectories(OUTPUT_DIR);
            Path out = OUTPUT_DIR.resolve(filename);
            Files.writeString(out, content, StandardCharsets.UTF_8);
            java.lang.System.out.println("[VersionModelViewIT] Saved response to: " + out.toAbsolutePath());
        } catch (Exception e) {
            java.lang.System.err.println("[VersionModelViewIT] Could not save output: " + e.getMessage());
        }
    }

    private Path clientTemp() {
        if (clientTempDir == null) {
            try {
                clientTempDir = Files.createTempDirectory("vitruv-ver-model-client-");
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
    @DisplayName("setup: commit a model element via Vitruv API so the VSUM XMI files land in the git tree")
    void setupCommitModelViaVitruvApi() {
        VitruvClient client = new VitruvRemoteConnection("http", "localhost", server.getPort(), clientTemp());
        View raw = openDefaultView(client);
        CommittableView view = raw.withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
        System system = ModelFactory.eINSTANCE.createSystem();
        URI uri = URI.createFileURI(repoRoot.resolve(MODEL_RESOURCE).toString());
        view.registerRoot(system, uri);
        view.commitChanges();

        HttpResponse<String> resp = post("/vsum/commit", "{\"message\":\"VersionModelViewIT: setup model commit\"}");
        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Setup commit failed: " + resp.body());
    }

    @Test
    @Order(2)
    @DisplayName("setup: create version tag at HEAD")
    void setupCreateVersionTag() {
        HttpResponse<String> resp = post("/vsum/version",
                "{\"versionId\":\"" + VERSION_ID + "\",\"description\":\"visualization IT version\"}");
        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Create version failed: " + resp.body());
    }

    @Test
    @Order(3)
    @DisplayName("GET /vsum/version/{id}/model (default text format) returns 200 with version header")
    void versionModelTextFormatReturns200() {
        HttpResponse<String> resp = get("/vsum/version/" + VERSION_ID + "/model");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Expected 200 but got " + resp.statusCode() + ": " + resp.body());
        assertTrue(resp.body().contains("Version: " + VERSION_ID),
                "Expected 'Version: " + VERSION_ID + "' in text response but got: " + resp.body());
        saveTestOutput("model-text.txt", resp.body());
    }

    @Test
    @Order(4)
    @DisplayName("GET /vsum/version/{id}/model?format=json returns 200 with JSON containing version and models fields")
    void versionModelJsonFormatReturns200() {
        HttpResponse<String> resp = get("/vsum/version/" + VERSION_ID + "/model?format=json");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Expected 200 but got " + resp.statusCode() + ": " + resp.body());
        String body = resp.body();
        assertTrue(body.contains("\"version\""), "Expected 'version' key in JSON: " + body);
        assertTrue(body.contains("\"models\""),  "Expected 'models' key in JSON: " + body);
        assertTrue(body.contains(VERSION_ID),    "Expected version ID value in JSON: " + body);
        saveTestOutput("model-json.json", body);
    }

    @Test
    @Order(5)
    @DisplayName("GET /vsum/version/{id}/model?format=mermaid returns 200 Mermaid graph starting with 'graph TD'")
    void versionModelMermaidFormatReturns200() {
        HttpResponse<String> resp = get("/vsum/version/" + VERSION_ID + "/model?format=mermaid");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Expected 200 but got " + resp.statusCode() + ": " + resp.body());
        assertTrue(resp.body().startsWith("graph TD"),
                "Expected Mermaid output starting with 'graph TD' but got: " + resp.body());
        saveTestOutput("model-mermaid.txt", resp.body());
    }

    @Test
    @Order(6)
    @DisplayName("GET /vsum/version/{id}/view returns 200 HTML page with embedded Mermaid diagram")
    void versionViewReturns200HtmlWithMermaid() {
        HttpResponse<String> resp = get("/vsum/version/" + VERSION_ID + "/view");

        assertEquals(HttpURLConnection.HTTP_OK, resp.statusCode(),
                "Expected 200 but got " + resp.statusCode() + ": " + resp.body());
        String body = resp.body();
        assertTrue(body.contains("<!DOCTYPE html"), "Expected HTML doctype in response: " + body);
        assertTrue(body.contains("mermaid"),        "Expected 'mermaid' script reference in HTML: " + body);
        assertTrue(body.contains(VERSION_ID),       "Expected version ID in HTML page: " + body);
        saveTestOutput("version-view.html", body);
    }

    @Test
    @Order(7)
    @DisplayName("GET /vsum/version/{id}/model returns 405 for a non-existent version ID")
    void versionModelUnknownVersionReturns405() {
        HttpResponse<String> resp = get("/vsum/version/nonexistent-vis-v99/model");

        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, resp.statusCode());
    }

    @Test
    @Order(8)
    @DisplayName("GET /vsum/version/{id}/view returns 405 for a non-existent version ID")
    void versionViewUnknownVersionReturns405() {
        HttpResponse<String> resp = get("/vsum/version/nonexistent-vis-v99/view");

        assertEquals(HttpURLConnection.HTTP_BAD_METHOD, resp.statusCode());
    }
}
