package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
/**
 * Integration tests for versioning endpoints.
 *
 * <p>Tests run in order: commit a model file first so that a version can be created at HEAD,
 * then exercise list, get, rollback preview, and delete. All tests share the same
 * server and VSUM instance.
 */
@TestMethodOrder(OrderAnnotation.class)
class VersioningIT extends AbstractServerIntegrationTest {

  private static final String VERSION_ID = "v1.0-it";

  @Test
  @Order(1)
  @DisplayName("setup: commit a dummy model file so HEAD has at least one commit")
  void setupCommitModelFile() throws IOException {
    writeModelFile("model/versioning-test.xmi",
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<model:Root xmi:version=\"2.0\"/>");
    HttpResponse<String> response = post("/vsum/commit",
        "{\"message\":\"Versioning IT setup commit\"}");
    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Setup commit failed: " + response.body());
  }

  @Test
  @Order(2)
  @DisplayName("POST /vsum/version creates a version at HEAD and returns 200")
  void createVersionReturns200() {
    String body = "{\"versionId\":\"" + VERSION_ID + "\","
        + "\"description\":\"Integration test version\"}";
    HttpResponse<String> response = post("/vsum/version", body);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Create version failed: " + response.body());
    assertTrue(response.body().contains(VERSION_ID),
        "Expected versionId in response but got: " + response.body());
  }

  @Test
  @Order(3)
  @DisplayName("POST /vsum/version returns 400 when versionId is blank")
  void createVersionBlankIdReturns400() {
    String body = "{\"versionId\":\"   \"}";
    HttpResponse<String> response = post("/vsum/version", body);

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(4)
  @DisplayName("GET /vsum/version returns JSON array containing the created version")
  void listVersionsContainsCreatedVersion() {
    HttpResponse<String> response = get("/vsum/version");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String body = response.body();
    assertTrue(body.contains(VERSION_ID),
        "Expected " + VERSION_ID + " in list but got: " + body);
  }

  @Test
  @Order(5)
  @DisplayName("GET /vsum/version/{versionId} returns version metadata")
  void getVersionReturnsMetadata() {
    HttpResponse<String> response = get("/vsum/version/" + VERSION_ID);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Get version failed: " + response.body());
    assertTrue(response.body().contains(VERSION_ID),
        "Expected versionId in response but got: " + response.body());
    assertTrue(response.body().contains("Integration test"),
        "Expected description in response but got: " + response.body());
  }

  @Test
  @Order(6)
  @DisplayName("GET /vsum/version/{versionId} returns 400 when version ID is missing from path")
  void getVersionMissingPathSegmentReturns400() {
    HttpResponse<String> response = get("/vsum/version/");
    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(7)
  @DisplayName("GET /vsum/version/{versionId} returns 405 for unknown version ID")
  void getVersionUnknownIdReturns405() {
    HttpResponse<String> response = get("/vsum/version/nonexistent-v99");
    // notFound() in RestEndpoint maps to HTTP_BAD_METHOD (405) by convention.
    assertEquals(HttpURLConnection.HTTP_BAD_METHOD, response.statusCode());
  }

  @Test
  @Order(8)
  @DisplayName("POST /vsum/version/{versionId}/rollback/preview returns 200 with preview data")
  void rollbackPreviewReturns200() {
    HttpResponse<String> response = post("/vsum/version/" + VERSION_ID + "/rollback/preview", "");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Rollback preview failed: " + response.body());
  }

  @Test
  @Order(9)
  @DisplayName("POST /vsum/version/{versionId}/rollback/preview returns 405 when version ID is missing")
  void rollbackPreviewMissingPathSegmentReturns405() {
    HttpResponse<String> response = post("/vsum/version/", "");
    assertEquals(HttpURLConnection.HTTP_BAD_METHOD, response.statusCode());
  }

  @Test
  @Order(10)
  @DisplayName("DELETE /vsum/version/{versionId} removes the version")
  void deleteVersionReturns200() {
    HttpResponse<String> response = delete("/vsum/version/" + VERSION_ID);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Delete version failed: " + response.body());
  }

  @Test
  @Order(11)
  @DisplayName("GET /vsum/version no longer contains deleted version")
  void listVersionsExcludesDeletedVersion() {
    HttpResponse<String> response = get("/vsum/version");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertFalse(response.body().contains(VERSION_ID),
        "Deleted version should not appear in list but got: " + response.body());
  }

  @Test
  @Order(12)
  @DisplayName("DELETE /vsum/version/{versionId} returns 400 when version ID is missing from path")
  void deleteVersionMissingPathSegmentReturns400() {
    HttpResponse<String> response = delete("/vsum/version/");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(13)
  @DisplayName("setup: recreate version so branch-from-version and rollback tests have a target")
  void setupRecreateVersion() {
    String body = "{\"versionId\":\"" + VERSION_ID + "\","
        + "\"description\":\"Recreated for branch and rollback tests\"}";
    HttpResponse<String> response = post("/vsum/version", body);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Recreate version failed: " + response.body());
  }

  @Test
  @Order(14)
  @DisplayName("POST /vsum/version/{id}/branch creates a branch from the version and returns branch metadata")
  void createBranchFromVersionReturns200() {
    String body = "{\"branchName\":\"feature/from-version-it\"}";
    HttpResponse<String> response = post("/vsum/version/" + VERSION_ID + "/branch", body);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Create branch from version failed: " + response.body());
    String responseBody = response.body();
    assertTrue(responseBody.contains("feature/from-version-it"),
        "Expected branch name in response but got: " + responseBody);
    assertTrue(responseBody.contains("maturity"),
        "Expected maturity field in response but got: " + responseBody);
  }

  @Test
  @Order(15)
  @DisplayName("POST /vsum/version/{id}/branch returns 400 when branchName is blank")
  void createBranchFromVersionBlankNameReturns400() {
    String body = "{\"branchName\":\"   \"}";
    HttpResponse<String> response = post("/vsum/version/" + VERSION_ID + "/branch", body);

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(16)
  @DisplayName("POST /vsum/version/{id}/branch returns 405 for a non-existent version ID")
  void createBranchFromVersionUnknownIdReturns405() {
    String body = "{\"branchName\":\"feature/from-nonexistent\"}";
    HttpResponse<String> response = post("/vsum/version/nonexistent-v99/branch", body);

    assertEquals(HttpURLConnection.HTTP_BAD_METHOD, response.statusCode());
  }

  @Test
  @Order(17)
  @DisplayName("POST /vsum/version/{id}/rollback/confirm executes rollback and returns successful=true")
  void rollbackConfirmReturnsSuccessful() {
    HttpResponse<String> response = post("/vsum/version/" + VERSION_ID + "/rollback/confirm", "");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(),
        "Rollback confirm failed: " + response.body());
    String body = response.body();
    assertTrue(body.contains("successful"),
        "Expected 'successful' field in rollback confirm response but got: " + body);
    assertTrue(body.contains("true"),
        "Expected successful=true in rollback confirm response but got: " + body);
  }

  @Test
  @Order(18)
  @DisplayName("POST /vsum/version/{id}/rollback/confirm returns 405 for a non-existent version ID")
  void rollbackConfirmUnknownIdReturns405() {
    HttpResponse<String> response = post("/vsum/version/nonexistent-v99/rollback/confirm", "");

    assertEquals(HttpURLConnection.HTTP_BAD_METHOD, response.statusCode());
  }
}
