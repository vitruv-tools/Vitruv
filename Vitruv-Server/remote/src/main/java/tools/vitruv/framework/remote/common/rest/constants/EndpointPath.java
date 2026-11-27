package tools.vitruv.framework.remote.common.rest.constants;

/** Constants for endpoint paths used in the Vitruvius remote framework. */
public final class EndpointPath {
  /** The health check endpoint path. */
  public static final String HEALTH = "/health";

  /** The endpoint path for view types. */
  public static final String VIEW_TYPES = "/vsum/view/types";

  /** The endpoint path for view selectors. */
  public static final String VIEW_SELECTOR = "/vsum/view/selector";

  /** The endpoint path for views. */
  public static final String VIEW = "/vsum/view";

  /** The endpoint path to check if a view is closed. */
  public static final String IS_VIEW_CLOSED = "/vsum/view/closed";

  /** The endpoint path to check if a view is outdated. */
  public static final String IS_VIEW_OUTDATED = "/vsum/view/outdated";

  /** The endpoint path for deriving changes. */
  public static final String CHANGE_DERIVING = "/vsum/view/derive-changes";

  /** The endpoint path for branch management (list, create). */
  public static final String BRANCH = "/vsum/branch";

  /** The endpoint path for retrieving the branch topology. */
  public static final String BRANCH_TOPOLOGY = "/vsum/branch/topology";

  /**
   * Catch-all context prefix for parameterized branch endpoints.
   * Handles: GET|DELETE /vsum/branch/{branchName},
   * GET /vsum/branch/{branchName}/state|history|conflicts,
   * POST /vsum/branch/{branchName}/switch,
   * PATCH /vsum/branch/{branchName}/maturity.
   */
  public static final String BRANCH_PARAM = "/vsum/branch/";

  /** The endpoint path for committing model changes. */
  public static final String COMMIT = "/vsum/commit";

  /**
   * Catch-all context prefix for commit listing by branch.
   * Handles: GET /vsum/commit/{branchName}.
   */
  public static final String COMMIT_BY_BRANCH = "/vsum/commit/";

  /**
   * Catch-all context prefix for changelog retrieval.
   * Handles: GET /vsum/changelog/{branchName}/{sha}.
   */
  public static final String CHANGELOG_BY_COMMIT = "/vsum/changelog/";

  /**
   * Catch-all context prefix for delta retrieval.
   * Handles: GET /vsum/delta/{branchName}?base=main.
   */
  public static final String DELTA_BY_BRANCH = "/vsum/delta/";

  /** The endpoint path for merging branches. */
  public static final String MERGE = "/vsum/merge";

  /** The endpoint path for listing and creating versions. */
  public static final String VERSION = "/vsum/version";

  /**
   * Catch-all context prefix for parameterized version endpoints.
   * Handles: GET|DELETE /vsum/version/{versionId},
   * GET /vsum/version/{versionId}/model|view,
   * POST /vsum/version/{versionId}/rollback/preview|rollback/confirm|branch.
   */
  public static final String VERSION_PARAM = "/vsum/version/";

  private EndpointPath() throws InstantiationException {
    throw new InstantiationException("Cannot be instantiated");
  }
}
