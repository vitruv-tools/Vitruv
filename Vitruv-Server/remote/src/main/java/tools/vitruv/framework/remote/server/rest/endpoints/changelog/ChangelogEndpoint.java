package tools.vitruv.framework.remote.server.rest.endpoints.changelog;

import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code GET /vsum/changelog/{branchName}/{sha}}
 *
 * <p>Returns the full semantic changelog document for a specific commit on a branch.
 * The branch name is path segment 0 and the 7-character short SHA is path segment 1,
 * both relative to the {@code /vsum/changelog/} context prefix.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/changelog/feature%2Fmy-feature/a1b2c3d
 * </pre>
 *
 * <p>Returns the full {@code ChangelogDocument} JSON including {@code fileChanges} with
 * per-element {@code SemanticChangeEntry} records and the {@code summary} block.
 *
 * <p>Returns {@code 405} if no changelog file was found for the given branch and SHA.
 */
public class ChangelogEndpoint implements GetEndpoint {

  private final CommitManager commitManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link ChangelogEndpoint}.
   *
   * @param commitManager the commit manager used to read the changelog.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public ChangelogEndpoint(CommitManager commitManager, JsonMapper mapper) {
    this.commitManager = commitManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String branch = wrapper.getPathSegment(0);
    String sha = wrapper.getPathSegment(1);
    if (branch == null || branch.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    if (sha == null || sha.isBlank()) {
      throw badRequest("Missing commit SHA in path");
    }
    // readChangelogRaw() expects a 7-char short SHA; truncate if caller passes a full SHA.
    String shortSha = sha.length() > 7 ? sha.substring(0, 7) : sha;
    try {
      String raw = commitManager.readChangelogRaw(branch, shortSha);
      if (raw == null) {
        throw notFound(
            "No changelog found for branch '" + branch + "', commit '" + shortSha + "'");
      }
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return raw;
    } catch (BranchOperationException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
