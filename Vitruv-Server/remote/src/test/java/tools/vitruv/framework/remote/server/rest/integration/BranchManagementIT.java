package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Integration tests for branch management endpoints.
 *
 * <p>Tests run in a fixed order so that later tests can rely on branches
 * created by earlier ones (all within the same server/VSUM instance).
 */
@TestMethodOrder(OrderAnnotation.class)
class BranchManagementIT extends AbstractServerIntegrationTest {

  @Test
  @Order(1)
  @DisplayName("GET /vsum/branch returns 200 with JSON array containing master")
  void listBranchesReturnsMaster() {
    HttpResponse<String> response = get("/vsum/branch");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String body = response.body();
    assertTrue(body.contains("master"), "Expected master in branch list but got: " + body);
  }

  @Test
  @Order(2)
  @DisplayName("POST /vsum/branch creates a new branch from master")
  void createBranchReturns200WithBranchJson() {
    String body = "{\"name\":\"feature/test\",\"fromBranch\":\"master\"}";
    HttpResponse<String> response = post("/vsum/branch", body);

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("feature/test"),
        "Expected branch name in response but got: " + response.body());
  }

  @Test
  @Order(3)
  @DisplayName("GET /vsum/branch lists newly created branch")
  void listBranchesIncludesNewBranch() {
    HttpResponse<String> response = get("/vsum/branch");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("feature/test"),
        "Expected feature/test in branch list but got: " + response.body());
  }

  @Test
  @Order(4)
  @DisplayName("GET /vsum/branch/{branchName}/state returns ACTIVE for existing branch")
  void branchStateIsActiveForNewBranch() {
    HttpResponse<String> response = get("/vsum/branch/feature%2Ftest/state");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("ACTIVE"),
        "Expected ACTIVE state but got: " + response.body());
  }

  @Test
  @Order(5)
  @DisplayName("GET /vsum/branch/{branchName} returns 400 when branch name is missing from path")
  void branchStateMissingPathSegmentReturns400() {
    HttpResponse<String> response = get("/vsum/branch/");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(6)
  @DisplayName("GET /vsum/branch/topology returns master as parent of feature/test")
  void branchTopologyShowsMasterAsParent() {
    HttpResponse<String> response = get("/vsum/branch/topology");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("master"),
        "Expected master in topology but got: " + response.body());
  }

  @Test
  @Order(7)
  @DisplayName("POST /vsum/branch/{branchName}/switch switches to feature/test")
  void switchBranchSucceeds() {
    HttpResponse<String> response = post("/vsum/branch/feature%2Ftest/switch", "");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("feature/test"),
        "Expected switched branch name in response but got: " + response.body());
  }

  @Test
  @Order(8)
  @DisplayName("POST /vsum/branch/{branchName}/switch switches back to master")
  void switchBackToMasterSucceeds() {
    HttpResponse<String> response = post("/vsum/branch/master/switch", "");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("master"),
        "Expected master in response but got: " + response.body());
  }

  @Test
  @Order(9)
  @DisplayName("DELETE /vsum/branch/{branchName} removes feature/test")
  void deleteBranchSucceeds() {
    HttpResponse<String> response = delete("/vsum/branch/feature%2Ftest");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
  }

  @Test
  @Order(10)
  @DisplayName("GET /vsum/branch does not list deleted branch")
  void listBranchesExcludesDeletedBranch() {
    HttpResponse<String> response = get("/vsum/branch");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertFalse(response.body().contains("feature/test"),
        "Deleted branch should not appear in list but got: " + response.body());
  }

  @Test
  @Order(11)
  @DisplayName("DELETE /vsum/branch/{branchName} returns 400 when branch name is missing from path")
  void deleteBranchMissingPathSegmentReturns400() {
    HttpResponse<String> response = delete("/vsum/branch/");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }

  @Test
  @Order(12)
  @DisplayName("GET /vsum/branch/{branchName} returns 200 with full branch metadata")
  void getSingleBranchReturnsMetadata() {
    post("/vsum/branch", "{\"name\":\"feature/single-get\",\"fromBranch\":\"master\"}");

    HttpResponse<String> response = get("/vsum/branch/feature%2Fsingle-get");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String body = response.body();
    assertTrue(body.contains("feature/single-get"), "Expected branch name in response: " + body);
    assertTrue(body.contains("maturity"), "Expected maturity field in response: " + body);
    assertTrue(body.contains("state"), "Expected state field in response: " + body);
  }

  @Test
  @Order(13)
  @DisplayName("GET /vsum/branch/{branchName} returns 405 for a non-existent branch")
  void getSingleBranchUnknownNameReturns405() {
    HttpResponse<String> response = get("/vsum/branch/no-such-branch-xyz");

    assertEquals(HttpURLConnection.HTTP_BAD_METHOD, response.statusCode());
  }

  @Test
  @Order(14)
  @DisplayName("PATCH /vsum/branch/{branchName}/maturity updates maturity to REVIEWED")
  void setBranchMaturityReturns200() {
    post("/vsum/branch", "{\"name\":\"feature/maturity-test\",\"fromBranch\":\"master\"}");

    HttpResponse<String> response = patch(
        "/vsum/branch/feature%2Fmaturity-test/maturity",
        "{\"maturity\":\"REVIEWED\"}");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
  }

  @Test
  @Order(15)
  @DisplayName("GET /vsum/branch/{branchName} reflects updated maturity after PATCH")
  void getBranchAfterMaturityUpdateShowsReviewed() {
    HttpResponse<String> response = get("/vsum/branch/feature%2Fmaturity-test");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertTrue(response.body().contains("REVIEWED"),
        "Expected REVIEWED maturity in response: " + response.body());
  }

  @Test
  @Order(16)
  @DisplayName("PATCH /vsum/branch/{branchName}/maturity returns 400 for an invalid maturity value")
  void setBranchMaturityInvalidValueReturns400() {
    HttpResponse<String> response = patch(
        "/vsum/branch/feature%2Fmaturity-test/maturity",
        "{\"maturity\":\"INVALID_LEVEL\"}");

    assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
  }
}
