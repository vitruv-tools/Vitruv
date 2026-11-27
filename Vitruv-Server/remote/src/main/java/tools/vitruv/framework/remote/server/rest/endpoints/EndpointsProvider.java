package tools.vitruv.framework.remote.server.rest.endpoints;

import java.util.ArrayList;
import java.util.List;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.DeleteEndpoint;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.server.rest.PatchEndpoint;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.remote.server.rest.PutEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchConflictsEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchHistoryEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchParamGetDispatcher;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchParamPostDispatcher;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchStateEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchTopologyEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.CreateBranchEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.DeleteBranchEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.DeltaEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.GetSingleBranchEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.ListBranchesEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.SetBranchMaturityEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.SwitchBranchEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.changelog.ChangelogEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.commit.CommitEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.commit.ListCommitsEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.merge.MergeEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.CreateVersionBranchEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.CreateVersionEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.DeleteVersionEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.GetVersionEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.ListVersionsEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.RollbackConfirmEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.RollbackPreviewEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.VersionModelEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.version.VersionParamGetDispatcher;
import tools.vitruv.framework.remote.server.rest.endpoints.version.VersionParamPostDispatcher;
import tools.vitruv.framework.remote.server.rest.endpoints.version.VersionViewEndpoint;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/** Provides all REST endpoints for the Vitruv server. */
public class EndpointsProvider {

  /**
   * Creates and returns all REST endpoints for the Vitruv server.
   *
   * @param virtualModel The virtual model to use.
   * @param mapper The JSON mapper to use.
   * @return A list of all REST endpoints.
   */
  public static List<PathEndointCollector> getAllEndpoints(
      VirtualModel virtualModel, JsonMapper mapper) {
    var defaultEndpoints = getDefaultEndpoints();

    List<PathEndointCollector> result = new ArrayList<>();
    result.add(
        new PathEndointCollector(
            EndpointPath.HEALTH,
            new HealthEndpoint(),
            defaultEndpoints.postEndpoint(),
            defaultEndpoints.putEndpoint(),
            defaultEndpoints.patchEndpoint(),
            defaultEndpoints.deleteEndpoint()));
    result.add(
        new PathEndointCollector(
            EndpointPath.IS_VIEW_CLOSED,
            new IsViewClosedEndpoint(),
            defaultEndpoints.postEndpoint(),
            defaultEndpoints.putEndpoint(),
            defaultEndpoints.patchEndpoint(),
            defaultEndpoints.deleteEndpoint()));
    result.add(
        new PathEndointCollector(
            EndpointPath.IS_VIEW_OUTDATED,
            new IsViewOutdatedEndpoint(),
            defaultEndpoints.postEndpoint(),
            defaultEndpoints.putEndpoint(),
            defaultEndpoints.patchEndpoint(),
            defaultEndpoints.deleteEndpoint()));
    result.add(
        new PathEndointCollector(
            EndpointPath.VIEW,
            new UpdateViewEndpoint(mapper),
            new ViewEndpoint(mapper),
            defaultEndpoints.putEndpoint(),
            new ChangePropagationEndpoint(mapper),
            new CloseViewEndpoint()));
    result.add(
        new PathEndointCollector(
            EndpointPath.VIEW_SELECTOR,
            new ViewSelectorEndpoint(virtualModel, mapper),
            defaultEndpoints.postEndpoint(),
            defaultEndpoints.putEndpoint(),
            defaultEndpoints.patchEndpoint(),
            defaultEndpoints.deleteEndpoint()));
    result.add(
        new PathEndointCollector(
            EndpointPath.VIEW_TYPES,
            new ViewTypesEndpoint(virtualModel, mapper),
            defaultEndpoints.postEndpoint(),
            defaultEndpoints.putEndpoint(),
            defaultEndpoints.patchEndpoint(),
            defaultEndpoints.deleteEndpoint()));
    result.add(
        new PathEndointCollector(
            EndpointPath.CHANGE_DERIVING,
            defaultEndpoints.getEndpoint(),
            defaultEndpoints.postEndpoint(),
            defaultEndpoints.putEndpoint(),
            new ChangeDerivingEndpoint(mapper),
            defaultEndpoints.deleteEndpoint()));

    return result;
  }

  /**
   * Returns a builder for constructing the endpoint list with optional manager groups.
   * The base V-SUM endpoints are always included; add further groups via the builder.
   *
   * <p>Example:
   * <pre>
   * EndpointsProvider.builder(model, mapper)
   *     .branchManager(branchMgr)
   *     .commitManager(commitMgr)
   *     .versioningService(versioningSvc)
   *     .build();
   * </pre>
   *
   * @param virtualModel the virtual model for the base V-SUM endpoints.
   * @param mapper the JSON mapper used by all endpoints.
   * @return a new {@link Builder}.
   */
  public static Builder builder(VirtualModel virtualModel, JsonMapper mapper) {
    return new Builder(virtualModel, mapper);
  }

  /**
   * Builder for incrementally adding endpoint groups to the server.
   *
   * <p>Only endpoints whose backing manager is set are registered.
   * Omitting a manager simply leaves those routes unregistered.
   */
  public static final class Builder {

    private final VirtualModel virtualModel;
    private final JsonMapper mapper;
    private BranchManager branchManager;
    private CommitManager commitManager;
    private MergeManager mergeManager;
    private VersioningService versioningService;

    private Builder(VirtualModel virtualModel, JsonMapper mapper) {
      this.virtualModel = virtualModel;
      this.mapper = mapper;
    }

    /**
     * Registers branch lifecycle endpoints (list, create, delete, switch, state, topology).
     *
     * @param branchManager the branch manager backing these endpoints.
     * @return this builder.
     */
    public Builder branchManager(BranchManager branchManager) {
      this.branchManager = branchManager;
      return this;
    }

    /**
     * Registers commit and changelog endpoints.
     *
     * @param commitManager the commit manager backing these endpoints.
     * @return this builder.
     */
    public Builder commitManager(CommitManager commitManager) {
      this.commitManager = commitManager;
      return this;
    }

    /**
     * Registers the merge endpoint.
     *
     * @param mergeManager the merge manager backing this endpoint.
     * @return this builder.
     */
    public Builder mergeManager(MergeManager mergeManager) {
      this.mergeManager = mergeManager;
      return this;
    }

    /**
     * Registers versioning endpoints (list, get, create, delete, rollback, branch-from-version).
     *
     * @param versioningService the versioning service backing these endpoints.
     * @return this builder.
     */
    public Builder versioningService(VersioningService versioningService) {
      this.versioningService = versioningService;
      return this;
    }

    /**
     * Builds and returns the complete endpoint list.
     *
     * @return a list of all registered {@link PathEndointCollector} entries.
     */
    public List<PathEndointCollector> build() {
      List<PathEndointCollector> result = getAllEndpoints(virtualModel, mapper);
      var def = getDefaultEndpoints();

      if (branchManager != null) {
        // Fixed paths: list all branches, create branch, topology
        result.add(new PathEndointCollector(
            EndpointPath.BRANCH,
            new ListBranchesEndpoint(branchManager, mapper),
            new CreateBranchEndpoint(branchManager, mapper),
            def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));
        result.add(new PathEndointCollector(
            EndpointPath.BRANCH_TOPOLOGY,
            new BranchTopologyEndpoint(branchManager, mapper),
            def.postEndpoint(), def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));

        // Parameterized catch-all: /vsum/branch/{branchName} and sub-paths
        var switchEndpoint = new SwitchBranchEndpoint(branchManager, mapper);
        var stateEndpoint = new BranchStateEndpoint(branchManager, mapper);
        var historyEndpoint = commitManager != null
            ? (GetEndpoint) new BranchHistoryEndpoint(commitManager)
            : def.getEndpoint();
        var conflictsEndpoint = new BranchConflictsEndpoint(virtualModel.getFolder(), mapper);
        var singleEndpoint = new GetSingleBranchEndpoint(branchManager, mapper);
        var maturityEndpoint = new SetBranchMaturityEndpoint(branchManager, mapper);

        var branchParamGet = new BranchParamGetDispatcher(
            singleEndpoint, stateEndpoint, historyEndpoint, conflictsEndpoint);
        result.add(new PathEndointCollector(
            EndpointPath.BRANCH_PARAM,
            branchParamGet,
            new BranchParamPostDispatcher(switchEndpoint),
            def.putEndpoint(),
            maturityEndpoint,
            new DeleteBranchEndpoint(branchManager)));
      }

      if (commitManager != null) {
        // POST /vsum/commit (unchanged), GET /vsum/commit/{branchName}
        result.add(new PathEndointCollector(
            EndpointPath.COMMIT,
            def.getEndpoint(),
            new CommitEndpoint(commitManager, mapper, virtualModel.getFolder()),
            def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));
        result.add(new PathEndointCollector(
            EndpointPath.COMMIT_BY_BRANCH,
            new ListCommitsEndpoint(commitManager, mapper),
            def.postEndpoint(), def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));
        result.add(new PathEndointCollector(
            EndpointPath.CHANGELOG_BY_COMMIT,
            new ChangelogEndpoint(commitManager, mapper),
            def.postEndpoint(), def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));
        result.add(new PathEndointCollector(
            EndpointPath.DELTA_BY_BRANCH,
            new DeltaEndpoint(commitManager, virtualModel.getFolder()),
            def.postEndpoint(), def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));
      }

      if (mergeManager != null) {
        result.add(new PathEndointCollector(
            EndpointPath.MERGE,
            def.getEndpoint(), new MergeEndpoint(mergeManager, mapper),
            def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));
      }

      if (versioningService != null) {
        // Fixed: list all versions, create version
        result.add(new PathEndointCollector(
            EndpointPath.VERSION,
            new ListVersionsEndpoint(versioningService, mapper),
            new CreateVersionEndpoint(versioningService, mapper),
            def.putEndpoint(), def.patchEndpoint(), def.deleteEndpoint()));

        // Parameterized catch-all: /vsum/version/{versionId} and sub-paths
        var getVersion = new GetVersionEndpoint(versioningService, mapper);
        var versionModel = new VersionModelEndpoint(versioningService, mapper, virtualModel.getFolder());
        var versionView = new VersionViewEndpoint(versioningService, virtualModel.getFolder());
        var rollbackPreview = new RollbackPreviewEndpoint(versioningService, mapper);
        var rollbackConfirm = new RollbackConfirmEndpoint(versioningService, mapper);
        var createBranch = new CreateVersionBranchEndpoint(versioningService, mapper);
        result.add(new PathEndointCollector(
            EndpointPath.VERSION_PARAM,
            new VersionParamGetDispatcher(getVersion, versionModel, versionView),
            new VersionParamPostDispatcher(rollbackPreview, rollbackConfirm, createBranch),
            def.putEndpoint(), def.patchEndpoint(),
            new DeleteVersionEndpoint(versioningService)));
      }

      return result;
    }
  }

  private static PathEndointCollector getDefaultEndpoints() {
    var getEndpoint =
        new GetEndpoint() {
          @Override
          public String process(HttpWrapper wrapper) throws ServerHaltingException {
            throw notFound("Get mapping for this request path not found!");
          }
        };
    var postEndpoint =
        new PostEndpoint() {
          @Override
          public String process(HttpWrapper wrapper) throws ServerHaltingException {
            throw notFound("Post mapping for this request path not found!");
          }
        };
    var patchEndpoint =
        new PatchEndpoint() {
          @Override
          public String process(HttpWrapper wrapper) throws ServerHaltingException {
            throw notFound("Patch mapping for this request path not found!");
          }
        };
    var deleteEndpoint =
        new DeleteEndpoint() {
          @Override
          public String process(HttpWrapper wrapper) throws ServerHaltingException {
            throw notFound("Delete mapping for this request path not found!");
          }
        };
    var putEndpoint =
        new PutEndpoint() {
          @Override
          public String process(HttpWrapper wrapper) throws ServerHaltingException {
            throw notFound("Put mapping for this request path not found!");
          }
        };
    return new PathEndointCollector(
        "", getEndpoint, postEndpoint, putEndpoint, patchEndpoint, deleteEndpoint);
  }

  private EndpointsProvider() {}
}
