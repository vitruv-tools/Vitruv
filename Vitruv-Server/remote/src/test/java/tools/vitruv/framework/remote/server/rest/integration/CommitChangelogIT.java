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
 * Integration tests for commit and changelog endpoints.
 *
 * <p>Model changes are made via the Vitruv REST API (PATCH /vsum/view) so that the
 * SemanticChangeBuffer is populated and the changelog is written when POST /vsum/commit
 * is called. Direct file writes bypass the change buffer and produce no changelog.
 *
 * <p>Tests run in order: add a model element via API, commit it, verify commit listing,
 * then verify the changelog contains semantic change entries.
 */
@TestMethodOrder(OrderAnnotation.class)
class CommitChangelogIT extends AbstractServerIntegrationTest {

  private static final String MODEL_RESOURCE_NAME = "changelog-test.model";

  private static String commitSha;

  private Path clientTempDir;

  private Path clientTemp() {
    if (clientTempDir == null) {
      try {
        clientTempDir = Files.createTempDirectory("vitruv-changelog-client-");
      } catch (Exception e) {
        fail("Could not create client temp dir: " + e.getMessage());
      }
    }
    return clientTempDir;
  }

  private VitruvClient client() {
    return new VitruvRemoteConnection("http", "localhost", server.getPort(), clientTemp());
  }

  @SuppressWarnings("unchecked")
  private View openView(VitruvClient client, Class<?> rootTypeFilter) {
    Collection<ViewType<?>> types = client.getViewTypes();
    assertFalse(types.isEmpty(), "Expected at least one registered view type");
    ViewType<ViewSelector> defaultType =
        (ViewType<ViewSelector>) types.stream()
            .filter(t -> "default".equals(t.getName()))
            .findFirst()
            .orElseThrow(() -> new AssertionError("View type 'default' not found"));
    ViewSelector selector = client.createSelector(defaultType);
    selector.getSelectableElements().forEach(e -> {
      boolean include = rootTypeFilter == null || rootTypeFilter.isInstance(e);
      selector.setSelected(e, include);
    });
    return selector.createView();
  }

  @Test
  @Order(1)
  @DisplayName("GET /vsum/commit/{branchName} returns empty list before any commit")
  void listCommitsEmptyInitially() {
    HttpResponse<String> response = get("/vsum/commit/master");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String body = response.body();
    assertTrue(body.startsWith("[") && body.endsWith("]"),
        "Expected JSON array but got: " + body);
  }

  @Test
  @Order(2)
  @DisplayName("GET /vsum/commit/{branchName} returns 400 when branch name is missing from path")
  void listCommitsMissingPathSegmentReturns400() {
    HttpResponse<String> response = get("/vsum/commit/");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(3)
  @DisplayName("POST /vsum/commit after Vitruv API change returns 200 and populates changelog")
  void commitViaVitruvApiPopulatesChangelog() {
    VitruvClient client = client();

    // Open an empty view and add a System element via PATCH /vsum/view.
    // This is the only path that populates the SemanticChangeBuffer.
    // Direct file writes (writeModelFile) bypass the buffer and produce no changelog.
    View rawView = openView(client, null);
    CommittableView view = rawView.withChangeDerivingTrait(
        new DefaultStateBasedChangeResolutionStrategy());

    System system = ModelFactory.eINSTANCE.createSystem();
    URI modelUri = URI.createFileURI(repoRoot.resolve(MODEL_RESOURCE_NAME).toString());
    view.registerRoot(system, modelUri);
    view.commitChanges();

    // POST /vsum/commit: CommitManager drains SemanticChangeBuffer and writes changelog.
    HttpResponse<String> response = post("/vsum/commit",
        "{\"message\":\"Add System via Vitruv API\"}");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String responseBody = response.body();
    assertTrue(responseBody.contains("commitSha"),
        "Expected commitSha in response but got: " + responseBody);
    assertTrue(responseBody.contains("master"),
        "Expected branch name in response but got: " + responseBody);

    // Extract SHA for the changelog test.
    int keyIdx = responseBody.indexOf("\"commitSha\"");
    if (keyIdx >= 0) {
      int valueQuote = responseBody.indexOf("\"", keyIdx + "\"commitSha\"".length() + 1);
      if (valueQuote >= 0) {
        int start = valueQuote + 1;
        int end = responseBody.indexOf("\"", start);
        if (end > start) {
          commitSha = responseBody.substring(start, end);
        }
      }
    }
    assertNotNull(commitSha, "Failed to extract commitSha from response: " + responseBody);
  }

  @Test
  @Order(4)
  @DisplayName("POST /vsum/commit returns 400 when message is blank")
  void commitBlankMessageReturns400() {
    HttpResponse<String> response = post("/vsum/commit", "{\"message\":\"   \"}");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(5)
  @DisplayName("GET /vsum/commit/{branchName} returns at least one commit after committing")
  void listCommitsNonEmptyAfterCommit() {
    HttpResponse<String> response = get("/vsum/commit/master");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String body = response.body();
    assertNotEquals("[]", body, "Expected at least one commit but got empty array");
    assertTrue(body.contains("Add System via Vitruv API"),
        "Expected commit message in response but got: " + body);
  }

  @Test
  @Order(6)
  @DisplayName("GET /vsum/changelog/{branchName}/{sha} returns 400 when branch name is missing from path")
  void changelogMissingPathSegmentReturns400() {
    HttpResponse<String> response = get("/vsum/changelog/");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(7)
  @DisplayName("GET /vsum/changelog/{branchName}/{sha} returns changelog with semantic change entries")
  void changelogContainsSemanticChanges() {
    assertNotNull(commitSha, "commitSha must be set by test order 3");

    HttpResponse<String> response = get("/vsum/changelog/master/" + commitSha);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Expected 200 but got " + response.statusCode() + ": " + response.body());
    String body = response.body();
    assertTrue(body.contains("changeType"),
        "Expected changelog body to contain 'changeType' entries but got: " + body);
  }
}
