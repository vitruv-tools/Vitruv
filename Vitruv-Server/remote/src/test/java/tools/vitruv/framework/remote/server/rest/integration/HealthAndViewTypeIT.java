package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Smoke tests: verifies the server boots and its two simplest endpoints respond correctly.
 *
 * <p>No model changes are made; these tests only read server state.
 */
class HealthAndViewTypeIT extends AbstractServerIntegrationTest {

  @Test
  @DisplayName("GET /health returns 200 and expected body")
  void healthEndpointReturns200() {
    HttpResponse<String> response = get("/health");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    assertEquals("Vitruv server up and running!", response.body());
  }

  @Test
  @DisplayName("GET /vsum/view/types returns 200 and a JSON array containing the registered type")
  void viewTypesReturnsJsonArray() {
    HttpResponse<String> response = get("/vsum/view/types");

    assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    String body = response.body();
    assertTrue(body.startsWith("[") && body.endsWith("]"),
        "Expected JSON array but got: " + body);
    // The "default" identity-mapping view type is registered in AbstractServerIntegrationTest.
    assertTrue(body.contains("default"),
        "Expected 'default' view type in response but got: " + body);
  }

  @Test
  @DisplayName("unknown endpoint returns 404")
  void unknownEndpointReturns404() {
    HttpResponse<String> response = get("/vsum/nonexistent");

    assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.statusCode());
  }
}
