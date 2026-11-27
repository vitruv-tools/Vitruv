package tools.vitruv.framework.remote.server.rest.endpoints.commit;

import com.fasterxml.jackson.core.JsonProcessingException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code GET /vsum/commit/{branchName}}
 *
 * <p>Returns a list of all commits on the given branch, newest first.
 * The branch name is extracted from path segment 0 after {@code /vsum/commit/}.
 *
 * <p>Each entry includes commit metadata and, if a semantic changelog JSON was written
 * for that commit, the total number of semantic changes.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/commit/feature%2Fmy-feature
 * </pre>
 *
 * <p>Example response:
 * <pre>
 * [
 *   {
 *     "sha": "a1b2c3d4e5f6...",
 *     "shortSha": "a1b2c3d",
 *     "branch": "feature/my-feature",
 *     "authorName": "Linh Nguyen",
 *     "authorEmail": "linh@example.com",
 *     "authorDate": "2026-04-07T14:00:00",
 *     "message": "Add system element",
 *     "parentShas": ["b2c3d4e"],
 *     "hasChangelog": true,
 *     "totalSemanticChanges": 3
 *   }
 * ]
 * </pre>
 */
public class ListCommitsEndpoint implements GetEndpoint {

  private final CommitManager commitManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link ListCommitsEndpoint}.
   *
   * @param commitManager the commit manager used to list commits.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public ListCommitsEndpoint(CommitManager commitManager, JsonMapper mapper) {
    this.commitManager = commitManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String branch = wrapper.getPathSegment(0);
    if (branch == null || branch.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    try {
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(commitManager.listCommits(branch));
    } catch (BranchOperationException | JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
