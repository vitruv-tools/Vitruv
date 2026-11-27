package tools.vitruv.framework.remote.server.rest.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.methodologisttemplate.model.model.ModelFactory;
import tools.vitruv.methodologisttemplate.model.model.System;
import tools.vitruv.methodologisttemplate.model.model2.Root;

/**
 * Behavioral integration tests: verifies that model changes sent via the REST API are correctly
 * propagated through the V-SUM, triggering consistency reactions, and that branch switching
 * reloads the right model state.
 *
 * <p>Tests are ordered because later tests build on model state created by earlier ones.
 * All tests share the same server and V-SUM instance (one per class).
 *
 * <p>Prerequisites (enforced by test order):
 * <ol>
 *   <li>A "default" view type must be registered with the V-SUM (done in
 *       {@link AbstractServerIntegrationTest#startServer()}).
 *   <li>The {@link VitruvRemoteConnection} is used as the Java client, using the same
 *       port as the running test server.
 * </ol>
 */
@TestMethodOrder(OrderAnnotation.class)
class ModelInteractionIT extends AbstractServerIntegrationTest {

  // The V-SUM stores model files relative to the repo root.
  // This URI is the persistent address of the System model resource
  // written during the first test: all subsequent tests can load it.
  private static final String MODEL_RESOURCE_NAME = "example.model";

  // Shared client temp directory, created once and reused across all tests.
  // MUST be outside repoRoot: VirtualModelBuilder marks repoRoot as the
  // project root via ProjectMarker. IdTransformation walks ancestors and would
  // inherit that root, causing the client's ResourceSetDeserializer to call
  // resource.save() at repoRoot/example.model — overwriting the server's XMI
  // file with emfjson JSON. The reload then fails with SAXParseException
  // ("Content is not allowed in prolog") because XMIResourceImpl reads JSON.
  private Path clientTempDir;

  private Path clientTemp() {
    if (clientTempDir == null) {
      try {
        clientTempDir = Files.createTempDirectory("vitruv-client-");
      } catch (Exception e) {
        fail("Could not create client temp dir: " + e.getMessage());
      }
    }
    return clientTempDir;
  }

  /**
   * Creates a {@link VitruvRemoteConnection} pointing at the test server.
   * Each call returns a new connection; the underlying HttpClient is stateless.
   */
  private VitruvClient client() {
    return new VitruvRemoteConnection("http", "localhost", server.getPort(), clientTemp());
  }

  /**
   * Opens a view via the remote client and selects all elements that are
   * instances of the given root type filter.
   *
   * <p>If {@code rootTypeFilter} is null all selectable elements are selected.
   */
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

    // Select only elements matching the requested root type (or all if no filter).
    selector.getSelectableElements().forEach(e -> {
      boolean include = rootTypeFilter == null || rootTypeFilter.isInstance(e);
      selector.setSelected(e, include);
    });

    return selector.createView();
  }

  /**
   * Adds a {@link System} element via the REST API and verifies that the
   * Model2Model2 consistency reaction automatically creates a corresponding
   * {@link Root} element in the Model2 metamodel.
   *
   * <p>This is the core behavioral test: it proves that the propagation pipeline
   * (client → REST endpoint → V-SUM → reaction framework) works end-to-end.
   */
  @Test
  @Order(1)
  @DisplayName("adding System via REST triggers Model2 reaction (Root created)")
  void addingSystemTriggersModel2Reaction() {
    VitruvClient client = client();

    // Open an empty view (fresh V-SUM has no elements to select).
    View rawView = openView(client, null);

    // Wrap in change-deriving trait so that resource-level state diff is used
    // to compute the VitruviusChange that is sent to the server.
    CommittableView view = rawView.withChangeDerivingTrait(
        new DefaultStateBasedChangeResolutionStrategy());

    // Register a new System root at the well-known model URI.
    // This adds a new resource to the view's ResourceSet without
    // touching the server yet: the change is sent on commitChanges().
    System system = ModelFactory.eINSTANCE.createSystem();
    URI modelUri = URI.createFileURI(repoRoot.resolve(MODEL_RESOURCE_NAME).toString());
    view.registerRoot(system, modelUri);

    // Commit: derive diff against original empty state, PATCH /vsum/view,
    // server propagates change, reaction fires, Root created in Model2.
    view.commitChanges();

    // Verify reaction: open a new view filtered to Root objects only.
    // If the reaction fired, exactly one Root should now be present.
    View reactionView = openView(client, Root.class);
    Collection<Root> roots = reactionView.getRootObjects(Root.class);
    assertEquals(1, roots.size(),
        "Expected Model2 reaction to have created exactly one Root element");

    Root root = roots.iterator().next();
    assertNotNull(root, "Root element must not be null");
  }

  /**
   * Re-opens a view after the System was committed in test 1 and verifies that
   * the V-SUM's in-memory state correctly reflects the persistent model.
   *
   * <p>This guards against regressions where the server propagates changes
   * in memory but does not retain them across view open/close cycles.
   */
  @Test
  @Order(2)
  @DisplayName("re-opening view after commit shows committed System element")
  void reopenedViewShowsCommittedSystem() {
    VitruvClient client = client();

    View view = openView(client, System.class);
    Collection<System> systems = view.getRootObjects(System.class);

    assertEquals(1, systems.size(),
        "Expected the System added in test 1 to be present in a freshly opened view");
  }

  /**
   * Re-opens a view filtered to {@link Root} elements and verifies the reaction
   * result from test 1 is still present.
   *
   * <p>Confirms that the Model2 state is persisted by the V-SUM, not just held
   * transiently during the propagation call.
   */
  @Test
  @Order(3)
  @DisplayName("re-opening view after commit shows reaction-created Root element")
  void reopenedViewShowsModel2Root() {
    VitruvClient client = client();

    View view = openView(client, Root.class);
    Collection<Root> roots = view.getRootObjects(Root.class);

    assertEquals(1, roots.size(),
        "Expected reaction-created Root to be present in a freshly opened view");
  }

  /**
   * Verifies that model state is branch-isolated:
   * <ol>
   *   <li>Commit the current model state to the {@code master} branch via the
   *       REST commit endpoint.
   *   <li>Create and switch to a new {@code feature/isolation-test} branch.
   *   <li>On the feature branch the V-SUM is reloaded from the checked-out
   *       files: because master was committed, the feature branch starts with
   *       the same state (one System). This confirms the commit+reload cycle
   *       works rather than starting completely empty.
   *   <li>Switch back to {@code master} and verify the original System is still
   *       there.
   * </ol>
   *
   * <p>Note: this is an isolation-via-reload test, not a divergence test. A full
   * divergence test (add different data on each branch, verify they don't bleed)
   * requires two separate commits and is left for a dedicated test.
   */
  @Test
  @Order(4)
  @DisplayName("branch switch reloads model state from checked-out files")
  void branchSwitchReloadsModelState() {
    // Step 1: commit the current model to git so the branch starts clean.
    var commitResponse = post("/vsum/commit", "{\"message\": \"behavioral test baseline\"}");
    // Commit may return 200 (committed) or 409 (nothing to commit: already clean).
    assertTrue(commitResponse.statusCode() == 200 || commitResponse.statusCode() == 409,
        "Unexpected commit status: " + commitResponse.statusCode()
            + ": " + commitResponse.body());

    // Step 2: create a feature branch forked from master.
    var createResponse = post("/vsum/branch",
        "{\"name\": \"feature/isolation-test\", \"fromBranch\": \"master\"}");
    assertEquals(200, createResponse.statusCode(),
        "Branch creation failed: " + createResponse.body());

    // Step 3: switch to the feature branch.
    var switchResponse = post("/vsum/branch/feature%2Fisolation-test/switch", "");
    assertEquals(200, switchResponse.statusCode(),
        "Branch switch failed: " + switchResponse.body());

    // Step 4: on the feature branch the model is reloaded from git.
    // The branch was created from master HEAD which included our System commit,
    // so the model should still have exactly one System.
    VitruvClient client = client();
    View featureView = openView(client, System.class);
    Collection<System> featureSystems = featureView.getRootObjects(System.class);
    assertEquals(1, featureSystems.size(),
        "Feature branch (forked from master) should show the committed System");

    // Step 5: switch back to master.
    var switchBackResponse = post("/vsum/branch/master/switch", "");
    assertEquals(200, switchBackResponse.statusCode(),
        "Switch back to master failed: " + switchBackResponse.body());

    // Step 6: master should still have the same one System.
    View masterView = openView(client(), System.class);
    Collection<System> masterSystems = masterView.getRootObjects(System.class);
    assertEquals(1, masterSystems.size(),
        "After switching back to master, the original System must still be present");
  }


  /**
   * Verifies true branch divergence:
   * <ol>
   *   <li>Fork a new branch {@code feature/divergence-test} from the committed master state.
   *   <li>On the feature branch, add a second {@link System} at a different resource URI
   *       via the Vitruv API and commit it.
   *   <li>Verify the feature branch shows two Systems (the original plus the new one).
   *   <li>Switch back to master and verify only one System is visible: the second
   *       resource file is not present in the master checkout.
   * </ol>
   */
  @Test
  @Order(5)
  @DisplayName("changes committed on feature branch are absent on master after branch switch")
  void featureBranchChangesAbsentOnMaster() {
    // Step 1: fork feature/divergence-test from the current master state.
    var createResponse = post("/vsum/branch",
        "{\"name\": \"feature/divergence-test\", \"fromBranch\": \"master\"}");
    assertEquals(200, createResponse.statusCode(),
        "Branch creation failed: " + createResponse.body());

    // Step 2: switch to the feature branch.
    var switchResponse = post("/vsum/branch/feature%2Fdivergence-test/switch", "");
    assertEquals(200, switchResponse.statusCode(),
        "Branch switch failed: " + switchResponse.body());

    // Step 3: add a second System at a new resource URI via the Vitruv API.
    // Using a different file name (divergence.model) ensures it is a distinct
    // resource from example.model and will not exist on master after switching back.
    VitruvClient client = client();
    View rawView = openView(client, null);
    CommittableView view = rawView.withChangeDerivingTrait(
        new DefaultStateBasedChangeResolutionStrategy());

    System featureSystem = ModelFactory.eINSTANCE.createSystem();
    URI featureUri = URI.createFileURI(repoRoot.resolve("divergence.model").toString());
    view.registerRoot(featureSystem, featureUri);
    view.commitChanges();

    // Step 4: commit the new System to the feature branch.
    var commitResponse = post("/vsum/commit",
        "{\"message\": \"Add divergent System on feature branch\"}");
    assertEquals(200, commitResponse.statusCode(),
        "Commit on feature branch failed: " + commitResponse.body());

    // Step 5: verify feature branch has both Systems (example.model + divergence.model).
    View featureView = openView(client(), System.class);
    Collection<System> featureSystems = featureView.getRootObjects(System.class);
    assertEquals(2, featureSystems.size(),
        "Feature branch should show both the original and the new System");

    // Step 6: switch back to master.
    var switchBackResponse = post("/vsum/branch/master/switch", "");
    assertEquals(200, switchBackResponse.statusCode(),
        "Switch back to master failed: " + switchBackResponse.body());

    // Step 7: master must show only the original System; divergence.model is not in
    // the master checkout so the V-SUM reload must not include the feature branch System.
    View masterView = openView(client(), System.class);
    Collection<System> masterSystems = masterView.getRootObjects(System.class);
    assertEquals(1, masterSystems.size(),
        "Master should show only 1 System after switching back; feature branch change must not bleed");
  }
}
