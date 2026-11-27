package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.*;

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
import tools.vitruv.methodologisttemplate.model.model2.Root;

/**
 * Full end-to-end integration test covering the complete Vitruv branching REST API workflow.
 *
 * <p>This test is the single authoritative proof that the implementation works correctly
 * from a real client perspective. All model changes go through the Vitruv REST API
 * (PATCH /vsum/view) so that reactions fire, the SemanticChangeBuffer is populated,
 * and changelogs are written. Direct file writes are intentionally avoided.
 *
 * <p>Scenario covered in order:
 * <ol>
 *   <li>Add a System on master via the Vitruv API; verify the consistency reaction creates
 *       a Root in Model2 (reaction pipeline works end-to-end).
 *   <li>Commit the change; verify the commit list and the semantic changelog.
 *   <li>Fork a feature branch, add a second System there; verify branch isolation
 *       (master does not see the second System).
 *   <li>Create a named version snapshot of master before the merge.
 *   <li>Merge the feature branch into master; verify the V-SUM is reloaded and master
 *       now shows both Systems.
 * </ol>
 */
@TestMethodOrder(OrderAnnotation.class)
class EndToEndIT extends AbstractServerIntegrationTest {

  private static final String FEATURE_BRANCH = "feature/add-system2";
  private static final String VERSION_ID = "v1.0-pre-merge";

  private static String sha1;
  private static String sha2;

  private Path clientTempDir;

  private Path clientTemp() {
    if (clientTempDir == null) {
      try {
        clientTempDir = Files.createTempDirectory("vitruv-e2e-client-");
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

  private static String extractSha(String json) {
    int keyIdx = json.indexOf("\"commitSha\"");
    if (keyIdx < 0) return null;
    int valueQuote = json.indexOf("\"", keyIdx + "\"commitSha\"".length() + 1);
    if (valueQuote < 0) return null;
    int start = valueQuote + 1;
    int end = json.indexOf("\"", start);
    return end > start ? json.substring(start, end) : null;
  }

  @Test
  @Order(1)
  @DisplayName("Phase 1a: add System on master via Vitruv API - consistency reaction creates Root in Model2")
  void addSystemOnMasterAndVerifyReaction() {
    VitruvClient client = client();

    // Open an empty view and register a System at a new resource URI.
    // commitChanges() sends PATCH /vsum/view which propagates through the V-SUM
    // and triggers the Model2Model2 reaction.
    View rawView = openView(client, null);
    CommittableView view = rawView.withChangeDerivingTrait(
        new DefaultStateBasedChangeResolutionStrategy());

    System system = ModelFactory.eINSTANCE.createSystem();
    URI modelUri = URI.createFileURI(repoRoot.resolve("system.model").toString());
    view.registerRoot(system, modelUri);
    view.commitChanges();

    // Verify reaction: adding a System must trigger the Model2Model2 spec to create a Root.
    View reactionView = openView(client(), Root.class);
    Collection<Root> roots = reactionView.getRootObjects(Root.class);
    assertEquals(1, roots.size(),
        "Consistency reaction must have created exactly one Root in Model2");
  }

  @Test
  @Order(2)
  @DisplayName("Phase 1b: commit to master - changelog auto-committed and visible in commit list")
  void commitSystemToMasterAndVerifyCommitList() {
    var response = post("/vsum/commit", "{\"message\":\"Initial: add System\"}");
    assertEquals(200, response.statusCode(),
        "Commit failed: " + response.body());

    sha1 = extractSha(response.body());
    assertNotNull(sha1, "Failed to extract commitSha from: " + response.body());

    var commitsResponse = get("/vsum/commit/master");
    assertEquals(200, commitsResponse.statusCode());
    assertTrue(commitsResponse.body().contains("Initial: add System"),
        "Commit list must contain the model commit: " + commitsResponse.body());
    assertTrue(commitsResponse.body().contains("[vitruvius]"),
        "Commit list must contain the auto-committed changelog entry: " + commitsResponse.body());
  }

  @Test
  @Order(3)
  @DisplayName("Phase 1c: changelog for master commit contains semantic change entries")
  void changelogContainsEntriesForMasterCommit() {
    assertNotNull(sha1, "sha1 must be set by test order 2");

    var response = get("/vsum/changelog/master/" + sha1);

    assertEquals(200, response.statusCode(),
        "Expected 200 but got " + response.statusCode() + ": " + response.body());
    assertTrue(response.body().contains("changeType"),
        "Changelog must contain changeType entries: " + response.body());
  }

  @Test
  @Order(4)
  @DisplayName("Phase 2a: create feature branch and switch to it")
  void createAndSwitchToFeatureBranch() {
    var create = post("/vsum/branch",
        "{\"name\":\"" + FEATURE_BRANCH + "\",\"fromBranch\":\"master\"}");
    assertEquals(200, create.statusCode(),
        "Branch creation failed: " + create.body());

    var switchResponse = post("/vsum/branch/" + FEATURE_BRANCH.replace("/", "%2F") + "/switch", "");
    assertEquals(200, switchResponse.statusCode(),
        "Branch switch failed: " + switchResponse.body());
    assertTrue(switchResponse.body().contains(FEATURE_BRANCH),
        "Switch response must name the target branch: " + switchResponse.body());
  }

  @Test
  @Order(5)
  @DisplayName("Phase 2b: add second System on feature branch via Vitruv API and commit")
  void addSecondSystemOnFeatureBranch() {
    VitruvClient client = client();

    View rawView = openView(client, null);
    CommittableView view = rawView.withChangeDerivingTrait(
        new DefaultStateBasedChangeResolutionStrategy());

    System system2 = ModelFactory.eINSTANCE.createSystem();
    URI uri = URI.createFileURI(repoRoot.resolve("system2.model").toString());
    view.registerRoot(system2, uri);
    view.commitChanges();

    var response = post("/vsum/commit", "{\"message\":\"Feature: add System2\"}");
    assertEquals(200, response.statusCode(),
        "Commit on feature branch failed: " + response.body());

    sha2 = extractSha(response.body());
    assertNotNull(sha2, "Failed to extract sha2 from: " + response.body());
  }

  @Test
  @Order(6)
  @DisplayName("Phase 2c: feature branch shows 2 Systems and changelog has entries")
  void featureBranchHasTwoSystemsAndChangelog() {
    View view = openView(client(), System.class);
    assertEquals(2, view.getRootObjects(System.class).size(),
        "Feature branch must show both Systems (inherited from master + new one)");

    assertNotNull(sha2, "sha2 must be set by test order 5");
    var changelog = get("/vsum/changelog/" + FEATURE_BRANCH.replace("/", "%2F") + "/" + sha2);
    assertEquals(200, changelog.statusCode(),
        "Expected changelog 200 but got " + changelog.statusCode() + ": " + changelog.body());
    assertTrue(changelog.body().contains("changeType"),
        "Feature branch changelog must contain changeType entries: " + changelog.body());
  }

  @Test
  @Order(7)
  @DisplayName("Phase 3: switch back to master - second System must not be visible (branch isolation)")
  void masterIsolatedFromFeatureBranchChanges() {
    var switchResponse = post("/vsum/branch/master/switch", "");
    assertEquals(200, switchResponse.statusCode(),
        "Switch back to master failed: " + switchResponse.body());

    View view = openView(client(), System.class);
    assertEquals(1, view.getRootObjects(System.class).size(),
        "Master must show only the original System - feature branch changes must not bleed over");
  }

  @Test
  @Order(8)
  @DisplayName("Phase 4: create named version snapshot of master state before merge")
  void createVersionSnapshotBeforeMerge() {
    var create = post("/vsum/version",
        "{\"versionId\":\"" + VERSION_ID + "\",\"description\":\"State before feature merge\"}");
    assertEquals(200, create.statusCode(),
        "Version creation failed: " + create.body());
    assertTrue(create.body().contains(VERSION_ID),
        "Version response must contain the version ID: " + create.body());

    var detail = get("/vsum/version/" + VERSION_ID);
    assertEquals(200, detail.statusCode(),
        "Version detail lookup failed: " + detail.body());
    assertTrue(detail.body().contains(VERSION_ID),
        "Version detail must contain the version ID: " + detail.body());
  }

  @Test
  @Order(9)
  @DisplayName("Phase 5a: merge feature branch into master - status SUCCESS or FAST_FORWARD")
  void mergeFeatureBranchIntoMaster() {
    var response = post("/vsum/merge",
        "{\"sourceBranch\":\"" + FEATURE_BRANCH + "\",\"deleteAfterMerge\":false}");
    assertEquals(200, response.statusCode(),
        "Merge endpoint returned error: " + response.body());

    String body = response.body();
    assertTrue(body.contains("SUCCESS") || body.contains("FAST_FORWARD"),
        "Expected SUCCESS or FAST_FORWARD merge status but got: " + body);
    assertTrue(body.contains("\"successful\" : true") || body.contains("\"successful\":true"),
        "Expected successful:true in merge response: " + body);
  }

  @Test
  @Order(10)
  @DisplayName("Phase 5b: after merge, master V-SUM reloaded and shows both Systems")
  void masterShowsBothSystemsAfterMerge() {
    View view = openView(client(), System.class);
    assertEquals(2, view.getRootObjects(System.class).size(),
        "Master must show both Systems after merging the feature branch - "
            + "V-SUM must have reloaded from the merged working tree");
  }

  @Test
  @Order(11)
  @DisplayName("Phase 5c: master commit history contains the feature branch commit after merge")
  void masterCommitHistoryContainsFeatureCommit() {
    var response = get("/vsum/commit/master");
    assertEquals(200, response.statusCode());
    // For a fast-forward merge there is no dedicated merge commit.
    // The feature branch commits are directly reachable from master HEAD.
    assertTrue(response.body().contains("Feature: add System2"),
        "Master commit history must include the feature branch commit after merge: "
            + response.body());
  }
}
